/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.validator.jscript;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
import com.tibco.xpd.script.parser.internal.validator.jscript.AbstractExpressionValidator;

import antlr.Token;
import antlr.collections.AST;

/**
 * @author mtorres
 * 
 *         ExpressionValidator class that handles the validation of the method
 *         call expression ie: ClassName.methodCallToValidate(param1);
 * 
 */
public class JScriptMethodCallValidator extends AbstractExpressionValidator {

    @Override
    public void validate(AST methodAST, Token token) {
        // This is handled now by the Expression Validator
    }

    @Override
    public IValidateResult evaluate(IExpr expression) {
        IScriptRelevantData returnDataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false,
                        null, null);
        IScriptRelevantData genericContext = null;
        if (expression != null) {
            Object expr = expression.getExpr();
            Object token = expression.getToken();
            if (expr instanceof AST && token instanceof Token) {
                AST astExpression = (AST) expr;
                switch (astExpression.getType()) {
                case JScriptTokenTypes.METHOD_CALL:
                    AST firstChildExpr = astExpression.getFirstChild();
                    if (firstChildExpr != null
                            && firstChildExpr.getType() == JScriptTokenTypes.IDENT) {
                        // It has to be a global function
                        returnDataType =
                                processGlobalFunction(firstChildExpr,
                                        (Token) token);
                        genericContext =
                                JScriptUtils
                                        .getCurrentGenericContext(returnDataType);
                    } else {
                        // Delegate evaluation
                        IValidateResult evaluateExpression =
                                delegateEvaluateExpression(firstChildExpr,
                                        token);
                        if (evaluateExpression != null) {
                            returnDataType = evaluateExpression.getType();
                            genericContext =
                                    JScriptUtils
                                            .getCurrentGenericContext(returnDataType);
                        }
                    }
                    break;

                default:
                    break;
                }
            }
        }
        IValidateResult result =
                updateResult(expression, returnDataType, genericContext);
        return result;
    }

    protected IScriptRelevantData processGlobalFunction(AST astExpression,
            Token token) {
        IScriptRelevantData returnDataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false,
                        null, null);
        if (astExpression != null && shouldValidateGlobalFunctions(getInfoObject())) {
            String globalFunctionName = astExpression.getText();
            if (globalFunctionName != null) {
                List<JsMethod> globalMethodsWithName =
                        getGlobalMethodsWithName(globalFunctionName);
                if (globalMethodsWithName == null
                        || globalMethodsWithName.isEmpty()) {
                    // No global function found
                    String message =
                            Messages.ExpressionValidator_GlobalFunctionInvalid_For_Data_Type;
                    List<String> additionalAttributes = new ArrayList<String>();
                    additionalAttributes.add(globalFunctionName);
                    addErrorMessage(token, message, additionalAttributes);
                } else {
                    Map<String, IScriptRelevantData> parameters = null;
                    if (astExpression.getNextSibling() != null
                            && astExpression.getNextSibling().getType() == JScriptTokenTypes.ELIST) {
                        parameters =
                                resolveMethodParameters(astExpression
                                        .getNextSibling(), token);
                    }
                    int parametersNumber = 0;
                    if (parameters != null) {
                        parametersNumber = parameters.size();
                    }
                    List<JsMethod> methodsWithParamNumber =
                            getMethodsWithParamNumber(globalMethodsWithName,
                                    parametersNumber);
                    if (methodsWithParamNumber == null
                            || methodsWithParamNumber.isEmpty()) {
                        // param number don't match
                        String message =
                                Messages.ExpressionValidator_GlobalFunctionParamsNumberInvalid_For_Data_Type;
                        List<String> additionalAttributes =
                                new ArrayList<String>();
                        additionalAttributes.add(globalFunctionName);
                        addErrorMessage(token, message, additionalAttributes);
                        return null;
                    } else {
                        // Check if param types are compatible
                        List<JsMethod> methodsWithMatchingParamTypes =
                                getMethodsWithMatchingParamTypes(methodsWithParamNumber,
                                        parameters,
                                        null);
                        if (methodsWithMatchingParamTypes == null
                                || methodsWithMatchingParamTypes.isEmpty()) {
                            // param types don't match
                            String message =
                                    Messages.ExpressionValidator_GlobalFunctionParamsTypeInvalid_For_Data_Type;
                            List<String> additionalAttributes =
                                    new ArrayList<String>();
                            additionalAttributes.add(globalFunctionName);
                            additionalAttributes
                                    .add(getParameterNames(parameters));
                            addErrorMessage(token,
                                    message,
                                    additionalAttributes);
                            return null;
                        } else {
                            JsMethod mathMethod =
                                    methodsWithMatchingParamTypes.iterator()
                                            .next();
                            if (mathMethod != null) {
                                JsMethodParam returnType =
                                        mathMethod.getReturnType();
                                if (returnType != null) {
                                    IScriptRelevantData createScriptRelevantData =
                                            null;
                                    String paramDataType =
                                            JScriptUtils
                                                    .getJsMethodParamBaseDataType(returnType);
                                    if (getUmlClass(returnType) != null) {
                                        createScriptRelevantData =
                                                createUMLScriptRelevantData(paramDataType,
                                                        returnType.canRepeat(),
                                                        getUmlClass(returnType),
                                                        null,
                                                        true);
                                    } else {
                                        createScriptRelevantData =
                                                createScriptRelevantData(returnType
                                                        .getType(),
                                                        paramDataType,
                                                        returnType.canRepeat(),
                                                        null,
                                                        true);
                                    }
                                    if (createScriptRelevantData != null) {
                                        addResolutionTypes(createScriptRelevantData,
                                                createScriptRelevantData
                                                        .isArray(),
                                                null);
                                        returnDataType =
                                                createScriptRelevantData;
                                    } else {
                                        logUnexpectedExpressionValidatorProblem();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return returnDataType;
    }

}
