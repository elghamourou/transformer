package map;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;











import object.ChooseBox;
import object.DragableMapObject;
import object.ExpressionBox;
import object.FunctionBox;
import object.IfBox;
import object.LinkLine;
import object.VariableBox;
import object.WhenBox;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import utils.XPathUtils;



public class Objects extends ArrayList
{
	private Map _map = null;

	public Objects(Map map)
	{
		_map = map;
	}
	
	/**
	 * Creates a new link and adds it to the
	 * _objects collection.
	 *
	 * @param src source Linkable object
	 * @param dst destination Linkable object
	 *
	 * @return Newly created LinkLine object
	 */
	public LinkLine addLink(ILinkable src, ILinkable dst)
	{
		if((null != src.getParentObject() && null != dst.getParentObject()) &&
				(src.getParentObject() == dst.getParentObject()))
			return null;
			
		LinkLine line = new LinkLine(src, dst);

		super.add(line);

		src.getLinks().add(line);
		dst.getLinks().add(line);

		return line;
	}

	public void clear()
	{
		Iterator iter = iterator();
		while ( iter.hasNext() ) {
			IMapObject obj = (IMapObject)iter.next();
			if(obj instanceof LinkLine) {
				LinkLine line = (LinkLine)obj;

				ILinkable src = line.getSrc();
				ILinkable dst = line.getDest();

				if(src != null)
					src.getLinks().remove(line);
				if(dst != null)
					dst.getLinks().remove(line);
			}
		}
		super.clear();
	}


	public void clearLinks()
	{
		for(int i=0; i<size(); i++)
		{
			IMapObject tmp = (IMapObject)get(i);
			if(tmp instanceof LinkLine) {
				LinkLine line = (LinkLine)tmp;
				remove(line);
				i--;
			}
		}
	}

	/**
	 * Removes the link from the _objects collection.
	 *
	 * @param line
	 */
	private void remove(LinkLine line)
	{
		ILinkable src = line.getSrc();
		ILinkable dst = line.getDest();

		if(src != null)
			src.getLinks().remove(line);
		if(dst != null)
			dst.getLinks().remove(line);

		super.remove(line);
	}

	public void remove(IMapObject obj)
	{
		if(obj instanceof LinkLine)
			remove((LinkLine)obj);
		else if(obj instanceof DragableMapObject.LinkPoint) {
			removeLinksConnectedTo(obj);
			obj.getParentObject().removeChild(obj);
		} else if(obj instanceof ExpressionBox.Input) {
			removeLinksConnectedTo(obj);
			obj.getParentObject().removeChild(obj);
			super.remove(obj);
		} else {
			removeLinksConnectedTo(obj);
			super.remove(obj);
		}
	}

	public void removeLinksConnectedTo(IMapObject obj)
	{
		for(int i=0; i<size(); i++)
		{
			IMapObject tmp = (IMapObject)get(i);
			if(tmp instanceof LinkLine) {
				LinkLine line = (LinkLine)tmp;

				Object src = line.getSrc();
				Object dst = line.getDest();
				
				if(!(obj instanceof DragableMapObject.LinkPoint))
				{
					if(src instanceof DragableMapObject.LinkPoint)
						src = (IMapObject)((DragableMapObject.LinkPoint)src).getParentObject();
	
					if(dst instanceof DragableMapObject.LinkPoint)
						dst = (IMapObject)((DragableMapObject.LinkPoint)dst).getParentObject();
				}
				
				if	((src != null) && (src.equals(obj)) ||
					(dst != null) && (dst.equals(obj)))
				{
					remove(line);
					i--;
				}
			}
		}
	}
	
	
//	public IMapObject get(Point pt)
//	{
//		Iterator iter = iterator();
//		while ( iter.hasNext() ) {
//			IMapObject obj = (IMapObject)iter.next();
//			if(obj.hitTest(pt)) {
//				IMapObject param = obj.getChildAt(pt);
//				return(param != null ? param : obj);
//			}
//		}
//		return null;
//	}
	
