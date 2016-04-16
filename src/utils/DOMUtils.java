/*********************************************************************\
*                                                                     *
*         Jamper - Java XML mapper (visual XSLT generator)            *
*                Copyright (C) 2005 Filip Pavlovic                    *
*                    sqba@users.sourceforge.net                       *
*                                                                     *
*  This program is free software; you can redistribute it and/or      *
*  modify it under the terms of the GNU General Public License        *
*  as published by the Free Software Foundation; either version 2     *
*  of the License, or (at your option) any later version.             *
*                                                                     *
*  This program is distributed in the hope that it will be useful,    *
*  but WITHOUT ANY WARRANTY; without even the implied warranty of     *
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the      *
*  GNU General Public License for more details.                       *
*                                                                     *
*  You should have received a copy of the GNU General Public License  *
*  along with this program; if not, write to the Free Software        *
*  Foundation, Inc., 59 Temple Place - Suite 330, Boston,             *
*  MA 02111-1307, USA.                                                *
*                                                                     *
\*********************************************************************/


package utils;


import java.io.File;
import java.io.StringWriter;
//import java.io.IOException;
import java.io.InputStream;
//import java.io.DataOutputStream;

//import org.w3c.dom.Node;
import org.w3c.dom.Document;
//import org.w3c.dom.NamedNodeMap;

import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


/**
 * Utility class.
 * Contains utility functions for manipulating XML DOMs.
 *
 * @author	Filip Pavlovic
 * @version	1.0
 */
public class DOMUtils
{
	public static Document loadDocument(String uri)
	{
		boolean ignoreWhitespace = false;
		boolean ignoreComments   = false;
		boolean putCDATAIntoText = false;
		boolean createEntityRefs = false;

        // Step 1: create a DocumentBuilderFactory and configure it
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // Set namespaceAware to true to get a DOM Level 2 tree with nodes
        // containing namesapce information.  This is necessary because the
        // default value from JAXP 1.0 was defined to be false.
        dbf.setNamespaceAware(true);

        // Optional: set various configuration options
        dbf.setIgnoringComments(ignoreComments);
        dbf.setIgnoringElementContentWhitespace(ignoreWhitespace);
        dbf.setCoalescing(putCDATAIntoText);
        // The opposite of creating entity ref nodes is expanding them inline
        dbf.setExpandEntityReferences(!createEntityRefs);

        // Step 2: create a DocumentBuilder that satisfies the constraints
        // specified by the DocumentBuilderFactory
        try{
        	DocumentBuilder db = dbf.newDocumentBuilder();
        	try{
		        // Set an ErrorHandler before parsing
		        /*OutputStreamWriter errorWriter;
		        errorWriter = new OutputStreamWriter(System.err, "UTF-8");
		        PrintWriter printWriter;
		        printWriter = new PrintWriter(errorWriter, true);
		        MyErrorHandler errHandler;
		        errHandler = new MyErrorHandler(printWriter);
		        db.setErrorHandler(errHandler);*/

	        	// Step 3: parse the input file
	        	return db.parse(uri);
	        } catch(java.io.IOException err){
	        	System.out.println(err);
		    } catch(SAXException err) {
		    	System.out.println(err);
		    }
        } catch(ParserConfigurationException err) {
        	System.out.println(err);
        }
        return null;
	}

	public static Document loadDocument(File f)
	{
		boolean ignoreWhitespace = false;
		boolean ignoreComments   = false;
		boolean putCDATAIntoText = false;
		boolean createEntityRefs = false;

        // Step 1: create a DocumentBuilderFactory and configure it
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // Set namespaceAware to true to get a DOM Level 2 tree with nodes
        // containing namesapce information.  This is necessary because the
        // default value from JAXP 1.0 was defined to be false.
        dbf.setNamespaceAware(true);

        // Optional: set various configuration options
        dbf.setIgnoringComments(ignoreComments);
        dbf.setIgnoringElementContentWhitespace(ignoreWhitespace);
        dbf.setCoalescing(putCDATAIntoText);
        // The opposite of creating entity ref nodes is expanding them inline
        dbf.setExpandEntityReferences(!createEntityRefs);

        // Step 2: create a DocumentBuilder that satisfies the constraints
        // specified by the DocumentBuilderFactory
        try{
        	DocumentBuilder db = dbf.newDocumentBuilder();
        	try{
		        // Set an ErrorHandler before parsing
		        /*OutputStreamWriter errorWriter;
		        errorWriter = new OutputStreamWriter(System.err, "UTF-8");
		        PrintWriter printWriter;
		        printWriter = new PrintWriter(errorWriter, true);
		        MyErrorHandler errHandler;
		        errHandler = new MyErrorHandler(printWriter);
		        db.setErrorHandler(errHandler);*/

	        	// Step 3: parse the input file
	        	return db.parse(f);
	        } catch(java.io.IOException err){
	        	System.out.println(err);
		    } catch(SAXException err) {
		    	System.out.println(err);
		    }
        } catch(ParserConfigurationException err) {
        	System.out.println(err);
        }
        return null;
	}

