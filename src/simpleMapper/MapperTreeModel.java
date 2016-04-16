package simpleMapper;





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
        return ((MapperTreeNode)parent).getChildCount();
    }
	
	public Object getChild(Object parent, int index) {
        return ((MapperTreeNode)parent).getChildAt(index);
    }
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getTreeText(this, this.getRoot(), "");
	}
	
	
	private static String getTreeText(MapperTreeModel model, Object object, String indent) {
	    String myRow = indent + object + "\n";
	    for (int i = 0; i < model.getChildCount(object); i++) {
	        myRow += getTreeText(model, model.getChild(object, i), indent + "  ");
	    }
	    return myRow;
	}
	
	
	
}
