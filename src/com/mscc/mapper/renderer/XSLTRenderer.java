package com.mscc.mapper.renderer;

import java.io.StringWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mscc.mapper.map.Map;
import com.mscc.mapper.tree.MapperTreeNode;
import com.mscc.mapper.tree.PathList;
import com.mscc.mapper.utils.DOMUtils;

public class XSLTRenderer {
	private Document _doc = null;
	private PathList _contextPath = new PathList();
	private StringWriter _strWriter = new StringWriter();
	private boolean _bOmitXSLDecl = true;

	public XSLTRenderer() {
	}

	private Element createEmptyXSL() {
		_doc = DOMUtils.newDocument();

		if (null == _doc)
			return null;

		_doc.appendChild(_doc.createComment("Generated using the MSCC XML Mapper"));
		_doc.appendChild(_doc.createComment("dev@mscc.com"));

		Element stylesheet = _doc.createElement("xsl:stylesheet");
		stylesheet.setAttribute("version", "2.0");
		stylesheet.setAttribute("xmlns:var", "urn:var");
		stylesheet.setAttribute("xmlns:user", "urn:user");
		stylesheet.setAttribute("xmlns:xsl", "http://www.w3.org/1999/XSL/Transform");
		stylesheet.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		stylesheet.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema");
		stylesheet.setAttribute("xmlns:fn", "http://www.w3.org/2005/xpath-functions");
		stylesheet.setAttribute("exclude-result-prefixes", "user xs fn  user");
		_doc.appendChild(stylesheet);

		Element outputElement = _doc.createElement("xsl:output");
		outputElement.setAttribute("method", "xml");
		outputElement.setAttribute("encoding", "UTF-8");
		outputElement.setAttribute("indent", "yes");
		if (_bOmitXSLDecl)
			outputElement.setAttribute("omit-xml-declaration", "yes");
		stylesheet.appendChild(outputElement);

		return stylesheet;
	}

	public void render(Map map) {
		Element stylesheet = createEmptyXSL();

		if (null == stylesheet)
			return;

		PathList context = new PathList();
		map.renderGlobalDeclarations(context, stylesheet);

		MapperTreeNode srcRoot = map.getSource().getRootNode();
		Element templateElement = _doc.createElement("xsl:template");
		templateElement.setAttribute("match", srcRoot.getFullPath());
		stylesheet.appendChild(templateElement);
		_contextPath.set(srcRoot.toString());

		MapperTreeNode dstRoot = map.getDestination().getRootNode();
		dstRoot.render(_contextPath, templateElement);

		DOMUtils.renderXMLString(_doc, _strWriter);
	}

	public String getString() {
		return _strWriter.toString();
	}
}
