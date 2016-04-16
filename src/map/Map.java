package map;


import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.net.URL;

import object.ChooseBox;
import object.DefaultMapObject;
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
import org.w3c.dom.NodeList;

import simpleMapper.MapperTree;
import simpleMapper.PathList;
import utils.DOMUtils;
import utils.XMLMapperInit;
import utils.XPathUtils;

import java.net.MalformedURLException;
import java.awt.event.MouseMotionListener;


public class Map extends DefaultMapObject
//implements MouseMotionListener
{
	private Objects		objects	= new Objects(this);
	private MapperTree	srcTree	= null;
	private MapperTree	dstTree	= null;
	private boolean		bLoaded	= true;
	private String		uri; 



	public Map()
	{
		bLoaded = true;
	}

	public Map(MapperTree lTree, MapperTree rTree)
	{
		srcTree = lTree;
		dstTree = rTree;
		

	}
	
	public Collection	getChildObjects()		{ return objects; }
	public MapperTree 	getSource()				{ return srcTree; }
	public MapperTree 	getDestination()		{ return dstTree; }

	public void removeChild(IMapObject obj)
	{
		objects.remove(obj);
	}

	public void clear()
	{
		objects.clear();
	}


	
//	public IMapObject getChildAt(Point pt)
//	{
//		return objects.get(pt);
//	}

	public LinkLine addLink(ILinkable src, ILinkable dst)
	{
		LinkLine line = objects.addLink(src, dst);
		return line;
	}

	/*private void removeLink(LinkLine line, boolean bRepaint)
	{
		objects.remove(line);
		if(bRepaint)
			repaint();
	}*/

	public IMapObject getSelectedObject()
	{
		return objects.getSelectedObject();
	}

	

	public void addDragableObject(DragableMapObject obj)
	{
		objects.add(obj);
	}
	
	public FunctionBox addNewFunction()
	{
		int i = objects.getFunctionCount();
		String name = "New function " + i;
		FunctionBox func = new FunctionBox(this, name);
		addDragableObject(func);
		func.chooseFunction();
		return func;
	}

	public VariableBox addNewVariable()
	{
		int i = objects.getVariableCount();
		String name = "New Variable " + i;
		VariableBox vbox = new VariableBox(this, name);
		addDragableObject(vbox);
		return vbox;
	}
	
	public IfBox addNewIfBox()
	{
		IfBox ifbox = new IfBox(this);
		addDragableObject(ifbox);
		return ifbox;
	}
	
	public WhenBox addNewWhenBox()
	{
		WhenBox whenBox = new WhenBox(this);
		addDragableObject(whenBox);
		return whenBox;
	}
	
	public ChooseBox addNewChooseBox()
	{
		ChooseBox choosebox = new ChooseBox(this);
		addDragableObject(choosebox);
		return choosebox;
	}
	
	public ExpressionBox addNewComplexExpression()
	{
		ExpressionBox expbox = new ExpressionBox(this);
		addDragableObject(expbox);
		return expbox;
	}

	
	
	public void clearLinks()
	{
		objects.clearLinks();
	}

	public IMapObject getChild(String uniqueName)
	{
		return objects.get(uniqueName);
	}
	
	public ILinkable getLinkable(String uniqueName)
	{
		return objects.getLinkable(uniqueName);
	}
	
	




	public String getDisplayName()			{ return "Map"; }
	public String getFullPath()				{ return "Map"; }
	public int getID()						{ return hashCode(); } 
	public void setID(int ID)				{ } 
	public String getName()					{ return "Map"; }
//	public Point getOrigin()				{ return null; }
	public IMapObject getParentObject()		{ return null; }
	public String getUniqueName()			{ return getName(); }
	public boolean isSelected()				{ return false; }
	
//	public void setLoaded(boolean b)		{ bLoaded = b; repaint(); }





	public Element save(Element node)
	{
		Document doc = node.getOwnerDocument();

		if(null != srcTree)
		{
			Element srcElement = doc.createElement("src");
			srcElement.setTextContent(srcTree.getURI());
			srcElement.setAttribute("root", srcTree.getRootNode().getFullPath());
			node.appendChild(srcElement);
		}
		
		if(null != dstTree)
		{
			Element dstElement = doc.createElement("dst");
			dstElement.setTextContent(dstTree.getURI());
			dstElement.setAttribute("root", dstTree.getRootNode().getFullPath());
			node.appendChild(dstElement);
		}

		objects.save(node);
		
		return node;
	}
	
	public boolean loadNoClear(Element node)
	{
		bLoaded = false;


		Node srcNode = XPathUtils.getNode(node, "src");
		if(null != srcNode)
			loadTree( srcTree, (Element)srcNode );

		Node dstNode = XPathUtils.getNode(node, "dst");
		if(null != dstNode)
			loadTree( dstTree, (Element)dstNode );
		
		objects.load(node);
		
		bLoaded = true;

		return false;
	}

	public boolean load(Element node)
	{
		bLoaded = false;

		clear();

		Node srcNode = XPathUtils.getNode(node, "src");
		if(null != srcNode)
			loadTree( srcTree, (Element)srcNode );

		Node dstNode = XPathUtils.getNode(node, "dst");
		if(null != dstNode)
			loadTree( dstTree, (Element)dstNode );
		
		objects.load(node);
		
		bLoaded = true;
		
		return false;
	}

	private String getURL()
	{
		String s = uri.toString();
		File f1= new File(uri);
		if( !f1.isAbsolute()) {
			s = XMLMapperInit.getPathOnly(f1.getAbsolutePath()) + s;
		}
		
		return s;
	}

	private boolean loadTree(MapperTree tree, Element node)
	{
		String path = XPathUtils.getNodeText(node, "text()");
		if("" != path.trim())
		{
			path = getURL(); // Important for applet!
			System.out.print("Loading " + path + "\n");
			tree.load(path);

			try { Thread.sleep(500); }
			catch (InterruptedException e) { e.printStackTrace(); }
			
			String rootPath = XPathUtils.getNodeText(node, "@root/text()");
			if("" != rootPath.trim())
				tree.setRoot(rootPath);
			
			return true;
		}
		return false;
	}

	public void load(String uri)
	{
		uri = uri;
		Document doc = DOMUtils.loadDocument(uri);
		if(null != doc)
		{
			Node root = XPathUtils.getNode(doc, "jamper");
			load((Element)root);
		}
	}
	
	public void save(String uri)
	{
		Document doc = DOMUtils.newDocument();
		Element mapElement = doc.createElement("jamper");
		save(mapElement);
		doc.appendChild(mapElement);
		
		DOMUtils.renderXMLString(doc, new File(uri));
	}

	/*public void move(int x, int y)
	{
		Iterator iter = objects.iterator();
		while ( iter.hasNext() ) {
			IMapObject obj = (IMapObject)iter.next();
			Point pt = obj.getOrigin();
			obj.move(pt.x + x, pt.y + y);
		}
		repaint();
	}*/

	public void renderGlobalDeclarations(PathList context, Element parent)
	{
		Node firstChild = parent.getFirstChild();
		Document doc = parent.getOwnerDocument();
		Iterator iter = objects.iterator();
		while ( iter.hasNext() ) {
    		IMapObject obj = (IMapObject)iter.next();
    		Element decl = null;
    		if(obj instanceof VariableBox) {
    			VariableBox var = (VariableBox)obj;
    			if(var.isGlobal())
    				decl = var.renderDeclaration(context, doc);
    				//xslParent.appendChild(var.renderDeclaration(contextPath, doc));
			} else if(obj instanceof FunctionBox) {
    			FunctionBox func = (FunctionBox)obj;
    			//xslParent.appendChild(func.renderDeclaration(contextPath, doc));
    			if(func.isGlobal())
    				decl = func.renderDeclaration(context, doc);
			} else if(obj instanceof ExpressionBox) {
				ExpressionBox exp = (ExpressionBox)obj;
    			//xslParent.appendChild(exp.renderDeclaration(contextPath, doc));
				if(exp.isTemplate())
					decl = exp.renderDeclaration(context, doc);
			}
    		if(null != decl)
    		{
    			String name = decl.getAttribute("name");
    			//parent.appendChild(decl);
    			Element var = getVariableThatUses(name, parent);
    			if(null == var)
    				var = (Element)firstChild;
    			parent.insertBefore(decl, var);
    		}
    		decl = null;
    	}
	}
	
	private Element getVariableThatUses(String name, Element parent)
	{
		/*List variables = XPathUtils.getNodes(parent, "xsl:variable");
        if(null != variables)
        {
	        Iterator iter = variables.iterator();
			while ( iter.hasNext() ) {
	        	Element var = (Element)iter.next();
	        	String sel = var.getAttribute("select");
	        	if(sel.contains(name))
	        		return var;
			}
        }*/
        NodeList children = parent.getChildNodes();
        for(int i=0; i<children.getLength(); i++)
        {
        	Node node = children.item(i);
        	if(Node.ELEMENT_NODE == node.getNodeType())
        	{
	        	Element child = (Element)node;
	        	if(child instanceof Element)
	        	{
		        	String nodename = child.getNodeName();
		        	if(null != nodename && nodename.equals("xsl:variable"))
		        	{
		        		String sel = child.getAttribute("select");
			        	if(sel.contains(name))
			        		return child;
		        	}
	        	}
        	}
        }
		return null;
	}
	
	public void removeLinksConnectedTo(IMapObject obj)
	{
		objects.removeLinksConnectedTo(obj);
	}




	//////////////////////////////////////////////////////////////////////
	// MouseMotionListener interface implementation
	//////////////////////////////////////////////////////////////////////
	/*public void mouseDragged(MouseEvent e)
	{
		IMapObject obj = objects.get(e.getPoint());
		if(null != obj)
			return;
		objects.move(e.getX(), e.getY());
		panel.repaint();
	}
	public void mouseMoved(MouseEvent e)	{}*/
}
