package com.mscc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.xmlbeans.XmlException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.mscc.mapper.project.MapperProjectHandler;
import com.mscc.mapper.project.MappingBean;
import com.mscc.mapper.project.MappingProjectException;
import com.mscc.mapper.project.ProjectBean;
import com.mscc.metaxslt.MetaXSLTTransformer;
import com.mscc.transformer.engine.Transformation;
import com.mscc.transformer.engine.TransformerEngine;

public class PlayGround {

	
	public static void main(String[] args) throws IOException, MappingProjectException, XmlException, TransformerException, ArchiveException, InterruptedException, XPathExpressionException, ParserConfigurationException, SAXException, TransformerFactoryConfigurationError {
		
		
		
/*		
		
		ProjectBean project = new ProjectBean();
		
		project.setMappingProjectName("hdps_to_hl7");
		project.setMappingProjectVersion("1.0");
		project.setMappingProjectRoot("project");
		
		MapperProjectHandler.createProject(project);
		
		
		MappingBean mapping1 = new MappingBean();
		
		mapping1.setMappingDestination("ADT_A01");
		mapping1.setMappingVersion("1.0");
		mapping1.setMappingSource("HDPS_A01");
		
		List<String> xmlFiles1 = new ArrayList<String>();
		xmlFiles1.add("demo/mapping/hdps.xml");
		MapperProjectHandler.getActiveProjectHandler().createMapping(mapping1);
		MapperProjectHandler.getActiveProjectHandler().importSourceXSDFromXML(xmlFiles1,"hdps", null);
		MapperProjectHandler.getActiveProjectHandler().importDestinationXSD("xsd2_3/ADT_A01.xsd","ADT_A01","urn:hl7-org:v2xml", "xsd2_3/segments.xsd","xsd2_3/fields.xsd", "xsd2_3/datatypes.xsd");
		MapperProjectHandler.getActiveProjectHandler().importMappingFile("demo/mapping/hdpsToHL7-mappingL.xml");
		
		
		
		
		MappingBean mapping2 = new MappingBean();
		
		mapping2.setMappingDestination("ADT_A01");
		mapping2.setMappingVersion("2.0");
		mapping2.setMappingSource("HDPS_A01");
		
		List<String> xmlFiles2 = new ArrayList<String>();
		xmlFiles2.add("demo/mapping/hdps.xml");
		MapperProjectHandler.getActiveProjectHandler().createMapping(mapping2);
		MapperProjectHandler.getActiveProjectHandler().importSourceXSDFromXML(xmlFiles2,"hdps", null);
		MapperProjectHandler.getActiveProjectHandler().importDestinationXSD("xsd2_3/ADT_A01.xsd","ADT_A01","urn:hl7-org:v2xml", "xsd2_3/segments.xsd","xsd2_3/fields.xsd", "xsd2_3/datatypes.xsd");
		MapperProjectHandler.getActiveProjectHandler().importMappingFile("demo/mapping/hdpsToHL7-mappingL.xml");
		
		
		
		
		MappingBean mapping3 = new MappingBean();
		
		mapping3.setMappingDestination("ADT_R01");
		mapping3.setMappingVersion("0.9");
		mapping3.setMappingSource("HDPS_R01");
		
		List<String> xmlFiles3 = new ArrayList<String>();
		xmlFiles3.add("demo/mapping/hdps.xml");
		MapperProjectHandler.getActiveProjectHandler().createMapping(mapping3);
		MapperProjectHandler.getActiveProjectHandler().importSourceXSDFromXML(xmlFiles3,"hdps", null);
		MapperProjectHandler.getActiveProjectHandler().importDestinationXSD("xsd2_3/ADT_A01.xsd","ADT_A01","urn:hl7-org:v2xml", "xsd2_3/segments.xsd","xsd2_3/fields.xsd", "xsd2_3/datatypes.xsd");
		MapperProjectHandler.getActiveProjectHandler().importMappingFile("demo/mapping/hdpsToHL7-mappingL.xml");
			
		
		
//		MappingBean mapping2 = new MappingBean();
//		
//		mapping2.setMappingDestination("ADT_R01");
//		mapping2.setMappingVersion("2.5");
//		mapping2.setMappingSource("HDPS_MSG_TYPE_24");
//		
//		List<String> jsonFiles = new ArrayList<String>();
//		jsonFiles.add("demo/demo.hdps");
//		MapperProjectHandler.getActiveProjectHandler().createMapping(mapping2);
//		MapperProjectHandler.getActiveProjectHandler().importSourceXSDFromJson(jsonFiles, "hdps_R01",null);
//		MapperProjectHandler.getActiveProjectHandler().importDestinationXSD("xsd2_3/ADT_A01.xsd","ADT_A01","urn:hl7-org:v2xml", "xsd2_3/segments.xsd","xsd2_3/fields.xsd", "xsd2_3/datatypes.xsd");
		
		MapperProjectHandler.importProject("M:/project/transformer/project/hdps_to_hl7", false);
		System.out.println(MapperProjectHandler.getActiveProjectHandler());
		MapperProjectHandler.getActiveProjectHandler().build("project/build", true, "HDPS_TO_HL7");
		
		
		//ZipUtil.decompress("project/build/HDPS_TO_HL7.zip", "project/build/HDPS_TO_HL7-decomp" );
        
        
 */       
		//SupportedTransformationTree stt  = new SupportedTransformationTree();
//		PluginFolderMonitor.startMonitor();
//		
//		Thread.currentThread().sleep(60000);
//		
//		System.out.println("Stopiing the monit");
//		PluginFolderMonitor.stopMonitor();
		


//		TransformationTreeModel ttm = new TransformationTreeModel();
//		
//		ttm.addTransformation2("transformationName1", "srcMsg1", "dstMsg1", "version1");
//		ttm.addTransformation2("transformationName1", "srcMsg2", "dstMsg2", "version1");
//		ttm.addTransformation2("transformationName1", "srcMsg2", "dstMsg2", "version2");
//		ttm.addTransformation2("transformationName1", "srcMsg2", "dstMsg2", "version3");
//		ttm.addTransformation2("transformationName1", "srcMsg3", "dstMsg3", "version3");
//		ttm.addTransformation2("transformationName1", "srcMsg1", "dstMsg1", "version1");
//		
//		ttm.addTransformation2("transformationName2", "srcMsg1", "dstMsg1", "version1");
//		ttm.addTransformation2("transformationName2", "srcMsg1", "dstMsg1", "version2");
//		ttm.addTransformation2("transformationName2", "srcMsg1", "dstMsg2", "version1");
//		
//		System.out.println(ttm);
//		ttm.removeTransformation("transformationName1");
//		ttm.addTransformation2("transformationName1", "srcMsg1", "dstMsg1", "version1");
//		ttm.addTransformation2("transformationName2", "srcMsg1", "dstMsg2", "version2");
//		System.out.println(ttm);
//		
//		System.out.println(ttm.getTransformationXslt("transformationName2", "srcMsg1", "dstMsg2", "version2"));
//		System.out.println(ttm.getTransformationXslt("transformationName2", "srcMsg1", "dstMsg2", "version3"));
//		
//		File plugs= Paths.get("./plugins").toFile();
//		for(String plug:plugs.list(DirectoryFileFilter.INSTANCE)){
//			File srcs = Paths.get("./plugins").resolve(plug).toFile();
//			for(String src:srcs.list(DirectoryFileFilter.INSTANCE)){
//				File dsts = Paths.get("./plugins").resolve(plug).resolve(src).toFile();
//				for(String dst:dsts.list(DirectoryFileFilter.INSTANCE)){
//					File vers = Paths.get("./plugins").resolve(plug).resolve(src).resolve(dst).toFile();
//					for(String ver:vers.list(DirectoryFileFilter.INSTANCE)){
//						System.out.println(plug+"  "+src+"  "+dst+"  "+ver);
//					}
//					
//				}
//				
//			}
//		}
//		
//		
//		System.out.println("_______________________________");
//		
//		String plugin_name = "HDPS_TO_HL7";
//		File srcs = Paths.get("./plugins").resolve(plugin_name).toFile();
//		for(String src:srcs.list(DirectoryFileFilter.INSTANCE)){
//			File dsts = Paths.get("./plugins").resolve(plugin_name).resolve(src).toFile();
//			for(String dst:dsts.list(DirectoryFileFilter.INSTANCE)){
//				File vers = Paths.get("./plugins").resolve(plugin_name).resolve(src).resolve(dst).toFile();
//				for(String ver:vers.list(DirectoryFileFilter.INSTANCE)){
//					System.out.println(plugin_name+"  "+src+"  "+dst+"  "+ver);
//				}
//				
//			}
//			
//		}
		
		TransformerEngine.startTransformationEngine();
		
		
		/*
		 * create a demo of the whole application workflow
		 * 
		 * CreateMappingProject
		 * CreateMappingForHDPSJSon (hdps jsons as source ampping, load simple xml as destination)
		 * EditMappingManually (Edit it manually or using method like preloading[better])
		 * TestMapping (a method that test mapping on an example for confirmation)
		 * BuildPlugins
		 * DeployPlugins
		 * CallTransformationEngin (test on messages)
		 * --> enhance the transform message from engine to handle unsuported and exception and multiple(default) versions
		 * */
		
		//Create Mapping Project
		createMappingProject("hdps_to_hl7", "1.0", "projects");
		
		//Create a mapping into active project
		List<String> xmlFiles = new ArrayList<String>();
		xmlFiles.add("demo/mapping/hdps.xml");
		createMapping("HDPS_A01", "ADT_A01", "1.0", xmlFiles, "hdps");
		
		//Create, edit and import mappingFile into active mapping
		createMappingFile("demo/mapping/hdpsToHL7-mapping.xml");
		
		//test mapping
		testMapping("demo/mapping/hdpsToHL7-mapping.xml", "demo/mapping/hdps.xml", "demo/mapping/hl7.xml");
		testMapping2("demo/mapping/hdpsToHL7-mapping.xml", "demo/mapping/hdps.xml");
		
		//build plugin
		buildPlugin("project/build", true, "HDPS_TO_HL7");
		
		//deploy the plugin
		deployPlugin("project/build", "HDPS_TO_HL7", "plugins");
		
		
		//getting all supported transformations
		System.out.println(getSupportedTransformations());
		
		//calling transformation engine with an example
		String hdpsMessage = IOUtils.toString(new FileReader("demo/mapping/hdps.xml"));
		System.out.println(transform("HDPS_TO_HL7", "HDPS_A01", "ADT_A01", "1.0", hdpsMessage));
		
	}
	
	
	private static String transform(String name, 
			String source, 
			String destination,
			String verssion,
			String message) throws TransformerException{
		return TransformerEngine.transformMessage(name, source, destination, verssion, message);
	}
	
