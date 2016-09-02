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
import com.mscc.transformer.engine.exceptions.TransformationNotSupported;

public class Demo2 {

	
	public static void main(String[] args) throws IOException, MappingProjectException, XmlException, TransformerException, ArchiveException, InterruptedException, XPathExpressionException, ParserConfigurationException, SAXException, TransformerFactoryConfigurationError, TransformationNotSupported {
		
		
		

		
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
		 * enhance the transform message from engine to handle unsuported and exception and multiple(default) versions
		 * */
		
		//Demo 1
		
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
		
		//._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._
		
		//demo 2
		//Create Mapping Project
		createMappingProject("hl7_to_hdps", "1.0", "projects");
		
		
		//Create a mapping into active project
		createMapping2("ADT_A01", "HDPS_A01", "1.0", xmlFiles, "hdps");
		
		//Create, edit and import mappingFile into active mapping
		createMappingFile2("demo/mapping/HL7ToHdps-mapping.xml");
		
		//test mapping
		testMappingRev("demo/mapping/HL7ToHdps-mapping.xml", "demo/mapping/hl7.xml", "demo/mapping/hdps2.xml");
		testMappingRev2("demo/mapping/HL7ToHdps-mapping.xml", "demo/mapping/hl7.xml");
		
		//build plugin
		buildPlugin("project/build", true, "HL7_TO_HDPS");
		
		//deploy the plugin
		deployPlugin("project/build", "HL7_TO_HDPS", "plugins");
		
		
		//getting all supported transformations
		System.out.println(getSupportedTransformations());
		
		//calling transformation engine with an example
		String hl7Message = IOUtils.toString(new FileReader("demo/mapping/hl7.xml"));
		System.out.println(transform("HL7_TO_HDPS", "ADT_A01", "HDPS_A01", "1.0", hl7Message));
		
		
		
		
		
		
		
	}
	
private static void testMappingRev2(String mapping_file_name, String dataSource) throws TransformerException{
		
		String transormer_xslt_output = "demo/mapping/hl7ToHdps.xslt";
		System.out.println(MetaXSLTTransformer.transormmation(dataSource, transormer_xslt_output));
	}
	
	private static void testMappingRev(String mapping_file_name, String dataSource, String transformedOutput) throws TransformerException{
		
		String transormer_xslt_output = "demo/mapping/hl7ToHdps.xslt";
		MetaXSLTTransformer.transormer_generator(mapping_file_name, transormer_xslt_output);
		MetaXSLTTransformer.demo_transormmation(dataSource, transormer_xslt_output, transformedOutput);
	}
	
private static void createMappingFile2(String mappingFileName) throws IOException, ParserConfigurationException, SAXException, TransformerFactoryConfigurationError, TransformerException, XPathExpressionException{
		
		File mappingFile = new File(mappingFileName);
		FileUtils.copyFile(MapperProjectHandler.getActiveProjectHandler().getActiveMapping().getDestinationTreeRepsFile(), mappingFile);
		
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		DocumentBuilder b = f.newDocumentBuilder();
		Document doc = b.parse(mappingFile);
		String namespace = "mapping";
		String prefix = "map";
		String root = "ADT_A01";
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		Node node = (Node)(Node) xPath.compile("/hdps/uid").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "/"+root+"/MSH/MSH.3/HD.1");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/pid/id").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path","/"+root+"/PID/PID.1" );
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/pid/code").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path","/"+root+"/PID/PID.3/CX.1" );
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/pid/lastName").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path","/"+root+"/PID/PID.5/XPN.1" );
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/pid/firstName").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path","/"+root+"/PID/PID.5/XPN.2" );
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/pid/birthDate").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path","/"+root+"/PID/PID.7/TS.1" );
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/pid/gender").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path","/"+root+"/PID/PID.8" );
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/pid/address").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path","/"+root+"/PID/PID.11/XAD.1" );
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/pid/address").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path","/"+root+"/PID/PID.11/XAD.3" );
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/pid/homePhone").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path","/"+root+"/PID/PID.13/XTN.1" );
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/pid/maritalStatus").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path","/"+root+"/PID/PID.16" );
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/pid/deceasedDate").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path","/"+root+"/PID/PID.29/TS.1" );
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/obx/id").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "./OBX.1");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/obx/resultUid").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path", "./OBX.3/CE.2");
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/obx/valueReference").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path","./OBX.5" );
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/obx/unitLabel").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path","./OBX.6/CE.2" );
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/obx/examDate").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path","./OBX.14/TS.1" );
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node) xPath.compile("/hdps/obx").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element parent = doc.createElementNS(namespace, "list");
			parent.setAttribute("path", "/"+root+"/OBX");
			parent.setPrefix(prefix);
			Node grandParent = node.getParentNode();
			
			
			node = grandParent.removeChild(node);
			parent.appendChild(node);
			grandParent.appendChild(parent);
			
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/al/id").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path","./AL1.1" );
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/al/label").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path","./AL1.3/CE.2" );
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/al/severity").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path","./AL1.4" );
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node)(Node) xPath.compile("/hdps/al/diagnosticDate").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "value");
			child.setPrefix(prefix);
			child.setAttribute("path","./AL1.6" );
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node) xPath.compile("/hdps/al").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element parent = doc.createElementNS(namespace, "list");
			parent.setAttribute("path", "/"+root+"/AL1");
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
	private static void createMapping2(String mappingSource, String mappingdestination, String mappingVersion,
			List<String> xmlFiles, String xmlRoot) throws IOException, XmlException, TransformerException, XPathExpressionException, ParserConfigurationException, SAXException, TransformerFactoryConfigurationError{

		MappingBean mapping1 = new MappingBean();
		mapping1.setMappingDestination(mappingdestination);
		mapping1.setMappingVersion(mappingVersion);
		mapping1.setMappingSource(mappingSource);
		
		
		MapperProjectHandler.getActiveProjectHandler().createMapping(mapping1);
		
		
		MapperProjectHandler.getActiveProjectHandler().importDestinationXSDFromXML(xmlFiles,xmlRoot, null);
		MapperProjectHandler.getActiveProjectHandler().importSourceXSD("xsd2_3/"+mappingSource+".xsd",mappingSource,"urn:hl7-org:v2xml", "xsd2_3/segments.xsd","xsd2_3/fields.xsd", "xsd2_3/datatypes.xsd");

}
	
	private static String transform(String name, 
			String source, 
			String destination,
			String verssion,
			String message) throws TransformerException, TransformationNotSupported{
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
