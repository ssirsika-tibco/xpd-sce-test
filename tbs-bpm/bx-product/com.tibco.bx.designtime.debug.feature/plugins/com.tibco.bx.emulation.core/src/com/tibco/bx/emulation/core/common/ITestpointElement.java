package com.tibco.bx.emulation.core.common;

import com.tibco.bx.emulation.model.Testpoint;

public interface ITestpointElement extends IActivityElement{

	public Testpoint getTestpoint();
	public String getValueString();
	public String getVariableValueString(String valiableName);
}
