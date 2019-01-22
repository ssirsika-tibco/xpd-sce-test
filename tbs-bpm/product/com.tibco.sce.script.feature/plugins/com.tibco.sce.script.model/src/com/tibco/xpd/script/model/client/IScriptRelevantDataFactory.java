/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import org.eclipse.uml2.uml.Class;

/**
 * Factory interface for the creation of IScriptRelevantData
 * 
 * @author mtorres
 * 
 */
public interface IScriptRelevantDataFactory {

    /**
     * Creates a IScriptRelevantData
     * 
     * @param name the name
     * @param type the type
     * @param isArray if is an Array
     * @param genericContext the generic context
     * @param isReadOnly if is read only
     * @param extendedInfo any extended information
     * @return
     *      a {@link IScriptRelevantData}
     */
    IScriptRelevantData createScriptRelevantData(String name, String type, boolean isArray, IScriptRelevantData genericContext, boolean isReadOnly, Object extendedInfo);

    /**
     * Creates a IUMLScriptRelevantData
     * @param name the name
     * @param className the class name
     * @param isArray if is an array
     * @param jsClass the jsClass
     * @param genericContext the generic context
     * @param isReadOnly if is read only
     * @param extendedInfo any extended information
     * @return
     *      a {@link IScriptRelevantData}
     */
    IUMLScriptRelevantData createUMLScriptRelevantData(String name, String className, boolean isArray, JsClass jsClass, IScriptRelevantData genericContext, boolean isReadOnly, Object extendedInfo);
    
    /**
     * 
     * @param name the name
     * @param isArray if is an array
     * @param umlClass the umlClass
     * @param genericContext the generic context
     * @param isReadOnly if is read only
     * @param extendedInfo any extended information
     * @return
     */
    IUMLScriptRelevantData createUMLScriptRelevantData(String name, boolean isArray, Class umlClass, IScriptRelevantData genericContext, boolean isReadOnly, Object extendedInfo);
}
