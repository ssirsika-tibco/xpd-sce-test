package com.tibco.bx.validation.internal.validator;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.validator.jscript.AbstractExpressionValidator;
import com.tibco.xpd.script.parser.util.ParseUtil;

public class N2JScriptASTTreeLastExpressionValidator extends AbstractExpressionValidator {

    public void validate(AST astTree, Token token) {
        if (astTree == null) {
            return;
        }
        AST lastExpressionAST = ParseUtil.getLastExpressionFromTree(astTree);
        if (lastExpressionAST == null) {
            return;
        }
        if (lastExpressionAST.getType() == JScriptTokenTypes.EXPR) {
            AST firstChild = lastExpressionAST.getFirstChild();
            if (firstChild != null
                    && firstChild.getType() == JScriptTokenTypes.ASSIGN) {
                addWarningMessage(token,
                        Messages.N2JScriptASTTreeValidator_LastExpressionAssignment);
            }
        }
    }

}
