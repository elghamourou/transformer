package com.mscc.mapper.project;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.xmlbeans.XmlException;

public final class MapperProjectHandler {
	
	private Map<String, String> projects = new HashMap<String, String>();
	
	private static String activeProjectName;
	private static String activeProjectFolder;
	
	private static ProjectHandler activeProjectHandler;
	
	public static ProjectHandler getActiveProjectHandler() {
		return activeProjectHandler;
	}

	private static boolean projectBuilderInitialized = false;
	
	
	
	private MapperProjectHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public static void init(){
		if(!projectBuilderInitialized){
			
			//initialise config
			
			//initialize projects list
			
			//load last active project
			
			projectBuilderInitialized = true;
		}
	}
	
	public static void createProject(ProjectBean mappingProject) throws IOException{
		
		
		//create project handler
		ProjectHandler ph = new ProjectHandler(mappingProject);
		ph.createProject();
		
		
		//setting the project as active project
		MapperProjectHandler.activeProjectHandler = ph;
		
	}
	
	public static void deleteProject(String projectName){
		//load an existing project and set it as active one
	}

	public static void openProject(String projectName){
		MapperProjectHandler.activeProjectHandler.openProject(projectName);
	}
	
	public static void importProject(String projectPath, boolean isPackaged) throws MappingProjectException, IOException{
		ProjectHandler ph = new ProjectHandler(null);
		MapperProjectHandler.activeProjectHandler = ph;
		MapperProjectHandler.activeProjectHandler.importProject(projectPath, isPackaged);
	}
	
	public static void exportProject(String projectName, String exportTo, boolean isPackaged){
		MapperProjectHandler.activeProjectHandler.exportProject(projectName, exportTo, isPackaged);
	}
	
	public static void buildProject(String buildPath, boolean isPackaged) throws IOException{
		MapperProjectHandler.activeProjectHandler.build(buildPath, isPackaged);
	}
	
	
	
	public static void importSourceXSDFromXML(List<String> xmlFiles) throws XmlException, IOException{
		MapperProjectHandler.activeProjectHandler.importSourceXSDFromXML(xmlFiles);;
	}
	
	public static void importDestinationXSDFromXML(List<String> xmlFiles) throws XmlException, IOException{
		MapperProjectHandler.activeProjectHandler.importDestinationXSDFromXML(xmlFiles);
	}
	
	public static void importSourceXSDFromJson(List<String> jsonFiles) throws XmlException, IOException{
		MapperProjectHandler.activeProjectHandler.importSourceXSDFromJson(jsonFiles);
	}
	
	public static void importDestinationXSDFromJson(List<String> jsonFiles) throws XmlException, IOException{
		MapperProjectHandler.activeProjectHandler.importDestinationXSDFromJson(jsonFiles);;
	}
	
	public static void importSourceXSD(String xsdFile) throws XmlException, IOException{
		MapperProjectHandler.activeProjectHandler.importSourceXSD(xsdFile);;
	}
	
	public static void importDestinationXSD(String xsdFile) throws XmlException, IOException{
		MapperProjectHandler.activeProjectHandler.importDestinationXSD(xsdFile);;
	}
	
	
	public static void newVesrion(){
		//create new version
	}
}
