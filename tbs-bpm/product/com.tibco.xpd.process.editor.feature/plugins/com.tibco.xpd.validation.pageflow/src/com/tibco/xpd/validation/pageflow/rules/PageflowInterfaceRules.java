/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.validation.pageflow.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * Rules for pageflows that implement process interfaces.
 * 
 * @author aallway
 * @since 11 May 2011
 */
public class PageflowInterfaceRules extends ProcessValidationRule {

    private static final String ISSUE_CANNOT_IMPLEMENT_MESSAGE_INTERFACE =
            "pageflow.cannotImplementMessageInterface"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {
        /*
         * Check if it implements a process interface
         */
        ProcessInterface processInterface =
                ProcessInterfaceUtil.getImplementedProcessInterface(process);

        if (processInterface != null) {
            /*
             * Pageflow cannot implement an interface that has message events.
             */
            if (hasMessageEvents(processInterface)) {
                addIssue(ISSUE_CANNOT_IMPLEMENT_MESSAGE_INTERFACE, process);
            }
        }

        return;
    }

    /**
     * @param processInterface
     * @return <code>true</code> if interface contains message events.
     */
    private boolean hasMessageEvents(ProcessInterface processInterface) {
        boolean hasMessageEvents = false;

        for (StartMethod startMethod : processInterface.getStartMethods()) {
            if (TriggerType.MESSAGE_LITERAL.equals(startMethod.getTrigger())) {
                hasMessageEvents = true;
                break;
            }
        }

        if (!hasMessageEvents) {
            for (IntermediateMethod intermediateMethod : processInterface
                    .getIntermediateMethods()) {
                if (TriggerType.MESSAGE_LITERAL.equals(intermediateMethod
                        .getTrigger())) {
                    hasMessageEvents = true;
                    break;
                }
            }
        }
        return hasMessageEvents;
    }

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList,
     *      org.eclipse.emf.common.util.EList)
     * 
     * @param process
     * @param activities
     * @param transitions
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        // not required.
    }

}
