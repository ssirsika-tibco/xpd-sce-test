/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.WorkItemPriority;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Priority;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessHeader;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Base rule class for checking initial priority value to be in a given range
 * 
 * Sub classes must provide their destination specific valid range and error
 * message
 * 
 * @author bharge
 * 
 */
public class BaseInitialPriorityValueRule extends ProcessValidationRule {

    private static final String INITIAL_PRIORITY_ID =
            "bpmn.initialPriorityNotInRange"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {
            ActivityResourcePatterns activityResourcePatterns =
                    (ActivityResourcePatterns) Xpdl2ModelUtil
                            .getOtherElement(activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ActivityResourcePatterns());
            if (null != activityResourcePatterns
                    && null != activityResourcePatterns.getWorkItemPriority()) {
                WorkItemPriority workItemPriority =
                        activityResourcePatterns.getWorkItemPriority();
                String initialPriority = workItemPriority.getInitialPriority();
                // reg ex match done to avoid NumberFormatException in logs
                if (null != initialPriority && !initialPriority.isEmpty()) {
                    boolean validRange = false;
                    if (initialPriority.matches("[-]?[\\d]+")) { //$NON-NLS-1$
                        validRange = isPriorityValidRange(initialPriority);
                    }
                    if (!validRange) {
                        addIssue(get_ISSUE_INITIAL_PRIORITY_INVALID_RANGE(),
                                activity);
                    }
                }
            }
        }
        if (null != process.getProcessHeader()) {
            ProcessHeader processHeader = process.getProcessHeader();
            if (null != processHeader.getPriority()) {
                Priority priority = processHeader.getPriority();
                if (null != priority) {
                    String value = priority.getValue();
                    boolean validRange = isProcessPriorityValidRange(value);
                    if (!validRange) {
                        addIssue(get_ISSUE_PROCESS_INITIAL_PRIORITY_INVALID_RANGE(),
                                process);
                    }
                }
            }
        }
    }

    /**
     * @param value
     * @return
     */
    protected boolean isProcessPriorityValidRange(String priority) {
        if (Integer.parseInt(priority) <= 0) {
            return false;
        }
        return true;
    }

    protected boolean isPriorityValidRange(String priority) {
        // XPD-3786 changed to avoid contradiction for 0 , which is valid in
        // runtime.
        if (Integer.parseInt(priority) < 0) {
            return false;
        }
        return true;
    }

    protected String get_ISSUE_INITIAL_PRIORITY_INVALID_RANGE() {
        return INITIAL_PRIORITY_ID;
    }

    protected String get_ISSUE_PROCESS_INITIAL_PRIORITY_INVALID_RANGE() {
        return INITIAL_PRIORITY_ID;
    }
}
