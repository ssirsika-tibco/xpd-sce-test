/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.refactoring.jscript;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.refactoring.RefactorResult;

/**
 * 
 * @author mtorres
 */
public class JScriptExpressionRefactor extends AbstractExpressionRefactor {

    @Override
    public RefactorResult evaluate(IExpr expression) {
        if (expression != null) {
            Object expr = expression.getExpr();
            Object token = expression.getToken();
            if (expr instanceof AST && token instanceof Token) {
                AST astExpr = (AST) expr;
                AST firstChild = astExpr.getFirstChild();
                RefactorResult delegateEvaluateExpression =
                        delegateEvaluateExpression(firstChild, token);
                if (delegateEvaluateExpression != null) {
                    getRefactorResultList().add(delegateEvaluateExpression);
                }
            }
        }
        return null;
    }

}
