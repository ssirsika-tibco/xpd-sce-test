/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.bx.validation.validator;

import java.util.regex.Pattern;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.process.js.parser.Messages;
import com.tibco.xpd.process.js.parser.validator.jscript.ProcessJScriptNumberASTTreeValidator;

/**
 * validator that complains of
 * 
 * 1. error - if there is only a single statement defined in the script that is
 * a hard coded value other than zero
 * 
 * 2. warning - if there are multiple statements then the script must eventually
 * evaluate to zero
 * 
 * @author bharge
 * 
 */
public class N2NumberASTTreeValidator extends
        ProcessJScriptNumberASTTreeValidator {

    /**
     * @see com.tibco.xpd.process.js.parser.validator.jscript.ProcessJScriptNumberASTTreeValidator#validate(antlr.collections.AST,
     *      antlr.Token)
     * 
     * @param astTree
     * @param token
     */
    @Override
    public void validate(AST astTree, Token token) {
        super.validate(astTree, token);
        if (astTree == null) {
            return;
        }

        boolean onlyOneExpression = isOnlyOneExpression(astTree);
        /*
         * Do not allow a script that's a single non-zero constant number,
         * otherwise there will always be additional task instances added when
         * task is submitted and therefore the task will never complete.
         */
        if (onlyOneExpression && astTree.getNumberOfChildren() == 1) {
            // if (astTree.getFirstChild() == null) {
            // return;
            // }
            String text = astTree.getFirstChild().getText();

            /** allow only zero */

            String exprPattern = "^[0]*[1-9]+[0]*$"; //$NON-NLS-1$
            if (Pattern.matches(exprPattern, text)) {
                String errorMessage =
                        Messages.JScriptASTTreeValidator_SingleStatementHardcodedNumber1;
                addErrorMessage(token, errorMessage);
            }
        } else {
            String warningMessage =
                    Messages.JScriptASTTreeValidator_MIAddlInstanceScriptEvaluateToZero1;
            addWarningMessage(token, warningMessage);
        }
    }

    protected boolean isOnlyOneExpression(AST treeAST) {
        AST exprAST = null;
        if (null != treeAST) {
            exprAST = treeAST.getNextSibling();
            if (null == exprAST && treeAST.getNumberOfChildren() == 1) {
                return true;
            }
        }
        return false;
    }

}
