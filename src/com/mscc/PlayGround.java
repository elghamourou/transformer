package com.mscc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.transform.TransformerException;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.xmlbeans.XmlException;

import com.mscc.mapper.project.MapperProjectHandler;
import com.mscc.mapper.project.MappingBean;
import com.mscc.mapper.project.MappingProjectException;
import com.mscc.mapper.project.ProjectBean;
import com.mscc.mapper.utils.ZipUtil;
import com.mscc.transformer.engine.PluginFolderMonitor;
import com.mscc.transformer.engine.SupportedTransformationTree;
import com.mscc.transformer.engine.TransformationTreeModel;
import com.mscc.transformer.engine.TransformerEngine;

public class PlayGround {

	
	public static void main(String[] args) throws IOException, MappingProjectException, XmlException, TransformerException, ArchiveException, InterruptedException {
		
		
		
/*		
		
		ProjectBean project = new ProjectBean();
		
		project.setMappingProjectName("hdps_to_hl7");
		project.setMappingProjectVersion("1.0");
		project.setMappingProjectRoot("project");
		
		MapperProjectHandler.createProject(project);
		
		
		MappingBean mapping1 = new MappingBean();
		
		mapping1.setMappingDestination("ADT_A01");
		mapping1.setMappingVersion("1.0");
		mapping1.setMappingSource("HDPS_A01");
		
		List<String> xmlFiles1 = new ArrayList<String>();
		xmlFiles1.add("demo/mapping/hdps.xml");
		MapperProjectHandler.getActiveProjectHandler().createMapping(mapping1);
		MapperProjectHandler.getActiveProjectHandler().importSourceXSDFromXML(xmlFiles1,"hdps", null);
		MapperProjectHandler.getActiveProjectHandler().importDestinationXSD("xsd2_3/ADT_A01.xsd","ADT_A01","urn:hl7-org:v2xml", "xsd2_3/segments.xsd","xsd2_3/fields.xsd", "xsd2_3/datatypes.xsd");
		MapperProjectHandler.getActiveProjectHandler().importMappingFile("demo/mapping/hdpsToHL7-mappingL.xml");
		
		
		
		
		MappingBean mapping2 = new MappingBean();
		
		mapping2.setMappingDestination("ADT_A01");
		mapping2.setMappingVersion("2.0");
		mapping2.setMappingSource("HDPS_A01");
		
		List<String> xmlFiles2 = new ArrayList<String>();
		xmlFiles2.add("demo/mapping/hdps.xml");
		MapperProjectHandler.getActiveProjectHandler().createMapping(mapping2);
		MapperProjectHandler.getActiveProjectHandler().importSourceXSDFromXML(xmlFiles2,"hdps", null);
		MapperProjectHandler.getActiveProjectHandler().importDestinationXSD("xsd2_3/ADT_A01.xsd","ADT_A01","urn:hl7-org:v2xml", "xsd2_3/segments.xsd","xsd2_3/fields.xsd", "xsd2_3/datatypes.xsd");
		MapperProjectHandler.getActiveProjectHandler().importMappingFile("demo/mapping/hdpsToHL7-mappingL.xml");
		
		
		
		
		MappingBean mapping3 = new MappingBean();
		
		mapping3.setMappingDestination("ADT_R01");
		mapping3.setMappingVersion("0.9");
		mapping3.setMappingSource("HDPS_R01");
		
		List<String> xmlFiles3 = new ArrayList<String>();
		xmlFiles3.add("demo/mapping/hdps.xml");
		MapperProjectHandler.getActiveProjectHandler().createMapping(mapping3);
		MapperProjectHandler.getActiveProjectHandler().importSourceXSDFromXML(xmlFiles3,"hdps", null);
		MapperProjectHandler.getActiveProjectHandler().importDestinationXSD("xsd2_3/ADT_A01.xsd","ADT_A01","urn:hl7-org:v2xml", "xsd2_3/segments.xsd","xsd2_3/fields.xsd", "xsd2_3/datatypes.xsd");
		MapperProjectHandler.getActiveProjectHandler().importMappingFile("demo/mapping/hdpsToHL7-mappingL.xml");
			
		
		
//		MappingBean mapping2 = new MappingBean();
//		
//		mapping2.setMappingDestination("ADT_R01");
//		mapping2.setMappingVersion("2.5");
//		mapping2.setMappingSource("HDPS_MSG_TYPE_24");
//		
//		List<String> jsonFiles = new ArrayList<String>();
//		jsonFiles.add("demo/demo.hdps");
//		MapperProjectHandler.getActiveProjectHandler().createMapping(mapping2);
//		MapperProjectHandler.getActiveProjectHandler().importSourceXSDFromJson(jsonFiles, "hdps_R01",null);
//		MapperProjectHandler.getActiveProjectHandler().importDestinationXSD("xsd2_3/ADT_A01.xsd","ADT_A01","urn:hl7-org:v2xml", "xsd2_3/segments.xsd","xsd2_3/fields.xsd", "xsd2_3/datatypes.xsd");
		
		MapperProjectHandler.importProject("M:/project/transformer/project/hdps_to_hl7", false);
		System.out.println(MapperProjectHandler.getActiveProjectHandler());
		MapperProjectHandler.getActiveProjectHandler().build("project/build", true, "HDPS_TO_HL7");
		
		
		//ZipUtil.decompress("project/build/HDPS_TO_HL7.zip", "project/build/HDPS_TO_HL7-decomp" );
        
        
 */       
		//SupportedTransformationTree stt  = new SupportedTransformationTree();
//		PluginFolderMonitor.startMonitor();
//		
//		Thread.currentThread().sleep(60000);
//		
//		System.out.println("Stopiing the monit");
//		PluginFolderMonitor.stopMonitor();
		


//		TransformationTreeModel ttm = new TransformationTreeModel();
//		
//		ttm.addTransformation2("transformationName1", "srcMsg1", "dstMsg1", "version1");
//		ttm.addTransformation2("transformationName1", "srcMsg2", "dstMsg2", "version1");
//		ttm.addTransformation2("transformationName1", "srcMsg2", "dstMsg2", "version2");
//		ttm.addTransformation2("transformationName1", "srcMsg2", "dstMsg2", "version3");
//		ttm.addTransformation2("transformationName1", "srcMsg3", "dstMsg3", "version3");
//		ttm.addTransformation2("transformationName1", "srcMsg1", "dstMsg1", "version1");
//		
//		ttm.addTransformation2("transformationName2", "srcMsg1", "dstMsg1", "version1");
//		ttm.addTransformation2("transformationName2", "srcMsg1", "dstMsg1", "version2");
//		ttm.addTransformation2("transformationName2", "srcMsg1", "dstMsg2", "version1");
//		
//		System.out.println(ttm);
//		ttm.removeTransformation("transformationName1");
//		ttm.addTransformation2("transformationName1", "srcMsg1", "dstMsg1", "version1");
//		ttm.addTransformation2("transformationName2", "srcMsg1", "dstMsg2", "version2");
//		System.out.println(ttm);
//		
//		System.out.println(ttm.getTransformationXslt("transformationName2", "srcMsg1", "dstMsg2", "version2"));
//		System.out.println(ttm.getTransformationXslt("transformationName2", "srcMsg1", "dstMsg2", "version3"));
//		
//		File plugs= Paths.get("./plugins").toFile();
//		for(String plug:plugs.list(DirectoryFileFilter.INSTANCE)){
//			File srcs = Paths.get("./plugins").resolve(plug).toFile();
//			for(String src:srcs.list(DirectoryFileFilter.INSTANCE)){
//				File dsts = Paths.get("./plugins").resolve(plug).resolve(src).toFile();
//				for(String dst:dsts.list(DirectoryFileFilter.INSTANCE)){
//					File vers = Paths.get("./plugins").resolve(plug).resolve(src).resolve(dst).toFile();
//					for(String ver:vers.list(DirectoryFileFilter.INSTANCE)){
//						System.out.println(plug+"  "+src+"  "+dst+"  "+ver);
//					}
//					
//				}
//				
//			}
//		}
//		
//		
//		System.out.println("_______________________________");
//		
//		String plugin_name = "HDPS_TO_HL7";
//		File srcs = Paths.get("./plugins").resolve(plugin_name).toFile();
//		for(String src:srcs.list(DirectoryFileFilter.INSTANCE)){
//			File dsts = Paths.get("./plugins").resolve(plugin_name).resolve(src).toFile();
//			for(String dst:dsts.list(DirectoryFileFilter.INSTANCE)){
//				File vers = Paths.get("./plugins").resolve(plugin_name).resolve(src).resolve(dst).toFile();
//				for(String ver:vers.list(DirectoryFileFilter.INSTANCE)){
//					System.out.println(plugin_name+"  "+src+"  "+dst+"  "+ver);
//				}
//				
//			}
//			
//		}
		
		TransformerEngine.startTransformationEngine();
		
		
		
		
	}
	
}