	private static List<Transformation> getSupportedTransformations(){
		return TransformerEngine.getSupportedTransformation();
	}
	
	private static void deployPlugin(String pluginsSourceFolder, String pluginName,
							String pluginsDeployFolder) throws IOException{
		Path pluginFolderPath = Paths.get(pluginsSourceFolder, pluginName);
		
		FileUtils.copyDirectoryToDirectory(pluginFolderPath.toFile() , new File(pluginsDeployFolder));
		
	}
	
	private static void buildPlugin(String buildFolder, boolean isPackaged, String mappingName) throws IOException, TransformerException, ArchiveException{
		MapperProjectHandler.getActiveProjectHandler().build(buildFolder, isPackaged, mappingName);
	}
	
private static void testMapping2(String mapping_file_name, String dataSource) throws TransformerException{
		
		String transormer_xslt_output = "demo/mapping/hdpsToHL7.xslt";
		System.out.println(MetaXSLTTransformer.transormmation(dataSource, transormer_xslt_output));
	}
	
	private static void testMapping(String mapping_file_name, String dataSource, String transformedOutput) throws TransformerException{
		
		String transormer_xslt_output = "demo/mapping/hdpsToHL7.xslt";
		MetaXSLTTransformer.transormer_generator(mapping_file_name, transormer_xslt_output);
		MetaXSLTTransformer.demo_transormmation(dataSource, transormer_xslt_output, transformedOutput);
	}
	
