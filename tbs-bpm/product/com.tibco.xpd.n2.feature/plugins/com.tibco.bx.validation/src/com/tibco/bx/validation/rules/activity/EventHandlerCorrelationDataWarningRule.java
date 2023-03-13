/*
 * Copyright (c) 2004-2023. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.bx.validation.rules.activity;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Sid ACE-6815
 * 
 * Run-time does not support correlation data on event-handler / event sub-process events. So this has been disabled in
 * the UI, but due to the nature of 'implicit correlation' a user may think that merely defining correlation data on
 * process means they will be able to use it for event-handlers (i.e. they can use it for in-flow event etc so why not
 * these?).
 * 
 * Therefore we raise the following warning on event-handler and event-sub-process start events if there is correlation
 * data defined in the process
 * 
 * - Note that the correlation data defined in this process cannot be used when triggering event-handlers at run-time.
 * You must use the process instance ID option to trigger this event.
 *
 * @author aallway
 * @since Mar 2023
 */
public class EventHandlerCorrelationDataWarningRule extends ProcessActivitiesValidationRule {

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     *
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {
        if (EventObjectUtil.isEventHandlerOrEventSubProcessStartEventActivity(activity)) {
            if (EventTriggerType.EVENT_NONE_LITERAL.equals(EventObjectUtil.getEventTriggerType(activity))) {
                if (!ProcessInterfaceUtil.getCorrelationDataFields(activity.getProcess()).isEmpty()) {
                    addIssue("bx.correlation.not.supported.on.event.handlers", activity); //$NON-NLS-1$
                }
            }
        }
    }

}
