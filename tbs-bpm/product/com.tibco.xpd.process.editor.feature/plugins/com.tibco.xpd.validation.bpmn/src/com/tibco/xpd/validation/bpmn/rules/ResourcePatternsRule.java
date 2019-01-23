/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.ProcessResourcePatterns;
import com.tibco.xpd.xpdExtension.RetainFamiliarActivities;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 1. The same individual (Human type participant) cannot be the sole
 * participant for multiple tasks in Separation of Duties group.
 * 
 * 2. System Participant cannot be used in a Separation of Duties group.
 * 
 * 3. A task in a Separation/Retain Familiar group must have at least one
 * Participant.
 * 
 * 4. A task cannot exist in both Separation/Retain Familiar group at the same
 * time
 * 
 * @author bharge
 * 
 */
public class ResourcePatternsRule extends ProcessValidationRule {

    /** No participant assigned to activity in separation group. */
    private static final String NO_PARTICIPANT = "bpmn.separationNoParticipant"; //$NON-NLS-1$

    private static final String ACTIVITY_EXISTS_IN_BOTH_PATTERNS =
            "bpmn.activityExistsInBothSODAndRetainFam"; //$NON-NLS-1$

    private static final String DELETED_ACTIVITY_IN_SEPARATIONGROUP =
            "bpmn.deletedActivityInSeparationGroup"; //$NON-NLS-1$