	private static void createMappingFile(String mappingFileName) throws IOException, ParserConfigurationException, SAXException, TransformerFactoryConfigurationError, TransformerException, XPathExpressionException{
		
		File mappingFile = new File(mappingFileName);
		FileUtils.copyFile(MapperProjectHandler.getActiveProjectHandler().getActiveMapping().getDestinationTreeRepsFile(), mappingFile);
		
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		DocumentBuilder b = f.newDocumentBuilder();
		Document doc = b.parse(mappingFile);
		String namespace = "mapping";
		String prefix = "map";
		String root = "ADT_A01";
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		Node node = (Node)(Node) xPath.compile("/"+root+"/MSH/MSH.3/HD.1").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "/hdps/uid");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/PID/PID.1").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "/hdps/pid/id");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/PID/PID.3/CX.1").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "/hdps/pid/code");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/PID/PID.5/XPN.1").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "/hdps/pid/lastName");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/PID/PID.5/XPN.2").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "/hdps/pid/firstName");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/PID/PID.7/TS.1").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "/hdps/pid/birthDate");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/PID/PID.8").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "/hdps/pid/gender");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/PID/PID.11/XAD.1").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "/hdps/pid/address");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/PID/PID.11/XAD.3").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "/hdps/pid/address");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/PID/PID.13/XTN.1").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "/hdps/pid/homePhone");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/PID/PID.16").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "/hdps/pid/maritalStatus");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/PID/PID.29/TS.1").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "/hdps/pid/deceasedDate");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/OBX/OBX.1").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "./id");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/OBX/OBX.3/CE.2").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "./resultUid");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/OBX/OBX.5").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "./valueReference");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/OBX/OBX.6/CE.2").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "./unitLabel");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/OBX/OBX.14/TS.1").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "./examDate");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node) xPath.compile("/"+root+"/OBX").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element parent = doc.createElementNS(namespace, "list");
			parent.setAttribute("path", "/hdps/obx");
			parent.setPrefix(prefix);
			Node grandParent = node.getParentNode();
			
			
			node = grandParent.removeChild(node);
			parent.appendChild(node);
			grandParent.appendChild(parent);
			
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/AL1/AL1.1").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "./id");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/AL1/AL1.3/CE.2").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "./label");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/AL1/AL1.4").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "./severity");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/"+root+"/AL1/AL1.6").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "./diagnosticDate");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node) xPath.compile("/"+root+"/AL1").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element parent = doc.createElementNS(namespace, "list");
			parent.setAttribute("path", "/hdps/al");
			parent.setPrefix(prefix);
			Node grandParent = node.getParentNode();
			
			
			node = grandParent.removeChild(node);
			parent.appendChild(node);
			grandParent.appendChild(parent);
			
			}
		
		
		
		
		
		
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		tf.setOutputProperty(OutputKeys.METHOD, "xml");
		tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		DOMSource domSource = new DOMSource(doc);
		StreamResult sr = new StreamResult(mappingFile);
		tf.transform(domSource, sr);

		
		
		
		MapperProjectHandler.getActiveProjectHandler().importMappingFile(mappingFile);
	}
	
	private static void createMapping(String mappingSource, String mappingdestination, String mappingVersion,
										List<String> xmlFiles, String xmlRoot) throws IOException, XmlException, TransformerException, XPathExpressionException, ParserConfigurationException, SAXException, TransformerFactoryConfigurationError{

		MappingBean mapping1 = new MappingBean();
		mapping1.setMappingDestination(mappingdestination);
		mapping1.setMappingVersion(mappingVersion);
		mapping1.setMappingSource(mappingSource);
		
		
		MapperProjectHandler.getActiveProjectHandler().createMapping(mapping1);
		
		
		MapperProjectHandler.getActiveProjectHandler().importSourceXSDFromXML(xmlFiles,xmlRoot, null);
		MapperProjectHandler.getActiveProjectHandler().importDestinationXSD("xsd2_3/"+mappingdestination+".xsd",mappingdestination,"urn:hl7-org:v2xml", "xsd2_3/segments.xsd","xsd2_3/fields.xsd", "xsd2_3/datatypes.xsd");
		
	}
	

	private static void createMappingProject(String projectName, String projectVersion, String projectRoot) throws IOException{
		ProjectBean project = new ProjectBean();
		
		project.setMappingProjectName(projectName);
		project.setMappingProjectVersion(projectVersion);
		project.setMappingProjectRoot(projectRoot);
		
		MapperProjectHandler.createProject(project);
	}
	
}
