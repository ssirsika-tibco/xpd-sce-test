/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.script.ui.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.xpd.script.model.client.IScriptRelevantData;

/**
 * This class is to be implemented for the return of ScriptRelevantData
 * and complex ScriptRelevantData for a given context and contex type.
 * 
 * @author mtorres
 */
public abstract class AbstractScriptRelevantDataProvider {
 
    private String destinationName = null;
    
    private String scriptType = null;
    
    private Object context = null;
    
    /**
     * This map can used as place holder for the some custom properties. This is
     * currently used for process Scope Enumeration cache. Process package Scope
     * enumeration cache is passed on to the dataProviders by Validation Rules
     * when used in Validation Scope.
     */
    private Map<Class<?>, Object> customPropertyClassMap =
            new HashMap<Class<?>, Object>();

    /**
     * Returns the ScriptRelevantData
     **/
    public abstract List<IScriptRelevantData> getScriptRelevantDataList();
    
    /**
     * Returns the Complex ScriptRelevantData
     * 
     * @return List of complex ScriptRelevantData
     **/
    public abstract List getComplexScriptRelevantDataList();

    /**
     * Returns the name of the destination
     * 
     * @return String the name
     **/
    public String getDestinationName() {
        return destinationName;
    }

    /**
     * sets the name of the destination
     * 
     * @param String the name of the destination
     **/
    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    /**
     * Returns the script type
     * 
     * @return String the script type
     **/
    public String getScriptType() {
        return scriptType;
    }

    /**
     * setd the script type
     * 
     * @param String the script type
     **/
    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

    /**
     * Returns the context
     * 
     * @return Object the context
     **/
    public Object getContext() {
        return context;
    }

    /**
     * Sets the context
     * 
     * @param Object the context
     **/
    public void setContext(Object context) {
        this.context = context;
    }
    
    /**
     * Adds the custom property to the cache.The property Value should be of
     * Type propertyType, throws IllegalArgumentException otherwise.
     * 
     * @param propertyType
     *            , type of the property value.
     * @param propertyValue
     *            , value of the property of type propertyType.
     */
    public void setCustomPropertyClass(Class<?> propertyType, Object propertyValue) {
        try {
            // Property value should be of type propertyKey
            propertyType.cast(propertyValue);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(
                    "Proprty value should be of given Property Type", e); //$NON-NLS-1$
        }
        customPropertyClassMap.put(propertyType, propertyValue);
    }

    /**
     * Returns the property value for the given property Type.
     * 
     * @param propertyKey
     * @return
     */
    public Object getCustomPropertyClass(Class<?> propertyKey) {
        return customPropertyClassMap.get(propertyKey);
    }

}
