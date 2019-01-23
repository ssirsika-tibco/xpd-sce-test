/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.refactoring;

import java.util.List;

import com.tibco.xpd.script.parser.internal.expr.IExpr;
/**
 * 
 * Interface that provides refactoring to the expression passed
 * 
 * @author mtorres
 */
public interface IRefactor {
	
    /**
     * @return the list
     */
    public List<RefactorResult> getRefactorResultList();

	/**
	 * Evaluates the expression
	 * 
	 * @param expresion
	 * @return the result of the evaluation
	 */
	RefactorResult evaluate(IExpr expresion);
	
}
