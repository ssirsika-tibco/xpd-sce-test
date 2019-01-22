/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator;

import com.tibco.xpd.script.parser.internal.expr.IExpr;

/**
 * 
 * Factory class for the creation of IExpr
 * 
 * @author mtorres
 */
public interface IExpressionFactory {
    /**
     * Creates the Expression
     * 
     * @param expr
     * @param token
     * @return
     */
    public IExpr createExpr(Object expr, Object token);
}
