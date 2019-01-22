/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.validator;

import org.eclipse.emf.ecore.EObject;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.internal.client.ITypeResolution;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptReturnTypeValidator;
import com.tibco.xpd.script.parser.util.ParseUtil;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Replacement for the default return type validator to enforce return types
 * only on the last statement of the script and correctly identify the return
 * type.
 * 
 * @author nwilson
 * @since 29 Apr 2015
 */
public class N2JScriptReturnTypeValidator extends JScriptReturnTypeValidator {

    /**
     * @see com.tibco.xpd.script.parser.internal.validator.jscript.AbstractExpressionValidator#validate(antlr.collections.AST,
     *      antlr.Token)
     * 
     * @param astTree
     *            The complete AST tree.
     * @param token
     *            The top level token.
     */
    @SuppressWarnings("restriction")
    @Override
    public void validate(AST astTree, Token token) {
        if (astTree == null) {
            return;
        }
        AST lastStatementAST = ParseUtil.getLastExpressionFromTree(astTree);
        AST lastExpressionAST = lastStatementAST;
        if (lastExpressionAST == null) {
            return;
        }
        int statType = lastExpressionAST.getType();
        if (statType == JScriptTokenTypes.LITERAL_return) {
            AST expr = lastExpressionAST.getFirstChild();
            if (expr != null && JScriptTokenTypes.EXPR == expr.getType()) {
                lastExpressionAST = expr.getFirstChild();
            }
        }
        if (lastExpressionAST == null) {
            return;
        }
        IValidateResult delegateEvaluateExpression =
                delegateEvaluateExpression(lastExpressionAST, token);
        if (delegateEvaluateExpression != null
                && delegateEvaluateExpression.getType() != null) {
            IScriptRelevantData type = delegateEvaluateExpression.getType();
            if (JScriptUtils.isGenericType(type.getType())) {
                if (type instanceof ITypeResolution) {
                    IScriptRelevantData genericContextType =
                            ((ITypeResolution) type).getGenericContextType();
                    if (genericContextType != null) {
                        type = genericContextType;
                    }
                }
            }
            registerReturnType(type);
        }
        // Prevent non-return errors from being reported.
        getErrorMessageList().clear();
        getWarningMessageList().clear();

        /*
         * Sid XPD-7996: Many mapping scenarios support both JavaScript and
         * DataMapper mapping grammars. User defined mapping scripts are exactly
         * equivalent in both EXCEPT that data mapper scripts demand that last
         * statement MUST be RETURN and mapping scripts for JavaScripts mapping
         * grammar do not support return.
         * 
         * In order to NOT have to have SEPARATE script contexts and
         * contributions for JavaScript and Datamapper mapping scripts FOR EVERY
         * mapping scenario that supports both JavaScript and DataMapper, this
         * 'must have return statement as last line' strategy is now
         * conditionalised. If the input ScriptInformation element is contained
         * within a ScriptDataMapper element then we know that it is a
         * DataMapper grammar scenario and can enforce this rule.
         * 
         * This means that the N2JScriptFunctionValidationStrategy can be
         * contributed to a single mapping script context for both JavaScript
         * and DataMapper scenarios.
         */
        boolean isDataMapperScenario = isDataMapperMappingScript();

        if (isDataMapperScenario) {
            if (statType != JScriptTokenTypes.LITERAL_return) {
                String errorMessage =
                        Messages.N2FunctionStatementValidator_LastStatementReturn;
                addErrorMessage(token, errorMessage);
            }
        }

        // Check that there are no other return statements
        validateOtherReturns(astTree, lastStatementAST, token);
    }

    /**
     * @return <code>true</code> if we are validating a user defined mapping
     *         script within a DataMapper mapping grammar scenario.
     */
    protected boolean isDataMapperMappingScript() {
        boolean isDataMapperScenario = false;
        if (getInfoObject() != null) {
            EObject input = getInput(getInfoObject());

            if (input != null
                    && Xpdl2ModelUtil
                            .getAncestor(input, ScriptDataMapper.class) != null) {
                isDataMapperScenario = true;
            }
        }
        return isDataMapperScenario;
    }

    /**
     * @param astTree
     *            The current AST tree location.
     * @param lastExpressionAST
     *            The last expression in the script.
     * @param token
     *            The top level token.
     */
    private void validateOtherReturns(AST astTree, AST lastExpressionAST,
            Token token) {
        // Ignore last statement. Note this must use != not 'equals' as the
        // equals method only compares text and type.
        if (astTree != null && astTree != lastExpressionAST) {
            int statType = astTree.getType();
            if (statType == JScriptTokenTypes.LITERAL_return) {
                String errorMessage =
                        Messages.N2JScriptReturnTypeValidator_ReturnLastLineOnly;
                addErrorMessage(token, errorMessage);
            }
            validateOtherReturns(astTree.getFirstChild(),
                    lastExpressionAST,
                    token);
            validateOtherReturns(astTree.getNextSibling(),
                    lastExpressionAST,
                    token);
        }
    }
}
