package com.tibco.bx.debug.core.launching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.operation.ILauncherEventHandler;

public class PageflowProcessLauncher extends JMSProcessLauncher {

	protected ProcessTemplate processTemplate;

	public PageflowProcessLauncher(EObject startActivity, ProcessTemplate processTemplate, IBxProcessListing processListing) {
		super(startActivity, processTemplate, processListing);
		this.processTemplate = processTemplate;
	}

	@Override
	public Object launch(ILauncherEventHandler handler) {
		try {
			return processListing.createPageflowInstance(processTemplate, getParamterMap(), handler);
		} catch (CoreException e) {
			Throwable cause = e.getCause();
			cause.printStackTrace();
			return cause;
		}
	}

}
