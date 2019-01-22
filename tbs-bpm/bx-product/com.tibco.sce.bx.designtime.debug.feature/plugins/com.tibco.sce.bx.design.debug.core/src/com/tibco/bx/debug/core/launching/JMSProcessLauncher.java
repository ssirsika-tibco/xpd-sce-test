package com.tibco.bx.debug.core.launching;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.invoke.launcher.EmulationService;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.operation.ILauncherEventHandler;
import com.tibco.bx.debug.operation.StarterOperation;

public class JMSProcessLauncher extends AbstractProcessLauncher {

	protected Map<String, String> parametersMap = new HashMap<String, String>();

	protected ProcessTemplate processTemplate;

	protected StarterOperation starterOperation;

	public JMSProcessLauncher(EObject startActivity, ProcessTemplate processTemplate, IBxProcessListing processListing) {
		super(startActivity, processListing);
		this.processTemplate = processTemplate;
	}

	private StarterOperation getStartOperation() {

		StarterOperation[] starterOperations;
		try {
			starterOperations = processListing.listStarterOperations(processTemplate);
			// TODO here will be a bug
			if (starterOperations.length > 0) {
				return starterOperations[0];
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setProcessListing(IBxProcessListing processListing) {
		super.setProcessListing(processListing);
		starterOperation = getStartOperation();
	}

	@Override
	public boolean isAvailable() {
		return super.isAvailable() && starterOperation != null;
	}

	@Override
	public Object launch(ILauncherEventHandler handler) {
		try {
			if (getStartOperation() != null) {
				return processListing.createProcessInstance(processTemplate, getStartOperation().getOperationName(), getParamterMap(), handler);
			}
			return null;
		} catch (CoreException e) {
			Throwable cause = e.getCause();
			cause.printStackTrace();
			return cause;
		}
	}

	public void setInput(Object input) {
		super.setInput(input);
		EmulationService service = DebugCoreActivator.createEmulationService();
		if(service !=null){
			service.setInput(input, getStartActivity(), parametersMap);
		}
	}

	public Map<String, String> getParamterMap() {
		return parametersMap;
	}

	@Override
	public ProcessTemplate getProcessTemplate() {
		return processTemplate;
	}


}
