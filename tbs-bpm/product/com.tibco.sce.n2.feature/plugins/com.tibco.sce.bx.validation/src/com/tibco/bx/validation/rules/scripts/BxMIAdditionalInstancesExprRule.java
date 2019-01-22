/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.bx.validation.rules.scripts;

import com.tibco.xpd.js.validation.rules.AbstractMIAdditionalInstancesExprRule;

/**
 * 
 * 
 * 
 * @author agondal
 * @since 17 Sep 2013
 */
public class BxMIAdditionalInstancesExprRule extends
        AbstractMIAdditionalInstancesExprRule {

    /** The issue id. */
    private static final String ERROR_ID =
            "bx.validateMIAdditionalInstancesExpr"; //$NON-NLS-1$

    private static final String WARNING_ID =
            "bx.warning.validateMIAdditionalInstancesExpr"; //$NON-NLS-1$

    @Override
    protected String getErrorId() {
        return ERROR_ID;
    }

    @Override
    protected String getWarningId() {
        return WARNING_ID;
    }

}
