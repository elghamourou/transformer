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

        Source text = new StreamSource(new File("metaxslt/mapping.xml"));
        transformer.transform(text, new StreamResult(new File("metaxslt/srcToDst.xslt")));
        
        
        
        
        TransformerFactory factory2= TransformerFactory.newInstance();
        Source xslt2 = new StreamSource(new File("metaxslt/srcToDst.xslt"));
        Transformer transformer2 = factory2.newTransformer(xslt2);

        Source text2 = new StreamSource(new File("metaxslt/src.xml"));
        transformer2.transform(text2, new StreamResult(new File("metaxslt/transformedDst.xml")));
        
	}
}
