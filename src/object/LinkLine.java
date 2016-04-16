/*********************************************************************\
*                                                                     *
*         Jamper - Java XML mapper (visual XSLT generator)            *
*                Copyright (C) 2005 Filip Pavlovic                    *
*                    sqba@users.sourceforge.net                       *
*                                                                     *
*  This program is free software; you can redistribute it and/or      *
*  modify it under the terms of the GNU General Public License        *
*  as published by the Free Software Foundation; either version 2     *
*  of the License, or (at your option) any later version.             *
*                                                                     *
*  This program is distributed in the hope that it will be useful,    *
*  but WITHOUT ANY WARRANTY; without even the implied warranty of     *
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the      *
*  GNU General Public License for more details.                       *
*                                                                     *
*  You should have received a copy of the GNU General Public License  *
*  along with this program; if not, write to the Free Software        *
*  Foundation, Inc., 59 Temple Place - Suite 330, Boston,             *
*  MA 02111-1307, USA.                                                *
*                                                                     *
\*********************************************************************/


package object;


import map.ILinkable;
import object.DragableMapObject.LinkPoint;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import simpleMapper.MapperTreeNode;
import simpleMapper.PathList;
import utils.XPathUtils;


/**
 * Object representing a line between two linkable objects.
 * A linkable object may be either tree node or function box.
 *
 * @author	Filip Pavlovic
 * @version	1.0
 */
public class LinkLine extends DefaultMapObject
{
    public static final int XSL_FOR_EACH	= 0;
    public static final int XSL_VALUE_OF	= 1;
    public static final int XSL_TEMPLATE	= 2;

    private int			_lineType = XSL_VALUE_OF;
	private ILinkable	_src = null, _dst = null;
	/** Coordinates */
	private int			_x1	= 0, _y1 = 0, _x2 = 0, _y2 = 0;
	


	public LinkLine(ILinkable src, ILinkable dst)
	{

		if( ((src instanceof MapperTreeNode) && src.isOutput()) ||
				((dst instanceof MapperTreeNode) && !dst.isOutput()) ) {
			ILinkable tmp = src;
			src = dst;
			dst = tmp;
		}
		else if(!(src instanceof MapperTreeNode) && !(dst instanceof MapperTreeNode))
		{
			// Now output means something else!!!
			if( !src.isOutput() || dst.isOutput()) {
				ILinkable tmp = src;
				src = dst;
				dst = tmp;
			}
		}
		
		_src = src;
		_dst = dst;

		this.addProperty("Source", _src);
		this.addProperty("Destination", _dst);
		//this.addProperty("Type", _lineType);
	}

	/**
	 * Returns the source LinkLine object.
	 *
	 * @return source LinkLine object
	 */
	public ILinkable getSrc()	{ return _src; }
	/*{
		Linkable parent = (Linkable)_src.getParentObject();
		return (parent!=null ? parent : _src);
	}*/

	/**
	 * Returns the destination LinkLine object.
	 *
	 * @return destination LinkLine object
	 */
	public ILinkable getDest()	{ return _dst; }
	/*{
		Linkable parent = (Linkable)_dst.getParentObject();
		return (parent!=null ? parent : _dst);
	}*/

	public String toString()
	{
		return _src.getFullPath() + " -> " + _dst.getFullPath();
		/*if(_src.getParentObject() != null)
			return _src.getParentObject() + " -> " + _dst.getFullPath();
		else if(_dst.getParentObject() != null)
			return _src.getFullPath() + " -> " + _dst.getParentObject() + "." + _dst;
		else
			return _src.getFullPath() + " -> " + _dst.getFullPath();*/
		//return getSrc().getFullPath() + " -> " + getDest().getFullPath();
	}

	//////////////////////////////////////////////////////////////////////
	// DefaultMapObject overrides
	//////////////////////////////////////////////////////////////////////
	public void select(boolean sel)
	{
		super.select(sel);
		if(!_src.isSelected())
			_src.select(sel);
		if(!_dst.isSelected())
			_dst.select(sel);
	}



	public String	getName()	{ return "LinkLine"; }

