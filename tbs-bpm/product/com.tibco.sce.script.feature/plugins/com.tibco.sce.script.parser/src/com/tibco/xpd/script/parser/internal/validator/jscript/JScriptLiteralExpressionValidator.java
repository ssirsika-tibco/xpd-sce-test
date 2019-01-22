/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import antlr.collections.AST;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;

/**
 * @author mtorres
 * 
 *         ExpressionValidator class that handles the validation of the literal
 *         expressions
 * 
 */
public class JScriptLiteralExpressionValidator extends
        AbstractExpressionValidator {

    @Override
    public IValidateResult evaluate(IExpr expression) {
        IScriptRelevantData returnDataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false, null, null);
        if (expression != null) {
            Object expr = expression.getExpr();
            if (expr instanceof AST) {
                AST astExpression = (AST) expr;
                switch (astExpression.getType()) {
                case JScriptTokenTypes.STRING_LITERAL:
                    returnDataType =
                            createScriptRelevantData(JsConsts.STRING,
                                    JsConsts.STRING,
                                    false, null, true);
                    break;
                case JScriptTokenTypes.LITERAL_true:
                case JScriptTokenTypes.LITERAL_false:
                    returnDataType =
                            createScriptRelevantData(JsConsts.BOOLEAN,
                                    JsConsts.BOOLEAN,
                                    false, null, true);
                    break;
                case JScriptTokenTypes.LITERAL_null:
                    returnDataType =
                            createScriptRelevantData(JsConsts.NULL,
                                    JsConsts.NULL,
                                    false, null, true);
                    break;
                default:
                    break;
                }
                returnDataType = JScriptUtils.setReadOnly(returnDataType, true);
            }
        }
        addResolutionTypes(returnDataType,
                returnDataType.isArray(),
                JScriptUtils.getCurrentGenericContext(returnDataType));
        IValidateResult result =
                updateResult(expression,
                        returnDataType,
                        createGenericContext(returnDataType, isGenericContextArray(returnDataType, returnDataType)));
        return result;
    }

}
