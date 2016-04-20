package com.mscc.mapper.map;

import java.util.Collection;

import org.w3c.dom.Element;

import com.mscc.mapper.tree.PathList;

public interface ILinkable extends IMapObject {
	abstract Collection getLinks();

	abstract boolean isOutput();

	abstract String getFullPath();

	abstract String getXPath();

	abstract ILinkable getSource();

	abstract boolean willAccept(ILinkable src);

	abstract Element render(PathList context, Element parent);

	abstract Element renderDeclaration(PathList context, Element parent);

}
