/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.pageflow.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Transition;

/**
 * PageflowTaskTypeRules
 * 
 * 
 * @author aallway
 * @since 3.3 (5 Nov 2009)
 */
public class PageflowTaskTypeRules extends ProcessValidationRule {
    private static final String ISSUE_RECEIVETASK_NOT_SUPPORTED =
            "pageflow.receiveTaskNotSupported"; //$NON-NLS-1$ 

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        for (Activity activity : activities) {
            if (activity.getImplementation() instanceof Task) {
                Task task = (Task) activity.getImplementation();

                if (task.getTaskReceive() != null) {
                    addIssue(ISSUE_RECEIVETASK_NOT_SUPPORTED, activity);
                }
            }
        }

        return;
    }

}
