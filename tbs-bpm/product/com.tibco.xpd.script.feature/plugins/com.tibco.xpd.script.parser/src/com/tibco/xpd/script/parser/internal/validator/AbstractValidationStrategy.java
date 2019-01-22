/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator;

import java.util.ArrayList;
import java.util.List;

import antlr.LLkParser;
import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.script.model.client.DefaultScriptRelevantDataFactory;
import com.tibco.xpd.script.model.client.IScriptRelevantDataFactory;
import com.tibco.xpd.script.model.client.ITypeResolver;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.model.internal.client.IDataTypeMapper;
import com.tibco.xpd.script.model.internal.jscript.JScriptDataTypeMapper;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.expr.IInfoObject;
import com.tibco.xpd.script.parser.internal.validator.jscript.AntlrExpressionFactory;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptReturnTypeValidator;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.IExpressionValidator;
import com.tibco.xpd.script.parser.validator.IExpressionValidatorFactory;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.parser.validator.jscript.JSExpressionValidatorFactory;

public abstract class AbstractValidationStrategy implements
        IValidationStrategy, IValidationStrategyExtended {

    protected List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();

    protected List<ErrorMessage> warningList = new ArrayList<ErrorMessage>();

    protected LLkParser scriptParser = null;

    private IVarNameResolver varNameResolver;

    private IDataTypeMapper dataTypeMapper = null;

    private IExpressionFactory expressionFactory = null;

    private IExpressionValidatorFactory expressionValidatorFactory = null;

    private IScriptRelevantDataFactory scriptRelevantDataFactory = null;

    private List<ITypeResolver> dynamicTypeResolvers = null;

    private List<JsClassDefinitionReader> classDefinitionReaders = null;

    private boolean validateGlobalFunctions = true;

    protected IExpressionValidator returnTypeValidator = null;

    @Override
    public void validateVariableDeclaration(AST varDefAST, Token token) {
        IExpressionValidator varDeclarationValidator =
                getVariableDeclarationValidator();
        if (varDeclarationValidator != null) {
            initialiseExpressionValidator(varDeclarationValidator);
            varDeclarationValidator.validate(varDefAST, token);
            reportErrorsAndWarnings(varDeclarationValidator);
        }
    }

    @Override
    public void validateMethodCall(AST methodAST, Token token) {
        IExpressionValidator methodCallValidator = getMethodCallValidator();
        if (methodCallValidator != null) {
            initialiseExpressionValidator(methodCallValidator);
            methodCallValidator.validate(methodAST, token);
            reportErrorsAndWarnings(methodCallValidator);
        }
    }

    /**
     * This has been put as we do not know any case where we should allow local
     * method defintion.
     */
    @Override
    public void validateMethodDeclaration(AST methodAST, Token token) {
        IExpressionValidator methodDefValidator =
                getMethodDefinitionValidator();
        if (methodDefValidator != null) {
            initialiseExpressionValidator(methodDefValidator);
            methodDefValidator.validate(methodAST, token);
            reportErrorsAndWarnings(methodDefValidator);
        }
    }

    /**
     * This method will validate whether the passed expression will evaluate
     * will true or false.
     */
    @Override
    public void validateConditionalExpression(AST expressionAST, Token token) {
        IExpressionValidator conditionalExprValidator =
                getConditionalExpressionValidator();
        if (conditionalExprValidator != null) {
            initialiseExpressionValidator(conditionalExprValidator);
            conditionalExprValidator.validate(expressionAST, token);
            reportErrorsAndWarnings(conditionalExprValidator);
        }
    }

    @Override
    public void validateNewExpression(AST expressionAST, Token token) {
        IExpressionValidator newExpressionValidator =
                getNewExpressionValidator();
        if (newExpressionValidator != null) {
            initialiseExpressionValidator(newExpressionValidator);
            newExpressionValidator.validate(expressionAST, token);
            reportErrorsAndWarnings(newExpressionValidator);
        }
    }

    @Override
    public void validateUndefinedVariableUse(AST expression, Token token) {
        IExpressionValidator undefinedVarUseValidator =
                getUndefinedVariableUseValidator();
        if (undefinedVarUseValidator != null) {
            initialiseExpressionValidator(undefinedVarUseValidator);
            undefinedVarUseValidator.validate(expression, token);
            reportErrorsAndWarnings(undefinedVarUseValidator);
        }

    }

    @Override
    public void validateStatement(AST statementAST, Token token) {
        IValidator statementValidator = getStatementValidator();
        if (statementValidator != null) {
            statementValidator.validate(statementAST, token);
            reportErrorsAndWarnings(statementValidator);
        }
    }

    protected void addErrorMessage(List<ErrorMessage> errorMessageList) {
        List<ErrorMessage> comprehensiveErrorList = getErrorList();
        if (errorMessageList != null) {
            for (ErrorMessage message : errorMessageList) {
                if (!comprehensiveErrorList.contains(message)) {
                    comprehensiveErrorList.add(message);
                }
            }
        }
    }

    protected void addWarningMessage(List<ErrorMessage> warningMessageList) {
        List<ErrorMessage> comprehensiveWarningList = getWarningList();
        if (warningMessageList != null) {
            for (ErrorMessage message : warningMessageList) {
                if (!comprehensiveWarningList.contains(message)) {
                    comprehensiveWarningList.add(message);
                }
            }
        }
    }

    protected void initialiseExpressionValidator(
            IExpressionValidator expressionValidator) {
        if (expressionValidator != null) {
            expressionValidator.setScriptParser(getScriptParser());
            expressionValidator.setVarNameResolver(getVarNameResolver());
            expressionValidator.setInfoObject(getInfoObject());
        }
    }

    protected void reportErrorsAndWarnings(IValidator validator) {
        if (validator != null) {
            List<ErrorMessage> errorMessageList =
                    validator.getErrorMessageList();
            if (errorMessageList != null && errorMessageList.size() > 0) {
                addErrorMessage(errorMessageList);
            }
            List<ErrorMessage> warningMessageList =
                    validator.getWarningMessageList();
            if (warningMessageList != null && warningMessageList.size() > 0) {
                addWarningMessage(warningMessageList);
            }
        }
    }

    @Override
    public List<ErrorMessage> getErrorList() {
        return errorList;
    }

    @Override
    public List<ErrorMessage> getWarningList() {
        return warningList;
    }

    @Override
    public void setScriptParser(LLkParser parser) {
        this.scriptParser = parser;
    }

    protected LLkParser getScriptParser() {
        return this.scriptParser;
    }

    @Override
    public void setVarNameResolver(IVarNameResolver varNameResolver) {
        this.varNameResolver = varNameResolver;
    }

    @Override
    public IVarNameResolver getVarNameResolver() {
        return this.varNameResolver;
    }

    /**
     * This method gets the whole tree to validate
     */
    @Override
    public void validateASTTree(AST astTree, Token token) {
        IExpressionValidator astTreeValidator = getASTTreeValidator();
        if (astTreeValidator != null) {
            initialiseExpressionValidator(astTreeValidator);
            astTreeValidator.validate(astTree, token);
            reportErrorsAndWarnings(astTreeValidator);
        }
        IExpressionValidator returnTypeValidator = getReturnTypeValidator();
        if (returnTypeValidator != null) {
            initialiseExpressionValidator(returnTypeValidator);
            returnTypeValidator.validate(astTree, token);
            reportErrorsAndWarnings(returnTypeValidator);
        }
    }

    @Override
    public void validateExpression(AST expressionAST, Token token) {
        IExpressionValidator exprValidator = getExpressionValidator();
        if (exprValidator != null) {
            initialiseExpressionValidator(exprValidator);
            exprValidator.validate(expressionAST, token);
            reportErrorsAndWarnings(exprValidator);
        }
    }

    @Override
    public List<JsClass> getSupportedJsClasses() {
        List<JsClass> supportedJsClasses = new ArrayList<JsClass>();
        // Get the supported classes from the expression validator
        IExpressionValidator expressionValidator = getExpressionValidator();
        if (expressionValidator != null) {
            initialiseExpressionValidator(expressionValidator);
            supportedJsClasses = expressionValidator.getSupportedJsClasses();
        }
        return supportedJsClasses;
    }

    /**
     * @see com.tibco.xpd.script.parser.validator.IValidationStrategy#setDestinationName(java.lang.String)
     * 
     * @param destinationName
     */
    @Override
    public void setDestinationName(String destinationName) {
        // TODO - Generated
    }

    /**
     * @see com.tibco.xpd.script.parser.validator.IValidationStrategy#setScriptType(java.lang.String)
     * 
     * @param scriptType
     */
    @Override
    public void setScriptType(String scriptType) {
        // TODO - Generated
    }

    /**
     * @see com.tibco.xpd.script.parser.validator.IValidationStrategy#getDestinationName()
     * 
     * @return
     */
    @Override
    public String getDestinationName() {
        // TODO - Generated
        return null;
    }

    /**
     * @see com.tibco.xpd.script.parser.validator.IValidationStrategy#getScriptType()
     * 
     * @return
     */
    @Override
    public String getScriptType() {
        // TODO - Generated
        return null;
    }

    protected abstract IExpressionValidator getExpressionValidator();

    protected abstract IExpressionValidator getNewExpressionValidator();

    protected abstract IExpressionValidator getMethodCallValidator();

    protected abstract IExpressionValidator getMethodDefinitionValidator();

    protected abstract IExpressionValidator getVariableDeclarationValidator();

    protected abstract IExpressionValidator getConditionalExpressionValidator();

    protected abstract IExpressionValidator getUndefinedVariableUseValidator();

    protected abstract IExpressionValidator getASTTreeValidator();

    protected abstract IValidator getStatementValidator();

    protected IExpressionValidator getReturnTypeValidator() {
        if (returnTypeValidator == null) {
            returnTypeValidator = new JScriptReturnTypeValidator();
        }
        return returnTypeValidator;
    }

    /**
     * Override this if another Expression Validator Factory is needed
     * 
     * @return Default JScript Expression Validator Factory
     **/
    protected IExpressionValidatorFactory getExpresionValidatorFactory() {
        if (expressionValidatorFactory == null) {
            expressionValidatorFactory = new JSExpressionValidatorFactory();
        }
        return expressionValidatorFactory;
    }

    /**
     * Override this if another Expression Validator Factory is needed
     * 
     * @return Default JScript Expression Validator Factory
     **/
    protected IExpressionFactory getExpresionFactory() {
        if (expressionFactory == null) {
            expressionFactory = new AntlrExpressionFactory();
        }
        return expressionFactory;
    }

    /**
     * Override this if another DataTypeMapper is needed
     * 
     * @return IDataTypeMapper
     **/
    protected IDataTypeMapper getDataTypeMapper() {
        if (dataTypeMapper == null) {
            dataTypeMapper = JScriptDataTypeMapper.getInstance();
        }
        return dataTypeMapper;
    }

    /**
     * Override this if another IScriptRelevantDataFactory is needed
     * 
     * @return Default IScriptRelevantDataFactory
     **/
    protected IScriptRelevantDataFactory getScriptRelevantDataFactory() {
        if (scriptRelevantDataFactory == null) {
            scriptRelevantDataFactory = new DefaultScriptRelevantDataFactory();
        }
        return scriptRelevantDataFactory;
    }

    public abstract IInfoObject getInfoObject();

    @Override
    public IValidateResult evaluateExpression(AST expressionAST, Token token) {
        IExpressionValidator exprValidator = getExpressionValidator();
        if (exprValidator != null) {
            initialiseExpressionValidator(exprValidator);
            if (getExpresionFactory() != null) {
                IExpr expr =
                        getExpresionFactory().createExpr(expressionAST, token);
                IValidateResult evaluate = exprValidator.evaluate(expr);
                reportErrorsAndWarnings(exprValidator);
                return evaluate;
            }
        }
        return null;
    }

    public List<ITypeResolver> getDynamicTypeResolvers() {
        return dynamicTypeResolvers;
    }

    public void setDynamicTypeResolvers(List<ITypeResolver> dynamicTypeResolvers) {
        this.dynamicTypeResolvers = dynamicTypeResolvers;
    }

    public boolean isValidateGlobalFunctions() {
        return validateGlobalFunctions;
    }

    public void setValidateGlobalFunctions(boolean validateGlobalFunctions) {
        this.validateGlobalFunctions = validateGlobalFunctions;
    }

    public List<JsClassDefinitionReader> getClassDefinitionReaders() {
        return classDefinitionReaders;
    }

    public void setClassDefinitionReaders(
            List<JsClassDefinitionReader> classDefinitionReaders) {
        this.classDefinitionReaders = classDefinitionReaders;
    }
}
