/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.script;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;
import com.tibco.xpd.xpdExtension.AdHocExecutionTypeType;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.LoopStandard;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TestTimeType;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 */
public class PlainTextScriptInfoProvider extends AbstractScriptInfoProvider {

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getDecriptionLabel()
     * 
     * @return
     */
    @Override
    public String getDecriptionLabel() {
        String scriptDesc = ""; //$NON-NLS-1$
        String scriptContext = getScriptContext();

        if (ProcessScriptContextConstants.SCRIPT_TASK.equals(scriptContext)) {
            scriptDesc =
                    Messages.PlainTextScriptInfoProvider_DescribeTaskScript_shortdesc;

        } else if (ProcessScriptContextConstants.PERFORMER_DATA_FIELD
                .equals(scriptContext)) {
            scriptDesc =
                    Messages.PlainTextScriptInfoProvider_DescribeParticipantSelScript_shortdesc;

        } else if (ProcessScriptContextConstants.SEQUENCE_FLOW
                .equals(scriptContext)) {
            scriptDesc =
                    Messages.PlainTextScriptInfoProvider_DescribeSeqFlowCondn_shortdesc;

        } else if (scriptContext
                .equals(ProcessScriptContextConstants.ADHOC_PRECONDITION)) {

            AdHocTaskConfigurationType adhocTaskConfigurationType =
                    getAdhocTaskConfigurationType();
            /*
             * XPD-6861 : The Adhoc Activity Precondition Script label should be
             * specific to the Adhoc Task Execution Type(i.e. Manual or
             * Automatic)
             */
            if (adhocTaskConfigurationType != null
                    && AdHocExecutionTypeType.MANUAL
                            .equals(adhocTaskConfigurationType
                                    .getAdHocExecutionType())) {
                scriptDesc =
                        Messages.PlainTextScriptInfoProvider_ManualAdhocPreconditionScriptEnablament_label;

            } else {

                scriptDesc =
                        Messages.PlainTextScriptInfoProvider_AutomaticAdhocPreconditionScriptEnablament_label;
            }

        } else if (ProcessScriptContextConstants.TIMER_EVENT
                .equals(scriptContext)) {
            scriptDesc =
                    Messages.PlainTextScriptInfoProvider_DescribeTimeoutScrpt_shortdesc;

        } else if (ProcessScriptContextConstants.RESCHEDULE_TIMER_EVENT
                .equals(scriptContext)) {
            scriptDesc =
                    Messages.PlainTextScriptInfoProvider_DescribeRescheduleTimerScript_shortdesc;

        } else if (ProcessScriptContextConstants.OPEN_USER_TASK
                .equals(scriptContext)) {
            scriptDesc =
                    Messages.PlainTextScriptInfoProvider_DescribeInitiatedScrpt_shortdesc;

        } else if (ProcessScriptContextConstants.SCHEDULE_USER_TASK
                .equals(scriptContext)) {
            scriptDesc =
                    Messages.PlainTextScriptInfoProvider_DescScheduledScpt_shortdesc2;
        } else if (ProcessScriptContextConstants.RESCHEDULE_USER_TASK
                .equals(scriptContext)) {
            scriptDesc =
                    Messages.PlainTextScriptInfoProvider_ResceduleScritpheader_shortdesc;
        } else if (ProcessScriptContextConstants.CLOSE_USER_TASK
                .equals(scriptContext)) {
            scriptDesc =
                    Messages.PlainTextScriptInfoProvider_DescUserTaskCloseScpt_shortdesc;

        } else if (ProcessScriptContextConstants.SUBMIT_USER_TASK
                .equals(scriptContext)) {
            scriptDesc =
                    Messages.PlainTextScriptInfoProvider_DescUserTaskSubmit_shortdesc;
        } else if (ProcessScriptContextConstants.MI_LOOP_EXPR
                .equals(scriptContext)) {
            scriptDesc =
                    Messages.PlainTextScriptInfoProvider_DescNumInstances_shortdesc;
        } else if (ProcessScriptContextConstants.MI_COMPLEX_EXIT_EXPR
                .equals(scriptContext)) {
            scriptDesc =
                    Messages.PlainTextScriptInfoProvider_DescComplexExitScript_shortdesc;
        } else if (ProcessScriptContextConstants.MI_ADDITIONAL_INSTANCES_LOOP_EXPR
                .equals(scriptContext)) {
            scriptDesc =
                    Messages.PlainTextScriptInfoProvider_DescAddnlInstances_shortdesc;
        } else if (ProcessScriptContextConstants.INITIATED_SCRIPT_TASK
                .equals(scriptContext)) {
            scriptDesc =
                    Messages.PlainTextScriptInfoProvider_DescInitiatedScpt_shortdesc;
        } else if (ProcessScriptContextConstants.COMPLETED_SCRIPT_TASK
                .equals(scriptContext)) {
            scriptDesc =
                    Messages.PlainTextScriptInfoProvider_DescCompletedScpt_shortdesc;
        } else if (ProcessScriptContextConstants.DEADLINE_EXPIRED_SCRIPT_TASK
                .equals(scriptContext)) {
            scriptDesc =
                    Messages.PlainTextScriptInfoProvider_DescDeadlineExpired_shortdesc;
        } else if (ProcessScriptContextConstants.CANCELLED_SCRIPT_TASK
                .equals(scriptContext)) {
            scriptDesc =
                    Messages.PlainTextScriptInfoProvider_DescCancelledScpt_shortdesc;
        } else if (ProcessScriptContextConstants.QUERY_PARTICIPANT
                .equals(scriptContext)) {
            scriptDesc =
                    Messages.PlainTextScriptInfoProvider_DescOrgModelQuery_shortdesc;
        }
        return scriptDesc;

    }

