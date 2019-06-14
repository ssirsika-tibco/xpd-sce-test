/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;

import antlr.collections.AST;

/**
 * 
 * Expression validator for the validation of numeric expressions
 * 
 * @author mtorres
 */
public class JScriptNumericExpressionValidator
        extends AbstractExpressionValidator {

    /**
     * Validates an expression that contains a number ie: 2
     * 
     **/
    @Override
    public IValidateResult evaluate(IExpr expression) {
        IScriptRelevantData returnDataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false,
                        null,
                        null);
        if (expression != null) {
            Object expr = expression.getExpr();
            if (expr instanceof AST) {
                AST astExpression = (AST) expr;
                switch (astExpression.getType()) {
                case JScriptTokenTypes.NUM_INT:
                    returnDataType = createScriptRelevantData(JsConsts.INTEGER,
                            JsConsts.INTEGER,
                            false,
                            null,
                            true);
                    break;
                case JScriptTokenTypes.NUM_FLOAT:
                    returnDataType = createScriptRelevantData(JsConsts.DECIMAL,
                            JsConsts.DECIMAL,
                            false,
                            null,
                            true);
                    break;
                case JScriptTokenTypes.NUM_LONG:
                    returnDataType = createScriptRelevantData(JsConsts.INTEGER,
                            JsConsts.INTEGER,
                            false,
                            null,
                            true);
                    break;
                case JScriptTokenTypes.NUM_DOUBLE:
                    returnDataType = createScriptRelevantData(JsConsts.DECIMAL,
                            JsConsts.DECIMAL,
                            false,
                            null,
                            true);
                    break;
                case JScriptTokenTypes.NUMBER:
                    returnDataType = createScriptRelevantData(JsConsts.INTEGER,
                            JsConsts.INTEGER,
                            false,
                            null,
                            true);
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
        IValidateResult result = updateResult(expression,
                returnDataType,
                createGenericContext(returnDataType,
                        isGenericContextArray(returnDataType, returnDataType)));
        return result;
    }

}
