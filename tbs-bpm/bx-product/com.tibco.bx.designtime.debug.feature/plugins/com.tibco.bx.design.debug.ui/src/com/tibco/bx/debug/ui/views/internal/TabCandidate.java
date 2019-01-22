package com.tibco.bx.debug.ui.views.internal;


import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.core.invoke.util.StringUtils;
import com.tibco.bx.debug.core.runtime.IProcessInstanceController;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.ui.wizards.IResultCandidate;
import com.tibco.bx.emulation.model.ProcessNode;

public class TabCandidate implements IResultCandidate{
	private IProcessInstanceController controller;
	
	
	public TabCandidate(IProcessInstanceController controller) {
		this.controller = controller;
	}

	@Override
	public ProcessNode getProcessNode() {
		if(controller.getProcessNode() !=null){
			return (ProcessNode)controller.getProcessNode();
		}
		return null;
	}

	@Override
	public String toString() {
		EObject process = controller.getProcess();
		String instanceId = controller.getProcessInstanceId();
		if(StringUtils.isNotBlank(instanceId)){
			return ProcessUtil.getDisplayName(process) + "[" + (instanceId == null? "" : instanceId) + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return ProcessUtil.getDisplayName(process) ;
		
	}
}