/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * @author nwilson
 */
public class ParticipantSimDataRule extends ProcessValidationRule {

    /** Simulation data issue ID. */
    public static final String PARTICIPANT_WITH_NO_SIM_DATA =
            "sim.noSimDataForParticipant"; //$NON-NLS-1$

    /**
     * @param process The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(
     *      com.tibco.xpd.xpdl2.Process)
     */
    @Override
    public void validate(Process process) {
        Package xpdlPackage = process.getPackage();
        List<?> packageParticipants = xpdlPackage.getParticipants();
        List<?> processParticipants = process.getParticipants();
        for (Object next : packageParticipants) {
            Participant participant = (Participant) next;
            if (!checkSimulationData(participant)) {
                addIssue(PARTICIPANT_WITH_NO_SIM_DATA, participant);
            }
        }
        for (Object next : processParticipants) {
            Participant participant = (Participant) next;
            if (!checkSimulationData(participant)) {
                addIssue(PARTICIPANT_WITH_NO_SIM_DATA, participant);
            }
        }
    }

    /**
     * @param participant The participant to check.
     * @return true if it has simulation data.
     */
    private boolean checkSimulationData(Participant participant) {
        List<?> extendedAttributes = participant.getExtendedAttributes();
        boolean simDataPresent = false;
        for (Object next : extendedAttributes) {
            ExtendedAttribute extAttrib = (ExtendedAttribute) next;
            if (SimulationConstants.SIM_PARTICIPANT_DATA.equals(extAttrib.getName())) {
                simDataPresent = true;
            }
        }
        return simDataPresent;
    }

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) { 
    }

}
