package com.mscc.metaxslt;

import java.io.File;
import java.io.StringWriter;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class MetaXSLTTransformer {

	
	public static void demo_transormer_generator(String mapping, String transformerOutput) throws TransformerException {

		TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("metaxslt/map-generator.xslt"));
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File(mapping));
        transformer.transform(text, new StreamResult(new File(transformerOutput)));
//        StringWriter outputWriter = new StringWriter();
//        transformer.transform(text, new StreamResult(outputWriter));
//        
//        
//        
//        
//        String xslt_not_formated = outputWriter.toString();
//        TransformerFactory factory2 = TransformerFactory.newInstance();
//        Source xslt_formatter = new StreamSource(new File("metaxslt/formatter.xslt"));
//        Transformer transformer_formatter = factory2.newTransformer(xslt_formatter);
//        Source result_not_formatted_source = new StreamSource(xslt_not_formated);
//        transformer_formatter.transform(result_not_formatted_source, new StreamResult(new File(transformerOutput)));
//        
        
	}
	
	
	
	public static void demo_transormmation(String dataSource, String transformer, String transformedOutput) throws TransformerException{
        
        
        TransformerFactory factory2= TransformerFactory.newInstance();
        Source xslt2 = new StreamSource(new File(transformer));
        Transformer transformer2 = factory2.newTransformer(xslt2);

        Source text2 = new StreamSource(new File(dataSource));
        transformer2.transform(text2, new StreamResult(new File(transformedOutput+".tmp")));
        
        
        
      //clean
        TransformerFactory factory3= TransformerFactory.newInstance();
        Source xslt3 = new StreamSource(new File("metaxslt/clean.xslt"));
        Transformer transformer3 = factory3.newTransformer(xslt3);

        Source text3 = new StreamSource(new File(transformedOutput+".tmp"));
        transformer3.transform(text3, new StreamResult(new File(transformedOutput)));
        
	}
}
