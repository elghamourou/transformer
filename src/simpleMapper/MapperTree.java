package simpleMapper;

import java.util.Hashtable;
import java.util.Iterator;








import java.util.Stack;


import map.ILinkable;



public class MapperTree {
	
	private boolean sink;
	private MapperTreeNode	treeRoot	= null;
	String uri = "";
	transient protected MapperTreeModel        treeModel;
	private boolean editable;
	private boolean			showValues	= false;
	
	
	public boolean getShowValues()	{ return showValues; }
	
	public MapperTree(boolean sink)
	{
		this(new MapperTreeNode("Root", null));



		this.treeRoot = (MapperTreeNode)this.getModel().getRoot();

		treeRoot.setTree(this);


		this.sink = sink;

		this.editable = sink; // Only output (sink) tree is editable
	}
	
	
	
	
	
	
	
	
	public MapperTree(MapperTreeNode mapperTreeNode) {
        setModel(new MapperTreeModel(mapperTreeNode));
	}

	public boolean isSinkTree(){
		return this.sink;
	}
	
	public void load(String uri){
		if(null == uri)
			return;

		MapperTreeNode root = new MapperTreeNode(uri, this);

		MapperTreeModel newModel = null;
		this.uri = uri;
		int len = uri.length();
		int x = uri.lastIndexOf('.')+1;
		String ext = uri.substring(x, len);
		
		if(ext.equals("xml")) {
			newModel = new SAXTreeModel(root);
			
		} else if(ext.equals("xsd")) {
			newModel = new XSOMTreeModel(root);
			
		} else if(ext.equals("htm") || ext.equals("html")) {
			//newModel = new HTMLTreeModel(root);
			
		}
		
		if(newModel!=null){
			newModel.load(uri);
			this.setModel(newModel);
			this.treeRoot = (MapperTreeNode)this.getModel().getRoot();
		}
	}
	private void setModel(MapperTreeModel newModel) {

        treeModel = newModel;
       System.out.println(treeModel);
		
	}
	
	
	
	
	
	
	public MapperTreeModel getModel() {
        return treeModel;
    }
	public MapperTreeNode getNodeByXPath(String path) {
		//MapperTreeNode tmp = _treeRoot;
				MapperTreeNode tmp = (MapperTreeNode)this.getModel().getRoot();

				// Scroll through the XPath
				PathList pl = new PathList(path);
				Iterator iter = pl.iterator();
				while ( iter.hasNext() ) {
					String tmpName = (String)iter.next();
					//tmpName = tmpName.replaceAll("[1]", "");
					//tmpName = tmpName.replaceAll("\x5b1\x5d", "");
					//tmpName = tmpName.replaceAll("/[1/]", "");
					if(tmpName.length()>0) {
						//String s = tmp.getXPathName();
						if(!tmpName.equals(tmp.getXPathName())) {
							MapperTreeNode child = tmp.getChildByName(tmpName);
							if(null != child)
								tmp = child;
							else
							{
								tmp = null;
								break;
							}
						}
					}
				}

				return tmp;
	}

	 public String getURI()									{ return uri; }
	 public MapperTreeNode getRootNode()	{ return treeRoot; }
	 
	 
	 
	 
		public void setRoot(String path)
		{
			MapperTreeNode node = getNodeByXPath(path);
			if(null != node)
				setRoot(node);
		}
		
		public void setRoot(MapperTreeNode node)
		{
			MapperTreeModel tm = (MapperTreeModel)this.getModel();
			tm.setRoot(node);
	     	node.setParent(null);
		}
		
}
