/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;


/**
 * generic JavaScript validation for reschedule script.
 * 
 * 
 * @author aallway
 * @since 23 Jul 2012
 */
public class JsUserTaskRescheduleScriptTaskRule extends
        AbstractUserTaskScheduleScriptTaskScriptRule {

    /** The issue id. */
    private static final String ERROR_ID = "js.validateScriptTask"; //$NON-NLS-1$

    private static final String WARNING_ID = "js.warning.validateScriptTask"; //$NON-NLS-1$

    @Override
    protected String getErrorId() {
        return ERROR_ID;
    }

    @Override
    protected String getWarningId() {
        return WARNING_ID;
    }

}
