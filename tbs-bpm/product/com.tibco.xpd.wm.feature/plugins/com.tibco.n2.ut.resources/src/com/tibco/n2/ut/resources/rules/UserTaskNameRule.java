/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.n2.ut.resources.rules;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * UserTaskNameRule - validates if the user task(s) within a process and its
 * embedded sub-processes have unique names
 * 
 * 
 * @author bharge
 * @since 3.3 (29 Apr 2010)
 */
public class UserTaskNameRule extends ProcessValidationRule {

    private static final String DUPLICATE_NAME_ID =
            "n2.ut.userTaskNameDuplicate"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#
     * validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     * org.eclipse.emf.common.util.EList, org.eclipse.emf.common.util.EList)
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> duplicates = new ArrayList<String>();

        for (Activity activity : activities) {
            if (isUserTask(activity)) {
                addNames(names, duplicates, activity.getName());
            }
            if (null != activity.getBlockActivity()) {
                ActivitySet activitySet =
                        TaskObjectUtil.getActivitySet(activity);
                for (Activity act : activitySet.getActivities()) {
                    if (isUserTask(act)) {
                        addNames(names, duplicates, act.getName());
                    }
                }
            }
        }

        for (Activity activity : activities) {
            if (isUserTask(activity)) {
                if (duplicates.contains(activity.getName())) {
                    addIssue(DUPLICATE_NAME_ID, activity);
                }
            }
            if (null != activity.getBlockActivity()) {
                ActivitySet activitySet =
                        TaskObjectUtil.getActivitySet(activity);
                for (Activity act : activitySet.getActivities()) {
                    if (isUserTask(act)) {
                        if (duplicates.contains(act.getName())) {
                            addIssue(DUPLICATE_NAME_ID, act);
                        }
                    }
                }
            }
        }
    }

    private boolean isUserTask(Activity activity) {
        TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);
        if (TaskType.USER_LITERAL.equals(taskType)) {
            return true;
        }
        return false;
    }

    /**
     * @param names
     * @param duplicates
     * @param name
     */
    private void addNames(ArrayList<String> names,
            ArrayList<String> duplicates, String name) {
        if (null != name && name.length() > 0) {
            if (!names.contains(name)) {
                names.add(name);
            } else {
                if (!duplicates.contains(name)) {
                    duplicates.add(name);
                }
            }
        }
    }

}
