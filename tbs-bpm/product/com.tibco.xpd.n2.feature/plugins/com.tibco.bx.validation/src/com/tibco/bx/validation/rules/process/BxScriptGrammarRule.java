/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.parser.util.ScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.studioiprocess.scriptconverter.AbstractIProcessToJavaScriptConverter;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.AuditEvent;
import com.tibco.xpd.xpdExtension.AuditEventType;
import com.tibco.xpd.xpdExtension.EnablementType;
import com.tibco.xpd.xpdExtension.MultiInstanceScripts;
import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.LoopMultiInstance;
import com.tibco.xpd.xpdl2.LoopStandard;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * BxScriptGrammarRule - validates user task scripts, script task, task audit
 * scripts, re-usable sub-proc scripts free text grammar type not supported. and
 * validates javascript script is defined. validates if a conditional transition
 * has javascript defined
 * 
 * 
 * @author bharge
 * @since 3.3 (28 Jun 2010)
 */
public class BxScriptGrammarRule extends ProcessValidationRule {

    private static final String UNSPECIFIED_SCRIPTGRAMMAR = "Unspecified"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#
     * validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     * org.eclipse.emf.common.util.EList, org.eclipse.emf.common.util.EList)
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Transition transition : transitions) {
            validate(transition);
        }

        for (Activity activity : activities) {
            validate(activity);
        }
    }

    /**
     * @param transition
     */
    private void validate(Transition transition) {
        if (hasIProcessScripts(transition)) {
            addIssue(ScriptGrammarErrors.IPROCESS_SCRIPT_GRAMMAR_FLOW.getValue(),
                    transition);
            /* Don't bother with other kinds of error until this is sorted out. */
            return;
        }

        Condition condition = transition.getCondition();
        if (condition != null) {
            if (ConditionType.CONDITION_LITERAL.equals(condition.getType())) {
                if (condition.getExpression() == null) {
                    addIssue(ScriptGrammarErrors.CONDITIONAL_FLOW_ISSUE_ID.getValue(),
                            transition);
                } else {
                    String scriptGrammar =
                            condition.getExpression().getScriptGrammar();

                    if (scriptGrammar == null
                            || ProcessJsConsts.FREE_TEXT_GRAMMAR
                                    .equalsIgnoreCase(scriptGrammar)
                            || BxScriptGrammarRule.UNSPECIFIED_SCRIPTGRAMMAR
                                    .equalsIgnoreCase(scriptGrammar)) {
                        addIssue(ScriptGrammarErrors.CONDITIONAL_FLOW_ISSUE_ID.getValue(),
                                transition);
                    } else if (ProcessJsConsts.JAVASCRIPT_GRAMMAR
                            .equalsIgnoreCase(scriptGrammar)) {
                        String scriptText = condition.getExpression().getText();
                        boolean emptyScript =
                                ScriptParserUtil.isEmptyScript(scriptText,
                                        ProcessJsConsts.SEQUENCE_FLOW,
                                        scriptGrammar);
                        if (emptyScript) {
                            addIssue(ScriptGrammarErrors.CONDITIONAL_FLOW_ISSUE_ID
                                    .getValue(),
                                    transition);
                        }
                    }
                }
            }
        }
    }

    public void validate(Activity activity) {
        if (hasIProcessScripts(activity)) {
            addIssue(ScriptGrammarErrors.IPROCESS_SCRIPT_GRAMMAR_ACTIVITY.getValue(),
                    activity);
            /* Don't bother with other kinds of error until this is sorted out. */
            return;
        }

        // validate audit scripts
        Audit audit =
                (Audit) Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Audit());
        if (null != audit) {
            validateAuditScripts(audit, activity);
        }

        // validate user task scripts
        if (activity.getImplementation() instanceof Task) {
            Task task = (Task) activity.getImplementation();

            TaskUser taskUser = task.getTaskUser();
            if (null != taskUser) {
                validateUserTaskScripts(taskUser, activity);
            }
        }

        // validate script task scripts
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;
            TaskScript taskScript = task.getTaskScript();
            if (null != taskScript) {
                validateScriptTaskScripts(taskScript, activity);
            }
        }

        // validate sub-proc task scripts
        validateTaskLoopScripts(activity);

        Object adhocConfig =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AdHocTaskConfiguration());
        /*
         * If the Acivity is an Adhoc activity then validate its scripts.
         */
        if (adhocConfig instanceof AdHocTaskConfigurationType) {
            validateAdhocPreconditionScripts(activity,
                    (AdHocTaskConfigurationType) adhocConfig);
        }
    }

    /**
     * Validate the Adhoc Task Precondition Scripts.
     * 
     * @param activity
     *            the Adhoc Activity
     * @param adhocConfig
     *            the Adhoc Task Configuration.
     */
    private void validateAdhocPreconditionScripts(Activity activity,
            AdHocTaskConfigurationType adhocConfig) {

        EnablementType enablement = adhocConfig.getEnablement();

        if (enablement != null) {
            Expression preconditionExpression =
                    enablement.getPreconditionExpression();

            if (preconditionExpression != null) {
                String scriptGrammar =
                        preconditionExpression.getScriptGrammar();

                if (ProcessJsConsts.JAVASCRIPT_GRAMMAR
                        .equalsIgnoreCase(scriptGrammar)) {

                    String scriptText = preconditionExpression.getText();

                    if (ScriptParserUtil.isEmptyScript(scriptText,
                            scriptGrammar)) {
                        /*
                         * If the Precondition Script Grammar is 'JavaScript'
                         * and the Script is empty then raise an error.
                         */
                        addIssue(ScriptGrammarErrors.ADHOC_PRECONDITION_JAVA_SCRIPT_EMPTY
                                .getValue(),
                                activity);
                    }
                } else if (!ProcessJsConsts.DATA_MAPPER_GRAMMAR
                        .equalsIgnoreCase(scriptGrammar)
                        && !UNSPECIFIED_SCRIPTGRAMMAR
                                .equalsIgnoreCase(scriptGrammar)) {
                    /*
                     * If the Precondition Script Grammar is 'FreeText' then
                     * raise an error.
                     */
                    addIssue(ScriptGrammarErrors.ADHOC_PRECONDITION_FREETEXT_UNSUPPORTED_ISSUE_ID
                            .getValue(),
                            activity);
                }
            }
        }
    }

    private void validateTaskLoopScripts(Activity activity) {
        String scriptGrammar = null;
        LoopStandard standardLoop = TaskObjectUtil.getStandardLoop(activity);

        if (null != standardLoop) {
            Expression stdLoopExpression =
                    ProcessScriptUtil.getStdLoopExpression(activity);
            if (null != stdLoopExpression) {
                scriptGrammar = stdLoopExpression.getScriptGrammar();
                if (ProcessJsConsts.JAVASCRIPT_GRAMMAR
                        .equalsIgnoreCase(scriptGrammar)) {
                    String scriptText = stdLoopExpression.getText();
                    if (ScriptParserUtil.isEmptyScript(scriptText,
                            ProcessJsConsts.STD_LOOP_EXPR,
                            scriptGrammar)) {
                        addIssue(ScriptGrammarErrors.STD_LOOP_EXPR_REQUIRED.getValue(),
                                activity);
                    }
                } else {
                    addIssue(ScriptGrammarErrors.LOOP_SCRIPT_UNSUPPORTED_ISSUE_ID
                            .getValue(),
                            activity);
                }
            } else {
                addIssue(ScriptGrammarErrors.STD_LOOP_EXPR_REQUIRED.getValue(),
                        activity);
            }
        }

        LoopMultiInstance loopMultiInstance =
                TaskObjectUtil.getLoopMultiInstance(activity);
        if (null != loopMultiInstance) {
            if (null != loopMultiInstance.getMICondition()) {
                scriptGrammar =
                        loopMultiInstance.getMICondition().getScriptGrammar();
                if (ProcessJsConsts.JAVASCRIPT_GRAMMAR
                        .equalsIgnoreCase(scriptGrammar)) {
                    String scriptText =
                            loopMultiInstance.getMICondition().getText();
                    if (ScriptParserUtil.isEmptyScript(scriptText,
                            ProcessJsConsts.MI_LOOP_EXPR,
                            scriptGrammar)) {
                        addIssue(ScriptGrammarErrors.MI_LOOP_EXPR_REQUIRED.getValue(),
                                activity);
                    }
                } else {
                    addIssue(ScriptGrammarErrors.LOOP_SCRIPT_UNSUPPORTED_ISSUE_ID
                            .getValue(),
                            activity);
                }
            } else {
                addIssue(ScriptGrammarErrors.MI_LOOP_EXPR_REQUIRED.getValue(),
                        activity);
            }

            if (null != loopMultiInstance.getComplexMIFlowCondition()) {
                scriptGrammar =
                        loopMultiInstance.getComplexMIFlowCondition()
                                .getScriptGrammar();
                if (ProcessJsConsts.JAVASCRIPT_GRAMMAR
                        .equalsIgnoreCase(scriptGrammar)) {
                    String scriptText =
                            loopMultiInstance.getComplexMIFlowCondition()
                                    .getText();
                    if (ScriptParserUtil.isEmptyScript(scriptText,
                            ProcessJsConsts.MI_LOOP_EXPR,
                            scriptGrammar)) {
                        addIssue(ScriptGrammarErrors.LOOP_COMPLEX_JAVA_SCRIPT_EMPTY
                                .getValue(),
                                activity);
                    }
                } else {
                    addIssue(ScriptGrammarErrors.LOOP_COMPLEX_SCRIPT_UNSUPPORTED_ISSUE_ID
                            .getValue(),
                            activity);
                }
            }

            if (null != loopMultiInstance) {
                MultiInstanceScripts miLoopScript =
                        (MultiInstanceScripts) Xpdl2ModelUtil
                                .getOtherElement(loopMultiInstance,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_MultiInstanceScripts());
                if (null != miLoopScript.getAdditionalInstances()) {
                    scriptGrammar =
                            miLoopScript.getAdditionalInstances()
                                    .getScriptGrammar();
                    if (ProcessJsConsts.JAVASCRIPT_GRAMMAR
                            .equalsIgnoreCase(scriptGrammar)) {
                        String scriptText =
                                miLoopScript.getAdditionalInstances().getText();
                        if (ScriptParserUtil.isEmptyScript(scriptText,
                                ProcessJsConsts.SCRIPT_TASK,
                                scriptGrammar)) {
                            addIssue(ScriptGrammarErrors.LOOP_ADDITIONAL_JAVA_SCRIPT_EMPTY
                                    .getValue(),
                                    activity);
                        }
                    } else {
                        addIssue(ScriptGrammarErrors.LOOP_ADDITIONAL_SCRIPT_UNSUPPORTED_ISSUE_ID
                                .getValue(),
                                activity);
                    }
                }
            }
        }
    }

    private void validateScriptTaskScripts(TaskScript taskScript,
            Activity activity) {
        String scriptGrammar = null;
        if (null != taskScript) {
            scriptGrammar = UNSPECIFIED_SCRIPTGRAMMAR;
            if (taskScript.getScript() != null
                    && taskScript.getScript().getScriptGrammar() != null) {
                scriptGrammar = taskScript.getScript().getScriptGrammar();
            }

            if (ProcessJsConsts.JAVASCRIPT_GRAMMAR
                    .equalsIgnoreCase(scriptGrammar)) {
                boolean emptyScript =
                        ScriptParserUtil.isEmptyScript(taskScript.getScript()
                                .getText(),
                                ProcessJsConsts.SCRIPT_TASK,
                                scriptGrammar);
                if (emptyScript) {
                    addIssue(ScriptGrammarErrors.EMPTY_JAVASCRIPT.getValue(),
                            activity);
                }
            } else if (!ProcessJsConsts.DATA_MAPPER_GRAMMAR
                    .equalsIgnoreCase(scriptGrammar)) {
                addIssue(ScriptGrammarErrors.SCRIPT_UNSUPPORTED_ISSUE_ID.getValue(),
                        activity);
            }
        }
    }

    private void validateUserTaskScripts(TaskUser taskUser, Activity activity) {
        String scriptGrammar = null;
        if (null != taskUser) {

            /**
             * XPD-1070: Java Script grammar for open, close, submit and
             * schedule scripts on page activities are not supported
             */
            Process process = activity.getProcess();
            boolean isPageflowProcess = Xpdl2ModelUtil.isPageflow(process);
            // XPD-1070 - end
            UserTaskScripts userTaskScripts =
                    (UserTaskScripts) taskUser
                            .getOtherElement(XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_UserTaskScripts()
                                    .getName());

            if (null != userTaskScripts) {

                // open script
                if (null != userTaskScripts.getOpenScript()) {
                    scriptGrammar =
                            userTaskScripts.getOpenScript().getScriptGrammar();
                    /**
                     * XPD-1070: Free Text grammar for page activity open script
                     * not supported
                     */
                    if (isPageflowProcess) {
                        addIssue(ScriptGrammarErrors.PAGE_ACTIVITY_OPEN_SCRIPT_NOT_SUPPORTED
                                .getValue(),
                                activity);
                    } else {
                        if (ProcessJsConsts.JAVASCRIPT_GRAMMAR
                                .equalsIgnoreCase(scriptGrammar)) {
                            String scriptText =
                                    userTaskScripts.getOpenScript().getText();
                            if (ScriptParserUtil.isEmptyScript(scriptText,
                                    ProcessJsConsts.OPEN_USER_TASK,
                                    scriptGrammar)) {
                                addIssue(ScriptGrammarErrors.OPEN_JAVA_SCRIPT_EMPTY
                                        .getValue(),
                                        activity);
                            }
                        } else if (!ProcessJsConsts.DATA_MAPPER_GRAMMAR
                                .equalsIgnoreCase(scriptGrammar)
                                && !UNSPECIFIED_SCRIPTGRAMMAR
                                        .equalsIgnoreCase(scriptGrammar)) {
                            addIssue(ScriptGrammarErrors.OPEN_SCRIPT_UNSUPPORTED_ISSUE_ID
                                    .getValue(),
                                    activity);
                        }
                    }
                }

                // close script
                if (null != userTaskScripts.getCloseScript()) {
                    scriptGrammar =
                            userTaskScripts.getCloseScript().getScriptGrammar();
                    /**
                     * XPD-1070: Free Text grammar for page activity close
                     * script not supported
                     */
                    if (isPageflowProcess) {
                        addIssue(ScriptGrammarErrors.PAGE_ACTIVITY_CLOSE_SCRIPT_NOT_SUPPORTED
                                .getValue(),
                                activity);
                    } else {
                        if (ProcessJsConsts.JAVASCRIPT_GRAMMAR
                                .equalsIgnoreCase(scriptGrammar)) {
                            String scriptText =
                                    userTaskScripts.getCloseScript().getText();
                            if (ScriptParserUtil.isEmptyScript(scriptText,
                                    ProcessJsConsts.CLOSE_USER_TASK,
                                    scriptGrammar)) {
                                addIssue(ScriptGrammarErrors.CLOSE_JAVA_SCRIPT_EMPTY
                                        .getValue(),
                                        activity);
                            }
                        } else if (!ProcessJsConsts.DATA_MAPPER_GRAMMAR
                                .equalsIgnoreCase(scriptGrammar)
                                && !UNSPECIFIED_SCRIPTGRAMMAR
                                        .equalsIgnoreCase(scriptGrammar)) {
                            addIssue(ScriptGrammarErrors.CLOSE_SCRIPT_UNSUPPORTED_ISSUE_ID
                                    .getValue(),
                                    activity);
                        }
                    }
                }

                // submit script
                if (null != userTaskScripts.getSubmitScript()) {
                    scriptGrammar =
                            userTaskScripts.getSubmitScript()
                                    .getScriptGrammar();
                    /**
                     * XPD-1070: Free Text grammar for page activity submit
                     * script not supported
                     */
                    if (isPageflowProcess) {
                        addIssue(ScriptGrammarErrors.PAGE_ACTIVITY_SUBMIT_SCRIPT_NOT_SUPPORTED
                                .getValue(),
                                activity);
                    } else {
                        if (ProcessJsConsts.JAVASCRIPT_GRAMMAR
                                .equalsIgnoreCase(scriptGrammar)) {
                            String scriptText =
                                    userTaskScripts.getSubmitScript().getText();
                            if (ScriptParserUtil.isEmptyScript(scriptText,
                                    ProcessJsConsts.SUBMIT_USER_TASK,
                                    scriptGrammar)) {
                                addIssue(ScriptGrammarErrors.SUBMIT_JAVA_SCRIPT_EMPTY
                                        .getValue(),
                                        activity);
                            }
                        } else if (!ProcessJsConsts.DATA_MAPPER_GRAMMAR
                                .equalsIgnoreCase(scriptGrammar)
                                && !UNSPECIFIED_SCRIPTGRAMMAR
                                        .equalsIgnoreCase(scriptGrammar)) {
                            addIssue(ScriptGrammarErrors.SUBMIT_SCRIPT_UNSUPPORTED_ISSUE_ID
                                    .getValue(),
                                    activity);
                        }
                    }
                }

                // schedule script
                if (null != userTaskScripts.getScheduleScript()) {
                    scriptGrammar =
                            userTaskScripts.getScheduleScript()
                                    .getScriptGrammar();
                    /**
                     * XPD-1070: Free Text grammar for page activity schedule
                     * script not supported
                     */
                    if (isPageflowProcess) {
                        addIssue(ScriptGrammarErrors.PAGE_ACTIVITY_SCHEDULE_SCRIPT_NOT_SUPPORTED
                                .getValue(),
                                activity);
                    } else {
                        if (ProcessJsConsts.JAVASCRIPT_GRAMMAR
                                .equalsIgnoreCase(scriptGrammar)) {
                            String scriptText =
                                    userTaskScripts.getScheduleScript()
                                            .getText();
                            if (ScriptParserUtil.isEmptyScript(scriptText,
                                    ProcessJsConsts.SCHEDULE_USER_TASK,
                                    scriptGrammar)) {
                                addIssue(ScriptGrammarErrors.SCHEDULE_JAVA_SCRIPT_EMPTY
                                        .getValue(),
                                        activity);
                            }
                        } else if (!ProcessJsConsts.DATA_MAPPER_GRAMMAR
                                .equalsIgnoreCase(scriptGrammar)
                                && !UNSPECIFIED_SCRIPTGRAMMAR
                                        .equalsIgnoreCase(scriptGrammar)) {
                            addIssue(ScriptGrammarErrors.SCHEDULE_SCRIPT_UNSUPPORTED_ISSUE_ID
                                    .getValue(),
                                    activity);
                        }
                    }
                }

                // reschedule script
                if (null != userTaskScripts.getRescheduleScript()) {
                    scriptGrammar =
                            userTaskScripts.getRescheduleScript()
                                    .getScriptGrammar();

                    if (isPageflowProcess) {
                        addIssue(ScriptGrammarErrors.PAGE_ACTIVITY_RESCHEDULE_SCRIPT_NOT_SUPPORTED
                                .getValue(),
                                activity);
                    } else {
                        if (ProcessJsConsts.JAVASCRIPT_GRAMMAR
                                .equalsIgnoreCase(scriptGrammar)) {
                            String scriptText =
                                    userTaskScripts.getRescheduleScript()
                                            .getText();
                            if (ScriptParserUtil.isEmptyScript(scriptText,
                                    ProcessJsConsts.RESCHEDULE_USER_TASK,
                                    scriptGrammar)) {
                                addIssue(ScriptGrammarErrors.RESCHEDULE_JAVA_SCRIPT_EMPTY
                                        .getValue(),
                                        activity);
                            }
                        } else if (!ProcessJsConsts.DATA_MAPPER_GRAMMAR
                                .equalsIgnoreCase(scriptGrammar)
                                && !UNSPECIFIED_SCRIPTGRAMMAR
                                        .equalsIgnoreCase(scriptGrammar)) {
                            addIssue(ScriptGrammarErrors.RESCHEDULE_SCRIPT_UNSUPPORTED_ISSUE_ID
                                    .getValue(),
                                    activity);
                        }
                    }
                }
            }
        }
    }

    private void validateAuditScripts(Audit audit, Activity activity) {
        String scriptGrammar = null;

        if (null != audit) {
            for (AuditEvent auditEvent : audit.getAuditEvent()) {
                if (null != auditEvent.getInformation()) {
                    scriptGrammar =
                            auditEvent.getInformation().getScriptGrammar();
                    if (ProcessJsConsts.JAVASCRIPT_GRAMMAR
                            .equalsIgnoreCase(scriptGrammar)) {
                        String auditScript =
                                auditEvent.getInformation().getText();
                        if (ScriptParserUtil.isEmptyScript(auditScript,
                                ProcessJsConsts.SCRIPT_TASK,
                                scriptGrammar)) {
                            if (AuditEventType.CANCELLED_LITERAL
                                    .equals(auditEvent.getType())) {
                                addIssue(ScriptGrammarErrors.CANCEL_JAVA_SCRIPT_EMTPY
                                        .getValue(),
                                        activity);
                            }
                            if (AuditEventType.COMPLETED_LITERAL
                                    .equals(auditEvent.getType())) {
                                addIssue(ScriptGrammarErrors.COMPLETE_JAVA_SCRIPT_EMPTY
                                        .getValue(),
                                        activity);
                            }
                            if (AuditEventType.DEADLINE_EXPIRED_LITERAL
                                    .equals(auditEvent.getType())) {
                                addIssue(ScriptGrammarErrors.DEADLINE_JAVA_SCRIPT_EMPTY
                                        .getValue(),
                                        activity);
                            }
                            if (AuditEventType.INITIATED_LITERAL
                                    .equals(auditEvent.getType())) {
                                addIssue(ScriptGrammarErrors.INITIATE_JAVA_SCRIPT_EMPTY
                                        .getValue(),
                                        activity);
                            }
                        }
                    } else if (!ProcessJsConsts.DATA_MAPPER_GRAMMAR
                            .equalsIgnoreCase(scriptGrammar)
                            && !UNSPECIFIED_SCRIPTGRAMMAR
                                    .equalsIgnoreCase(scriptGrammar)) {
                        if (AuditEventType.CANCELLED_LITERAL.equals(auditEvent
                                .getType())) {
                            addIssue(ScriptGrammarErrors.CANCEL_SCRIPT_UNSUPPORTED_ISSUE_ID
                                    .getValue(),
                                    activity);
                        }

                        if (AuditEventType.COMPLETED_LITERAL.equals(auditEvent
                                .getType())) {
                            addIssue(ScriptGrammarErrors.COMPLETE_SCRIPT_UNSUPPORTED_ISSUE_ID
                                    .getValue(),
                                    activity);
                        }

                        if (AuditEventType.DEADLINE_EXPIRED_LITERAL
                                .equals(auditEvent.getType())) {
                            addIssue(ScriptGrammarErrors.DEADLINE_SCRIPT_UNSUPPORTED_ISSUE_ID
                                    .getValue(),
                                    activity);
                        }

                        if (AuditEventType.INITIATED_LITERAL.equals(auditEvent
                                .getType())) {
                            addIssue(ScriptGrammarErrors.INITIATE_SCRIPT_UNSUPPORTED_ISSUE_ID
                                    .getValue(),
                                    activity);
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * @param actOrTrans
     * @return <code>true</code> if the given object has any xpdl2 expression
     *         scripts with iProcessScript grammar.
     */
    private boolean hasIProcessScripts(EObject eObject) {
        /*
         * Iterate thru entire content of object looking for Expression objects
         * with iProcessScript grammar.
         */
        for (Iterator iterator = eObject.eAllContents(); iterator.hasNext();) {
            EObject eo = (EObject) iterator.next();

            if (eo instanceof Expression) {
                Expression expression = (Expression) eo;

                if (AbstractIProcessToJavaScriptConverter.IPROCESSSCRIPT_GRAMMAR
                        .equals(expression.getScriptGrammar())) {
                    return true;
                }
            }
        }
        return false;
    }

}
