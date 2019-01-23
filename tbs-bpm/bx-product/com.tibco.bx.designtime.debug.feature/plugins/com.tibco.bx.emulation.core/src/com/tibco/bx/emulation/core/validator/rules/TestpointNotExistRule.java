package com.tibco.bx.emulation.core.validator.rules;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.core.validator.EmulationIssueIds;
import com.tibco.bx.emulation.core.validator.util.EmulationValidationUtil;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

public class TestpointNotExistRule implements IValidationRule{

	public TestpointNotExistRule() {
	}

	public Class<?> getTargetClass() {
		return Testpoint.class;
	}

	public void validate(IValidationScope validationScope, Object object) {
		//the corresponding Activity of current Testpoint must exist
		Testpoint testpoint = (Testpoint)object;
		ProcessNode processNode = testpoint.getProcessNode();
		EObject process = ProcessUtil.getProcess(processNode.getId(), processNode.getModelType());
		if(process != null){
			//TODO no sure resolveEObject(...) can work or not if testpoint id is not xpdlid
			EObject activity = WorkingCopyUtil.getWorkingCopyFor(process).resolveEObject(testpoint.getId());
			if(activity != null)
				return;
		}
		ArrayList<String> list = new ArrayList<String>();
		list.add(testpoint.getName());
		validationScope.createIssue(EmulationIssueIds.ACTIVITY_NOT_EXIST,
					EmulationValidationUtil.getLocation(processNode), testpoint.eResource().getURIFragment(testpoint),list);
		return;
	}

}
