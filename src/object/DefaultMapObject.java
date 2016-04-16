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


import map.IMapObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import simpleMapper.ObjectProperty;
import utils.DOMUtils;
import simpleMapper.PathList;
import utils.XPathUtils;


/**
 * Default implementation of the MapObject interface.
 *
 * @author Filip Pavlovic
 * @version 1.0
 */
public class DefaultMapObject extends Object implements IMapObject
{
	/** Is the object currently selected? */
	protected boolean	_selected	= false;

	protected ArrayList	_properties	= new ArrayList();
	protected int		_ID = this.hashCode();

	//////////////////////////////////////////////////////////////////////
	// MapObject interface implementation
	//////////////////////////////////////////////////////////////////////
	public boolean		isSelected()				{ return _selected; }
	public void			select( boolean sel )		{ _selected = sel; }
	public String		getName()					{ return "DefaultMapObject"; }
	public void			setName(String name)		{}
	public IMapObject	getParentObject()			{ return null; }
	public Collection	getChildObjects()			{ return null; }
	public void			removeChild(IMapObject obj)	{ }
//	public IMapObject	getChildAt(Point pt)		{ return null; }
	public String		getDisplayName()			{ return getName(); }
	public IMapObject	getChild(String name)		{ return null; }
//	public Point		getOrigin()					{ return null; }
	public String		getFullPath()				{ return null; }
	public int			getID()						{ return _ID; } 
	public void			setID(int ID)				{ _ID = ID; } 

	//////////////////////////////////////////////////////////////////////
	// PropObject interface implementation
	//////////////////////////////////////////////////////////////////////
	public int getPropertyCount()			{ return _properties.size(); }
	public void addProperty(String name, Object value)
		{ _properties.add(new ObjectProperty(this, name, value)); }
	public void removeProperty(int index)	{ _properties.remove(index); }
	public void removeProperty(ObjectProperty prop)
		{ _properties.remove(prop); }
	public ObjectProperty getProperty(int index)
		{ return (ObjectProperty)_properties.get(index); }
	//public ObjectProperty getProperty(String name);
	public void propertyUpdated(ObjectProperty prop)
		{ System.out.println("propertyUpdated(" + prop + ")"); }


	//private void writeObject(java.io.ObjectOutputStream out) {}
	//private void readObject(java.io.ObjectInputStream in) {}
	
	public String getUniqueName()
	{
		return getName() + "[" + String.valueOf(_ID) + "]";
	}

	public Element save(Element node)
	{
		// Save object unique ID
		node.setAttribute("ID", String.valueOf(this.getID()));

//		String path = getFullPath();//obj.getName()
//		if(null != path)
//			node.setAttribute("path", path);

		return node;
	}
	
	public boolean load(Element node)
	{
		String tmp = XPathUtils.getNodeText(node, "@ID/text()");
		if(tmp.trim() == "")
			return false;
		_ID = Integer.valueOf(tmp).intValue();
		return true;
	}
	
	public Element render(PathList context, Element parent) { return null; }
	public Element renderDeclaration(PathList context, Element parent) { return null; }
	
	public void move(int x, int y) {}
	
	public IMapObject copy()
	{
		Document doc = DOMUtils.newDocument();
		Element element = doc.createElement("copy");
		save(element);
		try {
			IMapObject copy = (IMapObject)this.clone();
			copy.load(element);
			return copy;
		} catch(CloneNotSupportedException e) {
		}
		return null;
	}
}
