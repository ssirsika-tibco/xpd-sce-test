/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import java.util.List;

import com.tibco.xpd.script.model.internal.client.IDataTypeMapper;

/**
 * This interface provides the resolution of a type
 * 
 * @author mtorres
 * 
 */
public interface ITypeResolver {

    /**
     * @return the type
     * 
     **/
    List<IScriptRelevantData> resolveType(Object input, boolean isMultiple, IScriptRelevantData genericContext);

    /**
     * @return the supported Js classes
     * 
     **/
    List<JsClass> getSupportedJsClasses();

    /**
     * @param the
     *            supported Js classes
     * 
     **/
    void setSupportedJsClasses(List<JsClass> supportedJsClasses);

    /**
     * @return
     *      The data type mapper
     */
    IDataTypeMapper getDataTypeMapper();

}
