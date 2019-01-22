/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.IntermediateEvent;

/**
 * @author nwilson
 */
public class IntermediateEventRule extends FlowContainerValidationRule {

    /** intermediate event issue. */
    public static final String ID = "sim.intermediateEvent"; //$NON-NLS-1$

    /**
     * @param container The container to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#
     * validate(com.tibco.xpd.xpdl2.FlowContainer)
     */
    @Override
    public void validate(FlowContainer container) {
        for (Object next : container.getActivities()) {
            Activity activity = (Activity) next;
            Event event = activity.getEvent();
            if (event instanceof IntermediateEvent) {
                addIssue(ID, activity);
            }
        }
    }

}
