/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.bx.validation.validator;

import com.tibco.xpd.process.js.parser.validator.jscript.ProcessJScriptBooleanASTTreeValidator;
import com.tibco.xpd.script.parser.validator.IExpressionValidator;

/**
 * 
 * @author Miguel Torres
 * 
 */
public class N2JScriptStdLoopExprValidationStrategy extends
        N2JScriptValidationStrategy {

    private IExpressionValidator booleanTreeValidator;

    @Override
    protected IExpressionValidator getASTTreeValidator() {
        if (booleanTreeValidator == null) {
            booleanTreeValidator = new ProcessJScriptBooleanASTTreeValidator();
        }
        return booleanTreeValidator;
    }

}
