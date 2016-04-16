/**
 * 
 */
package simpleMapper;

import java.io.File;
//import java.io.OutputStreamWriter;
import java.io.IOException;





import org.xml.sax.InputSource;
//import org.w3c.dom.Document;
//import org.w3c.dom.Node;
import org.xml.sax.SAXException;





//import com.sun.org.apache.xerces.internal.impl.xs.XSAttributeDecl;
//import com.sun.org.apache.xerces.internal.impl.xs.XSAttributeUseImpl;
import com.sun.xml.xsom.XSSchemaSet;
import com.sun.xml.xsom.impl.ComplexTypeImpl;
import com.sun.xml.xsom.impl.ParticleImpl;
//import com.sun.xml.xsom.impl.util.SchemaWriter;
import com.sun.xml.xsom.parser.XSOMParser;

import java.util.Iterator;

import com.sun.xml.xsom.*;


/**
 * @author sqba
 *
 */
public class XSOMTreeModel extends MapperTreeModel
{
	String uri = "";
	private InputSource			inputSource = null;
	private MapperTree	tree		= null;
	private int			level		= 0;

	/**
	 * Constructor
	 */
    public XSOMTreeModel(MapperTreeNode newRoot)
    {
		super(newRoot);
		tree = newRoot.getTree();
	}

