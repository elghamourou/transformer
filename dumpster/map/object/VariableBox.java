package com.mscc.mapper.map.object;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import com.mscc.mapper.map.ILinkable;
import com.mscc.mapper.map.IMapObject;
import com.mscc.mapper.map.Map;
import com.mscc.mapper.tree.PathList;
import com.mscc.mapper.utils.XPathUtils;

public class VariableBox extends DragableMapObject {
	private String _value;
	private LinkPoint _input = new LinkPoint("input");
	private boolean _bValueIsXPath;
	private boolean _bGlobal;

	public VariableBox(Map map, Element node) {
		super(map, node);
		_linkPoints.add(new LinkPoint(true));

		addProperty("Value", _value);
	}

	public VariableBox(Map map, String name) {
		super(map, name);
		_value = "test_variable";
		_bValueIsXPath = false;
		_bGlobal = false;

		_linkPoints.add(new LinkPoint(true));
		_linkPoints.add(_input);

		addProperty("Value", _value);
	}

	public Element renderDeclaration(PathList context, Document doc) {
		ILinkable src = _input.getSource();
		String val = _bValueIsXPath ? _value : "'" + _value + "'";
		if (null != src)
			val = context.getRelativePath(src.getXPath());
		Element e = doc.createElement("xsl:variable");
		e.setAttribute("name", "var:" + _name);
		e.setAttribute("select", val);
		return e;
	}

	public Element render(PathList context, Element parent) {
		Document doc = parent.getOwnerDocument();

		if (!isGlobal())
			parent.appendChild(renderDeclaration(context, doc));

		Element newElement = doc.createElement("xsl:value-of");
		newElement.setAttribute("select", "$var:" + _name);
		parent.appendChild(newElement);
		return newElement;
	}

	public String getName() {
		return _name;
	}

	public String getXPath() {
		return "$var:" + _name;
	}

	public void removeChild(IMapObject param) {
	}

	public String getValue() {
		return _value;
	}

	public String getDisplayName() {
		ILinkable src = _input.getSource();
		if (null != src)
			return _name;
		else
			return _value != "" ? _value : _name;
	}

	public void setValue(String value) {
		_value = value;
	}

	public boolean isGlobal() {
		return _bGlobal;
	}

	public void setGlobal(boolean bGlobal) {
		_bGlobal = bGlobal;
	}

	public boolean isXPath() {
		return _bValueIsXPath;
	}

	public void setXPath(boolean bIsXPath) {
		_bValueIsXPath = bIsXPath;
	}

	public Element save(Element node) {
		super.save(node);

		node.setAttribute("value", getValue());
		node.setAttribute("global", String.valueOf(isGlobal()));
		node.setAttribute("xpath", String.valueOf(isXPath()));

		return node;
	}

	public boolean load(Element node) {
		if (super.load(node)) {
			setValue(XPathUtils.getNodeText(node, "@value/text()"));

			String tmp = XPathUtils.getNodeText(node, "@global/text()");
			if (null != tmp)
				setGlobal(Boolean.valueOf(tmp).booleanValue());

			tmp = XPathUtils.getNodeText(node, "@xpath/text()");
			if (null != tmp)
				setXPath(Boolean.valueOf(tmp).booleanValue());

			return true;
		}
		return false;
	}
}
