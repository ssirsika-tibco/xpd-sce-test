/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.rules.scripts;

import com.tibco.xpd.js.validation.rules.AbstractAdhocPreconditionScriptRule;

/**
 * Script rule for Adhoc Task Precondition Script. 
 * 
 * @author kthombar
 * @since 15-Aug-2014
 */
public class BxAdhocPreconditionScriptRule extends
        AbstractAdhocPreconditionScriptRule {

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#getErrorId()
     * 
     * @return
     */
    @Override
    protected String getErrorId() {
        return "bx.error.adhocPreconditionScript"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#getWarningId()
     * 
     * @return
     */
    @Override
    protected String getWarningId() {
        return "bx.warning.adhocPreconditionScript"; //$NON-NLS-1$
    }

}
