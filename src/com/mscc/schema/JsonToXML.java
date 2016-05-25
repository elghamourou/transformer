package com.mscc.schema;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;


public class JsonToXML {
	
	
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
	public static void demo(String jsonfilename, String rootname, String output) throws JSONException, IOException{
		JSONObject json = new JSONObject(readFile(jsonfilename, Charset.forName("utf-8")));
		String xml = XML.toString(json,rootname);
		FileWriter writer = new FileWriter(new File(output));
		writer.write(xml);
		writer.flush();writer.close();
		
	}
	

}
