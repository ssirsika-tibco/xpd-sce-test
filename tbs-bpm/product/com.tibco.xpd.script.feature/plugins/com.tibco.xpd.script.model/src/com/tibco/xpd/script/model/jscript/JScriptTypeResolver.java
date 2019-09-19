/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.script.model.jscript;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultJsClass;
import com.tibco.xpd.script.model.client.DefaultJsMethod;
import com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.IMultipleJsClassResolver;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.ITypeResolver;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.client.globaldata.CaseJsMethodParam;
import com.tibco.xpd.script.model.internal.client.DefaultJsEnumeration;
import com.tibco.xpd.script.model.internal.client.IDataTypeMapper;
import com.tibco.xpd.script.model.internal.client.ITypeResolution;
import com.tibco.xpd.script.model.internal.client.JsEnumeration;
import com.tibco.xpd.script.model.internal.client.JsEnumerationLiteral;
import com.tibco.xpd.script.model.internal.client.PojoJsMethodParam;
import com.tibco.xpd.script.model.internal.jscript.JScriptDataTypeMapper;

/**
 * This class provides the resolution of a type
 * 
 * @author mtorres
 * 
 */
public class JScriptTypeResolver implements ITypeResolver {

    private List<JsClass> supportedJsClasses = null;

    private IDataTypeMapper dataTypeMapper = null;

    @Override
    public List<IScriptRelevantData> resolveType(Object input,
            boolean isMultiple, IScriptRelevantData genericContext) {
        if (input instanceof IScriptRelevantData) {
            IScriptRelevantData scriptRelevantData =
                    (IScriptRelevantData) input;
            return resolveFromScriptRelevantData(scriptRelevantData,
                    isMultiple,
                    genericContext);
        } else if (input instanceof JsAttribute) {
            JsAttribute jsAttribute = (JsAttribute) input;
            return resolveFromJsAttribute(jsAttribute,
                    isMultiple,
                    genericContext);
        } else if (input instanceof JsReference) {
            JsReference jsReference = (JsReference) input;
            return resolveFromJsReference(jsReference,
                    isMultiple,
                    genericContext);
        } else if (input instanceof JsMethod) {
            JsMethod jsMethod = (JsMethod) input;
            return resolveFromJsMethod(jsMethod, isMultiple, genericContext);
        }
        return null;
    }

    protected List<IScriptRelevantData> resolveFromScriptRelevantData(
            IScriptRelevantData scriptRelevantData, boolean isMultiple,
            IScriptRelevantData genericContext) {
        if (scriptRelevantData != null) {
            IScriptRelevantData resolveJavaScriptStringType = null;
            if (isMultiple) {
                resolveJavaScriptStringType =
                        resolveMultipleType(scriptRelevantData.getName(),
                                getSupportedJsClasses(),
                                null,
                                getTypeMap());
            } else if (JScriptUtils.isGenericType(scriptRelevantData.getType())) {
                resolveJavaScriptStringType =
                        resolveGenericContext(genericContext);
            } else if (scriptRelevantData instanceof IUMLScriptRelevantData) {
                resolveJavaScriptStringType = scriptRelevantData;
            } else {
                String name = scriptRelevantData.getName();
                String type = scriptRelevantData.getType();
                resolveJavaScriptStringType =
                        JScriptUtils.resolveJavaScriptStringType(name,
                                type,
                                scriptRelevantData.isArray(),
                                getSupportedJsClasses(),
                                null,
                                getTypeMap());
            }
            if (resolveJavaScriptStringType instanceof IUMLScriptRelevantData) {
                return Collections.singletonList(resolveJavaScriptStringType);
            }
        }
        return null;
    }

