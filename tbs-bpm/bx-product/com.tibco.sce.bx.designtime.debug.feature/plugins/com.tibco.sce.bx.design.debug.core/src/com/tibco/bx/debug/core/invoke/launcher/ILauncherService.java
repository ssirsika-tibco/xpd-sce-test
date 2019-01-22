package com.tibco.bx.debug.core.invoke.launcher;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Operation;

import com.tibco.bx.debug.operation.ILauncherEventHandler;


public interface ILauncherService {
	
	String getEndPointDialog();
	
	boolean isEmulationProcessInstanceController(ILauncherEventHandler handler);

	Operation getOperationFromUrl(EObject startActivity);

	void connectionClosed();

	Object getThreadAdapter(Class adapter);

	Object getStackFrameAdapter(Class adapter);
}
