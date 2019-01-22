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
 * 
 * Expression validator for the validation of equality expressions
 * 
 * @author mtorres
 */
public class JScriptEqualityExpressionValidator extends
        AbstractExpressionValidator {

    /**
     * 
     */
    private static final String NULL_STRING = "null";

    /**
     * Validates an equality expr Cases: 1.- lhs == rhs 2.- lhs != rhs
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
                // XPD-157 - passing lhsExpression to traverse thru the entire
                // tree to find out if toString is used before ==
                IScriptRelevantData returnDataType =
                        evaluateEqualityOperator(astExpression,
                                lhsExpression,
                                (Token) token,
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
     * This method can deal with equality(=) and InEquality(!=) operators.
     * 
     * @param astExpression
     * @param lhsExpression
     * 
     * @param operator
     * @param lhsType
     * @param rhsType
     * @param token
     * @return
     */
    protected IScriptRelevantData evaluateEqualityOperator(AST astExpression,
            AST lhsExpression, Token token, IScriptRelevantData lhsDataType,
            IScriptRelevantData rhsDataType) {
        IScriptRelevantData dataType =
                getEqualityOperatorType(lhsDataType, rhsDataType);
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
            String message = Messages.ExpressionValidator_Operation_Undefined;
            addWarningMessage(token, message);
        } else if (!isValidEqualityOperator(lhsDataType, rhsDataType)) {
            String errorMessage =
                    Messages.ExpressionValidator_EqualityOperationBetween;
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(parseTypeMessage(lhsDataType));
            additionalAttributes.add(parseTypeMessage(rhsDataType));
            addErrorMessage(token, errorMessage, additionalAttributes);
        }

        // XPD-157, XPD-770, XPD-1693 (Not needed anymore)
//        if (shouldWarnComparison(lhsDataType, rhsDataType)) {
//            if (!isUsingToString(lhsExpression)) {
//                String warningMessage =
//                        Messages.ExpressionValidator_EqualityOperationComparison;
//                List<String> additionalAttributes = new ArrayList<String>();
//                if (JsConsts.EQUALITY_OPERATOR.equalsIgnoreCase(astExpression
//                        .getText())) {
//                    additionalAttributes.add(JsConsts.EQUALITY_OPERATOR);
//                } else if (JsConsts.NOT_EQUAL_OPERATOR
//                        .equalsIgnoreCase(astExpression.getText())) {
//                    additionalAttributes.add(JsConsts.NOT_EQUAL_OPERATOR);
//                }
//                additionalAttributes.add(parseTypeMessage(lhsDataType));
//                additionalAttributes.add(parseTypeMessage(rhsDataType));
//                // additionalAttributes.add(getDestinationName(getInfoObject()));
//                addWarningMessage(token, warningMessage, additionalAttributes);
//                return dataType;
//            }
//        }
        // XPD-157 end
        return dataType;
    }

//    private boolean isUsingToString(AST lhsExpression) {
//        boolean visited = false;
//        if (null != lhsExpression) {
//            AST firstChild = lhsExpression.getFirstChild();
//            // traverse thru the first child of the tree to check if toString is
//            // visited
//            if (null != firstChild) {
//                visited = isToStringVisited(visited, firstChild);
//            }
//            // if toString is not visited for the first child, then branch to
//            // the next sibling and traverse thru it
//            if (!visited) {
//                firstChild = lhsExpression.getNextSibling();
//                visited = isToStringVisited(visited, firstChild);
//            }
//        }
//        return visited;
//    }

