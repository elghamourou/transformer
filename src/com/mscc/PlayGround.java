package com.mscc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.apache.xmlbeans.XmlException;

import com.mscc.mapper.project.MapperProjectHandler;
import com.mscc.mapper.project.MappingBean;
import com.mscc.mapper.project.MappingProjectException;
import com.mscc.mapper.project.ProjectBean;

public class PlayGround {

	
	public static void main(String[] args) throws IOException, MappingProjectException, XmlException, TransformerException {
		
		
		
		
		
		ProjectBean project = new ProjectBean();
		
		project.setMappingProjectName("hdps_to_hl7");
		project.setMappingProjectVersion("1.0");
		project.setMappingProjectRoot("project");
		
		MapperProjectHandler.createProject(project);
		
		
		MappingBean mapping1 = new MappingBean();
		
		mapping1.setMappingDestination("ADT_A01");
		mapping1.setMappingVersion("1.0");
		mapping1.setMappingSource("HDPS_MSG_TYPE_1");
		
		List<String> xmlFiles = new ArrayList<String>();
		xmlFiles.add("demo/src.xml");
		MapperProjectHandler.getActiveProjectHandler().createMapping(mapping1);
		MapperProjectHandler.getActiveProjectHandler().importSourceXSDFromXML(xmlFiles,"src", null);
		MapperProjectHandler.getActiveProjectHandler().importDestinationXSD("xsd2_3/ADT_A01.xsd","ADT_A01","urn:hl7-org:v2xml", "xsd2_3/segments.xsd","xsd2_3/fields.xsd", "xsd2_3/datatypes.xsd");
		
		MappingBean mapping2 = new MappingBean();
		
		mapping2.setMappingDestination("ADT_R01");
		mapping2.setMappingVersion("2.5");
		mapping2.setMappingSource("HDPS_MSG_TYPE_24");
		
		List<String> jsonFiles = new ArrayList<String>();
		jsonFiles.add("demo/demo.hdps");
		MapperProjectHandler.getActiveProjectHandler().createMapping(mapping2);
		MapperProjectHandler.getActiveProjectHandler().importSourceXSDFromJson(jsonFiles, "hdps_R01",null);
		MapperProjectHandler.getActiveProjectHandler().importDestinationXSD("xsd2_3/ADT_A01.xsd","ADT_A01","urn:hl7-org:v2xml", "xsd2_3/segments.xsd","xsd2_3/fields.xsd", "xsd2_3/datatypes.xsd");
		
		MapperProjectHandler.importProject("M:/project/transformer/project/hdps_to_hl7", false);
		System.out.println(MapperProjectHandler.getActiveProjectHandler());
		MapperProjectHandler.getActiveProjectHandler().build("project/build", false);
		
	}
}
