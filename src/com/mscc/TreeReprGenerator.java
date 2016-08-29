package com.mscc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.xerces.xs.XSModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;



public class TreeReprGenerator {
	
	
	private static File generateTree(String root, String mappingSourceXSD) throws IOException, TransformerException{
		
		String NameSpace = "urn:hl7-org:v2xml";
		String mappingFolderPath = root;
		String rootFolder = "Trees";
		String mappingSourceTreeRepr = root+".XML";
		Path rootFolderPath = Paths.get(rootFolder);
		Path folderPath = rootFolderPath.resolve(root);
		File sourceTreeRepsFile = new File(folderPath.resolve(mappingSourceTreeRepr).toString());
		Path schemas = Paths.get(".");
		
		File mapping_folder = new File(rootFolderPath.resolve(mappingFolderPath).toString());
		if (!mapping_folder.exists()) {

			FileUtils.forceMkdir(mapping_folder);
		}										//?
		XSModel xsModel = new XSParser().parse(schemas.resolve(mappingSourceXSD).toString());//, "xsd2_3/segments.xsd","xsd2_3/fields.xsd", "xsd2_3/datatypes.xsd");
		XSInstance xsInstance = new XSInstance();
		xsInstance.minimumElementsGenerated = 1;
		xsInstance.maximumElementsGenerated = 1;
		xsInstance.generateOptionalElements = Boolean.TRUE; // null means random
		xsInstance.generateDefaultAttributes =  Boolean.FALSE;
		xsInstance.generateDefaultElementValues =  Boolean.FALSE;
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		QName rootElement = new QName(NameSpace, root );//"urn:hl7-org:v2xml","ADT_A01"
		XMLDocument sampleXml = new XMLDocument(new StreamResult(os), true, 4, null);
		xsInstance.generate(xsModel, rootElement, sampleXml);
		
		
		String xmlString = new String(os.toByteArray());
		xmlString = xmlString.replaceAll( "(?s)<!--.*?-->", "" );
		xmlString = xmlString.replaceAll("(?m)^[ \t]*\r?\n", "");;


		TransformerFactory factory= TransformerFactory.newInstance();
	    Source xslt = new StreamSource(new File("metaxslt/cleanRepTree.xslt"));
	    Transformer transformer = factory.newTransformer(xslt);

	    Source text = new StreamSource(IOUtils.toInputStream(xmlString, Charset.forName("UTF-8")));
	    //transformer3.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.transform(text, new StreamResult(sourceTreeRepsFile));
		
		return sourceTreeRepsFile;
		
	}
	
	private static void preloadingXML(File xmlFile, String root, String hl7_version) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException{
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		DocumentBuilder b = f.newDocumentBuilder();
		Document doc = b.parse(xmlFile);
		String namespace = "mapping";
		String prefix = "map";
		
		String message = root.split("_")[0];
		String trigger = root.split("_")[1];
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		Node node = (Node) xPath.compile("/"+root+"/MSH/MSH.1").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "cte");
			child.setPrefix(prefix);
			child.setAttribute("val", "|");
			node.appendChild(child);
			}
		
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node) xPath.compile("/"+root+"/MSH/MSH.2").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "cte");
			child.setPrefix(prefix);
			child.setAttribute("val", "^~\\&");
			node.appendChild(child);
			}
		
		
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node) xPath.compile("/"+root+"/MSH/MSH.9/CM_MSG.1").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "cte");
			child.setPrefix(prefix);
			child.setAttribute("val", message );
			node.appendChild(child);
			}
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node) xPath.compile("/"+root+"/MSH/MSH.9/CM_MSG.2").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "cte");
			child.setPrefix(prefix);
			child.setAttribute("val", trigger );
			node.appendChild(child);
			}
		
		
		
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node) xPath.compile("/"+root+"/MSH/MSH.10").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "uuid");
			child.setPrefix(prefix);
			node.appendChild(child);
			}
		
		
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node) xPath.compile("/"+root+"/MSH/MSH.12").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "cte");
			child.setPrefix(prefix);
			child.setAttribute("val", hl7_version );
			node.appendChild(child);
			}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		xPath = XPathFactory.newInstance().newXPath();
		node = (Node) xPath.compile("/"+root+"/OBX/OBX.2").evaluate(doc, XPathConstants.NODE);
		if(node!=null){
			Element child = doc.createElementNS(namespace, "cte");
			child.setPrefix(prefix);
			child.setAttribute("val", "ST" );
			node.appendChild(child);
			}
		
		
		
		
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		tf.setOutputProperty(OutputKeys.METHOD, "xml");
		tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		DOMSource domSource = new DOMSource(doc);
		StreamResult sr = new StreamResult(xmlFile);
		tf.transform(domSource, sr);
		
		
		
		
		
		
		
	}
	
	
	public static void main(String[] args) throws TransformerException, IOException, XPathExpressionException, ParserConfigurationException, SAXException, TransformerFactoryConfigurationError {
	
	String[] exts = {"xsd"};
	Collection<File> files = FileUtils.listFiles(new File("xsd2_3"), exts, false);	
	
	String root;
	String mappingSourceXSD;
	File xmlFile;
	String message;
	String trigger= "";
	
	
	for(File f:files){
		String name = f.getName();
		if(name.substring(0, 3).toUpperCase().equals(name.substring(0, 3))){
			
			root = name.substring(0, name.length()-4);
			mappingSourceXSD = "xsd2_3/"+name;
			message = root.split("_")[0];
			if(root.split("_").length>1){
				trigger = root.split("_")[1];
				System.out.println(root+"----"+mappingSourceXSD+"("+message+","+trigger+")");
				xmlFile = generateTree(root, mappingSourceXSD);
				System.out.println(xmlFile.getName());
				preloadingXML(xmlFile, root, "2.3");
			}
				
			
		}
			
		
	}
		
		
		
	
	
	
	
	
	
	
	//,,
	
	
	
	
}
}
