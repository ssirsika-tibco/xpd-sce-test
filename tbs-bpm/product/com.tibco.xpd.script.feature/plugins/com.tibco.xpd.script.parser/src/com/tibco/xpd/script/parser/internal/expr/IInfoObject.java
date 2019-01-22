/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.script.parser.internal.expr;

import java.util.List;

import com.tibco.xpd.script.model.client.IScriptRelevantDataFactory;
import com.tibco.xpd.script.model.client.ITypeResolver;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.model.internal.client.IDataTypeMapper;
import com.tibco.xpd.script.parser.internal.validator.IExpressionFactory;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.IExpressionValidatorFactory;

/**
 * 
 * Interface that provides information to the expression validators
 * 
 * @author mtorres
 */
public interface IInfoObject {
   
    /**
     * Returns the destination name
     * 
     * @return the destination name
     */
    String getDestinationName();
    
    /**
     * Sets the destination name 
     * 
     * @param destinationName
     */
    void setDestinationName(String destinationName);

    /**
     * Returns the Script type
     * 
     * @return script type
     */
    String getScriptType();

    /**
     * Sets the script type
     * 
     * @param scriptType
     */
    void setScriptType(String scriptType);
    
    /**
     * Returns the expression factory
     * 
     * @return {@link IExpressionFactory}
     */
    IExpressionFactory getExpressionFactory();
    
    /**
     * Sets the expression Factory 
     * 
     * @param expressionFactory
     */
    void setExpressionFactory(IExpressionFactory expressionFactory);

    /**
     * Returns the {@link IExpressionValidatorFactory}
     * @return
     */
    IExpressionValidatorFactory getExpresionValidatorFactory();

    /**
     * Sets the expression validator factory
     * 
     * @param expressionValidatorFactory
     */
    void setExpresionValidatorFactory(IExpressionValidatorFactory expressionValidatorFactory);
    
    /**
     * Returns the ITypeResolver
     * 
     * @return {@link ITypeResolver}
     */
    List<ITypeResolver> getTypeResolvers();
    
    /**
     * Sets the dynamic type resolvers
     * 
     * @param dynamicTypeResolvers
     */
    void setDynamicTypeResolvers(List<ITypeResolver> dynamicTypeResolvers);
    
    /**
     * Returns the classDefinitionReaders
     * 
     * @return the readers
     */
    List<JsClassDefinitionReader> getClassDefinitionReaders();
    
    /**
     * Sets the class definition readers
     * 
     * @param classDefinitionReaders
     */
    void setClassDefinitionReaders(List<JsClassDefinitionReader> classDefinitionReaders);
    
    /**
     * Sets the script parser 
     * 
     * @param scriptParser
     */
    void setScriptParser(IScriptParser scriptParser);
    
    /**
     * Returns the Script Parser
     * 
     * @return scriptParser
     */
    IScriptParser getScriptParser();
    
    /**
     * Sets the var name resolver 
     * 
     * @param varNameResolver
     */
    void setVarNameResolver(IVarNameResolver varNameResolver);

    /**
     * Returns the var name resolver
     * 
     * @return varNameResolver
     */
    IVarNameResolver getVarNameResolver();
    
    /**
     * Returns the Data Type Mapper
     * 
     * @return dataTypeMapper
     */
    IDataTypeMapper getDataTypeMapper();
    
    /**
     * Sets the Data  type mapper 
     * 
     * @param dataTypeMapper
     */
    void setDataTypeMapper(IDataTypeMapper dataTypeMapper);
    
    /**
     * Returns the script relevant data factory
     * 
     * @return script relevant data factory
     */
    IScriptRelevantDataFactory getScriptRelevantDataFactory();
    
    /**
     * Sets the script relevant data factory
     * 
     * @param scriptRelevantData
     */
    void setScriptRelevantDataFactory(IScriptRelevantDataFactory scriptRelevantData);
    
    /**
     * Returns if should validate global functions
     * 
     * @return
     */
    boolean isValidateGlobalFunctions();

    /**
     * Sets if it should validate global functions
     * 
     * @param validateGlobalFunctions
     */
    void setValidateGlobalFunctions(boolean validateGlobalFunctions);
    
}
