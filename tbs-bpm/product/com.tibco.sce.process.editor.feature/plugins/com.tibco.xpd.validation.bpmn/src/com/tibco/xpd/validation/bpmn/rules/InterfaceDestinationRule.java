/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * @author rsomayaj
 * 
 * 
 */
public class InterfaceDestinationRule extends ProcessValidationRule {

	private static final String ID = "bpmn.processInterfaceDestination"; //$NON-NLS-1$

	@Override
	public void validate(Process process) {
		ProcessInterface processInterface = ProcessInterfaceUtil
				.getImplementedProcessInterface(process);
		if (processInterface != null) {
			Set<String> enabledDestinations = DestinationUtil
					.getEnabledGlobalDestinations(process);
			for (String enabledDestination : enabledDestinations) {
				if (!DestinationUtil.isGlobalDestinationEnabled(processInterface,
						enabledDestination))
					addIssue(ID, process);
			}
		}
	}

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) { 
    }
}
