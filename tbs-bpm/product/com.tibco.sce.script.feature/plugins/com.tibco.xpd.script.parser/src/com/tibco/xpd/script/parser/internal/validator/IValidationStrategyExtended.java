/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator;

import antlr.Token;
import antlr.collections.AST;

/**
 * Extension Interface to evaluate an expression
 * 
 * @author mtorres
 *
 */
public interface IValidationStrategyExtended {

    /**
     * 
     * @param expressionAST
     * @param token
     * 
     * @return IValidateResult
     */
    IValidateResult evaluateExpression(AST expressionAST, Token token);


}
