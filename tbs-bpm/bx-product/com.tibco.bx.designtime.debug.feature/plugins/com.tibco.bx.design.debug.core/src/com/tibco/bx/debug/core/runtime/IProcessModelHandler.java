package com.tibco.bx.debug.core.runtime;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.core.ws.finder.WSProperties;

public interface IProcessModelHandler {

	Class getProcessClass();

	String getElementId(EObject object);

	String getDisplayName(EObject element);
	
	String getElementName(EObject object);

	EObject getProcess(String processId);

	EObject getActivity(EObject process, String activityId);

	EObject getLink(EObject process, String linkId);

	boolean isIntermediateEvent(EObject activity);

	boolean isWebServiceImplementationActivity(EObject startActivity);
	
	WSProperties getWSProperties(EObject startActivity);

	String getWebServiceUri(EObject startActivity);
	
	EObject[] getCanStartActivities(EObject currentProcess);

	List<EObject> getAllVariables(EObject process);

	String getVariableTypeName(EObject variable);
	
	String getVariableName(EObject variable);

	boolean isArrayVariable(EObject variable);

	EObject getLink(String linkId, EObject process);

	EObject[] getProcesses(IFile element);
	
	boolean isEndActivity(EObject activity);

}
