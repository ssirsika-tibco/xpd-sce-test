/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

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
 * Rule for multiple outgoing transitions from task activities. Multiple
 * outgoing transitions are not allowed from tasks. They are allowed only from
 * gateways and events.
 * <p>
 * <i>Created: 12 Apr 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class OutgoingTransitionRule extends ProcessValidationRule {

    /** Multiple outgoing transitions issue ID. */
    public static final String MULTIPLE_OUTGOING_TRANSITIONS = "sim.multipleOutgoingTransitions"; //$NON-NLS-1$

    /**
     * Checks if activity has more then one outgoing transitions.
     * 
     * @param process
     *            the context process.
     * @param activity
     *            activity to be checked.
     */
    @SuppressWarnings("unchecked") 
    private void checkOutgoingTransitions(Process process, Activity activity) {
        String activityId = activity.getId();
        int outgoingTrans = 0;
        for (Transition trans : (List<Transition>) process.getTransitions()) {
            if (trans.getFrom().equals(activityId)) {
                outgoingTrans++;
                if (outgoingTrans > 1) {
                    addIssue(MULTIPLE_OUTGOING_TRANSITIONS, activity);
                }
            }
        }
    }

    /**
     * Check if activity is task activity.
     * 
     * @param activity
     *            The activity.
     * @return true if it is a Task activity.
     */
    private boolean isTaskActivity(Activity activity) {
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            return true;
        }
        return false;
    }

    /**
     * Checks if activity is independent subprocess.
     * 
     * @param activity
     *            activity to check.
     * @return true if activity is an independent subprocess.
     */
    private boolean isIndependentSubFlow(Activity activity) {
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof SubFlow) {
            return true;
        }
        return false;
    }

    /**
     * Check if the activity is start event.
     * 
     * @param activity
     *            activity to check.
     * @return true if activity is start event.
     */
    private boolean isStartEvent(Activity activity) {
        Event event = activity.getEvent();
        if (event instanceof StartEvent) {
            return true;
        }
        return false;
    }

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {
            if (isStartEvent(activity) || isTaskActivity(activity)
                    || isIndependentSubFlow(activity)) {
                checkOutgoingTransitions(activity.getProcess(), activity);
            }
        }        
    }
}