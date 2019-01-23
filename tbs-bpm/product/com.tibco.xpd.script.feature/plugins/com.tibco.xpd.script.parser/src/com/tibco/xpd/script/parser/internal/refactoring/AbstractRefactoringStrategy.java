/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.refactoring;

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
import com.tibco.xpd.script.parser.internal.expr.IRefactoringInfoObject;
import com.tibco.xpd.script.parser.internal.validator.IExpressionFactory;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.IExpressionValidator;

/**
 * Abstract class for the refactoring strategy
 * 
 * @author mtorres
 */
public abstract class AbstractRefactoringStrategy implements IRefactoringStrategy {
    
    private RefactoringInfo refactoringInfo;

    private LLkParser scriptParser;
    
    private IVarNameResolver varNameResolver;
    
    private IDataTypeMapper dataTypeMapper;
    
    private IScriptRelevantDataFactory scriptRelevantDataFactory;
    
    private List<ITypeResolver> dynamicTypeResolvers;
    
    private boolean validateGlobalFunctions;
    
    private List<JsClassDefinitionReader> classDefinitionReaders;
    
    private String destinationName;
    
    private String scriptType;
    
    private List<RefactorResult> refactorResultList;
    
    @Override
    public String getDestinationName() {
        return destinationName;
    }

    @Override
    public String getScriptType() {
        return scriptType;
    }

    @Override
    public IVarNameResolver getVarNameResolver() {
        return varNameResolver;
    }

    @Override
    public void refactorASTTree(AST astTree, Token token) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void refactorConditionalExpression(AST expressionAST, Token token) {
        IExpressionRefactor conditionalExpressionRefactor =
                getConditionalExpressionRefactor();
        if (conditionalExpressionRefactor != null) {
            initialiseExpressionRefactor(conditionalExpressionRefactor);
            IExpr expr = createExpression(expressionAST, token);
            conditionalExpressionRefactor.evaluate(expr);
            if (conditionalExpressionRefactor.getRefactorResultList() != null) {
                addAllRefactorResult(conditionalExpressionRefactor
                        .getRefactorResultList());
            }
        }
    }

    @Override
    public void refactorExpression(AST expressionAST, Token token) {
        IExpressionRefactor expressionRefactor =
                getExpressionRefactor();
        if (expressionRefactor != null) {
            initialiseExpressionRefactor(expressionRefactor);
            IExpr expr = createExpression(expressionAST, token);
            expressionRefactor.evaluate(expr);
            if (expressionRefactor.getRefactorResultList() != null) {
                addAllRefactorResult(expressionRefactor
                        .getRefactorResultList());
            }
        }
    }

    @Override
    public void refactorMethodCall(AST expressionAST, Token token) {
        IExpressionRefactor methodCallExpressionRefactor =
                getMethodCallRefactor();
        if (methodCallExpressionRefactor != null) {
            initialiseExpressionRefactor(methodCallExpressionRefactor);
            IExpr expr = createExpression(expressionAST, token);
            methodCallExpressionRefactor.evaluate(expr);
            if (methodCallExpressionRefactor.getRefactorResultList() != null) {
                addAllRefactorResult(methodCallExpressionRefactor
                        .getRefactorResultList());
            }
        }
    }

    @Override
    public void refactorMethodDeclaration(AST methodAST, Token token) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void refactorNewExpression(AST expressionAST, Token token) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void refactorStatement(AST statementAST, Token token) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void refactorUndefinedVariableUse(AST expression, Token token) {
        IExpressionRefactor undefinedVarUseRefactor =
                getUndefinedVariableUseRefactor();
        if (undefinedVarUseRefactor != null) {
            initialiseExpressionRefactor(undefinedVarUseRefactor);
            IExpr expr = createExpression(expression, token);
            undefinedVarUseRefactor.evaluate(expr);
            if (undefinedVarUseRefactor.getRefactorResultList() != null) {
                addAllRefactorResult(undefinedVarUseRefactor
                        .getRefactorResultList());
            }
        }
    }

    @Override
    public void refactorVariableDeclaration(AST varDefAST, Token token) {
        IExpressionRefactor variableDeclarationRefactor =
                getVariableDeclarationRefactor();
        if (variableDeclarationRefactor != null) {
            initialiseExpressionRefactor(variableDeclarationRefactor);
            IExpr expr = createExpression(varDefAST, token);
            variableDeclarationRefactor.evaluate(expr);
            if (variableDeclarationRefactor.getRefactorResultList() != null) {
                addAllRefactorResult(variableDeclarationRefactor
                        .getRefactorResultList());
            }
        }
    }

