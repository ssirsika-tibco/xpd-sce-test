/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator;

import java.util.List;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
/**
 * 
 * Interface that provides validation to the expression passed
 * 
 * @author mtorres
 */
public interface IValidator {
	/**
	 * Deprecated, use validate evaluate(IExpr expression)
	 * 
	 * @return
	 */
    @Deprecated
	void validate(AST expression,Token token);
	
    /**
     * Returns the Error message list
     * 
     * @return the list
     */
	List<ErrorMessage> getErrorMessageList();
	
	/**
     * Returns the warning message list
     * 
     * @return the list
     */
	List<ErrorMessage> getWarningMessageList();

	/**
	 * Evaluates the expression
	 * 
	 * @param expresion
	 * @return the result of the evaluation
	 */
	IValidateResult evaluate(IExpr expresion);
	
}
