package com.tibco.bx.emulation.core.common;

import org.eclipse.emf.ecore.EObject;

public interface IVariableElement {

	public String getValueString();
	
	public void setValueString(String value);
	
	public Object getValue();
	
	public String getName();
	
	public boolean hasVariables();
	
	public IVariableElement[] getVariableElements();
	
	public EObject getEMFCharacter();
	
	public Object getType();
	
	public String getScriptString();
	
	public IVariableElement getParent();
	
	public boolean isValid();
}
