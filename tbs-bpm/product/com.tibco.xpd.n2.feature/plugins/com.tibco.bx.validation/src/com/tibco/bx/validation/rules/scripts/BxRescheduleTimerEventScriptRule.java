/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.bx.validation.rules.scripts;

import com.tibco.xpd.js.validation.rules.AbstractRescheduleTimerEventRule;

/**
 * Reschedule timer event script rule.
 * 
 * 
 * @author aallway
 * @since 19 Mar 2013
 */
public class BxRescheduleTimerEventScriptRule extends
        AbstractRescheduleTimerEventRule {

    /** The issue id. */
    private static final String ERROR_ID =
            "bx.validateRescheduleTimerEventScript"; //$NON-NLS-1$

    private static final String WARNING_ID =
            "bx.warning.validateRescheduleTimerEventScript"; //$NON-NLS-1$

    @Override
    protected String getErrorId() {
        return ERROR_ID;
    }

    @Override
    protected String getWarningId() {
        return WARNING_ID;
    }

}
