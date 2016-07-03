package com.mscc.mapper.project;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.TransformerException;

import org.apache.xmlbeans.XmlException;
import org.json.JSONException;

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
	
	
	
	public static void importSourceXSDFromXML(List<String> xmlFiles, String root, String NameSpace) throws XmlException, IOException, TransformerException{
		MapperProjectHandler.activeProjectHandler.importSourceXSDFromXML(xmlFiles, root, NameSpace);;
	}
	
	public static void importDestinationXSDFromXML(List<String> xmlFiles, String root, String NameSpace) throws XmlException, IOException, TransformerException{
		MapperProjectHandler.activeProjectHandler.importDestinationXSDFromXML(xmlFiles, root, NameSpace);
	}
	
	public static void importSourceXSDFromJson(List<String> jsonFiles, String root, String NameSpace) throws XmlException, IOException, JSONException, TransformerException{
		MapperProjectHandler.activeProjectHandler.importSourceXSDFromJson(jsonFiles, root, NameSpace);
	}
	
	public static void importDestinationXSDFromJson(List<String> jsonFiles, String root, String NameSpace) throws XmlException, IOException, JSONException, TransformerException{
		MapperProjectHandler.activeProjectHandler.importDestinationXSDFromJson(jsonFiles, root, NameSpace);;
	}
	
	public static void importSourceXSD(String xsdFile, String root, String NameSpace, String... xsdFileDeps) throws XmlException, IOException, TransformerException{
		MapperProjectHandler.activeProjectHandler.importSourceXSD(xsdFile, root, NameSpace, xsdFileDeps);
	}
	
	public static void importDestinationXSD(String xsdFile, String root, String NameSpace, String... xsdFileDeps) throws XmlException, IOException, TransformerException{
		MapperProjectHandler.activeProjectHandler.importDestinationXSD(xsdFile, root, NameSpace, xsdFileDeps);;
	}
	
	
	public static void newVesrion(){
		//create new version
	}
}
