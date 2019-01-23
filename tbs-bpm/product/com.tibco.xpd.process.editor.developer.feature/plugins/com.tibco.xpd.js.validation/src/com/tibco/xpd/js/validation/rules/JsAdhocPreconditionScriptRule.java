/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.js.validation.rules;

/**
 * JavaScript Rules for Adhoc Task Precondition Script.
 * 
 * @author kthombar
 * @since 15-Aug-2014
 */
public class JsAdhocPreconditionScriptRule extends
        AbstractAdhocPreconditionScriptRule {

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#getErrorId()
     * 
     * @return
     */
    @Override
    protected String getErrorId() {
        return "js.error.adhocPreconditionScript"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#getWarningId()
     * 
     * @return
     */
    @Override
    protected String getWarningId() {
        return "js.warning.adhocTaskPreconditionScript"; //$NON-NLS-1$
    }

}