    protected List<IScriptRelevantData> resolveFromJsAttribute(
            JsAttribute jsAttribute, boolean isMultiple,
            IScriptRelevantData genericContext) {
        if (jsAttribute != null) {
            String dataType = null;
            boolean isAttributeMultiple = false;
            if (jsAttribute instanceof JsEnumerationLiteral) {
                JsEnumerationLiteral jsEnumerationLiteral =
                        (JsEnumerationLiteral) jsAttribute;
                if (jsEnumerationLiteral.getOwner() != null) {
                    Enumeration owner = jsEnumerationLiteral.getOwner();
                    JsEnumeration jsEnumeration =
                            new DefaultJsEnumeration(owner);
                    jsEnumeration
                            .setContentAssistIconProvider(jsEnumerationLiteral
                                    .getContentAssistIconProvider());
                    return Collections
                            .singletonList((IScriptRelevantData) new DefaultUMLScriptRelevantData(
                                    jsEnumeration.getName(), jsEnumeration
                                            .getName(), false, jsEnumeration));
                }
            } else {
                dataType = JScriptUtils.getJsAttributeBaseDataType(jsAttribute);
                isAttributeMultiple = jsAttribute.isMultiple();
            }
            IScriptRelevantData resolveJavaScriptStringType = null;
            if (isMultiple) {
                resolveJavaScriptStringType =
                        resolveMultipleType(jsAttribute.getName(),
                                getSupportedJsClasses(),
                                null,
                                getTypeMap());
            } else if (JScriptUtils.isGenericType(dataType)) {
                resolveJavaScriptStringType =
                        resolveGenericContext(genericContext);
            } else {
                resolveJavaScriptStringType =
                        JScriptUtils.resolveJavaScriptStringType(jsAttribute
                                .getName(),
                                dataType,
                                isAttributeMultiple,
                                getSupportedJsClasses(),
                                null,
                                getTypeMap());
            }
            if (resolveJavaScriptStringType instanceof IUMLScriptRelevantData) {
                return Collections.singletonList(resolveJavaScriptStringType);
            }
        }
        return null;
    }

    protected List<IScriptRelevantData> resolveFromJsReference(
            JsReference jsReference, boolean isMultiple,
            IScriptRelevantData genericContext) {
        if (jsReference != null) {
            IScriptRelevantData resolveJavaScriptStringType = null;
            String dataType = null;
            JsClass referencedJsClass = jsReference.getReferencedJsClass();
            if (referencedJsClass != null) {
                dataType = referencedJsClass.getName();
            }
            if (isMultiple) {
                Class multipleClass = null;
                if (jsReference instanceof IMultipleJsClassResolver) {
                    multipleClass =
                            ((IMultipleJsClassResolver) jsReference)
                                    .getMultipleClass();
                }
                resolveJavaScriptStringType =
                        resolveMultipleType(jsReference.getName(),
                                getSupportedJsClasses(),
                                multipleClass,
                                getTypeMap());
            } else if (JScriptUtils.isGenericType(dataType)) {
                resolveJavaScriptStringType =
                        resolveGenericContext(genericContext);
            } else {
                boolean isReferenceMultiple = jsReference.isMultiple();
                resolveJavaScriptStringType =
                        new DefaultUMLScriptRelevantData(jsReference.getName(),
                                dataType, isReferenceMultiple,
                                referencedJsClass);
            }
            if (resolveJavaScriptStringType instanceof IUMLScriptRelevantData) {
                return Collections.singletonList(resolveJavaScriptStringType);
            }
        }
        return null;
    }

