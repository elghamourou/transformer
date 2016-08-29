package com.mscc.mapper.map.object;

import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mscc.mapper.map.ILinkable;
import com.mscc.mapper.map.Map;
import com.mscc.mapper.tree.PathList;

public class ChooseBox extends DragableMapObject {

	public LinkPoint addParameter() {
		LinkPoint param = new LinkPoint("when");
		_linkPoints.add(param);
		WhenBox wb = _map.addNewWhenBox();
		_map.addLink(wb.getOutputLink(), param, LinkLine.XSL_VALUE_OF);
		return param;
	}

	public ChooseBox(Map map, Element node) {
		super(map, node);
		_linkPoints.add(new LinkPoint(true));
	}

	public ChooseBox(Map map) {
		super(map, "Choose");
		_linkPoints.add(new LinkPoint(true));
	}

	public Element render(PathList context, Element parent) {
		Document doc = parent.getOwnerDocument();

		Element chooseElement = doc.createElement("xsl:choose");
		parent.appendChild(chooseElement);

		Iterator iter = _linkPoints.iterator();
		while (iter.hasNext()) {
			LinkPoint link = (LinkPoint) iter.next();
			if (!link.isOutput()) {
				ILinkable src = link.getSource();
				if (null != src) {
					Element whenElement = null;
					if (null != src.getParentObject() && src.getParentObject() instanceof WhenBox) {
						WhenBox box = (WhenBox) src.getParentObject();
						box.render(context, chooseElement);
					} else {
						String srcPath = context.getRelativePath(src.getXPath());
						whenElement = doc.createElement("xsl:when");
						whenElement.setAttribute("test", srcPath);
						chooseElement.appendChild(whenElement);
					}
				}
			}
		}
		return chooseElement;
	}

	public String getDisplayName() {
		return "Choose";
	}

	public Element save(Element node) {
		return super.save(node);

	}

	public boolean load(Element node) {
		return super.load(node);
	}
}
