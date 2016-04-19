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
			treeRoot.setTree(this);
		}
	}
	private void setModel(MapperTreeModel newModel) {

        treeModel = newModel;
       //System.out.println(treeModel);
		
	}
	
	
	
	
	
	
	public MapperTreeModel getModel() {
        return treeModel;
    }
	public MapperTreeNode getNodeByXPath(String path) {
		//MapperTreeNode tmp = _treeRoot;
				MapperTreeNode tmp = (MapperTreeNode)this.getModel().getRoot();//.getRootNode();

				// Scroll through the XPath
				PathList pl = new PathList(path);
				Iterator iter = pl.iterator();
				int tmpNamelevel = 0;
				int tmplevel = 0;
				while ( iter.hasNext() ) {
					String tmpName = (String)iter.next();
					if(tmpName.length()>0) {
						//String s = tmp.getXPathName();
						
						
						
						
						
						if(tmplevel==tmpNamelevel){
							if(!tmpName.equals(tmp.getXPathName())){
								tmp = null;
								break;
							}else {
								tmpNamelevel++;
								continue;
							}
							
						}else{
							MapperTreeNode child =  tmp.getChildByName(tmpName);
							if(null != child){
								if(tmpName.equals(child.getXPathName())){
								
									tmp = child;
									tmplevel++;
									tmpNamelevel++;
									continue;
							}else
								{
									tmp = null;
									break;
								}
							}else{
								tmp = null;
								break;
							}
						}
						
						
						
						
						
						
						
						
						
//						if(tmpName.equals(tmp.getXPathName()) && tmplevel==tmpNamelevel){
//							tmpNamelevel++;
//							continue;
//						}else if(tmpName.equals(tmp.getXPathName()) && tmplevel!=tmpNamelevel){
//							child = tmp.getChildByName(tmpName);
//							tmplevel++;
//							if(null != child){
//								tmp = child;
//								tmplevel++;
//								continue;
//							}
//							else
//							{
//								tmp = null;
//								break;
//							}
//						}else if(!tmpName.equals(tmp.getXPathName()) && tmplevel==tmpNamelevel){
//							tmp = null;
//							break;
//						}
						
//						if(!tmpName.equals(tmp.getXPathName()) && tmplevel!=tmpNamelevel) {
//							child = tmp.getChildByName(tmpName);
//							tmplevel++;
//							if(null != child)
//								tmp = child;
//							else
//							{
//								tmp = null;
//								break;
//							}
//						}else
//						{
//							tmp = null;
//							break;
//						}
					}
					tmpNamelevel++;
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
