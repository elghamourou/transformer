package object;

import java.util.Iterator;

import map.ILinkable;
import map.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


import simpleMapper.PathList;


public class ChooseBox extends DragableMapObject
{
	
	public LinkPoint addParameter()
	{
		LinkPoint param = new LinkPoint("when");
		_linkPoints.add(param);
		WhenBox wb = _map.addNewWhenBox();
		_map.addLink(wb.getOutputLink(), param);
		return param;
	}

	public ChooseBox(Map map, Element node)
	{
		super(map, node);
		//setMenu( new MyMenu() );
		_linkPoints.add(new LinkPoint(true));		// Output
	}
	
	public ChooseBox(Map map)
	{
		super(map, "Choose");
		_linkPoints.add(new LinkPoint(true));		// Output
		//linkPoints.add(_input = new LinkPoint("input"));
	}

	/*public String render(PathList context, int level, String xslt)
	{
		if(null != _input.getSource())
		{
			//String srcPath = _input.getSource().getFullPath();
			
			xslt += "<xsl:choose>\n" +
					"<xsl:when test='$level=1'>\n" +
					"</xsl:choose>\n";
		}
		return "";
	}*/
	
	public Element render(PathList context, Element parent)
	{
		Document doc = parent.getOwnerDocument();

//		renderDeclarations(context, parent);

		Element chooseElement = doc.createElement("xsl:choose");
		parent.appendChild(chooseElement);

		Iterator iter = _linkPoints.iterator();
		while ( iter.hasNext() ) {
			LinkPoint link = (LinkPoint)iter.next();
			if(!link.isOutput())
			{
				ILinkable src = link.getSource();
				if(null != src)
				{
					Element whenElement = null;
					if(null != src.getParentObject() && 
							src.getParentObject() instanceof WhenBox)
					{
						WhenBox box = (WhenBox)src.getParentObject();
						box.render(context, chooseElement);
					}
					else
					{
						String srcPath = context.getRelativePath(src.getXPath());
						whenElement = doc.createElement("xsl:when");
						whenElement.setAttribute("test", srcPath);
						// Add output to the when element!
						chooseElement.appendChild(whenElement);
					}
				}
			}
		}
		return chooseElement;
	}
	
//	public String getTypeName() { return "Choose"; }
	
	public String getDisplayName()		{ return "Choose"; }
	
//	public boolean rendersComplexNode()	{ return true; }

	public Element save(Element node)
	{
		return super.save(node);

		/*Document doc = node.getOwnerDocument();
		Iterator itr = getChildObjects().iterator();
		while ( itr.hasNext() ) {
			LinkPoint link = (LinkPoint)itr.next();
			if(!link.isOutput())
			{
				Element paramElement = doc.createElement("param");
				paramElement.setAttribute("name", link.getName());
				node.appendChild(paramElement);
			}
		}*/

		/*String path = getFullPath();//obj.getName()
		if(null != path)
			node.setAttribute("path", path);*/
		
		//return node;
	}
	
	public boolean load(Element node)
	{
		return super.load(node);
	}
}