	/**
	 * Main loader.
	 *
	 * @param file schema file
	 */
    public boolean load(String uri) {
		this.uri = uri;
		this.inputSource = new InputSource(uri);
		return load();
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	private boolean load()
	{
		boolean bResult = false;
		
		MapperTreeNode rootNode = null;
		
//		MapperTreeNode rootNode = new MapperTreeNode(uri, this.tree);
//		((MapperTreeNode)getRoot()).add(rootNode);
		
		try
		{
			XSOMParser reader = new XSOMParser();
	        // set an error handler so that you can receive error messages
	        //reader.setErrorHandler(new ErrorReporter(System.out));
			reader.parse(new File(uri));
			XSSchemaSet xss = reader.getResult();
			
			//iterate each XSSchema object. XSSchema is a per-namespace schema.
			/*Iterator itr = xss.iterateSchema();
			while( itr.hasNext() )
			{
			  XSSchema s = (XSSchema)itr.next();
			  //XSSchema s = (XSSchema)xss.getSchema(1);
			  
			  String ns = s.getTargetNamespace();
			  //if(ns != "http://www.w3.org/2001/XMLSchema")
			  {
				  MapperTreeNode nsNode = new MapperTreeNode(ns, this.tree);
				  rootNode.add(nsNode);
			  
				  loadSchema(s, nsNode);
			  }
			}*/
			XSSchema s = (XSSchema)xss.getSchema(1);
			if(null != s)
			{
				String name = uri.substring(uri.lastIndexOf('\\')+1);
				rootNode = new MapperTreeNode(name, this.tree);
				((MapperTreeNode)getRoot()).add(rootNode);
				loadSchema(s, rootNode);
				bResult = true;
			}
        } catch( IOException e ) {
        	e.printStackTrace();
        } catch(SAXException e) {
        	e.printStackTrace();
        }

		
		
		return bResult;
	}
	
	private void loadSchema(XSSchema schema, MapperTreeNode parent)
	{
		Iterator itr = schema.iterateElementDecls();
		while( itr.hasNext() )
		{
			XSElementDecl element = (XSElementDecl)itr.next();
			loadElement(schema, parent, element);
		}
	}
	
	
	private void loadElement(XSSchema schema, MapperTreeNode parent, XSElementDecl element)
	{
		level++;
		
		boolean bHasChildren = false;
		
	    String name = element.getName();
	    if( element.isAbstract() )
	    	name += " (abstract)";

	    
	    MapperTreeNode newNode = addElement(name, parent);
	    
	    newNode.addProperty("Type",				element.getType().getName());
	    newNode.addProperty("DefaultValue",		element.getDefaultValue());
	    newNode.addProperty("FixedValue",		element.getFixedValue());
	    newNode.addProperty("Abstract",			String.valueOf(element.isAbstract()));
	    newNode.addProperty("Nillable",			String.valueOf(element.isNillable()));
	    newNode.addProperty("TargetNamespace",	element.getTargetNamespace());
	    //newNode.addProperty("Anonymous",		String.valueOf(element.isAnonymous()));
	    newNode.addProperty("Global",			String.valueOf(element.isGlobal()));
	    newNode.addProperty("Local",			String.valueOf(element.isLocal()));
		
		XSType t = element.getType();
		if( t.isComplexType() )
		{
			XSComplexType ct = t.asComplexType();
//			if(ct.getContentType().asParticle().getTerm().isModelGroup()){
//				for(XSParticle p:ct.getContentType().asParticle().getTerm().asModelGroup().getChildren()){
//					System.out.println("______"+name+"________");
//					System.out.println(p.getMaxOccurs());
//					System.out.println(p.getMinOccurs());
//				}
//			}
			
			if(null != ct)
			{
			    loadAttributes(newNode, ct);
			    
			    //XSContentType cont = ct.getExplicitContent();
			    
			    XSContentType cont = ct.getContentType();
			    
			    if(null != cont)
			    {
			    	XSParticle part = cont.asParticle();
			    	if(null != part)
			    	{
			    		XSTerm trm = part.getTerm();
			    		if(null != trm)
			    		{
			    			XSModelGroup mg = trm.asModelGroup();
			    			for(int i=0; i<mg.getSize(); i++)
			    			{
			    				XSParticle p = mg.getChild(i);
			    				
			    				
			    				
			    				//addElement(p.toString(), newNode);
			    				XSTerm trm2 = p.getTerm();
			    				if(trm2.isElementDecl())
			    				{
			    					XSElementDecl e = trm2.asElementDecl(); 
			    					
			    					
//			    					String name2 = e.getName();
//				    			    if( element.isAbstract() )
//				    			    	name2 += " (abstract)";
//				    			    System.out.println("______"+name2+"________");
//				    				System.out.println(p.getMaxOccurs());
//									System.out.println(p.getMinOccurs());
			    					
			    					
			    					
			    					
			    					bHasChildren = true;
			    					loadElement(schema, newNode, e, p.getMinOccurs(), p.getMaxOccurs() );
			    				}
			    				//XSSimpleType st = p.asSimpleType();
			    				//if(null != st)
			    				//	addElement(st.getName(), newNode);
			    			}
			    		}
			    	}
			    }
			    /*XSElementDecl e = ct.getScope();
			    if(null != e)
			    {
				    boolean b1 = e.isModelGroup();
				    boolean b2 = e.isModelGroupDecl();
				    int d=1;
			    }*/
/*
			    if( element.isModelGroup() )
			    {
				    XSModelGroup mg = element.asModelGroup();
				    if(null != mg)
				    {
					    XSParticle[] children = mg.getChildren();
					    for(int i=0; i<mg.getSize(); i++)
					    {
					    	XSTerm term = children[i].getTerm();
					    	if( term.isElementDecl() )
					    	{
					    		XSElementDecl e = term.asElementDecl();
					    	}
					    	//loadElement(schema, newNode);
					    }
				    }
			    }
			    else if( element.isModelGroupDecl() )
			    {
			    	XSModelGroupDecl mgd = element.asModelGroupDecl();
				    if(null != mgd)
				    {
				    	XSModelGroup mg = mgd.getModelGroup();
					    XSParticle[] children = mg.getChildren();
					    for(int i=0; i<mg.getSize(); i++)
					    {
					    	XSTerm term = children[i].getTerm();
					    	if( term.isElementDecl() )
					    	{
					    		XSElementDecl e = term.asElementDecl();
					    	}
					    	//loadElement(schema, newNode);
					    }
				    }
			    }
*/
			}
		}
		else if( t.isSimpleType() )
		{
			XSSimpleType st = t.asSimpleType();
			if(null != st)
			{
			}
		}
		
		//parent.add(newNode);
		
		level--;
		
		if(bHasChildren && 0==level)
		{
			//_rootNode = newNode;
		}
	}

	private void loadAttributes(MapperTreeNode node, XSComplexType ct)
	{
		
		//Iterator itr = ct.iterateDeclaredAttributeUses();
		//Iterator itr = ct.iterateAttGroups();
		Iterator itr = ct.iterateAttributeUses();
		while( itr.hasNext() )
		{
			XSAttributeUse att = (XSAttributeUse)itr.next();
			//XSAttributeDecl ad = att.getDecl();
			MapperTreeNode newNode = addAttribute(att.getDecl().getName(), node);
			
			
			newNode.addProperty("Type", att.getDecl().getType().getName());
			newNode.addProperty("DefaultValue", att.getDefaultValue());
			newNode.addProperty("FixedValue", att.getFixedValue());
			newNode.addProperty("Required", String.valueOf(att.isRequired()));
			
			
			/*XSAttributeDecl decl = att.getDecl();
			if( decl.getType().isList() )
			{
				 XSListSimpleType list = decl.getType().asList();
				 //XSSimpleType type = list.getItemType();
				 newNode.addProperty("Variety", list.getVariety().toString());
			}*/
			
			//MapperTreeNode newNode = new MapperTreeNode(att.getDecl().getName(), this.tree);
			//newNode.setAllowsChildren(false);
			//node.add(newNode);
		}
		
		/*itr = ct.iterateAttGroups();
		while( itr.hasNext() )
		{
			XSAttributeUse att = (XSAttributeUse)itr.next();
		}

		itr = ct.iterateDeclaredAttributeUses();
		while( itr.hasNext() )
		{
			XSAttributeUse att = (XSAttributeUse)itr.next();
		}*/
}

	private MapperTreeNode addElement(String name, MapperTreeNode parent)
	{
		MapperTreeNode newNode = new MapperTreeNode(name, this.tree);
		newNode.setAllowsChildren(true);
		parent.add(newNode);
		return newNode;
	}

	private MapperTreeNode addAttribute(String name, MapperTreeNode parent)
	{
		MapperTreeNode newNode = new MapperTreeNode(name, this.tree);
		newNode.setAllowsChildren(false);
		parent.add(newNode);
		return newNode;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void loadElement(XSSchema schema, MapperTreeNode parent, XSElementDecl element, int minOcc, int maxOcc)
	{
		level++;
		
		boolean bHasChildren = false;
		
	    String name = element.getName();
	    if( element.isAbstract() )
	    	name += " (abstract)";

	    MapperTreeNode newNode = addElement(name, parent);
	    
	    newNode.addProperty("Type",				element.getType().getName());
	    newNode.addProperty("DefaultValue",		element.getDefaultValue());
	    newNode.addProperty("FixedValue",		element.getFixedValue());
	    newNode.addProperty("Abstract",			String.valueOf(element.isAbstract()));
	    newNode.addProperty("Nillable",			String.valueOf(element.isNillable()));
	    newNode.addProperty("TargetNamespace",	element.getTargetNamespace());
	    //newNode.addProperty("Anonymous",		String.valueOf(element.isAnonymous()));
	    newNode.addProperty("Global",			String.valueOf(element.isGlobal()));
	    newNode.addProperty("Local",			String.valueOf(element.isLocal()));
	    newNode.addProperty("maxOccurs",			String.valueOf(maxOcc));
	    newNode.addProperty("minOccurs",			String.valueOf(minOcc));
		
		XSType t = element.getType();
		if( t.isComplexType() )
		{
			XSComplexType ct = t.asComplexType();
			
			if(null != ct)
			{
			    loadAttributes(newNode, ct);
			    
			    //XSContentType cont = ct.getExplicitContent();
			    
			    XSContentType cont = ct.getContentType();
			    
			    if(null != cont)
			    {
			    	XSParticle part = cont.asParticle();
			    	if(null != part)
			    	{
			    		XSTerm trm = part.getTerm();
			    		if(null != trm)
			    		{
			    			XSModelGroup mg = trm.asModelGroup();
			    			for(int i=0; i<mg.getSize(); i++)
			    			{
			    				XSParticle p = mg.getChild(i);
			    				
			    				
			    				
			    				//addElement(p.toString(), newNode);
			    				XSTerm trm2 = p.getTerm();
			    				if(trm2.isElementDecl())
			    				{
			    					XSElementDecl e = trm2.asElementDecl(); 
			    					
			    					
//			    					String name2 = e.getName();
//				    			    if( element.isAbstract() )
//				    			    	name2 += " (abstract)";
//				    			    System.out.println("______"+name2+"________");
//				    				System.out.println(p.getMaxOccurs());
//									System.out.println(p.getMinOccurs());
			    					
			    					
			    					
			    					
			    					bHasChildren = true;
			    					loadElement(schema, newNode, e, p.getMinOccurs(), p.getMaxOccurs());
			    				}
			    				//XSSimpleType st = p.asSimpleType();
			    				//if(null != st)
			    				//	addElement(st.getName(), newNode);
			    			}
			    		}
			    	}
			    }
			    /*XSElementDecl e = ct.getScope();
			    if(null != e)
			    {
				    boolean b1 = e.isModelGroup();
				    boolean b2 = e.isModelGroupDecl();
				    int d=1;
			    }*/
/*
			    if( element.isModelGroup() )
			    {
				    XSModelGroup mg = element.asModelGroup();
				    if(null != mg)
				    {
					    XSParticle[] children = mg.getChildren();
					    for(int i=0; i<mg.getSize(); i++)
					    {
					    	XSTerm term = children[i].getTerm();
					    	if( term.isElementDecl() )
					    	{
					    		XSElementDecl e = term.asElementDecl();
					    	}
					    	//loadElement(schema, newNode);
					    }
				    }
			    }
			    else if( element.isModelGroupDecl() )
			    {
			    	XSModelGroupDecl mgd = element.asModelGroupDecl();
				    if(null != mgd)
				    {
				    	XSModelGroup mg = mgd.getModelGroup();
					    XSParticle[] children = mg.getChildren();
					    for(int i=0; i<mg.getSize(); i++)
					    {
					    	XSTerm term = children[i].getTerm();
					    	if( term.isElementDecl() )
					    	{
					    		XSElementDecl e = term.asElementDecl();
					    	}
					    	//loadElement(schema, newNode);
					    }
				    }
			    }
*/
			}
		}
		else if( t.isSimpleType() )
		{
			XSSimpleType st = t.asSimpleType();
			if(null != st)
			{
			}
		}
		
		//parent.add(newNode);
		
		level--;
		
		if(bHasChildren && 0==level)
		{
			//_rootNode = newNode;
		}
	}
	
	
	
	
	
	
	
}
