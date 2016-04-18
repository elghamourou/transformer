package simpleMapper;

import java.util.Enumeration;

import map.Map;

public class SimpleTstMain {
	
public static void showXpath(MapperTreeNode root){
		
		System.out.println(root.getXPath());
		Enumeration<MapperTreeNode> i = root.children();
		while(i.hasMoreElements()){
			showXpath(i.nextElement());
		}
		
	}

	public static void main(String[] args) {
		MapperTree src = new MapperTree(false);
		MapperTree dst = new MapperTree(true);
//		mt.load("src.xml");
//		mt.load("xsd2_3/My_ADT_A01.xsd");
		src.load("src.xml");
		dst.load("dst.xml");
		Map map = new Map(src, dst);
		
		showXpath(src.getRootNode());
		showXpath(dst.getRootNode());
		
//		MapperTreeNode nodeSrc = src.getNodeByXPath("/src.xsd/src.xsd/src/dataSrc/name");
//		MapperTreeNode nodeDst = dst.getNodeByXPath("/dst.xsd/dst.xsd/dst/dataDst/dstname");
//		map.addLink(nodeSrc, nodeDst);
//		
//		MapperTreeNode nodeSrc2 = src.getNodeByXPath("/src.xsd/src.xsd/src/dataSrc/desc");
//		MapperTreeNode nodeDst2 = dst.getNodeByXPath("/dst.xsd/dst.xsd/dst/dataDst/dstdesc");
//		map.addLink(nodeSrc2, nodeDst2);
//		
//		MapperTreeNode nodeSrc3 = src.getNodeByXPath("/src.xsd/src.xsd/src/content");
//		MapperTreeNode nodeDst3 = dst.getNodeByXPath("/dst.xsd/dst.xsd/dst/dstcnt");
//		map.addLink(nodeSrc3, nodeDst3);
		
		MapperTreeNode nodeSrc = src.getNodeByXPath("/src/dataSrc/name");
		MapperTreeNode nodeDst = dst.getNodeByXPath("/dst/dataDst/dstname");
		map.addLink(nodeSrc, nodeDst);
		
		MapperTreeNode nodeSrc2 = src.getNodeByXPath("/src/dataSrc/desc");
		MapperTreeNode nodeDst2 = dst.getNodeByXPath("/dst/dataDst/dstdesc");
		map.addLink(nodeSrc2, nodeDst2);
		
		MapperTreeNode nodeSrc3 = src.getNodeByXPath("/src/content");
		MapperTreeNode nodeDst3 = dst.getNodeByXPath("/dst/dstcnt");
		map.addLink(nodeSrc3, nodeDst3);
		
		
		
		XSLTRenderer xsltRenderer = new XSLTRenderer();
		xsltRenderer.render(map);
		System.out.println(xsltRenderer.getString());
		
	}
}
