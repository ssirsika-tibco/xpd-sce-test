/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

/**
 * 
 * <p>
 * <i>Created: 11 May 2011</i>
 * </p>
 * 
 * @author mtorres
 * 
 */
public class JsDecFlowUnmappedScriptOutputRule extends
        AbstractDecFlowUnmappedScriptOutputRule {

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
