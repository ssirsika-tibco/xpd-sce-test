/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.internal.client.ITypeResolution;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
import com.tibco.xpd.script.parser.util.ParseUtil;

/**
 * Class that registers the return type of the script expression
 * 
 * @author mtorres
 * 
 */
public class JScriptReturnTypeValidator extends AbstractExpressionValidator {

    @Override
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
        // This rule should not report errors.
        getErrorMessageList().clear();
        getWarningMessageList().clear();
    }

}