	public IMapObject get(String uniqueName)
	{
		Iterator iter = iterator();
		while ( iter.hasNext() ) {
			IMapObject obj = (IMapObject)iter.next();
			String tmp = obj.getUniqueName();
			if(uniqueName.equals(tmp))
				return obj;
		}
		return null;
	}
	
	public ILinkable getLinkable(String uniqueName)
	{
		String path[] = uniqueName.split("/");
		if(path.length == 2)
		{
			String basename = path[0];
			Iterator iter = iterator();
			while ( iter.hasNext() ) {
				IMapObject obj = (IMapObject)iter.next();
				String tmp = obj.getUniqueName();
				if(basename.equals(tmp))
				{
					return (ILinkable)obj.getChild(path[1]);
				}
			}
		}
		return null;
	}

	public IMapObject getSelectedObject()
	{
		// Check link lines first
		Iterator iter = iterator();
		while ( iter.hasNext() ) {
			IMapObject obj = (IMapObject)iter.next();
			if((obj instanceof LinkLine) && obj.isSelected())
				return obj;
		}

		// And then all other objects
		iter = iterator();
		while ( iter.hasNext() ) {
			IMapObject obj = (IMapObject)iter.next();
			if(!(obj instanceof LinkLine))
			{
				Collection params = obj.getChildObjects();
				if(params != null) {
					Iterator tmp = params.iterator();
					while ( tmp.hasNext() ) {
						ILinkable link = (ILinkable)tmp.next();
						if(link.isSelected())
							return link;
					}
				}
			if( obj.isSelected() )
				return obj;
			}
		}
		return null;
	}
	
	public void deselectAll()
	{
		Iterator iter = iterator();
		while ( iter.hasNext() ) {
			IMapObject obj = (IMapObject)iter.next();
			Collection params = obj.getChildObjects();
			if(params != null) {
				Iterator tmp = params.iterator();
				while ( tmp.hasNext() ) {
					ILinkable link = (ILinkable)tmp.next();
					link.select(false);
				}
			}
			obj.select(false);
		}
	}

	public void objectSelected(IMapObject sel)
	{
		Iterator iter = iterator();
		while ( iter.hasNext() ) {
			IMapObject obj = (IMapObject)iter.next();

			obj.select(sel == obj);

			Collection params = obj.getChildObjects();
			if(params != null) {
				Iterator tmp = params.iterator();
				while ( tmp.hasNext() ) {
					IMapObject link = (IMapObject)tmp.next();
					link.select(sel == link);
				}
			}
		}
	}
	
	public int getFunctionCount()
	{
		int count = 1;
		Iterator iter = iterator();
		while ( iter.hasNext() ) {
			IMapObject obj = (IMapObject)iter.next();
			if(obj instanceof FunctionBox)
				count++;
		}
		return count;
	}
	
	public int getVariableCount()
	{
		int count = 1;
		Iterator iter = iterator();
		while ( iter.hasNext() ) {
			IMapObject obj = (IMapObject)iter.next();
			if(obj instanceof VariableBox)
				count++;
		}
		return count;
	}

	public void save(Element parent)
	{
		Document doc = parent.getOwnerDocument();
		Element objectsElement = doc.createElement("objects");
		
		boolean bAppend = false;
		
		Iterator iter = iterator();
		while ( iter.hasNext() ) {
			Element objectElement = doc.createElement("object");
			IMapObject obj = (IMapObject)iter.next();
			if(obj instanceof LinkLine) {
				objectElement.setAttribute("type", "link");
				obj.save(objectElement);
				bAppend = true;
			} else if(obj instanceof VariableBox) {
				objectElement.setAttribute("type", "variable");
				obj.save(objectElement);
				bAppend = true;
			} else if(obj instanceof FunctionBox) {
				objectElement.setAttribute("type", "function");
				obj.save(objectElement);
				bAppend = true;
			} else if(obj instanceof IfBox) {
				objectElement.setAttribute("type", "if");
				obj.save(objectElement);
				bAppend = true;
			} else if(obj instanceof ChooseBox) {
				objectElement.setAttribute("type", "choose");
				obj.save(objectElement);
				bAppend = true;
			} else if(obj instanceof WhenBox) {
				objectElement.setAttribute("type", "when");
				obj.save(objectElement);
				bAppend = true;
			} else if(obj instanceof ExpressionBox) {
				objectElement.setAttribute("type", "expression");
				obj.save(objectElement);
				bAppend = true;
			}
			if(bAppend)
				objectsElement.appendChild(objectElement);
			bAppend = false;
		}
		parent.appendChild(objectsElement);
	}
	