	public static Document loadDocument(InputStream is)
	{
		boolean ignoreWhitespace = false;
		boolean ignoreComments   = false;
		boolean putCDATAIntoText = false;
		boolean createEntityRefs = false;

        // Step 1: create a DocumentBuilderFactory and configure it
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // Set namespaceAware to true to get a DOM Level 2 tree with nodes
        // containing namesapce information.  This is necessary because the
        // default value from JAXP 1.0 was defined to be false.
        dbf.setNamespaceAware(true);

        // Optional: set various configuration options
        dbf.setIgnoringComments(ignoreComments);
        dbf.setIgnoringElementContentWhitespace(ignoreWhitespace);
        dbf.setCoalescing(putCDATAIntoText);
        // The opposite of creating entity ref nodes is expanding them inline
        dbf.setExpandEntityReferences(!createEntityRefs);

        // Step 2: create a DocumentBuilder that satisfies the constraints
        // specified by the DocumentBuilderFactory
        try{
        	DocumentBuilder db = dbf.newDocumentBuilder();
        	try{
		        // Set an ErrorHandler before parsing
		        /*OutputStreamWriter errorWriter;
		        errorWriter = new OutputStreamWriter(System.err, "UTF-8");
		        PrintWriter printWriter;
		        printWriter = new PrintWriter(errorWriter, true);
		        MyErrorHandler errHandler;
		        errHandler = new MyErrorHandler(printWriter);
		        db.setErrorHandler(errHandler);*/

	        	// Step 3: parse the input file
	        	return db.parse(is);
	        } catch(java.io.IOException err){
	        	System.out.println(err);
		    } catch(SAXException err) {
		    	System.out.println(err);
		    }
        } catch(ParserConfigurationException err) {
        	System.out.println(err);
        }
        return null;
	}

	public static Document newDocument()
	{
		boolean ignoreWhitespace = false;
		boolean ignoreComments   = false;
		boolean putCDATAIntoText = false;
		boolean createEntityRefs = false;

        // Step 1: create a DocumentBuilderFactory and configure it
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // Set namespaceAware to true to get a DOM Level 2 tree with nodes
        // containing namesapce information.  This is necessary because the
        // default value from JAXP 1.0 was defined to be false.
        dbf.setNamespaceAware(true);

        // Optional: set various configuration options
        dbf.setIgnoringComments(ignoreComments);
        dbf.setIgnoringElementContentWhitespace(ignoreWhitespace);
        dbf.setCoalescing(putCDATAIntoText);
        // The opposite of creating entity ref nodes is expanding them inline
        dbf.setExpandEntityReferences(!createEntityRefs);

        // Step 2: create a DocumentBuilder that satisfies the constraints
        // specified by the DocumentBuilderFactory
        try{
        	DocumentBuilder db = dbf.newDocumentBuilder();
         	return db.newDocument();
        } catch(ParserConfigurationException err) {
        	System.out.println(err);
        }
        return null;
	}
	
	/**
	 * Renders the XML into text 
	 *
	 * @param doc DOM document
	 * @param writer StringWriter (could also be a File object)
	 */
	public static boolean renderXMLString(Document doc, StringWriter writer)
	{
		try {
			DOMSource domSource = new DOMSource(doc);
			StreamResult streamResult = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer serializer = tf.newTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
			//serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"users.dtd");
			serializer.setOutputProperty(OutputKeys.INDENT,"yes");
			serializer.transform(domSource, streamResult); 
			return true;
		} catch(TransformerConfigurationException e) {
		} catch(TransformerException e) {
		}
		return false;
	}

	public static boolean renderXMLString(Document doc, File f)
	{
		try {
			DOMSource domSource = new DOMSource(doc);
			StreamResult streamResult = new StreamResult(f);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer serializer = tf.newTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
			//serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"users.dtd");
			serializer.setOutputProperty(OutputKeys.INDENT,"yes");
			serializer.transform(domSource, streamResult); 
			return true;
		} catch(TransformerConfigurationException e) {
		} catch(TransformerException e) {
		}
		return false;
	}
}
