/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.cds.reader;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.n2.cds.script.IRestScriptRelevantData;
import com.tibco.xpd.n2.cds.script.N2JScriptTypeResolver;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IMultipleJsClassResolver;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.ITypeResolver;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * Overridden definition reader to treat multi-instance fields for REST output
 * mappings as Arrays not Lists.
 * 
 * @author nwilson
 * @since 20 Aug 2015
 */
public class CdsRestExtendedJScriptProcessDefinitionReader extends
        CdsExtendedJScriptProcessDefinitionReader {

    private ITypeResolver typeResolver = null;

    /**
     * @see com.tibco.xpd.n2.cds.reader.CdsExtendedJScriptProcessDefinitionReader#getTypeResolver()
     * 
     * @return a specific type resolver to handle Rest types.
     */
    @Override
    public ITypeResolver getTypeResolver() {
        if (typeResolver == null) {
            typeResolver = new N2JScriptTypeResolver() {
                /**
                 * @see com.tibco.xpd.n2.cds.script.N2JScriptTypeResolver#resolveFromJsAttribute(com.tibco.xpd.script.model.client.JsAttribute,
                 *      boolean,
                 *      com.tibco.xpd.script.model.client.IScriptRelevantData)
                 * 
                 * @param jsAttribute
                 * @param isMultiple
                 * @param genericContext
                 * @return
                 */
                @Override
                protected List<IScriptRelevantData> resolveFromJsAttribute(
                        JsAttribute jsAttribute, boolean isMultiple,
                        IScriptRelevantData genericContext) {
                    if (jsAttribute != null && isMultiple) {
                        return Collections
                                .singletonList(resolveMultipleArrayType(jsAttribute
                                        .getName(),
                                        getSupportedJsClasses(),
                                        null,
                                        getTypeMap()));
                    }
                    return super.resolveFromJsAttribute(jsAttribute,
                            isMultiple,
                            genericContext);
                }

                /**
                 * @see com.tibco.xpd.script.model.jscript.JScriptTypeResolver#resolveFromJsReference(com.tibco.xpd.script.model.client.JsReference,
                 *      boolean,
                 *      com.tibco.xpd.script.model.client.IScriptRelevantData)
                 * 
                 * @param jsReference
                 *            the JsReference.
                 * @param isMultiple
                 *            true if this is an array/list.
                 * @param genericContext
                 *            The context.
                 * @return The resolved type.
                 */
                @Override
                protected List<IScriptRelevantData> resolveFromJsReference(
                        JsReference jsReference, boolean isMultiple,
                        IScriptRelevantData genericContext) {
                    if (jsReference != null
                            && isMultiple
                            && genericContext instanceof IRestScriptRelevantData) {
                        Class multipleClass = null;
                        if (jsReference instanceof IMultipleJsClassResolver) {
                            multipleClass =
                                    ((IMultipleJsClassResolver) jsReference)
                                            .getMultipleClass();
                        }
                        IScriptRelevantData resolveJavaScriptStringType =
                                resolveMultipleArrayType(jsReference.getName(),
                                        getSupportedJsClasses(),
                                        multipleClass,
                                        getTypeMap());
                        if (resolveJavaScriptStringType != null) {
                            return Collections
                                    .singletonList(resolveJavaScriptStringType);
                        }

                    }
                    return super.resolveFromJsReference(jsReference,
                            isMultiple,
                            genericContext);
                }

                /**
                 * @see com.tibco.xpd.n2.cds.script.N2JScriptTypeResolver#resolveFromScriptRelevantData(com.tibco.xpd.script.model.client.IScriptRelevantData,
                 *      boolean,
                 *      com.tibco.xpd.script.model.client.IScriptRelevantData)
                 * 
                 * @param scriptRelevantData
                 *            The type to resolve.
                 * @param isMultiple
                 *            true for arrays/lists.
                 * @param genericContext
                 *            The context.
                 * @return The resolved type.
                 */
                @Override
                protected List<IScriptRelevantData> resolveFromScriptRelevantData(
                        IScriptRelevantData scriptRelevantData,
                        boolean isMultiple, IScriptRelevantData genericContext) {
                    if (scriptRelevantData instanceof IRestScriptRelevantData
                            && isMultiple) {
                        IScriptRelevantData resolveJavaScriptStringType =
                                resolveMultipleArrayType(scriptRelevantData.getName(),
                                        getSupportedJsClasses(),
                                        null,
                                        getTypeMap());
                        return Collections
                                .singletonList(resolveJavaScriptStringType);
                    }
                    return super
                            .resolveFromScriptRelevantData(scriptRelevantData,
                                    isMultiple,
                                    genericContext);
                }

                /**
                 * Similar to resolveMultipleType(), but returns Array rather
                 * than List.
                 * 
                 * @param name
                 *            The property name.
                 * @param jsClasses
                 *            JsClass instances.
                 * @param multipleClass
                 *            not used.
                 * @param typeMap
                 *            The type map.
                 * @return The resolved multiple type.
                 */
                protected IScriptRelevantData resolveMultipleArrayType(
                        String name, List<JsClass> jsClasses,
                        Class multipleClass, Map<String, String> typeMap) {

                    return JScriptUtils.resolveJavaScriptStringType(name,
                            JsConsts.ARRAY,
                            false,
                            jsClasses,
                            null,
                            getTypeMap());
                }

                /**
                 * @see com.tibco.xpd.script.model.jscript.JScriptTypeResolver#getTypeMap()
                 * 
                 * @return
                 */
                @Override
                protected Map<String, String> getTypeMap() {
                    Map<String, String> map =
                            new HashMap<String, String>(super.getTypeMap());
                    map.put("Array", "Array"); //$NON-NLS-1$//$NON-NLS-2$
                    return map;
                }
            };
            typeResolver.setSupportedJsClasses(getSupportedClasses());
        }
        return typeResolver;
    }

}
