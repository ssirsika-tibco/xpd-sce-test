/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Transition;

/**
 * @author nwilson
 */
public class StartEventWithSimDataRule extends ProcessValidationRule {

    /** Multiple start event issue ID. */
    public static final String MORE_THAN_ONE_START =
            "sim.moreThanOneStartActivity"; //$NON-NLS-1$

    /** Start activity not a start event issue ID. */
    public static final String START_ACTIVITY_NOT_START_EVENT =
            "sim.startActivityNotStartEvent"; //$NON-NLS-1$

    /** Start event without sim data issue ID. */
    public static final String START_EVENT_WITHOUT_SIM_DATA =
            "sim.startEventWithOutSimulationData"; //$NON-NLS-1$

    /** Start activity not linked to start event issue ID. */
    public static final String START_ACTIVITY_NOT_LINKED_TO_START_EVENT =
            "sim.startActivityNotLinkedToStartEvent"; //$NON-NLS-1$

    /**
     * @param process The process.
     * @param activity The activity to check.
     */
    private void checkSimulationData(Process process, Activity activity) {
        List extendedAttributes = activity.getExtendedAttributes();
        boolean extFound = false;
        for (Object next : extendedAttributes) {
            ExtendedAttribute element = (ExtendedAttribute) next;
            if (SimulationConstants.SIM_START_EVENT_DATA.equals(element.getName())) {
                extFound = true;
                break;
            }
        }
        if (!extFound) {
            addIssue(START_EVENT_WITHOUT_SIM_DATA, activity);
        }
    }

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) {        
        if (process != null){
            List<String> toList = new ArrayList<String>();
            List<Activity> startEvents = new ArrayList<Activity>();
            List<Activity> startActivities = new ArrayList<Activity>();
            for (Object next : process.getTransitions()) {
                Transition transition = (Transition) next;
                toList.add(transition.getTo());
            }
            for (Object next : activities) {
                Activity activity = (Activity) next;
                if (!toList.contains(activity.getId())) {
                    Event event = activity.getEvent();
                    if (event instanceof StartEvent) {
                        startEvents.add(activity);
                    } else if (!(event instanceof IntermediateEvent)) {
                        startActivities.add(activity);
                    }
                }
            }
            int startCount = startEvents.size() + startActivities.size();
            if (startCount > 1) {
                addIssue(MORE_THAN_ONE_START, process);
            }
            boolean isStartEvent = startEvents.size() > 0;
            for (Activity activity : startEvents) {
                checkSimulationData(process, activity);
            }
            for (Activity activity : startActivities) {
                if (isStartEvent) {
                    addIssue(START_ACTIVITY_NOT_LINKED_TO_START_EVENT, activity);
                } else {                        
                    addIssue(START_ACTIVITY_NOT_START_EVENT, activity);
                }
            }
            if (startCount == 0) {
                addIssue(START_ACTIVITY_NOT_START_EVENT, process);
            }
        }
        
    }

}
