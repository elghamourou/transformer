package com.mscc.transformer.engine;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.filefilter.DirectoryFileFilter;

public class TransformerEngine {
	
	 
	private static String pluginFolder = "plugins";
	public static String getPluginFolder() {
		return pluginFolder;
	}


	public static void setPluginFolder(String pluginFolder) {
		TransformerEngine.pluginFolder = pluginFolder;
	}


	public static TransformationTreeModel tree = new TransformationTreeModel();
	
	
	public static String transformMessage(String source, 
			String transformation, 
			String sourceMessageTrigger,
			String hl7MessageTypeTrigger,
			String verssion){
		String result = null;
		
		
		
		
		return result;
	}
	
	
	public static void startTransformationEngine(){
		
		//parse plugin folder and build supported list
		parsePlugingsTree();
		
		//start monitor on plugin folder and make coresponding changes
		PluginFolderMonitor.startMonitor();
		
		
	}
	
	public static void stopTransformationEngine(){
		PluginFolderMonitor.stopMonitor();
		
		
	}
	public List<String> getSupportedTransformation(){
		
		return null;
	}
	
	
	public static void refresh(String transformationName){
		tree.removeTransformation(transformationName);
		File srcs = Paths.get(pluginFolder).resolve(transformationName).toFile();
		for(String src:srcs.list(DirectoryFileFilter.INSTANCE)){
			File dsts = Paths.get(pluginFolder).resolve(transformationName).resolve(src).toFile();
			for(String dst:dsts.list(DirectoryFileFilter.INSTANCE)){
				File vers = Paths.get(pluginFolder).resolve(transformationName).resolve(src).resolve(dst).toFile();
				for(String ver:vers.list(DirectoryFileFilter.INSTANCE)){
					
					tree.addTransformation(transformationName, src, dst, ver);
				}
				
			}
			
		}
	}
	public static void delete(String transformationName){
		tree.removeTransformation(transformationName);
	}
	
	private static void parsePlugingsTree(){
		File plugs= Paths.get(pluginFolder).toFile();
		for(String plug:plugs.list(DirectoryFileFilter.INSTANCE)){
			File srcs = Paths.get(pluginFolder).resolve(plug).toFile();
			for(String src:srcs.list(DirectoryFileFilter.INSTANCE)){
				File dsts = Paths.get(pluginFolder).resolve(plug).resolve(src).toFile();
				for(String dst:dsts.list(DirectoryFileFilter.INSTANCE)){
					File vers = Paths.get(pluginFolder).resolve(plug).resolve(src).resolve(dst).toFile();
					for(String ver:vers.list(DirectoryFileFilter.INSTANCE)){
						tree.addTransformation(plug, src, dst, ver);
					}
					
				}
				
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
}
