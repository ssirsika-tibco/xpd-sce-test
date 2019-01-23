package com.tibco.bx.debug.core.runtime.eventhandlers;

import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.core.runtime.events.BxProcessCreationEvent;
import com.tibco.bx.debug.core.runtime.events.BxRuntimeEvent;

public class StartEventHandler implements IRuntimeEventHandler {

	private final IBxDebugTarget debugTarget;

	public StartEventHandler(IBxDebugTarget debugTarget) {
		this.debugTarget = debugTarget;
	}

	@Override
	public boolean handleRuntimeEvent(BxRuntimeEvent re) throws CoreException {
		BxProcessCreationEvent event = (BxProcessCreationEvent) re;
		
		ProcessTemplate template = getProcessTemplate(event.getProcessTemplateId());
		ProcessInstance instance = new ProcessInstance(template, event.getProcessInstanceId());
		instance.setState(event.getState());
		instance.setStarter(event.isStarter());
		
		IBxProcessListing processListing = debugTarget.getProcessListing();
		if (processListing != null) {
			processListing.addProcessInstance(instance);
		}

		debugTarget.createBxThread(instance);
		return true;
	}

	private ProcessTemplate getProcessTemplate(String processId) throws CoreException {
		IBxProcessListing processListing = (IBxProcessListing) debugTarget.getProcessListing();
		if (processListing != null) {
			ProcessTemplate[] templates = processListing.getProcessTemplates(processId);
			switch (templates.length) {
			case 0: break;
			case 1: return templates[0];
			default: 
				Arrays.sort(templates, new Comparator<ProcessTemplate>() {
					@Override
					public int compare(ProcessTemplate t1, ProcessTemplate t2) {
						return t2.getModuleVersion().compareTo(t1.getModuleVersion());
					}
				});
				return templates[0];
			}
		}
		
		//if we get this far, we didn't find a valid process template. So return a dummy one
		return new ProcessTemplate(processId, "Unknown Process", "Unknown Module", "1.0.0"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	}
}
