package com.mscc.mapper.map.object;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.mscc.mapper.map.ILinkable;
import com.mscc.mapper.map.object.DragableMapObject.LinkPoint;
import com.mscc.mapper.tree.MapperTreeNode;
import com.mscc.mapper.tree.PathList;
import com.mscc.mapper.utils.XPathUtils;

public class LinkLine extends DefaultMapObject {
	public static final int XSL_FOR_EACH = 0;
	public static final int XSL_VALUE_OF = 1;
	public static final int XSL_TEMPLATE = 2;

	private int _lineType = XSL_VALUE_OF;
	private ILinkable _src = null, _dst = null;
	private int _x1 = 0, _y1 = 0, _x2 = 0, _y2 = 0;

	public LinkLine(ILinkable src, ILinkable dst) {

		if (((src instanceof MapperTreeNode) && src.isOutput()) || ((dst instanceof MapperTreeNode) && !dst.isOutput())) {
			ILinkable tmp = src;
			src = dst;
			dst = tmp;
		} else if (!(src instanceof MapperTreeNode) && !(dst instanceof MapperTreeNode)) {
			if (!src.isOutput() || dst.isOutput()) {
				ILinkable tmp = src;
				src = dst;
				dst = tmp;
			}
		}

		_src = src;
		_dst = dst;

		this.addProperty("Source", _src);
		this.addProperty("Destination", _dst);
	}

	public ILinkable getSrc() {
		return _src;
	}

	/*
	 * { Linkable parent = (Linkable)_src.getParentObject(); return
	 * (parent!=null ? parent : _src); }
	 */

	public ILinkable getDest() {
		return _dst;
	}

	public String toString() {
		return _src.getFullPath() + " -> " + _dst.getFullPath();
	}

	public void select(boolean sel) {
		super.select(sel);
		if (!_src.isSelected())
			_src.select(sel);
		if (!_dst.isSelected())
			_dst.select(sel);
	}

	public String getName() {
		return "LinkLine";
	}

	public int getType() {
		return _lineType;
	}

	public Element render(PathList context, Element parent) {
		Document doc = parent.getOwnerDocument();

		if ((_src instanceof LinkPoint) && (_src.getParentObject() instanceof IfBox)) {
			IfBox ifbox = (IfBox) _src.getParentObject();
			Element newElement = ifbox.render(context, parent);
			return newElement;
		} else if ((_src instanceof LinkPoint) && (_src.getParentObject() instanceof ChooseBox)) {
			ChooseBox box = (ChooseBox) _src.getParentObject();
			Element newElement = box.render(context, parent);
			return newElement;
		} else {
			boolean bNewContext = false;
			String xslcmd = getXSLName();
			String srcPath = context.getRelativePath(_src.getXPath());
			Element newElement = doc.createElement(xslcmd);
			newElement.setAttribute("select", srcPath);
			parent.appendChild(newElement);
			bNewContext = (getType() == LinkLine.XSL_FOR_EACH);
			if (bNewContext)
				context.set(_src.getXPath());
			return bNewContext ? newElement : parent;
		}
	}

	private String getXSLName() {
		String xslcmd = "";
		switch (getType()) {
		case LinkLine.XSL_FOR_EACH:
			xslcmd = "xsl:for-each";
			break;
		case LinkLine.XSL_TEMPLATE:
			xslcmd = "xsl:template";
			break;
		case LinkLine.XSL_VALUE_OF:
			xslcmd = "xsl:value-of";
			break;
		}
		return xslcmd;
	}

	public Element save(Element node) {
		ILinkable src = this.getSrc();
		ILinkable dst = this.getDest();
		if ((null != src) && (null != dst)) {
			node.setAttribute("src", src.getFullPath());
			node.setAttribute("dst", dst.getFullPath());
			node.setAttribute("xsl-type", getXSLName());
		}
		return node;
	}

	public boolean load(Element node) {
		Node tmp = XPathUtils.getNode(node, "@xsl-type/text()");
		if (null != tmp) {
			String str = tmp.getNodeValue().trim();
			if (str.equals("xsl:for-each"))
				_lineType = LinkLine.XSL_FOR_EACH;
			else if (str.equals("xsl:template"))
				_lineType = LinkLine.XSL_TEMPLATE;
			else if (str.equals("xsl:value-of"))
				_lineType = LinkLine.XSL_VALUE_OF;
		}
		return true;
	}
}
