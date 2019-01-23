/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.PilingInfo;
import com.tibco.xpd.xpdExtension.ProcessResourcePatterns;
import com.tibco.xpd.xpdExtension.RetainFamiliarActivities;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class ChainedExecutionRule extends ProcessValidationRule {

    /** Chained tasks must all use the same role. */
    private static final String HUMAN_PARTICIPANT = "bpmn.chainedTaskHuman"; //$NON-NLS-1$

    /** Chained tasks must use Allocate Directly. */
    //private static final String OFFER_FIRST = "bpmn.chainedTaskHasOfferFirst"; //$NON-NLS-1$
    /** Chained tasks cannot be in a separation group. */
    private static final String CHAINED_SEPARATION =
            "bpmn.chainedTaskInSeparationGroup"; //$NON-NLS-1$

    private static final String PILING_IGNORED_RULE =
            "bpmn.pilingIgnoredIfInChain"; //$NON-NLS-1$

    private static final String CHAINED_RETAINFAMILIAR =
            "bpmn.chainedTaskInRetainFamiliarGroup"; //$NON-NLS-1$

    /**
     * @param process
     *            Th process.
     * @param activities
     *            The list of activities in the process.
     * @param transitions
     *            The list of transitions.
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#
     *      validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList,
     *      org.eclipse.emf.common.util.EList)
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        Set<String> separations = getAllSeparatedActivityIds(process);
        Set<String> retainFamiliarActivities =
                getAllRetainFamiliarActivityIds(process);
        for (Activity activity : activities) {
            BlockActivity block = activity.getBlockActivity();
            if (block != null) {
                Object other =
                        Xpdl2ModelUtil.getOtherAttribute(activity,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_IsChained());
                if (other instanceof Boolean
                        && ((Boolean) other).booleanValue()) {
                    ActivitySet set =
                            process.getActivitySet(block.getActivitySetId());
                    if (set != null) {
                        validateChainedActivitySet(activity,
                                set,
                                separations,
                                retainFamiliarActivities);
                    }
                }
            }
        }
    }

    /**
     * @param process
     * @return
     */
    private Set<String> getAllRetainFamiliarActivityIds(Process process) {
        Set<String> ids = new HashSet<String>();
        Object other =
                Xpdl2ModelUtil.getOtherElement(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ProcessResourcePatterns());
        if (other instanceof ProcessResourcePatterns) {
            ProcessResourcePatterns patterns = (ProcessResourcePatterns) other;
            for (Object next : patterns.getRetainFamiliarActivities()) {
                if (next instanceof RetainFamiliarActivities) {
                    RetainFamiliarActivities retainFamiliarActivities =
                            (RetainFamiliarActivities) next;
                    for (Object nextRef : retainFamiliarActivities
                            .getActivityRef()) {
                        if (nextRef instanceof ActivityRef) {
                            ActivityRef ref = (ActivityRef) nextRef;
                            String id = ref.getIdRef();
                            if (id != null) {
                                ids.add(id);
                            }
                        }
                    }
                }
            }
        }
        return ids;
    }

    /**
     * @param process
     *            The process to get separated activities for.
     * @return a list of all ids of activities in any separation group.
     */
    private Set<String> getAllSeparatedActivityIds(Process process) {
        Set<String> ids = new HashSet<String>();
        Object other =
                Xpdl2ModelUtil.getOtherElement(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ProcessResourcePatterns());
        if (other instanceof ProcessResourcePatterns) {
            ProcessResourcePatterns patterns = (ProcessResourcePatterns) other;
            for (Object next : patterns.getSeparationOfDutiesActivities()) {
                if (next instanceof SeparationOfDutiesActivities) {
                    SeparationOfDutiesActivities separation =
                            (SeparationOfDutiesActivities) next;
                    for (Object nextRef : separation.getActivityRef()) {
                        if (nextRef instanceof ActivityRef) {
                            ActivityRef ref = (ActivityRef) nextRef;
                            String id = ref.getIdRef();
                            if (id != null) {
                                ids.add(id);
                            }
                        }
                    }
                }
            }

        }
        return ids;
    }

    /**
     * @param subproc
     *            The embedded sub-process activity containing the activity set.
     * @param set
     *            The activity set to validate.
     * @param separations
     *            Set of all separated activity ids.
     * @param retainFamiliarActivities
     */
    private void validateChainedActivitySet(Activity subproc, ActivitySet set,
            Set<String> separations, Set<String> retainFamiliarActivities) {
        boolean differentHuman = false;
        boolean hasNonHumans = false;
        String human = null;
        Process process = subproc.getProcess();
        for (Object next : set.getActivities()) {
            if (next instanceof Activity) {
                Activity activity = (Activity) next;
                if (!differentHuman) {
                    List<?> performers = activity.getPerformerList();
                    for (Object nextPerformer : performers) {
                        if (nextPerformer instanceof Performer) {
                            Performer performer = (Performer) nextPerformer;
                            String performerId = performer.getValue();
                            Participant participant =
                                    process.getParticipant(performerId);
                            if (participant != null) {
                                ParticipantTypeElem participantType =
                                        participant.getParticipantType();
                                if (participantType != null
                                        && ParticipantType.HUMAN_LITERAL
                                                .equals(participantType
                                                        .getType())) {
                                    if (human == null) {
                                        human = performerId;
                                    } else if (!human.equals(performerId)) {
                                        differentHuman = true;
                                    }
                                } else {
                                    hasNonHumans = true;
                                }
                            }
                        }
                    }
                }
                validateChainedActivity(activity,
                        separations,
                        retainFamiliarActivities);
            }
        }
        if (differentHuman || (human != null && hasNonHumans)) {
            addIssue(getHumanParticpantIssueId(), subproc);
        }
    }

    /**
     * @param activity
     *            The activity to validate.
     * @param separations
     *            Set of all separated activity ids.
     * @param retainFamiliarActivities
     */
    private void validateChainedActivity(Activity activity,
            Set<String> separations, Set<String> retainFamiliarActivities) {
        // 
        // MR 37870 - Do not complain that tasks in chained execution embedded
        // sub-proc are set of Offer To All - Resource Pattern doesn't imply
        // this should be the case and worse still, BRM thinks the exact
        // opposite - so for now just disable it.
        //

        // Object other =
        // Xpdl2ModelUtil.getOtherElement(activity,
        // XpdExtensionPackage.eINSTANCE
        // .getDocumentRoot_ActivityResourcePatterns());
        // if (other instanceof ActivityResourcePatterns) {
        // ActivityResourcePatterns patterns =
        // (ActivityResourcePatterns) other;
        // AllocationStrategy strategy = patterns.getAllocationStrategy();
        // if (strategy != null && strategy.isSetOffer() && strategy.isOffer())
        // {
        // addIssue(OFFER_FIRST, activity);
        // }
        // }

        String id = activity.getId();
        if (separations.contains(id)) {
            addIssue(getChainedSeparationIssueId(), activity);
        }

        if (retainFamiliarActivities.contains(id)) {
            addIssue(getChainedRetainFamiliarIssueId(), activity);
        }
        // Check if piling is enabled as it should not be allowed in a chained
        // embedded subproc
        Object other =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ActivityResourcePatterns());
        if (other instanceof ActivityResourcePatterns) {
            ActivityResourcePatterns patterns =
                    (ActivityResourcePatterns) other;
            PilingInfo piling = patterns.getPiling();
            if (piling != null && piling.isPilingAllowed()) {
                addIssue(PILING_IGNORED_RULE, activity);
            }
        }
    }

    /**
     * Returns issueid for problems associated with chained tasks
     * 
     * @return
     */
    protected String getHumanParticpantIssueId() {
        return ChainedExecutionRule.HUMAN_PARTICIPANT;
    }

    /**
     * Returns issueid when chained task are part of separation group
     * 
     * @return
     */
    protected String getChainedSeparationIssueId() {
        return ChainedExecutionRule.CHAINED_SEPARATION;
    }

    protected String getChainedRetainFamiliarIssueId() {
        return ChainedExecutionRule.CHAINED_RETAINFAMILIAR;
    }
}
