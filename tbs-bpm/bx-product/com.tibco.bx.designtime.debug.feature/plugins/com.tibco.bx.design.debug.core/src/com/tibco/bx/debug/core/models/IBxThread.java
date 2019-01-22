package com.tibco.bx.debug.core.models;

import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;

public interface IBxThread extends IBxDebugElement, IThread, IStackFrame {

	public void addStackFrame(IBxStackFrame stackFrame);

	public IBxStackFrame getStackFrame(String instanceId);

	public void setTopStackFrame(IBxStackFrame stackFrame);
	
	public IBxStackFrame[] getAllStackFrames();
	
	public void setSuspended(boolean suspended);
}