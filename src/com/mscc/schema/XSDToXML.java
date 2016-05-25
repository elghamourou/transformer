package com.mscc.schema;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.namespace.QName;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamResult;

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

	public static void main(String[] args) throws IOException, TransformerConfigurationException {
		SchemaInstanceGenerator xmlGen = new SchemaInstanceGenerator();
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
		XSModel xsModel = new XSParser().parse("xsd2_3/ADT_A01.xsd", "xsd2_3/segments.xsd","xsd2_3/fields.xsd", "xsd2_3/datatypes.xsd");
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
		
		
		String xmlString = new String(os.toByteArray(),"UTF-8");
		xmlString = xmlString.replaceAll( "(?s)<!--.*?-->", "" );
		xmlString = xmlString.replaceAll("(?m)^[ \t]*\r?\n", "");;
		PrintWriter out = new PrintWriter("GeneratedXML.xml");
		out.print(xmlString);
		out.flush();
		out.close();
		
	}
}