	/*public String getXML()
	{
		String result = "<" + getName() + ">\n";
		result += "<point1 x='" + _x1 + "' y='" + _y1 + "'/>\n";
		result += "<point2 x='" + _x2 + "' y='" + _y2 + "'/>\n";
		result += "<source>" + _src.getFullPath() + "</source>\n";
		result += "<destination>" + _dst.getFullPath() + "</destination>\n";
		result += "</" + getName() + ">\n";
		return result;
	}*/

	//public void setType(int linkType) { _lineType = linkType; }
	public int getType() { return _lineType; }

	public Element render(PathList context, Element parent)
	{
		Document doc = parent.getOwnerDocument();

		if((_src instanceof LinkPoint) && 
				(_src.getParentObject() instanceof IfBox))
		{
			IfBox ifbox = (IfBox)_src.getParentObject();
			Element newElement = ifbox.render(context, parent);
			return newElement;
		}
		else if((_src instanceof LinkPoint) && 
				(_src.getParentObject() instanceof ChooseBox))
		{
			// Ovde treba izrenderovati variable
			ChooseBox box = (ChooseBox)_src.getParentObject();
			Element newElement = box.render(context, parent);
			return newElement;
		}
		/*else if((_src instanceof LinkPoint) && 
				(_src.getParentObject() instanceof FunctionBox))
		{
			// Ovde treba izrenderovati variable
			FunctionBox box = (FunctionBox)_src.getParentObject();
			Element newElement = box.render(context, parent);
			return newElement;
		}*/
		else
		{
			boolean bNewContext = false;
			String xslcmd = getXSLName();
			String srcPath = context.getRelativePath(_src.getXPath());
			Element newElement = doc.createElement(xslcmd);
			newElement.setAttribute("select", srcPath);
			parent.appendChild(newElement);
			bNewContext = (getType() == LinkLine.XSL_FOR_EACH);
			if(bNewContext)
				context.set(_src.getXPath());
			return bNewContext ? newElement : parent;
		}
	}

	/*public Element render(PathList context, Element parent)
	{
		Document doc = parent.getOwnerDocument();
		if(_src instanceof LinkPoint) {
			IMapObject obj = _src.getParentObject();
			Element newElement = obj.render(context, parent);
			return newElement;
		} else {
			boolean bNewContext = false;
			String xslcmd = getXSLName();
			String srcPath = context.getRelativePath(_src.getXPath());
			Element newElement = doc.createElement(xslcmd);
			newElement.setAttribute("select", srcPath);
			parent.appendChild(newElement);
			bNewContext = (getType() == LinkLine.XSL_FOR_EACH);
			if(bNewContext)
				context.set(_src.getXPath());
			return bNewContext ? newElement : parent;
		}
	}*/

//	public String getTypeName() { return "Link"; }
	
	private String getXSLName()
	{
		String xslcmd = "";
		switch( getType() )
		{
		case LinkLine.XSL_FOR_EACH:
			xslcmd = "xsl:for-each";
			break;
		case LinkLine.XSL_TEMPLATE:
			xslcmd = "xsl:template";
			break;
		case LinkLine.XSL_VALUE_OF:
			xslcmd = "xsl:value-of";
			break;
		}
		return xslcmd;
	}

	public Element save(Element node)
	{
		ILinkable src = this.getSrc();
		ILinkable dst = this.getDest();
		if((null != src) && (null != dst))
		{
			node.setAttribute("src", src.getFullPath());
			node.setAttribute("dst", dst.getFullPath());
			node.setAttribute("xsl-type", getXSLName());
		}
		return node;
	}
	
	public boolean load(Element node)
	{
		Node tmp = XPathUtils.getNode(node, "@xsl-type/text()");
		if(null != tmp)
		{
			String str = tmp.getNodeValue().trim();
			if(str.equals("xsl:for-each"))
				_lineType = LinkLine.XSL_FOR_EACH;
			else if(str.equals("xsl:template"))
				_lineType = LinkLine.XSL_TEMPLATE;
			else if(str.equals("xsl:value-of"))
				_lineType = LinkLine.XSL_VALUE_OF;
		}
		return true;
	}
}
