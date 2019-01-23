/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultCompensation;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author bharge
 * 
 */
public class CompensatedTaskRule extends ProcessValidationRule {

    private static final String NO_EVENT_ATTACHED =
            "bpmn.compensatedTaskDoesNotHaveEventAttached"; //$NON-NLS-1$

    private static final String EVENT_MUST_HAVE_ONE_ASSOCIATION_ID =
            "bpmn.noOutgoingAssociationForCompensatedTask"; //$NON-NLS-1$

    private static final String CANNOT_HAVE_SEQUENCE_FLOW_ID =
            "bpmn.cannotHaveIncomingOrOutgoingSequenceFlow"; //$NON-NLS-1$

    private static final String TASK_CANNOT_HAVE_MULTI_COMPENSATIONS_ID =
            "bpmn.taskCannotHaveMultipleCompensations"; //$NON-NLS-1$

    private static final String TASK_CANNOT_HAVE_MULTI_IN_ASSOCATIONS_ID =
            "bpmn.taskCannotHaveMultiInCompensation"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {

        /*
         * There were problems with some validation rules (such as
         * bpmn.noOutgoingAssociationForCompensatedTask was only issued if at
         * least one other compensation associaiton existed elsewhere.
         * 
         * So did a re-write (saw that other things weren't necessarily done in
         * the best way.
         */

        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        Map<String, Activity> activityIdMap = new HashMap<String, Activity>();

        /* Map of catch compensation events id to activity. */
        Map<String, Activity> catchCompensationEvents =
                new HashMap<String, Activity>();

        for (Activity activity : allActivitiesInProc) {
            activityIdMap.put(activity.getId(), activity);

            if (activity.getEvent() != null) {
                if (EventTriggerType.EVENT_COMPENSATION_CATCH_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(activity))) {
                    catchCompensationEvents.put(activity.getId(), activity);
                }
            }
        }

        /* Map of association source id to compensation association. */
        Map<String, Association> compenAssocSourceIdMap =
                new HashMap<String, Association>();

        /* Map of association target id to association. */
        HashMap<String, Association> compenAssocTargetIdMap =
                new HashMap<String, Association>();

        for (Association association : Xpdl2ModelUtil
                .getAllAssociationsInProc(process)) {
            /*
             * If source of association is a catch compensation event and target
             * is an activity (rather than an artifact etc) then it's a
             * Compensation association.
             */
            String sourceId = association.getSource();
            if (catchCompensationEvents.containsKey(sourceId)) {
                /* It's from a catch compensation. */
                String targetId = association.getTarget();
                if (activityIdMap.containsKey(targetId)) {
                    /*
                     * And to an activity.
                     * 
                     * Therefore it's a compensation association.
                     */
                    if (!compenAssocSourceIdMap.containsKey(sourceId)) {
                        compenAssocSourceIdMap.put(sourceId, association);
                    } else {
                        /*
                         * The source compensation event is already in list of
                         * association source ids - there are 2 associations
                         * from same event!
                         */
                        addIssue(EVENT_MUST_HAVE_ONE_ASSOCIATION_ID,
                                activityIdMap.get(sourceId));
                    }

                    if (!compenAssocTargetIdMap.containsKey(targetId)) {
                        compenAssocTargetIdMap.put(targetId, association);

                    } else {
                        /*
                         * This task is already the target of a compensation
                         * association.
                         */
                        addIssue(TASK_CANNOT_HAVE_MULTI_IN_ASSOCATIONS_ID,
                                activityIdMap.get(targetId));
                    }
                }
            }
        }

        /*
         * Check that activities that are targets of compensation associations
         * do not have incoming / outgoing sequence flow.
         */
        for (String compenAssocTargetId : compenAssocTargetIdMap.keySet()) {
            Activity compenAssocTarget = activityIdMap.get(compenAssocTargetId);
            if (compenAssocTarget != null) {
                EList<Transition> incoming =
                        compenAssocTarget.getIncomingTransitions();
                if (incoming != null && !incoming.isEmpty()) {
                    addIssue(CANNOT_HAVE_SEQUENCE_FLOW_ID, compenAssocTarget);
                } else {
                    EList<Transition> outgoing =
                            compenAssocTarget.getOutgoingTransitions();
                    if (outgoing != null && !outgoing.isEmpty()) {

                        addIssue(CANNOT_HAVE_SEQUENCE_FLOW_ID,
                                compenAssocTarget);
                    }
                }
            }
        }

