package testRun;

import java.util.Enumeration;

import com.mscc.mapper.map.Map;
import com.mscc.mapper.renderer.XSLTRenderer;
import com.mscc.mapper.tree.MapperTree;
import com.mscc.mapper.tree.MapperTreeNode;

public class SimpleTstMain {
	
public static void showXpath(MapperTreeNode root){
		
		System.out.println(root.getXPath());
		Enumeration<MapperTreeNode> i = root.children();
		while(i.hasMoreElements()){
			showXpath(i.nextElement());
		}
		
	}

	private static void testxsd(){
		MapperTree src = new MapperTree(false);
		MapperTree dst = new MapperTree(true);
		src.load("src.xsd");
		dst.load("dst.xsd");
		Map map = new Map(src, dst);
		
		showXpath(src.getRootNode());
		showXpath(dst.getRootNode());
		
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


	private static void testxml(){
		MapperTree src = new MapperTree(false);
		MapperTree dst = new MapperTree(true);
		src.load("src.xml");
		dst.load("dst.xml");
		Map map = new Map(src, dst);
		
		showXpath(src.getRootNode());
		showXpath(dst.getRootNode());
		
		
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

	public static void main(String[] args) {
		
		testxsd();
		testxml();
	}
}
