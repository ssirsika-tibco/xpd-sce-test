/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.bx.validation.validator;

import com.tibco.bx.validation.internal.validator.N2JScriptASTTreeLastExpressionValidator;
import com.tibco.xpd.script.parser.validator.IExpressionValidator;

/**
 * 
 * @author Miguel Torres
 * 
 */
public class N2JScriptSequenceFlowExprValidationStrategy extends
        N2JScriptValidationStrategy {

    private IExpressionValidator astSequenceFlowTreeValidator;

    @Override
    protected IExpressionValidator getASTTreeValidator() {
        if (astSequenceFlowTreeValidator == null) {
            astSequenceFlowTreeValidator = new N2JScriptASTTreeLastExpressionValidator();
        }
        return astSequenceFlowTreeValidator;
    }

}
