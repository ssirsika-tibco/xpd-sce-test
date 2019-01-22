package com.tibco.bx.validation.validator;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.bx.validation.internal.Messages;

public class N2PerformerASTTreeValidator extends
        N2ExpressionValidator {

    public void validate(AST expression, Token token) {
        String errorMessage = Messages.N2PerformerASTTreeValidator_ScriptIgnored;
        addWarningMessage(token, errorMessage);
    }

}
