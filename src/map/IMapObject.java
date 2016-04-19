package map;

import java.util.Collection;

import org.w3c.dom.Element;

import simpleMapper.PathList;

public interface IMapObject extends IPropObject {
	abstract boolean isSelected();

	abstract void select(boolean sel);

	abstract String getName();

	abstract IMapObject getParentObject();

	abstract Collection getChildObjects();

	abstract IMapObject getChild(String name);

	abstract void removeChild(IMapObject obj);

	abstract String getDisplayName();

	abstract void setName(String name);

	abstract String getFullPath();

	abstract int getID();

	abstract void setID(int ID);

	abstract String getUniqueName();

	abstract Element save(Element node);

	abstract boolean load(Element node);

	abstract Element render(PathList context, Element parent);

	abstract Element renderDeclaration(PathList context, Element parent);

	abstract void move(int x, int y);

	abstract IMapObject copy();

}
