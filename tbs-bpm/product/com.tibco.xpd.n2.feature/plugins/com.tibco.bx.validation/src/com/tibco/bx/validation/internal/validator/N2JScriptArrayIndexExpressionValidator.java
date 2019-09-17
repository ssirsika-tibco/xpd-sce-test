/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.bx.validation.internal.validator;

import java.util.List;

import com.tibco.bx.validation.validator.N2ExpressionValidator;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;

import antlr.Token;
import antlr.collections.AST;

/**
 * @author mtorres
 * 
 *         ExpressionValidator class that handles the validation of the
 *         expression inside the index of an array ie:
 *         arrField[EXPRESSION_TO_VALIDATE]
 * 
 */
@SuppressWarnings("restriction")
public class N2JScriptArrayIndexExpressionValidator
        extends N2ExpressionValidator {

    /**
     * When evaluating an array index we need to make sure that the returned type is the same as the array type but with
     * "isArray" set to false.
     * 
     * @see com.tibco.xpd.script.parser.internal.validator.jscript.AbstractExpressionValidator#evaluate(com.tibco.xpd.script.parser.internal.expr.IExpr)
     */
    @Override
    public IValidateResult evaluate(IExpr expression) {
        IScriptRelevantData returnDataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE, JsConsts.UNDEFINED_DATA_TYPE, false, null, null);
        IScriptRelevantData genericContext = null;
        if (expression != null) {
            Object expr = expression.getExpr();
            Object token = expression.getToken();
            if (expr instanceof AST && token instanceof Token) {
                AST astExpression = (AST) expr;
                AST firstChildExpr = astExpression.getFirstChild();
                IValidateResult evaluateExpression = delegateEvaluateExpression(firstChildExpr, token);
                if (evaluateExpression != null) {
                    // Evaluate the expression type and resolve it
                    IScriptRelevantData evaluatedType = evaluateExpression.getType();
                    List<IScriptRelevantData> resolvedList = resolveType(evaluatedType, false, genericContext);
                    if (resolvedList.size() != 0) {
                        IScriptRelevantData resolved = resolvedList.get(0);
                        if (resolved instanceof IUMLScriptRelevantData) {
                            // If we've managed to resolve it set the return type to the same JsClass but as a non-array
                            // type
                            IUMLScriptRelevantData resolvedType = (IUMLScriptRelevantData) resolved;
                            JsClass jsClass = resolvedType.getJsClass();
                            returnDataType = createUMLScriptRelevantData(jsClass
                                    .getName(), false, jsClass, resolvedType, jsClass);
                        }
                    }
                }
            }
        }
        // Create a new generic context and update the resolution types and return data type.
        genericContext = JScriptUtils.getCurrentGenericContext(returnDataType);
        addResolutionTypes(returnDataType, returnDataType.isArray(), genericContext);
        IValidateResult result = updateResult(expression,
                returnDataType,
                genericContext);
        return result;
    }

}
