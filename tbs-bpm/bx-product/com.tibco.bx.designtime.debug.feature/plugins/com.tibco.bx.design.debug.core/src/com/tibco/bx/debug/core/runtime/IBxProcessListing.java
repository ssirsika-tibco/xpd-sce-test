package com.tibco.bx.debug.core.runtime;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.debug.common.model.IBxProcessActivity;
import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.operation.ILauncherEventHandler;
import com.tibco.bx.debug.operation.StarterOperation;

public interface IBxProcessListing {

	ProcessTemplate[] getProcessTemplates() throws CoreException;

	ProcessTemplate[] getProcessTemplates(String processId) throws CoreException;
	
	void registerProcessTemplate(ProcessTemplate processTemplate);
	
	ProcessInstance getProcessInstance(String processInstanceId) throws CoreException;
	
	void addProcessInstance(ProcessInstance instance) throws CoreException;
	
	StarterOperation[] listStarterOperations(ProcessTemplate processTemplate) throws CoreException;
	
    String createProcessInstance(ProcessTemplate processTemplate, String starterOperationName,
			Map<String, String> parametersMap, ILauncherEventHandler handler) throws CoreException;
    
    IBxProcessActivity[] getActivities(String processInstanceId) throws CoreException;
    
    IConnection getConnection();

    boolean isProcessManagerValid();
    
    String createPageflowInstance(ProcessTemplate processTemplate, Map<String, String> parametersMap, ILauncherEventHandler handler)
			throws CoreException;
}
