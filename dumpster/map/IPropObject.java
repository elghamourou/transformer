package com.mscc.mapper.map;

import com.mscc.mapper.map.object.ObjectProperty;

public interface IPropObject {
	public int getPropertyCount();

	public void addProperty(String name, Object value);

	public void removeProperty(int index);

	public void removeProperty(ObjectProperty prop);

	public ObjectProperty getProperty(int index);

	public void propertyUpdated(ObjectProperty prop);

}
