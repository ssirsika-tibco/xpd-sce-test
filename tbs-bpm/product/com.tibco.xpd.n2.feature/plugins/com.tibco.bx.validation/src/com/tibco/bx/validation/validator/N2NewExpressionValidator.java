package com.tibco.bx.validation.validator;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;

import antlr.Token;
import antlr.collections.AST;

public class N2NewExpressionValidator extends N2ExpressionValidator {

    @Override
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
                            // constructor when/if new is allowed
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
        IValidateResult result = updateResult(expression,
                returnDataType,
                createGenericContext(returnDataType,
                        isGenericContextArray(returnDataType, returnDataType)));
        return result;
    }

}
