/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.bx.validation.validator;

import com.tibco.xpd.script.parser.validator.IExpressionValidator;

/**
 * @author bharge
 * 
 */
public class N2JScriptMIAddlInstanceExprValidationStrategy extends
        N2JScriptValidationStrategy {

    private IExpressionValidator numericTreeValidator;

    @Override
    protected IExpressionValidator getASTTreeValidator() {
        if (numericTreeValidator == null) {
            numericTreeValidator = new N2NumberASTTreeValidator();
        }
        return numericTreeValidator;
    }

}
