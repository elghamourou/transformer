package com.mscc.mapper.map.object;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import com.mscc.mapper.map.ILinkable;
import com.mscc.mapper.map.Map;
import com.mscc.mapper.tree.PathList;

public class IfBox extends DragableMapObject {
	private LinkPoint _input = null;
	private LinkPoint _condition = null;

	public IfBox(Map map, Element node) {
		super(map, node);

	}

	public IfBox(Map map) {
		super(map, "IF");

		_linkPoints.add(new LinkPoint(true));
		_linkPoints.add(_input = new LinkPoint("input"));
		_linkPoints.add(_condition = new LinkPoint("condition"));
	}

	public Element render(PathList context, Element parent) {
		_input = (DragableMapObject.LinkPoint) super.getChild("input");
		_condition = (DragableMapObject.LinkPoint) super.getChild("condition");

		ILinkable srcCondition = _condition.getSource();
		ILinkable srcInput = _input.getSource();

		if (null == srcCondition || null == srcInput)
			return null;

		Document doc = parent.getOwnerDocument();

		LinkLine link = _input.getLink();

		Element parentElement = parent;
		Element ifElement = null;

		ifElement = doc.createElement("xsl:if");

		String testPath = srcCondition.getXPath();
		testPath = context.getRelativePath(testPath);
		ifElement.setAttribute("test", testPath);

		if (link.getType() == LinkLine.XSL_FOR_EACH) {
			Element pparent = (Element) parent.getParentNode();
			if (null != pparent) {
				Element outNode = link.render(context, pparent);
				outNode.appendChild(ifElement);
				ifElement.appendChild(parent);
				parentElement = outNode;
			} else {
				link.render(context, ifElement);
				parent.appendChild(ifElement);
			}
		} else {
			link.render(context, ifElement);
			parent.appendChild(ifElement);
		}

		renderVariableDeclaration(context, srcCondition, ifElement, parentElement);

		renderVariableDeclaration(context, srcInput, ifElement, parentElement);

		return parent;
	}

	private void renderVariableDeclaration(PathList context, ILinkable srcCondition, Element ifElement, Element parent) {
		Document doc = parent.getOwnerDocument();
		Element decl = null;
		VariableBox var = null;
		if (srcCondition instanceof VariableBox)
			var = (VariableBox) srcCondition;
		else if (srcCondition instanceof LinkPoint) {
			LinkPoint lp = (LinkPoint) srcCondition;
			if (lp.getParentObject() instanceof VariableBox)
				var = (VariableBox) lp.getParentObject();
		}
		if (null != var && !var.isGlobal()) {
			decl = var.renderDeclaration(context, doc);
			parent.insertBefore(decl, ifElement);
		}
	}

	public ILinkable getSource() {
		return _input.getSource();
	}

	public String getDisplayName() {
		return "IF";
	}

	public Element save(Element node) {
		return super.save(node);
	}

	public boolean load(Element node) {
		if (super.load(node)) {
			_input = (DragableMapObject.LinkPoint) super.getChild("input");
			_condition = (DragableMapObject.LinkPoint) super.getChild("condition");
			return true;
		}
		return false;
	}
}
