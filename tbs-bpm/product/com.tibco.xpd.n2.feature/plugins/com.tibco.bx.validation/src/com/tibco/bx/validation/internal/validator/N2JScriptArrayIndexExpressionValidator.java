/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.bx.validation.internal.validator;

import com.tibco.bx.validation.validator.N2ExpressionValidator;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;

/**
 * @author mtorres
 * 
 *         ExpressionValidator class that handles the validation of the
 *         expression inside the index of an array ie:
 *         arrField[EXPRESSION_TO_VALIDATE]
 * 
 */
public class N2JScriptArrayIndexExpressionValidator
        extends N2ExpressionValidator {

    @Override
    public IValidateResult evaluate(IExpr expression) {
        IScriptRelevantData returnDataType =
                createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
                        JsConsts.UNDEFINED_DATA_TYPE,
                        false,
                        null,
                        null);
        IValidateResult result = updateResult(expression,
                returnDataType,
                createGenericContext(returnDataType,
                        isGenericContextArray(returnDataType, returnDataType)));
        return result;
    }

}
