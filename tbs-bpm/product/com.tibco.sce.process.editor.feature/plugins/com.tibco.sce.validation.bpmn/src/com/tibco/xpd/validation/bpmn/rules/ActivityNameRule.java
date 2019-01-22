/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;

/**
 * @author nwilson
 */
public class ActivityNameRule extends FlowContainerValidationRule {

    /** The issue id. */
    private static final String ID = "bpmn.activityName"; //$NON-NLS-1$

    /**
     * @param process The process to validate.
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(
     *      FlowContainer)
     */
    @Override
    public void validate(FlowContainer process) {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> duplicates = new ArrayList<String>();
        List contents = process.eContents();
        for (Object next : contents) {
            if (next instanceof Activity) {
                Activity activity = (Activity) next;
                String name = activity.getName();
                if (name != null && name.length() != 0) {
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
        for (Object next : contents) {
            if (next instanceof Activity) {
                Activity activity = (Activity) next;
                String name = activity.getName();
                if (duplicates.contains(name)) {
                    addIssue(ID, activity);
                }
            }
        }
    }

}
