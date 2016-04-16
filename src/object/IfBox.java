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


import org.w3c.dom.Element;
import org.w3c.dom.Document;

import map.ILinkable;
import simpleMapper.PathList;
import map.Map;


public class IfBox extends DragableMapObject
{
	private LinkPoint _input = null;
	private LinkPoint _condition = null;

	public IfBox(Map map, Element node)
	{
		super(map, node);

		//linkPoints.add(new LinkPoint(true));
		//linkPoints.add(_input = new LinkPoint("input"));
		//linkPoints.add(_condition = new LinkPoint("condition"));
	}

	public IfBox(Map map)
	{
		super(map, "IF");

		_linkPoints.add(new LinkPoint(true));
		_linkPoints.add(_input = new LinkPoint("input"));
		_linkPoints.add(_condition = new LinkPoint("condition"));
	}
	
	public Element render(PathList context, Element parent)
	{
		_input = (DragableMapObject.LinkPoint)super.getChild("input");
		_condition = (DragableMapObject.LinkPoint)super.getChild("condition");

		ILinkable srcCondition = _condition.getSource();
		ILinkable srcInput = _input.getSource();
		
		if(null == srcCondition || null == srcInput)
			return null;
		
		Document doc = parent.getOwnerDocument();
		
		LinkLine link = _input.getLink();
		
		Element parentElement = parent;
		Element ifElement = null;
		
		ifElement = doc.createElement("xsl:if");

		/*if(srcCondition instanceof LinkPoint && 
				srcCondition.getParentObject() instanceof IfBox)
		{
			IfBox ifbox = (IfBox)srcCondition.getParentObject();
			parent = ifbox.render(context, parent);
		}*/

		String testPath = srcCondition.getXPath();
		testPath = context.getRelativePath(testPath);
		ifElement.setAttribute("test", testPath);

		if(link.getType() == LinkLine.XSL_FOR_EACH)
		{
			Element pparent = (Element)parent.getParentNode();
			if(null != pparent) {
				Element outNode = link.render(context, pparent);
				outNode.appendChild(ifElement);
				ifElement.appendChild(parent);
				parentElement = outNode;
			} else {
				// The container component is rendered as a template
				// That cannot work correctly.
				link.render(context, ifElement);
				parent.appendChild(ifElement);
			}
		}
		else
		{
			link.render(context, ifElement);
			parent.appendChild(ifElement);
		}
		
		renderVariableDeclaration(context, srcCondition,
				ifElement, parentElement);

		renderVariableDeclaration(context, srcInput,
									ifElement, parentElement);

		return parent;
	}
	
	/** If the source is a variable and the variable is not
	 * declared as global, then the variable should be declared
	 * This should be done nicer (in some other place).
	 */
	private void renderVariableDeclaration(PathList context,
			ILinkable srcCondition, Element ifElement, Element parent)
	{
		Document doc = parent.getOwnerDocument();
		Element decl = null;
		VariableBox var = null;
		if(srcCondition instanceof VariableBox)
			var = (VariableBox)srcCondition;
		else if(srcCondition instanceof LinkPoint)
		{
			LinkPoint lp = (LinkPoint)srcCondition;
			if(lp.getParentObject() instanceof VariableBox)
				var = (VariableBox)lp.getParentObject();
		}
		if(null != var && !var.isGlobal())
		{
			decl = var.renderDeclaration(context, doc);
			parent.insertBefore(decl, ifElement);
		}
		/////////////////////////////////////////////////////////////
		/*if(srcCondition.requiresEmbededDeclaration())
		{
			Element decl = srcCondition.renderDeclaration(context, doc);
			parentElement.insertBefore(decl, ifElement);
		}*/
	}
	
	public ILinkable getSource()
	{
		return _input.getSource();
	}
	
//	public String getTypeName() { return "If"; }
	public String getDisplayName() { return "IF"; }
	
//	public boolean rendersComplexNode()		{ return true; }

	public Element save(Element node)
	{
		return super.save(node);
	}
	
	public boolean load(Element node)
	{
		if(super.load(node))
		{
			_input = (DragableMapObject.LinkPoint)super.getChild("input");
			_condition = (DragableMapObject.LinkPoint)super.getChild("condition");
			return true;
		}
		return false;
	}
}
