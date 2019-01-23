/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
public class JScriptArrayDeclaratorExpressionValidator extends
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
            Object token = expression.getToken();
            if (expr instanceof AST && token instanceof Token) {
                AST astExpression = (AST) expr;
                switch (astExpression.getType()) {
                case JScriptTokenTypes.ARRAY_DECLARATOR:
                    addErrorMessage((Token) token,
                            Messages.ExpressionValidator_Array_Initialisation_Not_Supported);
                    break;
                default:
                    break;
                }
            }
        }
        IValidateResult result =
                updateResult(expression,
                        returnDataType,
                        JScriptUtils
                        .getCurrentGenericContext(returnDataType));
        return result;
    }

}
