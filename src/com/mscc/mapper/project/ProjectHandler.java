package com.mscc.mapper.project;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.xmlbeans.XmlException;
import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

public class ProjectHandler {
	private Map<String, MappingHandler> mappings = new HashMap<String, MappingHandler>();
	private String mappingProjectName = null;
	private String mappingProjectVersion = null;
	private String mappingProjectRoot = null;
	private String projectFolderPath = null;
	private MappingHandler activeMapping = null;
	public String getProjectFolderPath() {
		Path root = Paths.get(new File(this.mappingProjectRoot).toURI());
		//Path root = Paths.get(this.mappingProjectRoot);
		return root.resolve(projectFolderPath).toString();
	}

	private String projectDiscriptorFilePath;
	private String projectMappingListFilePath;
	public ProjectHandler(ProjectBean project) throws IOException {
		
		if(project != null){
			Path root = Paths.get(project.getMappingProjectRoot());
			
			this.mappingProjectName = project.getMappingProjectName();
			this.mappingProjectVersion = project.getMappingProjectVersion();
			this.mappingProjectRoot = project.getMappingProjectRoot();
			this.projectFolderPath = root.resolve(project.getMappingProjectName()).toString();
			
		}
		
	}

	public void createProject() throws InvalidFileFormatException, IOException{
		
		Path rootPath = Paths.get(this.projectFolderPath);
		
		this.projectDiscriptorFilePath =rootPath.resolve("config.dsc").toString();
		this.projectMappingListFilePath =rootPath.resolve("mappings.list").toString();
		File project_folder = new File(this.projectFolderPath);
		if(!project_folder.exists()){
			 
			FileUtils.forceMkdir(project_folder);
		}
		Path root = Paths.get(new File(this.mappingProjectRoot).toURI());
		String relative = root.relativize(Paths.get(project_folder.getAbsoluteFile().toURI())).toString();
		this.projectFolderPath = relative;
		File descriptor_file =(new File(this.projectDiscriptorFilePath));
		if(!descriptor_file.exists()){
			FileUtils.touch(descriptor_file);
		}
		Ini ini = new Ini(descriptor_file);
		ini.put("Configuration", "ProjectName", this.mappingProjectName);
        ini.put("Version", "major", this.mappingProjectVersion);
        ini.put("PROJECT", "Root", project_folder.getParentFile().getAbsolutePath());
        
        
        ini.put("PROJECT", "path", this.projectFolderPath);
        ini.store();
			
		//initializing mapping list and mmaping list file if doesn't exist
        File mapping_list = new File(this.projectMappingListFilePath);
		if(!mapping_list.exists()){
			 
			FileUtils.touch(mapping_list);
		}
       
	}
	
	public void createMapping(MappingBean mapping) throws IOException{
		
		//create mmaping object and set his parent project to this
		MappingHandler mh = new MappingHandler(mapping, this);
		mh.createMapping();
		//add mapping to mappingList
		File mapping_list = new File(this.projectMappingListFilePath);
		Writer out = new FileWriter(mapping_list, true);
        CSVPrinter csvPrinter = new CSVPrinter(out, CSVFormat.RFC4180.withDelimiter('\t'));
        csvPrinter.printRecord(mh.getMappingSource(),mh.getMappingDestination(), mh.getMappingVersion(), mh.getMappingFolderPath());
        csvPrinter.flush();
		
        
		//add mapping to mapinglist file
        this.mappings.put(mh.getName(), mh);
        this.activeMapping = mh;
        File descriptor_file =(new File(this.projectDiscriptorFilePath));
        Ini ini = new Ini(descriptor_file);
		ini.put("PROJECT", "activeMapping", mh.getMappingFolderPath());
        ini.store();
		
	}
	
	public void importMappingsToList() throws IOException, MappingProjectException{
		File mapping_list = new File(this.projectMappingListFilePath);
		Reader in = new FileReader(mapping_list);
        CSVParser csvParser = new CSVParser(in, CSVFormat.RFC4180.withDelimiter('\t'));
        
        File descriptor_file =(new File(this.projectDiscriptorFilePath));
        Ini ini = new Ini(descriptor_file);
		String defaultMappingPath = ini.get("PROJECT", "activeMapping");
        
        for(CSVRecord r: csvParser.getRecords()){
        	
        	MappingBean mapping = new MappingBean();
        	mapping.setMappingSource(r.get(0));
        	mapping.setMappingDestination(r.get(1));
        	mapping.setMappingVersion(r.get(2));
        	String thisMappingFolder = r.get(3);
        	//create mmaping object and set his parent project to this
    		MappingHandler mh = new MappingHandler(mapping, this);
    		mh.loadMapping();
    		//add mapping to mappingList
            this.mappings.put(mh.getName(), mh);
            if(defaultMappingPath.equalsIgnoreCase(thisMappingFolder)){
            	this.activeMapping = mh;
            }
        }
	}
	
	
	public void openProject(String projectName){
		//add project to list, load it  and set it as active one
	}
	
