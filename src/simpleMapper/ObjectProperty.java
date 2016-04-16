package simpleMapper;

import object.DefaultMapObject;

public class ObjectProperty {

	
	DefaultMapObject parent;
	String name = null;
	Object value = null;
	public ObjectProperty(DefaultMapObject mapperTreeNode, String name, Object value) {
		this.parent = mapperTreeNode;
		this.name = name;
		this.value = value;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " ("+name+" = "+value+") ";
	}
}