	public void load(Node parent)
	{
		Node objectsNode = XPathUtils.getNode(parent, "objects");

		List results = XPathUtils.getNodes(objectsNode, "object[@type!='link']");
		if(null != results)
		{
            Iterator resultIter = results.iterator();
			while ( resultIter.hasNext() ) {
            	Node child = (Node)resultIter.next();
            	createObject(child);
			}
		}
		
		loadLinks(objectsNode);
	}

	private void createObject(Node node)
	{
		DragableMapObject obj = null;
		
		//int index = Integer.valueOf(XPathUtils.getNodeText(node, "@index/text()")).intValue();
		
		String type = XPathUtils.getNodeText(node, "@type/text()");
		//String name = XPathUtils.getNodeText(node, "@name/text()");
		
		if(type.equalsIgnoreCase("variable")) {
			obj = new VariableBox(_map, (Element)node);
		} else if(type.equalsIgnoreCase("function")) {
			obj = new FunctionBox(_map, (Element)node);
		} else if(type.equalsIgnoreCase("if")) {
			obj = new IfBox(_map, (Element)node);
		} else if(type.equalsIgnoreCase("choose")) {
			obj = new ChooseBox(_map, (Element)node);
		} else if(type.equalsIgnoreCase("when")) {
			obj = new WhenBox(_map, (Element)node);
		} else if(type.equalsIgnoreCase("expression")) {
			obj = new ExpressionBox(_map, (Element)node);
		}

		if(null != obj)
		{
			//obj.setName(name);
			//obj.load((Element)node);
			_map.addDragableObject(obj);
		}
	}

	private void loadLinks(Node node)
	{
        List results = XPathUtils.getNodes(node, "object[@type='link']");
        if(null != results)
        {
	        Iterator resultIter = results.iterator();
			while ( resultIter.hasNext() ) {
	        	Node child = (Node)resultIter.next();
	        	loadLink(child);
			}
        }
	}
	
	private void loadLink(Node node)
	{
		String srcPath = XPathUtils.getNodeText(node, "@src/text()");
		ILinkable src = null;
		if(null != _map.getSource())
			src = _map.getSource().getNodeByXPath(srcPath);
		if(null == src)
			src = getLinkable(srcPath);

		String dstPath = XPathUtils.getNodeText(node, "@dst/text()");
		ILinkable dst = null;
		if(null != _map.getDestination())
			dst = _map.getDestination().getNodeByXPath(dstPath);
		if(null == dst)
			dst = getLinkable(dstPath);

		if((null != src) && (null != dst))
		{
			LinkLine newLink = addLink(src, dst);
			
			newLink.load((Element)node);
			
			/*Node tmp = XPathUtils.getNode(node, "@xsl-type/text()");
			if(null != tmp)
			{
				String str = tmp.getNodeValue().trim();
    			if(str.equals("xsl:for-each"))
    				newLink.setType( LinkLine.XSL_FOR_EACH );
    			else if(str.equals("xsl:template"))
    				newLink.setType( LinkLine.XSL_TEMPLATE );
    			else if(str.equals("xsl:value-of"))
    				newLink.setType( LinkLine.XSL_VALUE_OF );
			}*/
		}
	}
	
	/*public void move(int x, int y)
	{
		Iterator iter = iterator();
		while ( iter.hasNext() ) {
			IMapObject obj = (IMapObject)iter.next();
			//Point pt = obj.getOrigin();
			//obj.move(pt.x+x, pt.y+y);
			obj.move(x, y);
		}
	}*/
}
