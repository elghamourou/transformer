package simpleMapper;

import map.Map;

public class SimpleTstMain {

	public static void main(String[] args) {
		MapperTree src = new MapperTree(false);
		MapperTree dst = new MapperTree(true);
//		mt.load("src.xml");
//		mt.load("xsd2_3/My_ADT_A01.xsd");
		src.load("src.xml");
		dst.load("dst.xml");
		
		MapperTreeNode nodeSrc = src.getNodeByXPath("/src/dataSrc/name");
		MapperTreeNode nodeDst = dst.getNodeByXPath("/dst/dataDst/dstname");
		
		
		
		Map map = new Map(src, dst);
		map.addLink(nodeSrc, nodeDst);
		
		
		
		XSLTRenderer xsltRenderer = new XSLTRenderer();
		xsltRenderer.render(map);
		System.out.println(xsltRenderer.getString());
		
	}
}
