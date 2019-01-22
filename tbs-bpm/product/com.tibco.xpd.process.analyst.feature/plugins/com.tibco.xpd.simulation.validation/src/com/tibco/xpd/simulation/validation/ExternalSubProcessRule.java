/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import java.util.List;

import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.SubFlow;

/**
 * @author nwilson
 */
public class ExternalSubProcessRule extends FlowContainerValidationRule {

    /** The issue ID. */
    public static final String ID = "sim.externalSubProcess"; //$NON-NLS-1$

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
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof SubFlow) {
                SubFlow subFlow = (SubFlow) implementation;
                if (subFlow.getPackageRefId() != null) {
                    addIssue(ID, activity);
                }
            }
        }
    }

}
