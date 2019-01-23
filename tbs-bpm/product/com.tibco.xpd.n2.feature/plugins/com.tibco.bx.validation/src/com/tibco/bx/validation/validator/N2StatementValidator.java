package com.tibco.bx.validation.validator;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
/**
 * 
 * @author Miguel Torres
 * 
 */
public class N2StatementValidator extends N2ExpressionValidator {

    public void validate(AST statementAST, Token token) {
        if (statementAST == null) {
            return;
        }
        int statType = statementAST.getType();
        if (statType == JScriptTokenTypes.LITERAL_try) {
            String errorMessage = Messages.N2StatementValidator_TryCatchStatNotSupported;
            addErrorMessage(token, errorMessage);
        } else if (statType == JScriptTokenTypes.LITERAL_switch) {
            String errorMessage = Messages.N2StatementValidator_SwitchStatNotSupported;
            addErrorMessage(token, errorMessage);
        } else if (statType == JScriptTokenTypes.LITERAL_throw) {
            String errorMessage = Messages.N2StatementValidator_ThrowStatNotSupported;
            addErrorMessage(token, errorMessage);
        }  else if (statType == JScriptTokenTypes.LITERAL_return) {
            String errorMessage = Messages.N2StatementValidator_ReturnStatNotSupported;
            addErrorMessage(token, errorMessage);
        } 
    }

}
