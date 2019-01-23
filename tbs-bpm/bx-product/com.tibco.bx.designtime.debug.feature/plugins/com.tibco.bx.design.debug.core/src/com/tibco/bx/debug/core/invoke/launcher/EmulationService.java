package com.tibco.bx.debug.core.invoke.launcher;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

public interface EmulationService {

	public void setInput(Object input,EObject startActivity, Map<String, String> parametersMap);

	public void afterMultiLauncher(String responseSoap,EObject startActivity, Object processNode);

	public Object beforeLauncher(EObject startActivity,String requestSoap,Object processNode,EObject process);
}
