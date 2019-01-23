package com.tibco.xpd.js.validation.rules;

/**
 * 
 * 
 * 
 * @author agondal
 * @since 25 Sep 2013
 */

public class JsTimerEventScriptRule extends AbstractTimerEventRule {

    /** The issue id. */
    private static final String ERROR_ID = "js.validateTimerEventScript"; //$NON-NLS-1$

    private static final String WARNING_ID =
            "js.warning.validateTimerEventScript"; //$NON-NLS-1$

    @Override
    protected String getErrorId() {
        return ERROR_ID;
    }

    @Override
    protected String getWarningId() {
        return WARNING_ID;
    }
}
