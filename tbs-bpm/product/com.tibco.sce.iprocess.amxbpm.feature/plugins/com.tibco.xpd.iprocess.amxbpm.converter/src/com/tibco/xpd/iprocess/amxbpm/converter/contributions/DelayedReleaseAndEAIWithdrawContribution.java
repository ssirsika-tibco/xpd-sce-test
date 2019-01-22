/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.ipm.iProcessExt.DatabaseTask;
import com.tibco.xpd.ipm.iProcessExt.DelayedReleaseType;
import com.tibco.xpd.ipm.iProcessExt.EAIStepTask;
import com.tibco.xpd.ipm.iProcessExt.IProcessExtPackage;
import com.tibco.xpd.ipm.iProcessExt.TaskProperties;
import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;
import com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AllocationStrategyType;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.ConnectorGraphicsInfo;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Convert EAI step withdraw actions to make them suite AMX BPM environment.
 * <p>
 * The conversion will insert a receive task (called "Release <EAI Step Label>"
 * into the flow after the service task created for the EAI step (with all flow
 * redirected to the output of the receive task). As there is no design time
 * information regarding the data that may have been returned in the iProcess
 * delayed release instruction from the external system, the created Receive
 * Task will have the FIX-ME annotation associated to force the user to perform
 * some remedial action.
 * <p>
 * The conversion will be amended to reconnect the 'catch signal event' outgoing
 * flow to the service task derived from the withdraw action if that previously
 * was pointing towards and end event.
 * <p>
 * If the timer event currently points towards an end event, then the conversion
 * will re-direct the event transition towards the specified task and delete the
 * end event to which it was previously directed.
 * <p>
 * If the timer event is NOT pointing towards an event, then insert a parallel
 * gateway which'd accept an incoming flow from the timer event and will split
 * into two flow: one towards the activity which the timer was already pointing
 * on AND the other towards the specified compensation task.
 * <p>
 * Where the original EAI step was defined as delayed release then the
 * conversion will:
 * <p>
 * - Delete the catch-compensation event attached the service task’s boundary
 * (and its association.
 * <p>
 * - Move all events attached to the service task created for the original EAI
 * step onto the receive task created for triggering the delayed release.
 * <p>
 * - Any of those events that are cancelling events (all catch signals and timer
 * events set to Withdraw on timeout) should be re-linked onto the service task
 * created for the EAI step’s withdraw action. Non-cancelling events will be
 * left attached to their respective end event.
 * <p>
 * - Any end event ‘left dangling’ will be removed.
 * 
 * 
 * @author sajain
 * @since May 21, 2014
 */
public class DelayedReleaseAndEAIWithdrawContribution extends
        AbstractIProcessToBPMContribution {

    private XpdExtensionFactory xpdExtensionFactory = getXpdExtensionFactory();

    private XpdExtensionPackage xpdExtensionPackage = getXpdExtensionPackage();

    private Xpdl2Factory xpdlFactory = getXpdlFactory();

    private static final String DOT = "."; //$NON-NLS-1$

    /**
     * Border color to be used in connector graphics info.
     */
    private static final String BORDER_COLOR = "0,0,128"; //$NON-NLS-1$

    /**
     * Map<activityID, Activity> for compensation Event Activity in a given
     * Process.
     */
    private Map<String, Activity> compensationCatchEventsInProcess =
            new HashMap<String, Activity>();

    /**
     * Map<activityID, Activity> for activities with Delayed release set, in a
     * given Process.
     */
    private Map<String, Activity> activitiesWithDelayedReleaseInProcess =
            new HashMap<String, Activity>();

    /**
     * Map<ActivityID, ActivityID> for activities with Delayed release set to
     * their corresponding receive tasks.
     */
    private Map<String, String> activitiesWithDelayedReleaseToReceiveTask =
            new HashMap<String, String>();

    /**
     * Map<activityID, Activity> for Catch Signal Event Activities and Timer
     * Event Activities, in a given Process.
     */
    private Map<String, Activity> catchSignalAndTimerEventActivitiesInProcess =
            new HashMap<String, Activity>();

    /**
     * @see com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution#convert(java.util.List,
     *      java.util.List, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param processes
     * @param processInterfaces
     * @param monitor
     * @return
     */
    @Override
    public IStatus convert(List<Process> processes,
            List<ProcessInterface> processInterfaces, IProgressMonitor monitor) {

        try {

            monitor.beginTask("", processes.size()); //$NON-NLS-1$

            for (Process eachProcess : processes) {

                Collection<Activity> allProcessActivities =
                        Xpdl2ModelUtil.getAllActivitiesInProc(eachProcess);

                if (allProcessActivities != null
                        && !allProcessActivities.isEmpty()) {
                    /*
                     * Filter/separate all activities in the process to specific
                     * lists of items to be worked on.
                     */
                    cacheEventActivitiesForProcess(allProcessActivities);

                    /*
                     * Map containing EAI Task with its corresponding withdraw
                     * compensation task.
                     */
                    Map<Activity, Activity> eaiTaskWithCompTask =
                            new HashMap<Activity, Activity>();

                    for (Activity compensationCatchEventAct : compensationCatchEventsInProcess
                            .values()) {

                        Activity compensationTask =
                                getCompensationTaskFromCompensationEvent(compensationCatchEventAct);

                        /*
                         * Check if there is a withdraw compensation task and
                         * then only proceed.
                         */
                        if (null != compensationTask) {

                            /*
                             * Fetch 'target' of the compensation event as
                             * that'll be the EAI task on to which the catch
                             * signal/timer events are also attached.
                             */
                            Activity eaiTask =
                                    EventObjectUtil
                                            .getTaskAttachedTo(compensationCatchEventAct);
                            /*
                             * Collect EAI tasks with compensations.
                             */
                            if (eaiTask != null) {

                                eaiTaskWithCompTask.put(eaiTask,
                                        compensationTask);

                            }

                        }
                    }

                    /*
                     * Now first of all handle Activities with delayed release
                     * set.
                     */
                    for (Activity delayedReleaseAct : activitiesWithDelayedReleaseInProcess
                            .values()) {
                        /*
                         * If the task has delayed release set, then add a
                         * receive task ahead of it in the flow along with an
                         * FIX-ME annotation.
                         */
                        addReceiveTaskAfterEAITask(delayedReleaseAct);
                    }

                    /*
                     * Finally, process EAI withdraw action to make them suit
                     * AMX BPM environment.
                     */
                    processEAIWithdraw(eaiTaskWithCompTask,
                            allProcessActivities);

                }
                monitor.worked(1);
            }

        } finally {
            monitor.done();
        }
        return Status.OK_STATUS;
    }

    /**
     * 
     * Filters the activity tasks and events in the process, to specific lists
     * of Compensation Catch Events, Activities with Delayed release Set, Catch
     * Signal/TimerEvent Activities.
     * 
     * @param allProcessActivities
     */
    private void cacheEventActivitiesForProcess(
            Collection<Activity> allProcessActivities) {

        compensationCatchEventsInProcess.clear();
        activitiesWithDelayedReleaseInProcess.clear();
        catchSignalAndTimerEventActivitiesInProcess.clear();

        for (Activity activity : allProcessActivities) {

            if (isCompensationCatchEvent(activity)) {

                compensationCatchEventsInProcess
                        .put(activity.getId(), activity);

            } else if (hasDelayedReleaseSet(activity)) {

                activitiesWithDelayedReleaseInProcess.put(activity.getId(),
                        activity);

            } else if (isCatchSignalEvent(activity) || isTimerEvent(activity)) {

                catchSignalAndTimerEventActivitiesInProcess.put(activity
                        .getId(), activity);
            }
        }
    }

    /**
     * Handle EAI withdraw action in BPM by connecting the cancelling catch
     * event/timer event with withdraw on timeout on to the withdraw
     * compensation task.
     * 
     * @param eaiTaskWithCompTask
     *            EAI task to which the events are attached.
     * @param allProcessActivities
     *            Withdraw compensation to which events have to be connected.
     * @param allActivities
     *            List of activities in the process.
     */
    private void processEAIWithdraw(
            Map<Activity, Activity> eaiTaskWithCompTask,
            Collection<Activity> allProcessActivities) {

        /*
         * Process all the 'catch events' and 'timer events' in order to process
         * events which are attached to an eaiTask.
         */
        for (Activity catSigOrTimerEvent : catchSignalAndTimerEventActivitiesInProcess
                .values()) {

            /*
             * Get the task to which this catch/timer event is attached
             */
            Activity attachedTask =
                    EventObjectUtil.getTaskAttachedTo(catSigOrTimerEvent);

            /*
             * Check if eaiTask has got compensation task associated (that's the
             * only way to figure out whether it has a withdraw action on)
             */
            if (null != attachedTask
                    && eaiTaskWithCompTask.keySet().contains(attachedTask)) {

                /*
                 * Check if this withdraw eai task has delayed release set as
                 * well.
                 */
                if (activitiesWithDelayedReleaseInProcess
                        .containsKey(attachedTask.getId())) {

                    /*
                     * If it is one of those, then manage event's transitions.
                     */
                    manageEventTransitions(catSigOrTimerEvent,
                            eaiTaskWithCompTask.get(attachedTask));

                }
            }
        }

        /*
         * Process all the EAI withdraw activities
         */
        for (Activity eachEAIWithdrawActivity : eaiTaskWithCompTask.keySet()) {

            /*
             * Check if the eai task with withdraw has 'delayed release' set as
             * well.
             */
            if (activitiesWithDelayedReleaseInProcess
                    .containsKey(eachEAIWithdrawActivity.getId())) {

                /*
                 * Remove the compensation catch event attached to the service
                 * task and move all other events attached to the service task
                 * created for the original EAI step onto the receive task
                 * created for triggering the delayed release.
                 */
                manageEventsAttachedToEAITaskWithDelayedRelease(eachEAIWithdrawActivity);

            } else {

                /*
                 * If it is just a eai withdraw task without 'delayed release',
                 * then add a FIX-ME annotation on the associated compensation
                 * task.
                 */
                IpmImportUtil
                        .addFixMeToActivity(eaiTaskWithCompTask
                                .get(eachEAIWithdrawActivity),
                                Messages.DRAndEAIWContribution_FIXME_TextAnnotation_text2);
            }
        }
    }

    /**
     * Remove the compensation catch event and move all other events attached to
     * the service task created for the original EAI step ONTO the receive task
     * created for triggering the delayed release.
     * 
     * @param eaiTask
     */
    private void manageEventsAttachedToEAITaskWithDelayedRelease(
            Activity eaiTask) {

        /*
         * List of catch compensation event activities to be deleted.
         */
        List<Activity> activitiesToBeDeleted = new ArrayList<Activity>();

        /*
         * Process all activities to remove the catch compensation events AND
         * move all other events attached to the service task created for the
         * original EAI step ONTO the receive task created for triggering the
         * delayed release.
         */
        for (Activity eachProcessActivity : eaiTask.getFlowContainer()
                .getActivities()) {

            /*
             * Fetch the activity's event.
             */
            Event ev = eachProcessActivity.getEvent();

            /*
             * See if it's an intermediate event.
             */
            if (null != ev && ev instanceof IntermediateEvent) {

                /*
                 * See if this intermediate event is attached to the eai task
                 * currently being processed.
                 */
                if (null != EventObjectUtil
                        .getTaskAttachedTo(eachProcessActivity)
                        && EventObjectUtil
                                .getTaskAttachedTo(eachProcessActivity).getId()
                                .equals(eaiTask.getId())) {

                    /*
                     * See if it's a catch compensation event because in that
                     * case we'd want to delete it.
                     */
                    if (compensationCatchEventsInProcess
                            .containsKey(eachProcessActivity.getId())) {

                        /*
                         * If it is a compensation catch event, remove the
                         * outgoing associations and transitions.
                         */
                        eaiTask.getProcess()
                                .getPackage()
                                .getAssociations()
                                .removeAll(eachProcessActivity.getOutgoingAssociations());

                        eaiTask.getFlowContainer()
                                .getTransitions()
                                .removeAll(eachProcessActivity.getOutgoingTransitions());

                        /*
                         * Add it to the list of activities to be deleted.
                         */
                        activitiesToBeDeleted.add(eachProcessActivity);

                    } else {

                        /*
                         * Else it's a genuine intermediate event attached to an
                         * eai task with delayed release set, so move it onto
                         * the receive task which we've created ahead of the eai
                         * task in the flow.
                         */
                        IntermediateEvent intermediateEvt =
                                (IntermediateEvent) ev;

                        /*
                         * Set it's target to the receive task which we've added
                         * ahead of this eai task.
                         */
                        intermediateEvt
                                .setTarget(activitiesWithDelayedReleaseToReceiveTask
                                        .get(eaiTask.getId()));
                    }
                }
            }
        }

        /*
         * Delete the compensation catch event activities which we've added to
         * the list of activities to be deleted.
         */
        eaiTask.getFlowContainer().getActivities()
                .removeAll(activitiesToBeDeleted);
    }

    /**
     * Get the withdraw compensation task from the compensation event. If there
     * is no such task, then return <code>null</code>>.
     * 
     * @param compensationEvent
     * @return Withdraw compensation task from the compensation event. If there
     *         is no such task, then return <code>null</code>>.
     */
    private Activity getCompensationTaskFromCompensationEvent(
            Activity compensationEvent) {
        /*
         * Compensation event is 'associated' with compensation task, so scan
         * all the associations to get hold of the compensation task.
         */
        for (Association eachAssociation : compensationEvent
                .getOutgoingAssociations()) {

            /*
             * Fetch the target activity of the association.
             */
            Activity targetAct =
                    compensationEvent.getFlowContainer()
                            .getActivity(eachAssociation.getTarget());

            if (targetAct.isIsForCompensation()) {
                return targetAct;
            }

        }

        return null;
    }

    /**
     * Manage event transitions.
     * 
     * @param evt
     * @param compensationTask
     */
    private void manageEventTransitions(Activity evt, Activity compensationTask) {

        /*
         * Check if there are no outgoing transitions at all because then we'll
         * have to create a new transition from event to the compensation task.
         */
        if (evt.getOutgoingAssociations() == null
                || evt.getOutgoingTransitions().isEmpty()) {

            /*
             * Create a new transition from evt to compensationTask.
             */
            Transition newTransition = getXpdlFactory().createTransition();
            newTransition.setFrom(evt.getId());
            newTransition.setTo(compensationTask.getId());

            ConnectorGraphicsInfo newCGInfo =
                    getXpdlFactory().createConnectorGraphicsInfo();

            newCGInfo.setBorderColor(BORDER_COLOR);
            newCGInfo.setToolId(Xpdl2ModelUtil.STUDIO_SPECIFIC_TOOL_ID + DOT
                    + Xpdl2ModelUtil.CONNECTION_INFO_IDSUFFIX);

            newTransition.getConnectorGraphicsInfos().add(newCGInfo);

            evt.getFlowContainer().getTransitions().add(newTransition);
        }

        /*
         * Go through event's all outgoing transitions.
         */
        for (Transition eachTransition : evt.getOutgoingTransitions()) {

            if (eachTransition != null) {

                /*
                 * Check the type of the event being processed and act
                 * accordingly.
                 */
                if (isCatchSignalEvent(evt)) {

                    /*
                     * If it is a catch signal event, then re-direct the event
                     * transition towards the compensation task (do this only
                     * when the catch signal event currently points towards an
                     * end event and after redirecting delete the dangling end
                     * event as well.)
                     */
                    setIntermediateCatchEventTargetToTask(evt,
                            compensationTask,
                            eachTransition);

                } else if (isTimerEvent(evt)) {

                    /*
                     * In case if the event is a timer event, then we'll have to
                     * see if it's going to be withdrawn on timeout OR not, and
                     * then act accordingly. Apparently, WE DON'T DO ANYTHING AT
                     * ALL WHEN THE TIMER EVENT IS GOING TO CONTINUE ON TIMEOUT.
                     */
                    if (isTimerEventWithdrawOnTimeout(evt)) {

                        setTimerEventTargetToTask(evt,
                                compensationTask,
                                eachTransition);

                    }
                }

            }
        }

    }

    /**
     * If the specified catch signal event currently points towards an end
     * event, then re-direct the event transition towards the specified task and
     * delete the end event to which it was previously directed.
     * 
     * @param evt
     *            The source event.
     * @param compensationTask
     *            The new target.
     * @param transition
     *            The transition to be edited.
     */
    private void setIntermediateCatchEventTargetToTask(Activity evt,
            Activity compensationTask, Transition transition) {
        /*
         * Get transition target.
         */
        Activity targetAct =
                evt.getFlowContainer().getActivity(transition.getTo());

        if (EventFlowType.FLOW_END_LITERAL.equals(EventObjectUtil
                .getFlowType(targetAct))) {
            transition.setTo(compensationTask.getId());

            /*
             * Remove the target activity now as it is of no use.
             */
            evt.getFlowContainer().getActivities().remove(targetAct);
        }
    }

    /**
     * If the specified timer event currently points towards an end event, then
     * re-direct the event transition towards the specified task and delete the
     * end event to which it was previously directed.
     * <p>
     * If the timer event is NOT pointing towards an event, then insert a
     * parallel gateway which'd accept an incoming flow from the timer event and
     * will split into two flow: one towards the activity which the timer was
     * already pointing on AND the other towards the specified compensation
     * task.
     * 
     * @param evt
     *            The source event.
     * @param compensationTask
     *            The new target.
     * @param transition
     *            The transition to be edited.
     */
    private void setTimerEventTargetToTask(Activity evt,
            Activity compensationTask, Transition transition) {
        /*
         * Get transition target.
         */
        Activity targetAct =
                evt.getFlowContainer().getActivity(transition.getTo());

        if (null == targetAct) {

            /*
             * No target activity, hence just point towards the compensation
             * task.
             */
            transition.setTo(compensationTask.getId());

        } else if (EventFlowType.FLOW_END_LITERAL.equals(EventObjectUtil
                .getFlowType(targetAct))) {

            /*
             * Target activity is an end event, so redirect the transition
             * towards the compensation task.
             */
            transition.setTo(compensationTask.getId());

            /*
             * Remove the target activity now as it is of no use.
             */
            evt.getFlowContainer().getActivities().remove(targetAct);

        } else {

            /*
             * Target activity is there and it's not an end event either, so add
             * a parallel gateway which'd split towards the current target
             * activity and the compensation task.
             */

            /*
             * Create a brand new parallel gateway.
             */
            Activity parallelGate = getNewParallelGateway();

            NodeGraphicsInfo nGInfo =
                    EcoreUtil.copy(Xpdl2ModelUtil
                            .getNodeGraphicsInfo(compensationTask));

            if (null != nGInfo) {
                addNodeGraphicsInfoToGateway(parallelGate, nGInfo);
            }

            evt.getFlowContainer().getActivities().add(parallelGate);

            /*
             * Point event's outgoing transition to the newly created parallel
             * gateway.
             */
            transition.setTo(parallelGate.getId());

            /*
             * Create two new transitions from parallel gate to the current
             * target activity and the compensation task respectively.
             */

            /*
             * First transition: from parallel gate to current target activity.
             */
            Transition newTransition1 = getXpdlFactory().createTransition();
            newTransition1.setFrom(parallelGate.getId());
            newTransition1.setTo(targetAct.getId());

            ConnectorGraphicsInfo newCGInfo1 =
                    getXpdlFactory().createConnectorGraphicsInfo();

            newCGInfo1.setBorderColor(BORDER_COLOR);
            newCGInfo1.setToolId(Xpdl2ModelUtil.STUDIO_SPECIFIC_TOOL_ID + DOT
                    + Xpdl2ModelUtil.CONNECTION_INFO_IDSUFFIX);

            newTransition1.getConnectorGraphicsInfos().add(newCGInfo1);

            /*
             * Second transition: from parallel gateway to the compensation
             * task.
             */
            Transition newTransition2 = getXpdlFactory().createTransition();
            newTransition2.setFrom(parallelGate.getId());
            newTransition2.setTo(compensationTask.getId());

            ConnectorGraphicsInfo newCGInfo2 = EcoreUtil.copy(newCGInfo1);

            newTransition2.getConnectorGraphicsInfos().add(newCGInfo2);

            /*
             * Add the new transitions into the process model.
             */
            evt.getFlowContainer().getTransitions().add(newTransition1);
            evt.getFlowContainer().getTransitions().add(newTransition2);

        }
    }

    /**
     * Creates and returns a brand new parallel gateway.
     * 
     * @return newly created XOR gateway.
     */
    private Activity getNewParallelGateway() {
        /*
         * Create new activity.
         */
        Activity parallelGateway = getXpdlFactory().createActivity();

        /*
         * Create and configure route.
         */
        Route route = getXpdlFactory().createRoute();
        route.setGatewayType(JoinSplitType.PARALLEL_LITERAL);

        /*
         * Add route to activity.
         */
        parallelGateway.setRoute(route);

        return parallelGateway;
    }

    /**
     * Returns <code>true</code>> is the specified activity is a compensation
     * catch event, <code>false</code>> otherwise.
     * 
     * @param act
     * @return <code>true</code>> is the specified activity is a compensation
     *         catch event, <code>false</code>> otherwise.
     */
    private boolean isCompensationCatchEvent(Activity act) {
        return EventFlowType.FLOW_INTERMEDIATE_LITERAL.equals(EventObjectUtil
                .getFlowType(act))
                && EventTriggerType.EVENT_COMPENSATION_CATCH_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(act));
    }

    /**
     * Returns <code>true</code>> is the specified activity is a catch signal
     * event, <code>false</code>> otherwise.
     * 
     * @param act
     * @return <code>true</code>> is the specified activity is a catch signal
     *         event, <code>false</code>> otherwise.
     */
    private boolean isCatchSignalEvent(Activity act) {

        return EventFlowType.FLOW_INTERMEDIATE_LITERAL.equals(EventObjectUtil
                .getFlowType(act))
                && EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(act));

    }

    /**
     * Returns <code>true</code>> is the specified activity is a timer event,
     * <code>false</code>> otherwise.
     * 
     * @param act
     * @return <code>true</code>> is the specified activity is a timer event,
     *         <code>false</code>> otherwise.
     */
    private boolean isTimerEvent(Activity act) {
        return EventFlowType.FLOW_INTERMEDIATE_LITERAL.equals(EventObjectUtil
                .getFlowType(act))
                && EventTriggerType.EVENT_TIMER_LITERAL.equals(EventObjectUtil
                        .getEventTriggerType(act));

    }

    /**
     * Returns <code>true</code>> if the specified timer event is going to be
     * withdrawn on timeout, <code>false</code>> otherwise.
     * 
     * @param timerEvent
     * @return <code>true</code>> is the specified activity is a timer event,
     *         <code>false</code>> otherwise.
     */
    private boolean isTimerEventWithdrawOnTimeout(Activity timerEvent) {

        return (!EventObjectUtil.getContinueOnTimeoutFlag(timerEvent));

    }

    /**
     * Adds node graphics information for the specified task (passed as a node)
     * according to the specified co-ordinates and lane ID.
     * 
     * @param node
     * @param laneId
     * @param xCoOrd
     * @param yCoOrd
     */
    private void addNodeGraphicsInfoToTask(GraphicalNode node,
            NodeGraphicsInfo nGInfo) {

        double xCoOrd =
                nGInfo.getCoordinates().getXCoordinate() + 1.25
                        * nGInfo.getWidth();

        nGInfo.getCoordinates().setXCoordinate(xCoOrd);

        nGInfo.setHeight(ProcessWidgetConstants.TASK_HEIGHT_SIZE);
        nGInfo.setWidth(ProcessWidgetConstants.TASK_WIDTH_SIZE);

        node.getNodeGraphicsInfos().add(nGInfo);
    }

    /**
     * Adds node graphics information for the specified gateway (passed as a
     * node) according to the specified co-ordinates and lane ID.
     * 
     * @param node
     * @param laneId
     * @param xCoOrd
     * @param yCoOrd
     */
    private void addNodeGraphicsInfoToGateway(GraphicalNode node,
            NodeGraphicsInfo nGInfo) {

        double xCoOrd =
                nGInfo.getCoordinates().getXCoordinate() - 0.5
                        * nGInfo.getWidth();

        double yCoOrd =
                nGInfo.getCoordinates().getYCoordinate() - 0.5
                        * nGInfo.getHeight();

        nGInfo.getCoordinates().setXCoordinate(xCoOrd);
        nGInfo.getCoordinates().setYCoordinate(yCoOrd);

        nGInfo.setHeight(ProcessWidgetConstants.GATEWAY_HEIGHT_SIZE);
        nGInfo.setWidth(ProcessWidgetConstants.GATEWAY_WIDTH_SIZE);

        node.getNodeGraphicsInfos().add(nGInfo);
    }

    /**
     * Checks if the specified activity refers a process interface.
     * 
     * @param act
     * @return
     */
    private boolean hasDelayedReleaseSet(Activity act) {

        Object taskPropElem =
                getExtensionElement(act,
                        IProcessExtPackage.eINSTANCE
                                .getDocumentRoot_TaskProperties());

        if (null != taskPropElem && taskPropElem instanceof TaskProperties) {

            TaskProperties taskProp = (TaskProperties) taskPropElem;
            DatabaseTask dbTsk = taskProp.getDatabaseTask();
            EAIStepTask eaiStepTsk = taskProp.getEAIStepTask();

            if (null != dbTsk) {

                return (DelayedReleaseType.ALWAYS.equals(dbTsk
                        .getDelayedReleaseType()) || DelayedReleaseType.CONDITIONAL
                        .equals(dbTsk.getDelayedReleaseType()));

            } else if (null != eaiStepTsk) {

                return (DelayedReleaseType.ALWAYS.equals(eaiStepTsk
                        .getDelayedReleaseType()) || DelayedReleaseType.CONDITIONAL
                        .equals(eaiStepTsk.getDelayedReleaseType()));
            }
        }
        return false;
    }

    /**
     * Add a receive task after the specified eai task along with a
     * <code>FIX-ME</code>> annotation.
     * 
     * @param eaiTask
     */
    private void addReceiveTaskAfterEAITask(Activity eaiTask) {
        /*
         * Create a new activity.
         */
        Activity newActivity = createNewReceiveTask();

        newActivity
                .setName(Messages.DRAndEAIWContribution_ReceiveTask_Name_Text
                        + eaiTask.getName());

        Xpdl2ModelUtil.setOtherAttribute(newActivity,
                xpdExtensionPackage.getDocumentRoot_DisplayName(),
                Messages.DRAndEAIWContribution_ReceiveTask_Name_Text
                        + eaiTask.getName());

        /*
         * Add node graphics info for the new activity.
         */
        NodeGraphicsInfo nGInfo =
                EcoreUtil.copy(Xpdl2ModelUtil.getNodeGraphicsInfo(eaiTask));

        if (null != nGInfo) {
            addNodeGraphicsInfoToTask(newActivity, nGInfo);
        }

        /*
         * Create a new message and set it to the receive task.
         */
        Message newMessage = xpdlFactory.createMessage();

        if (newActivity.getImplementation() instanceof Task) {

            Task tsk = (Task) newActivity.getImplementation();

            TaskReceive rcvTsk = tsk.getTaskReceive();

            rcvTsk.setMessage(newMessage);

            rcvTsk.setInstantiate(false);
        }

        /*
         * Create activity resource patterns and add it to newActivity.
         */
        ActivityResourcePatterns actResPatterns =
                xpdExtensionFactory.createActivityResourcePatterns();

        AllocationStrategy allocationStartegy =
                xpdExtensionFactory.createAllocationStrategy();

        allocationStartegy
                .setStrategy(AllocationStrategyType.SYSTEM_DETERMINED);

        actResPatterns.setAllocationStrategy(allocationStartegy);

        setExtensionElement(newActivity,
                xpdExtensionPackage.getDocumentRoot_ActivityResourcePatterns(),
                actResPatterns);

        /*
         * Add the activity to the flow container.
         */
        eaiTask.getFlowContainer().getActivities().add(newActivity);

        /*
         * Create and configure new web service operation and port type
         * operation for the receive task.
         */
        IpmImportUtil.addWebServiceOperationSetToGenerateWSDL(newActivity,
                newActivity.getProcess());

        /*
         * Fetch all the outgoing transitions from the eai task.
         */
        List<Transition> taskOutgoingTransitions =
                eaiTask.getOutgoingTransitions();

        /*
         * Go through all the transitions and change their source from the eai
         * task to the newly created activity.
         */
        for (Transition eachTransition : taskOutgoingTransitions) {
            eachTransition.setFrom(newActivity.getId());
        }

        /*
         * Create a new transition from the eai task to the newly created
         * activity.
         */
        Transition newTransition = xpdlFactory.createTransition();
        newTransition.setFrom(eaiTask.getId());
        newTransition.setTo(newActivity.getId());

        ConnectorGraphicsInfo newCGInfo =
                xpdlFactory.createConnectorGraphicsInfo();

        newCGInfo.setBorderColor(ProcessWidgetColors.getInstance()
                .getGraphicalNodeColor(null, ProcessWidgetColors.NOTE_LINE)
                .toString());

        newCGInfo.setToolId(Xpdl2ModelUtil.STUDIO_SPECIFIC_TOOL_ID
                + IpmImportUtil.DOT + Xpdl2ModelUtil.CONNECTION_INFO_IDSUFFIX);

        newTransition.getConnectorGraphicsInfos().add(newCGInfo);

        /*
         * Add the new transition to the flow container.
         */
        eaiTask.getFlowContainer().getTransitions().add(newTransition);

        /*
         * Add a FIX-ME annotation prompting the customer to modify receive task
         * to accept data that the original iProcess EAI step delayed release
         * instruction may have contained.
         */
        IpmImportUtil.addFixMeToActivity(newActivity,
                Messages.DRAndEAIWContribution_FIXME_TextAnnotation_text1);

        /*
         * Populate the 'eai task -> receive task' map which'd help while
         * handling withdraw actions later on.
         */
        activitiesWithDelayedReleaseToReceiveTask.put(eaiTask.getId(),
                newActivity.getId());

    }

    /**
     * Create and configures a new receive task.
     * 
     * @return a brand new Receive task.
     */
    private Activity createNewReceiveTask() {

        /*
         * Create a new activity.
         */
        Activity newActivity = xpdlFactory.createActivity();

        /*
         * Create a task.
         */
        Task task = xpdlFactory.createTask();

        /*
         * Create a receive task.
         */
        TaskReceive rcvTask = xpdlFactory.createTaskReceive();

        /*
         * Set implementation type to UNSPECIFIED.
         */
        setExtensionAttribute(rcvTask,
                xpdExtensionPackage.getDocumentRoot_ImplementationType(),
                "Unspecified"); //$NON-NLS-1$

        /*
         * Set implementation to UNSPECIFIED.
         */
        rcvTask.setImplementation(ImplementationType.UNSPECIFIED_LITERAL);

        task.setTaskReceive(rcvTask);

        newActivity.setImplementation(task);

        return newActivity;
    }
}
