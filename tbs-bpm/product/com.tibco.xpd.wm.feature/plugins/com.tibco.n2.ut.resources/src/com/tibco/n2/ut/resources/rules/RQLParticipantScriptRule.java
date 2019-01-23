/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.n2.ut.resources.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.rql.parser.util.RQLParserUtil;
import com.tibco.xpd.rql.validation.tools.RQLParticipantScriptTool;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.Process;

/**
 * 
 * 
 * <p>
 * <i>Created: 07 February 2008</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class RQLParticipantScriptRule extends PackageValidationRule {
    /** The issue id. */
    private static final String ERROR_ID = "rql.error.validateScript"; //$NON-NLS-1$

    private static final String WARNING_ID = "rql.warning.validateScript"; //$NON-NLS-1$

    @Override
    public void validate(Package pckg) {
        if (pckg != null) {
            List<EObject> packageParticipants =
                    ProcessDataUtil.getParticipants(pckg);
            for (EObject participant : packageParticipants) {
                validateParticipantScript(participant);
            }
            EList<Process> processes = pckg.getProcesses();
            for (Process process : processes) {
                List<EObject> processParticipants =
                        ProcessDataUtil.getParticipants(process);
                for (EObject participant : processParticipants) {
                    validateParticipantScript(participant);
                }
            }
        }
    }

    protected void validateParticipantScript(EObject eObject) {
        if (eObject instanceof Participant) {
            Participant participant = (Participant) eObject;
            if (participant.getParticipantType() != null
                    && participant.getParticipantType().getType()
                            .equals(ParticipantType.RESOURCE_SET_LITERAL)) {
                IValidationScope validationScope = getScope();
                RQLParticipantScriptTool tool =
                        validationScope.getTool(RQLParticipantScriptTool.class,
                                participant);
                if (tool != null) {
                    List<ErrorMessage> errorList =
                            tool.getErrorList(participant.getId(),
                                    validationScope.getCurrentDestination());
                    if (errorList == null) {
                        return;
                    }
                    reportError(participant, errorList);
                    List<ErrorMessage> warningList =
                            tool.getWarningList(participant.getId(),
                                    validationScope.getCurrentDestination());
                    if (warningList == null) {
                        return;
                    }
                    reportWarning(participant, warningList);
                }
            }
        } else if (eObject instanceof DataField) {
            DataField df = (DataField) eObject;
            if (df.getDataType() instanceof BasicType
                    && ((BasicType) df.getDataType()).getType() != null
                    && ((BasicType) df.getDataType()).getType()
                            .equals(BasicTypeType.PERFORMER_LITERAL)) {
                IValidationScope validationScope = getScope();
                RQLParticipantScriptTool tool =
                        validationScope.getTool(RQLParticipantScriptTool.class,
                                df);
                if (tool != null) {
                    List<ErrorMessage> errorList =
                            tool.getErrorList(df.getId(), validationScope
                                    .getCurrentDestination());
                    if (errorList == null) {
                        return;
                    }
                    reportError(df, errorList);
                    List<ErrorMessage> warningList =
                            tool.getWarningList(df.getId(), validationScope
                                    .getCurrentDestination());
                    if (warningList == null) {
                        return;
                    }
                    reportWarning(df, warningList);
                }
            }
        }
    }

    protected Map<String, String> getAdditionalInfoMap(ErrorMessage errorMessage) {
        Map<String, String> additionalInfoMap = new HashMap<String, String>();
        additionalInfoMap.put("LineNumber", Integer.toString(errorMessage //$NON-NLS-1$
                .getLineNumber()));
        additionalInfoMap.put("ColumnNumber", Integer.toString(errorMessage //$NON-NLS-1$
                .getColumnNumber()));
        additionalInfoMap.put("ErrorMessage", errorMessage.getErrorMessage()); //$NON-NLS-1$
        additionalInfoMap.put("ScriptContext", getScriptContext()); //$NON-NLS-1$
        return additionalInfoMap;
    }

    protected void reportError(EObject eObject, List<ErrorMessage> errorMsgList) {
        if (eObject != null) {
            reportIssue(eObject, errorMsgList, ERROR_ID);
        }
    }

    protected void reportWarning(EObject eObject,
            List<ErrorMessage> errorMsgList) {
        if (eObject != null) {
            reportIssue(eObject, errorMsgList, WARNING_ID);
        }
    }

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    protected void reportIssue(EObject eObject,
            List<ErrorMessage> issueMsgList, String issueId) {
        for (ErrorMessage errorMessage : issueMsgList) {
            List<String> tempMsgList = getSubstitutionList(errorMessage);
            if (tempMsgList == null) {
                tempMsgList = Collections.EMPTY_LIST;
            }
            Map<String, String> additionalInfoMap =
                    getAdditionalInfoMap(errorMessage);
            addIssue(issueId, eObject, tempMsgList, additionalInfoMap);
        }
    }


    protected List<String> getSubstitutionList(ErrorMessage errorMessage) {
        List<String> tempMsgList = new ArrayList<String>();
        tempMsgList.add(Integer.toString(errorMessage.getLineNumber()));
        tempMsgList.add(Integer.toString(errorMessage.getColumnNumber()));
        tempMsgList.add(errorMessage.getErrorMessage());
        return tempMsgList;
    }

    protected String getScriptGrammar() {
        return RQLParserUtil.RQL_GRAMMAR;
    }

    protected String getScriptContext() {
        return ProcessScriptContextConstants.QUERY_PARTICIPANT;
    }

}
