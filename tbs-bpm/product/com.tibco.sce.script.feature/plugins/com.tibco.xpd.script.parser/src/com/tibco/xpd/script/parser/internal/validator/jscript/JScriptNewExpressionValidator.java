/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
/**
 * 
 * Expression validator for the validation of new expressions
 * 
 * @author mtorres
 */
public class JScriptNewExpressionValidator extends AbstractExpressionValidator {

    public void validate(AST newExpressionAST, Token token) {
        delegateEvaluateExpression(newExpressionAST, token);
    }

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
                AST newExpressionAST = (AST) expr;
                switch (newExpressionAST.getType()) {
                case JScriptTokenTypes.LITERAL_new:
                    AST astFirsChild = newExpressionAST.getFirstChild();
                    if (astFirsChild != null) {
                        IValidateResult delegateEvaluateExpression =
                                delegateEvaluateExpression(astFirsChild, token);
                        if (delegateEvaluateExpression != null) {
                            // TODO Validate The parameters passed to the
                            // constructor
                            returnDataType =
                                    delegateEvaluateExpression.getType();
                        } else {
                            logUnexpectedExpressionValidatorProblem();
                        }
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
                        createGenericContext(returnDataType, isGenericContextArray(returnDataType, returnDataType)));
        return result;
    }

}
