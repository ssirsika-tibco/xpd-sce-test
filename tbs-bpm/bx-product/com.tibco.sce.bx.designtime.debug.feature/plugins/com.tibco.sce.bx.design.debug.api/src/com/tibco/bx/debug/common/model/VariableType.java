package com.tibco.bx.debug.common.model;

import org.eclipse.core.runtime.CoreException;
/**
 * Should be extended
 */
public abstract class VariableType {

	private String typeString;
	
	public VariableType(String typeString){
		this.typeString = typeString;
	}
	public String getTypeString(){
		return typeString;
	}
	
	public Object getValue(String valueString) throws CoreException {
		return valueString;
	}
}
