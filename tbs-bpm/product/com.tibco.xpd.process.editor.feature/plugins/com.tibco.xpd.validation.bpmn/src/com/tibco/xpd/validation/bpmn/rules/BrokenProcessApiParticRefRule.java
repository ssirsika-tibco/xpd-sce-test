/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.Collection;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Checks that any process that has incoming message activity that generates a
 * WSDL that references a valid participant must have a
 * xpdExt:ApiEndPointParticipant attribute set on xpdl2:WorkflowProcess model
 * element if not then the following validation problem is raised...
 * <p>
 * BPMN 1.2: The internal reference to process-as-service participant is broken.
 * 
 * @author aallway
 * @since 24 Jun 2013
 */
public class BrokenProcessApiParticRefRule extends ProcessValidationRule {

    private static final String ISSUE_BROKEN_PROCESS_API_PARTIC_REF =
            "bpmn.brokenProcessApiParticReference"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {

        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        /*
         * Only bother even checking broken api participant reference if this is
         * a process, with an incoming message activity, that has a valid
         * participant - if this isn't true then it's either not required or the
         * user has api activities with no aprticipant and therefore has bigger
         * fish to fry!
         */
        Participant generateWsdlActivityParticipant = null;

        for (Activity activity : allActivitiesInProc) {
            if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                generateWsdlActivityParticipant =
                        Xpdl2ModelUtil.getEndPointAliasParticipant(activity);

                if (generateWsdlActivityParticipant != null) {
                    break;
                }
            }
        }

        if (generateWsdlActivityParticipant != null) {
            /*
             * There is a participant we _could/should_ assign broken reference
             * to xpdExt:ApiEndPointParticipant - sp of the reference is broken
             * then we should complain.
             */
            Participant processApiActivityParticipant =
                    Xpdl2ModelUtil.getProcessApiActivityParticipant(process);

            if (processApiActivityParticipant == null) {
                addIssue(ISSUE_BROKEN_PROCESS_API_PARTIC_REF, process);
            }
        }

    }

}
