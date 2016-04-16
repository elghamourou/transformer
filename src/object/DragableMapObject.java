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


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import map.ILinkable;
import map.IMapObject;
import map.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import simpleMapper.PathList;
import utils.DOMUtils;
import utils.XPathUtils;


/**
 * This class represents a map object that can be repositioned
 * on the map view, such as a function box or a variable.
 *
 * @author Filip Pavlovic
 * @version 1.0
 */
public class DragableMapObject extends DefaultMapObject
{
	protected Map			_map		= null;
	protected String		_name		= "DragableMapObject";
	protected ArrayList		_linkPoints	= new ArrayList();

	private LinkPoint	_outputLink		= new LinkPoint(true);
	
	public static final String PATH_SEPARATOR	= "/";
	
	private boolean		_bGlobal		= true;

	/**
	 * Representation of the function parameter
	 * or the function output (_bOutput=true).
	 *
	 * @author Filip Pavlovic
	 * @version 1.0
	 */
	public class LinkPoint extends DefaultLinkable
	{
		private boolean	_bOutput = false;
		private int		_index = 0;
		private int		_x1 = 0, _y1 = 0, _x2 = 0, _y2 = 0;
		private String	_name = "param";

	    public LinkPoint() { this(false); }
		
		public LinkPoint(boolean bOutput) { this("param", bOutput); }

		public LinkPoint(String name, boolean bOutput)
		{
			name = name.replaceAll(" ", "_");
			if(!bOutput) {
				_name = name;
				_index = getLinkCount() + 1;
				if(getLinkCount(name) > 0)
					_name += _index-1;
			} else {
				_index = 0;
				_name = "output";
			}

			_bOutput = bOutput;
			

			addProperty("Name", _name);
			//addProperty("Index", _index);
			//addProperty("Output", _bOutput);
		}

		public LinkPoint(String name)
		{
			this(name, false);
			/*_name = name.replaceAll(" ", "_");
			for(int i=0; i<getPropertyCount(); i++)
			{
				if(getProperty(i).getName() == "Name")
				{
					removeProperty( getProperty(i) );
					addProperty("Name", _name);
					break;
				}
			}*/
		}

		public void setIndex(int index)
		{
			if(!_bOutput) {
				_index = index;
				_name = "param" + _index;
			}
		}

		public String toString() { return _name; }

		public String getFullPath()
		{
			return DragableMapObject.this.getFullPath() + DragableMapObject.PATH_SEPARATOR + _name;
		}
		
		public String getUniqueName() { return getName(); }
		
		public String getXPath()
		{
			return DragableMapObject.this.getXPath();
		}

		/*public void removeLinks()
		{
			for(int i=0; i<_links.size(); i++)
			{
				LinkLine line = (LinkLine)_links.get(i);
				_links.remove(line);
			}
		}*/

		public String		getDisplayName()	{ return (_bOutput ? "Function output" : "Function parameter"); }
		public String		getName()			{ return (_bOutput ? "output" : _name); }
		public IMapObject 	getParentObject()	{return DragableMapObject.this; }
		public void setName(String name)
		{
			if(!_bOutput && null != name && name.trim() != "") {
				_name = name;
				//_mapObjView.repaint();
			}
		}

		//////////////////////////////////////////////////////////////////
		// DefaultLinkable overrides
		//////////////////////////////////////////////////////////////////
		public boolean	isOutput()				{ return _bOutput; }
//		public Point	getOrigin()				{ return new Point(_x1, _y1); }
		public boolean willAccept(ILinkable src) { return (_bOutput ? true : !isLinked()); }
//		public boolean rendersComplexNode()		{ return DragableMapObject.this.rendersComplexNode(); }
//		public Element renderDeclaration(PathList context, Document doc) { return DragableMapObject.this.renderDeclaration(context, doc); }
//		public Element render(PathList context, Element parent) { return DragableMapObject.this.render(context, parent); }
//		public boolean requiresEmbededDeclaration() { return DragableMapObject.this.requiresEmbededDeclaration(); }
		
		/*public void select( boolean sel )
		{
			super.select(sel);
			if(sel) {
				DragableMapObject.this.select(true);
				DragableMapObject.this.selectLinks(this);
			}
		}
		
		private void select()
		{
			super.select(true);
		}*/
	}

	public DragableMapObject(Map map, Element node)
	{
		_map = map;
		_linkPoints.add(_outputLink); // Output
		load(node);
	}

	public DragableMapObject(Map map, String name)
	{
		_map = map;
		_name = name.replaceAll(" ", "_");
		addProperty("Name", _name);
		_linkPoints.add(_outputLink); // Output
	}
	

