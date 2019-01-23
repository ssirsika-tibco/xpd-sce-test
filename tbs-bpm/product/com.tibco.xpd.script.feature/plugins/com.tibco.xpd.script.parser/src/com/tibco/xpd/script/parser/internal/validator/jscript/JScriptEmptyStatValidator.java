/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator.jscript;

import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;

/**
 * 
 * Expression validator for the validation of empty stats
 * 
 * @author mtorres
 */
public class JScriptEmptyStatValidator extends AbstractExpressionValidator {

    @Override
    public IValidateResult evaluate(IExpr expression) {
        return null;
    }

}
