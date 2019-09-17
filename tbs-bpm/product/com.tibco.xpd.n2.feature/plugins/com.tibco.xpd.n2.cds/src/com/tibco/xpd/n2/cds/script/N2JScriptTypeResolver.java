/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.cds.script;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.internal.client.DefaultJsEnumeration;
import com.tibco.xpd.script.model.internal.client.IDataTypeMapper;
import com.tibco.xpd.script.model.internal.client.ITypeResolution;
import com.tibco.xpd.script.model.internal.client.JsEnumeration;
import com.tibco.xpd.script.model.internal.client.JsEnumerationLiteral;
import com.tibco.xpd.script.model.jscript.JScriptTypeResolver;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * This class provides the resolution of a type
 * 
 * @author mtorres
 * 
 */
public class N2JScriptTypeResolver extends JScriptTypeResolver {

    private IDataTypeMapper dataTypeMapper = null;

    @Override
    public IDataTypeMapper getDataTypeMapper() {
        if (dataTypeMapper == null) {
            dataTypeMapper = new N2JScriptDataTypeMapper();
        }
        return dataTypeMapper;
    }

    @Override
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
                    return Collections.singletonList(
                            (IScriptRelevantData) new DefaultUMLScriptRelevantData(
                                    jsEnumeration.getName(),
                                    jsEnumeration.getName(), false,
                                    jsEnumeration));
                }
            } else {
                dataType = getAttributeDataType(jsAttribute);

                Property property =
                        JScriptUtils.getJsAttributeProperty(jsAttribute);
                Classifier propertyTypeClass = null;
                if (null != property) {
                    Type propertyType = property.getType();
                    propertyTypeClass =
                            JScriptUtils.getPropertyClass(propertyType);
                }

                if (null != property && null != propertyTypeClass) {

                    Element owner = property.getOwner();
                    if (owner instanceof Class) {
                        Class class1 = (Class) owner;
                        boolean isRestrictionOverride =
                                JScriptUtils.isRestrictionOverride(class1);
                        if (isRestrictionOverride) {
                            isMultiple = JScriptUtils.isMultiple(jsAttribute,
                                    class1);
                            isAttributeMultiple = isMultiple;
                        }

                    }
                } else {

                    isAttributeMultiple = jsAttribute.isMultiple();
                }
            }
            IScriptRelevantData resolveJavaScriptStringType = null;
            if (isMultiple) {
                resolveJavaScriptStringType =
                        resolveMultipleType(jsAttribute.getName(), getSupportedJsClasses(), null, getTypeMap());
                if (resolveJavaScriptStringType instanceof ITypeResolution) {
                    ITypeResolution res = (ITypeResolution) resolveJavaScriptStringType;
                    res.setExtendedInfo(jsAttribute);
                }
            } else if (JScriptUtils.isGenericType(dataType)) {
                resolveJavaScriptStringType =
                        resolveGenericContext(genericContext);
            } else {
                resolveJavaScriptStringType = JScriptUtils
                        .resolveJavaScriptStringType(jsAttribute.getName(),
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

    protected String getAttributeDataType(JsAttribute jsAttribute) {
        String dataType = null;
        if (jsAttribute != null) {
            dataType = JScriptUtils.getJsAttributeBaseDataType(jsAttribute);
        }
        return dataType;
    }

    /**
     * resolves to a list type
     * 
     * @see com.tibco.xpd.script.model.jscript.JScriptTypeResolver#resolveMultipleType(java.lang.String,
     *      java.util.List, org.eclipse.uml2.uml.Class, java.util.Map)
     * 
     * @param name
     * @param jsClasses
     * @param multipleClass
     * @param typeMap
     * @return
     */
    @Override
    protected IScriptRelevantData resolveMultipleType(String name,
            List<JsClass> jsClasses, Class multipleClass,
            Map<String, String> typeMap) {

        return JScriptUtils
                .resolveJavaScriptStringType(name, JsConsts.ARRAY, false, getSupportedJsClasses(), null, getTypeMap());
    }

}
