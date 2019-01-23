/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.TriggerResultLink;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class LinkEventRule extends FlowContainerValidationRule {

    /** Target in different sub-process issue ID. */
    private static final String TARGET_DIFFERENT_POOL_OR_SUB_PROC =
            "bpmn.linkTargetInDifferentPoolOrSubProc"; //$NON-NLS-1$

    private static final String SOURCE_DIFFERENT_POOL_OR_SUB_PROC =
            "bpmn.linkSourceInDifferentPoolOrSubProc"; //$NON-NLS-1$

    private static final String SOURCE_Missing = "bpmn.linkSourceMissing"; //$NON-NLS-1$

    /**
     * @param process
     *            The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(FlowContainer)
     */
    @Override
    public void validate(FlowContainer process) {
        for (Object next : process.getActivities()) {
            Activity activity = (Activity) next;
            Event event = activity.getEvent();
            if (event instanceof IntermediateEvent) {
                IntermediateEvent intermediate = (IntermediateEvent) event;
                if (TriggerType.LINK_LITERAL.equals(intermediate.getTrigger())) {
                    TriggerResultLink link =
                            intermediate.getTriggerResultLink();
                    if (link != null) {
                        if (CatchThrow.THROW.equals(link.getCatchThrow())) {
                            String linkId = link.getName();

                            Activity target =
                                    getActivityInProcess(process, linkId);
                            if (!inSameSubProcessAndPool(activity, target)) {
                                addIssue(TARGET_DIFFERENT_POOL_OR_SUB_PROC,
                                        activity);
                            }

                        } else {
                            List<Activity> throwers =
                                    getThrowLinkEvents(activity);
                            if (throwers.isEmpty()) {
                                addIssue(SOURCE_Missing, activity);
                            }
                            for (Activity throwEvent : throwers) {
                                if (!inSameSubProcessAndPool(activity, throwEvent)) {
                                    addIssue(SOURCE_DIFFERENT_POOL_OR_SUB_PROC,
                                            activity);
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    /**
     * @param catchEvent
     * 
     * @return Return the list of throw link events for the given catch link
     *         event.
     */
    private List<Activity> getThrowLinkEvents(Activity catchEvent) {
        List<Activity> throwers = new ArrayList<Activity>();

        Collection<Activity> allActs = Xpdl2ModelUtil.getAllActivitiesInProc(catchEvent.getProcess());
        for (Activity act : allActs) {
            Event event = act.getEvent();
            if (event instanceof IntermediateEvent) {
                IntermediateEvent intermediate = (IntermediateEvent) event;
                if (TriggerType.LINK_LITERAL.equals(intermediate.getTrigger())) {
                    TriggerResultLink link =
                            intermediate.getTriggerResultLink();
                    if (link != null) {
                        if (CatchThrow.THROW.equals(link.getCatchThrow())) {
                            String linkId = link.getName();
                            
                            if (catchEvent.getId().equals(linkId)) {
                                throwers.add(act);
                            }
                        }
                    }
                }
            }
        }

        return throwers;
    }

    /**
     * Checks the entire process of which the container is a memeber for an
     * activity with the given id.
     * 
     * @param container
     *            The container in the process to be checked.
     * @param activityId
     *            The id of the activity to get.
     * @return The activity or null.
     */
    private Activity getActivityInProcess(FlowContainer container,
            String activityId) {
        Activity activity = null;
        Process process = null;
        if (container instanceof Process) {
            process = (Process) container;
        } else if (container instanceof ActivitySet) {
            ActivitySet set = (ActivitySet) container;
            process = set.getProcess();
        }
        if (process != null) {
            activity = process.getActivity(activityId);
            if (activity == null) {
                for (Object next : process.getActivitySets()) {
                    ActivitySet set = (ActivitySet) next;
                    activity = set.getActivity(activityId);
                    if (activity != null) {
                        break;
                    }
                }
            }
        }
        return activity;
    }

    /**
     * @param activity
     *            The first activity.
     * @param target
     *            The second activity.
     * @return true if they are in the same process/sub-process.
     */
    private boolean inSameSubProcessAndPool(Activity activity, Activity target) {
        boolean inSame = false;
        if (activity != null && target != null) {
            FlowContainer ac = activity.getFlowContainer();
            FlowContainer tc = target.getFlowContainer();
            if (ac.equals(tc)) {
                if (ac instanceof Process) {
                    Pool activityPool = Xpdl2ModelUtil.getParentPool(activity);
                    Pool targetPool = Xpdl2ModelUtil.getParentPool(target);
                    if (activityPool.equals(targetPool)) {
                        inSame = true;
                    }
                } else {
                    inSame = true;
                }
            }
        }
        return inSame;
    }

}