    private static final String DELETED_ACTIVITY_IN_RETAINGROUP =
            "bpmn.deletedActivityInRetainGroup"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {
        List<String> people = new ArrayList<String>();
        Map<String, Participant> duplicatePeople =
                new HashMap<String, Participant>();
        List<String> sodActRefIds = new ArrayList<String>();

        SeparationOfDutiesActivities sepOfDutiesActivities = null;
        RetainFamiliarActivities retainFamActivities = null;

        Object other =
                Xpdl2ModelUtil.getOtherElement(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ProcessResourcePatterns());

        if (other instanceof ProcessResourcePatterns) {
            ProcessResourcePatterns processResourcePatterns =
                    (ProcessResourcePatterns) other;

            /* XPD-5563: Take embedded sub-process activities into account. */
            Map<String, Object> activityMap =
                    Xpdl2ModelUtil.createMapById(Xpdl2ModelUtil
                            .getAllActivitiesInProc(process));

            for (Object next : processResourcePatterns
                    .getSeparationOfDutiesActivities()) {
                if (next instanceof SeparationOfDutiesActivities) {
                    sepOfDutiesActivities = (SeparationOfDutiesActivities) next;
                    for (Object nextRef : sepOfDutiesActivities
                            .getActivityRef()) {

                        if (nextRef instanceof ActivityRef) {
                            /**
                             * XPD-1166: begin - 1. get act ref ids for
                             * separation of duties, add it to the list and use
                             * this list to check if the same act ref id exist
                             * in retain familiar
                             */
                            ActivityRef activityRef = (ActivityRef) nextRef;

                            /*
                             * XPD-5563: Take embedded sub-process into account
                             * AND rationalise some code.
                             */
                            Activity activity =
                                    (Activity) activityMap.get(activityRef
                                            .getIdRef());

                            if (activity != null) {
                                sodActRefIds.add(activityRef.getIdRef());
                                // XPD-1166 - end
                                validateParticipant(process,
                                        activity,
                                        sepOfDutiesActivities);
                            } else {
                                /*
                                 * Nominally deleted activites are remvoed from
                                 * task groups automatically, but added rule in
                                 * case.
                                 */
                                List<String> messages = new ArrayList<String>();
                                messages.add(activityRef.getIdRef());
                                messages.add(sepOfDutiesActivities.getName());
                                addIssue(DELETED_ACTIVITY_IN_SEPARATIONGROUP,
                                        sepOfDutiesActivities,
                                        messages);
                            }
                        }
                    }

                    /*
                     * XPD-5563: Remove attempt at checking for same
                     * participants used in multiple tasks in a separation of
                     * duties group.
                     * 
                     * Although this looks sensible to do (a) it wasn't
                     * implemented correctly as it did not account for multiple
                     * partic's on single task and (b) it only bothered with
                     * participants of basicType=HUMAN may not be enough of a
                     * distinction to say "this can only be a single person"
                     * therefore two task could be addressed to "human" partic
                     * but there may be a number of physical poeple that fulfill
                     * that role (depending on runtime destination).
                     * 
                     * So decided just to remove this.
                     */
                }
            }

            for (Object next : processResourcePatterns
                    .getRetainFamiliarActivities()) {
                if (next instanceof RetainFamiliarActivities) {
                    retainFamActivities = (RetainFamiliarActivities) next;
                    for (Object nextRef : retainFamActivities.getActivityRef()) {
                        if (nextRef instanceof ActivityRef) {
                            /**
                             * XPD-1166: for each act ref id in the list of
                             * separation of duties act ref ids, check if that
                             * id exists in retain familiar. if yes raise issue
                             * for that activity complaining it cannot exist in
                             * both patterns at the same time
                             */

                            /*
                             * XPD-5563: Take embedded sub-process into account
                             * AND rationalise some code.
                             */
                            ActivityRef activityRef = (ActivityRef) nextRef;
                            Activity activity =
                                    (Activity) activityMap.get(activityRef
                                            .getIdRef());

                            if (activity != null) {

                                if (sodActRefIds.size() > 0) {
                                    for (String sodActRefId : sodActRefIds) {
                                        if (activityRef.getIdRef()
                                                .equalsIgnoreCase(sodActRefId)) {
                                            /*
                                             * XPD-5563: Take embedded
                                             * sub-process activities into
                                             * account.
                                             */
                                            addIssue(ACTIVITY_EXISTS_IN_BOTH_PATTERNS,
                                                    activity,
                                                    Collections
                                                            .singletonList(WorkingCopyUtil
                                                                    .getText(activity)));
                                        }
                                    }
                                }

                                // XPD-1166 - end
                                validateParticipant(process,
                                        activity,
                                        retainFamActivities);
                            } else {
                                /*
                                 * Nominally deleted activites are remvoed from
                                 * task groups automatically, but added rule in
                                 * case.
                                 */
                                List<String> messages = new ArrayList<String>();
                                messages.add(activityRef.getIdRef());
                                messages.add(retainFamActivities.getName());
                                addIssue(DELETED_ACTIVITY_IN_RETAINGROUP,
                                        retainFamActivities,
                                        messages);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        // Do nothing, we're just validating process...
    }

    /**
     * Valdiate participant.
     * 
     * @param process
     * @param activity
     * @param taskGroup
     */
    private void validateParticipant(Process process, Activity activity,
            Object taskGroup) {
        /*
         * XPD-5563: Some refactoring because parent looks up activity from task
         * group's activityRef and we were keeping set of activities that we
         * never used.
         */
        EList<Performer> performers = activity.getPerformerList();

        /*
         * XPD-5563: Removed checkPerformerTypes() - this raised an issue that
         * didn't exist and in reality the type of participant valid for a given
         * task type is desitnation specific.
         */

        /*
         * XPD-5563: Remove attempt at checking for same participants used in
         * multiple tasks in a separation of duties group.
         * 
         * Although this looks sensible to do (a) it wasn't implemented
         * correctly as it did not account for multiple partic's on single task
         * and (b) it only bothered with participants of basicType=HUMAN may not
         * be enough of a distinction to say "this can only be a single person"
         * therefore two task could be addressed to "human" partic but there may
         * be a number of physical poeple that fulfill that role (depending on
         * runtime destination).
         * 
         * So decided just to remove this.
         */
        if (performers.size() < 1) {
            addIssue(NO_PARTICIPANT, activity);
        }
    }

}
