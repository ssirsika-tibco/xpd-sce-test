package com.tibco.bx.emulation.core.common;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.emulation.model.InOutDataType;


public interface IInOutElement extends IActivityElement{

	public InOutDataType getInOutDataType();
	public String getVariableValueString(String valiableName);
	public void setSoapMessage(String soapMessage);
	public String getSoapMessage();
	public boolean isVisual(EObject prd);
	public IInOutElement clone();
}
