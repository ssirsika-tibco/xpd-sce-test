package com.tibco.bx.debug.core.invoke.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;

import com.tibco.xpd.xpdl2.Process;

public class ProcessTemplateManager {

	public static ProcessTemplateManager instance = new ProcessTemplateManager();

	private Map<EObject, ProcessTemplate> cacheProcessTempates;

	private ProcessTemplateManager() {
		cacheProcessTempates = new HashMap<EObject, ProcessTemplate>();
	}

	public ProcessTemplate getProcessTemplate(EObject process, IBxProcessListing processListing) {
		ProcessTemplate template = cacheProcessTempates.get(process);
		if (template == null) {
			try {
				ProcessTemplate[] templates = processListing.getProcessTemplates();
				String processId = ((Process) process).getId();
				for (ProcessTemplate temp : templates) {
					if (processId.equals(temp.getProcessId())) {
						template = temp;
						break;
					}
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return template;
	}

	public void put(EObject process, ProcessTemplate template) {
		cacheProcessTempates.put(process, template);
	}
}
