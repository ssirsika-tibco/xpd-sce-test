package com.tibco.bx.emulation.core.validator.rules;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.core.validator.EmulationIssueIds;
import com.tibco.bx.emulation.core.validator.util.EmulationValidationUtil;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

public class ProcessNodeNotExistRule implements IValidationRule{

	public ProcessNodeNotExistRule() {
	}

	public Class<?> getTargetClass() {
		return ProcessNode.class;
	}

	public void validate(IValidationScope validationScope, Object object) {
		//the corresponding Process of current ProcessNode must exist
		ProcessNode processNode = (ProcessNode)object;
		EObject process = ProcessUtil.getProcess(processNode.getId(), processNode.getModelType());
		if(process == null){
			ArrayList<String> list = new ArrayList<String>();
			list.add(processNode.getName());
			validationScope.createIssue(EmulationIssueIds.PROCESS_NOT_EXIST, 
					EmulationValidationUtil.getLocation(processNode), EmulationValidationUtil.getURIFragment(processNode),list);
		}
		return;
	}

}
