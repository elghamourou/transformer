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
import simpleMapper.MapperTreeNode;
import simpleMapper.PathList;
//import sqba.jamper.util.PathList;


import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;

import map.IMapObject;

//import org.w3c.dom.Document;
//import org.w3c.dom.Document;
import org.w3c.dom.Element;
//import javax.swing.JComponent;

//import java.io.*; // implements Serializable


/**
 * Default implementation of the Linkable interface.
 *
 * @author Filip Pavlovic
 * @version 1.0
 */
public class DefaultLinkable extends DefaultMapObject
implements ILinkable
{
	
	/** Collection of links to other linkables */
	protected ArrayList	_links	= new ArrayList();
	/** Object origin */


	
	public DefaultLinkable()				{}



	public String		getName()			{ return "DefaultLinkable"; }
//	public IMapObject 	getChildAt(Point pt){ return this; }
	
	public boolean		isLinked()			{ return !_links.isEmpty(); }
	public Collection	getLinks()			{ return _links; }
	public boolean		isOutput()			{ return false; }
	public String		getFullPath()		{ return this.toString(); }
	public String		getXPath()			{ return getFullPath(); }

	public ILinkable getSource()
	{
		Iterator iter = _links.iterator();
		while ( iter.hasNext() ) {
			LinkLine line = (LinkLine)iter.next();
			ILinkable src = line.getSrc();
			//Linkable dst = line.getDest();
			if(!src.equals(this))
				return src;
		}
		return null;
	}
	
	public LinkLine getLink()
	{
		Iterator iter = _links.iterator();
		while ( iter.hasNext() ) {
			LinkLine line = (LinkLine)iter.next();
			ILinkable src = line.getSrc();
			if(!src.equals(this))
				return line;
		}
		return null;
	}

	public boolean willAccept(ILinkable src) { return true; }


	

	

	


	
	
	/*public String render(PathList context, int level)
	{
		return context.getRelativePath(getFullPath());
	}*/
	
	public Element render(PathList context, Element parent)
	{
		return null;
	}
	
	public static LinkLine findLinkLine(ILinkable src, ILinkable dst)
	{
		Iterator iter = src.getLinks().iterator();
		while( iter.hasNext() )
		{
			LinkLine line = (LinkLine)iter.next();
			if(line.getSrc() == src)
				return line;
		}
		return null;
	}
	
	public Element renderDeclaration(PathList context, Element parent) { return null; }
	public void renderDeclarations(PathList context, Element parent) {}

	//	public boolean rendersComplexNode()		{ return false; }
//	public Element renderDeclaration(PathList context, Document doc) { return null; }
//	public boolean requiresEmbededDeclaration() { return false; }
}
