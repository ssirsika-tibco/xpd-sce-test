/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2ParticipantReferenceResolver;

/**
 * Rule to identify unused participants.
 * 
 * @author rsawant
 * @since 30-Nov-2012
 */
public class UnusedParticipantValidationRule extends PackageValidationRule {

    private static final String ISSUE_ID = "bpmn.unusedParticipant.warning"; //$NON-NLS-1$

    /**
     * @see com.tibco.bx.validation.rules.process.PackageParticipantValidationRule#validate(com.tibco.xpd.xpdl2.Package)
     * 
     * @param pckg
     */
    @Override
    public void validate(Package pckg) {
        for (Participant participant : pckg.getParticipants()) {
            validateParticipant(participant);
        }

        for (Process process : pckg.getProcesses()) {
            for (Participant participant : process.getParticipants()) {
                validateParticipant(participant);
            }
        }

        return;
    }

    /**
     * @param participant
     */
    private void validateParticipant(Participant participant) {
        Set<EObject> referencingObjects =
                Xpdl2ParticipantReferenceResolver
                        .getReferencingObjects(participant);

        if (referencingObjects.isEmpty()) {
            List<String> tempList = new ArrayList<String>();
            tempList.add(participant.getName());
            addIssue(ISSUE_ID, participant, tempList);
        }
    }
}
