package com.mscc.schema;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.xmlbeans.impl.xsd2inst.SchemaInstanceGenerator;
import org.apache.xmlbeans.impl.xsd2inst.SampleXmlUtil;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.mscc.mapper.tree.MapperTree;
import com.mscc.mapper.tree.XSOMTreeModel;

import jlibs.xml.xsd.XSParser;

import org.apache.xerces.xs.*;

import jlibs.xml.xsd.XSInstance;
import jlibs.xml.sax.XMLDocument;



public class XSDToXML {

	public static void main(String[] args) throws IOException, TransformerException {
		//SchemaInstanceGenerator xmlGen = new SchemaInstanceGenerator();
		/*
		 * Generates a document based on the given Schema file
			having the given element as root.
			The tool makes reasonable attempts to create a valid document,
			but this is not always possible since, for example, 
			there are schemas for which no valid instance document 
			can be produced.
			Usage: xsd2inst [flags] schema.xsd -name element_name
			Flags:
			    -name    the name of the root element
			    -dl      enable network downloads for imports and includes
			    -nopvr   disable particle valid (restriction) rule
			    -noupa   diable unique particle attributeion rule
			    -license prints license information
		 */
//		String[] arguments = new String[7];
//		arguments[0] = "xsd2inst";
//		arguments[1] = "-name";
//		arguments[2] = "ADT_A01";
//		arguments[3] = "-dl";
//		arguments[4] = "-nopvr";
//		arguments[5] = "-noupa";
//		arguments[6] = "xsd2_3/ADT_A01.xsd";
//		xmlGen.main(arguments);
		//xmlGen.printUsage();
		
//		MapperTree mt = new MapperTree(true);
//		//mt.load("src.xsd");
//		mt.load("xsd2_3/ADT_A01.xsd");
//		
//		XMLOutputter outter=new XMLOutputter();
//		outter.setFormat(Format.getPrettyFormat());
//		outter.output(mt.getModel().toXml(), System.out);
		XSParser parser = new XSParser();
		XSModel xsModel = parser.parse("xsd2_3/ADT_A01.xsd");//, "xsd2_3/segments.xsd","xsd2_3/fields.xsd", "xsd2_3/datatypes.xsd");
		XSInstance xsInstance = new XSInstance();
		xsInstance.minimumElementsGenerated = 1;
		xsInstance.maximumElementsGenerated = 1;
		xsInstance.generateOptionalElements = Boolean.TRUE; // null means random
		xsInstance.generateDefaultAttributes =  Boolean.FALSE;
		xsInstance.generateDefaultElementValues =  Boolean.FALSE;
//		xsInstance.minimumElementsGenerated = 0;
//		xsInstance.maximumElementsGenerated = 0;
//		xsInstance.generateDefaultAttributes = false;
//		xsInstance.generateOptionalAttributes = false;
//		xsInstance.maximumRecursionDepth = 0;
//		xsInstance.generateOptionalElements = true;
		//xmlFileContent.replaceAll( "(?s)<!--.*?-->", "" );
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		QName rootElement = new QName("urn:hl7-org:v2xml","ADT_A01");
		XMLDocument sampleXml = new XMLDocument(new StreamResult(os), true, 4, null);
		xsInstance.generate(xsModel, rootElement, sampleXml);
		
		System.out.println("==========================");
		System.out.println(xsModel.getElementDeclaration("HD.1", "urn:hl7-org:v2xml").getName());
		System.out.println(xsModel.getElementDeclaration("HD.1", "urn:hl7-org:v2xml").getNillable());
		System.out.println(xsModel.getElementDeclaration("HD.1", "urn:hl7-org:v2xml").getConstraintType());
		System.out.println(xsModel.getElementDeclaration("HD.1", "urn:hl7-org:v2xml").getName());
		System.out.println(xsModel.getElementDeclaration("HD.1", "urn:hl7-org:v2xml").getName());
		System.out.println("==========================");

		
		String xmlString = new String(os.toByteArray());
		xmlString = xmlString.replaceAll( "(?s)<!--.*?-->", "" );
		xmlString = xmlString.replaceAll("(?m)^[ \t]*\r?\n", "");;
//		PrintWriter out = new PrintWriter("GeneratedXML-tst_nodep2.xml");
//		out.print(xmlString);
//		out.flush();
//		out.close();
		
		TransformerFactory factory3= TransformerFactory.newInstance();
        Source xslt3 = new StreamSource(new File("metaxslt/cleanRepTree.xslt"));
        Transformer transformer3 = factory3.newTransformer(xslt3);

        Source text3 = new StreamSource(IOUtils.toInputStream(xmlString, Charset.forName("UTF-8")));
        //transformer3.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer3.transform(text3, new StreamResult(new File("GeneratedXML-tst_nodep3.xml")));
		
	}
}
