package com.tibco.xpd.process.js.parser.validator.jscript;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.process.js.parser.Messages;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
import com.tibco.xpd.script.parser.util.ParseUtil;

public class ProcessJScriptBooleanASTTreeValidator extends AbstractProcessJScriptExpressionValidator {

    
    
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
        String result = JsConsts.UNDEFINED_DATA_TYPE;
        if (delegateEvaluateExpression != null
                && delegateEvaluateExpression.getType() != null) {
            result = delegateEvaluateExpression.getType().getType();
        }
        String errorMessage =
                Messages.JScriptASTTreeValidator_LastStatementEvaluatingToBoolean;
        if (!JsConsts.BOOLEAN.equalsIgnoreCase(result)) {
            addErrorMessage(token, errorMessage);
        }
		
	}

}
