/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.script.parser.internal.refactoring.jscript;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.script.model.client.IScriptRelevantDataFactory;
import com.tibco.xpd.script.model.client.ITypeResolver;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.model.internal.client.IDataTypeMapper;
import com.tibco.xpd.script.model.internal.client.ITypeResolverProvider;
import com.tibco.xpd.script.parser.internal.expr.IInfoObject;
import com.tibco.xpd.script.parser.internal.expr.IRefactoringInfoObject;
import com.tibco.xpd.script.parser.internal.expr.IScriptParser;
import com.tibco.xpd.script.parser.internal.refactoring.IExpressionRefactorFactory;
import com.tibco.xpd.script.parser.internal.refactoring.RefactoringInfo;
import com.tibco.xpd.script.parser.internal.util.ExpressionUtil;
import com.tibco.xpd.script.parser.internal.validator.IExpressionFactory;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;

/**
 * see {@link IInfoObject}
 * 
 * @author mtorres
 * 
 */
public class JScriptRefactoringInfoObject implements IRefactoringInfoObject {

    private String destinationName;

    private String scriptType;

    protected List<JsClassDefinitionReader> classDefinitionReaders;

    private IExpressionFactory expressionFactory;

    private IExpressionRefactorFactory expresionRefactorFactory;
    
    private IScriptParser scriptParser;
    
    private IVarNameResolver varNameResolver;
    
    private IDataTypeMapper dataTypeMapper;
    
    private IScriptRelevantDataFactory scriptRelevantDataFactory;
    
    private List<ITypeResolver> dynamicTypeResolvers;
    
    private List<ITypeResolver> typeResolvers;
    
    private RefactoringInfo refactoringInfo;
    
    private boolean validateGlobalFunctions;

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDestinationName() {
        return this.destinationName;
    }

    public String getScriptType() {
        return scriptType;
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

    public List<JsClassDefinitionReader> getClassDefinitionReaders() {
        if(classDefinitionReaders == null){
            classDefinitionReaders = ExpressionUtil.getClassDefintionReader();
        }
        return classDefinitionReaders;
    }
    
    public void setClassDefinitionReaders(
            List<JsClassDefinitionReader> classDefinitionReaders) {
        this.classDefinitionReaders = classDefinitionReaders;
    }

    public IExpressionFactory getExpressionFactory() {
        return expressionFactory;
    }

    public void setExpressionFactory(IExpressionFactory expressionFactory) {
        this.expressionFactory = expressionFactory;
    }

    public void setExpresionRefactorFactory(
            IExpressionRefactorFactory expresionRefactorFactory) {
        this.expresionRefactorFactory = expresionRefactorFactory;
    }

    public IExpressionRefactorFactory getExpresionRefactorFactory() {
        return expresionRefactorFactory;
    }

    public IScriptParser getScriptParser() {
        return scriptParser;
    }

    public void setScriptParser(IScriptParser scriptParser) {
        this.scriptParser = scriptParser;
    }

    public IVarNameResolver getVarNameResolver() {
        return varNameResolver;
    }

    public void setVarNameResolver(IVarNameResolver varNameResolver) {
        this.varNameResolver = varNameResolver;
    }

    public IDataTypeMapper getDataTypeMapper() {
        return dataTypeMapper;
    }

    public void setDataTypeMapper(IDataTypeMapper dataTypeMapper) {
        this.dataTypeMapper = dataTypeMapper;
    }

    public IScriptRelevantDataFactory getScriptRelevantDataFactory() {
        return scriptRelevantDataFactory;
    }

    public void setScriptRelevantDataFactory(
            IScriptRelevantDataFactory scriptRelevantDataFactory) {
        this.scriptRelevantDataFactory = scriptRelevantDataFactory;
    }

    public List<ITypeResolver> getTypeResolvers() {
        if (typeResolvers == null) {
            typeResolvers = new ArrayList<ITypeResolver>();
            // Add static Type Resolvers
            List<JsClassDefinitionReader> classDefinitionReaders =
                    getClassDefinitionReaders();
            if (classDefinitionReaders != null
                    && !classDefinitionReaders.isEmpty()) {
                for (JsClassDefinitionReader jsClassDefinitionReader : classDefinitionReaders) {
                    if (jsClassDefinitionReader instanceof ITypeResolverProvider) {
                        ITypeResolverProvider typeResolverProvider =
                                (ITypeResolverProvider) jsClassDefinitionReader;
                        if (typeResolverProvider.getTypeResolver() != null) {
                            typeResolvers.add(typeResolverProvider
                                    .getTypeResolver());
                        }
                    }
                }
            }
            // Add dynamic Type Resolvers
            if (dynamicTypeResolvers != null) {
                typeResolvers.addAll(dynamicTypeResolvers);
            }
        }
        return typeResolvers;
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
    
    public RefactoringInfo getRefactoringInfo() {
        return refactoringInfo;
    }
    
    public void setRefactoringInfo(RefactoringInfo refactoringInfo) {
        this.refactoringInfo = refactoringInfo;
    }

}
