package utils;

import java.io.File;
import java.io.StringWriter;
import java.io.InputStream;
import org.w3c.dom.Document;

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

public class DOMUtils {
	public static Document loadDocument(String uri) {
		boolean ignoreWhitespace = false;
		boolean ignoreComments = false;
		boolean putCDATAIntoText = false;
		boolean createEntityRefs = false;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setNamespaceAware(true);

		dbf.setIgnoringComments(ignoreComments);
		dbf.setIgnoringElementContentWhitespace(ignoreWhitespace);
		dbf.setCoalescing(putCDATAIntoText);
		dbf.setExpandEntityReferences(!createEntityRefs);

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			try {
				return db.parse(uri);
			} catch (java.io.IOException err) {
				System.out.println(err);
			} catch (SAXException err) {
				System.out.println(err);
			}
		} catch (ParserConfigurationException err) {
			System.out.println(err);
		}
		return null;
	}

	public static Document loadDocument(File f) {
		boolean ignoreWhitespace = false;
		boolean ignoreComments = false;
		boolean putCDATAIntoText = false;
		boolean createEntityRefs = false;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setNamespaceAware(true);

		dbf.setIgnoringComments(ignoreComments);
		dbf.setIgnoringElementContentWhitespace(ignoreWhitespace);
		dbf.setCoalescing(putCDATAIntoText);
		dbf.setExpandEntityReferences(!createEntityRefs);

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			try {
				return db.parse(f);
			} catch (java.io.IOException err) {
				System.out.println(err);
			} catch (SAXException err) {
				System.out.println(err);
			}
		} catch (ParserConfigurationException err) {
			System.out.println(err);
		}
		return null;
	}

	public static Document loadDocument(InputStream is) {
		boolean ignoreWhitespace = false;
		boolean ignoreComments = false;
		boolean putCDATAIntoText = false;
		boolean createEntityRefs = false;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setNamespaceAware(true);

		dbf.setIgnoringComments(ignoreComments);
		dbf.setIgnoringElementContentWhitespace(ignoreWhitespace);
		dbf.setCoalescing(putCDATAIntoText);
		dbf.setExpandEntityReferences(!createEntityRefs);

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			try {
				return db.parse(is);
			} catch (java.io.IOException err) {
				System.out.println(err);
			} catch (SAXException err) {
				System.out.println(err);
			}
		} catch (ParserConfigurationException err) {
			System.out.println(err);
		}
		return null;
	}

	public static Document newDocument() {
		boolean ignoreWhitespace = false;
		boolean ignoreComments = false;
		boolean putCDATAIntoText = false;
		boolean createEntityRefs = false;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);

		dbf.setIgnoringComments(ignoreComments);
		dbf.setIgnoringElementContentWhitespace(ignoreWhitespace);
		dbf.setCoalescing(putCDATAIntoText);
		dbf.setExpandEntityReferences(!createEntityRefs);

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			return db.newDocument();
		} catch (ParserConfigurationException err) {
			System.out.println(err);
		}
		return null;
	}

	public static boolean renderXMLString(Document doc, StringWriter writer) {
		try {
			DOMSource domSource = new DOMSource(doc);
			StreamResult streamResult = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer serializer = tf.newTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.transform(domSource, streamResult);
			return true;
		} catch (TransformerConfigurationException e) {
		} catch (TransformerException e) {
		}
		return false;
	}

	public static boolean renderXMLString(Document doc, File f) {
		try {
			DOMSource domSource = new DOMSource(doc);
			StreamResult streamResult = new StreamResult(f);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer serializer = tf.newTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.transform(domSource, streamResult);
			return true;
		} catch (TransformerConfigurationException e) {
		} catch (TransformerException e) {
		}
		return false;
	}
}
