package simpleMapper;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.ParserAdapter;

public class SAXTreeModel extends MapperTreeModel {

	MapperTreeNode parent = null;
	MapperTree tree = null;
	String uri = "";
	private boolean bStart = true;
	private InputSource inputSource = null;
	private PathList path = new PathList();

	public SAXTreeModel(MapperTreeNode newRoot) {
		super(newRoot);
		this.parent = newRoot;
		this.tree = newRoot.getTree();
	}

	public boolean load(String uri) {
		this.uri = uri;
		this.inputSource = new InputSource(uri);
		return load();

	}

	private boolean load() {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser sp = spf.newSAXParser();
			ParserAdapter pa = new ParserAdapter(sp.getParser());
			pa.setContentHandler(new MyContentHandler());
			pa.parse(inputSource);
			setRoot(getRoot());
			return true;
		} catch (ParserConfigurationException e) {
			System.out.println("Processing URL error: " + e);
		} catch (SAXException e) {
			System.out.println("Processing URL error: " + e);
		} catch (IOException e) {
			System.out.println("Processing URL error: " + e);
		}
		return false;
	}

	class MyContentHandler implements ContentHandler {
		public void characters(char[] ch, int start, int length) {
			if (parent != null)
				parent.setValue(new String(ch, start, length));
		}

		public void endDocument() {
		}

		public void endPrefixMapping(String prefix) {
		}

		public void ignorableWhitespace(char[] ch, int start, int length) {
		}

		public void processingInstruction(String target, String data) {
		}

		public void setDocumentLocator(Locator locator) {
		}

		public void skippedEntity(String name) {
		}

		public void startDocument() {
		}

		public void startPrefixMapping(String prefix, String uri) {
		}

		public void startElement(String uri, String localName, String qName, Attributes attrs) {
			path.add(qName);

			MapperTreeNode newNode = new MapperTreeNode(qName, SAXTreeModel.this.tree);
			newNode.setAllowsChildren(true);

			if (bStart) {
				setRoot(newNode);
				bStart = false;
			} else
				parent.add(newNode);

			parent = newNode;

			if (attrs != null) {
				MapperTreeNode attNode;
				for (int i = 0; i < attrs.getLength(); i++) {
					String attName = attrs.getLocalName(i);
					String val = attrs.getValue(i);
					attNode = new MapperTreeNode(attName, SAXTreeModel.this.tree);
					if (val.length() > 0)
						attNode.setValue(val);
					attNode.setAllowsChildren(false);
					newNode.add(attNode);
				}
			}

		}

		public void endElement(String uri, String localName, String qName) {
			parent = (MapperTreeNode) parent.getParent();

			if (!qName.equals(path.getLast()))
				System.out.println("Malformed XML document!");

			path.removeLast();

		}
	}
}
