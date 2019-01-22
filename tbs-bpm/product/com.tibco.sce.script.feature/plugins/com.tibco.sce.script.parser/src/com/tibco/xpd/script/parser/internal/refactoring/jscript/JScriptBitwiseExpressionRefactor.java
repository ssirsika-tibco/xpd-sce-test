/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.refactoring.jscript;

import antlr.collections.AST;

import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.refactoring.RefactorResult;

/**
 * 
 * @author mtorres
 */
public class JScriptBitwiseExpressionRefactor extends
        AbstractExpressionRefactor {

    @Override
    public RefactorResult evaluate(IExpr expression) {
        if (null != expression) {
            Object expr = expression.getExpr();
            Object token = expression.getToken();
            if (expr instanceof AST) {
                AST astExpression = (AST) expr;
                switch (astExpression.getType()) {
                case JScriptTokenTypes.SR: // >>
                case JScriptTokenTypes.SR_ASSIGN: // >>=
                case JScriptTokenTypes.BSR: // >>>
                case JScriptTokenTypes.BSR_ASSIGN: // >>>=
                case JScriptTokenTypes.SL: // <<
                case JScriptTokenTypes.SL_ASSIGN: // <<=
                case JScriptTokenTypes.BOR_ASSIGN: // |=
                case JScriptTokenTypes.BAND_ASSIGN: // &=
                case JScriptTokenTypes.BOR: // |
                case JScriptTokenTypes.BAND: // &
                case JScriptTokenTypes.BNOT:
                    AST lhsExpression = astExpression.getFirstChild();
                    if (null != lhsExpression) {
                        // Evaluate LHS expression
                        RefactorResult evaluateLHS =
                                delegateEvaluateExpression(lhsExpression, token);
                        if (evaluateLHS != null) {
                            getRefactorResultList().add(evaluateLHS);
                        }
                        // Get RHS Expression
                        AST rhsExpression = lhsExpression.getNextSibling();
                        if (null != rhsExpression) {
                            // Evaluate RHS expression
                            RefactorResult evaluateRHS =
                                    delegateEvaluateExpression(rhsExpression,
                                            token);
                            if (evaluateRHS != null) {
                                getRefactorResultList().add(evaluateRHS);
                            }
                        }
                    }
                    break;

                default:
                    break;
                }
            }
        }
        return null;
    }
}
