/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.bx.validation.validator;

import com.tibco.xpd.process.js.parser.validator.jscript.ProcessJScriptNumberOrBooleanASTTreeValidator;
import com.tibco.xpd.script.parser.validator.IExpressionValidator;

/**
 * 
 * 
 * @author Miguel Torres
 * 
 */
public class N2JScriptMIComplexExitExprValidationStrategy extends
        N2JScriptValidationStrategy {

    private IExpressionValidator booleanNumericTreeValidator;

    @Override
    protected IExpressionValidator getASTTreeValidator() {
        if (booleanNumericTreeValidator == null) {
            booleanNumericTreeValidator = new ProcessJScriptNumberOrBooleanASTTreeValidator();
        }
        return booleanNumericTreeValidator;
    }

}
