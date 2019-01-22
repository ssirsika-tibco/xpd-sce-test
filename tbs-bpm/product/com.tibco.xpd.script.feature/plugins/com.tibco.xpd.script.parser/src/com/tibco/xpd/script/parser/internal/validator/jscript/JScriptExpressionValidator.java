/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;

import antlr.Token;
import antlr.collections.AST;
/**
 * 
 * Expression validator for the validation of an expression
 * 
 * @author mtorres
 */
public class JScriptExpressionValidator extends
        AbstractExpressionValidator {
    
    public void validate(AST expression, Token token) {
        if (expression != null) {
            AST firstChild = expression.getFirstChild();
            delegateEvaluateExpression(firstChild, token);
        }
    }
    
    @Override
    public IValidateResult evaluate(IExpr expression) {
        IScriptRelevantData returnDataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false, null, null);
        if (expression != null) {
            Object expr = expression.getExpr();
            Object token = expression.getToken();
            if (expr instanceof AST && token instanceof Token) {
                AST astExpr = (AST) expr;
                AST firstChild = astExpr.getFirstChild();
                IValidateResult delegateEvaluateExpression =
                        delegateEvaluateExpression(firstChild, token);
                if (delegateEvaluateExpression != null
                        && delegateEvaluateExpression.getType() != null) {
                    returnDataType = delegateEvaluateExpression.getType();
                }
            }
        }
        IValidateResult result =
                updateResult(expression,
                        returnDataType,
                        JScriptUtils.getCurrentGenericContext(returnDataType));
        return result;
    }
    
}
