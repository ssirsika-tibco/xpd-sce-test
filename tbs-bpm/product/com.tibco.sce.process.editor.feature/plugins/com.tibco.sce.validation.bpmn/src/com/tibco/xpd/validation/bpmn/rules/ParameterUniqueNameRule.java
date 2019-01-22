/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;

/**
 * @author rsomayaj
 * 
 * 
 */
public class ParameterUniqueNameRule extends ProcessValidationRule {

	private static final String ID = "bpmn.uniqueParameterNames"; //$NON-NLS-1$
	@Override
	public void validate(Process process) {
		ProcessInterface processInterface = ProcessInterfaceUtil
				.getImplementedProcessInterface(process);
		if (processInterface != null) {
			List<FormalParameter> parameters = process
					.getFormalParameters();
			for (FormalParameter parameter : parameters) {
				if (EMFSearchUtil.findInList(processInterface.getFormalParameters(),
						Xpdl2Package.eINSTANCE.getNamedElement_Name(),
						parameter.getName()) != null) {
						addIssue(ID, parameter);
				}
			}
		}
	}
    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) {        
    }
}
