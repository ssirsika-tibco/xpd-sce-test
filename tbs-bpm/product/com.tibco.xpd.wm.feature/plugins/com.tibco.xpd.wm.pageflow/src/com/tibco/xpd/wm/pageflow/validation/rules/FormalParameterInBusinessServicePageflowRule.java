/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.validation.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * FormalParameterInBusinessServicePageflowRule
 * <p>
 * This class fulfils the purpose of the Studio to give a validate warning that
 * the use of Formal Parameters in Business Service Pageflow Processes will
 * cause that not to appear in out of the box clients.
 * <p>
 * This class extends the ProcessValidationRule class which is an Abstract base
 * class for rules that operate on an XPDL2 Process. To meet our intended
 * purpose, FormalParameterInBusinessServicePageflowRule class overwrites its
 * validateFlowContainer method. The process is checked to be a Pageflow
 * process. If it is so and if any formal parameters are found in it, then a
 * validate warning is added on that Pageflow.
 * <p>
 * 
 * @author sajain
 * @since Nov 27, 2012
 */

public class FormalParameterInBusinessServicePageflowRule extends
        ProcessValidationRule {

    private String ISSUE_ID =
            "com.tibco.xpd.wm.pageflow.validation.rules.FormalParameterInBusinessServicePageflowRule"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        /*
         * XPD-6803: Saket: Don't raise this warning marker for case actions.
         */
        if (Xpdl2ModelUtil.isPageflowBusinessService(process)
                && !Xpdl2ModelUtil.isCaseService(process)) {
            if (!process.getFormalParameters().isEmpty()) {
                addIssue(ISSUE_ID, process);
            }
        }
    }
}
