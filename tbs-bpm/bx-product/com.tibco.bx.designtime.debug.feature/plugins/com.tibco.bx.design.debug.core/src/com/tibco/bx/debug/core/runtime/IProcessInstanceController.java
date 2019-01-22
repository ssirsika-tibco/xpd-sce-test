package com.tibco.bx.debug.core.runtime;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import com.tibco.bx.debug.core.models.BxThread;
import com.tibco.bx.debug.core.models.IBxDebugListener;
import com.tibco.bx.debug.operation.ILauncherEventHandler;

public interface IProcessInstanceController extends ILauncherEventHandler, IBxDebugListener {

	public static final String SOAP_INPUT = "inputsoap"; //$NON-NLS-1$
	public static final String SOAP_OUTPUT = "ouputsoap"; //$NON-NLS-1$
	public static final String PARA_INPUT = "parainput"; //$NON-NLS-1$
	public static final String PARA_OUTPUT = "paraoutput"; //$NON-NLS-1$
	
	//void setProcessNode(ProcessNode processNode);
	
	Object getProcessNode();
	EObject getProcess();
	public String getProcessId();
	String getProcessInstanceId();
	void setProcessInstanceId(String processInstanceId);
	void setInputElement(Object inputElement);
	Object getInputElement();
	void setOutputElement(Object outputElement);
	String getErrorMessage();
	int getStatus();//debug event kind
	void dispose();
	boolean isSoapType();
	BxThread getThread(); 
	
	IStatus getRunningStatus();
	void setRunningStatus(IStatus runningStatus); 
	public List<EObject> getRunnedActivity();
	public Object getData4Show(String key);
	
}
