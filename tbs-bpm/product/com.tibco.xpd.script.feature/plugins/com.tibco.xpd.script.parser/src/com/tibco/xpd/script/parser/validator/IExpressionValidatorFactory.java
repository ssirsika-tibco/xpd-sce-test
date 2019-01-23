/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.validator;

import com.tibco.xpd.script.parser.internal.expr.IExpr;
/**
 * Factory class for the creation of IExpressionValidator
 * 
 * @author mtorres
 */
public interface IExpressionValidatorFactory {
    /**
     * Returns expression validator
     * 
     * @param expr
     * @return the expression validator
     */
    IExpressionValidator getExpressionValidator(IExpr expr);
}
