package com.tibco.xpd.process.js.parser.validator.jscript;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.process.js.parser.Messages;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
import com.tibco.xpd.script.parser.util.ParseUtil;

public class ProcessJScriptNumberASTTreeValidator extends
        AbstractProcessJScriptExpressionValidator {

    public void validate(AST astTree, Token token) {
        if (astTree == null) {
            return;
        }
        AST lastExpressionAST = ParseUtil.getLastExpressionFromTree(astTree);
        if (lastExpressionAST == null) {
            return;
        }
        IValidateResult delegateEvaluateExpression =
                delegateEvaluateExpression(lastExpressionAST, token);
        if (delegateEvaluateExpression != null
                && delegateEvaluateExpression.getType() != null) {
            String errorMessage =
                    Messages.JScriptASTTreeValidator_LastStatementEvaluatingToNumber;
            if (!isNumericDataType(delegateEvaluateExpression.getType())) {
                addErrorMessage(token, errorMessage);
            }
        }

    }

}
