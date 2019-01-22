/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.bx.validation.validator;

import com.tibco.xpd.script.parser.internal.validator.IValidator;
import com.tibco.xpd.script.parser.validator.IExpressionValidator;

/**
 * 
 * 
 * <p>
 * <i>Created: 6 Dec 2009</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class N2JScriptPerformerValidationStrategy extends
        N2JScriptValidationStrategy {

    @Override
    protected IExpressionValidator getNewExpressionValidator() {
        return null;
    }

    @Override
    protected IExpressionValidator getConditionalExpressionValidator() {
        return null;
    }

    @Override
    protected IExpressionValidator getMethodCallValidator() {
        return null;
    }

    @Override
    protected IExpressionValidator getMethodDefinitionValidator() {
        return null;
    }

    @Override
    protected IExpressionValidator getUndefinedVariableUseValidator() {
        return null;
    }

    @Override
    protected IExpressionValidator getVariableDeclarationValidator() {
        return null;
    }

    @Override
    protected IExpressionValidator getExpressionValidator() {
        return null;
    }

    private IExpressionValidator astTreeValidator = null;

    @Override
    protected IExpressionValidator getASTTreeValidator() {
        if (astTreeValidator == null) {
            astTreeValidator = new N2PerformerASTTreeValidator();
        }
        return astTreeValidator;
    }

    @Override
    protected IValidator getStatementValidator() {
        return null;
    }

}
