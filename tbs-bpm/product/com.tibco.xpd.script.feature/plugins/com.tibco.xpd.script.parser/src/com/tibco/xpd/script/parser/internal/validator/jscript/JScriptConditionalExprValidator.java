/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
/**
 * 
 * Expression validator for the validation of conditional expressions
 * 
 * @author mtorres
 */
public class JScriptConditionalExprValidator extends AbstractExpressionValidator {

	public void validate(AST expressionAST, Token token) {
        if (expressionAST == null) {
            return;
        }

        if (expressionAST.getType() == JScriptTokenTypes.EXPR) {
            AST exprChildAST = expressionAST.getFirstChild();
            IValidateResult evaluateExpression =
                    delegateEvaluateExpression(exprChildAST, token);
            if (evaluateExpression != null) {
                IScriptRelevantData type = evaluateExpression.getType();
                if (type != null && type.getType() != null) {
                    if (JsConsts.UNDEFINED_DATA_TYPE.equalsIgnoreCase(type
                            .getType())) {
                        String errorMessage =
                                Messages.JScriptConditionalExprValidator_Unable_to_validateConditionalExpression;
                        addWarningMessage(token, errorMessage);
                    } else if (!JsConsts.BOOLEAN.equalsIgnoreCase(type
                            .getType())) {
                        String errorMessage =
                                Messages.JScriptConditionalExprValidator_ConditionalExpressionExpected;
                        addErrorMessage(token, errorMessage);
                    }
                }
            }
        }
    }

}
