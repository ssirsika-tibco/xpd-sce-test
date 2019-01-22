package com.tibco.bx.debug.core.models;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;

import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.core.models.variable.IVariableHandler;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;

public interface IBxDebugTarget extends IBxDebugElement, IDebugTarget{

	public IBxThread getCurrentThread() throws DebugException;
	public IBxThread getThread(String processInstance);
	public IBxThread createBxThread(ProcessInstance instance) throws CoreException;
	public void addBxTheadChangedListener(String processInstanceId, IBxDebugListener listener);
	public IBxProcessListing  getProcessListing();
	public IVariableHandler getVariableHandler();
	public void fireBxThreadChanged(String instance, BxDebugEvent bxDebugEvent);
	public String getModelType();
}
