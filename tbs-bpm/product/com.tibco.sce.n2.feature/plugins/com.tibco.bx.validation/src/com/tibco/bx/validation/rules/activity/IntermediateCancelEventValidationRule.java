/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rule for Intermediate Cancel Events.
 * 
 * @author kthombar
 * @since Dec 16, 2014
 */
public class IntermediateCancelEventValidationRule extends
        ProcessValidationRule {

    /**
     * Intermediate Cancel Events are only supported as event handlers.
     */
    private static String INTERMEDIATE_CANCEL_EVENTS_ONLY_SUPPORTED_AS_EVENT_HANDLERS_ISSUE =
            "bx.intermediateCancelEventsOnlySupportedAsEventHandlers"; //$NON-NLS-1$

    /**
     * A process can have only one cancel event handler flow.
     */
    private static String PROCESS_CAN_HAVE_ONLY_ONE_CANCEL_EVENT_HANDLER_ISSUE =
            "bx.processCanHaveOnlyOneCancelEventHandler"; //$NON-NLS-1$

    /**
     * Cancel event handlers are only supported at top level of process(not in
     * embedded sub-processes).
     */
    private static String CANCEL_EVENT_HANDLERS_ARE_SUPPORTED_AT_TOP_LEVEL_ISSUE =
            "bx.cancelEventHandlersOnlySupportedAtTopLevelOfProcess"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {
        /*
         * get all the activities
         */
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        /*
         * collects all the Cancel event handlers in the current process.
         */
        List<Activity> allCancelEventHandlerActivities =
                new ArrayList<Activity>();

        for (Activity activity : allActivitiesInProc) {

            Event event = activity.getEvent();

            if (event instanceof IntermediateEvent) {

                if (EventTriggerType.EVENT_CANCEL_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(activity))) {
                    /*
                     * we are interested only in intermediate cancel events
                     */
                    boolean eventHandlerActivity =
                            Xpdl2ModelUtil.isEventHandlerActivity(activity);

                    if (eventHandlerActivity) {
                        /*
                         * add the cancel event handler to the list
                         */
                        allCancelEventHandlerActivities.add(activity);

                        if (!(activity.eContainer() instanceof Process)) {
                            /*
                             * Cancel event handlers are allowed only at top
                             * level of process
                             */
                            addIssue(CANCEL_EVENT_HANDLERS_ARE_SUPPORTED_AT_TOP_LEVEL_ISSUE,
                                    activity);
                        }
                    } else {
                        /*
                         * Cancel event as only supported as events Handlers
                         */
                        addIssue(INTERMEDIATE_CANCEL_EVENTS_ONLY_SUPPORTED_AS_EVENT_HANDLERS_ISSUE,
                                activity);
                    }
                }
            }
        }
        if (allCancelEventHandlerActivities.size() > 1) {
            /*
             * Only one cancel event handler is allowed per process.
             */
            for (Activity activity : allCancelEventHandlerActivities) {
                addIssue(PROCESS_CAN_HAVE_ONLY_ONE_CANCEL_EVENT_HANDLER_ISSUE,
                        activity);
            }
        }
    }
}
