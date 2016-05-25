package com.mscc.schema;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
  


import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.impl.inst2xsd.Inst2Xsd;
import org.apache.xmlbeans.impl.inst2xsd.Inst2XsdOptions;
import org.apache.xmlbeans.impl.xb.xsdschema.SchemaDocument;
public class XSDGenerator {

	public enum XMLSchemaDesign {
    	VENETIAN_BLIND, RUSSIAN_DOLL, SALAMI_SLICE 
	}
	
	public static XSDTree generateXSDTree(List<String> xmlfiles){
		
		XSDTree tree = null;
		
		
		
		return tree;
	}
	public XSDGenerator() {
		// TODO Auto-generated constructor stub
	}
	
	public static void demo(List<String> xmlfiles, String output){
	
		 try {
			 	XSDGenerator xmlBeans = new XSDGenerator();
			 	List<File> inputFiles = new ArrayList<File>();
			 	for(String fileName: xmlfiles){
			 		inputFiles.add(new File(fileName));
			 	}
			 	
			 	
	            SchemaDocument schemaDocument = xmlBeans.generateSchema(inputFiles);
	  
	            FileWriter writer = new FileWriter(new File(output));
	            schemaDocument.save(writer, new XmlOptions().setSavePrettyPrint());
	            writer.close();
	  
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	  
	    public SchemaDocument generateSchema(List<File> inputFiles) throws XmlException, IOException {
	        return generateSchema(inputFiles, XMLSchemaDesign.VENETIAN_BLIND);
	    }
	  
	    public SchemaDocument generateSchema(List<File> inputFiles, XMLSchemaDesign design) throws XmlException, IOException {
	        // Only 1 instance is required for now
	        XmlObject[] xmlInstances = new XmlObject[inputFiles.size()];
	        
	        for(int i = 0; i<inputFiles.size(); i++)
	        	xmlInstances[i] = XmlObject.Factory.parse(inputFiles.get(i));
	  
	        return inst2xsd(xmlInstances, design);
	    }
	  
	    public SchemaDocument generateSchema(InputStream is, XMLSchemaDesign design) throws XmlException, IOException {
	        // Only 1 instance is required for now
	        XmlObject[] xmlInstances = new XmlObject[1];
	        xmlInstances[0] = XmlObject.Factory.parse(is);
	  
	        return inst2xsd(xmlInstances, design);
	    }
	  
	    public SchemaDocument generateSchema(String input) throws XmlException, IOException {
	        return generateSchema(input, XMLSchemaDesign.VENETIAN_BLIND);
	    }
	  
	    public SchemaDocument generateSchema(String input, XMLSchemaDesign design) throws XmlException, IOException {
	        // Only 1 instance is required for now
	        XmlObject[] xmlInstances = new XmlObject[1];
	        xmlInstances[0] = XmlObject.Factory.parse(input);
	  
	        return inst2xsd(xmlInstances, design);
	    }
	  
	    private SchemaDocument inst2xsd(XmlObject[] xmlInstances, XMLSchemaDesign design) throws IOException {
	        Inst2XsdOptions inst2XsdOptions = new Inst2XsdOptions();
	        
	        
//	        		-design [rd | ss | vb]
//	        		The XML schema design type to use for the generated schema.
//	        		rd — Use russian doll design; local elements and local types.
//	        		ss — Use salami slice design; global elements and local types.
//	        		vb (default) — Use venetian blind design; local elements and global complex types.
//	        		-simple-content-types [smart | string]
//	        		The manner for detecting content types (leaf text)
//	        		smart (default) — Use a likely type, such as xs:byte for a value of "123".
//	        		string — Use xs:string as the type.
//	        		 
//	        		-enumerations [never | number]
//	        		Whether to use enumerations.
//	        		never — Never use enumerations.
//	        		number (default: 10) — Use number as the threshold for enumerations. Specifying "2" will create enumerations for elements with no more than two different values.
	        
	        inst2XsdOptions.setUseEnumerations(Inst2XsdOptions.ENUMERATION_NEVER);
	        inst2XsdOptions.setSimpleContentTypes(Inst2XsdOptions.SIMPLE_CONTENT_TYPES_SMART);
	        inst2XsdOptions.setVerbose(false);
	        if (design == null || design == XMLSchemaDesign.VENETIAN_BLIND) {
	            inst2XsdOptions.setDesign(Inst2XsdOptions.DESIGN_VENETIAN_BLIND);
	        } else if (design == XMLSchemaDesign.RUSSIAN_DOLL) {
	            inst2XsdOptions.setDesign(Inst2XsdOptions.DESIGN_RUSSIAN_DOLL);
	        } else if (design == XMLSchemaDesign.SALAMI_SLICE) {
	            inst2XsdOptions.setDesign(Inst2XsdOptions.DESIGN_SALAMI_SLICE);
	        }
	  
	        SchemaDocument[] schemaDocuments = Inst2Xsd.inst2xsd(xmlInstances, inst2XsdOptions);
	        if (schemaDocuments != null && schemaDocuments.length > 0) {
	            return schemaDocuments[0];
	        }
	  
	        return null;
	    }
	    
}
