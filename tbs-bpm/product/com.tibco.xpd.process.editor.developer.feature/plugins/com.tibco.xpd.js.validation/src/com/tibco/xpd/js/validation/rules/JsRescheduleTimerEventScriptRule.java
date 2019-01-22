/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;


/**
 * Reschedule timer event script rule.
 * 
 * 
 * @author aallway
 * @since 19 Mar 2013
 */
public class JsRescheduleTimerEventScriptRule extends
        AbstractRescheduleTimerEventRule {

    /** The issue id. */
    private static final String ERROR_ID =
            "js.validateRescheduleTimerEventScript"; //$NON-NLS-1$

    private static final String WARNING_ID =
            "js.warning.validateRescheduleTimerEventScript"; //$NON-NLS-1$

    @Override
    protected String getErrorId() {
        return ERROR_ID;
    }

    @Override
    protected String getWarningId() {
        return WARNING_ID;
    }

}
