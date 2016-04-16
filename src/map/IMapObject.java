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


package map;


import java.util.Collection;

import org.w3c.dom.Element;

import simpleMapper.PathList;


/**
 * Interface for all mapper objects:
 * tree nodes, link lines, function parameters, function output, etc.
 *
 * @author	Filip Pavlovic
 * @version	1.0
 */
public interface IMapObject extends IPropObject
{
	/** Maximum distance in pixels (for hitTest()). */
	public static final double	_max_dist = 2.0;

	/**
	 * Returns true if the object is selected.
	 *
	 * @return true if the object is selected.
	 */
	abstract boolean isSelected();

	/**
	 * Selects or deselects object.
	 *
	 * @param sel if true then the object is selected.
	 */
	abstract void select( boolean sel );


	abstract String getName();

	
	abstract IMapObject getParentObject();

	
	abstract Collection getChildObjects();
	
	abstract IMapObject getChild(String name);

	
	
	
	abstract void removeChild(IMapObject obj);
	
	
	abstract String getDisplayName();
	
	
	
	abstract void setName(String name);
	
	abstract String getFullPath();

	abstract int getID();
	abstract void setID(int ID);
	
	abstract String getUniqueName();


	abstract Element save(Element node);
	abstract boolean load(Element node);
	
	abstract Element render(PathList context, Element parent);
	
	
	abstract Element renderDeclaration(PathList context, Element parent);
	
	abstract void move(int x, int y);
	
	abstract IMapObject copy();
	
	
	
	
	
	
	
//	abstract IMapObject getChildAt(Point pt);
//	
//	abstract Point getOrigin();
	
	
	
	
}
