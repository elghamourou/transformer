package object;

import java.util.ArrayList;
import java.util.Collection;

import map.IMapObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import simpleMapper.ObjectProperty;
import utils.DOMUtils;
import simpleMapper.PathList;
import utils.XPathUtils;

public class DefaultMapObject extends Object implements IMapObject {
	protected boolean _selected = false;

	protected ArrayList _properties = new ArrayList();
	protected int _ID = this.hashCode();

	public boolean isSelected() {
		return _selected;
	}

	public void select(boolean sel) {
		_selected = sel;
	}

	public String getName() {
		return "DefaultMapObject";
	}

	public void setName(String name) {
	}

	public IMapObject getParentObject() {
		return null;
	}

	public Collection getChildObjects() {
		return null;
	}

	public void removeChild(IMapObject obj) {
	}

	public String getDisplayName() {
		return getName();
	}

	public IMapObject getChild(String name) {
		return null;
	}

	public String getFullPath() {
		return null;
	}

	public int getID() {
		return _ID;
	}

	public void setID(int ID) {
		_ID = ID;
	}

	public int getPropertyCount() {
		return _properties.size();
	}

	public void addProperty(String name, Object value) {
		_properties.add(new ObjectProperty(this, name, value));
	}

	public void removeProperty(int index) {
		_properties.remove(index);
	}

	public void removeProperty(ObjectProperty prop) {
		_properties.remove(prop);
	}

	public ObjectProperty getProperty(int index) {
		return (ObjectProperty) _properties.get(index);
	}

	public void propertyUpdated(ObjectProperty prop) {
		System.out.println("propertyUpdated(" + prop + ")");
	}

	public String getUniqueName() {
		return getName() + "[" + String.valueOf(_ID) + "]";
	}

	public Element save(Element node) {
		node.setAttribute("ID", String.valueOf(this.getID()));

		return node;
	}

	public boolean load(Element node) {
		String tmp = XPathUtils.getNodeText(node, "@ID/text()");
		if (tmp.trim() == "")
			return false;
		_ID = Integer.valueOf(tmp).intValue();
		return true;
	}

	public Element render(PathList context, Element parent) {
		return null;
	}

	public Element renderDeclaration(PathList context, Element parent) {
		return null;
	}

	public void move(int x, int y) {
	}

	public IMapObject copy() {
		Document doc = DOMUtils.newDocument();
		Element element = doc.createElement("copy");
		save(element);
		try {
			IMapObject copy = (IMapObject) this.clone();
			copy.load(element);
			return copy;
		} catch (CloneNotSupportedException e) {
		}
		return null;
	}
}
