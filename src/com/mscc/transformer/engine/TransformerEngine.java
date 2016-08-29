package com.mscc.transformer.engine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;

import com.mscc.metaxslt.MetaXSLTTransformer;

public class TransformerEngine {
	
	 
	private static String pluginFolder = "plugins";
	public static String getPluginFolder() {
		return pluginFolder;
	}


	public static void setPluginFolder(String pluginFolder) {
		TransformerEngine.pluginFolder = pluginFolder;
	}


	public static TransformationTreeModel tree = new TransformationTreeModel();
	
	
	public static String transformMessage(String name, 
			String source, 
			String destination,
			String version,
			String message) throws TransformerException{
		
		
//		System.out.println("Transforming message...");
//		System.out.println("["
//				+name +", "
//				+source +", "
//				+destination +", "
//				+version + "]");
//		System.out.println("------------------Source message---------------------------");
//		System.out.println(message);
//		System.out.println("------------------Source message---------------------------");
//		
		
		String result = null;
		Path pf = Paths.get(pluginFolder);
		String transormer_xslt  = pf.resolve(tree.getTransformationXslt(name, source, destination, version)).toString();
		
		result = MetaXSLTTransformer.transorm(message, transormer_xslt);
		
		return result;
	}
	
	
	public static void startTransformationEngine() throws IOException{
		
		//parse plugin folder and build supported list
		parsePlugingsTree();
		
		//start monitor on plugin folder and make coresponding changes
		PluginFolderMonitor.startMonitor();
		
		
	}
	
	public static void stopTransformationEngine(){
		PluginFolderMonitor.stopMonitor();
		
		
	}
	public static List<Transformation> getSupportedTransformation(){
		
		return tree.getTranformationList();
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
//		System.out.println(tree);
//		System.out.println(tree.getTranformationList());
	}
	public static void delete(String transformationName){
		tree.removeTransformation(transformationName);
//		System.out.println(tree);
//		System.out.println(tree.getTranformationList());
	}
	
	private static void parsePlugingsTree() throws IOException{
		File plugs= Paths.get(pluginFolder).toFile();
		if (!plugs.exists()) {

			FileUtils.forceMkdir(plugs);
		}
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
