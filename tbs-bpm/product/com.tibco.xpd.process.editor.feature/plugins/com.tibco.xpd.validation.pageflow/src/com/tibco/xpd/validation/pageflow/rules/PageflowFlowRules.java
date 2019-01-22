/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.pageflow.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * PageflowFlowRules
 * 
 * 
 * @author aallway
 * @since 3.3 (13 Nov 2009)
 */
public class PageflowFlowRules extends ProcessValidationRule {

    private static final String ISSUE_STARTWITH_EVENT_AND_TASK_ONLY =
            "pageflow.startWithEventOrTaskOnly"; //$NON-NLS-1$

    @Override
    public void validate(Process process) {
        for (Activity activity : process.getActivities()) {
            /*
             * Allow event handler activity without incoming flow and do things
             * most efficient way around (search for incoming trans first)
             */
            if (Xpdl2ModelUtil.isStartProcessActivity(activity)) {
                if (!isValidStartActivityType(activity)) {
                    addIssue(ISSUE_STARTWITH_EVENT_AND_TASK_ONLY, activity);
                }
            }
        }

        return;
    }

    /**
     * @param activity
     * 
     * @return true if the activity is valid type to start a pageflow process.
     */
    private boolean isValidStartActivityType(Activity activity) {
        if (activity.getEvent() instanceof StartEvent) {
            return true;

        } else if (TaskObjectUtil.getTaskTypeStrict(activity) != null) {
            return true;
        }
        return false;
    }

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        return;
    }
}
