/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.cds.script;

import com.tibco.xpd.script.model.client.DefaultScriptRelevantDataFactory;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;

/**
 * ScriptRelevantDataFactory specifically for Rest payload types.
 * 
 * @author nwilson
 * @since 21 Aug 2015
 */
public class RestUMLScriptRelevantDataFactory extends
        DefaultScriptRelevantDataFactory {

    /**
     * @see com.tibco.xpd.script.model.client.DefaultScriptRelevantDataFactory#createScriptRelevantData(java.lang.String,
     *      java.lang.String, boolean,
     *      com.tibco.xpd.script.model.client.IScriptRelevantData, boolean,
     *      java.lang.Object)
     * 
     * @param name
     * @param type
     * @param isArray
     * @param genericContext
     * @param isReadOnly
     * @param extendedInfo
     * @return
     */
    @Override
    public IScriptRelevantData createScriptRelevantData(String name,
            String type, boolean isArray, IScriptRelevantData genericContext,
            boolean isReadOnly, Object extendedInfo) {
        if (genericContext instanceof RestUMLScriptRelevantData) {
            if (name != null && type != null) {
                RestScriptRelevantData scriptRelevantData =
                        new RestScriptRelevantData(name, type, isArray);

                scriptRelevantData.setGenericContextType(genericContext);

                scriptRelevantData.setReadOnly(isReadOnly);
                scriptRelevantData.setExtendedInfo(extendedInfo);
                return scriptRelevantData;
            }
        }
        return super.createScriptRelevantData(name,
                type,
                isArray,
                genericContext,
                isReadOnly,
                extendedInfo);
    }

    /**
     * @see com.tibco.xpd.script.model.client.DefaultScriptRelevantDataFactory#createUMLScriptRelevantData(java.lang.String,
     *      java.lang.String, boolean,
     *      com.tibco.xpd.script.model.client.JsClass,
     *      com.tibco.xpd.script.model.client.IScriptRelevantData, boolean,
     *      java.lang.Object)
     * 
     * @param name
     * @param className
     * @param isArray
     * @param jsClass
     * @param genericContext
     * @param isReadOnly
     * @param extendedInfo
     * @return
     */
    @Override
    public IUMLScriptRelevantData createUMLScriptRelevantData(String name,
            String className, boolean isArray, JsClass jsClass,
            IScriptRelevantData genericContext, boolean isReadOnly,
            Object extendedInfo) {
        if (jsClass instanceof RestJsClass) {
            RestUMLScriptRelevantData umlScriptRelevantData =
                    new RestUMLScriptRelevantData(name, className, isArray,
                            jsClass);
            umlScriptRelevantData.setGenericContextType(genericContext);
            umlScriptRelevantData.setReadOnly(isReadOnly);
            umlScriptRelevantData.setExtendedInfo(extendedInfo);
            return umlScriptRelevantData;
        }
        return super.createUMLScriptRelevantData(name,
                className,
                isArray,
                jsClass,
                genericContext,
                isReadOnly,
                extendedInfo);
    }

}
