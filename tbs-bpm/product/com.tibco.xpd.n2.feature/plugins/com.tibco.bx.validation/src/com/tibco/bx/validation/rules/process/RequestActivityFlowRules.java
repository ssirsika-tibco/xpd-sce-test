/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.validation.xpdl2.tools.ProcessFlowAnalyserPreProcessor;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.ProcessFlowAnalyser;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rules surrounding flow from request activities.
 * 
 * @author aallway
 * @since 9 May 2011
 */
public class RequestActivityFlowRules extends FlowContainerValidationRule {

    private static final String ISSUE_EVENTHANDLER_FLOW_CANT_JOIN =
            "bx.eventHandlerFlowMayNotJoinOthers"; //$NON-NLS-1$ 

    private static final String ISSUE_ESP_START_EVENT_FLOW_CANT_JOIN =
            "bx.espStartEventFlowMayNotJoinOthers"; //$NON-NLS-1$

    private static final String ISSUE_EVENTHANDLER_HAS_ARBITRARY_LENGTH_ACTIVITIES =
            "bx.eventHandlerFlowHasArbitraryLengthActs"; //$NON-NLS-1$ 

    private static final String ISSUE_ESP_START_EVENT_HAS_ARBITRARY_LENGTH_ACTIVITIES =
            "bx.espStartEventFlowHasArbitraryLengthActs"; //$NON-NLS-1$ 

    private static final String ISSUE_ARBITRARY_LENGTHY_ACTIVITIES_BETWEEN_REQUEST_REPLY_MSGS =
            "bx.arbitraryLengthActivitiesBetweenRequestReplyMsgs"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(com.tibco.xpd.xpdl2.FlowContainer)
     * 
     * @param container
     */
    @Override
    public void validate(FlowContainer container) {

        ProcessFlowAnalyser flowAnalyser =
                getTool(ProcessFlowAnalyserPreProcessor.class, container)
                        .getProcessFlowAnalyser();

        checkForJoiningEventHandlerFlows(container, flowAnalyser);

        checkForArbitraryLengthActsForRequestReplies(container, flowAnalyser);

    }

    /**
     * check and warn the user of arbitrary length activities (user tasks / in
     * flow catch events / receive tasks) between message request/reply
     * activities that can cause the web-service invocation to timeout.
     * (XPD-4229)
     * 
     * @param container
     * @param flowAnalyser
     */
    private void checkForArbitraryLengthActsForRequestReplies(
            FlowContainer container, ProcessFlowAnalyser flowAnalyser) {

        if (container != null) {

            // get all incoming request activities
            List<Activity> requestActivities = new ArrayList<Activity>();

            for (Activity activity : container.getActivities()) {

                if (ReplyActivityUtil.isIncomingRequestActivity(activity)) {
                    requestActivities.add(activity);
                }
            }

            /*
             * get all the activities between the request and reply and raise
             * issue if there is any long-running activities found
             */

            for (Activity requestActivity : requestActivities) {

                for (Activity replyActivity : ReplyActivityUtil
                        .getReplyActivities(requestActivity)) {

                    Set<Activity> intermediateActivities =
                            getActivitiesBetween(requestActivity,
                                    replyActivity,
                                    flowAnalyser);

                    checkForDownStreamArbitraryLengthActivities(requestActivity,
                            intermediateActivities,
                            new HashSet<FlowContainer>(),
                            ISSUE_ARBITRARY_LENGTHY_ACTIVITIES_BETWEEN_REQUEST_REPLY_MSGS);
                }
            }
        }
    }

