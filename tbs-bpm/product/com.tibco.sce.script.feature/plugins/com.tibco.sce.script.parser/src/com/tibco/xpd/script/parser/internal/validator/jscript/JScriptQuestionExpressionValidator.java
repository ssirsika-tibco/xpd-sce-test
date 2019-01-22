/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import antlr.collections.AST;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
/**
 * 
 * Expression validator for the validation of question expressions
 * 
 * @author mtorres
 */
public class JScriptQuestionExpressionValidator extends
        AbstractExpressionValidator {

    @Override
    public IValidateResult evaluate(IExpr expression) {
        IScriptRelevantData returnDataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false,
                        null, null);
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
        IValidateResult result =
                updateResult(expression,
                        returnDataType,
                        createGenericContext(returnDataType, isGenericContextArray(returnDataType, returnDataType)));
        return result;
    }

}
