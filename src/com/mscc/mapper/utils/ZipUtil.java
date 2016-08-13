package com.mscc.mapper.utils;

import org.apache.commons.compress.archivers.ArchiveException; 
import org.apache.commons.compress.archivers.ArchiveInputStream; 
import org.apache.commons.compress.archivers.ArchiveOutputStream; 
import org.apache.commons.compress.archivers.ArchiveStreamFactory; 
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry; 
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.*; 
import java.net.URL; 
import java.util.ArrayList;
import java.util.Collection;
import java.util.List; 
 
/**
 * ZipUtil.java 
 * <p/> 
 * Utility class dependent on commons-compress and commons-io to 
 * handle my zip compression needs. 
 * 
 * @author Jason Ferguson 
 */ 
public class ZipUtil { 
 
    public static void decompress(String pluginArchiveFile, String dstFolder) throws 
            FileNotFoundException, 
            ArchiveException, 
            IOException { 
 
    	File  zipFile = new File(pluginArchiveFile);
    	File destination = new File(dstFolder);
    	FileUtils.forceMkdir(destination);
 
        // create the input stream for the file, then the input stream for the actual zip file 
        final InputStream is = new FileInputStream(zipFile); 
        ArchiveInputStream ais = new 
                ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.ZIP, 
                is); 
 
        // cycle through the entries in the zip archive and write them to the system temp dir 
        ZipArchiveEntry entry = (ZipArchiveEntry) ais.getNextEntry(); 
        while (entry != null) { 
            File outputFile = new File(destination, entry.getName());   // don't do this anonymously, need it for the list
            FileUtils.forceMkdir(new File(outputFile.getParent()));
            OutputStream os = new FileOutputStream(outputFile); 
 
            IOUtils.copy(ais, os);  // copy from the archiveinputstream to the output stream 
            os.close();     // close the output stream 
 
 
            entry = (ZipArchiveEntry) ais.getNextEntry(); 
        } 
 
        ais.close(); 
        is.close(); 
 
    } 
 
   
    
    
    
    
    
    
    
    
    
    
    private static String getEntryName(File source, File file) throws IOException {
        int index = source.getAbsolutePath().length() + 1;
        String path = file.getCanonicalPath();

        return path.substring(index);
    }
    
    
    
    public static void compress(String sourceFile, String destFile) throws ArchiveException, IOException{
    	File source = new File(sourceFile);
		File destination = new File(destFile);
		OutputStream archiveStream = new FileOutputStream(destination);
        ArchiveOutputStream archive = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, archiveStream);
		Collection<File> fileList = FileUtils.listFiles(new File("project/build"+"/HDPS_TO_HL7"), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE );
		for (File file : fileList) {
            String entryName = getEntryName(source, file);
            ZipArchiveEntry entry = new ZipArchiveEntry(entryName);
            archive.putArchiveEntry(entry);

            BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));

            IOUtils.copy(input, archive);
            input.close();
            archive.closeArchiveEntry();
        }
		archive.finish();
        archiveStream.close();
    }
    
    
    
    
    
    
    
    
    
    
    
    
 
}

