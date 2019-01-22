/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.refactoring.jscript;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import antlr.LLkParser;

import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantDataFactory;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.model.internal.client.IGlobalDataDefinitionReader;
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.expr.IRefactoringInfoObject;
import com.tibco.xpd.script.parser.internal.refactoring.IExpressionRefactor;
import com.tibco.xpd.script.parser.internal.refactoring.RefactorResult;
import com.tibco.xpd.script.parser.internal.refactoring.RefactoringInfo;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.ISymbolTable;

/**
 * Abstract Expression Refactor class
 * 
 * @author mtorres
 * 
 */
public abstract class AbstractExpressionRefactor implements IExpressionRefactor {

    private IRefactoringInfoObject infoObject;

    private LLkParser scriptParser;

    private IVarNameResolver varNameResolver;

    private List<RefactorResult> refactorResultList;

    @Override
    public IRefactoringInfoObject getInfoObject() {
        return infoObject;
    }

    @Override
    public void setInfoObject(IRefactoringInfoObject infoObject) {
        this.infoObject = infoObject;
    }

    @Override
    public void setScriptParser(LLkParser scriptParser) {
        this.scriptParser = scriptParser;
    }

    @Override
    public void setVarNameResolver(IVarNameResolver varNameResolver) {
        this.varNameResolver = varNameResolver;
    }

    public List<JsClass> getSupportedJsClasses() {
        List<JsClassDefinitionReader> classDefinitionReaders =
                getClassDefinitionReader();
        if (classDefinitionReaders != null && !classDefinitionReaders.isEmpty()) {
            List<JsClass> allSupportedClasses = new ArrayList<JsClass>();
            for (JsClassDefinitionReader jsClassDefinitionReader : classDefinitionReaders) {
                List<JsClass> supportedClasses =
                        jsClassDefinitionReader.getSupportedClasses();
                if (supportedClasses != null) {
                    allSupportedClasses.addAll(supportedClasses);
                }
            }
            return allSupportedClasses;
        }
        return Collections.emptyList();
    }

    protected List<JsClassDefinitionReader> getClassDefinitionReader() {
        List<JsClassDefinitionReader> classDefinitionReader =
                Collections.EMPTY_LIST;
        if (getInfoObject() != null) {
            classDefinitionReader = getInfoObject().getClassDefinitionReaders();
        }
        if (classDefinitionReader.isEmpty()) {
            throw new IllegalStateException(Messages.Utility_8);
        }
        return classDefinitionReader;
    }

    protected List<JsAttribute> getSupportedGlobalProperties() {
        List<JsClassDefinitionReader> classDefinitionReaders =
                getClassDefinitionReader();
        if (classDefinitionReaders != null && !classDefinitionReaders.isEmpty()) {
            List<JsAttribute> allSupportedGlobalProperties =
                    new ArrayList<JsAttribute>();
            for (JsClassDefinitionReader jsClassDefinitionReader : classDefinitionReaders) {
                if (jsClassDefinitionReader instanceof IGlobalDataDefinitionReader) {
                    IGlobalDataDefinitionReader globalDataDefinitionReader =
                            (IGlobalDataDefinitionReader) jsClassDefinitionReader;
                    List<JsAttribute> supportedProperties =
                            globalDataDefinitionReader
                                    .getSupportedGlobalProperties();
                    if (supportedProperties != null
                            && !supportedProperties.isEmpty()) {
                        allSupportedGlobalProperties
                                .addAll(supportedProperties);
                    }
                }
            }
            return allSupportedGlobalProperties;
        }
        return Collections.emptyList();
    }

    protected Map<String, IScriptRelevantData> getSupportedScriptRelevantDataMap() {
        Map<String, IScriptRelevantData> supportedScriptRelevantDataMap =
                new HashMap<String, IScriptRelevantData>();
        ISymbolTable symbolTable = getSymbolTable();
        if (symbolTable != null) {
            Map<String, IScriptRelevantData> scriptRelevantDataMap =
                    symbolTable.getScriptRelevantDataTypeMap();

            supportedScriptRelevantDataMap.putAll(scriptRelevantDataMap);
        }
        return supportedScriptRelevantDataMap;
    }

    protected ISymbolTable getSymbolTable() {
        if (getInfoObject() != null
                && getInfoObject().getScriptParser() != null
                && getInfoObject().getScriptParser().getScriptParser() instanceof JScriptParser) {
            return ((JScriptParser) getInfoObject().getScriptParser()
                    .getScriptParser()).getSymbolTable();
        }
        return null;
    }

    protected Map<String, IScriptRelevantData> getLocalVariablesMap() {
        Map<String, IScriptRelevantData> localVariablesMap =
                new HashMap<String, IScriptRelevantData>();
        ISymbolTable symbolTable = getSymbolTable();
        if (symbolTable != null) {
            Map<String, IScriptRelevantData> tempLocalVariables =
                    symbolTable.getLocalVariableMap();
            if (tempLocalVariables != null) {
                return tempLocalVariables;
            }
        }
        return localVariablesMap;
    }

    protected Map<String, IScriptRelevantData> getLocalMethodsMap() {
        Map<String, IScriptRelevantData> localMethodsMap =
                new HashMap<String, IScriptRelevantData>();
        ISymbolTable symbolTable = getSymbolTable();
        if (symbolTable != null) {
            Map<String, IScriptRelevantData> tempLocalMethods =
                    symbolTable.getLocalMethodMap();
            if (tempLocalMethods != null) {
                return tempLocalMethods;
            }
        }
        return localMethodsMap;
    }

    protected IScriptRelevantDataFactory getScriptRelevantDataFactory() {
        if (getInfoObject() != null
                && getInfoObject().getScriptRelevantDataFactory() != null) {
            return getInfoObject().getScriptRelevantDataFactory();
        }
        return null;
    }

    protected RefactoringInfo getRefactoringInfo() {
        if (getInfoObject() != null) {
            return getInfoObject().getRefactoringInfo();
        }
        return null;
    }

    /**
     * This method should be called to delegate the evaluation of an expression
     * 
     *@param expr
     *@param token
     * 
     * @return {@link IValidateResult}
     **/
    public RefactorResult delegateEvaluateExpression(Object expr, Object token) {
        IExpr createdExpression = createExpression(expr, token);
        if (createdExpression != null) {
            if (infoObject != null
                    && infoObject.getExpresionRefactorFactory() != null) {
                IExpressionRefactor expresionRefactor =
                        infoObject.getExpresionRefactorFactory()
                                .getExpressionRefactor(createdExpression);
                if (expresionRefactor != null) {
                    expresionRefactor.setInfoObject(getInfoObject());
                    RefactorResult evaluate =
                            expresionRefactor.evaluate(createdExpression);
                    if (expresionRefactor.getRefactorResultList() != null) {
                        getRefactorResultList().addAll(expresionRefactor
                                .getRefactorResultList());
                    }
                    return evaluate;
                } else {
                    // TODO flag an error here
                }
            } else {
                // TODO flag an error here
            }
        }
        return null;
    }

    protected IExpr createExpression(Object expr, Object token) {
        if (infoObject != null && infoObject.getExpressionFactory() != null) {
            return infoObject.getExpressionFactory().createExpr(expr, token);
        }
        return null;
    }

    public List<RefactorResult> getRefactorResultList() {
        if (refactorResultList == null) {
            refactorResultList = new ArrayList<RefactorResult>();
        }
        return refactorResultList;
    }

}
