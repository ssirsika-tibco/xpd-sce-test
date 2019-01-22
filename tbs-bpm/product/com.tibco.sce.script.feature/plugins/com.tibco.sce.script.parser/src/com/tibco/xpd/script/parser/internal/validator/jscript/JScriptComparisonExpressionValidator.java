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
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;

/**
 * @author mtorres
 * 
 *         ExpressionValidator class that handles the validation of the
 *         comparison expression ie: arrField > 1
 * 
 */
public class JScriptComparisonExpressionValidator extends
        AbstractExpressionValidator {

    /**
     * Validates an equality expr Cases: 1.- lhs > rhs 2.- lhs <= rhs
     * 
     **/
    @Override
    public IValidateResult evaluate(IExpr expression) {
        if (expression != null) {
            Object expr = expression.getExpr();
            Object token = expression.getToken();
            if (expr instanceof AST && token instanceof Token) {
                AST astExpression = (AST) expr;
                AST lhsExpression = astExpression.getFirstChild();
                IScriptRelevantData lhsDataType = null;
                IScriptRelevantData rhsDataType = null;
                if (lhsExpression != null) {
                    // Evaluate LHS expression
                    IValidateResult evaluateLHS =
                            delegateEvaluateExpression(lhsExpression, token);
                    if (evaluateLHS != null) {
                        lhsDataType = evaluateLHS.getType();
                    }
                    // Get RHS expression
                    AST rhsExpression = lhsExpression.getNextSibling();
                    if (rhsExpression != null) {
                        // Evaluate LHS expression
                        IValidateResult evaluateRHS =
                                delegateEvaluateExpression(rhsExpression, token);
                        if (evaluateRHS != null) {
                            rhsDataType = evaluateRHS.getType();
                        }
                    }
                }
                // Check if the types for lhs and rhs of the equality
                // expressions are correct
                IScriptRelevantData returnDataType =
                        evaluateComparisonOperator((Token) token,
                                lhsDataType,
                                rhsDataType);
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
        return null;
    }

    /**
     * This method can deal with >,>=,<,<= operators.
     * 
     * @param operator
     * @param lhsType
     * @param rhsType
     * @param token
     * @return
     */
    protected IScriptRelevantData evaluateComparisonOperator(Token token,
            IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType) {
        IScriptRelevantData dataType =
                getComparisonOperatorType(lhsDataType, rhsDataType);
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
            addWarningMessage(token, errorMessage);
        } else if (!isValidComparisonOperator(lhsDataType, rhsDataType)) {
            String errorMessage =
                    Messages.ExpressionValidator_ComparisonOperationBetween;
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(parseTypeMessage(lhsDataType));
            additionalAttributes.add(parseTypeMessage(rhsDataType));
            addErrorMessage(token, errorMessage, additionalAttributes);
        }
        return dataType;
    }

    protected IScriptRelevantData getComparisonOperatorType(
            IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType) {
        IScriptRelevantData returnDataType =
            createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                    JsConsts.UNDEFINED_DATA_TYPE,
                    false, null, null);
            if (lhsDataType != null && lhsDataType.getType() != null
                    && rhsDataType != null && rhsDataType.getType() != null) {
                returnDataType = createScriptRelevantData(JsConsts.BOOLEAN,
                                        JsConsts.BOOLEAN,
                                        false, null, null);
            }
            return returnDataType;
    }

    protected boolean isValidComparisonOperator(
            IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType) {
        if (lhsDataType != null && lhsDataType.getType() != null
                && rhsDataType != null && rhsDataType.getType() != null) {
            if (JScriptUtils.isXsdDerivedObject(lhsDataType)
                    || JScriptUtils.isXsdDerivedObject(rhsDataType)) {
                return false;
            }
            String lhsStrType = convertSpecificToGenericType(lhsDataType);
            String rhsTypeStr = convertSpecificToGenericType(rhsDataType);
            Map<String, Set<String>> inCompatibleComparisonOperatorTypesMap =
                    null;
            IDataTypeMapper dataTypeMapper = getDataTypeMapper(getInfoObject());
            if (dataTypeMapper instanceof IJScriptDataTypeMapper) {
                IJScriptDataTypeMapper jsDataTypeMapper =
                        (IJScriptDataTypeMapper) dataTypeMapper;
                inCompatibleComparisonOperatorTypesMap =
                        jsDataTypeMapper
                                .getInCompatibleComparisonOperatorTypesMap();
                inCompatibleComparisonOperatorTypesMap =
                    jsDataTypeMapper
                            .convertSpecificMapToGeneric(inCompatibleComparisonOperatorTypesMap);
            }
            if (inCompatibleComparisonOperatorTypesMap != null) {
                Set<String> inCompatibleComparisonOperatorSet =
                        inCompatibleComparisonOperatorTypesMap.get(lhsStrType);
                if (inCompatibleComparisonOperatorSet != null
                        && inCompatibleComparisonOperatorSet
                                .contains(rhsTypeStr)) {
                    return false;
                }
            }
        }
        return true;
    }

}