        /*
         * Check that all catch compensation events have outgoing compensation
         * association.
         */
        Set<String> activitiesWithCompensation = new HashSet<String>();
        Set<Activity> activitiesWithMultiCompensation = new HashSet<Activity>();

        for (Activity catchCompensation : catchCompensationEvents.values()) {
            if (!compenAssocSourceIdMap.containsKey(catchCompensation.getId())) {
                /*
                 * This compensation event is not the source of a compensation
                 * association.
                 */
                addIssue(EVENT_MUST_HAVE_ONE_ASSOCIATION_ID, catchCompensation);
            }

            /* Keep track of tasks that have multiple compensations attached. */
            String taskIdAttachedTo =
                    EventObjectUtil.getTaskIdAttachedTo(catchCompensation);
            if (taskIdAttachedTo != null) {
                if (activitiesWithCompensation.contains(taskIdAttachedTo)) {
                    addIssue(TASK_CANNOT_HAVE_MULTI_COMPENSATIONS_ID,
                            activityIdMap.get(taskIdAttachedTo));
                    activitiesWithMultiCompensation.add(activityIdMap
                            .get(taskIdAttachedTo));
                } else {
                    activitiesWithCompensation.add(taskIdAttachedTo);
                }
            }
        }

        for (Activity activity : activitiesWithMultiCompensation) {
            // addIssue(TASK_CANNOT_HAVE_MULTI_COMPENSATIONS_ID, activity);
        }

        /*
         * Check that any task that is explicitly compensated for in a throw
         * compensation event is setup correctly.
         */
        for (Activity activity : allActivitiesInProc) {
            TriggerResultCompensation triggerResultCompensation =
                    getTriggerResultCompensation(activity);
            if (triggerResultCompensation != null) {
                /* It's a throw compensation event. */
                String compensateForActivityId =
                        triggerResultCompensation.getActivityId();
                if (compensateForActivityId != null
                        && compensateForActivityId.length() > 0) {

                    Activity compensateForActivity =
                            activityIdMap.get(compensateForActivityId);
                    if (compensateForActivity != null) {
                        /*
                         * This is an explicitly compensated activity
                         * 
                         * Check it has an attached compensation event.
                         */
                        Collection<Activity> attachedEvents =
                                Xpdl2ModelUtil
                                        .getAttachedEvents(compensateForActivity);
                        boolean hasCatchCompensationEvent = false;
                        for (Activity attachedEvent : attachedEvents) {
                            if (catchCompensationEvents
                                    .containsKey(attachedEvent.getId())) {
                                hasCatchCompensationEvent = true;
                                break;
                            }
                        }

                        if (!hasCatchCompensationEvent) {
                            addIssue(NO_EVENT_ATTACHED,
                                    activity,
                                    Collections
                                            .singletonList(Xpdl2ModelUtil
                                                    .getDisplayNameOrName(compensateForActivity)));
                        }
                    }
                }
            }
        }

        return;
    }

    /**
     * @param activity
     * @return The trigger result compensation element of the activity if it is
     *         a throw compensation event.
     */
    private TriggerResultCompensation getTriggerResultCompensation(
            Activity activity) {
        if (EventTriggerType.EVENT_COMPENSATION_THROW_LITERAL
                .equals(EventObjectUtil.getEventTriggerType(activity))) {
            if (activity.getEvent() instanceof IntermediateEvent) {
                return ((IntermediateEvent) activity.getEvent())
                        .getTriggerResultCompensation();
            } else if (activity.getEvent() instanceof EndEvent) {
                return ((EndEvent) activity.getEvent())
                        .getTriggerResultCompensation();
            }
        }
        return null;
    }

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
        // do nothing
        // override this method to avoid UnsupportedOperationException thrown in
        // the super class
    }

}