    @Override
    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    @Override
    public void setScriptParser(LLkParser parser) {
        this.scriptParser = parser;
    }

    @Override
    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

    @Override
    public void setVarNameResolver(IVarNameResolver varNameResolver) {
        this.varNameResolver = varNameResolver;
    }
    
    protected LLkParser getScriptParser() {
        return this.scriptParser;
    }
    
    @Override
    public void setRefactoringInfo(RefactoringInfo info) {
        this.refactoringInfo = info;
    }
    
    public RefactoringInfo getRefactoringInfo() {
        return refactoringInfo;
    }
    
    
    protected abstract IExpressionRefactor getExpressionRefactor();

    protected abstract IExpressionRefactor getNewExpressionRefactor();

    protected abstract IExpressionRefactor getMethodCallRefactor();

    protected abstract IExpressionRefactor getMethodDefinitionRefactor();

    protected abstract IExpressionRefactor getVariableDeclarationRefactor();

    protected abstract IExpressionRefactor getConditionalExpressionRefactor();

    protected abstract IExpressionRefactor getUndefinedVariableUseRefactor();

    protected abstract IExpressionRefactor getASTTreeRefactor();

    protected abstract IRefactor getStatementRefactor();
    
    public abstract IRefactoringInfoObject getInfoObject();
    
    public abstract IExpressionFactory getExpresionFactory();
    
    protected void initialiseExpressionRefactor(
            IExpressionRefactor expressionRefactor) {
        if (expressionRefactor != null) {
            expressionRefactor.setScriptParser(getScriptParser());
            expressionRefactor.setVarNameResolver(getVarNameResolver());
            expressionRefactor.setInfoObject(getInfoObject());
        }
    }
    
    private IExpr createExpression(AST expr, Token token) {
        return getExpresionFactory().createExpr(expr, token);
    }
    
    /**
     * Override this if another DataTypeMapper is needed
     * 
     * @return IDataTypeMapper
     **/
    protected IDataTypeMapper getDataTypeMapper(){
        if(dataTypeMapper == null){
            dataTypeMapper = JScriptDataTypeMapper.getInstance();
        }
        return dataTypeMapper;
    }
    
    /**
     * Override this if another IScriptRelevantDataFactory is needed
     * 
     * @return Default IScriptRelevantDataFactory
     **/
    protected IScriptRelevantDataFactory getScriptRelevantDataFactory(){
        if(scriptRelevantDataFactory == null){
            scriptRelevantDataFactory = new DefaultScriptRelevantDataFactory();
        }
        return scriptRelevantDataFactory;
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

    public void setClassDefinitionReaders(List<JsClassDefinitionReader> classDefinitionReaders) {
        this.classDefinitionReaders = classDefinitionReaders;
    }
    
    @Override
    public List<RefactorResult> getRefactorResultList() {
        return refactorResultList;
    }
    
    @Override
    public void setRefactorResultList(List<RefactorResult> refactorResultList) {
        this.refactorResultList = refactorResultList;
    }
    
    public void addRefactorResult(RefactorResult refactorResult){
        if(refactorResultList == null){
            refactorResultList = new ArrayList<RefactorResult>(); 
        }
        refactorResultList.add(refactorResult);
    }
    
    public void addAllRefactorResult(List<RefactorResult> refactorResultList){
        if(this.refactorResultList == null){
            this.refactorResultList = new ArrayList<RefactorResult>(); 
        }
        this.refactorResultList.addAll(refactorResultList);
    }
    
    public RefactorResult evaluateExpression(AST expressionAST, Token token) {
        IExpressionRefactor exprRefactor = getExpressionRefactor();
        if (exprRefactor != null) {
            initialiseExpressionRefactor(exprRefactor);
            if (getExpresionFactory() != null) {
                IExpr expr =
                        getExpresionFactory().createExpr(expressionAST, token);
                return exprRefactor.evaluate(expr);
            }
        }
        return null;
    }
    
    public List<JsClass> getSupportedJsClasses() {
        List<JsClass> supportedJsClasses = new ArrayList<JsClass>();
        // Get the supported classes from the expression refactor
        IExpressionRefactor expressionRefactor = getExpressionRefactor();
        if (expressionRefactor != null) {
            initialiseExpressionRefactor(expressionRefactor);
            supportedJsClasses = expressionRefactor.getSupportedJsClasses();
        }
        return supportedJsClasses;
    }
   
}
