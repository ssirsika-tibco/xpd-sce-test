/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.refactoring.jscript;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.refactoring.RefactorResult;
/**
 * 
 * @author mtorres
 */
public class JScriptUnaryExpressionRefactor extends
        AbstractExpressionRefactor {

    @Override
    public RefactorResult evaluate(IExpr expression) {
        if (null != expression) {
            Object expr = expression.getExpr();
            Object token = expression.getToken();
            if (expr instanceof AST) {
                AST astExpression = (AST) expr;
                switch (astExpression.getType()) {
                case JScriptTokenTypes.UNARY_MINUS:
                case JScriptTokenTypes.UNARY_PLUS:
                case JScriptTokenTypes.POST_DEC:
                case JScriptTokenTypes.POST_INC:
                case JScriptTokenTypes.INC:
                case JScriptTokenTypes.DEC:
                case JScriptTokenTypes.LNOT:
                    RefactorResult evaluateNumericUnaryOperator =
                            evaluateUnaryOperator(astExpression, (Token) token);
                    return evaluateNumericUnaryOperator;
                default:
                    break;
                }
            }
        }
        return null;
    }

    /**
     * @param exprAST
     * @param token
     * @return
     */
    private RefactorResult evaluateUnaryOperator(AST exprAST, Token token) {
        AST fcAST = exprAST.getFirstChild();
        if (fcAST.getType() == JScriptTokenTypes.NUM_INT
                || fcAST.getType() == JScriptTokenTypes.NUM_LONG
                || fcAST.getType() == JScriptTokenTypes.NUM_FLOAT
                || fcAST.getType() == JScriptTokenTypes.NUM_DOUBLE) {
            RefactorResult delegateEvaluateExpression =
                    delegateEvaluateExpression(fcAST, token);
            if (delegateEvaluateExpression != null) {
                getRefactorResultList().add(delegateEvaluateExpression);
            }
            return delegateEvaluateExpression;
        } else {
            RefactorResult delegateEvaluateExpression =
                    delegateEvaluateExpression(fcAST, token);
            if (delegateEvaluateExpression != null) {
                getRefactorResultList().add(delegateEvaluateExpression);
            }
            return delegateEvaluateExpression;
        }
    }

}
