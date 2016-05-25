package com.mscc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.json.JSONException;

import com.mscc.metaxslt.MetaXSLTTransformer;
import com.mscc.schema.JsonToXML;
import com.mscc.schema.XSDGenerator;

public class Demo {

	
	
	public static void main(String[] args) throws JSONException, IOException, TransformerException {
		//PI:
		//1:generate xsd from a xmls examples
		List<String> xmlfiles = new ArrayList<String>();
		xmlfiles.add("demo/src.xml");
		XSDGenerator.demo(xmlfiles, "demo/output/src_to_xsd.xsd");
		
		xmlfiles = new ArrayList<String>();
		xmlfiles.add("demo/demo.xml");
		XSDGenerator.demo(xmlfiles, "demo/output/demo_xml_to_xsd.xsd");
		
		//2:generate xml from a json 
		String jsonfilename = "demo/demo.hdps";
		String jsonxmlfilename = "demo/output/demo_json_to_xml.xml";
		String jsonxsdoutput = "demo/output/demo_json_to_xsd.xsd";
		JsonToXML.demo(jsonfilename, "hdps", jsonxmlfilename);
		//3:generate xsd from a json 
		xmlfiles = new ArrayList<String>();
		xmlfiles.add(jsonxmlfilename);
		XSDGenerator.demo(xmlfiles, jsonxsdoutput);
		
		//PII
		//1: generating transformer xslt
		String mapping_file_name = "demo/mapping/hdpsToHL7-mappingL.xml";
		String transormer_xslt_output = "demo/mapping/hdpsToHL7.xslt";
		MetaXSLTTransformer.demo_transormer_generator(mapping_file_name, transormer_xslt_output);
		
		//2: transforming data
		String dataSource = "demo/mapping/hdps.xml";
		String transformedOutput = "demo/mapping/hl7.xml";
		MetaXSLTTransformer.demo_transormmation(dataSource, transormer_xslt_output, transformedOutput);
	}
}
