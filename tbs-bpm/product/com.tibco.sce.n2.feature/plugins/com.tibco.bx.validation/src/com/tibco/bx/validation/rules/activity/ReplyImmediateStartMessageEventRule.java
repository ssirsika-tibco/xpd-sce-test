/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.List;

import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;

/**
 * 
 * Rule validation : Cannot reply/throw error to a start message that has
 * already replied immediately with the process Id.XPD-3704 ABPM-470
 * 
 * @author aprasad
 * @since 24 Jul 2012
 */
public class ReplyImmediateStartMessageEventRule extends
        ProcessActivitiesValidationRule {
    private static final String ISSUE_DUPLICATE_REPLY =
            "bx.duplicateReply.ReplyImmediateStartMesaage"; //$NON-NLS-1$

    private static final String ISSUE_INVALID_THROW =
            "bx.invalidThrow.ReplyImmediateStartMesaage"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {
        /*
         * if it is a start activity with reply Immediate option set, validate
         * other reply events.
         */
        if (activity.getEvent() instanceof StartEvent
                && ReplyActivityUtil
                        .isStartMessageWithReplyImmediate(activity)) {
            // get all reply activity from the process.
            List<Activity> replyActivitiesToStart =
                    ReplyActivityUtil.getReplyActivities(activity);
            // check if any of them reply to start message, raise error
            if (replyActivitiesToStart != null
                    && replyActivitiesToStart.size() > 0) {
                for (Activity replyAct : replyActivitiesToStart) {
                    addIssue(ISSUE_DUPLICATE_REPLY, replyAct);
                }
            }

            // get all throw end activity,
            List<Activity> throwErrActivitiesToStart =
                    ThrowErrorEventUtil
                            .getThrowErrorEvents(activity);
            // if any of them refer to start message,raise error.
            if (throwErrActivitiesToStart != null
                    && throwErrActivitiesToStart.size() > 0) {
                for (Activity throwErrorAct : throwErrActivitiesToStart) {
                    addIssue(ISSUE_INVALID_THROW, throwErrorAct);
                }

            }

        }

    }

}
