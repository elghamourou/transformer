package com.mscc.transformer;

import java.io.File;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XSLTTransformer {

	
	public static void main(String[] args) throws TransformerException {
		
		
		
//		TransformerFactory factory = TransformerFactory.newInstance();
//        Source xslt = new StreamSource(new File("srctodst4.xslt"));
//        Transformer transformer = factory.newTransformer(xslt);
//
//        Source text = new StreamSource(new File("src1.xml"));
//        transformer.transform(text, new StreamResult(new File("output5.xml")));
//        
        
		TransformerFactory factory2= TransformerFactory.newInstance();
        Source xslt2 = new StreamSource(new File("metaxslt/srcToDst.xslt"));
        Transformer transformer2 = factory2.newTransformer(xslt2);

        Source text2 = new StreamSource(new File("metaxslt/src.xml"));
        transformer2.transform(text2, new StreamResult(new File("metaxslt/transformedDst.xml")));
        
        
        
        
        //clean
        TransformerFactory factory3= TransformerFactory.newInstance();
        Source xslt3 = new StreamSource(new File("metaxslt/clean.xslt"));
        Transformer transformer3 = factory3.newTransformer(xslt3);

        Source text3 = new StreamSource(new File("metaxslt/transformedDst.xml"));
        transformer3.transform(text3, new StreamResult(new File("metaxslt/transformedDstClean.xml")));
        
        
	}
}
