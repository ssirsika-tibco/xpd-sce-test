package com.tibco.bx.debug.core.models.variable;

import java.io.IOException;

public interface ICDSSerializer {

	public String serialize(Object object) throws IOException;
	
}
