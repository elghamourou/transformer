package com.mscc.mapper.map.object;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import com.mscc.mapper.map.ILinkable;
import com.mscc.mapper.map.Map;
import com.mscc.mapper.tree.PathList;

public class WhenBox extends DragableMapObject {
	private LinkPoint _input = null;
	private LinkPoint _condition = null;

	public WhenBox(Map map, Element node) {
		super(map, node);

	}

	public WhenBox(Map map) {
		super(map, "When");

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

		ifElement = doc.createElement("xsl:when");

		String testPath = srcCondition.getXPath();
		testPath = context.getRelativePath(testPath);
		ifElement.setAttribute("test", testPath);

		if (link.getType() == LinkLine.XSL_FOR_EACH) {
			Element pparent = (Element) parent.getParentNode();
			Element outNode = link.render(context, pparent);
			outNode.appendChild(ifElement);
			ifElement.appendChild(parent);
			parentElement = outNode;
		} else {
			link.render(context, ifElement);
			parent.appendChild(ifElement);
		}

		renderVariableDeclaration(context, srcCondition, ifElement, parentElement);

		renderVariableDeclaration(context, srcInput, ifElement, parent);

		return parent;
	}

	private void renderVariableDeclaration(PathList context, ILinkable srcCondition, Element ifElement, Element parent) {
		Element chooseParent = (Element) parent.getParentNode();

		Document doc = parent.getOwnerDocument();
		Element decl = null;
		VariableBox var = null;
		FunctionBox func = null;
		if (srcCondition instanceof VariableBox)
			var = (VariableBox) srcCondition;
		else if (srcCondition instanceof FunctionBox)
			func = (FunctionBox) srcCondition;
		else if (srcCondition instanceof LinkPoint) {
			LinkPoint lp = (LinkPoint) srcCondition;
			if (lp.getParentObject() instanceof VariableBox)
				var = (VariableBox) lp.getParentObject();
			else if (lp.getParentObject() instanceof FunctionBox)
				func = (FunctionBox) lp.getParentObject();
		}
		if (null != var && !var.isGlobal()) {
			decl = var.renderDeclaration(context, doc);
			chooseParent.insertBefore(decl, parent);
		} else if (null != func && !func.isGlobal()) {
			decl = func.renderDeclaration(context, doc);
			chooseParent.insertBefore(decl, parent);
		}
	}

	public ILinkable getSource() {
		return _input.getSource();
	}

	public String getDisplayName() {
		return "When";
	}

	public Element save(Element node) {
		return super.save(node);
	}

	public boolean load(Element node) {
		return super.load(node);
	}
}
