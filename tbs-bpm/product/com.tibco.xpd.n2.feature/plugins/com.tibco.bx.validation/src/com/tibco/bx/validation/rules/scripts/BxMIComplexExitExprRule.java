/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.bx.validation.rules.scripts;

import com.tibco.xpd.js.validation.rules.AbstractMIComplexExitExprRule;

/**
 * 
 * 
 * 
 * @author agondal
 * @since 17 Sep 2013
 */
public class BxMIComplexExitExprRule extends AbstractMIComplexExitExprRule {

    /** The issue id. */
    private static final String ERROR_ID = "bx.validateMIComplexExitExpr"; //$NON-NLS-1$

    private static final String WARNING_ID =
            "bx.warning.validateMIComplexExitExpr"; //$NON-NLS-1$

    @Override
    protected String getErrorId() {
        return ERROR_ID;
    }

    @Override
    protected String getWarningId() {
        return WARNING_ID;
    }

}
