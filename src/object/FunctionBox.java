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


import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.ArrayList;



import map.IMapObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import map.ILinkable;
import map.Map;
import simpleMapper.PathList;
import utils.ListDialog;

/**
 * This class represents the function box.
 * It can be linked to a tree node or another
 * function box. It has input and output links.
 *
 * @author Filip Pavlovic
 * @version 1.0
 */
public class FunctionBox extends DragableMapObject
{
	private String _body;
    private String[] functions = {	"%param1 = %param2",
    								"%param1 != %param2",
									"%param = ''",
									"%param != ''"};
    private String[] names = {	"Equals",
								"NotEquals",
								"IsEmpty",
								"NotEmpty"};

	public FunctionBox(Map map, Element node)
	{
		super(map, node);
	}
	
	public FunctionBox(Map map, String name)
	{
		super(map, name);
		_name = "equals";
		setFunctionBody("%param1 = %param2");
	}

	/**
	 * Creates a new parameter and adds it to the list.
	 *
	 * @return pointer to parameter's LinkPoint interface.
	 */
	public LinkPoint addParameter(String name)
	{
		LinkPoint param = new LinkPoint(name);
		_linkPoints.add(param);
		return param;
	}

	public void removeChild(IMapObject param)
	{
		if(!(param instanceof LinkPoint))
			return;

		LinkPoint lp = (LinkPoint)param;
		if(lp.isOutput())
			return;

		_linkPoints.remove(param);

		int index = 1;
		Iterator iter = _linkPoints.iterator();
		while ( iter.hasNext() ) {
			LinkPoint link = (LinkPoint)iter.next();
			if(!link.isOutput())
				link.setIndex(index++);
		}
		
		_map.removeLinksConnectedTo(param);

	}

	public Element renderDeclaration(PathList context, Document doc)
	{
		Element e = doc.createElement("xsl:variable");
		e.setAttribute("name", "var:ResultOf" + getVarName());

		String select = _body;
		Iterator itr = _linkPoints.iterator();
		while ( itr.hasNext() ) {
			LinkPoint link = (LinkPoint)itr.next();
			if(!link.isOutput()) {
				ILinkable src = link.getSource();
				if(null != src) {
					if(src instanceof VariableBox) {
						VariableBox var = (VariableBox)src;
						if(!var.isGlobal())
							e.appendChild(var.renderDeclaration(context, doc));
					} else if(src instanceof FunctionBox) {
						FunctionBox func = (FunctionBox)src;
						if(!func.isGlobal())
							e.appendChild(func.renderDeclaration(context, doc));
					}
					String paramName = "%" + link.getName();
					String paramValue = src.getXPath();
					paramValue = context.getRelativePath(paramValue);
					select = select.replace(paramName, paramValue);
				}
			}
		}

		e.setAttribute("select", select);
		return e;
	}
	
	public Element render(PathList context, Element parent)
	{
		Document doc = parent.getOwnerDocument();
		
		if(!isGlobal()) {
			Element decl = renderDeclaration(context, doc);
			parent.appendChild(decl);
		}

		Element newElement = doc.createElement("xsl:value-of");
		newElement.setAttribute("select", "$var:ResultOf" + getVarName());
		parent.appendChild(newElement);
		return newElement;
	}

	public Element save(Element node)
	{
		super.save(node);
		Document doc = node.getOwnerDocument();
		Text body = doc.createTextNode("body");
		body.setNodeValue(_body);
		node.appendChild(body);
		return node;
	}
	
	public boolean load(Element node)
	{
		if(super.load(node))
		{
			String body = node.getTextContent();
			if(null != body) {
				_body = body.replace("\n", "");
				return true;
			}
		}
		return false;
	}
	
	private void setFunctionBody(String body)
	{
		if(null == body)
			return;
		if(null == _body || !_body.trim().equalsIgnoreCase(body.trim()))
		{
			_body = body;
			ArrayList params = loadParams(_body);
			removeOldParams(params);
			addNewParameters(params);
		}
	}
	
	/**
	 * Removes parameters that are not found in ArrayList.
	 */
	private void removeOldParams(ArrayList params)
	{
		for(int i=0; i<_linkPoints.size(); i++) {
			LinkPoint link = (LinkPoint)_linkPoints.get(i);
			if(!link.isOutput()) {
				boolean bExists = false;
				Iterator itr2 = params.iterator();
				while ( itr2.hasNext() ) {
					String param = (String)itr2.next();
					if(param.equalsIgnoreCase(link.getName()))
						bExists = true;
				}
				if(!bExists) {
					removeChild(link);
					i--;
				}
			}
		}
	}

	/**
	 * Adds new parameters from the ArrayList.
	 */
	private void addNewParameters(ArrayList params)
	{
		Iterator itr1 = params.iterator();
		while ( itr1.hasNext() ) {
			String param = (String)itr1.next();
			boolean bExists = false;
			Iterator itr2 = _linkPoints.iterator();
			while ( itr2.hasNext() ) {
				LinkPoint link = (LinkPoint)itr2.next();
				if(!link.isOutput() && param.equalsIgnoreCase(link.getName()))
					bExists = true;
			}
			if(!bExists)
				addParameter(param);
		}
	}
	
	/**
	 * Loads parameters from the function body.
	 * Parameters are tokens that begin with '%'.
	 */
	private ArrayList loadParams(String func)
	{
		ArrayList params = new ArrayList();
		String separators = ",./\\<>(){}+-_=|[]*&^$#@!~`";
		StringTokenizer st = new StringTokenizer(func, separators);
//		int counter = 0;
		while (st.hasMoreTokens()) {
			String tok = st.nextToken();
			tok = tok.trim();
			if(tok.length() > 0){
				if('%' == tok.charAt(0)) {
					String name = tok.substring(1);
					params.add(name);
				}
//				if(0 == counter)
//					setName(tok);
//				counter++;
			}
		}
	     return params;
	}
	
	public String getXPath()
	{
		return "$var:ResultOf" + getVarName();
	}

	/**
	 * Removes a parameter from the list.
	 */
	/*private void removeParameter(IMapObject param)
	{
		removeChild(param);
	}*/
	
//	public String getDisplayName() { return _displayName; }
	public String getDisplayName()
	{
		return isSelected() ? _body : _name;
	}
	
	private String getFunctionBody(String funcName)
	{
		for(int i=0; i<names.length; i++) {
			if(names[i].equals(funcName))
				return functions[i];
		}
		return "";
	}

	public void chooseFunction()
	{
        ListDialog.initialize(null, names,
                "Select a function",
                "Functions:");
		String name = ListDialog.showDialog(null, names[0]);
		if(name!=null && name.length()>0) {
			String body = getFunctionBody(name);
			if(body.length()>0) {
				FunctionBox.this.setFunctionBody(body);
				FunctionBox.this.setName(name);
			}
		}
	}
	
	private String getVarName()
	{
		return getName() + "_" + String.valueOf(_ID);
	}

//	public boolean rendersComplexNode()		{ return true; }
}
