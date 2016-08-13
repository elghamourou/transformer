package com.mscc.transformer.engine;

import java.nio.file.Path;
import java.util.Date;

public class PluginsFolderEvent {
	
	
	public enum kinds {Create, Modify, Delete};
	private kinds kind = null;
	private Path fileName = null;
	private Date timeStamp = null;
	public kinds getKind() {
		return kind;
	}
	public void setKind(kinds kind) {
		this.kind = kind;
	}
	public Path getFileName() {
		return fileName;
	}
	public void setFileName(Path fileName) {
		this.fileName = fileName;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "["+timeStamp.getTime()+"]"+" Event: ("+ kind + ") path: ("+ fileName+")";
	}

}
