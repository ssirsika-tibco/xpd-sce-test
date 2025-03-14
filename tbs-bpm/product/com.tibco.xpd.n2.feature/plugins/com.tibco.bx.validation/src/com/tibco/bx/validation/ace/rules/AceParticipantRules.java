/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.bx.validation.ace.rules;

import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdExtension.EmailResource;
import com.tibco.xpd.xpdExtension.JdbcResource;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.RestServiceResource;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * ACE specific participant validation rules....
 * 
 * <li>Participant must be of type External reference, Organisation Model Query
 * or System.</li>
 * 
 * <li>System participants must have a shared resource type selected.</li>
 * 
 * @author aallway
 * @since 25 Apr 2019
 */
public class AceParticipantRules extends PackageValidationRule {

    private static final String ACE_ISSUE_INVALID_PARTICIPANT_TYPE =
            "ace.invalid.participant.type"; //$NON-NLS-1$

    private static final String ACE_ISSUE_SYSTEM_PARTICIPANT_MUST_HAVE_TYPE =
            "ace.system.participant.must.have.type.set"; //$NON-NLS-1$

    /**
     * REST Service participants must have Shared Resource Name set.
     */
    private static final String ACE_ISSUE_REST_PARTICIPANT_MUST_HAVE_RESOURCE_NAME =
            "ace.rest.participant.must.have.resource.name"; //$NON-NLS-1$

    /*
     * Sid ACE-7084 Email and Jdbc participants should be validated to have shared resource name set.
     */
    /**
     * Email participants must have Shared Resource Name set.
     */
    private static final String ACE_ISSUE_EMAIL_PARTICIPANT_MUST_HAVE_RESOURCE_NAME =
            "ace.rest.participant.must.have.resource.name"; //$NON-NLS-1$

    /**
     * Jdbc participants must have Shared Resource Name set.
     */
    private static final String ACE_ISSUE_JDBC_PARTICIPANT_MUST_HAVE_RESOURCE_NAME =
            "ace.rest.participant.must.have.resource.name"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com.tibco.xpd.xpdl2.Package)
     *
     * @param pckg
     */
    @Override
    public void validate(Package pckg) {

        for (Participant participant : pckg.getParticipants()) {
            validateParticipant(participant);
        }

        for (com.tibco.xpd.xpdl2.Process process : pckg.getProcesses()) {
            for (Participant participant : process.getParticipants()) {
                validateParticipant(participant);
            }
        }
    }

    /**
     * Validate the given participant.
     * 
     * @param participant
     */
    private void validateParticipant(Participant participant) {

        ParticipantTypeElem participantType = participant.getParticipantType();

        if (participantType == null) {
            if (participant.getExternalReference() == null) {
                // participant must be system, external ref or org query
                addIssue(ACE_ISSUE_INVALID_PARTICIPANT_TYPE, participant);
            }

        } else {
            ParticipantType type = participantType.getType();

            if (ParticipantType.SYSTEM_LITERAL.equals(type)) {
                // System participant must have type set
                Object psrObject =
                        Xpdl2ModelUtil.getOtherElement(participant,
                        XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ParticipantSharedResource());
                if (psrObject == null) {
                    addIssue(ACE_ISSUE_SYSTEM_PARTICIPANT_MUST_HAVE_TYPE,
                            participant);
                } else if (psrObject instanceof ParticipantSharedResource) {
                    ParticipantSharedResource psr =
                            (ParticipantSharedResource) psrObject;
                    RestServiceResource rsr = psr.getRestService();
                    if (null != rsr) {
                        /*
                         * REST Service participants must have Shared Resource Name set.
                         */
                        if (null == rsr.getResourceName() || rsr.getResourceName().trim().isEmpty()) {
                            addIssue(ACE_ISSUE_REST_PARTICIPANT_MUST_HAVE_RESOURCE_NAME, participant);
                        }
                    }
                    /*
                     * Sid ACE-7084 Email and Jdbc participants should be validated to have shared resource name set.
                     */
                    EmailResource esr = psr.getEmail();
                    if (null != esr) {
                        /*
                         * Email participants must have Shared Resource Name set.
                         */
                        if (null == esr.getInstanceName() || esr.getInstanceName().trim().isEmpty()) {
                            addIssue(ACE_ISSUE_EMAIL_PARTICIPANT_MUST_HAVE_RESOURCE_NAME, participant);
                        }
                    }

                    JdbcResource jsr = psr.getJdbc();
                    if (null != jsr) {
                        /*
                         * Jdbc participants must have an Endpoint Identification value set.
                         */
                        if (null == jsr.getInstanceName() || jsr.getInstanceName().trim().isEmpty()) {
                            addIssue(
                                    ACE_ISSUE_JDBC_PARTICIPANT_MUST_HAVE_RESOURCE_NAME,
                                    participant);
                        }
                    }

                }

            } else if (!ParticipantType.RESOURCE_LITERAL.equals(type)
                    && !ParticipantType.RESOURCE_SET_LITERAL.equals(type)
                    && participant.getExternalReference() == null) {
                // participant must be system, external ref or org query
                addIssue(ACE_ISSUE_INVALID_PARTICIPANT_TYPE, participant);

            }
        }

    }

}
