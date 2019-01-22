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
 * @author mtorres
 *  
 */
public class JScriptMethodCallRefactor extends AbstractExpressionRefactor {

    @Override
    public RefactorResult evaluate(IExpr expression) {
        if (expression != null) {
            Object expr = expression.getExpr();
            Object token = expression.getToken();
            if (expr instanceof AST && token instanceof Token) {
                AST astExpression = (AST) expr;
                switch (astExpression.getType()) {
                case JScriptTokenTypes.METHOD_CALL:
                    AST firstChildExpr = astExpression.getFirstChild();
                    // Delegate evaluation
                    RefactorResult result =
                            delegateEvaluateExpression(firstChildExpr,
                                    (Token) token);
                    if (result != null) {
                        getRefactorResultList().add(result);
                    }
                    return result;
                default:
                    break;
                }
            }
        }
        return null;
    }



}
