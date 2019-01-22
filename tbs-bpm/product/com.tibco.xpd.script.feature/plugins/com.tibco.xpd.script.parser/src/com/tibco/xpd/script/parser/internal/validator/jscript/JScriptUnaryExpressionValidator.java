/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import java.util.Map;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.DataTypeMapper;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
/**
 * 
 * Expression validator for the validation of unary expressions
 * 
 * @author mtorres
 */
public class JScriptUnaryExpressionValidator extends
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
                case JScriptTokenTypes.UNARY_MINUS:
                case JScriptTokenTypes.UNARY_PLUS:
                case JScriptTokenTypes.POST_DEC:
                case JScriptTokenTypes.POST_INC:
                case JScriptTokenTypes.INC:
                case JScriptTokenTypes.DEC:
                    IScriptRelevantData evaluateNumericUnaryOperator =
                            evaluateNumericUnaryOperator(astExpression,
                                    (Token) token);
                    if (evaluateNumericUnaryOperator != null) {
                        returnDataType = evaluateNumericUnaryOperator;
                    }
                    break;
                case JScriptTokenTypes.LNOT:
                    IScriptRelevantData evaluateBooleanUnaryOperator =
                            evaluateBooleanUnaryOperator(astExpression,
                                    (Token) token);
                    if (evaluateBooleanUnaryOperator != null) {
                        returnDataType = evaluateBooleanUnaryOperator;
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
                        createGenericContext(returnDataType, isGenericContextArray(returnDataType, returnDataType)));
        return result;
    }

    /**
     * @param exprAST
     * @param token
     * @return
     */
    private IScriptRelevantData evaluateNumericUnaryOperator(AST exprAST,
            Token token) {
        IScriptRelevantData dataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false, null, null);
        AST fcAST = exprAST.getFirstChild();
        if (fcAST.getType() == JScriptTokenTypes.NUM_INT
                || fcAST.getType() == JScriptTokenTypes.NUM_LONG
                || fcAST.getType() == JScriptTokenTypes.NUM_FLOAT
                || fcAST.getType() == JScriptTokenTypes.NUM_DOUBLE) {
            IValidateResult delegateEvaluateExpression =
                    delegateEvaluateExpression(fcAST, token);
            if (delegateEvaluateExpression != null) {
                dataType = delegateEvaluateExpression.getType();
            }
        } else {
            String variableName = getVariableName(fcAST);
            if (variableName != null && isVariableDefined(variableName, token)) {
                IValidateResult delegateEvaluateExpression =
                        delegateEvaluateExpression(fcAST, token);
                if (delegateEvaluateExpression != null) {
                    IScriptRelevantData type =
                            delegateEvaluateExpression.getType();
                    if (type != null && type.getType() != null) {
                        if (type.getType().equals(JsConsts.UNDEFINED_DATA_TYPE)) {
                            addWarningMessage(token,
                                    Messages.ExpressionValidator_Operation_Undefined);
                            dataType =
                                createScriptRelevantData(JsConsts.INTEGER,
                                        JsConsts.INTEGER,
                                        false, null, null);
                        } else if (isNumericDataType(type)) {
                            dataType = delegateEvaluateExpression.getType();
                        } else {
                            addErrorMessage(token,
                                    Messages.ExpressionValidator_UnaryOperatorsShouldBeOnlyOnFloatAndInteger);
                        }
                    } else {
                        logUnexpectedExpressionValidatorProblem();
                    }
                } else {
                    logUnexpectedExpressionValidatorProblem();
                }
            } else {
                addErrorMessage(token,
                        Messages.ExpressionValidator_UnaryOperatorsShouldBeOnlyOnFloatAndInteger);
            }
        }
        return dataType;
    }
    
    /**
     * @param exprChildAST
     * @param token
     * @return
     */
    private IScriptRelevantData evaluateBooleanUnaryOperator(AST exprAST,
            Token token) {
        IScriptRelevantData dataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false, null, null);
        AST fcAST = exprAST.getFirstChild();
        if (fcAST != null) {
            IValidateResult delegateEvaluateExpression =
                    delegateEvaluateExpression(fcAST, token);
            if (delegateEvaluateExpression != null) {
                IScriptRelevantData type = delegateEvaluateExpression.getType();
                if(type != null && type.getType() != null){
                    if(type.getType().equals(JsConsts.BOOLEAN)){
                        dataType = type;
                    } else if(type.getType().equals(JsConsts.UNDEFINED_DATA_TYPE)){
                        addWarningMessage(token,
                                Messages.ExpressionValidator_Operation_Undefined);
                        dataType =
                            createScriptRelevantData(JsConsts.BOOLEAN,
                                    JsConsts.BOOLEAN,
                                    false, null, null);
                    } else {
                        addErrorMessage(token,
                                Messages.ExpressionValidator_BooleanUnaryOperatorsShouldBeOnlyOnBooleans);
                    }
                } else {
                    logUnexpectedExpressionValidatorProblem();
                }
            } else {
                logUnexpectedExpressionValidatorProblem();
            }
        }
        return dataType;
    }

    private String getVariableName(AST expression) {
        String variableName = null;
        if (expression != null) {
            while (expression.getFirstChild() != null) {
                expression = expression.getFirstChild();
            }
            if (expression.getType() == JScriptTokenTypes.IDENT) {
                variableName = expression.getText();
            }
        }
        return variableName;
    }

    private boolean isVariableDefined(String varName, Token token) {
        ISymbolTable symbolTable = getSymbolTable(getInfoObject());
        boolean bool = false;
        if (symbolTable != null) {
            Map<String, IScriptRelevantData> localVariableMap =
                    symbolTable.getLocalVariableMap();
            Map<String, IScriptRelevantData> scriptRelevantDataMap =
                    symbolTable.getScriptRelevantDataTypeMap();
            bool =
                    JScriptUtils.isVariableDefined(localVariableMap,
                            scriptRelevantDataMap,
                            DataTypeMapper.getSymbolTableKeyWords(),
                            varName);
        }
        return bool;
    }

}
