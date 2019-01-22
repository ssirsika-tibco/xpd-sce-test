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
import com.tibco.xpd.script.model.internal.client.ITypeResolution;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;

/**
 * 
 * Expression validator for the validation of assignment expressions
 * 
 * @author mtorres
 */
public class JScriptAssignmentExpressionValidator extends
        AbstractExpressionValidator {

    @Override
    public IValidateResult evaluate(IExpr expression) {
        IScriptRelevantData returnDataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false,
                        null,
                        null);
        if (expression != null) {
            Object expr = expression.getExpr();
            Object token = expression.getToken();
            if (expr instanceof AST && token instanceof Token) {
                AST astExpression = (AST) expr;
                switch (astExpression.getType()) {
                case JScriptTokenTypes.ASSIGN:
                    AST lhsExpression = astExpression.getFirstChild();
                    IScriptRelevantData lhsDataType = null;
                    IScriptRelevantData rhsDataType = null;
                    if (lhsExpression != null) {
                        // Evaluate LHS expression
                        IValidateResult evaluateLHS =
                                delegateEvaluateExpression(lhsExpression, token);
                        if (evaluateLHS != null) {
                            evaluateLHSAssignment(evaluateLHS, (Token) token);
                            lhsDataType = evaluateLHS.getType();
                        } else {
                            logUnexpectedExpressionValidatorProblem();
                        }
                        // Get RHS expression
                        AST rhsExpression = lhsExpression.getNextSibling();
                        if (rhsExpression != null) {
                            // Evaluate LHS expression
                            IValidateResult evaluateRHS =
                                    delegateEvaluateExpression(rhsExpression,
                                            token);
                            if (evaluateRHS != null) {
                                rhsDataType = evaluateRHS.getType();
                            } else {
                                logUnexpectedExpressionValidatorProblem();
                            }
                            performExtraAssignmentValidation(evaluateLHS,
                                    evaluateRHS,
                                    (Token) token);
                        }
                    }
                    // Check if the types for lhs and rhs of the assignment
                    // expressions are correct
                    IScriptRelevantData evaluateAssignmentOperator =
                            evaluateAssignmentOperator((Token) token,
                                    lhsDataType,
                                    rhsDataType);
                    if (evaluateAssignmentOperator != null) {
                        returnDataType = evaluateAssignmentOperator;
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
                        JScriptUtils.getCurrentGenericContext(returnDataType));
        return result;
    }

    /**
     * Opportunity to perform some extra validation
     * 
     * @param lhsValidateResult
     * @param rhsValidateResult
     */
    protected void performExtraAssignmentValidation(
            IValidateResult lhsValidateResult,
            IValidateResult rhsValidateResult, Token token) {
        validateSuperTypeAssignment(lhsValidateResult, rhsValidateResult, token);
        // validateTypeRestrictions(lhsValidateResult, rhsValidateResult,
        // token);
    }

    protected void validateSuperTypeAssignment(
            IValidateResult lhsValidateResult,
            IValidateResult rhsValidateResult, Token token) {
        if (JScriptUtils.isSubType(lhsValidateResult.getType(),
                rhsValidateResult.getType())) {
            String warningMessage =
                    Messages.JScriptAssignmentExpressionValidator_AssignmentOfSuperclassToSubClass;
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(parseTypeMessage(rhsValidateResult
                    .getType()));
            additionalAttributes.add(parseTypeMessage(lhsValidateResult
                    .getType()));
            addWarningMessage(token, warningMessage, additionalAttributes);
        }
    }

    protected void validateTypeRestrictions(IValidateResult lhsValidateResult,
            IValidateResult rhsValidateResult, Token token) {
        // System.out.println();
    }

    /**
     * This method can deal with assignment operators.
     * 
     * @param operator
     * @param lhsType
     * @param rhsType
     * @param token
     * @return
     */
    protected IScriptRelevantData evaluateAssignmentOperator(Token token,
            IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType) {
        IScriptRelevantData dataType =
                getAssignmentOperatorType(lhsDataType, rhsDataType);
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
            // boolean lhsIsUnion = false;
            // boolean rhsIsUnion = false;
            // DataType lhsType = JScriptUtils.getDataType(lhsDataType);
            // DataType rhsType = JScriptUtils.getDataType(rhsDataType);
            // if (null != lhsType) {
            // lhsIsUnion =
            // JScriptUtils.isUnion(JScriptUtils.getDataType(lhsDataType));
            // }
            // if (null != rhsType) {
            // rhsIsUnion =
            // JScriptUtils.isUnion(JScriptUtils.getDataType(rhsDataType));
            // }
            // if (lhsIsUnion || rhsIsUnion) {
            // addErrorMessage(token, errorMessage);
            // } else {
            // addWarningMessage(token, errorMessage);
            // }

        } else if (!isValidAssignment(lhsDataType, rhsDataType)) {
            String errorMessage = null;
            if (haveSameMultiplicity(lhsDataType, rhsDataType)) {
                errorMessage =
                        Messages.ExpressionValidator_AssignmentOperationBetween;
            } else {
                errorMessage =
                        Messages.ExpressionValidator_AssignmentMultiplicityOperationBetween;
            }
            List<String> additionalAttributes = new ArrayList<String>();
            additionalAttributes.add(parseTypeMessage(lhsDataType));
            additionalAttributes.add(parseTypeMessage(rhsDataType));
            addErrorMessage(token, errorMessage, additionalAttributes);
        }
        return dataType;
    }

    protected IScriptRelevantData getAssignmentOperatorType(
            IScriptRelevantData lhsDataType, IScriptRelevantData rhsDataType) {
        IScriptRelevantData returnDataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false,
                        null,
                        null);
        if (lhsDataType != null && lhsDataType.getType() != null
                && rhsDataType != null && rhsDataType.getType() != null) {
            returnDataType = lhsDataType;
        }
        return returnDataType;
    }

    protected void evaluateLHSAssignment(IValidateResult evaluateLHS,
            Token token) {
        if (evaluateLHS != null) {
            evaluateLHSIsVariable(evaluateLHS, token);
            evaluateLHSIsReadOnly(evaluateLHS, token);
        }
    }

    protected void evaluateLHSIsVariable(IValidateResult evaluateLHS,
            Token token) {
        if (evaluateLHS != null
                && evaluateLHS.getExpr() instanceof AntlrExprImpl) {
            AntlrExprImpl expr = (AntlrExprImpl) evaluateLHS.getExpr();
            AST astExpr = expr.getExpr();
            if (astExpr != null) {
                if (astExpr.getType() == JScriptTokenTypes.METHOD_CALL) {
                    String errorMessage =
                            Messages.ExpressionValidator_lhsOfAssignmentCanOnlyBeVariable;
                    addErrorMessage(token, errorMessage);
                }
            }
        }
    }

    protected void evaluateLHSIsReadOnly(IValidateResult evaluateLHS,
            Token token) {
        IScriptRelevantData lhsDataType = evaluateLHS.getType();
        if (lhsDataType instanceof ITypeResolution) {
            boolean isReadOnly = ((ITypeResolution) lhsDataType).isReadOnly();
            String errorMessage = null;
            if (lhsDataType.isArray() && isReadOnly) {
                errorMessage =
                        Messages.ExpressionValidator_ListsAssignedDirectly;
            } else if (isReadOnly) {
                errorMessage =
                        Messages.ExpressionValidator_ReadOnlyFieldAssigned;
            }

            if (errorMessage != null) {
                addErrorMessage(token, errorMessage);
            }
        }
    }

}
