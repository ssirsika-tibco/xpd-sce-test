/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.bx.validation.rules.scripts;

import com.tibco.xpd.js.validation.rules.AbstractUserTaskRescheduleScriptTaskScriptRule;

/**
 * AMX BPM user task reschedule script rule.
 * 
 * 
 * @author aallway
 * @since 23 Jul 2012
 */
public class BxUserTaskRescheduleScriptTaskRule extends
        AbstractUserTaskRescheduleScriptTaskScriptRule {

    /** The issue id. */
    private static final String ERROR_ID = "bx.error.userTaskRescheduleScript"; //$NON-NLS-1$

    private static final String WARNING_ID =
            "bx.warning.userTaskRescheduleScript"; //$NON-NLS-1$

    @Override
    protected String getErrorId() {
        return ERROR_ID;
    }

    @Override
    protected String getWarningId() {
        return WARNING_ID;
    }

}
