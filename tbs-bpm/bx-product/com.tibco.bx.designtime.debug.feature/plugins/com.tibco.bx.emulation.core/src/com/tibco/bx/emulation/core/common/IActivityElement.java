package com.tibco.bx.emulation.core.common;

import org.eclipse.emf.ecore.EObject;

public interface IActivityElement {

	public String getProcessId();
	public String getProcessModelType();
	public IVariableElement[] getVariableElements();
	public void removeVariableElement(IVariableElement variableElement);
	public void clearVariableElements();
	public IVariableElement createVariableElement(EObject processVariable, String valueString);
	public String getVariableValueString(String name);
	public boolean isValid();
}