    /**
     * Check whether event handler flows join into other flows.
     * 
     * @param container
     * @param flowAnalyser
     */
    private void checkForJoiningEventHandlerFlows(
            FlowContainer container,
            ProcessFlowAnalyser flowAnalyser) {
        List<Activity> eventHandlers = new ArrayList<Activity>();
        Set<Activity> downstreamOfStartActs = new LinkedHashSet<Activity>();

        /*
         * Sid XPD-6894 - finally determined that Checking for one event handler
         * / event sub-process triggerring another was just too difficult mixed
         * in with the check for joining flows method.
         * 
         * So this check has been moved to separate rule
         * EventHandlersCannotInvokeHandlersRule and removed from here.
         */

        /*
         * Collect the list of event handler activities.
         */
        for (Activity activity : container.getActivities()) {
            if (Xpdl2ModelUtil.isEventHandlerActivity(activity)) {
                eventHandlers.add(activity);
            }
        }

        if (!eventHandlers.isEmpty()) {
            /*
             * Create a set that is initially the set of activity id's that are
             * downstream of any start process activity (not including event
             * handlers).
             */
            for (Activity activity : container.getActivities()) {

                if (Xpdl2ModelUtil.isStartProcessActivity(activity)) {
                    downstreamOfStartActs.addAll(flowAnalyser
                            .getDownstreamActivities(activity.getId(), false));
                }
            }

            /*
             * Now all we need to do is check whether any activity that is down
             * stream of an event handler is also downstream of any start of
             * process activity (or any other event handler).
             */
            Set<Activity> downstreamOfStartsAndEventHandlers =
                    new HashSet<Activity>(downstreamOfStartActs);

            Set<Activity> joinActivities = new HashSet<Activity>();

            for (Activity eventHandlerActivity : eventHandlers) {
                Set<Activity> downstreamOfEventHandlerActivities =
                        flowAnalyser
                                .getDownstreamActivities(eventHandlerActivity
                                        .getId(), false);

                boolean hasJoinToOtherFlow = false;

                for (Activity activity : downstreamOfEventHandlerActivities) {
                    if (downstreamOfStartsAndEventHandlers.contains(activity)) {
                        /* This activity is downstream of other threads. */
                        joinActivities.add(eventHandlerActivity);

                        hasJoinToOtherFlow = true;

                        /* Only need to add the initial join point. */
                        break;
                    }
                }

                /*
                 * Sid XPD-6894 - finally determined that Checking for one event
                 * handler / event sub-process triggerring another was just too
                 * difficult mixed in with the check for joining flows method.
                 * 
                 * So this check has been moved to separate rule
                 * EventHandlersCannotInvokeHandlersRule and removed from here.
                 */

                /*
                 * If not already found a problem then check for
                 * arbitrary-length activities downstream of event handlers and
                 * add warning that these will prevent migration whilst active.
                 */
                if (!hasJoinToOtherFlow) {

                    /*
                     * XPD-6759: Saket: Raise separate warning marker for event
                     * sub-process start events.
                     */
                    if (EventObjectUtil
                            .isEventSubProcessStartEvent(eventHandlerActivity)) {

                        checkForDownStreamArbitraryLengthActivities(eventHandlerActivity,
                                downstreamOfEventHandlerActivities,
                                new HashSet<FlowContainer>(),
                                ISSUE_ESP_START_EVENT_HAS_ARBITRARY_LENGTH_ACTIVITIES);
                    } else {

                        checkForDownStreamArbitraryLengthActivities(eventHandlerActivity,
                                downstreamOfEventHandlerActivities,
                                new HashSet<FlowContainer>(),
                                ISSUE_EVENTHANDLER_HAS_ARBITRARY_LENGTH_ACTIVITIES);
                    }
                }

                /*
                 * When we're done checking this event handler downstream flow
                 * add it's downstream activities to the overall set (thus we
                 * check that event handler threads don't join with other event
                 * handler threads.
                 */
                downstreamOfStartsAndEventHandlers
                        .addAll(downstreamOfEventHandlerActivities);
            }

            /*
             * Add issue for each event handler activity that has a join to main
             * activities or another event handler.
             */
            for (Activity activity : joinActivities) {

                /*
                 * XPD-6759: Saket: Raise separate warning marker for event
                 * sub-process start events.
                 */
                if (EventObjectUtil.isEventSubProcessStartEvent(activity)) {
                    addIssue(ISSUE_ESP_START_EVENT_FLOW_CANT_JOIN, activity);
                } else {
                    addIssue(ISSUE_EVENTHANDLER_FLOW_CANT_JOIN, activity);
                }
            }

        }

        return;
    }

