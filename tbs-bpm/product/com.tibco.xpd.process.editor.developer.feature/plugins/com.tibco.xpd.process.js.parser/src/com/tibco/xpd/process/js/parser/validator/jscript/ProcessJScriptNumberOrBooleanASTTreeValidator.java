package com.tibco.xpd.process.js.parser.validator.jscript;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.process.js.parser.Messages;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
import com.tibco.xpd.script.parser.util.ParseUtil;

public class ProcessJScriptNumberOrBooleanASTTreeValidator extends
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
        IScriptRelevantData resultType = null;
        if (delegateEvaluateExpression != null
                && delegateEvaluateExpression.getType() != null) {
            resultType = delegateEvaluateExpression.getType();
        }
        String errorMessage =
                Messages.JScriptASTTreeValidator_LastStatementEvaluatingToNumberOrBoolean;
        if (resultType != null) {
            if (!isNumericDataType(resultType)
                    && !JsConsts.BOOLEAN.equalsIgnoreCase(resultType.getType())) {
                addErrorMessage(token, errorMessage);
            }
        }

    }

}