    protected List<IScriptRelevantData> resolveFromJsMethod(JsMethod jsMethod,
            boolean isMultiple, IScriptRelevantData genericContext) {
        if (jsMethod != null) {
            IScriptRelevantData resolveJavaScriptStringType = null;
            String propertyName = null;
            String dataType = null;
            Type type = null;
            JsMethodParam parameter = jsMethod.getReturnType();
            if (parameter != null) {
                propertyName = jsMethod.getName();
                JScriptGenericsService gs = new JScriptGenericsService();
                if (gs.isGeneric(parameter)) {
                    Map<String, Type> typeMap = gs.createTypeMap(genericContext, parameter);
                    type = typeMap.get(JScriptUtils.getJsMethodParamBaseDataType(parameter));
                    dataType = type == null ? JsConsts.UNDEFINED_DATA_TYPE : type.getName();
                } else {
                    type = JScriptUtils.getReturnedClass(parameter);
                    dataType = JScriptUtils.getJsMethodParamBaseDataType(parameter);
                }
            }
            if (isMultiple) {
                resolveJavaScriptStringType =
                        resolveMultipleType(jsMethod.getName(),
                                getSupportedJsClasses(),
                                null,
                                getTypeMap());
            } else if (JScriptUtils.isGenericType(dataType)) {
                resolveJavaScriptStringType =
                        resolveGenericContext(genericContext);
            } else if (type instanceof Class) {
                Class umlClass = (Class) type;
                DefaultJsClass jsClass = new DefaultJsClass(umlClass);
                if (jsMethod instanceof DefaultJsMethod
                        && ((DefaultJsMethod) jsMethod).getContentAssistIconProvider() != null) {
                    jsClass.setContentAssistIconProvider(((DefaultJsMethod) jsMethod).getContentAssistIconProvider());
                } else {
                    jsClass.setContentAssistIconProvider(JScriptUtils.getJsContentAssistIconProvider());
                }
                resolveJavaScriptStringType = new DefaultUMLScriptRelevantData(propertyName, umlClass.getName(),
                        jsMethod.isMultiple(), jsClass);
            } else if (parameter instanceof PojoJsMethodParam
                    && ((PojoJsMethodParam) parameter).isReferenceReturnType()) {
                JsClass referencedReturnType =
                        ((PojoJsMethodParam) parameter)
                                .getReferencedReturnType();
                if (referencedReturnType != null) {
                    resolveJavaScriptStringType =
                            new DefaultUMLScriptRelevantData(propertyName,
                                    referencedReturnType.getName(),
                                    jsMethod.isMultiple(), referencedReturnType);
                }
            } else if (parameter instanceof CaseJsMethodParam
                    && parameter.getScriptRelevantData() != null) {
                /*
                 * XPD-3129 ScriptrelevantData is used to resolve local var of
                 * GlobalRef types which are otherwise resolved using UMLClass
                 */
                resolveJavaScriptStringType = parameter.getScriptRelevantData();
            } else {

                resolveJavaScriptStringType =
                        JScriptUtils.resolveJavaScriptStringType(propertyName,
                                dataType,
                                jsMethod.isMultiple(),
                                getSupportedJsClasses(),
                                null,
                                getTypeMap());
            }
            if (resolveJavaScriptStringType instanceof IUMLScriptRelevantData) {
                return Collections.singletonList(resolveJavaScriptStringType);
            }
        }
        return null;
    }

    @Override
    public List<JsClass> getSupportedJsClasses() {
        return supportedJsClasses;
    }

    @Override
    public void setSupportedJsClasses(List<JsClass> supportedJsClasses) {
        this.supportedJsClasses = supportedJsClasses;
    }

    @Override
    public IDataTypeMapper getDataTypeMapper() {
        if (dataTypeMapper == null) {
            dataTypeMapper = JScriptDataTypeMapper.getInstance();
        }
        return dataTypeMapper;
    }

    protected Map<String, String> getTypeMap() {
        IDataTypeMapper dtm = getDataTypeMapper();
        if (dtm instanceof JScriptDataTypeMapper) {
            return ((JScriptDataTypeMapper) dtm).getJavaScriptType();
        }
        return Collections.emptyMap();
    }

    protected IScriptRelevantData resolveMultipleType(String name,
            List<JsClass> jsClasses, Class multipleClass,
            Map<String, String> typeMap) {
        return JScriptUtils.resolveJavaScriptStringType(name,
                JsConsts.ARRAY,
                false,
                getSupportedJsClasses(),
                null,
                getTypeMap());
    }

    protected IScriptRelevantData resolveGenericContext(
            IScriptRelevantData genericContext) {
        IScriptRelevantData resolveJavaScriptStringType = null;
        if (genericContext instanceof IUMLScriptRelevantData) {
            resolveJavaScriptStringType = genericContext;
        } else if (genericContext != null) {
            resolveJavaScriptStringType =
                    JScriptUtils.resolveJavaScriptStringType(genericContext
                            .getName(),
                            genericContext.getType(),
                            genericContext.isArray(),
                            getSupportedJsClasses(),
                            null,
                            getTypeMap());
            if (resolveJavaScriptStringType instanceof ITypeResolution
                    && genericContext instanceof ITypeResolution) {
                ((ITypeResolution) resolveJavaScriptStringType)
                        .setExtendedInfo(((ITypeResolution) genericContext)
                                .getExtendedInfo());
                ((ITypeResolution) resolveJavaScriptStringType)
                        .setGenericContextType(((ITypeResolution) genericContext)
                                .getGenericContextType());
                ((ITypeResolution) resolveJavaScriptStringType)
                        .setReadOnly(((ITypeResolution) genericContext)
                                .isReadOnly());
                ((ITypeResolution) resolveJavaScriptStringType)
                        .setTypesResolution((((ITypeResolution) genericContext)
                                .getTypesResolution()));
            }
        }
        return resolveJavaScriptStringType;
    }
}
