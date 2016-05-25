package com.mscc.schema;

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
	
	public static void main(String[] args) throws JSONException, IOException {
		JSONObject json = new JSONObject(readFile("hdps/hdpsJson-3.json", Charset.forName("utf-8")));
		String xml = XML.toString(json,"hdps");
		System.out.println(xml);
	}

}
