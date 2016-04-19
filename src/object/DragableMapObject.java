package object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import map.ILinkable;
import map.IMapObject;
import map.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import simpleMapper.PathList;
import utils.DOMUtils;
import utils.XPathUtils;

public class DragableMapObject extends DefaultMapObject {
	protected Map _map = null;
	protected String _name = "DragableMapObject";
	protected ArrayList _linkPoints = new ArrayList();

	private LinkPoint _outputLink = new LinkPoint(true);

	public static final String PATH_SEPARATOR = "/";

	private boolean _bGlobal = true;

	public class LinkPoint extends DefaultLinkable {
		private boolean _bOutput = false;
		private int _index = 0;
		private int _x1 = 0, _y1 = 0, _x2 = 0, _y2 = 0;
		private String _name = "param";

		public LinkPoint() {
			this(false);
		}

		public LinkPoint(boolean bOutput) {
			this("param", bOutput);
		}

		public LinkPoint(String name, boolean bOutput) {
			name = name.replaceAll(" ", "_");
			if (!bOutput) {
				_name = name;
				_index = getLinkCount() + 1;
				if (getLinkCount(name) > 0)
					_name += _index - 1;
			} else {
				_index = 0;
				_name = "output";
			}

			_bOutput = bOutput;

			addProperty("Name", _name);
		}

		public LinkPoint(String name) {
			this(name, false);
		}

		public void setIndex(int index) {
			if (!_bOutput) {
				_index = index;
				_name = "param" + _index;
			}
		}

		public String toString() {
			return _name;
		}

		public String getFullPath() {
			return DragableMapObject.this.getFullPath() + DragableMapObject.PATH_SEPARATOR + _name;
		}

		public String getUniqueName() {
			return getName();
		}

		public String getXPath() {
			return DragableMapObject.this.getXPath();
		}

		public String getDisplayName() {
			return (_bOutput ? "Function output" : "Function parameter");
		}

		public String getName() {
			return (_bOutput ? "output" : _name);
		}

		public IMapObject getParentObject() {
			return DragableMapObject.this;
		}

		public void setName(String name) {
			if (!_bOutput && null != name && name.trim() != "") {
				_name = name;
			}
		}

		public boolean isOutput() {
			return _bOutput;
		}

		public boolean willAccept(ILinkable src) {
			return (_bOutput ? true : !isLinked());
		}
	}

	public DragableMapObject(Map map, Element node) {
		_map = map;
		_linkPoints.add(_outputLink); // Output
		load(node);
	}

	public DragableMapObject(Map map, String name) {
		_map = map;
		_name = name.replaceAll(" ", "_");
		addProperty("Name", _name);
		_linkPoints.add(_outputLink); // Output
	}

	private int getLinkCount(String name) {
		int result = 0;
		Collection children = getChildObjects();
		if (null != children) {
			Iterator iter = children.iterator();
			while (iter.hasNext()) {
				LinkPoint link = (LinkPoint) iter.next();
				if (!link.isOutput() && name.equalsIgnoreCase(link.getName()))
					result++;
			}
		}
		return result;
	}

	private int getLinkCount() {
		int result = 0;
		Collection children = getChildObjects();
		if (null != children) {
			Iterator iter = children.iterator();
			while (iter.hasNext()) {
				LinkPoint link = (LinkPoint) iter.next();
				if (!link.isOutput())
					result++;
			}
		}
		return result;
	}

	public String toString() {
		return _name;
	}

	public String getName() {
		return _name;
	}

	public String getFullPath() {
		return getUniqueName();
	}

	public String getXPath() {
		return _name;
	}

	public void setName(String name) {
		if (name != null && name.length() > 0) {
			_name = name;
		}
	}

	public Collection getChildObjects() {
		return _linkPoints;
	}

	public IMapObject getChild(String uniqueName) {
		Iterator iter = _linkPoints.iterator();
		while (iter.hasNext()) {
			IMapObject obj = (IMapObject) iter.next();
			// String tmp = obj.getName();
			if (uniqueName.equals(obj.getUniqueName()))
				return obj;
		}
		return null;
	}

