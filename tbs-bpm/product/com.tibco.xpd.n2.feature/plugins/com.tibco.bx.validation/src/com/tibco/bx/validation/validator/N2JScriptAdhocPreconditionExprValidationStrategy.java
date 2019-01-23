/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.bx.validation.validator;

import com.tibco.bx.validation.internal.validator.N2JScriptASTTreeLastExpressionValidator;
import com.tibco.xpd.script.parser.validator.IExpressionValidator;

/**
 * Adhoc Precondition Expression validation strategy which additionally checks
 * if the last statement of the Script is a boolean expression or not. If not
 * then shows a warning marker in the Script editor
 * 
 * 
 * @author kthombar
 * @since 15-Aug-2014
 */
public class N2JScriptAdhocPreconditionExprValidationStrategy extends
        N2JScriptValidationStrategy {

    private IExpressionValidator astSequenceFlowTreeValidator;

    @Override
    protected IExpressionValidator getASTTreeValidator() {
        if (astSequenceFlowTreeValidator == null) {
            astSequenceFlowTreeValidator =
                    new N2JScriptASTTreeLastExpressionValidator();
        }
        return astSequenceFlowTreeValidator;
    }
}
