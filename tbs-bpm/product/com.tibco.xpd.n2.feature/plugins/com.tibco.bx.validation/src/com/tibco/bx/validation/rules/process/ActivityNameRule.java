/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rule to ensure that all activities in a process are uniquely named
 * (XPD-4331)
 * 
 * @author agondal
 * @since 11 Jan 2013
 */
public class ActivityNameRule extends ProcessValidationRule {

    public static final String ISSUE_DUPLICATE_ACTIVITY_NAME =
            "bx.duplicateActivityName"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(com.tibco.xpd.xpdl2.FlowContainer)
     * 
     * @param container
     */
    @Override
    public void validate(Process process) {

        if (process != null) {

            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);

            Set<Activity> duplicates = new HashSet<Activity>();

            for (Activity activity : activities) {

                if (activity.getName() != null && !activity.getName().isEmpty()) {

                    for (Activity act : activities) {

                        if (act.getName() != null && !act.getName().isEmpty()
                                && act != activity
                                && act.getName().equals(activity.getName())) {
                            duplicates.add(activity);
                        }
                    }
                }
            }

            for (Activity activity : duplicates) {

                Map<String, String> additionalInfo =
                        new HashMap<String, String>();
                
                additionalInfo.put(ISSUE_DUPLICATE_ACTIVITY_NAME,
                        activity.getName());
                
                addIssue(ISSUE_DUPLICATE_ACTIVITY_NAME,
                        activity.getProcess(),
                        Collections.singletonList(activity.getName()),
                        additionalInfo);
            }
        }
    }
}
