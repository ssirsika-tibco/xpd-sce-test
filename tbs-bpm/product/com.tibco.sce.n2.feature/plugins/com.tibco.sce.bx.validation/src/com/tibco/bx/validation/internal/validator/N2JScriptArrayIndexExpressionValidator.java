/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.bx.validation.internal.validator;

import java.util.ArrayList;
import java.util.List;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.bx.validation.validator.N2ExpressionValidator;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;

/**
 * @author mtorres
 * 
 *         ExpressionValidator class that handles the validation of the
 *         expression inside the index of an array ie:
 *         arrField[EXPRESSION_TO_VALIDATE]
 * 
 */
public class N2JScriptArrayIndexExpressionValidator extends
        N2ExpressionValidator {

    @Override
    public IValidateResult evaluate(IExpr expression) {
        IScriptRelevantData returnDataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false,
                        null,
                        null);
        // XPD-7432 Exclude REST data mapper as scripts may need to loop through
        // arrays
        if (expression != null
                && !ProcessScriptContextConstants.DATA_MAPPER_REST_MAPPING_SCRIPTS
                        .equals(getInfoObject().getScriptType())) {
            Object expr = expression.getExpr();
            Object token = expression.getToken();
            if (expr instanceof AST && token instanceof Token) {
                AST astExpression = (AST) expr;
                switch (astExpression.getType()) {
                case JScriptTokenTypes.INDEX_OP:
                    List<String> additionalAttributes = new ArrayList<String>();
                    additionalAttributes
                            .add(getDestinationName(getInfoObject()));
                    addErrorMessage((Token) token,
                            Messages.N2ExpressionValidator_ArrayIndexNotSupported,
                            additionalAttributes);
                    break;
                default:
                    break;
                }
            }
        }
        IValidateResult result =
                updateResult(expression,
                        returnDataType,
                        createGenericContext(returnDataType,
                                isGenericContextArray(returnDataType,
                                        returnDataType)));
        return result;
    }

}
