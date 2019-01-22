/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.script;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;
import com.tibco.xpd.xpdl2.Activity;

/**
 * @author rsomayaj
 * 
 */
public class UnspecifiedScriptInfoProvider extends AbstractScriptInfoProvider {

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getSetScriptGrammarCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.String)
     * 
     * @param editingDomain
     * @param scriptContainer
     * @param scriptGrammar
     * @return
     */
    @Override
    public Command getSetScriptGrammarCommand(EditingDomain editingDomain,
            EObject eObject, String scriptGrammar) {
        String scriptContext = getScriptContext();
        Command toReturn = null;
        if (scriptContext.equals(ProcessScriptContextConstants.SEQUENCE_FLOW)) {
            toReturn =
                    ProcessScriptUtil
                            .getRemoveSequenceFlowScriptGrammarCommand(editingDomain,
                                    eObject);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.SCRIPT_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getRemoveScriptTaskGrammarCommand(editingDomain,
                                    eObject);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.ADHOC_PRECONDITION)) {
            toReturn =
                    ProcessScriptUtil
                            .getRemoveAdhocScriptGrammarCommand(editingDomain,
                                    eObject);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.PERFORMER_DATA_FIELD)) {
            toReturn =
                    ProcessScriptUtil
                            .getRemovePerfomerScriptGrammarCommand(editingDomain,
                                    eObject);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.TIMER_EVENT)) {
            toReturn =
                    ProcessScriptUtil
                            .getRemoveTimerEventScriptGrammarCommand(editingDomain,
                                    eObject);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.RESCHEDULE_TIMER_EVENT)) {
            toReturn =
                    ProcessScriptUtil
                            .getRemoveRescheduleTimerEventScriptGrammarCommand(editingDomain,
                                    eObject);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.OPEN_USER_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getDeleteOpenUserTaskScriptGrammarCommand(editingDomain,
                                    eObject);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.SCHEDULE_USER_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getDeleteScheduleUserTaskScriptGrammarCommand(editingDomain,
                                    eObject);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.RESCHEDULE_USER_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getDeleteRescheduleUserTaskScriptGrammarCommand(editingDomain,
                                    eObject);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.CLOSE_USER_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getDeleteCloseUserTaskScriptGrammarCommand(editingDomain,
                                    eObject);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.SUBMIT_USER_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getDeleteSubmitUserTaskScriptGrammarCommand(editingDomain,
                                    eObject);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.STD_LOOP_EXPR)) {
            if (eObject instanceof Activity) {
                toReturn =
                        ProcessScriptUtil
                                .getRemoveStandardLoopExpressionGrammarCommand(editingDomain,
                                        (Activity) eObject);
            }
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.MI_LOOP_EXPR)) {
            if (eObject instanceof Activity) {
                toReturn =
                        ProcessScriptUtil
                                .getRemoveMILoopExprScriptGrammarCommand(editingDomain,
                                        (Activity) eObject);
            }
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.MI_COMPLEX_EXIT_EXPR)) {
            if (eObject instanceof Activity) {
                toReturn =
                        ProcessScriptUtil
                                .getRemoveMIComplexExitScriptGrammarCommand(editingDomain,
                                        (Activity) eObject);
            }
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.MI_ADDITIONAL_INSTANCES_LOOP_EXPR)) {
            if (eObject instanceof Activity) {
                toReturn =
                        ProcessScriptUtil
                                .getRemoveMIAdditionalInstancesScriptGrammarCommand(editingDomain,
                                        (Activity) eObject);
            }
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.INITIATED_SCRIPT_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getDeleteInitiatedScriptGrammarCommand(editingDomain,
                                    eObject);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.COMPLETED_SCRIPT_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getDeleteCompletedScriptGrammarCommand(editingDomain,
                                    eObject);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.DEADLINE_EXPIRED_SCRIPT_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getDeleteDeadlineExpiredScriptGrammarCommand(editingDomain,
                                    eObject);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.CANCELLED_SCRIPT_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getDeleteCancelledScriptGrammarCommand(editingDomain,
                                    eObject);
        }

        return toReturn;
    }

    /**
     * Contribution local identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     */
    public String getLocalId() {
        return "analyst.editor"; //$NON-NLS-1$
    }
}
