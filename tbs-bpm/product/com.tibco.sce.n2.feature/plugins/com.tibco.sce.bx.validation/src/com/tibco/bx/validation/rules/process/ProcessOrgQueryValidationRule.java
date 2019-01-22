/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.bx.validation.rules.process;

import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rule to check the process data fields of Performer type and
 * Participants have specified RQL for the initial value.
 * 
 * @author njpatel
 * 
 */
public class ProcessOrgQueryValidationRule extends ProcessValidationRule {

    private static final String ISSUE_ID =
            "bx.queryWillRunOnLatestOrgModel.issue"; //$NON-NLS-1$

    private static final String RQL = "RQL"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com
     * .tibco.xpd.xpdl2.Process)
     */
    public void validate(Process process) {

        // Check all process relevant data
        List<ProcessRelevantData> allData =
                ProcessInterfaceUtil.getAllProcessRelevantData(process);
        for (ProcessRelevantData data : allData) {
            DataType type = data.getDataType();
            if (type instanceof BasicType
                    && ((BasicType) type).getType() == BasicTypeType.PERFORMER_LITERAL) {

                Object pQueryObject =
                        Xpdl2ModelUtil.getOtherElement((BasicType) type,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ParticipantQuery());
                if (pQueryObject instanceof Expression) {
                    Expression expression = (Expression) pQueryObject;
                    if (RQL.equals(expression.getScriptGrammar())) {
                        addIssue(ISSUE_ID, data);
                    }
                }
            }
        }

        // Check all Participants
        for (Participant participant : process.getParticipants()) {
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

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        // Do nothing
        return;
    }
}
