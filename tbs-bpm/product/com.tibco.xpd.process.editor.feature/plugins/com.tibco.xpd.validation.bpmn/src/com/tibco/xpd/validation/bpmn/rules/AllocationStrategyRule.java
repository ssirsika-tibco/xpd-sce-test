/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EReference;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AllocationStrategyType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class AllocationStrategyRule extends ProcessValidationRule {

    /** The participant is not a role or organisation. */
    private static final String PARTICIPANT_TYPE = "bpmn.allocationInvalidParticipantType"; //$NON-NLS-1$

    /**
     * @param process
     *            The process to validate.
     * @param activities
     *            The activities.
     * @param transitions
     *            The transitions.
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#
     *      validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList,
     *      org.eclipse.emf.common.util.EList)
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        EReference ref = XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_ActivityResourcePatterns();
        for (Activity activity : activities) {
            Object other = Xpdl2ModelUtil.getOtherElement(activity, ref);
            if (other instanceof ActivityResourcePatterns) {
                ActivityResourcePatterns patterns = (ActivityResourcePatterns) other;
                AllocationStrategy strategy = patterns.getAllocationStrategy();
                if (strategy != null && strategy.isSetStrategy()) {
                    AllocationStrategyType type = strategy.getStrategy();
                    if (!AllocationStrategyType.SYSTEM_DETERMINED.equals(type)) {
                        validateParticipants(activity);
                    }
                }
            }
        }
    }

    /**
     * Participants can be roles or organisations.
     * 
     * @param activity
     *            The activity to validate.
     */
    private void validateParticipants(Activity activity) {
        Process process = activity.getProcess();
        List<?> performers = activity.getPerformerList();
        boolean valid = true;
        for (Object next : performers) {
            Performer performer = (Performer) next;
            String performerId = performer.getValue();
            Participant participant = process.getParticipant(performerId);
            ParticipantType type = participant.getParticipantType().getType();
            if (participant != null
                    && !(ParticipantType.ORGANIZATIONAL_UNIT_LITERAL
                            .equals(type) || ParticipantType.ROLE_LITERAL
                            .equals(type))) {
                valid = false;
                break;
            }
        }
        if (!valid) {
            addIssue(PARTICIPANT_TYPE, activity);
        }
    }

}
