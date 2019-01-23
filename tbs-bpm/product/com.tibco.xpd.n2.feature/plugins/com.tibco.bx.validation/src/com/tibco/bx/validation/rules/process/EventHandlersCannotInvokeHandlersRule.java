/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.validation.xpdl2.tools.ProcessFlowAnalyserPreProcessor;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.ProcessFlowAnalyser;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Check whether any downstream activity of the given event handler or event
 * sub-process start activity has a throw signal that triggers any other event
 * handler or event sub-process.
 * <p>
 * The only time this is allowed is a start event sub-process flow invoking an
 * event handler flow IN the SAME event sub-process (because the downstream flow
 * of start event in event sub-process is classed as the main flow within its
 * own event sub-process
 * 
 * @author aallway
 * @since 8 Oct 2014
 */
public class EventHandlersCannotInvokeHandlersRule extends
        ProcessValidationRule {

    private static final String ISSUE_CANT_TRIGGER_SIGNAL_EVENTHANDLER_FROM_ANOTEHR_EVENTHANDLER_FLOW =
            "bx.cantTriggerEventhandlersFromEventHandler"; //$NON-NLS-1$

    private static final String ISSUE_CANT_TRIGGER_SIGNAL_EVENTHANDLER_FROM_EVENT_SUBPROCESS =
            "bx.cantTriggerEventHandlersFromEventSubProc"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {
        /*
         * Get all signal event handler activities and event sub-process start
         * events that could potentially be triggered by a throw signal in
         * another event handler / event sub-process.
         */
        List<Activity> allSignalEventHandlersAndSubProcs =
                new ArrayList<Activity>();

        for (Activity activity : Xpdl2ModelUtil.getAllActivitiesInProc(process)) {

            if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                    .equals(EventObjectUtil.getEventTriggerType(activity))) {
                if (Xpdl2ModelUtil.isEventHandlerActivity(activity)
                        || Xpdl2ModelUtil.isEventSubProcessStartEvent(activity)) {
                    allSignalEventHandlersAndSubProcs.add(activity);
                }
            }
        }

        if (!allSignalEventHandlersAndSubProcs.isEmpty()) {
            /*
             * Now go thru all event handlers checking their downstream flow
             * doesn't trigger one of the signal event handlers.
             */
            for (Activity activity : Xpdl2ModelUtil
                    .getAllActivitiesInProc(process)) {
                if (Xpdl2ModelUtil.isEventHandlerActivity(activity)) {
                    checkDoesntThrowEventHandlerSignal(activity,
                            false,
                            allSignalEventHandlersAndSubProcs);

                } else if (Xpdl2ModelUtil.isEventSubProcessStartEvent(activity)) {
                    checkDoesntThrowEventHandlerSignal(activity,
                            true,
                            allSignalEventHandlersAndSubProcs);
                }
            }
        }

    }

    /**
     * Check whether any downstream activity of the given event handler or event
     * sub-process start activity has a throw signal that triggers any other
     * event handler or event sub-process.
     * <p>
     * The only time this is allowed is a start event sub-process flow invoking
     * an event handler flow IN the SAME event sub-process (because the
     * downstream flow of start event in event sub-process is classed as the
     * main flow within its own event sub-process
     * 
     * @param eventHandlerOrStartEvtSubProcessActivity
     * @param isSignalStartEventSubProcess
     * @param allSignalEventHandlersAndSubProcs
     */
    private void checkDoesntThrowEventHandlerSignal(
            Activity eventHandlerOrStartEvtSubProcessActivity,
            boolean isSignalStartEventSubProcess,
            List<Activity> allSignalEventHandlersAndSubProcs) {

        /*
         * Get the downstream activities of this event handler / event
         * sub-process.
         */
        ProcessFlowAnalyser flowAnalyser =
                getTool(ProcessFlowAnalyserPreProcessor.class,
                        eventHandlerOrStartEvtSubProcessActivity
                                .getFlowContainer()).getProcessFlowAnalyser();

        Set<Activity> downstreamActivities =
                flowAnalyser
                        .getDownstreamActivities(eventHandlerOrStartEvtSubProcessActivity
                                .getId(),
                                false);

        for (Activity downStreamActivity : downstreamActivities) {
            if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                    .equals(EventObjectUtil
                            .getEventTriggerType(downStreamActivity))) {

                String thrownSignalName =
                        EventObjectUtil.getSignalName(downStreamActivity);

                if (thrownSignalName != null && thrownSignalName.length() > 0) {

                    /*
                     * Now see if this signal is caught by any event handler /
                     * event sub-process
                     */
                    for (Activity eventHandler : allSignalEventHandlersAndSubProcs) {
                        if (isSignalStartEventSubProcess
                                && eventHandlerOrStartEvtSubProcessActivity
                                        .getFlowContainer()
                                        .equals(eventHandler.getFlowContainer())) {
                            /*
                             * The only time this is allowed is a start event
                             * sub-process flow invoking an event handler flow
                             * IN the SAME event sub-process (because the
                             * downstream flow of start event in event
                             * sub-process is classed as the main flow within
                             * its own event sub-process
                             */
                            continue;
                        }

                        if (thrownSignalName.equals(EventObjectUtil
                                .getSignalName(eventHandler))) {
                            if (isSignalStartEventSubProcess) {
                                addIssue(ISSUE_CANT_TRIGGER_SIGNAL_EVENTHANDLER_FROM_EVENT_SUBPROCESS,
                                        downStreamActivity);

                            } else {
                                addIssue(ISSUE_CANT_TRIGGER_SIGNAL_EVENTHANDLER_FROM_ANOTEHR_EVENTHANDLER_FLOW,
                                        downStreamActivity);
                            }
                            break;
                        }
                    } /* Next event handler. */
                }
            }

        } /* Next activity downstream of any event handler. */
    }

}
