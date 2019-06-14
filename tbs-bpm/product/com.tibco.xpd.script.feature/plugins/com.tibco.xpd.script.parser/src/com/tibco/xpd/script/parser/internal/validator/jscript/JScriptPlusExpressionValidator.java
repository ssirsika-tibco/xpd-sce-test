/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.internal.client.IDataTypeMapper;
import com.tibco.xpd.script.model.internal.jscript.IJScriptDataTypeMapper;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;

import antlr.Token;
import antlr.collections.AST;

/**
 * @author mtorres
 * 
 *         ExpressionValidator class that handles the validation of the plus
 *         expression ie: field + field2
 * 
 */
public class JScriptPlusExpressionValidator
        extends AbstractExpressionValidator {
    @Override
    public IValidateResult evaluate(IExpr expression) {
        IScriptRelevantData returnDataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false,
                        null,
                        null);
        if (null != expression) {
            Object expr = expression.getExpr();
            Object token = expression.getToken();
            if (expr instanceof AST) {
                AST astExpression = (AST) expr;
                switch (astExpression.getType()) {
                case JScriptTokenTypes.PLUS:
                case JScriptTokenTypes.PLUS_ASSIGN:
                    AST lhsExpression = astExpression.getFirstChild();
                    IScriptRelevantData lhsDataType = null;
                    IScriptRelevantData rhsDataType = null;
                    if (null != lhsExpression) {
                        // Evaluate LHS expression
                        IValidateResult evaluateLHS =
                                delegateEvaluateExpression(lhsExpression,
                                        token);
                        if (null != evaluateLHS) {
                            lhsDataType = evaluateLHS.getType();
                        }
                        // Get RHS Expression
                        AST rhsExpression = lhsExpression.getNextSibling();
                        if (null != rhsExpression) {
                            // Evaluate RHS expression
                            IValidateResult evaluateRHS =
                                    delegateEvaluateExpression(rhsExpression,
                                            token);
                            if (null != evaluateRHS) {
                                rhsDataType = evaluateRHS.getType();
                            }
                        }
                    }
                    // Check if the types for lhs and rhs of the plus
                    // expressions are correct
                    IScriptRelevantData evaluatePlusOperator =
                            evaluatePlusOperator((Token) token,
                                    lhsDataType,
                                    rhsDataType,
                                    astExpression.getType());
                    if (evaluatePlusOperator != null) {
                        returnDataType = evaluatePlusOperator;
                    }
                    break;
                default:
                    break;
                }

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

    /**
     * @param token
     * @param lhsDataType
     * @param rhsDataType
     * @param operationType
     * @return
     */
    private IScriptRelevantData evaluatePlusOperator(Token token,
            IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType,
            int operationType) {
        IScriptRelevantData dataType =
                getPlusOperatorType(lhsDataType, rhsDataType);
        String lhsStrType = JsConsts.UNDEFINED_DATA_TYPE;
        if (lhsDataType != null && lhsDataType.getType() != null) {
            lhsStrType = lhsDataType.getType();
        }
        String rhsStrType = JsConsts.UNDEFINED_DATA_TYPE;
        if (rhsDataType != null && rhsDataType.getType() != null) {
            rhsStrType = rhsDataType.getType();
        }
        if (lhsStrType == null || rhsStrType == null
                || lhsStrType.equals(JsConsts.UNDEFINED_DATA_TYPE)
                || rhsStrType.equals(JsConsts.UNDEFINED_DATA_TYPE)) {
            String errorMessage =
                    Messages.ExpressionValidator_Operation_Undefined;
            List<String> additionalAttributes = new ArrayList<String>();
            // additionalAttributes.add(getDestinationName(getInfoObject()));
            addWarningMessage(token, errorMessage, additionalAttributes);
            return dataType;
        }
        // XPD-5355: Complex Types are not allowed on the LHS of the
        // PLUS_Assignment operator.
        if (!isValidPlusOperator(lhsDataType, rhsDataType)
                || (JScriptTokenTypes.PLUS_ASSIGN == operationType
                        && JScriptUtils.isDynamicComplexType(lhsDataType))) {
            String errorMessage = Messages.ExpressionValidator_PlusOperation;
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(parseTypeMessage(lhsDataType));
            additionalAttributes.add(parseTypeMessage(rhsDataType));
            // additionalAttributes.add(getDestinationName(getInfoObject()));
            addErrorMessage(token, errorMessage, additionalAttributes);
            return dataType;
        }
        if (shouldWarnAboutConcat(lhsStrType, rhsStrType)) {
            String errorMessage =
                    Messages.ExpressionValidator_PlusOperation_Concatenation;
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(parseTypeMessage(lhsDataType));
            additionalAttributes.add(parseTypeMessage(rhsDataType));
            // additionalAttributes.add(getDestinationName(getInfoObject()));
            addWarningMessage(token, errorMessage, additionalAttributes);
            return dataType;
        }
        return dataType;
    }

    protected IScriptRelevantData getPlusOperatorType(
            IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType) {
        IScriptRelevantData dataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false,
                        null,
                        null);
        if (lhsDataType != null && lhsDataType.getType() != null
                && rhsDataType != null && rhsDataType.getType() != null) {
            String lhsStrType = convertSpecificToGenericType(lhsDataType);
            String rhsStrType = convertSpecificToGenericType(rhsDataType);
            String newType = JsConsts.TEXT;
            if (isNumericDataType(lhsStrType)
                    && isNumericDataType(rhsStrType)) {
                newType = getBiggerNumericDataType(lhsDataType, rhsDataType);
            }
            dataType = createScriptRelevantData(newType,
                    newType,
                    false,
                    null,
                    null);
        }
        return dataType;
    }

    protected boolean isValidPlusOperator(IScriptRelevantData lhsDataType,
            IScriptRelevantData rhsDataType) {
        if (lhsDataType != null && lhsDataType.getType() != null
                && rhsDataType != null && rhsDataType.getType() != null) {
            if (JScriptUtils.isXsdDerivedObject(lhsDataType)
                    || JScriptUtils.isXsdDerivedObject(rhsDataType)) {
                return false;
            }
            String lhsStrType = convertSpecificToGenericType(lhsDataType);
            String rhsTypeStr = convertSpecificToGenericType(rhsDataType);
            Map<String, Set<String>> inCompatiblePlusOperatorTypesMap = null;

            IDataTypeMapper dataTypeMapper = getDataTypeMapper(getInfoObject());
            if (dataTypeMapper instanceof IJScriptDataTypeMapper) {
                IJScriptDataTypeMapper jsDataTypeMapper =
                        (IJScriptDataTypeMapper) dataTypeMapper;
                inCompatiblePlusOperatorTypesMap =
                        jsDataTypeMapper.getInCompatiblePlusOperatorTypesMap();
                inCompatiblePlusOperatorTypesMap =
                        jsDataTypeMapper.convertSpecificMapToGeneric(
                                inCompatiblePlusOperatorTypesMap);
            }
            if (inCompatiblePlusOperatorTypesMap != null) {
                Set<String> compatiblePlusOperatorSet =
                        inCompatiblePlusOperatorTypesMap.get(lhsStrType);
                if (compatiblePlusOperatorSet != null
                        && compatiblePlusOperatorSet.contains(rhsTypeStr)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean shouldWarnAboutConcat(String lhsStrType,
            String rhsStrType) {
        if (lhsStrType != null && rhsStrType != null) {
            if (lhsStrType.equals(JsConsts.DATE)
                    || lhsStrType.equals(JsConsts.BOM_DATE)
                    || lhsStrType.equals(JsConsts.TIME)
                    || lhsStrType.equals(JsConsts.DATETIME)
                    || lhsStrType.equals(JsConsts.DATETIMETZ)
                    || lhsStrType.equals(JsConsts.DURATION)
                    || rhsStrType.equals(JsConsts.DATE)
                    || rhsStrType.equals(JsConsts.BOM_DATE)
                    || rhsStrType.equals(JsConsts.TIME)
                    || rhsStrType.equals(JsConsts.DATETIME)
                    || rhsStrType.equals(JsConsts.DATETIMETZ)
                    || rhsStrType.equals(JsConsts.DURATION)) {
                return true;
            }
        }
        return false;
    }
}
