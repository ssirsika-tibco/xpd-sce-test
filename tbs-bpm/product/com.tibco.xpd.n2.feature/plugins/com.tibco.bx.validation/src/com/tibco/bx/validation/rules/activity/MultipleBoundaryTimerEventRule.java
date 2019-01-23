/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * validates if a task has more than one withdraw task as a boundary timer event
 * 
 * @author bharge
 * 
 */
public class MultipleBoundaryTimerEventRule extends ProcessValidationRule {

    private static final String MULTIPLE_BOUNDARY_WITHDRAW_TASK_ISSUE_ID =
            "bx.multipleWithdrawBoundaryNotSupported"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList,
     *      org.eclipse.emf.common.util.EList)
     * 
     * @param process
     * @param activities
     * @param transitions
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        for (Activity activity : activities) {
            /** check if the activity has boundary events */
            boolean hasBoundaryEvent = hasBoundaryEvent(activity, activities);
            if (hasBoundaryEvent) {
                /** get all the boundary timer events for this activity */
                List<TriggerTimer> boundaryEventsForThisActivity =
                        getAllBoundaryEventsForThisActivity(activity,
                                activities);
                if (boundaryEventsForThisActivity.size() > 1) {
                    /** check for more than one withdraw task boundary event */
                    boolean moreThanOneWithdrawTask =
                            hasMoreThanOneWithdrawTask(boundaryEventsForThisActivity);
                    if (moreThanOneWithdrawTask) {
                        addIssue(MULTIPLE_BOUNDARY_WITHDRAW_TASK_ISSUE_ID,
                                activity);
                    }
                }
            }
        }
    }

    /**
     * @param activity
     * @param activities
     */
    private List<TriggerTimer> getAllBoundaryEventsForThisActivity(
            Activity activity, EList<Activity> activities) {
        List<TriggerTimer> taskBoundaryTimerEventList =
                new ArrayList<TriggerTimer>();
        for (Activity eventAct : activities) {
            Event event = eventAct.getEvent();
            if (event instanceof IntermediateEvent) {
                IntermediateEvent intermediateEvent = (IntermediateEvent) event;
                String targetId = intermediateEvent.getTarget();
                if (null != targetId
                        && targetId.equalsIgnoreCase(activity.getId())) {
                    EventTriggerType eventTriggerType =
                            EventObjectUtil.getEventTriggerType(eventAct);
                    if (eventTriggerType.getValue() == EventTriggerType.EVENT_TIMER) {
                        TriggerTimer triggerTimer =
                                intermediateEvent.getTriggerTimer();
                        if (null != triggerTimer) {
                            taskBoundaryTimerEventList.add(triggerTimer);
                        }
                    }
                }
            }
        }
        return taskBoundaryTimerEventList;
    }

    /**
     * @param activity
     * @param activities
     */
    private boolean hasBoundaryEvent(Activity activity,
            EList<Activity> activities) {
        for (Activity eventAct : activities) {
            Event event = eventAct.getEvent();
            if (event instanceof IntermediateEvent) {
                IntermediateEvent intermediateEvent = (IntermediateEvent) event;
                String targetId = intermediateEvent.getTarget();
                if (null != targetId) {
                    if (targetId.equalsIgnoreCase(activity.getId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param activity
     * 
     */
    private boolean hasMoreThanOneWithdrawTask(
            List<TriggerTimer> triggerTimerList) {
        int withDrawTaskCnt = 0;
        for (TriggerTimer triggerTimer : triggerTimerList) {
            Object otherAttribute =
                    Xpdl2ModelUtil.getOtherAttribute(triggerTimer,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ContinueOnTimeout());
            boolean withDrawTask = true;
            if (otherAttribute instanceof Boolean) {
                Boolean continueTask = (Boolean) otherAttribute;
                if (continueTask) {
                    withDrawTask = false;
                }
            }
            if (withDrawTask) {
                withDrawTaskCnt++;
            }
        }
        if (withDrawTaskCnt > 1) {
            return true;
        }
        return false;
    }

}