	public void importProject(String projectPath, boolean isPackaged) throws MappingProjectException, IOException{
		//import project from a path
		Path projPath = Paths.get(projectPath);
		
		String mappingProjectRoot = null;
		String projectFolderPath = projectPath;
		String projectDiscriptorFilePath =  projPath.resolve("config.dsc").toString();
		String projectMappingListFilePath =  projPath.resolve("mappings.list").toString();
		File project_folder = new File(projectFolderPath);
		File descriptor_file =(new File(projectDiscriptorFilePath));
		File mapping_list = new File(projectMappingListFilePath);
		
		if(project_folder.exists()){
			if(descriptor_file.exists()){
				if(mapping_list.exists()){
					Ini ini = new Ini(descriptor_file);
					this.mappingProjectName = ini.get("Configuration", "ProjectName");
					this.mappingProjectVersion = ini.get("Version", "major"); 
					this.mappingProjectRoot = project_folder.getParentFile().getAbsolutePath();
					
					Path root = Paths.get(new File(this.mappingProjectRoot).toURI());
					String relative = root.relativize(Paths.get(project_folder.getAbsoluteFile().toURI())).toString();
					this.projectFolderPath = relative;
					
					this.projectDiscriptorFilePath = projectDiscriptorFilePath;
					this.projectMappingListFilePath = projectMappingListFilePath;
					ini.put("PROJECT", "Root", this.mappingProjectRoot);
			        ini.put("PROJECT", "path", this.projectFolderPath);
			        ini.store();
			        importMappingsToList();

				}else{
					throw new MappingProjectException("Project mapping list file not found in project path: "+projectPath );
				}
			} else{
				throw new MappingProjectException("Project discriptor file note found not found in project path: "+projectPath);
			}
		}
		else{
			throw new MappingProjectException("Project root not found in project path: "+projectPath);
		}
	}
	
	public void exportProject(String projectName, String exportTo, boolean isPackaged){
		//export project to path
	}

	public void build(String buildPath, boolean isPackaged) throws IOException {
		//create build folder
		File buildRoot = new File(buildPath);
		if(!buildRoot.exists()){
			 
			FileUtils.forceMkdir(buildRoot);
		}
		Path bPath = Paths.get(buildPath);
		//create discriptor (manifest) file
		File descriptor_file =(new File(bPath.resolve("transformer.dsc").toString()));
		
		if(!descriptor_file.exists()){
			FileUtils.touch(descriptor_file);
		}
		
		Ini ini = new Ini(descriptor_file);
		ini.put("INFO", "TransformerName", this.mappingProjectName);
        ini.put("VERSION", "major", this.mappingProjectVersion);
        
        ini.store();
		
		//parse project mapping list and build coresponding transformer structure
		for (Map.Entry<String, MappingHandler> entry : mappings.entrySet()) {
		    String name = entry.getKey();
		    MappingHandler mh = entry.getValue();
		    String source = mh.getMappingSource();
		    String dst = mh.getMappingDestination();
		    String ver = mh.getMappingVersion();
		    
		    //creating mapping folders
		    File srcFolder = new File(bPath.resolve(source).toString());
			if(!srcFolder.exists()){
				 
				FileUtils.forceMkdir(srcFolder);
			}
			
			File dstFolder = new File(bPath.resolve(source+"/"+dst).toString());
			if(!dstFolder.exists()){
				 
				FileUtils.forceMkdir(dstFolder);
			}
			
			File verFolder = new File(bPath.resolve(source+"/"+dst+"/"+ver).toString());
			if(!verFolder.exists()){
				 
				FileUtils.forceMkdir(verFolder);
			}
			
			//parse coresponding mapping file to generate transformation xslt
		    
		}
	}
	
	public void importSourceXSDFromXML(List<String> xmlFiles) throws XmlException, IOException{
		this.activeMapping.loadSourceXSDFromXml(xmlFiles);
	}
	
	public void importDestinationXSDFromXML(List<String> xmlFiles) throws XmlException, IOException{
		this.activeMapping.loadDestinationXSDFromXml(xmlFiles);
	}
	
	public void importSourceXSDFromJson(List<String> jsonFiles) throws XmlException, IOException{
		this.activeMapping.loadSourceXSDFromJson(jsonFiles);
	}
	
	public void importDestinationXSDFromJson(List<String> jsonFiles) throws XmlException, IOException{
		this.activeMapping.loadDestinationXSDFromJson(jsonFiles);
	}
	
	public void importSourceXSD(String xsdFile) throws XmlException, IOException{
		this.activeMapping.loadSourceXSD(xsdFile);
	}
	
	public void importDestinationXSD(String xsdFile) throws XmlException, IOException{
		this.activeMapping.loadDestinationXSD(xsdFile);
	}
	
	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("mappingProjectName=");
		sb.append(this.mappingProjectName);
		sb.append("\n");
		sb.append("mappingProjectVersion=");
		sb.append(this.mappingProjectVersion);
		sb.append("\n");
		sb.append("mappingProjectRoot=");
		sb.append(this.mappingProjectRoot);
		sb.append("\n");
		sb.append("projectFolderPath=");
		sb.append(this.projectFolderPath);
		sb.append("\n");
		if(this.activeMapping != null){
			sb.append("activeMapping=");
			sb.append("[");
			sb.append(this.activeMapping.getMappingSource());
			sb.append("---->");
			sb.append(this.activeMapping.getMappingDestination());
			sb.append("] -- V");
			sb.append(this.activeMapping.getMappingVersion());
			sb.append("\n");
		}else{
			sb.append("No activeMapping is set");
			sb.append("\n");
		}
		
		sb.append("------------Mappings---------------");
		sb.append("\n");
		for (Map.Entry<String, MappingHandler> entry : this.mappings.entrySet()) {
			sb.append("[");
			sb.append(entry.getKey());
			sb.append("]\n");
		    sb.append(entry.getValue().toString());
		    sb.append("****************************\n");
		}
		
		
		
		return sb.toString();
	}
}
