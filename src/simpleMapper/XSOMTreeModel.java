package simpleMapper;

import java.io.File;
import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.xml.xsom.XSSchemaSet;
import com.sun.xml.xsom.parser.XSOMParser;

import java.util.Iterator;

import com.sun.xml.xsom.*;

public class XSOMTreeModel extends MapperTreeModel {
	String uri = "";
	private InputSource inputSource = null;
	private MapperTree tree = null;
	private int level = 0;
	MapperTreeNode parent = null;
	private boolean bStart = true;

	public XSOMTreeModel(MapperTreeNode newRoot) {
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
		boolean bResult = false;

		try {
			XSOMParser reader = new XSOMParser();
			reader.parse(new File(uri));
			XSSchemaSet xss = reader.getResult();

			XSSchema s = (XSSchema) xss.getSchema(1);
			if (null != s) {
				loadSchema(s, this.parent);
				bResult = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

		return bResult;
	}

	private void loadSchema(XSSchema schema, MapperTreeNode parent) {
		Iterator itr = schema.iterateElementDecls();
		while (itr.hasNext()) {
			XSElementDecl element = (XSElementDecl) itr.next();
			loadElement(schema, parent, element);
		}
	}

	private void loadElement(XSSchema schema, MapperTreeNode parent, XSElementDecl element) {
		level++;

		boolean bHasChildren = false;

		String name = element.getName();
		if (element.isAbstract())
			name += " (abstract)";

		MapperTreeNode newNode = addElement(name, parent);

		newNode.addProperty("Type", element.getType().getName());
		newNode.addProperty("DefaultValue", element.getDefaultValue());
		newNode.addProperty("FixedValue", element.getFixedValue());
		newNode.addProperty("Abstract", String.valueOf(element.isAbstract()));
		newNode.addProperty("Nillable", String.valueOf(element.isNillable()));
		newNode.addProperty("TargetNamespace", element.getTargetNamespace());
		newNode.addProperty("Global", String.valueOf(element.isGlobal()));
		newNode.addProperty("Local", String.valueOf(element.isLocal()));

		XSType t = element.getType();
		if (t.isComplexType()) {
			XSComplexType ct = t.asComplexType();

			if (null != ct) {
				loadAttributes(newNode, ct);

				XSContentType cont = ct.getContentType();

				if (null != cont) {
					XSParticle part = cont.asParticle();
					if (null != part) {
						XSTerm trm = part.getTerm();
						if (null != trm) {
							XSModelGroup mg = trm.asModelGroup();
							for (int i = 0; i < mg.getSize(); i++) {
								XSParticle p = mg.getChild(i);

								XSTerm trm2 = p.getTerm();
								if (trm2.isElementDecl()) {
									XSElementDecl e = trm2.asElementDecl();

									bHasChildren = true;
									loadElement(schema, newNode, e, p.getMinOccurs(), p.getMaxOccurs());
								}
							}
						}
					}
				}
			}
		} else if (t.isSimpleType()) {
			XSSimpleType st = t.asSimpleType();
			if (null != st) {
			}
		}

		level--;

		if (bHasChildren && 0 == level) {
			// _rootNode = newNode;
		}
	}

	private void loadAttributes(MapperTreeNode node, XSComplexType ct) {

		Iterator itr = ct.iterateAttributeUses();
		while (itr.hasNext()) {
			XSAttributeUse att = (XSAttributeUse) itr.next();
			MapperTreeNode newNode = addAttribute(att.getDecl().getName(), node);

			newNode.addProperty("Type", att.getDecl().getType().getName());
			newNode.addProperty("DefaultValue", att.getDefaultValue());
			newNode.addProperty("FixedValue", att.getFixedValue());
			newNode.addProperty("Required", String.valueOf(att.isRequired()));

		}

	}

	private MapperTreeNode addElement(String name, MapperTreeNode parent) {
		MapperTreeNode newNode = new MapperTreeNode(name, this.tree);
		newNode.setAllowsChildren(true);

		if (bStart) {
			setRoot(newNode);
			bStart = false;
		} else {
			parent.add(newNode);
		}

		return newNode;

	}

	private MapperTreeNode addAttribute(String name, MapperTreeNode parent) {
		MapperTreeNode newNode = new MapperTreeNode(name, this.tree);
		newNode.setAllowsChildren(false);
		parent.add(newNode);
		return newNode;
	}

	private void loadElement(XSSchema schema, MapperTreeNode parent, XSElementDecl element, int minOcc, int maxOcc) {
		level++;

		boolean bHasChildren = false;

		String name = element.getName();
		if (element.isAbstract())
			name += " (abstract)";

		MapperTreeNode newNode = addElement(name, parent);

		newNode.addProperty("Type", element.getType().getName());
		newNode.addProperty("DefaultValue", element.getDefaultValue());
		newNode.addProperty("FixedValue", element.getFixedValue());
		newNode.addProperty("Abstract", String.valueOf(element.isAbstract()));
		newNode.addProperty("Nillable", String.valueOf(element.isNillable()));
		newNode.addProperty("TargetNamespace", element.getTargetNamespace());
		newNode.addProperty("Global", String.valueOf(element.isGlobal()));
		newNode.addProperty("Local", String.valueOf(element.isLocal()));
		newNode.addProperty("maxOccurs", String.valueOf(maxOcc));
		newNode.addProperty("minOccurs", String.valueOf(minOcc));

		XSType t = element.getType();
		if (t.isComplexType()) {
			XSComplexType ct = t.asComplexType();

			if (null != ct) {
				loadAttributes(newNode, ct);

				XSContentType cont = ct.getContentType();

				if (null != cont) {
					XSParticle part = cont.asParticle();
					if (null != part) {
						XSTerm trm = part.getTerm();
						if (null != trm) {
							XSModelGroup mg = trm.asModelGroup();
							for (int i = 0; i < mg.getSize(); i++) {
								XSParticle p = mg.getChild(i);

								XSTerm trm2 = p.getTerm();
								if (trm2.isElementDecl()) {
									XSElementDecl e = trm2.asElementDecl();

									bHasChildren = true;
									loadElement(schema, newNode, e, p.getMinOccurs(), p.getMaxOccurs());
								}
							}
						}
					}
				}
			}
		} else if (t.isSimpleType()) {
			XSSimpleType st = t.asSimpleType();
			if (null != st) {
			}
		}

		level--;

		if (bHasChildren && 0 == level) {
			// _rootNode = newNode;
		}
	}

}
