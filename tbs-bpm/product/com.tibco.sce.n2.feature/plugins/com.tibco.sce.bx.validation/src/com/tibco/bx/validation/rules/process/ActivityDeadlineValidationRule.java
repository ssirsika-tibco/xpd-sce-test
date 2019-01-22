/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.bx.validation.rules.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rule to ensure the activity deadline is set on a timer event that
 * is "withdraw on timeout" if available.
 * 
 * @author njpatel
 */
public class ActivityDeadlineValidationRule extends
        ProcessActivitiesValidationRule {

    private static final String ISSUE_ONLY_WITHDRAW_TIMER_CANBE_DEADLINE =
            "n2pe.onlyWithdrawTimerEventCanBeActivityDeadline"; //$NON-NLS-1$

    private static final String ISSUE_DEADLINE_ACTIVITY_DOESNOT_EXIST =
            "n2pe.activityDeadlineEventDoesNotExist"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param container
     */
    @Override
    public void validate(Activity container) {
        Object value =
                Xpdl2ModelUtil.getOtherAttribute(container,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ActivityDeadlineEventId());

        if (value instanceof String) {
            String deadlineActivityId = (String) value;

            /*
             * Deadline activity has been set so check if the activity has
             * multiple timer events attached
             */
            List<Activity> timerActivities = getAttachedTimerEvents(container);
            if (timerActivities.size() > 1) {
                Activity deadlineEvent = null;
                int withdrawTimerCount = 0;

                /*
                 * Check if the activity has "withdraw on timeout" timer events.
                 * If it does and the deadline activity is not one of them then
                 * raise issue.
                 */
                for (Activity timerActivity : timerActivities) {
                    if (!EventObjectUtil
                            .getContinueOnTimeoutFlag(timerActivity)) {
                        ++withdrawTimerCount;
                    }

                    if (deadlineActivityId.equals(timerActivity.getId())) {
                        deadlineEvent = timerActivity;
                    }
                }

                if (deadlineEvent == null) {
                    addIssue(ISSUE_DEADLINE_ACTIVITY_DOESNOT_EXIST, container);
                } else if (withdrawTimerCount > 0
                        && EventObjectUtil
                                .getContinueOnTimeoutFlag(deadlineEvent)) {
                    addIssue(ISSUE_ONLY_WITHDRAW_TIMER_CANBE_DEADLINE,
                            container);
                }
            } else {
                /*
                 * Make sure the deadline activity does exist and is a timer
                 * event
                 */
                if (timerActivities.isEmpty()
                        || !deadlineActivityId.equals(timerActivities.get(0)
                                .getId())) {
                    addIssue(ISSUE_DEADLINE_ACTIVITY_DOESNOT_EXIST, container);
                }
            }
        }
    }

    /**
     * Get all Timer events attached to the given activity.
     * 
     * @param activity
     * @return
     */
    private List<Activity> getAttachedTimerEvents(Activity activity) {
        List<Activity> timerEvents = new ArrayList<Activity>();
        Collection<Activity> attachedEvents =
                Xpdl2ModelUtil.getAttachedEvents(activity);

        for (Activity event : attachedEvents) {
            if (EventObjectUtil.getEventTriggerType(event) == EventTriggerType.EVENT_TIMER_LITERAL) {
                timerEvents.add(event);
            }
        }
        return timerEvents;
    }
}
