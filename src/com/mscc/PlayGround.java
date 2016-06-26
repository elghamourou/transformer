package com.mscc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlbeans.XmlException;

import com.mscc.mapper.project.MapperProjectHandler;
import com.mscc.mapper.project.MappingBean;
import com.mscc.mapper.project.MappingProjectException;
import com.mscc.mapper.project.ProjectBean;
import com.mscc.mapper.project.ProjectHandler;

public class PlayGround {

	//file managment
	//property file
	//list (perhaps csv file)
	//xml files (mapping)
	
	/* to parse this
	 [happy]
	 age = 99
	 height = 77.66
	 homeDir = /home/happy
   int age = ini.get("happy", "age", int.class);
   double height = ini.get("happy", "height", double.class);
   String dir = ini.get("happy", "homeDir");
   
   To write this
    [sleepy]
	 age = 55
	 weight = 45.6
   ini.put("sleepy", "age", 55);
   ini.put("sleepy", "weight", 45.6);
   ini.store();
	*/
	
	public static void main(String[] args) throws IOException, MappingProjectException, XmlException {
		
		
		
		
		
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
		MapperProjectHandler.getActiveProjectHandler().importSourceXSDFromXML(xmlFiles);
		MapperProjectHandler.getActiveProjectHandler().importDestinationXSD("xsd2_3/ADT_A01.xsd");
		
		MappingBean mapping2 = new MappingBean();
		
		mapping2.setMappingDestination("ADT_R01");
		mapping2.setMappingVersion("2.5");
		mapping2.setMappingSource("HDPS_MSG_TYPE_24");
		
		List<String> jsonFiles = new ArrayList<String>();
		jsonFiles.add("demo/demo.hdps");
		MapperProjectHandler.getActiveProjectHandler().createMapping(mapping2);
		MapperProjectHandler.getActiveProjectHandler().importSourceXSDFromJson(jsonFiles);
		MapperProjectHandler.getActiveProjectHandler().importDestinationXSD("xsd2_3/ADT_A01.xsd");
		
		MapperProjectHandler.importProject("M:/project/transformer/project/hdps_to_hl7", false);
		System.out.println(MapperProjectHandler.getActiveProjectHandler());
		MapperProjectHandler.getActiveProjectHandler().build("project/build", false);
		
	}
}
