/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.internal.client.IDataTypeMapper;
import com.tibco.xpd.script.model.internal.jscript.IJScriptDataTypeMapper;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
/**
 * 
 * Expression validator for the validation of multiplication expressions
 * 
 * @author mtorres
 */
public class JScriptMultiplicationExpressionValidator extends
        AbstractExpressionValidator {

    @Override
    public IValidateResult evaluate(IExpr expression) {
        IScriptRelevantData returnDataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false, null, null);
        if (null != expression) {
            Object expr = expression.getExpr();
            Object token = expression.getToken();
            if (expr instanceof AST) {
                AST astExpression = (AST) expr;
                switch (astExpression.getType()) {
                case JScriptTokenTypes.STAR:
                case JScriptTokenTypes.STAR_ASSIGN:
                    IScriptRelevantData lhsDataType = null;
                    IScriptRelevantData rhsDataType = null;
                    AST lhsExpression = astExpression.getFirstChild();
                    if (null != lhsExpression) {
                        // Evaluate LHS expression
                        IValidateResult evaluateLHS =
                                delegateEvaluateExpression(lhsExpression, token);
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
                    IScriptRelevantData evaluateMultiplicationOperator =
                            evaluateMultiplicationOperator((Token) token,
                                    lhsDataType,
                                    rhsDataType);
                    if (evaluateMultiplicationOperator != null) {
                        returnDataType = evaluateMultiplicationOperator;
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
                        JScriptUtils
                        .getCurrentGenericContext(returnDataType));
        return result;
    }

    /**
     * @param token
     * @param lhsDataType
     * @param rhsDataType
     * @return
     */
    private IScriptRelevantData evaluateMultiplicationOperator(Token token,
            IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType) {
        IScriptRelevantData dataType =
                getMultiplicationOperatorType(lhsDataType, rhsDataType);
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
        } else if (!isValidMultiplicationOperator(lhsDataType, rhsDataType)) {
            String errorMessage =
                    Messages.ExpressionValidator_MultiplicationOperationBetween;
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(parseTypeMessage(lhsDataType));
            additionalAttributes.add(parseTypeMessage(rhsDataType));
            // additionalAttributes.add(getDestinationName(getInfoObject()));
            addErrorMessage(token, errorMessage, additionalAttributes);
        }
        return dataType;
    }

    protected IScriptRelevantData getMultiplicationOperatorType(
            IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType) {
        IScriptRelevantData dataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false,
                        null, null);
        if (lhsDataType != null && lhsDataType.getType() != null
                && rhsDataType != null && rhsDataType.getType() != null) {
            String lhsStrType = lhsDataType.getType();
            dataType =
                    createScriptRelevantData(lhsStrType,
                            lhsStrType,
                            false,
                            JScriptUtils.getCurrentGenericContext(lhsDataType), JScriptUtils.getExtendedInfo(lhsDataType));
        }
        return dataType;
    }

    protected boolean isValidMultiplicationOperator(
            IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType) {
        if (lhsDataType != null && lhsDataType.getType() != null
                && rhsDataType != null && rhsDataType.getType() != null) {
            if (JScriptUtils.isXsdDerivedObject(lhsDataType)
                    || JScriptUtils.isXsdDerivedObject(rhsDataType)) {
                return false;
            }
            String lhsStrType =
                    convertSpecificToGenericType(lhsDataType);
            String rhsTypeStr =
                    convertSpecificToGenericType(rhsDataType);
            Map<String, Set<String>> compatibleMultiplicationOperatorTypesMap =
                    null;
            IDataTypeMapper dataTypeMapper = getDataTypeMapper(getInfoObject());
            if (dataTypeMapper instanceof IJScriptDataTypeMapper) {
                IJScriptDataTypeMapper jsDataTypeMapper =
                        (IJScriptDataTypeMapper) dataTypeMapper;
                compatibleMultiplicationOperatorTypesMap =
                        jsDataTypeMapper
                                .getCompatibleMultiplicationOperatorTypesMap();
                compatibleMultiplicationOperatorTypesMap =
                        jsDataTypeMapper
                                .convertSpecificMapToGeneric(compatibleMultiplicationOperatorTypesMap);
            }
            if (compatibleMultiplicationOperatorTypesMap != null) {
                Set<String> compatibleMultiplicationOperatorSet =
                        compatibleMultiplicationOperatorTypesMap
                                .get(lhsStrType);
                if (compatibleMultiplicationOperatorSet != null
                        && compatibleMultiplicationOperatorSet
                                .contains(rhsTypeStr)) {
                    return true;
                }
            }
        }
        return false;
    }
}
