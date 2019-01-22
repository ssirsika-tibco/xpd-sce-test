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
public class JScriptConditionalExprRefactor extends AbstractExpressionRefactor {


    @Override
    public RefactorResult evaluate(IExpr expresion) {
        if (expresion != null) {
            Object expr = expresion.getExpr();
            Object token = expresion.getToken();
            if (expr instanceof AST && token instanceof Token) {
                AST astExpr = (AST) expr;
                Token antlrToken = (Token) token;
                if (astExpr != null && antlrToken != null) {
                    if (astExpr.getType() == JScriptTokenTypes.EXPR) {
                        return delegateEvaluateExpression(astExpr
                                .getFirstChild(), token);
                    }
                }
            }
        }
        return null;
    }

}
