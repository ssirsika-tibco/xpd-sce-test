/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.ipm.iProcessExt.DatabaseTask;
import com.tibco.xpd.ipm.iProcessExt.DelayedReleaseType;
import com.tibco.xpd.ipm.iProcessExt.EAIStepTask;
import com.tibco.xpd.ipm.iProcessExt.IProcessExtPackage;
import com.tibco.xpd.ipm.iProcessExt.TaskProperties;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * JUnit test to protect the EAI Withdraw contribution.
 * 
 * @author sajain
 * @since Jul 14, 2014
 */
public class IpsBpm12_EAIWithdrawAndDeadlineTest extends
        AbstractIProcessConversionTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {

        return CONVERSION_TYPE.IPS_TO_BPM_CONVERT;
    }

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
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpsBpm12_EAIWithdrawAndDeadlineTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iProcessToAMXBPMConversion.AbstractConversionTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/ConversionTests", "IpsBpm12_EAIWithdrawAndDeadlineTest/Process Packages{processes}/test6494_1.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };
        return testResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {
        for (IFile file : convertedXpdls) {
            if ("TEST6494.xpdl".equalsIgnoreCase(file.getName())) { //$NON-NLS-1$
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);
                EObject rootElement = wc.getRootElement();
                if (rootElement instanceof Package) {
                    Package pkg = (Package) rootElement;
                    for (Process eachProcess : pkg.getProcesses()) {
                        if ("TEST6494".equalsIgnoreCase(eachProcess.getName())) { //$NON-NLS-1$

                            Collection<Activity> allProcessActivities =
                                    Xpdl2ModelUtil
                                            .getAllActivitiesInProc(eachProcess);

                            if (allProcessActivities != null
                                    && !allProcessActivities.isEmpty()) {
                                /*
                                 * Filter/separate all activities in the process
                                 * to specific lists of items to be worked on.
                                 */
                                cacheEventActivitiesForProcess(allProcessActivities);

                                /*
                                 * Map containing EAI Task with its
                                 * corresponding withdraw compensation task.
                                 */
                                Map<Activity, Activity> eaiTaskWithCompTask =
                                        new HashMap<Activity, Activity>();

                                for (Activity compensationCatchEventAct : compensationCatchEventsInProcess
                                        .values()) {

                                    Activity compensationTask =
                                            getCompensationTaskFromCompensationEvent(compensationCatchEventAct);

                                    /*
                                     * Check if there is a withdraw compensation
                                     * task and then only proceed.
                                     */
                                    if (null != compensationTask) {

                                        /*
                                         * Fetch 'target' of the compensation
                                         * event as that'll be the EAI task on
                                         * to which the catch signal/timer
                                         * events are also attached.
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
                                 * Now first of all handle Activities with
                                 * delayed release set.
                                 */
                                for (Activity delayedReleaseAct : activitiesWithDelayedReleaseInProcess
                                        .values()) {
                                    /*
                                     * If the task has delayed release set, then
                                     * add a receive task ahead of it in the
                                     * flow along with an FIX-ME annotation.
                                     */
                                    checkForReceiveTaskAfterEAITask(delayedReleaseAct);
                                }

                                /*
                                 * Finally, process EAI withdraw action to make
                                 * them suit AMX BPM environment.
                                 */
                                testEAIWithdraw(eaiTaskWithCompTask,
                                        allProcessActivities);

                            }

                        }
                    }
                }
            }
        }
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
     * Test EAI withdraw action in BPM by checking for the connection between
     * the cancelling catch event/timer event with withdraw on timeout on to the
     * withdraw compensation task.
     * 
     * @param eaiTaskWithCompTask
     *            EAI task to which the events are attached.
     * @param allProcessActivities
     *            Withdraw compensation to which events should be connected.
     * @param allActivities
     *            List of activities in the process.
     */
    private void testEAIWithdraw(Map<Activity, Activity> eaiTaskWithCompTask,
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
                     * If it is one of those, then test event's transitions.
                     */
                    testEventTransitions(catSigOrTimerEvent,
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
                 * Check that the compensation catch event attached to the
                 * service task has been removed and all other events attached
                 * to the service task created for the original EAI step have
                 * been moved onto the receive task created for triggering the
                 * delayed release.
                 */
                testEventsAttachedToEAITaskWithDelayedRelease(eachEAIWithdrawActivity);

            } else {

                /*
                 * If it is just a eai withdraw task without 'delayed release',
                 * then check for a FIX-ME annotation on the associated
                 * compensation task.
                 */

                boolean hasFixMe = false;

                for (Association eachAssociation : eaiTaskWithCompTask
                        .get(eachEAIWithdrawActivity).getIncomingAssociations()) {

                    if (null != eachEAIWithdrawActivity.getProcess()
                            .getPackage()
                            .getArtifact(eachAssociation.getSource())) {
                        if (eachEAIWithdrawActivity
                                .getProcess()
                                .getPackage()
                                .getArtifact(eachAssociation.getSource())
                                .getTextAnnotation()
                                .equals("FIXME: Withdraw action serves no purpose for iProcess EAI step which are not configured as 'delayed release'.")) { //$NON-NLS-1$
                            hasFixMe = true;
                        }
                    }
                }

                if (!hasFixMe) {
                    fail(String
                            .format("Withdraw compensation task %s doesn't have the expected FIXME annotation.", //$NON-NLS-1$
                                    eaiTaskWithCompTask
                                            .get(eachEAIWithdrawActivity)
                                            .getName()));
                }
            }
        }
    }

    /**
     * Check that the compensation catch event attached to the service task has
     * been removed and all other events attached to the service task created
     * for the original EAI step have been moved onto the receive task created
     * for triggering the delayed release.
     * 
     * 
     * @param eaiTask
     */
    private void testEventsAttachedToEAITaskWithDelayedRelease(Activity eaiTask) {

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

                    fail(String
                            .format("An intermediate event %1$s is found to be attached to an eai withdraw task with delayed release set (%2$s).", //$NON-NLS-1$
                                    eachProcessActivity.getName(),
                                    eaiTask.getName()));

                }
            }
        }
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
     * Test event transitions.
     * 
     * @param evt
     * @param compensationTask
     */
    private void testEventTransitions(Activity evt, Activity compensationTask) {

        /*
         * Check if there are no outgoing transitions at all
         */
        if (evt.getOutgoingAssociations() == null
                || evt.getOutgoingTransitions().isEmpty()) {

            fail(String
                    .format("Event %s has not outgoing transitions at all.", evt.getName())); //$NON-NLS-1$
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
                     * If it is a catch signal event, then check that the event
                     * is NOT directed towards an end event.
                     */
                    checkIntermediateCatchEventTarget(evt, eachTransition);

                } else if (isTimerEvent(evt)) {

                    /*
                     * In case if the event is a timer event, then we'll have to
                     * see if it's going to be withdrawn on timeout OR not, and
                     * then test accordingly. Apparently, WE DON'T TEST ANYTHING
                     * AT ALL WHEN THE TIMER EVENT IS GOING TO CONTINUE ON
                     * TIMEOUT.
                     */
                    if (isTimerEventWithdrawOnTimeout(evt)) {

                        checkTimerEventTarget(evt, eachTransition);

                    }
                }

            }
        }

    }

    /**
     * Check that the event doesn't point towards an end event.
     * 
     * @param evt
     *            The source event.
     * @param transition
     *            The transition to be tested.
     */
    private void checkIntermediateCatchEventTarget(Activity evt,
            Transition transition) {
        /*
         * Get transition target.
         */
        Activity targetAct =
                evt.getFlowContainer().getActivity(transition.getTo());

        if (EventFlowType.FLOW_END_LITERAL.equals(EventObjectUtil
                .getFlowType(targetAct))) {
            fail(String
                    .format("The event %s should not point to an end event.", //$NON-NLS-1$
                            evt.getName()));
        }
    }

    /**
     * Check that the specified timer event doesn't point towards an end event.
     * 
     * @param evt
     *            The source event.
     * @param transition
     *            The transition to be edited.
     */
    private void checkTimerEventTarget(Activity evt, Transition transition) {
        /*
         * Get transition target.
         */
        Activity targetAct =
                evt.getFlowContainer().getActivity(transition.getTo());

        if (null == targetAct) {

            fail(String
                    .format("The event %s doesn't have any outgoing transition.", evt.getName())); //$NON-NLS-1$

        } else if (EventFlowType.FLOW_END_LITERAL.equals(EventObjectUtil
                .getFlowType(targetAct))) {

            fail(String
                    .format("The event %s is directed towards an end event.", evt.getName())); //$NON-NLS-1$

        }
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
     * Checks if the specified activity refers a process interface.
     * 
     * @param act
     * @return
     */
    private boolean hasDelayedReleaseSet(Activity act) {

        Object taskPropElem =
                Xpdl2ModelUtil.getOtherElement(act,
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
     * Check for a receive task after the specified eai task along with a
     * <code>FIX-ME</code>> annotation.
     * 
     * @param eaiTask
     */
    private void checkForReceiveTaskAfterEAITask(Activity eaiTask) {

        /*
         * Fetch all the outgoing transitions from the eai task.
         */
        List<Transition> taskOutgoingTransitions =
                eaiTask.getOutgoingTransitions();

        /*
         * There should be just one transition (towards the receive task)
         */
        assertEquals(1, taskOutgoingTransitions.size());

        for (Transition eachTransition : taskOutgoingTransitions) {

            Activity act =
                    eaiTask.getFlowContainer()
                            .getActivity(eachTransition.getTo());

            Implementation impl = act.getImplementation();

            /*
             * Look for receive task.
             */
            if (impl instanceof Task) {
                Task tsk = (Task) impl;

                TaskReceive rcvTask = tsk.getTaskReceive();

                if (null == rcvTask) {
                    fail(String
                            .format("The transition from %s wasn't directed towards a receive task.", eaiTask.getName())); //$NON-NLS-1$
                }

                /*
                 * Look for FIX-ME annotation.
                 */

                boolean hasFixMe = false;

                for (Association eachAssociation : act
                        .getIncomingAssociations()) {

                    if (null != act.getProcess().getPackage()
                            .getArtifact(eachAssociation.getSource())) {
                        if (act.getProcess()
                                .getPackage()
                                .getArtifact(eachAssociation.getSource())
                                .getTextAnnotation()
                                .equals("FIXME: Modify receive task to accept data that the original iProcess EAI step delayed release instruction may have contained.")) { //$NON-NLS-1$
                            hasFixMe = true;
                        }
                    }
                }

                if (!hasFixMe) {
                    fail(String
                            .format("Receive task %s doesn't have a FIXME annotation.", //$NON-NLS-1$
                                    act.getName()));
                }

            } else {
                fail(String
                        .format("The transition from %s wasn't directed towards a receive task.", eaiTask.getName())); //$NON-NLS-1$
            }

            activitiesWithDelayedReleaseToReceiveTask.put(eaiTask.getId(),
                    act.getId());
        }
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getOtherResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {
        // No other resources.
        return null;
    }
}