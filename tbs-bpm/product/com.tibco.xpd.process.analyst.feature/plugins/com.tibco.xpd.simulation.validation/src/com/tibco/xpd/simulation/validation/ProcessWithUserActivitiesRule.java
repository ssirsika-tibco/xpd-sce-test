/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Transition;

/**
 * @author nwilson
 */
public class ProcessWithUserActivitiesRule extends ProcessValidationRule {

    /** Invalid activity issue ID. */
    public static final String NON_USER_ACTIVITY_PRESENT =
            "sim.invalidActivityPresent"; //$NON-NLS-1$

    /** Start event issue ID. */
    public static final String START_EVENT_MIDDLE_OF_PROCESS =
            "sim.startEventInMiddleOfProcess"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) {
        if (process != null){            
            ArrayList<String> toList = new ArrayList<String>();
            for (Object next : transitions) {
                Transition element = (Transition) next;
                String to = element.getTo();
                toList.add(to);
            }
            for (Object next : activities) {
                Activity activity = (Activity) next;
                Event event = activity.getEvent();
                if (event instanceof StartEvent) {
                    if (toList.contains(activity.getId())) {
                        addIssue(START_EVENT_MIDDLE_OF_PROCESS, activity);
                    }
                }
                Implementation implementation = activity.getImplementation();
                if (implementation != null) {
                    if (implementation instanceof Task) {
                        Task task = (Task) implementation;
                        if (task.getTaskUser() == null
                                && task.getTaskService() == null) {
                            addIssue(NON_USER_ACTIVITY_PRESENT, activity);
                        }
                    } else if (!(implementation instanceof SubFlow)) {
                        addIssue(NON_USER_ACTIVITY_PRESENT, activity);
                    }
                }
            }        
        }
    }
}
