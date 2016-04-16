package xsd;

import java.util.ArrayList;

public class XSDTree<T> {

	 private XSDTreeElement<T> root;

	    public XSDTree(T rootData) {
	        root = new XSDTreeElement<T>();
	        root.setData(rootData);
	        root.setChildren(new ArrayList<XSDTreeElement<T>>());
	    }

	   
	
	public void addXSDTreeElement(XSDTreeElement<T> element, XSDTreeElement<T> parentElement ){
		
		
		
	}
}
