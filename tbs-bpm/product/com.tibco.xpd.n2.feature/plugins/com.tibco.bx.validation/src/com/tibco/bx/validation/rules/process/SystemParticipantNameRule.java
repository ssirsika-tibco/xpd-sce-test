package com.tibco.bx.validation.rules.process;

import java.util.Arrays;

import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.Process;

public class SystemParticipantNameRule extends PackageValidationRule {

    private static final String ISSUE_INVALID_SYSTEM_PARTICIPANT_NAME =
            "bx.invalidSystemParticipantName"; //$NON-NLS-1$

    private static final int SYSTEM_PARTICIPANT_LENGTH = 57; // XPD-774

    private static final String ISSUE_INVALID_SYSTEM_PARTICIPANT_LENGTH =
            "bx.invalidSystemParticipantLength"; //$NON-NLS-1$

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
    }

    /**
     * @param participant
     */
    private void validateParticipant(Participant participant) {
        if (participant.getParticipantType() != null
                && ParticipantType.SYSTEM_LITERAL.equals(participant
                        .getParticipantType().getType())) {
            /*
             * XPD-1299: leading numerics in system participant name complains
             * during daa generation
             */
            if (!NameUtil.isValidName(participant.getName(), false)) {
                addIssue(ISSUE_INVALID_SYSTEM_PARTICIPANT_NAME,
                        participant,
                        Arrays.asList(participant.getName()));
            }
            if (null != participant.getName()
                    && participant.getName().length() > SYSTEM_PARTICIPANT_LENGTH) {
                addIssue(ISSUE_INVALID_SYSTEM_PARTICIPANT_LENGTH,
                        participant,
                        Arrays.asList(participant.getName()));
            }
        }
    }

}
