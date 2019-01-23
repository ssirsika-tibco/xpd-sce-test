package com.tibco.bx.validation.rules.scripts;

import com.tibco.xpd.js.validation.rules.AbstractTimerEventRule;

/**
 * 
 * 
 * 
 * @author agondal
 * @since 19 Sep 2013
 */
public class BxTimerEventScriptRule extends AbstractTimerEventRule {

    /** The issue id. */
    private static final String ERROR_ID = "bx.validateTimerEventScript"; //$NON-NLS-1$

    private static final String WARNING_ID =
            "bx.warning.validateTimerEventScript"; //$NON-NLS-1$

    @Override
    protected String getErrorId() {
        return ERROR_ID;
    }

    @Override
    protected String getWarningId() {
        return WARNING_ID;
    }
}
