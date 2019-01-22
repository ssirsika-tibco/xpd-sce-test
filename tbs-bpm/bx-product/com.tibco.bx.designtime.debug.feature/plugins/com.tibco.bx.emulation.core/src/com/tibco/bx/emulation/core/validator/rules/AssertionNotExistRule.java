package com.tibco.bx.emulation.core.validator.rules;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.core.validator.EmulationIssueIds;
import com.tibco.bx.emulation.core.validator.util.EmulationValidationUtil;
import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

public class AssertionNotExistRule implements IValidationRule{

	public AssertionNotExistRule() {
	}

	public Class<?> getTargetClass() {
		return Assertion.class;
	}

	public void validate(IValidationScope validationScope, Object object) {
		//the corresponding Sequence of current Assertion must exist
		Assertion assertion = (Assertion)object;
		ProcessNode processNode = assertion.getProcessNode();
		EObject process = ProcessUtil.getProcess(processNode.getId(), processNode.getModelType());
		if(process != null){
			EObject activity = WorkingCopyUtil.getWorkingCopyFor(process).resolveEObject(assertion.getId());
			if(activity != null)
				return;
		}
		ArrayList<String> list = new ArrayList<String>();
		list.add(assertion.getName());
		validationScope.createIssue(EmulationIssueIds.SEQUENCE_NOT_EXIST,
					EmulationValidationUtil.getLocation(processNode), assertion.eResource().getURIFragment(assertion),list);
		return;
	}

}
