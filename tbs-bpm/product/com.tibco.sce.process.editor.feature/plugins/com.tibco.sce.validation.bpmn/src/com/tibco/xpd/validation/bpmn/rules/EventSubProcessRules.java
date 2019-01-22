/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.List;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Event sub process validation rules.
 * 
 * @author sajain
 * @since Aug 14, 2014
 */
public class EventSubProcessRules extends ProcessActivitiesValidationRule {

    /**
     * Event Sub-Process must not have any incoming or outgoing flows.
     */
    private static final String ISSUE_EVENT_SUB_PROCESS_MUST_NOT_HAVE_FLOWS =
            "bpmn.eventSubProcessMustNotHaveFlows"; //$NON-NLS-1$

    /**
     * Event Sub-Process must have exactly one start event activity.
     */
    private static final String ISSUE_EVENT_SUB_PROCESS_MUST_HAVE_EXACTLY_ONE_START_EVENT =
            "bpmn.eventSubProcessMustHaveExactlyOneStartEvent"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity act) {
        if (TaskType.EVENT_SUBPROCESS_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(act))) {

            /*
             * Event Sub-Process must not have any incoming or outgoing flows.
             */
            if (hasIncomingOrOutgoingFlow(act)) {

                addIssue(ISSUE_EVENT_SUB_PROCESS_MUST_NOT_HAVE_FLOWS, act);
            }

            /*
             * Event Sub-Process must have exactly one start event activity.
             */
            if (needExactlyOneStartEventError(act)) {
                addIssue(ISSUE_EVENT_SUB_PROCESS_MUST_HAVE_EXACTLY_ONE_START_EVENT,
                        act);
            }

        }
    }

    /**
     * Return <code>true</code> if the specified activity has any incoming OR
     * outgoing flow, <code>false</code> otherwise.
     * 
     * @param act
     *            Event subprocess activity.
     * @return <code>true</code> if the specified activity has any incoming OR
     *         outgoing flow, <code>false</code> otherwise.
     */
    private boolean hasIncomingOrOutgoingFlow(Activity act) {
        return (act.getIncomingTransitions() != null && !act
                .getIncomingTransitions().isEmpty())
                || (act.getOutgoingTransitions() != null && !act
                        .getOutgoingTransitions().isEmpty());
    }

    /**
     * Return <code>true</code> if the specified event subprocess contains more
     * than one start event activities OR no start event activities at all,
     * <code>false</code> otherwise.
     * 
     * @param evSubProc
     *            Event Subprocess activity.
     * @return <code>true</code> if the specified event subprocess has more than
     *         one start event activities in it, <code>false</code> otherwise.
     */
    private boolean needExactlyOneStartEventError(Activity evSubProc) {

        BlockActivity ba = evSubProc.getBlockActivity();
        Process prc = evSubProc.getProcess();
        ActivitySet actSet = prc.getActivitySet(ba.getActivitySetId());
        List<Activity> allActs = actSet.getActivities();

        /*
         * Number of start event activities inside the event subprocess.
         */
        int numOfStartEventActs = 0;

        /*
         * Number of activities in the event subprocess that have no incoming
         * flows.
         */
        int numOfActivitiesWithNoIncomingFlow = 0;

        for (Activity eachActivity : allActs) {

            /*
             * Fetch the number of start event activities.
             */
            if (eachActivity.getEvent() != null) {
                if (EventFlowType.FLOW_START_LITERAL.equals(EventObjectUtil
                        .getFlowType(eachActivity))) {
                    numOfStartEventActs++;
                }
            }

            /*
             * Fetch the number of activities that has no incoming flow.
             * 
             * Sid XPD-6892: Actually what we're after is whether there are
             * other activities that act as the start of a process / embedded
             * sub-process. There are many activities that have no incoming flow
             * that do not start a process / embedded sub-process (such as event
             * handler, task boundary event. So use correct method instead of
             * simply checking for no incoming flow.
             */
            if (Xpdl2ModelUtil.isStartProcessActivity(eachActivity)) {

                numOfActivitiesWithNoIncomingFlow++;
            }
        }

        /*
         * If an event sub-process does not have a start event or has more than
         * one activity that has no incoming flow, return true.
         */
        if (numOfStartEventActs != 1 || numOfActivitiesWithNoIncomingFlow > 1) {
            return true;
        } else {
            return false;
        }
    }

}
