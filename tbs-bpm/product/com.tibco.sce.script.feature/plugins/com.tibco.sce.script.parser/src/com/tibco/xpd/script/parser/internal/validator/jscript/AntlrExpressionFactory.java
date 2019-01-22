/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IExpressionFactory;
/**
 * 
 * ExpressionFactory class that creates an AntlrExprImpl for a given expression
 * and token
 * 
 * @author mtorres
 */
public class AntlrExpressionFactory implements IExpressionFactory {
    public IExpr createExpr(Object expr, Object token) {
        if (expr instanceof AST && token instanceof Token) {
            return new AntlrExprImpl((AST) expr, (Token) token);
        }
        return null;
    }
}
