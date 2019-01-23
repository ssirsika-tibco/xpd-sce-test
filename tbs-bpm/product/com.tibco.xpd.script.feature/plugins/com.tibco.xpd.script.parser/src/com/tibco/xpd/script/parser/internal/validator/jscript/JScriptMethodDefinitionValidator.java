/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.parser.Messages;

/**
 * 
 * Expression validator for the validation of method definitions
 * 
 * @author mtorres
 */
public class JScriptMethodDefinitionValidator extends
        AbstractExpressionValidator {

    @Override
    public void validate(AST methodDefinitionAST, Token token) {
        String errorMessage =
                Messages.JScriptMethodDefinitionValidator_LocalMethodDefinitionIsNotAllowed;
        addErrorMessage(token, errorMessage);

        getLocalMethodsMap();

    }

}
