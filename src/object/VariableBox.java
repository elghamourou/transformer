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


//import sqba.jamper.map.LinkLine;
import map.ILinkable;
import simpleMapper.PathList;
import utils.XPathUtils;



import map.IMapObject;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import map.Map;


/**
 * This class represents the variable container box.
 * It's value can be set manualy or can be a link to the
 * source node or a map object such as function output.
 *
 * @author Filip Pavlovic
 * @version 1.0
 */
public class VariableBox extends DragableMapObject
{
	private String		_value;
	private LinkPoint	_input			= new LinkPoint("input");
	private boolean		_bValueIsXPath; // Doesn't put single quotations
	private boolean		_bGlobal;


//	public JMenu getMenu() 		{ return new MyMenu(); }

	public VariableBox(Map map, Element node)
	{
		super(map, node);
		_linkPoints.add(new LinkPoint(true));		// Output
		//linkPoints.add(_input);	// Input

		addProperty("Value", _value);
	}

	public VariableBox(Map map, String name )
	{
		super(map, name);
		_value = "test_variable";
		_bValueIsXPath = false;
		_bGlobal = false;

		_linkPoints.add(new LinkPoint(true));		// Output
		_linkPoints.add(_input);	// Input


		addProperty("Value", _value);
	}
	

	public Element renderDeclaration(PathList context, Document doc)
	{
		ILinkable src = _input.getSource();
		String val = _bValueIsXPath ? _value : "'" + _value + "'";
		if(null != src)
			val = context.getRelativePath(src.getXPath());
		Element e = doc.createElement("xsl:variable");
		e.setAttribute("name", "var:" + _name);
		e.setAttribute("select", val);
		return e;
	}
	
	public Element render(PathList context, Element parent)
	{
		Document doc = parent.getOwnerDocument();
		
		if(!isGlobal())
			parent.appendChild(renderDeclaration(context, doc));

		Element newElement = doc.createElement("xsl:value-of");
		newElement.setAttribute("select", "$var:" + _name);
		parent.appendChild(newElement);
		return newElement;
	}
	
	public String getName()						{ return _name; }
	public String getXPath()					{ return "$var:" + _name; }
	public void removeChild(IMapObject param)	{}
	public String getValue()					{ return _value; }
	
	public String getDisplayName()
	{
		ILinkable src = _input.getSource();
		if(null != src)
			return _name;
		else
			return _value != "" ? _value : _name;
	}
	
	public void setValue(String value)		{ _value = value; }
	
	public boolean isGlobal()				{ return _bGlobal; }
	public void setGlobal(boolean bGlobal)	{ _bGlobal = bGlobal; }
	public boolean isXPath()				{ return _bValueIsXPath; }
	public void setXPath(boolean bIsXPath)	{ _bValueIsXPath = bIsXPath; }
//	public boolean rendersComplexNode()		{ return !_bGlobal; }
//	public boolean requiresEmbededDeclaration() { return !_bGlobal; }

	public Element save(Element node)
	{
		super.save(node);
		
		node.setAttribute("value", getValue());
		node.setAttribute("global", String.valueOf(isGlobal()));
		node.setAttribute("xpath", String.valueOf(isXPath()));
		
		/*String path = getFullPath();//obj.getName()
		if(null != path)
			node.setAttribute("path", path);*/
		
		return node;
	}
	
	public boolean load(Element node)
	{
		if(super.load(node))
		{
			setValue(XPathUtils.getNodeText(node, "@value/text()"));

			String tmp = XPathUtils.getNodeText(node, "@global/text()");
			if(null != tmp)
				setGlobal(Boolean.valueOf(tmp).booleanValue());

			tmp = XPathUtils.getNodeText(node, "@xpath/text()");
			if(null != tmp)
				setXPath(Boolean.valueOf(tmp).booleanValue());


			return true;
		}
		return false;
	}
}
