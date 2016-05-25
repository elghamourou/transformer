package com.mscc.metaxslt;

import java.io.File;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class MetaXSLTTransformer {

	
	public static void main(String[] args) throws TransformerException {

		TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("metaxslt/map-generator.xslt"));
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File("hdps/hdpsToHL7-mapping.xml/hdpsToHL7-mappingL.xml"));
        transformer.transform(text, new StreamResult(new File("hdps/hdpsToHL7-mapping.xml/hdpsToHL7.xslt")));
        
        
        
        
        TransformerFactory factory2= TransformerFactory.newInstance();
        Source xslt2 = new StreamSource(new File("hdps/hdpsToHL7-mapping.xml/hdpsToHL7.xslt"));
        Transformer transformer2 = factory2.newTransformer(xslt2);

        Source text2 = new StreamSource(new File("hdps/hdpsToHL7-mapping.xml/A01Json-2.xml"));
        transformer2.transform(text2, new StreamResult(new File("hdps/hdpsToHL7-mapping.xml/transformedhdps.xml")));
        
        
        
      //clean
        TransformerFactory factory3= TransformerFactory.newInstance();
        Source xslt3 = new StreamSource(new File("metaxslt/clean.xslt"));
        Transformer transformer3 = factory3.newTransformer(xslt3);

        Source text3 = new StreamSource(new File("hdps/hdpsToHL7-mapping.xml/transformedhdps.xml"));
        transformer3.transform(text3, new StreamResult(new File("hdps/hdpsToHL7-mapping.xml/transformedhdpsClean.xml")));
        
	}
}
