package com.tibco.bx.debug.core.models;

import org.eclipse.debug.core.model.IDebugElement;

import com.tibco.bx.debug.common.model.ProcessElement;
import com.tibco.bx.debug.core.runtime.IBxDebugger;

public interface IBxDebugElement extends IDebugElement{
	public IBxDebugger getDebugger();
	public ProcessElement getProcessElement();
	public String getElementType();
	public String getInstanceId();
	String TASK = "task"; //$NON-NLS-1$
	
	String PROCESS = "process";//$NON-NLS-1$
	
	String TRACK = "track";//$NON-NLS-1$
	
	String VARIABLE = "variable";//$NON-NLS-1$
	
	String VALUE = "value";//$NON-NLS-1$
	
	String TARGET = "target";//$NON-NLS-1$
	
}
