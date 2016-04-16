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

import simpleMapper.ObjectProperty;



//import java.io.*; // extends Serializable
//import java.io.PrintWriter;


/**
 * Root interface for all mapper objects:
 * tree nodes, link lines, function parameters, function output, etc.
 * Provides main property functionality.
 *
 * @author	Filip Pavlovic
 * @version	1.0
 */
public interface IPropObject
{
	/**
	 * Returns the number of this object's properties.
	 *
	 * @return int, number of this object's properties
	 */
	public int getPropertyCount();

	/**
	 * Adds new property to this object's properties.
	 *
	 * @param name String, property name.
	 * @param value Object, property value.
	 */
	public void addProperty(String name, Object value);

	/**
	 * Removes the property from the list given it's index.
	 *
	 * @param index property's index
	 */
	public void removeProperty(int index);

	/**
	 * Removes the property from the list.
	 *
	 * @param prop reference to the ObjectProperty object
	 */
	public void removeProperty(ObjectProperty prop);

	/**
	 * Returns the reference to a ObjectProperty object.
	 *
	 * @param index int, property index.
	 *
	 * @return reference to a ObjectProperty object
	 */
	public ObjectProperty getProperty(int index);

	//public ObjectProperty getProperty(String name);

	/**
	 * Callback function
	 * Called when a property has been updated.
	 *
	 * @param prop Property that has been updated
	 */
	public void propertyUpdated(ObjectProperty prop);


	//abstract String getXML();
	//abstract void writeXML(PrintWriter out);

	//public void writeObject(java.io.ObjectOutputStream out)
	//public void readObject(java.io.ObjectInputStream in)
}