	private int getLinkCount(String name)
	{
		int result = 0;
		Collection children = getChildObjects();
		if(null != children) {
			Iterator iter = children.iterator();
			while ( iter.hasNext() ) {
				LinkPoint link = (LinkPoint)iter.next();
				if(!link.isOutput() && name.equalsIgnoreCase(link.getName()))
					result++;
			}
		}
		return result;
	}
	
	private int getLinkCount()
	{
		int result = 0;
		Collection children = getChildObjects();
		if(null != children) {
			Iterator iter = children.iterator();
			while ( iter.hasNext() ) {
				LinkPoint link = (LinkPoint)iter.next();
				if(!link.isOutput())
					result++;
			}
		}
		return result;
	}

	/**
	 * Returns object name.
	 *
	 * @return object name
	 */
	public String toString()
	{
		return _name;
	}
	
	




	//////////////////////////////////////////////////////////////////////
	// MapObject interface implementation
	//////////////////////////////////////////////////////////////////////
	

	public String getName ()			{ return _name; }
	
	//public String getUniqueName()		{ return _name + "[" + String.valueOf(getID()) + "]"; }
	
	public String getFullPath()
	{
		//return _name;// + "_" + String.valueOf(hashCode());
		return getUniqueName();
		//return _name + "[" + String.valueOf(getID()) + "]";
	}
	
	/*public void setFullPath(String path)
	{
		_name.
	}*/
	
	public String getXPath()
	{
		return _name;
	}
	
	public void setName(String name)
	{
		/*int n = name.lastIndexOf("_");
		if(name.length()-n == String.valueOf(hashCode()).length())
			_name = name.substring(0, n);
		else
			_name = name;*/
		if(name!=null && name.length()>0)
		{
			_name = name;
		}
	}

	public Collection getChildObjects() { return _linkPoints; }

//	public IMapObject getChildAt(Point pt)
//	{
//		Iterator iter = _linkPoints.iterator();
//		while ( iter.hasNext() ) {
//			LinkPoint link = (LinkPoint)iter.next();
//			if(link.hitTest(pt))
//				return link;
//		}
//		if(this.hitTest(pt))
//			return this;
//		return null;
//	}
	
	public IMapObject getChild(String uniqueName)
	{
		Iterator iter = _linkPoints.iterator();
		while ( iter.hasNext() ) {
			IMapObject obj = (IMapObject)iter.next();
			//String tmp = obj.getName();
			if(uniqueName.equals(obj.getUniqueName()))
				return obj;
		}
		return null;
	}

	public void removeChild(IMapObject obj)
	{
		LinkPoint lp = (LinkPoint)obj;
		if(!lp.isOutput())
			_linkPoints.remove(obj);
	}
	
	/*public void removeLinks()
	{
		Iterator iter = _linkPoints.iterator();
		while ( iter.hasNext() ) {
			LinkPoint link = (LinkPoint)iter.next();
			link.removeLinks();
			//for(int i=0; i<link._links.size(); i++)
			//{
			//	LinkLine line = (LinkLine)link._links.get(i);
			//	link._links.remove(line);
			//}
		}
	}*/
	
	/*public void save(Element parent)
	{
		Document doc = parent.getOwnerDocument();
		Element objectElement = doc.createElement("object");
		//objectElement.setAttribute("type", getTypeStr());
		parent.appendChild(objectElement);
		
		// Save links
		Iterator iter = _linkPoints.iterator();
		while ( iter.hasNext() ) {
			LinkPoint link = (LinkPoint)iter.next();
			Linkable src = link.getSource();
			LinkLine line = link.getLink();
			Element linkElement = doc.createElement("link");
			if(null != src)
				linkElement.setAttribute("src", src.getFullPath());
			objectElement.appendChild(linkElement);
		}
	}*/

	//////////////////////////////////////////////////////////////////////
	// DefaultLinkable overrides
	//////////////////////////////////////////////////////////////////////

//	public boolean rendersComplexNode()		{ return false; }
//	public Element renderDeclaration(PathList context, Document doc) { return null; }
//	public boolean requiresEmbededDeclaration() { return false; }
//	public Element render(PathList context, Element parent) { return null; }
	
	public Element save(Element node)
	{
		super.save(node);
		
		node.setAttribute("name", getName());
		node.setAttribute("global", String.valueOf(isGlobal()));


		// Save input parameters
		Document doc = node.getOwnerDocument();
		Iterator itr = _linkPoints.iterator();
		while ( itr.hasNext() ) {
			LinkPoint link = (LinkPoint)itr.next();
			if(!link.isOutput())
			{
				Element paramElement = doc.createElement("param");
				paramElement.setAttribute("name", link.getName());
				node.appendChild(paramElement);
			}
		}
		
		return node;
	}
	
