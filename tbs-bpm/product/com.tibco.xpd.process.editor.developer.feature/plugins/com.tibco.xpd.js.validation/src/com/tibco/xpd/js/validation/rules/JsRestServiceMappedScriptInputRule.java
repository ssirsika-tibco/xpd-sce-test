/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

/**
 * 
 * @author nwilson
 */
public class JsRestServiceMappedScriptInputRule extends
        AbstractRestServiceMappedScriptInputRule {

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
