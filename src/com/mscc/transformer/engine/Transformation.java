package com.mscc.transformer.engine;

public class Transformation {

	String name;
	String source;
	String destination;
	String version;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "["
				+name +", "
				+source +", "
				+destination +", "
				+version + "]";
	}
}
