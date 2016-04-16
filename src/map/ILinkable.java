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


//import org.w3c.dom.Document;
//import org.w3c.dom.Document;
import org.w3c.dom.Element;

import simpleMapper.PathList;

public interface ILinkable extends IMapObject
{
	abstract Collection getLinks();

	abstract boolean isOutput();

	abstract String getFullPath();
	
	abstract String getXPath();

	abstract ILinkable getSource();

	abstract boolean willAccept(ILinkable src);
	
	
	abstract Element render(PathList context, Element parent);
	
	abstract Element renderDeclaration(PathList context, Element parent);
	
	
	
//	abstract Point getOrigin();
}
