package com.tibco.bx.debug.core.models.variable;

import java.io.IOException;

public interface ICDSDeserializer {

	public Object deserialize(String value) throws IOException;
	public Object deserialize(String value,String pName) throws IOException;
	
}
