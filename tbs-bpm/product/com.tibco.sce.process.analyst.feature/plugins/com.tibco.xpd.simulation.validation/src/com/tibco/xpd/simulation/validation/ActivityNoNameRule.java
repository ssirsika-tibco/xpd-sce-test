/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * Rule to check for names on all activites.
 * 
 * @author nwilson
 */
public class ActivityNoNameRule extends ProcessValidationRule {

    /** The issue ID. */
    public static final String ID = "sim.activityNoName"; //$NON-NLS-1$

    /**
     * @param process The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(
     *      com.tibco.xpd.xpdl2.Process)
     */
    @Override
    public void validate(Process process) {
        List contents = process.eContents();
        for (Object next : contents) {
            if (next instanceof Activity) {
                Activity activity = (Activity) next;
                String name = activity.getName();
                if (name == null || name.length() == 0) {
                    addIssue(ID, activity);
                }
            }
        }
    }

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) {
        for (Activity next : activities) {
            Activity activity = (Activity) next;
            String name = activity.getName();
            if (name == null || name.length() == 0) {
                addIssue(ID, activity);
            }
        }
        
    }

}