    /**
     * Check for arbitrary-length activities downstream of event handlers and
     * add warning that these will prevent migration whilst active.
     * <p>
     * This method is recursive thru multiple layers of embedded / reusabled
     * sub-process
     * 
     * @param eventHandlerActivity
     * @param downstreamOfEventHandlerActivities
     * @param issueId
     */
    private void checkForDownStreamArbitraryLengthActivities(
            Activity eventHandlerActivity,
            Set<Activity> downstreamOfEventHandlerActivities,
            Set<FlowContainer> alreadyProcessedProcesses, String issueId) {

        /* Check for arbitrary length activities in the list. */
        for (Activity downstreamActivity : downstreamOfEventHandlerActivities) {
            if (isArbitraryLengthActivity(downstreamActivity)) {
                String taskLabel;
                if (!eventHandlerActivity.getProcess()
                        .equals(downstreamActivity.getProcess())) {
                    taskLabel =
                            Xpdl2ModelUtil
                                    .getDisplayNameOrName(downstreamActivity
                                            .getProcess())
                                    + "/" //$NON-NLS-1$
                                    + Xpdl2ModelUtil
                                            .getDisplayNameOrName(downstreamActivity);
                } else {
                    taskLabel =
                            Xpdl2ModelUtil
                                    .getDisplayNameOrName(downstreamActivity);
                }

                addIssue(issueId,
                        eventHandlerActivity,
                        Collections.singletonList(taskLabel));
            }
        }

        /* Check for need to recurs for sub-processes and do so. */
        for (Activity downstreamActivity : downstreamOfEventHandlerActivities) {
            TaskType taskType =
                    TaskObjectUtil.getTaskTypeStrict(downstreamActivity);

            if (TaskType.SUBPROCESS_LITERAL.equals(taskType)) {
                /*
                 * Gather the activities downstream of all start activities and
                 * recurs.
                 */
                EObject procOrIfc =
                        TaskObjectUtil
                                .getSubProcessOrInterface(downstreamActivity);

                if (procOrIfc instanceof Process
                        && !alreadyProcessedProcesses.contains(procOrIfc)) {

                    alreadyProcessedProcesses.add((Process) procOrIfc);

                    Set<Activity> subDownstreamActivities =
                            getActivitiesDownstreamOfStarts((Process) procOrIfc);

                    if (!subDownstreamActivities.isEmpty()) {
                        checkForDownStreamArbitraryLengthActivities(eventHandlerActivity,
                                subDownstreamActivities,
                                alreadyProcessedProcesses,
                                issueId);
                    }
                }
            } else if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(taskType)
                    || TaskType.EVENT_SUBPROCESS_LITERAL.equals(taskType)) {

                /*
                 * ABPM-911: Saket: An event subprocess should mostly behave
                 * like an embedded sub-process.
                 */

                /*
                 * Gather the activities downstream of all start activities and
                 * recurs.
                 */
                ActivitySet embeddedSubProcessActivitySet =
                        Xpdl2ModelUtil
                                .getEmbeddedSubProcessActivitySet(downstreamActivity);
                if (embeddedSubProcessActivitySet != null
                        && !alreadyProcessedProcesses
                                .contains(embeddedSubProcessActivitySet)) {

                    alreadyProcessedProcesses
                            .add(embeddedSubProcessActivitySet);

                    Set<Activity> subDownstreamActivities =
                            getActivitiesDownstreamOfStarts(embeddedSubProcessActivitySet);

                    if (!subDownstreamActivities.isEmpty()) {
                        checkForDownStreamArbitraryLengthActivities(eventHandlerActivity,
                                subDownstreamActivities,
                                alreadyProcessedProcesses,
                                issueId);
                    }
                }
            }
        }

    }

    /**
     * @param flowContainer
     * @return get all activities that are downstream of the start activities in
     *         the given flowContainer (not including activities within
     *         embedded/reusable sub-processes)..
     */
    private Set<Activity> getActivitiesDownstreamOfStarts(
            FlowContainer flowContainer) {
        ProcessFlowAnalyser subFlowAnalyser =
                getTool(ProcessFlowAnalyserPreProcessor.class, flowContainer)
                        .getProcessFlowAnalyser();

        Set<Activity> subDownstreamActivities = new HashSet<Activity>();

        for (Activity subActivity : flowContainer.getActivities()) {

            if (Xpdl2ModelUtil.isStartProcessActivity(subActivity)) {
                subDownstreamActivities.addAll(subFlowAnalyser
                        .getDownstreamActivities(subActivity.getId(), false));
            }
        }

        return subDownstreamActivities;
    }

    /**
     * @param activity
     * 
     * @return <code>true</code> if the given activity is one that can be
     *         long-running.
     */
    private boolean isArbitraryLengthActivity(Activity activity) {
        /*
         * Arbitrary length activities are receive request type activities
         * (catch message event / receive task) and User Tasks.
         */
        if (ReplyActivityUtil.isIncomingRequestActivity(activity)
                || TaskType.USER_LITERAL.equals(TaskObjectUtil
                        .getTaskTypeStrict(activity))) {
            return true;
        }
        return false;
    }

    /**
     * @param requestActivity
     * @param replyActivity
     * @param flowAnalyser
     * 
     * @return List of activities between the two given activities
     */
    private Set<Activity> getActivitiesBetween(Activity requestActivity,
            Activity replyActivity, ProcessFlowAnalyser flowAnalyser) {

        Process process = requestActivity.getProcess();

        Set<String> intermediateActivityIds =
                flowAnalyser.getActivitiesBetween(requestActivity.getId(),
                        replyActivity.getId(),
                        null,
                        false);

        Set<Activity> intermediateActivities = new HashSet<Activity>();
        if (intermediateActivityIds != null) {

            for (String activityId : intermediateActivityIds) {

                if (!activityId.equals(requestActivity.getId())
                        && !activityId.equals(replyActivity.getId())) {

                    intermediateActivities.add(Xpdl2ModelUtil
                            .getActivityById(process, activityId));
                }
            }

        }
        return intermediateActivities;
    }

}
