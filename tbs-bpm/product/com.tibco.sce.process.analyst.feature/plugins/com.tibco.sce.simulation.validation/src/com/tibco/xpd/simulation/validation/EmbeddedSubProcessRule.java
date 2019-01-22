/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import java.util.List;

import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;

/**
 * Rule to check for embedded sub-processes.
 * 
 * @author nwilson
 */
public class EmbeddedSubProcessRule extends FlowContainerValidationRule {

    /** The issue ID. */
    public static final String ID = "sim.embeddedSubProcess"; //$NON-NLS-1$
    
    /**
     * @param process The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(
     *      FlowContainer)
     */
    @Override
    public void validate(FlowContainer process) {
        List activities = process.getActivities();
        for (Object next : activities) {
            Activity activity = (Activity) next;
            if (activity.getBlockActivity() != null) {
                addIssue(ID, activity);
            }
        }
    }

}
