package com.mscc.mapper.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mscc.mapper.map.ILinkable;
import com.mscc.mapper.map.IMapObject;
import com.mscc.mapper.map.object.DefaultLinkable;
import com.mscc.mapper.map.object.DefaultMapObject;
import com.mscc.mapper.map.object.LinkLine;
import com.mscc.mapper.map.object.ObjectProperty;
import com.mscc.mapper.utils.XMLUtils;

public class MapperTreeNode extends DefaultMapObject implements ILinkable {

	private DefaultLinkable linkable = new DefaultLinkable();
	MapperTree tree = null;
	private String name = "Root";
	private ArrayList properties = new ArrayList();
	private MapperTreeNode textNode = null;
	private String value = "";
	private boolean bTextNode = false;
	private boolean bAttribute = false;
	private MapperTreeNode parent;
	protected Vector children;
	static public final Enumeration<TreeNode> EMPTY_ENUMERATION = Collections.emptyEnumeration();

	public void setTree(MapperTree tree) {
		this.tree = tree;
		for (Enumeration e = children(); e.hasMoreElements();) {
			MapperTreeNode child = (MapperTreeNode) e.nextElement();
			child.setTree(tree);
		}

	}

	public Collection getLinks() {
		return linkable.getLinks();
	}

	public boolean isOutput() {
		if (tree != null)
			return ((MapperTree) tree).isSinkTree();
		else
			return false;
	}

	public String getFullPath() {
		String path = "";
		MapperTreeNode parent = (MapperTreeNode) this.getParent();

		while (parent != null) {
			path = parent.getXPathName() + "/" + path;
			parent = (MapperTreeNode) parent.getParent();
		}

		path += this.getXPathName();
		return "/" + path;
	}

	public boolean isLinked() {
		return linkable.isLinked();
	}

	public String getXPath() {
		return getFullPath();
	}

	public ILinkable getSource() {
		return linkable.getSource();
	}

	public boolean willAccept(ILinkable src) {
		if ((src != null) && (src instanceof MapperTreeNode)) {
			MapperTreeNode node = (MapperTreeNode) src;
			if (node.getTree().equals(tree))
				return false;
		}
		return (!(isLinked()));
	}

	public MapperTreeNode getChildByName(String name) {
		Enumeration e = this.children();
		while (e.hasMoreElements()) {
			MapperTreeNode child = (MapperTreeNode) e.nextElement();
			// String s = child.getXPathName();
			if (child.getXPathName().equals(name))
				return child;
		}

		return null;
	}

	public Enumeration children() {
		if (children == null) {
			return EMPTY_ENUMERATION;
		} else {
			return children.elements();
		}
	}

	private int getIndex() {
		int index = 1;
		MapperTreeNode parent = (MapperTreeNode) this.getParent();
		if (null != parent) {
			Enumeration e = parent.children();
			while (e.hasMoreElements()) {
				MapperTreeNode child = (MapperTreeNode) e.nextElement();
				if (this == child)
					break;
				else if (child.getName().equals(this.name))
					index++;
			}
		}
		return index;
	}

	private boolean hasSiblings() {
		MapperTreeNode parent = (MapperTreeNode) this.getParent();
		if (null != parent) {
			Enumeration e = parent.children();
			int index = 0;
			while (e.hasMoreElements() && index < 2) {
				index++;
			}
			return (index > 1);
		}
		return false;
	}

	public String getXPathName() {
		int index = getIndex();
		String result = ((bAttribute && !isTextNode()) ? "@" : "") + name;
		if (index > 1 && hasSiblings() && !bAttribute)
			result += "[" + index + "]";

		return result;
	}

	public boolean isNodeChild(MapperTreeNode aNode) {
		boolean retval;

		if (aNode == null) {
			retval = false;
		} else {
			if (getChildCount() == 0) {
				retval = false;
			} else {
				retval = (aNode.getParent() == this);
			}
		}

		return retval;
	}

	public MapperTreeNode getChildAt(int index) {
		if (children == null) {
			throw new ArrayIndexOutOfBoundsException("node has no children");
		}
		return (MapperTreeNode) children.elementAt(index);
	}

	public void remove(int childIndex) {
		MapperTreeNode child = (MapperTreeNode) getChildAt(childIndex);
		children.removeElementAt(childIndex);
		child.setParent(null);
	}

	public void setParent(MapperTreeNode newParent) {
		parent = newParent;
	}

	public int getIndex(MapperTreeNode aChild) {
		if (aChild == null) {
			throw new IllegalArgumentException("argument is null");
		}

		if (!isNodeChild(aChild)) {
			return -1;
		}
		return children.indexOf(aChild); // linear search
	}

	public void remove(MapperTreeNode aChild) {
		if (aChild == null) {
			throw new IllegalArgumentException("argument is null");
		}

		if (!isNodeChild(aChild)) {
			throw new IllegalArgumentException("argument is not a child");
		}
		remove(getIndex(aChild)); // linear search
	}

	public boolean isNodeAncestor(MapperTreeNode anotherNode) {
		if (anotherNode == null) {
			return false;
		}

		MapperTreeNode ancestor = this;

		do {
			if (ancestor == anotherNode) {
				return true;
			}
		} while ((ancestor = ancestor.getParent()) != null);

		return false;
	}

