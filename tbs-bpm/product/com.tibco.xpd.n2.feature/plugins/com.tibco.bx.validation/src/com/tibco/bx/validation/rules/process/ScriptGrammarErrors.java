/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

/**
 * ScriptGrammarErrors - issue ids pertaining to conditional transition, audit
 * scripts, user task scripts, re-usable sub-proc scripts when free text is not
 * supported and when java script is selected but no script is specified
 * 
 * 
 * @author bharge
 * @since 3.3 (21 Jun 2010)
 */
public enum ScriptGrammarErrors {

    CANCEL_SCRIPT_UNSUPPORTED_ISSUE_ID("bx.cancelScriptUnsupported"), //$NON-NLS-1$

    COMPLETE_SCRIPT_UNSUPPORTED_ISSUE_ID("bx.completeScriptUnsupported"), //$NON-NLS-1$

    DEADLINE_SCRIPT_UNSUPPORTED_ISSUE_ID("bx.deadlineScriptUnsupported"), //$NON-NLS-1$

    INITIATE_SCRIPT_UNSUPPORTED_ISSUE_ID("bx.initiateScriptUnsupported"), //$NON-NLS-1$

    OPEN_SCRIPT_UNSUPPORTED_ISSUE_ID("bx.openScriptUnsupported"), //$NON-NLS-1$

    CLOSE_SCRIPT_UNSUPPORTED_ISSUE_ID("bx.closeScriptUnsupported"), //$NON-NLS-1$

    SUBMIT_SCRIPT_UNSUPPORTED_ISSUE_ID("bx.submitScriptUnsupported"), //$NON-NLS-1$

    SCHEDULE_SCRIPT_UNSUPPORTED_ISSUE_ID("bx.scheduleScriptUnsupported"), //$NON-NLS-1$

    RESCHEDULE_SCRIPT_UNSUPPORTED_ISSUE_ID("bx.rescheduleScriptUnsupported"), //$NON-NLS-1$

    SCRIPT_UNSUPPORTED_ISSUE_ID("bx.scriptUnsupported"), //$NON-NLS-1$

    EMPTY_JAVASCRIPT("bx.emptyJavaScript"), //$NON-NLS-1$

    LOOP_SCRIPT_UNSUPPORTED_ISSUE_ID("bx.loopScriptUnsupported"), //$NON-NLS-1$

    LOOP_COMPLEX_SCRIPT_UNSUPPORTED_ISSUE_ID("bx.loopComplexUnsupported"), //$NON-NLS-1$

    LOOP_ADDITIONAL_SCRIPT_UNSUPPORTED_ISSUE_ID(
            "bx.loopAdditionalScriptUnsupported"), //$NON-NLS-1$

    CANCEL_JAVA_SCRIPT_EMTPY("bx.cancelJavaScriptEmpty"), //$NON-NLS-1$

    COMPLETE_JAVA_SCRIPT_EMPTY("bx.completeJavaScriptEmpty"), //$NON-NLS-1$

    DEADLINE_JAVA_SCRIPT_EMPTY("bx.deadlineJavaScriptEmpty"), //$NON-NLS-1$

    INITIATE_JAVA_SCRIPT_EMPTY("bx.initiateJavaScriptEmpty"), //$NON-NLS-1$

    OPEN_JAVA_SCRIPT_EMPTY("bx.openJavaScriptEmpty"), //$NON-NLS-1$

    CLOSE_JAVA_SCRIPT_EMPTY("bx.closeJavaScriptEmpty"), //$NON-NLS-1$

    SUBMIT_JAVA_SCRIPT_EMPTY("bx.submitJavaScriptEmpty"), //$NON-NLS-1$

    SCHEDULE_JAVA_SCRIPT_EMPTY("bx.scheduleJavaScriptEmpty"), //$NON-NLS-1$

    RESCHEDULE_JAVA_SCRIPT_EMPTY("bx.rescheduleJavaScriptEmpty"), //$NON-NLS-1$

    ADHOC_PRECONDITION_JAVA_SCRIPT_EMPTY("bx.adhocPreconditionJavaScriptEmpty"), //$NON-NLS-1$

    ADHOC_PRECONDITION_FREETEXT_UNSUPPORTED_ISSUE_ID(
            "bx.adhocPreconditionFreeTextUnsupported"), //$NON-NLS-1$

    PAGE_ACTIVITY_OPEN_SCRIPT_NOT_SUPPORTED(
            "bx.pageActivityOpenScriptNotSupported"), //$NON-NLS-1$

    PAGE_ACTIVITY_CLOSE_SCRIPT_NOT_SUPPORTED(
            "bx.pageActivityCloseScriptNotSupported"), //$NON-NLS-1$

    PAGE_ACTIVITY_SUBMIT_SCRIPT_NOT_SUPPORTED(
            "bx.pageActivitySubmitScriptNotSupported"), //$NON-NLS-1$

    PAGE_ACTIVITY_SCHEDULE_SCRIPT_NOT_SUPPORTED(
            "bx.pageActivityScheduleScriptNotSupported"), //$NON-NLS-1$

    PAGE_ACTIVITY_RESCHEDULE_SCRIPT_NOT_SUPPORTED(
            "bx.pageActivityRescheduleScriptNotSupported"), //$NON-NLS-1$

    STD_LOOP_EXPR_REQUIRED("bx.StandardLoopExpressionRequired"), //$NON-NLS-1$

    MI_LOOP_EXPR_REQUIRED("bx.MILoopExpressionIsRequired"), //$NON-NLS-1$

    LOOP_COMPLEX_JAVA_SCRIPT_EMPTY("bx.loopComplexJavaScriptEmpty"), //$NON-NLS-1$

    LOOP_ADDITIONAL_JAVA_SCRIPT_EMPTY("bx.loopAdditionalJavaScriptEmpty"), //$NON-NLS-1$

    CONDITIONAL_FLOW_ISSUE_ID("bx.conditionalScriptExprRequired"), //$NON-NLS-1$

    IPROCESS_SCRIPT_GRAMMAR_ACTIVITY("bx.iProcessScriptGrammarUsedInActivity"), //$NON-NLS-1$

    IPROCESS_SCRIPT_GRAMMAR_FLOW("bx.iProcessScriptGrammarUsedInFlow"); //$NON-NLS-1$

    private final String error;

    private ScriptGrammarErrors(String errMessage) {
        this.error = errMessage;
    }

    public String getValue() {
        return error;
    }
}
