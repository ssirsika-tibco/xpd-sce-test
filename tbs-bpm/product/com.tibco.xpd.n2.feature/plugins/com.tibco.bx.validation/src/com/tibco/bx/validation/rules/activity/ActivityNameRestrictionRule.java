/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * Rule check that conditions imposed by runtime on activity name are not
 * violated.
 * 
 * @author Jan Arciuchiewicz
 */
public class ActivityNameRestrictionRule extends ProcessValidationRule {

    private static final String ACTIVITY_NAME_TOO_LONG_ISSUE_ID =
            "bx.activityNameTooLong"; //$NON-NLS-1$

    /** Max number of characters in an activity's name. */
    public static final int MAX_ACTIVITY_NAME_LENGTH = 58;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {
            String name = activity.getName();
            if (name != null && name.length() > MAX_ACTIVITY_NAME_LENGTH) {
                List<String> messages = new ArrayList<String>();
                messages.add(new Integer(MAX_ACTIVITY_NAME_LENGTH).toString());
                addIssue(ACTIVITY_NAME_TOO_LONG_ISSUE_ID, activity, messages);
            }
        }
    }

}