	public void insert(MapperTreeNode newChild, int childIndex) {
		if (bAttribute) {
			throw new IllegalStateException("node does not allow children");
		} else if (newChild == null) {
			throw new IllegalArgumentException("new child is null");
		} else if (isNodeAncestor(newChild)) {
			throw new IllegalArgumentException("new child is an ancestor");
		}

		MapperTreeNode oldParent = (MapperTreeNode) newChild.getParent();

		if (oldParent != null) {
			oldParent.remove(newChild);
		}
		newChild.setParent(this);
		if (children == null) {
			children = new Vector();
		}
		children.insertElementAt(newChild, childIndex);
	}

	public MapperTreeNode getParent() {
		return parent;
	}

	public int getChildCount() {
		if (children == null) {
			return 0;
		} else {
			return children.size();
		}
	}

	public void add(MapperTreeNode newChild) {
		if (newChild != null && newChild.getParent() == this)
			insert(newChild, getChildCount() - 1);
		else
			insert(newChild, getChildCount());
	}

	public void setAllowsChildren(boolean allows) {
		bAttribute = !allows;

	}

	public boolean isTextNode() {
		return bTextNode;
	}

	public void setValue(String value) {
		if (value != null && value.length() > 0) {
			if (!isTextNode() && null != textNode)
				textNode.setValue(value);
			else
				value = value.trim();
		}
	}

	public MapperTreeNode(String name, MapperTree mapperTree) {
		this(name, mapperTree, false);
	}

	private MapperTreeNode(MapperTree tree) {
		tree = tree;
		bTextNode = true;
		name = "text()";
		setAllowsChildren(false);
	}

	public MapperTreeNode(String name, MapperTree mapperTree, boolean bAttrib) {
		this.name = name;
		this.tree = mapperTree;

		if (null == tree)
			return;
		setAllowsChildren(!bAttrib);
		// Add text node
		if (!tree.isSinkTree() && !bAttrib) {
			textNode = new MapperTreeNode(tree);
			this.add(textNode);
		}

		addProperty("Name", name);
		addProperty("Value", value);

	}

	public MapperTree getTree() {
		return tree;
	}

	public void addProperty(String name, Object value) {
		properties.add(new ObjectProperty(this, name, value));
	}

	public String toString() {
		if (getShowValue()) {
			if (bAttribute)
				return name + "=\"" + value + "\"";
			else
				return name + " (" + value + ")";
		}

		return name;
	}

	private boolean getShowValue() {
		if (tree != null)
			return ((MapperTree) tree).getShowValues();
		else
			return false;
	}

	public Element render(PathList context, Element parent) {
		if (tree.isSinkTree()) {
			return renderOutput(context, parent);
		} else {
			Iterator iter = getLinks().iterator();

			while (iter.hasNext()) {
				LinkLine line = (LinkLine) iter.next();
				if (line.getSrc() == this) {
					Element newElem = line.render(context, parent);
					if (null != newElem)
						context.set(getFullPath());
					return newElem;
				}
			}

			return null;
		}
	}

	private Element renderOutput(PathList context, Element parent) {
		if (null == parent)
			return null;

		if (!hasToBeRendered())
			return parent;

		Document doc = parent.getOwnerDocument();
		String prevContext = context.toString();
		Element result = null;
		Element newElement = createOutputElement(doc);

		if (isLinked()) {
			ILinkable src = getSource();
			if (src instanceof MapperTreeNode) {
				MapperTreeNode node = (MapperTreeNode) src;
				if (!node.isLeaf())
					result = node.render(context, newElement);
			}
		}

		if (null == result)
			result = newElement;

		if (parent != result)
			parent.appendChild(result);
		renderOutputContents(context, result);

		for (Enumeration e = children(); e.hasMoreElements();) {
			MapperTreeNode child = (MapperTreeNode) e.nextElement();
			child.render(context, result);
		}

		context.set(prevContext);

		return result;
	}

	public String getName() {
		return name;
	}

	private Element createOutputElement(Document doc) {
		String parentName = getName();
		Element newElement = null;
		if (isLeaf()) {
			newElement = doc.createElement("xsl:attribute");
			newElement.setAttribute("name", parentName);
		} else {
			newElement = doc.createElement(parentName);
		}
		return newElement;
	}

	private void renderOutputContents(PathList context, Element parent) {
		if (isLinked()) {
			ILinkable src = getSource();
			if (src instanceof MapperTreeNode) {
				MapperTreeNode node = (MapperTreeNode) src;
				String srcPath = context.getRelativePath(node.getFullPath()); // Take
																				// in
																				// account
																				// parent
																				// xsl:for-each!
				if (node.isLeaf() && null != parent) {
					Document doc = parent.getOwnerDocument();
					Element tmp = doc.createElement("xsl:value-of");
					tmp.setAttribute("select", srcPath);
					parent.appendChild(tmp);
				}
			} else if (src.getParentObject() != null) {
				IMapObject parentObj = src.getParentObject();
				parentObj.render(context, parent);
			}
		} else {
			String val = getValue();
			if (val.length() > 0)
				parent.setTextContent(XMLUtils.escape(val));
		}
	}

	public boolean isLeaf() {
		return bAttribute;
	}

	public String getValue() {
		if (!isTextNode() && null != textNode)
			return textNode.getValue();
		else
			return value;
	}

	public boolean hasToBeRendered() {
		Enumeration e = this.children();
		while (e.hasMoreElements()) {
			MapperTreeNode child = (MapperTreeNode) e.nextElement();
			if (child.hasToBeRendered())
				return true;
		}
		return isLinked() || (this.getValue().trim() != "");
	}
}
