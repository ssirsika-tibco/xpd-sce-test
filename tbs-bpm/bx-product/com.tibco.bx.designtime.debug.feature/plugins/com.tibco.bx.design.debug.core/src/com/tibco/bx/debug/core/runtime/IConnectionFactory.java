package com.tibco.bx.debug.core.runtime;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;

public interface IConnectionFactory {
	
	String getConnectionType();
	
	IConnection createConnection(Map<String, Object> connectionParameters) throws CoreException;

}
