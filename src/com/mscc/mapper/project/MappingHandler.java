package com.mscc.mapper.project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.impl.xb.xsdschema.SchemaDocument;
import org.ini4j.Ini;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.mscc.schema.XSDGenerator;

public class MappingHandler {

	private ProjectHandler parentProject;
	private String mappingSource;

	public String getMappingSource() {
		return mappingSource;
	}

	public String getMappingDestination() {
		return mappingDestination;
	}

	public String getMappingVersion() {
		return mappingVersion;
	}

	private String mappingDestination;
	private String mappingVersion;
	private String mappingFolderPath;

	public String getMappingFolderPath() {
		return mappingFolderPath;
	}

	private String mappingdiscriptorFilePath;
	private String mappingFilePath;
	private String name;
	private String mappingSourceXSD;
	private String mappingDestinationXSD;
	private String rootFolder;

	public String getName() {
		return name;
	}

	public ProjectHandler getParentProject() {
		return parentProject;
	}

	public MappingHandler(MappingBean mapping, ProjectHandler parentProject) throws IOException {

		this.parentProject = parentProject;
		this.rootFolder = this.parentProject.getProjectFolderPath();
		this.mappingSource = mapping.getMappingSource();
		this.mappingDestination = mapping.getMappingDestination();
		this.mappingVersion = mapping.getMappingVersion();
		this.mappingFolderPath = this.mappingSource + "-To-" + this.mappingDestination + "/" + this.mappingVersion;
		Path folderPath = Paths.get(this.mappingFolderPath);
		this.mappingSourceXSD = folderPath.resolve("source.xsd").toString();
		this.mappingDestinationXSD = folderPath.resolve("destination.xsd").toString();

	}

	public void createMapping() throws IOException {

		Path rootFolderPath = Paths.get(this.rootFolder);
		// rootFolderPath.resolve().toString();

		Path folderPath = Paths.get(this.mappingFolderPath);
		// folderPath.resolve().toString();

		this.mappingdiscriptorFilePath = folderPath.resolve("config.dsc").toString();
		this.mappingFilePath = folderPath.resolve("map.xml").toString();
		this.name = this.mappingSource + "-To-" + this.mappingDestination + "-V" + this.mappingVersion;
		// creating mapping folder with appropriate version
		File mapping_folder = new File(rootFolderPath.resolve(this.mappingFolderPath).toString());
		if (!mapping_folder.exists()) {

			FileUtils.forceMkdir(mapping_folder);
		}
		// create mmaping file
		File map_file = (new File(rootFolderPath.resolve(this.mappingFilePath).toString()));
		if (!map_file.exists()) {
			FileUtils.touch(map_file);
		}
		// create empty xsd files
		File src_xsd_file = (new File(rootFolderPath.resolve(this.mappingSourceXSD).toString()));
		if (!src_xsd_file.exists()) {
			FileUtils.touch(src_xsd_file);
		}
		File dst_xsd_file = (new File(rootFolderPath.resolve(this.mappingDestinationXSD).toString()));
		if (!dst_xsd_file.exists()) {
			FileUtils.touch(dst_xsd_file);
		}

		// create and fill descriptor file
		File descriptor_file = (new File(rootFolderPath.resolve(this.mappingdiscriptorFilePath).toString()));
		if (!descriptor_file.exists()) {
			FileUtils.touch(descriptor_file);
		}

		Ini ini = new Ini(descriptor_file);
		ini.put("INFO", "mappingName", this.name);
		ini.put("INFO", "mappingVersion", this.mappingVersion);
		ini.put("MAPPING", "source", this.mappingSource);
		ini.put("MAPPING", "destination", this.mappingDestination);
		ini.put("MAPPING", "rootFolder", this.rootFolder);
		ini.put("MAPPING", "mappingFile", this.mappingFilePath);
		// ini.put("MAPPING", "sourceSchemaFile",
		// src_xsd_file.getAbsolutePath());
		// ini.put("MAPPING", "destinationSchemaFile",
		// dst_xsd_file.getAbsolutePath());
		// ini.put("MAPPING", "mappingFile", map_file.getAbsolutePath());

		ini.store();

	}