//    /**
//     * @param visited
//     * @param firstChild
//     * @return
//     */
//    private boolean isToStringVisited(boolean visited, AST firstChild) {
//        // navigating thru the entire tree until there is no first child for
//        // a node and then getting the next sibling for that node. if there
//        // is a next sibling and that uses toString, return it
//        while (null != firstChild.getFirstChild()) {
//            firstChild = firstChild.getFirstChild();
//            AST nextSibling = firstChild.getNextSibling();
//            if (null != nextSibling) {
//                visited = JScriptUtils.isToString(nextSibling.getText());
//                if (visited)
//                    break;
//            }
//        }
//        // if no node that uses toString is found, then traverse to the next
//        // sibling level of the tree for this node
//        if (!visited) {
//            AST nextSibling = firstChild.getNextSibling();
//            if (null != nextSibling) {
//                visited = JScriptUtils.isToString(nextSibling.getText());
//            }
//        }
//        return visited;
//    }

//    /**
//     * @param lhsDataType
//     * @param rhsDataType
//     * @return
//     */
//    private boolean shouldWarnComparison(IScriptRelevantData lhsDataType,
//            IScriptRelevantData rhsDataType) {
//        if (null != lhsDataType && null != rhsDataType) {
//            String lhsStrType = convertSpecificToGenericType(lhsDataType);
//            String rhsTypeStr = convertSpecificToGenericType(rhsDataType);
//            if (null != lhsStrType && null != rhsTypeStr) {
//                if (!lhsDataType.getType().equalsIgnoreCase(NULL_STRING)
//                        && !rhsDataType.getType().equalsIgnoreCase(NULL_STRING)) {
//                    if ((JsConsts.TEXT.equals(lhsStrType)
//                            || JsConsts.STRING.equals(lhsStrType) || JsConsts.STRING_LITERAL
//                            .equals(lhsStrType))
//                            || (JsConsts.TEXT.equals(rhsTypeStr)
//                                    || JsConsts.STRING.equals(rhsTypeStr) || JsConsts.STRING_LITERAL
//                                    .equals(rhsTypeStr))) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }

    protected boolean isValidEqualityOperator(IScriptRelevantData lhsDataType,
            IScriptRelevantData rhsDataType) {
        if (lhsDataType != null && lhsDataType.getType() != null
                && rhsDataType != null && rhsDataType.getType() != null) {
            if (JScriptUtils.isXsdDerivedObject(lhsDataType)
                    || JScriptUtils.isXsdDerivedObject(rhsDataType)) {
                return false;
            }
            String lhsStrType = convertSpecificToGenericType(lhsDataType);
            String rhsTypeStr = convertSpecificToGenericType(rhsDataType);
            Map<String, Set<String>> inCompatibleEqualityOperatorTypesMap =
                    null;
            IDataTypeMapper dataTypeMapper = getDataTypeMapper(getInfoObject());
            if (dataTypeMapper instanceof IJScriptDataTypeMapper) {
                IJScriptDataTypeMapper jsDataTypeMapper =
                        (IJScriptDataTypeMapper) dataTypeMapper;
                inCompatibleEqualityOperatorTypesMap =
                        jsDataTypeMapper
                                .getInCompatibleEqualityOperatorTypesMap();
                inCompatibleEqualityOperatorTypesMap =
                        jsDataTypeMapper
                                .convertSpecificMapToGeneric(inCompatibleEqualityOperatorTypesMap);
            }
            if (inCompatibleEqualityOperatorTypesMap != null) {
                Set<String> inCompatibleEqualityOperatorSet =
                        inCompatibleEqualityOperatorTypesMap.get(lhsStrType);
                if (inCompatibleEqualityOperatorSet != null
                        && inCompatibleEqualityOperatorSet.contains(rhsTypeStr)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected IScriptRelevantData getEqualityOperatorType(
            IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType) {
        IScriptRelevantData returnDataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false,
                        null, null);
        if (lhsDataType != null && lhsDataType.getType() != null
                && rhsDataType != null && rhsDataType.getType() != null) {
            returnDataType =
                    createScriptRelevantData(JsConsts.BOOLEAN,
                            JsConsts.BOOLEAN,
                            false,
                            null,
                            null);
        }
        return returnDataType;
    }

}
