package com.mscc.mapper.map.object;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;

import org.w3c.dom.Element;

import com.mscc.mapper.map.ILinkable;
import com.mscc.mapper.tree.PathList;

public class DefaultLinkable extends DefaultMapObject implements ILinkable {

	protected ArrayList _links = new ArrayList();

	public DefaultLinkable() {
	}

	public String getName() {
		return "DefaultLinkable";
	}

	public boolean isLinked() {
		return !_links.isEmpty();
	}

	public Collection getLinks() {
		return _links;
	}

	public boolean isOutput() {
		return false;
	}

	public String getFullPath() {
		return this.toString();
	}

	public String getXPath() {
		return getFullPath();
	}

	public ILinkable getSource() {
		Iterator iter = _links.iterator();
		while (iter.hasNext()) {
			LinkLine line = (LinkLine) iter.next();
			ILinkable src = line.getSrc();
			if (!src.equals(this))
				return src;
		}
		return null;
	}

	public LinkLine getLink() {
		Iterator iter = _links.iterator();
		while (iter.hasNext()) {
			LinkLine line = (LinkLine) iter.next();
			ILinkable src = line.getSrc();
			if (!src.equals(this))
				return line;
		}
		return null;
	}

	public boolean willAccept(ILinkable src) {
		return true;
	}

	public Element render(PathList context, Element parent) {
		return null;
	}

	public static LinkLine findLinkLine(ILinkable src, ILinkable dst) {
		Iterator iter = src.getLinks().iterator();
		while (iter.hasNext()) {
			LinkLine line = (LinkLine) iter.next();
			if (line.getSrc() == src)
				return line;
		}
		return null;
	}

	public Element renderDeclaration(PathList context, Element parent) {
		return null;
	}

	public void renderDeclarations(PathList context, Element parent) {
	}

}
