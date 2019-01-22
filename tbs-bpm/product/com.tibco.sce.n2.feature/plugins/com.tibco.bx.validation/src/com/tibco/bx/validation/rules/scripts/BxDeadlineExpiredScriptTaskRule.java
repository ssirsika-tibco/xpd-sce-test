/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.bx.validation.rules.scripts;

import com.tibco.xpd.js.validation.rules.AbstractDeadlineExpiredScriptTaskScriptRule;

/**
 * 
 * 
 * 
 * @author agondal
 * @since 17 Sep 2013
 */
public class BxDeadlineExpiredScriptTaskRule extends
        AbstractDeadlineExpiredScriptTaskScriptRule {

    /** The issue id. */
    private static final String ERROR_ID = "bx.error.taskTimoutScript"; //$NON-NLS-1$

    private static final String WARNING_ID = "bx.warning.taskTimeoutScript"; //$NON-NLS-1$

    @Override
    protected String getErrorId() {
        return ERROR_ID;
    }

    @Override
    protected String getWarningId() {
        return WARNING_ID;
    }
}
