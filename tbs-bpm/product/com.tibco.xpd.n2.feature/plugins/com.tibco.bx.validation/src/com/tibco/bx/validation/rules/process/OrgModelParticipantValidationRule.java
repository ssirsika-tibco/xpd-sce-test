/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.bx.validation.rules.process;

import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rule to check the package participant that uses Org Model Query
 * (RQL).
 * 
 * @author njpatel
 * 
 */
public class OrgModelParticipantValidationRule extends PackageValidationRule {

    private static final String ISSUE_ID =
            "bx.queryWillRunOnLatestOrgModel.issue"; //$NON-NLS-1$

    private static final String RQL = "RQL"; //$NON-NLS-1$

    @Override
    public void validate(Package pckg) {

        // Check all Participants
        for (Participant participant : pckg.getParticipants()) {
            checkParticipant(participant);
        }

        /*
         * SID XPD-855: noticed that this rule only chewcks package level
         * participants but should check process level ones in case there are
         * some.
         */
        for (Process process : pckg.getProcesses()) {
            for (Participant participant : process.getParticipants()) {
                checkParticipant(participant);
            }
        }
    }

    private void checkParticipant(Participant participant) {
        ParticipantTypeElem type = participant.getParticipantType();
        if (type != null
                && type.getType() == ParticipantType.RESOURCE_SET_LITERAL) {
            Object pQueryObject =
                    Xpdl2ModelUtil.getOtherElement(type,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ParticipantQuery());
            if (pQueryObject instanceof Expression) {
                Expression expression = (Expression) pQueryObject;
                if (RQL.equals(expression.getScriptGrammar())) {
                    addIssue(ISSUE_ID, participant);
                }
            }
        }
    }
}
