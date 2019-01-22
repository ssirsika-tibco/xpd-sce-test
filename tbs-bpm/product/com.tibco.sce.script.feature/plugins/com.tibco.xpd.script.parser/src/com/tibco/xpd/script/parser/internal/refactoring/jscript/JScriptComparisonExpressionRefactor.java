/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.refactoring.jscript;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.refactoring.RefactorResult;

/**
 * @author mtorres
 * 
 */
public class JScriptComparisonExpressionRefactor extends
        AbstractExpressionRefactor {

    /**
     * Refactors an equality expr Cases: 1.- lhs > rhs 2.- lhs <= rhs
     * 
     **/
    @Override
    public RefactorResult evaluate(IExpr expression) {
        if (expression != null) {
            Object expr = expression.getExpr();
            Object token = expression.getToken();
            if (expr instanceof AST && token instanceof Token) {
                AST astExpression = (AST) expr;
                AST lhsExpression = astExpression.getFirstChild();
                if (lhsExpression != null) {
                    // Evaluate LHS expression
                    RefactorResult evaluateLHS =
                            delegateEvaluateExpression(lhsExpression, token);
                    if (evaluateLHS != null) {
                        getRefactorResultList().add(evaluateLHS);
                    }
                    // Get RHS expression
                    AST rhsExpression = lhsExpression.getNextSibling();
                    if (rhsExpression != null) {
                        // Evaluate LHS expression
                        RefactorResult evaluateRHS =
                                delegateEvaluateExpression(rhsExpression, token);
                        if (evaluateRHS != null) {
                            getRefactorResultList().add(evaluateRHS);
                        }
                    }
                }
            }
        }
        return null;
    }
}
