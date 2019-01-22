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
 * Expression validator for the validation of bitwise expressions
 * 
 * @author mtorres
 */
public class JScriptBitwiseExpressionValidator extends
        AbstractExpressionValidator {

    @Override
    public IValidateResult evaluate(IExpr expression) {
        IScriptRelevantData returnDataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false,
                        null, null);
        if (null != expression) {
            Object expr = expression.getExpr();
            Object token = expression.getToken();
            if (expr instanceof AST) {
                AST astExpression = (AST) expr;
                // sample test data used to test this validator
                /*
                 * x = 14 >> 2; x = 15; x >>= 1; x = 13 >>> 1; x >>>= 2; x = 2
                 * << 2; x = 3; x <<= 2; x |= 5; x = 6; x &= 12;
                 */
                // get the token name from the expression to show in the
                // error/warning
                // message
                String tokenName = getTokenName(astExpression);

                switch (astExpression.getType()) {
                case JScriptTokenTypes.SR: // >>
                case JScriptTokenTypes.SR_ASSIGN: // >>=
                case JScriptTokenTypes.BSR: // >>>
                case JScriptTokenTypes.BSR_ASSIGN: // >>>=
                case JScriptTokenTypes.SL: // <<
                case JScriptTokenTypes.SL_ASSIGN: // <<=
                case JScriptTokenTypes.BOR_ASSIGN: // |=
                case JScriptTokenTypes.BAND_ASSIGN: // &=
                case JScriptTokenTypes.BOR: // |
                case JScriptTokenTypes.BAND: // &

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
                    // Check if the types for lhs and rhs of the division
                    // expressions are correct
                    IScriptRelevantData evaluateBitwiseOperator =
                            evaluateBitwiseOperator((Token) token,
                                    lhsDataType,
                                    rhsDataType,
                                    tokenName);
                    if (evaluateBitwiseOperator != null) {
                        returnDataType = evaluateBitwiseOperator;
                    }
                    break;
                case JScriptTokenTypes.BNOT:
                    IScriptRelevantData complementorDataType = null;
                    AST childExpression = astExpression.getFirstChild();
                    if (null != childExpression) {
                        // Evaluate Bitwise expression
                        IValidateResult evaluate =
                                delegateEvaluateExpression(childExpression,
                                        token);
                        if (null != childExpression) {
                            complementorDataType = evaluate.getType();
                        }
                    }
                    // Check if the types for lhs and rhs of the division
                    // expressions are correct
                    IScriptRelevantData evaluateBitwiseComplementorOperator =
                            evaluateBitwiseComplementorOperator((Token) token,
                                    complementorDataType,
                                    tokenName);
                    if (evaluateBitwiseComplementorOperator != null) {
                        returnDataType = evaluateBitwiseComplementorOperator;
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
        IValidateResult result =
                updateResult(expression,
                        returnDataType,
                        createGenericContext(returnDataType,
                                isGenericContextArray(returnDataType,
                                        returnDataType)));
        return result;
    }

    /**
     * @param astExpression
     */
    private String getTokenName(AST astExpression) {
        String tokenName = null;
        switch (astExpression.getType()) {
        case JScriptTokenTypes.SR: // >>
            tokenName = JsConsts.RIGHT_SHIFT;
            break;
        case JScriptTokenTypes.SR_ASSIGN: // >>=
            tokenName = JsConsts.RIGHT_SHIFT_ASSIGN;
            break;
        case JScriptTokenTypes.BSR: // >>>
            tokenName = JsConsts.ZERO_FILL_RIGHT_SHIFT;
            break;
        case JScriptTokenTypes.BSR_ASSIGN: // >>>=
            tokenName = JsConsts.ZERO_FILL_RIGHT_SHIFT_ASSIGN;
            break;
        case JScriptTokenTypes.SL: // <<
            tokenName = JsConsts.LEFT_SHIFT;
            break;
        case JScriptTokenTypes.SL_ASSIGN: // <<=
            tokenName = JsConsts.LEFT_SHIFT_ASSIGN;
            break;
        case JScriptTokenTypes.BOR_ASSIGN: // |=
            tokenName = JsConsts.OR_ASSIGN;
            break;
        case JScriptTokenTypes.BAND_ASSIGN: // &=
            tokenName = JsConsts.AND_ASSIGN;
            break;
        case JScriptTokenTypes.BOR: // |
            tokenName = JsConsts.OR;
            break;
        case JScriptTokenTypes.BAND: // &
            tokenName = JsConsts.AND;
            break;
        case JScriptTokenTypes.BNOT:
            tokenName = JsConsts.NOT;
            break;
        }
        return tokenName;
    }

    /**
     * @param token
     * @param lhsDataType
     * @param rhsDataType
     * @param tokenName
     * @return
     */
    private IScriptRelevantData evaluateBitwiseOperator(Token token,
            IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType,
            String tokenName) {
        IScriptRelevantData dataType =
                getBitwiseOperatorType(lhsDataType, rhsDataType);
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
            // additionalAttributes
            // .add(getDisplayDestinationName(getInfoObject()));
            addWarningMessage(token, errorMessage, additionalAttributes);
        } else if (!isValidBitwiseOperator(lhsDataType, rhsDataType)) {
            String errorMessage = Messages.ExpressionValidator_BitwiseOperation;
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(parseTypeMessage(lhsDataType));
            additionalAttributes.add(parseTypeMessage(rhsDataType));
            additionalAttributes.add(tokenName);
            // additionalAttributes
            // .add(getDisplayDestinationName(getInfoObject()));
            addErrorMessage(token, errorMessage, additionalAttributes);
        }
        return dataType;

    }

    /**
     * @param token
     * @param tokenName
     * @param lhsDataType
     * @param rhsDataType
     * @return
     */
    private IScriptRelevantData evaluateBitwiseComplementorOperator(
            Token token, IScriptRelevantData complementorDataType,
            String tokenName) {
        IScriptRelevantData dataType =
                getBitwiseComplementorOperatorType(complementorDataType);
        String complementorStrType = JsConsts.UNDEFINED_DATA_TYPE;
        if (complementorDataType != null
                && complementorDataType.getType() != null) {
            complementorStrType = complementorDataType.getType();
        }
        if (complementorStrType == null
                || complementorStrType.equals(JsConsts.UNDEFINED_DATA_TYPE)) {
            String errorMessage =
                    Messages.ExpressionValidator_Operation_Undefined;
            List<String> additionalAttributes = new ArrayList<String>();
            // additionalAttributes
            // .add(getDisplayDestinationName(getInfoObject()));
            addWarningMessage(token, errorMessage, additionalAttributes);
        } else if (!isValidBitwiseComplementorOperator(complementorDataType)) {
            String errorMessage = Messages.ExpressionValidator_BitwiseOperation;
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(complementorStrType);
            additionalAttributes.add(tokenName);
            // additionalAttributes
            // .add(getDisplayDestinationName(getInfoObject()));
            addErrorMessage(token, errorMessage, additionalAttributes);
        }
        return dataType;

    }

    /**
     * @param lhsDataType
     * @param rhsDataType
     * @return
     */
    private boolean isValidBitwiseOperator(IScriptRelevantData lhsDataType,
            IScriptRelevantData rhsDataType) {
        if (lhsDataType != null && lhsDataType.getType() != null
                && rhsDataType != null && rhsDataType.getType() != null) {
            if (JScriptUtils.isXsdDerivedObject(lhsDataType)
                    || JScriptUtils.isXsdDerivedObject(rhsDataType)) {
                return false;
            }
            String lhsStrType = convertSpecificToGenericType(lhsDataType);
            String rhsTypeStr = convertSpecificToGenericType(rhsDataType);
            Map<String, Set<String>> compatibleBitwiseOperatorTypesMap = null;

            IDataTypeMapper dataTypeMapper = getDataTypeMapper(getInfoObject());
            if (dataTypeMapper instanceof IJScriptDataTypeMapper) {
                IJScriptDataTypeMapper jsDataTypeMapper =
                        (IJScriptDataTypeMapper) dataTypeMapper;
                compatibleBitwiseOperatorTypesMap =
                        jsDataTypeMapper.getCompatibleBitwiseOperatorTypesMap();
                compatibleBitwiseOperatorTypesMap =
                        jsDataTypeMapper
                                .convertSpecificMapToGeneric(compatibleBitwiseOperatorTypesMap);
            }
            if (compatibleBitwiseOperatorTypesMap != null) {
                Set<String> compatibleBitwiseOperatorSet =
                        compatibleBitwiseOperatorTypesMap.get(lhsStrType);
                if (compatibleBitwiseOperatorSet != null
                        && compatibleBitwiseOperatorSet.contains(rhsTypeStr)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param lhsDataType
     * @param rhsDataType
     * @return
     */
    private boolean isValidBitwiseComplementorOperator(
            IScriptRelevantData complementorDataType) {
        if (complementorDataType != null
                && complementorDataType.getType() != null) {
            String complementorStrType =
                    convertSpecificToGenericType(complementorDataType);
            if (complementorStrType != null
                    && complementorStrType.equals(JsConsts.INTEGER)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param lhsDataType
     * @param rhsDataType
     * @return
     */
    private IScriptRelevantData getBitwiseOperatorType(
            IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType) {
        IScriptRelevantData dataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false,
                        null, null);
        if (lhsDataType != null && lhsDataType.getType() != null
                && rhsDataType != null && rhsDataType.getType() != null) {
            dataType =
                    createScriptRelevantData(JsConsts.INTEGER,
                            JsConsts.INTEGER,
                            false,
                            null,
                            JScriptUtils.getExtendedInfo(lhsDataType));
        }
        return dataType;
    }

    /**
     * @param complementorDataType
     * @return
     */
    private IScriptRelevantData getBitwiseComplementorOperatorType(
            IScriptRelevantData complementorDataType) {
        IScriptRelevantData dataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false,
                        null, null);
        if (complementorDataType != null
                && complementorDataType.getType() != null) {
            dataType =
                    createScriptRelevantData(JsConsts.INTEGER,
                            JsConsts.INTEGER,
                            false,
                            null,
                            null);
        }
        return dataType;
    }

}
