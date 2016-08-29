package com.mscc.tree;

import org.jdom2.Document;
import org.jdom2.Element;

public class MapperTreeModel {

	MapperTreeNode root;

	public MapperTreeModel(MapperTreeNode newRoot) {

		this.root = newRoot;

	}

	public boolean load(String uri) {
		return false;

	}

	public void setRoot(MapperTreeNode root) {
		Object oldRoot = this.root;
		this.root = root;
	}

	public MapperTreeNode getRoot() {
		return root;
	}

	public int getChildCount(Object parent) {
		return ((MapperTreeNode) parent).getChildCount();
	}

	public Object getChild(Object parent, int index) {
		return ((MapperTreeNode) parent).getChildAt(index);
	}

	public String toString() {
		return getTreeText(this, this.getRoot(), "\t");
	}

	private static String getTreeText(MapperTreeModel model, Object object, String indent) {
		String myRow = indent + object + "\n";
		for (int i = 0; i < model.getChildCount(object); i++) {
			myRow += getTreeText(model, model.getChild(object, i), indent + "\t");
		}
		return myRow;
	}
	
	public Element toXml() {
		return getXMLTree(this, this.getRoot());
	}
	
	private static Document xmldoc = null;
	private static Element getXMLTree(MapperTreeModel model, MapperTreeNode mtn) {
		Element element=new Element(mtn.getName());
		
		if(xmldoc==null){
			xmldoc=new Document();
			xmldoc.setRootElement(element);
		}
		
		for (int i = 0; i < model.getChildCount(mtn); i++) {
			if(!((MapperTreeNode)model.getChild(mtn, i)).isTextNode()){
				element.addContent(getXMLTree(model, (MapperTreeNode)model.getChild(mtn, i)));
			}
			
		}
		return element;
	}

}
