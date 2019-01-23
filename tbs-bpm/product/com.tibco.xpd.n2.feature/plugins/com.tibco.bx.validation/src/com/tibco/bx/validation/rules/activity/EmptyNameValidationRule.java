/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Empty name rule for process/tasks
 * <p>
 * Sid XPD-4133: Separated out package name checking to
 * {@link EmptyPkgNameValidationRule}. Package name needs to be validated if any
 * process within has destination env set (which will happen for
 * PackageValidaitonRule's when Xpdl2ScopeProvider is used)- however,
 * process/tasks should only be validated if the specific process has
 * destination set.
 * <p>
 * Easiest way to do this is to validate the process and tasks as a
 * ProcessValidationRule which I have changed this class to do
 * 
 * 
 * @author aprasad
 * @since 21 Aug 2012
 */
public class EmptyNameValidationRule extends ProcessValidationRule {
    private static final String TASK_NAME_BLANK_ID = "bx.activityNameBlank"; //$NON-NLS-1$

    private static final String PROCESS_NAME_BLANK_ID = "bx.processNameBlank"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com.tibco.xpd.xpdl2.Package)
     * 
     * @param pckg
     */
    @Override
    public void validate(Process process) {
        checkName(process, process.getName(), PROCESS_NAME_BLANK_ID);

        Collection<Activity> activities =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        for (Activity activity : activities) {
            // Task Activity
            if (TaskObjectUtil.getTaskTypeStrict(activity) != null) {
                checkName(activity, activity.getName(), TASK_NAME_BLANK_ID);
            }
        }
    }

    private void checkName(EObject eObject, String name, String errorId) {
        if (name != null && name.length() == 0) {
            if (eObject != null && errorId != null) {
                addIssue(errorId, eObject);
            }
        }
    }
}