	public boolean load(Element node)
	{
		if(!super.load(node))
			return false;
		
		_name = XPathUtils.getNodeText(node, "@name/text()");
		
		//String tmp = XPathUtils.getNodeText(node, "@ID/text()");
		//if(null != tmp)
		//	setID(Integer.valueOf(tmp).intValue());

		String tmp = XPathUtils.getNodeText(node, "@x/text()");
		int x = tmp != "" ? Integer.valueOf(tmp).intValue() : -1;
		
		tmp = XPathUtils.getNodeText(node, "@y/text()");
		int y = tmp != "" ? Integer.valueOf(tmp).intValue() : -1;

		tmp = XPathUtils.getNodeText(node, "@global/text()");
		if(null != tmp)
			setGlobal(Boolean.valueOf(tmp).booleanValue());
		
		if(x > 0 && y > 0)
		{
	        List results = XPathUtils.getNodes(node, "param");
	        if(null != results)
	        {
		        Iterator resultIter = results.iterator();
				while ( resultIter.hasNext() ) {
		        	Node child = (Node)resultIter.next();
		        	String name = XPathUtils.getNodeText(child, "@name/text()");
		    		LinkPoint param = new LinkPoint(name);
		    		_linkPoints.add(param);
				}
	        }
			return true;
		}

		return false;
	}
	
	
	public LinkPoint getOutputLink()	{ return _outputLink; }

	/*public void renderDeclarations(PathList context, Element parent)
	{
		Iterator itr = _linkPoints.iterator();
		while ( itr.hasNext() ) {
			LinkPoint link = (LinkPoint)itr.next();
			if(!link.isOutput())
			{
				ILinkable src = link.getSource();
				if(null != src)
				{
					if(src instanceof LinkPoint)
					{
						IMapObject obj = src.getParentObject();
						Element var = obj.renderDeclaration(context, parent);
						if(null != var)
							parent.appendChild(var);
						obj.renderDeclarations(context, parent);
					}
					else
						src.renderDeclarations(context, parent);
				}
			}
		}
	}*/
	
	protected void clearInputs()
	{
		for(int i=0; i<_linkPoints.size(); i++)
		{
			LinkPoint tmp = (LinkPoint)_linkPoints.get(i);
			if(!tmp.isOutput())
			{
				_map.removeLinksConnectedTo(tmp);
				_linkPoints.remove(tmp);
				// Remove lines!
				i--;
			}
		}
	}
	
	protected void removeInput(LinkPoint input)
	{
		Iterator itr = _linkPoints.iterator();
		while ( itr.hasNext() ) {
			LinkPoint link = (LinkPoint)itr.next();
			if(!link.isOutput() && link.equals(input))
			{
				_linkPoints.remove(link);
				_map.removeLinksConnectedTo(link);
				break;
			}
		}
	}

    /**
     * Returns number of inputs (parameters).
	 * @return number of inputs (parameters)
     */
	public int getInputCount()
	{
		int count = 0;
		Iterator iter = _linkPoints.iterator();
		while ( iter.hasNext() ) {
			LinkPoint link = (LinkPoint)iter.next();
			if(!link.isOutput())
				count++;
		}
		return count;
	}

	protected LinkPoint getInput(String name)
	{
		Iterator itr = _linkPoints.iterator();
		while ( itr.hasNext() ) {
			LinkPoint link = (LinkPoint)itr.next();
			if(!link.isOutput() && name.equalsIgnoreCase(link.getName()))
				return link;
		}
		return null;
	}

	/**
	 * Renders the object, puts it's result in a variable
	 * and returns variable name.
	 */
	public String renderAsVariable(PathList context, Element parent)
	{
		String name = "ResultOf" + _name;
		Document doc = parent.getOwnerDocument();
		Element e = doc.createElement("xsl:variable");
		e.setAttribute("name", "var:" + name);
		parent.appendChild(e);
		boolean bStringValue = false;
		if(bStringValue) {
			String val = "";
			e.setAttribute("select", val);
		} else
			render(context, e);
		return name;
	}
	
	public boolean isGlobal()				{ return _bGlobal; }
	public void setGlobal(boolean bGlobal)	{ _bGlobal = bGlobal; }

	/*public void select( boolean sel )
	{
		super.select(sel);
		selectLinks(null);
	}

	private void selectLinks( LinkPoint caller )
	{
		Iterator itr = _linkPoints.iterator();
		while ( itr.hasNext() ) {
			LinkPoint link = (LinkPoint)itr.next();
			if(caller != link)
				link.select();
		}
	}*/
	
	public IMapObject copy()
	{
		Document doc = DOMUtils.newDocument();
		Element element = doc.createElement("copy");
		save(element);
//		try {
			IMapObject copy = new DragableMapObject(_map, element);
			//copy.load(element);
			return copy;
//		} catch(CloneNotSupportedException e) {
//		}
//		return null;
	}
}
