package com.mscc.mapper.map.object;

import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mscc.mapper.map.ILinkable;
import com.mscc.mapper.map.IMapObject;
import com.mscc.mapper.map.Map;
import com.mscc.mapper.tree.PathList;
import com.mscc.mapper.utils.XPathUtils;

public class ExpressionBox extends DragableMapObject {
	private Map _map;
	private int _viewWidth, _viewHeight;
	private boolean _bTemplate;
	private Output _output;

	public class Input extends DragableMapObject {
		private LinkPoint _input;

		public Input(LinkPoint input) {
			super(ExpressionBox.this._map, input.getName());
			_input = input;
			_linkPoints.add(new LinkPoint(true));
		}

		public String getName() {
			return _input.getName();
		}

		public String getUniqueName() {
			return getName();
		}

		public String getXPath() {
			if (!ExpressionBox.this._bTemplate)
				return getSource().getXPath();
			else
				// Template parameter
				return "$" + _input.getName();
		}

		public ILinkable getSource() {
			return _input.getSource();
		}

		public Element render(PathList context, Element parent) {
			return _input.getParentObject().render(context, parent);
		}

		public IMapObject getParentObject() {
			return ExpressionBox.this;
		}
	}

	private class Output extends DragableMapObject {
		public Output(LinkPoint input) {
			super(ExpressionBox.this._map, input.getName());

			// Remove output link
			Iterator iter = _linkPoints.iterator();
			while (iter.hasNext()) {
				LinkPoint link = (LinkPoint) iter.next();
				if (link.isOutput()) {
					_linkPoints.remove(link);
					break;
				}
			}

			_linkPoints.add(new LinkPoint("input"));
		}

		public String getUniqueName() {
			return getName();
		}
	}

	public ExpressionBox(Map map) {
		super(map, "Expression");
		_viewWidth = _viewHeight = 200;
		_bTemplate = true;
		_map = new Map();
		setOutput(getOutputLink());
	}

	public ExpressionBox(Map map, Element node) {
		super(map, node);
	}

	public boolean isTemplate() {
		return _bTemplate;
	}

	private void addInput(LinkPoint param, boolean bNew) {
		if (bNew)
			_linkPoints.add(param);

		int count = getInputCount();
		_map.addDragableObject(new Input(param));

	}

	public Element save(Element node) {
		super.save(node);
		_map.save(node);

		node.setAttribute("width", String.valueOf(_viewWidth));
		node.setAttribute("height", String.valueOf(_viewHeight));
		node.setAttribute("template", String.valueOf(_bTemplate));

		return node;
	}

	public boolean load(Element node) {
		if (super.load(node)) {
			_map = new Map();
			_map.clear();
			loadInputs();

			String tmp = XPathUtils.getNodeText(node, "@width/text()");
			int i = tmp != "" ? Integer.valueOf(tmp).intValue() : -1;
			if (i > 0)
				_viewWidth = i;

			tmp = XPathUtils.getNodeText(node, "@height/text()");
			i = tmp != "" ? Integer.valueOf(tmp).intValue() : -1;
			if (i > 0)
				_viewHeight = i;

			tmp = XPathUtils.getNodeText(node, "@template/text()");
			if (null != tmp)
				_bTemplate = Boolean.valueOf(tmp).booleanValue();

			// Load input and output coordinates

			return _map.loadNoClear(node);
		}
		return false;
	}

	public void removeChild(IMapObject obj) {
		if (obj instanceof Input) {
			Input in = (Input) obj;
			super.removeChild(in._input);
		} else if (obj instanceof LinkPoint) {
			Iterator iter = _map.getChildObjects().iterator();
			while (iter.hasNext()) {
				IMapObject tmp = (IMapObject) iter.next();
				if (tmp instanceof Input) {
					Input in = (Input) tmp;
					if (in._input == obj) {
						_map.removeChild(in);
						break;
					}
				}
			}
			super.removeChild(obj);
		}
	}

	private void loadInputs() {
		Iterator iter = _linkPoints.iterator();
		while (iter.hasNext()) {
			LinkPoint link = (LinkPoint) iter.next();
			if (!link.isOutput())
				addInput(link, false);
			else
				setOutput(link);
		}
	}

	private void setOutput(LinkPoint link) {
		_output = new Output(link);
		_map.addDragableObject(_output);
	}

	public Element renderDeclaration(PathList context, Document doc) {
		if (isTemplate())
			return renderTemplateDecl(context, doc);
		else
			return null;
	}

	private Element renderTemplateDecl(PathList context, Document doc) {
		Element e = doc.createElement("xsl:template");
		e.setAttribute("name", "user:" + _name);

		LinkPoint lp = (LinkPoint) _output.getChild("input");
		if (null == lp)
			return null;

		ILinkable src = lp.getSource();
		if (null == src)
			return null;

		_map.renderGlobalDeclarations(context, e);

		Iterator iter = _linkPoints.iterator();
		while (iter.hasNext()) {
			LinkPoint link = (LinkPoint) iter.next();
			if (!link.isOutput()) {
				Element p = doc.createElement("xsl:param");
				p.setAttribute("name", link.getName());
				// e.appendChild(p);
				e.insertBefore(p, e.getFirstChild());
			}
		}

		if (src instanceof LinkPoint)
			src.getParentObject().render(context, e);
		else
			src.render(context, e);

		return e;
	}

	private Element renderTemplateCall(PathList context, Element parent) {
		Document doc = parent.getOwnerDocument();
		Element e = doc.createElement("xsl:call-template");
		e.setAttribute("name", "user:" + _name);

		Iterator iter = _linkPoints.iterator();
		while (iter.hasNext()) {
			LinkPoint link = (LinkPoint) iter.next();
			if (!link.isOutput()) {
				ILinkable src = link.getSource();
				if (null != src) {
					Element p = doc.createElement("xsl:with-param");
					p.setAttribute("name", link.getName());
					p.setAttribute("select", context.getRelativePath(src.getXPath()));
					e.appendChild(p);
				}
			}
		}

		parent.appendChild(e);
		return parent;
	}

	public Element render(PathList context, Element parent) {
		if (isTemplate())
			return renderTemplateCall(context, parent);

		LinkPoint lp = (LinkPoint) _output.getChild("input");
		if (null == lp)
			return parent;

		ILinkable src = lp.getSource();
		if (null == src)
			return parent;

		_map.renderGlobalDeclarations(context, parent);

		Element expElement = null;

		if (src instanceof LinkPoint)
			expElement = src.getParentObject().render(context, parent);
		else
			expElement = src.render(context, parent);

		return expElement;
	}
}
