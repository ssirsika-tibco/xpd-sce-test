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
public class JScriptQuestionExpressionRefactor extends
        AbstractExpressionRefactor {

    @Override
    public RefactorResult evaluate(IExpr expression) {
        if (expression != null) {
            Object expr = expression.getExpr();
            if (expr instanceof AST) {
                AST astExpression = (AST) expr;
                switch (astExpression.getType()) {
                case JScriptTokenTypes.QUESTION: // ?

                    break;
                default:
                    break;
                }
            }
        }
        return null;
    }

}
