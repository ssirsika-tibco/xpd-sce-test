/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import java.util.ArrayList;
import java.util.List;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;

/**
 * @author mtorres
 * 
 *         ExpressionValidator class that handles the validation of an
 *         identifier ie: field;
 * 
 */
public class JScriptIdentifierExpressionValidator extends
        AbstractExpressionValidator {

    @Override
    public IValidateResult evaluate(IExpr expression) {
        IScriptRelevantData returnDataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false, null, null);
        IScriptRelevantData newGenericContext = null;
        if (expression != null) {
            Object expr = expression.getExpr();
            Object token = expression.getToken();
            if (expr instanceof AST && token instanceof Token) {
                AST astExpression = (AST) expr;
                switch (astExpression.getType()) {
                case JScriptTokenTypes.IDENT:
                    String identifierText = astExpression.getText();
                    if (identifierText != null) {
                        IScriptRelevantData identifierScriptRelevantDataType =
                                JScriptUtils
                                .getIdentifierScriptRelevantDataType(identifierText,
                                                getSupportedJsClasses(getInfoObject()),
                                                getSupportedGlobalProperties(getInfoObject()),
                                                getSupportedScriptRelevantDataMap(getInfoObject()),
                                                getLocalVariablesMap(getInfoObject()),
                                                getLocalMethodsMap(getInfoObject()),
                                                getScriptRelevantDataFactory(getInfoObject()),
                                                null);
                        if (identifierScriptRelevantDataType == null) {
                            String errorMessage =
                                    Messages.JsValidationStrategy_Variable_Undefined;
                            List<String> additionalAttributes =
                                    new ArrayList<String>();
                            additionalAttributes.add(identifierText);
                            addErrorMessage((Token) token,
                                    errorMessage,
                                    additionalAttributes);
                        } else {
                            returnDataType = identifierScriptRelevantDataType;
                            addResolutionTypes(returnDataType,
                                    returnDataType.isArray(),
                                    JScriptUtils.getCurrentGenericContext(returnDataType));
                            if(isLocalVariable(identifierText, getInfoObject())){                                
                                newGenericContext = JScriptUtils.getCurrentGenericContext(returnDataType);
                            } else {
                                newGenericContext = returnDataType;
                            }
                        }
                    } else {
                        logUnexpectedExpressionValidatorProblem();
                    }
                    break;
                default:
                    break;
                }
            }
        }
        IValidateResult result =
                updateResult(expression,
                        returnDataType,
                        createGenericContext(newGenericContext,
                                isGenericContextArray(returnDataType,
                                        newGenericContext)));
        return result;
    }

}
