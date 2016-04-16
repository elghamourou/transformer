package xsd;

import java.util.List;


public class XSDTreeElement<T> {

	public enum Multiplicity {
	    ZERO, ONE, Multiple 
	}
	
	private String xpath = null;
	private Multiplicity minMultiplicity = null;
	private Multiplicity maxMultiplicity = null;
	
	private T data;
    public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	private XSDTreeElement<T> parent;
    private List<XSDTreeElement<T>> children;
	public List<XSDTreeElement<T>> getChildren() {
		return children;
	}
	public void setChildren(List<XSDTreeElement<T>> children) {
		this.children = children;
	}
}
