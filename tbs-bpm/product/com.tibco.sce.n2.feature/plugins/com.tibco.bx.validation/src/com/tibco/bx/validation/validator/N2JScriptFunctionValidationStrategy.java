/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.bx.validation.validator;

import com.tibco.xpd.script.parser.internal.validator.IValidator;
import com.tibco.xpd.script.parser.validator.IExpressionValidator;

/**
 * Overridden to enforce return statements on the last statement of the script.
 * This is needed when the script will be inserted into the body of a function.
 * 
 * @author nwilson
 * 
 */
public class N2JScriptFunctionValidationStrategy extends
        N2JScriptValidationStrategy {

    @Override
    protected IValidator getStatementValidator() {
        if (statementValidator == null) {
            statementValidator = new N2FunctionStatementValidator();
        }
        return statementValidator;
    }

    /**
     * @see com.tibco.xpd.script.parser.internal.validator.AbstractValidationStrategy#getReturnTypeValidator()
     * 
     * @return
     */
    @Override
    protected IExpressionValidator getReturnTypeValidator() {
        if (returnTypeValidator == null) {
            returnTypeValidator = new N2JScriptReturnTypeValidator();
        }
        return returnTypeValidator;
    }

}
