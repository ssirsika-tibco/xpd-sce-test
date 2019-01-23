/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.refactoring;

import com.tibco.xpd.script.parser.internal.expr.IExpr;
/**
 * Factory class for the creation of IExpressionRefactor
 * 
 * @author mtorres
 */
public interface IExpressionRefactorFactory {
    /**
     * Returns expression refactor
     * 
     * @param expr
     * @return the expression refactor
     */
    IExpressionRefactor getExpressionRefactor(IExpr expr);
}
