/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.bx.validation.validator;

import com.tibco.bx.validation.internal.validator.N2JSExpressionValidatorFactory;
import com.tibco.xpd.n2.cds.script.N2JScriptDataTypeMapper;
import com.tibco.xpd.process.js.parser.validator.AbstractProcessValidationStrategy;
import com.tibco.xpd.process.js.parser.validator.IProcessValidationStrategy;
import com.tibco.xpd.script.model.internal.client.IDataTypeMapper;
import com.tibco.xpd.script.parser.internal.validator.IValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptConditionalExprValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptMethodDefinitionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptUndefinedVarUseValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptVariableDeclarationValidator;
import com.tibco.xpd.script.parser.validator.IExpressionValidator;
import com.tibco.xpd.script.parser.validator.IExpressionValidatorFactory;
import com.tibco.xpd.script.parser.validator.jscript.JScriptMethodCallValidator;

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
public class N2JScriptValidationStrategy extends
        AbstractProcessValidationStrategy implements IProcessValidationStrategy {

    private IExpressionValidator newExprValidator = null;

    private IDataTypeMapper dataTypeMapper = null;

    private IExpressionValidatorFactory expressionValidatorFactory = null;

    @Override
    protected IExpressionValidator getNewExpressionValidator() {
        if (newExprValidator == null) {
            newExprValidator = new N2NewExpressionValidator();
        }
        return newExprValidator;
    }

    private IExpressionValidator conditionalExprValidator = null;

    @Override
    protected IExpressionValidator getConditionalExpressionValidator() {
        if (conditionalExprValidator == null) {
            conditionalExprValidator = new JScriptConditionalExprValidator();
        }
        return conditionalExprValidator;
    }

    private IExpressionValidator methodCallValidator = null;

    @Override
    protected IExpressionValidator getMethodCallValidator() {
        if (methodCallValidator == null) {
            methodCallValidator = new JScriptMethodCallValidator();
        }
        return methodCallValidator;
    }

    private IExpressionValidator methodDefValidator = null;

    @Override
    protected IExpressionValidator getMethodDefinitionValidator() {
        if (methodDefValidator == null) {
            methodDefValidator = new JScriptMethodDefinitionValidator();
        }
        return methodDefValidator;
    }

    private IExpressionValidator undefinedVarUseValidator = null;

    @Override
    protected IExpressionValidator getUndefinedVariableUseValidator() {
        if (undefinedVarUseValidator == null) {
            undefinedVarUseValidator = new JScriptUndefinedVarUseValidator();
        }
        return undefinedVarUseValidator;
    }

    private IExpressionValidator varDeclaratorValidator = null;

    @Override
    protected IExpressionValidator getVariableDeclarationValidator() {
        if (varDeclaratorValidator == null) {
            varDeclaratorValidator = new JScriptVariableDeclarationValidator();
        }
        return varDeclaratorValidator;
    }

    private IExpressionValidator exprValidator = null;

    @Override
    protected IExpressionValidator getExpressionValidator() {
        if (exprValidator == null) {
            exprValidator = new JScriptExpressionValidator();
        }
        return exprValidator;
    }

    @Override
    protected IExpressionValidator getASTTreeValidator() {
        return null;
    }

    protected IValidator statementValidator = null;

    @Override
    protected IValidator getStatementValidator() {
        if (statementValidator == null) {
            statementValidator = new N2StatementValidator();
        }
        return statementValidator;
    }

    @Override
    protected IExpressionValidatorFactory getExpresionValidatorFactory() {
        if (expressionValidatorFactory == null) {
            expressionValidatorFactory = new N2JSExpressionValidatorFactory();
        }
        return expressionValidatorFactory;
    }

    @Override
    protected IDataTypeMapper getDataTypeMapper() {
        if (dataTypeMapper == null) {
            dataTypeMapper = N2JScriptDataTypeMapper.getInstance();
        }
        return dataTypeMapper;
    }

}