	public void removeChild(IMapObject obj) {
		LinkPoint lp = (LinkPoint) obj;
		if (!lp.isOutput())
			_linkPoints.remove(obj);
	}

	public Element save(Element node) {
		super.save(node);

		node.setAttribute("name", getName());
		node.setAttribute("global", String.valueOf(isGlobal()));

		// Save input parameters
		Document doc = node.getOwnerDocument();
		Iterator itr = _linkPoints.iterator();
		while (itr.hasNext()) {
			LinkPoint link = (LinkPoint) itr.next();
			if (!link.isOutput()) {
				Element paramElement = doc.createElement("param");
				paramElement.setAttribute("name", link.getName());
				node.appendChild(paramElement);
			}
		}

		return node;
	}

	public boolean load(Element node) {
		if (!super.load(node))
			return false;

		_name = XPathUtils.getNodeText(node, "@name/text()");

		String tmp = XPathUtils.getNodeText(node, "@x/text()");
		int x = tmp != "" ? Integer.valueOf(tmp).intValue() : -1;

		tmp = XPathUtils.getNodeText(node, "@y/text()");
		int y = tmp != "" ? Integer.valueOf(tmp).intValue() : -1;

		tmp = XPathUtils.getNodeText(node, "@global/text()");
		if (null != tmp)
			setGlobal(Boolean.valueOf(tmp).booleanValue());

		if (x > 0 && y > 0) {
			List results = XPathUtils.getNodes(node, "param");
			if (null != results) {
				Iterator resultIter = results.iterator();
				while (resultIter.hasNext()) {
					Node child = (Node) resultIter.next();
					String name = XPathUtils.getNodeText(child, "@name/text()");
					LinkPoint param = new LinkPoint(name);
					_linkPoints.add(param);
				}
			}
			return true;
		}

		return false;
	}

	public LinkPoint getOutputLink() {
		return _outputLink;
	}

	protected void clearInputs() {
		for (int i = 0; i < _linkPoints.size(); i++) {
			LinkPoint tmp = (LinkPoint) _linkPoints.get(i);
			if (!tmp.isOutput()) {
				_map.removeLinksConnectedTo(tmp);
				_linkPoints.remove(tmp);
				i--;
			}
		}
	}

	protected void removeInput(LinkPoint input) {
		Iterator itr = _linkPoints.iterator();
		while (itr.hasNext()) {
			LinkPoint link = (LinkPoint) itr.next();
			if (!link.isOutput() && link.equals(input)) {
				_linkPoints.remove(link);
				_map.removeLinksConnectedTo(link);
				break;
			}
		}
	}

	public int getInputCount() {
		int count = 0;
		Iterator iter = _linkPoints.iterator();
		while (iter.hasNext()) {
			LinkPoint link = (LinkPoint) iter.next();
			if (!link.isOutput())
				count++;
		}
		return count;
	}

	protected LinkPoint getInput(String name) {
		Iterator itr = _linkPoints.iterator();
		while (itr.hasNext()) {
			LinkPoint link = (LinkPoint) itr.next();
			if (!link.isOutput() && name.equalsIgnoreCase(link.getName()))
				return link;
		}
		return null;
	}

	public String renderAsVariable(PathList context, Element parent) {
		String name = "ResultOf" + _name;
		Document doc = parent.getOwnerDocument();
		Element e = doc.createElement("xsl:variable");
		e.setAttribute("name", "var:" + name);
		parent.appendChild(e);
		boolean bStringValue = false;
		if (bStringValue) {
			String val = "";
			e.setAttribute("select", val);
		} else
			render(context, e);
		return name;
	}

	public boolean isGlobal() {
		return _bGlobal;
	}

	public void setGlobal(boolean bGlobal) {
		_bGlobal = bGlobal;
	}

	public IMapObject copy() {
		Document doc = DOMUtils.newDocument();
		Element element = doc.createElement("copy");
		save(element);
		IMapObject copy = new DragableMapObject(_map, element);
		return copy;
	}
}