	public void loadMapping() throws IOException, MappingProjectException {

		Path rootFolderPath = Paths.get(this.rootFolder);
		// rootFolderPath.resolve().toString();
		Path folderPath = Paths.get(this.mappingFolderPath);
		// folderPath.resolve().toString();
		this.mappingdiscriptorFilePath = folderPath.resolve("config.dsc").toString();
		this.mappingFilePath = folderPath.resolve("map.xml").toString();
		this.name = this.mappingSource + "-To-" + this.mappingDestination + "-V" + this.mappingVersion;
		File mapping_folder = new File(rootFolderPath.resolve(this.mappingFolderPath).toString());
		if (mapping_folder.exists()) {
			File map_file = (new File(rootFolderPath.resolve(this.mappingFilePath).toString()));
			if (map_file.exists()) {

				File src_xsd_file = (new File(rootFolderPath.resolve(this.mappingSourceXSD).toString()));
				File dst_xsd_file = (new File(rootFolderPath.resolve(this.mappingDestinationXSD).toString()));
				if (src_xsd_file.exists() && dst_xsd_file.exists()) {

					File descriptor_file = (new File(rootFolderPath.resolve(this.mappingdiscriptorFilePath).toString()));
					if (descriptor_file.exists()) {
						Ini ini = new Ini(descriptor_file);
						this.name = ini.get("INFO", "mappingName");
						this.mappingVersion = ini.get("INFO", "mappingVersion");
						this.mappingSource = ini.get("MAPPING", "source");
						this.mappingFilePath = ini.get("MAPPING", "mappingFile");
					} else {
						throw new MappingProjectException("descriptor file not found in mapping path: " + this.mappingFolderPath);
					}
				} else {
					throw new MappingProjectException("either source or destination xsd file not found in mapping path: " + this.mappingFolderPath);
				}
			} else {
				throw new MappingProjectException("map file not found in mapping path: " + this.mappingFolderPath);
			}

		} else {
			throw new MappingProjectException("mapping folder in mapping path: " + this.mappingFolderPath);
		}

	}


	private void generateXSDFromXML(List<File> xmlFiles, String outputFilePath) throws XmlException, IOException {

		Path rootFolderPath = Paths.get(this.rootFolder);
		XSDGenerator xmlBeans = new XSDGenerator();
		SchemaDocument schemaDocument = xmlBeans.generateSchema(xmlFiles);
		FileWriter writer = new FileWriter(new File(rootFolderPath.resolve(outputFilePath).toString()));
		schemaDocument.save(writer, new XmlOptions().setSavePrettyPrint());
		writer.close();

	}

	private String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	private void generateXSDFromJson(List<String> jsonFiles, String outputFilePath, String rootName) throws JSONException, IOException, XmlException {

		List<File> tmpXmlFiles = new ArrayList<File>();
		for (String fileName : jsonFiles) {
			JSONObject json = new JSONObject(readFile(fileName, Charset.forName("utf-8")));
			String xml = XML.toString(json, rootName);
			File f = File.createTempFile(fileName, "_tmp.xml", new File("."));
			f.canWrite();
			FileWriter writer = new FileWriter(f);
			writer.write(xml);
			writer.flush();
			writer.close();
			tmpXmlFiles.add(f);
		}
		generateXSDFromXML(tmpXmlFiles, outputFilePath);
		for (File f : tmpXmlFiles) {
			f.delete();
		}

	}

	public void loadSourceXSDFromXml(List<String> xmlFiles) throws XmlException, IOException {

		List<File> inputFiles = new ArrayList<File>();
		for (String fileName : xmlFiles) {
			inputFiles.add(new File(fileName));
		}
		generateXSDFromXML(inputFiles, mappingSourceXSD);
	}

	public void loadDestinationXSDFromXml(List<String> xmlFiles) throws XmlException, IOException {
		List<File> inputFiles = new ArrayList<File>();
		for (String fileName : xmlFiles) {
			inputFiles.add(new File(fileName));
		}
		generateXSDFromXML(inputFiles, mappingDestinationXSD);
	}

	public void loadSourceXSDFromJson(List<String> jsonFiles) throws JSONException, IOException, XmlException {
		generateXSDFromJson(jsonFiles, mappingSourceXSD, mappingSource);
	}

	public void loadDestinationXSDFromJson(List<String> jsonFiles) throws JSONException, IOException, XmlException {
		generateXSDFromJson(jsonFiles, mappingDestinationXSD, mappingDestination);
	}

	public void loadSourceXSD(String xsdFile) throws IOException {
		Path rootFolderPath = Paths.get(this.rootFolder);

		FileUtils.copyFile(new File(xsdFile), new File(rootFolderPath.resolve(mappingSourceXSD).toString()));
	}

	public void loadDestinationXSD(String xsdFile) throws IOException {
		Path rootFolderPath = Paths.get(this.rootFolder);
		FileUtils.copyFile(new File(xsdFile), new File(rootFolderPath.resolve(mappingDestinationXSD).toString()));
	}

	@Override
	public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append("mappingSource=");
		sb.append(this.mappingSource);
		sb.append("\n");
		sb.append("mappingDestination=");
		sb.append(this.mappingDestination);
		sb.append("\n");
		sb.append("mappingVersion=");
		sb.append(this.mappingVersion);
		sb.append("\n");
		sb.append("mappingFolderPath=");
		sb.append(this.mappingFolderPath);
		sb.append("\n");
		sb.append("mappingdiscriptorFilePath=");
		sb.append(this.mappingdiscriptorFilePath);
		sb.append("\n");
		sb.append("mappingFilePath=");
		sb.append(this.mappingFilePath);
		sb.append("\n");
		sb.append("mappingSourceXSD=");
		sb.append(this.mappingSourceXSD);
		sb.append("\n");
		sb.append("mappingDestinationXSD=");
		sb.append(this.mappingDestinationXSD);
		sb.append("\n");
		sb.append("rootFolder=");
		sb.append(this.rootFolder);
		sb.append("\n");
		return sb.toString();
	}

}
