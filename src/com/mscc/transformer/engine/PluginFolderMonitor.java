package com.mscc.transformer.engine;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.nio.file.WatchEvent.Kind;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.mscc.transformer.engine.PluginsFolderEvent.kinds;

public class PluginFolderMonitor {
	
	private static volatile boolean running = true;
	
	public static synchronized void stopMonitor(){
		PluginFolderMonitor.running = false;
	}
	
	public static synchronized void startMonitor(){
		running = true;
		Thread t1 = new Thread(new Runnable() {
		     public void run() {
		    	 try {
		    		pluginFolder = TransformerEngine.getPluginFolder();
					PluginFolderMonitor.watchDirectoryPath(Paths.get(pluginFolder));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		     }
		});  
		t1.start();
	}
	
	private static String pluginFolder = null;
	private static Queue<PluginFolderMonitor> eventsQueue = new ConcurrentLinkedQueue<PluginFolderMonitor>();

	public static void watchDirectoryPath(Path path) throws IOException, InterruptedException {
		Path pluginsPath =  Paths.get(pluginFolder);
		if(!pluginsPath.getFileName().toFile().exists()){
			 
			FileUtils.forceMkdir(pluginsPath.getFileName().toFile());
		}
		// Sanity check - Check if path is a folder
		Boolean isFolder = (Boolean) Files.getAttribute(path,
				"basic:isDirectory", NOFOLLOW_LINKS);
		if (!isFolder) {
			throw new IllegalArgumentException("Path: " + path + " is not a folder");
		}
		
		System.out.println("Watching path: " + path);
		
		// We obtain the file system of the Path
		FileSystem fs = path.getFileSystem ();
		
		// We create the new WatchService using the new try() block
		WatchService service = fs.newWatchService();
			
		// We register the path to the service
		// We watch for creation events
		path.register(service, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
		
		
		// Start the infinite polling loop
		WatchKey key = null;
		Date dtime = null;
		PluginsFolderEvent folderEvent = null;
		while(PluginFolderMonitor.running) {
			key = service.poll();//.poll(60, TimeUnit.SECONDS);//.take();
			
			if(key!=null){
			// Dequeueing events
			Kind<?> kind = null;
			int cnt = 0;
			for(WatchEvent<?> watchEvent : key.pollEvents()) {
				dtime = new Date();
				Thread.currentThread().sleep(10);
				folderEvent = new PluginsFolderEvent();
				folderEvent.setTimeStamp(dtime);
				// Get the type of the event
				kind = watchEvent.kind();
				cnt = watchEvent.count();
				if (OVERFLOW == kind) {
					continue; //loop
				} else if (ENTRY_CREATE == kind) {
					// A new Path was created 
					Path newPath = ((WatchEvent<Path>) watchEvent).context();
					folderEvent.setFileName(newPath);
					folderEvent.setKind(kinds.Create);
					
					// for loop *TransformerEngine.tree.addTransformation(transformationName, src, dst, ver);
					TransformerEngine.refresh(newPath.getFileName().toString());
					
				}else if (ENTRY_MODIFY == kind) {
					// A new Path was created 
					Path newPath = ((WatchEvent<Path>) watchEvent).context();
					folderEvent.setFileName(newPath);
					folderEvent.setKind(kinds.Modify);
					
					//TransformerEngine.tree.removeTransformation(transformationName);
					// for loop *TransformerEngine.tree.addTransformation(transformationName, src, dst, ver);
					TransformerEngine.refresh(newPath.getFileName().toString());
				}else if (ENTRY_DELETE == kind) {
					// A new Path was created 
					Path newPath = ((WatchEvent<Path>) watchEvent).context();
					folderEvent.setFileName(newPath);
					folderEvent.setKind(kinds.Delete);
					
					//TransformerEngine.tree.removeTransformation(transformationName);
					TransformerEngine.delete(newPath.getFileName().toString());
				}
			
			if(!key.reset() || !PluginFolderMonitor.running) {
				break; //loop
			}
		}
		}	
		}
		System.out.println("Stop watching path: " + path + "...");
	}
	
	public static void parsePlugin(Path path){
		File plug = path.toFile();
		if(plug.isDirectory()){
			FileUtils.listFiles(plug, DirectoryFileFilter.DIRECTORY, TrueFileFilter.INSTANCE);
		}else{
			//try to decompress then delete
		}
		
		//TODO:parse plugin folder for structure
	}
}
