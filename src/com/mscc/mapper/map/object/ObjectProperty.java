package com.mscc.mapper.map.object;


public class ObjectProperty {

	DefaultMapObject parent;
	String name = null;
	Object value = null;

	public ObjectProperty(DefaultMapObject mapperTreeNode, String name, Object value) {
		this.parent = mapperTreeNode;
		this.name = name;
		this.value = value;
	}

	public String toString() {
		return " (" + name + " = " + value + ") ";
	}
}