    /**
     * 
     * @return the {@link AdHocTaskConfigurationType} extension element if the
     *         Input of this provider is an Adhoc Activity else return
     *         <code>null</code>
     */
    private AdHocTaskConfigurationType getAdhocTaskConfigurationType() {

        AdHocTaskConfigurationType adhocConfigType = null;

        EObject input = getInput();

        if (input instanceof Activity) {

            Activity activity = (Activity) input;

            Object adhocConfig =
                    Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AdHocTaskConfiguration());

            if (adhocConfig instanceof AdHocTaskConfigurationType) {

                adhocConfigType = (AdHocTaskConfigurationType) adhocConfig;
            }
        }
        return adhocConfigType;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getDescriptionLabel(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public String getDescriptionLabel(EObject input) {
        String scriptDesc = null;
        String scriptContext = getScriptContext();

        if (ProcessScriptContextConstants.STD_LOOP_EXPR.equals(scriptContext)) {
            /* Default to description for "evaluate before" */
            scriptDesc =
                    Messages.PlainTextScriptInfoProvider_StdLoopBefore_shortdesc;

            if (input instanceof Activity) {
                Loop loop = ((Activity) input).getLoop();

                if (loop != null) {
                    LoopStandard loopStandard = loop.getLoopStandard();

                    if (loopStandard != null) {
                        if (TestTimeType.AFTER_LITERAL.equals(loopStandard
                                .getTestTime())) {
                            scriptDesc =
                                    Messages.PlainTextScriptInfoProvider_StdLoopAfter_shortdesc;
                        }
                    }
                }
            }
        }

        if (scriptDesc == null) {
            scriptDesc = super.getDescriptionLabel(input);
        }

        return scriptDesc != null ? scriptDesc : ""; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getScript(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public String getScript(EObject input) {
        String strScript = ""; //$NON-NLS-1$
        String scriptContext = getScriptContext();
        if (ProcessScriptContextConstants.SCRIPT_TASK.equals(scriptContext)) {
            strScript = ProcessScriptUtil.getScriptTaskScript((Activity) input);
        } else if (ProcessScriptContextConstants.PERFORMER_DATA_FIELD
                .equals(scriptContext)) {
            strScript =
                    ProcessScriptUtil
                            .getScriptPerformerScript((ProcessRelevantData) input);

        } else if (ProcessScriptContextConstants.SEQUENCE_FLOW
                .equals(scriptContext)) {
            strScript = ProcessScriptUtil.getSeqFlowScript((Transition) input);

        } else if (scriptContext
                .equals(ProcessScriptContextConstants.ADHOC_PRECONDITION)) {
            return ProcessScriptUtil
                    .getAdhocTaskPreconditionScript((Activity) getInput());

        } else if (ProcessScriptContextConstants.TIMER_EVENT
                .equals(scriptContext)) {
            strScript = ProcessScriptUtil.getTimerEventScript((Activity) input);

        } else if (ProcessScriptContextConstants.RESCHEDULE_TIMER_EVENT
                .equals(scriptContext)) {
            strScript =
                    ProcessScriptUtil
                            .getRescheduleTimerEventScript((Activity) input);

        } else if (ProcessScriptContextConstants.OPEN_USER_TASK
                .equals(scriptContext)) {
            strScript =
                    ProcessScriptUtil.getUserTaskOpenScript((Activity) input);

        } else if (ProcessScriptContextConstants.SCHEDULE_USER_TASK
                .equals(scriptContext)) {
            strScript =
                    ProcessScriptUtil
                            .getUserTaskScheduleScript((Activity) input);

        } else if (ProcessScriptContextConstants.RESCHEDULE_USER_TASK
                .equals(scriptContext)) {
            strScript =
                    ProcessScriptUtil
                            .getUserTaskRescheduleScript((Activity) input);

        } else if (ProcessScriptContextConstants.CLOSE_USER_TASK
                .equals(scriptContext)) {
            strScript =
                    ProcessScriptUtil.getUserTaskCloseScript((Activity) input);

        } else if (ProcessScriptContextConstants.SUBMIT_USER_TASK
                .equals(scriptContext)) {
            strScript =
                    ProcessScriptUtil.getUserTaskSubmitScript((Activity) input);
        } else if (ProcessScriptContextConstants.STD_LOOP_EXPR
                .equals(scriptContext)) {
            strScript =
                    ProcessScriptUtil
                            .getStdLoopExpressionScript((Activity) input);
        } else if (ProcessScriptContextConstants.MI_LOOP_EXPR
                .equals(scriptContext)) {
            strScript =
                    ProcessScriptUtil
                            .getMILoopExpressionScript((Activity) input);
        } else if (ProcessScriptContextConstants.MI_COMPLEX_EXIT_EXPR
                .equals(scriptContext)) {
            strScript =
                    ProcessScriptUtil
                            .getMIComplexExitExpressionScript((Activity) input);
        } else if (ProcessScriptContextConstants.MI_ADDITIONAL_INSTANCES_LOOP_EXPR
                .equals(scriptContext)) {
            strScript =
                    ProcessScriptUtil
                            .getMIAdditionalLoopExpressionScript((Activity) input);
        } else if (ProcessScriptContextConstants.INITIATED_SCRIPT_TASK
                .equals(scriptContext)) {
            strScript = ProcessScriptUtil.getInitiatedScript((Activity) input);
        } else if (ProcessScriptContextConstants.COMPLETED_SCRIPT_TASK
                .equals(scriptContext)) {
            strScript = ProcessScriptUtil.getCompletedScript((Activity) input);
        } else if (ProcessScriptContextConstants.DEADLINE_EXPIRED_SCRIPT_TASK
                .equals(scriptContext)) {
            strScript =
                    ProcessScriptUtil
                            .getDeadlineExpiredScript((Activity) input);
        } else if (ProcessScriptContextConstants.CANCELLED_SCRIPT_TASK
                .equals(scriptContext)) {
            strScript = ProcessScriptUtil.getCancelledScript((Activity) input);
        } else if (ProcessScriptContextConstants.QUERY_PARTICIPANT
                .equals(scriptContext)) {
            strScript = ProcessScriptUtil.getQueryParticipantScript(input);
        }
        return strScript;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getSetScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.String, java.lang.String)
     * 
     * @param ed
     * @param input
     * @param script
     * @param grammar
     * @return
     */
    @Override
    public Command getSetScriptCommand(EditingDomain editingDomain,
            EObject eObject, String scriptText, String grammar) {
        Command cmd = null;
        String oldScript = getScript(eObject);
        if (scriptText != null && !scriptText.equals(oldScript)) {
            String scriptContext = getScriptContext();
            if (ProcessScriptContextConstants.SCRIPT_TASK.equals(scriptContext)) {
                cmd =
                        ProcessScriptUtil
                                .getSetScriptTaskScriptCommand(editingDomain,
                                        scriptText,
                                        (Activity) getInput(),
                                        getScriptGrammar());

            } else if (ProcessScriptContextConstants.PERFORMER_DATA_FIELD
                    .equals(scriptContext)) {
                cmd =
                        ProcessScriptUtil
                                .getSetScriptPerfomerScriptCommand(editingDomain,
                                        scriptText,
                                        (ProcessRelevantData) getInput(),
                                        getScriptGrammar());
            } else if (ProcessScriptContextConstants.SEQUENCE_FLOW
                    .equals(scriptContext)) {
                cmd =
                        ProcessScriptUtil
                                .getSetSequenceFlowScriptCommand(editingDomain,
                                        scriptText,
                                        (Transition) getInput(),
                                        getScriptGrammar());

            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.ADHOC_PRECONDITION)) {
                return ProcessScriptUtil
                        .getSetAdhocScriptCommand(editingDomain,
                                scriptText,
                                (Activity) eObject,
                                getScriptGrammar());

            } else if (ProcessScriptContextConstants.TIMER_EVENT
                    .equals(scriptContext)) {
                cmd =
                        ProcessScriptUtil
                                .getSetTimerEventScriptCommand(editingDomain,
                                        scriptText,
                                        (Activity) getInput(),
                                        getScriptGrammar());

            } else if (ProcessScriptContextConstants.RESCHEDULE_TIMER_EVENT
                    .equals(scriptContext)) {
                cmd =
                        ProcessScriptUtil
                                .getSetRescheduleTimerEventScriptCommand(editingDomain,
                                        scriptText,
                                        (Activity) getInput(),
                                        getScriptGrammar());

            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.OPEN_USER_TASK)) {
                cmd =
                        ProcessScriptUtil
                                .getSetUserTaskOpenScriptCommand(editingDomain,
                                        scriptText,
                                        getInput(),
                                        getScriptGrammar());

            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.SCHEDULE_USER_TASK)) {
                cmd =
                        ProcessScriptUtil
                                .getSetUserTaskScheduleScriptCommand(editingDomain,
                                        scriptText,
                                        getInput(),
                                        getScriptGrammar());

            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.RESCHEDULE_USER_TASK)) {
                cmd =
                        ProcessScriptUtil
                                .getSetUserTaskRescheduleScriptCommand(editingDomain,
                                        scriptText,
                                        getInput(),
                                        getScriptGrammar());

            } else if (ProcessScriptContextConstants.CLOSE_USER_TASK
                    .equals(scriptContext)) {
                cmd =
                        ProcessScriptUtil
                                .getSetUserTaskCloseScriptCommand(editingDomain,
                                        scriptText,
                                        getInput(),
                                        getScriptGrammar());

            } else if (ProcessScriptContextConstants.SUBMIT_USER_TASK
                    .equals(scriptContext)) {
                cmd =
                        ProcessScriptUtil
                                .getSetUserTaskSubmitScriptCommand(editingDomain,
                                        scriptText,
                                        getInput(),
                                        getScriptGrammar());

            } else if (ProcessScriptContextConstants.STD_LOOP_EXPR
                    .equals(scriptContext)) {
                return ProcessScriptUtil
                        .getSetStdLoopExpressionScriptCommand(editingDomain,
                                scriptText,
                                (Activity) getInput(),
                                getScriptGrammar());
            } else if (ProcessScriptContextConstants.MI_LOOP_EXPR
                    .equals(scriptContext)) {
                return ProcessScriptUtil
                        .getSetMILoopExprScriptCommand(editingDomain,
                                scriptText,
                                (Activity) getInput(),
                                getScriptGrammar());
            } else if (ProcessScriptContextConstants.MI_COMPLEX_EXIT_EXPR
                    .equals(scriptContext)) {
                return ProcessScriptUtil
                        .getSetMIComplexExitExprScriptCommand(editingDomain,
                                scriptText,
                                (Activity) getInput(),
                                getScriptGrammar());
            } else if (ProcessScriptContextConstants.MI_ADDITIONAL_INSTANCES_LOOP_EXPR
                    .equals(scriptContext)) {
                return ProcessScriptUtil
                        .getSetMIAdditionalInstancesExprScriptCommand(editingDomain,
                                scriptText,
                                (Activity) getInput(),
                                getScriptGrammar());
            } else if (ProcessScriptContextConstants.INITIATED_SCRIPT_TASK
                    .equals(scriptContext)) {
                cmd =
                        ProcessScriptUtil
                                .getSetInitiatedScriptCommand(editingDomain,
                                        scriptText,
                                        getInput(),
                                        getScriptGrammar());
            } else if (ProcessScriptContextConstants.COMPLETED_SCRIPT_TASK
                    .equals(scriptContext)) {
                cmd =
                        ProcessScriptUtil
                                .getSetCompletedScriptCommand(editingDomain,
                                        scriptText,
                                        getInput(),
                                        getScriptGrammar());
            } else if (ProcessScriptContextConstants.DEADLINE_EXPIRED_SCRIPT_TASK
                    .equals(scriptContext)) {
                cmd =
                        ProcessScriptUtil
                                .getSetDeadlineExpiredScriptCommand(editingDomain,
                                        scriptText,
                                        getInput(),
                                        getScriptGrammar());
            } else if (ProcessScriptContextConstants.CANCELLED_SCRIPT_TASK
                    .equals(scriptContext)) {
                cmd =
                        ProcessScriptUtil
                                .getSetCancelledScriptCommand(editingDomain,
                                        scriptText,
                                        getInput(),
                                        getScriptGrammar());
            } else if (ProcessScriptContextConstants.QUERY_PARTICIPANT
                    .equals(scriptContext)) {
                cmd =
                        ProcessScriptUtil
                                .getSetParticipantQueryScriptCommand(editingDomain,
                                        scriptText,
                                        getScriptGrammar(),
                                        getInput(),
                                        Messages.PlainTextScriptInfoProvider_SetParticipantQuery_shortdesc);
            }
        }
        return cmd;
    }

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
        if (ProcessScriptContextConstants.SEQUENCE_FLOW.equals(scriptContext)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetSequenceFlowScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    eObject,

                                    true);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.ADHOC_PRECONDITION)) {

            toReturn =
                    ProcessScriptUtil.getSetAdhocGrammarCommand(editingDomain,
                            scriptGrammar,
                            eObject,
                            true);

        } else if (ProcessScriptContextConstants.SCRIPT_TASK
                .equals(scriptContext)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetScriptTaskGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    eObject,

                                    true,
                                    scriptContext);
        } else if (ProcessScriptContextConstants.PERFORMER_DATA_FIELD
                .equals(scriptContext)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetPerformerScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    eObject,

                                    true);
        } else if (ProcessScriptContextConstants.TIMER_EVENT
                .equals(scriptContext)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetTimerEventScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    eObject,

                                    true);
        } else if (ProcessScriptContextConstants.RESCHEDULE_TIMER_EVENT
                .equals(scriptContext)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetRescheduleTimerEventScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    eObject,

                                    true,
                                    scriptContext);
        } else if (ProcessScriptContextConstants.OPEN_USER_TASK
                .equals(scriptContext)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetOpenUserTaskScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    eObject,

                                    true,
                                    scriptContext);
        } else if (ProcessScriptContextConstants.SCHEDULE_USER_TASK
                .equals(scriptContext)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetScheduleUserTaskScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    eObject,
                                    true,
                                    scriptContext);
        } else if (ProcessScriptContextConstants.RESCHEDULE_USER_TASK
                .equals(scriptContext)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetRescheduleUserTaskScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    eObject,
                                    true,
                                    scriptContext);
        } else if (ProcessScriptContextConstants.CLOSE_USER_TASK
                .equals(scriptContext)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetCloseUserTaskScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    eObject,

                                    true,
                                    scriptContext);
        } else if (ProcessScriptContextConstants.SUBMIT_USER_TASK
                .equals(scriptContext)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetSubmitUserTaskScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    eObject,

                                    true,
                                    scriptContext);
        } else if (ProcessScriptContextConstants.STD_LOOP_EXPR
                .equals(scriptContext)) {
            if (eObject instanceof Activity) {
                toReturn =
                        ProcessScriptUtil
                                .getSetStandardLoopExpressionGrammarCommand(editingDomain,
                                        scriptGrammar,
                                        (Activity) eObject,

                                        true);
            }
        } else if (ProcessScriptContextConstants.MI_LOOP_EXPR
                .equals(scriptContext)) {
            if (eObject instanceof Activity) {
                toReturn =
                        ProcessScriptUtil
                                .getSetMILoopExprScriptGrammarCommand(editingDomain,
                                        scriptGrammar,
                                        (Activity) eObject,

                                        true);
            }
        } else if (ProcessScriptContextConstants.MI_COMPLEX_EXIT_EXPR
                .equals(scriptContext)) {
            if (eObject instanceof Activity) {
                toReturn =
                        ProcessScriptUtil
                                .getSetMIComplexExitScriptGrammarCommand(editingDomain,
                                        scriptGrammar,
                                        (Activity) eObject,

                                        true);
            }
        } else if (ProcessScriptContextConstants.MI_ADDITIONAL_INSTANCES_LOOP_EXPR
                .equals(scriptContext)) {
            if (eObject instanceof Activity) {
                toReturn =
                        ProcessScriptUtil
                                .getSetMIAdditionalInstancesScriptGrammarCommand(editingDomain,
                                        scriptGrammar,
                                        (Activity) eObject,

                                        true);
            }
        } else if (ProcessScriptContextConstants.INITIATED_SCRIPT_TASK
                .equals(scriptContext)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetInitiatedScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    eObject,

                                    true,
                                    scriptContext);
        } else if (ProcessScriptContextConstants.COMPLETED_SCRIPT_TASK
                .equals(scriptContext)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetCompletedScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    eObject,

                                    true,
                                    scriptContext);
        } else if (ProcessScriptContextConstants.DEADLINE_EXPIRED_SCRIPT_TASK
                .equals(scriptContext)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetDeadlineExpiredScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    eObject,

                                    true,
                                    scriptContext);
        } else if (ProcessScriptContextConstants.CANCELLED_SCRIPT_TASK
                .equals(scriptContext)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetCancelledScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    eObject,

                                    true,
                                    scriptContext);
        } else if (ProcessScriptContextConstants.QUERY_PARTICIPANT
                .equals(scriptContext)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetQueryParticipantScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    eObject);
        }
        return toReturn;
    }
}
