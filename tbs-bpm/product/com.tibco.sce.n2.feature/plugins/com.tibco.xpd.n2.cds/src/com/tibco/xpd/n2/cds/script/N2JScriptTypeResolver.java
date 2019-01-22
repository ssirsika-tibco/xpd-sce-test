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
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.IUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.globaldata.CaseRefPaginatedListJsClass;
import com.tibco.xpd.script.model.client.globaldata.CaseUMLScriptRelevantData;
import com.tibco.xpd.script.model.internal.client.DefaultJsEnumeration;
import com.tibco.xpd.script.model.internal.client.IDataTypeMapper;
import com.tibco.xpd.script.model.internal.client.IUMLElement;
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
                    return Collections
                            .singletonList((IScriptRelevantData) new DefaultUMLScriptRelevantData(
                                    jsEnumeration.getName(), jsEnumeration
                                            .getName(), false, jsEnumeration));
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
                            isMultiple =
                                    JScriptUtils
                                            .isMultiple(jsAttribute, class1);
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

    protected String getAttributeDataType(JsAttribute jsAttribute) {
        String dataType = null;
        if (jsAttribute != null) {
            dataType = JScriptUtils.getJsAttributeBaseDataType(jsAttribute);
            if (dataType != null
                    && (dataType.equals(JsConsts.INTEGER) || dataType
                            .equals(JsConsts.DECIMAL))) {
                if (jsAttribute instanceof IUMLElement) {
                    Element element = ((IUMLElement) jsAttribute).getElement();
                    if (element instanceof Property) {
                        Property property = (Property) element;
                        if (property.getType() instanceof PrimitiveType) {
                            PrimitiveType basePrimitiveType =
                                    PrimitivesUtil
                                            .getBasePrimitiveType((PrimitiveType) property
                                                    .getType());
                            if (dataType.equals(JsConsts.INTEGER)) {
                                Object facetPropertyValue =
                                        PrimitivesUtil
                                                .getFacetPropertyValue(basePrimitiveType,
                                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_SUBTYPE,
                                                        property);
                                if (facetPropertyValue instanceof EnumerationLiteral
                                        && PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH
                                                .equals((((EnumerationLiteral) facetPropertyValue)
                                                        .getName()))) {
                                    dataType = JsConsts.BIGINTEGER;
                                }
                            } else if (dataType.equals(JsConsts.DECIMAL)) {
                                Object facetPropertyValue =
                                        PrimitivesUtil
                                                .getFacetPropertyValue(basePrimitiveType,
                                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE,
                                                        property);
                                if (facetPropertyValue instanceof EnumerationLiteral
                                        && PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT
                                                .equals((((EnumerationLiteral) facetPropertyValue)
                                                        .getName()))) {
                                    dataType = JsConsts.BIGDECIMAL;
                                }
                            }
                        }
                    }
                }
            }
        }
        return dataType;
    }

    /**
     * @see com.tibco.xpd.script.model.jscript.JScriptTypeResolver#resolveFromScriptRelevantData(com.tibco.xpd.script.model.client.IScriptRelevantData,
     *      boolean, com.tibco.xpd.script.model.client.IScriptRelevantData)
     * 
     * @param scriptRelevantData
     * @param isMultiple
     * @param genericContext
     * @return
     */
    @Override
    protected List<IScriptRelevantData> resolveFromScriptRelevantData(
            IScriptRelevantData scriptRelevantData, boolean isMultiple,
            IScriptRelevantData genericContext) {

        if (scriptRelevantData instanceof CaseUMLScriptRelevantData) {

            CaseUMLScriptRelevantData caseUMLScriptRelevantData =
                    (CaseUMLScriptRelevantData) scriptRelevantData;

            JsClass jsClass = caseUMLScriptRelevantData.getJsClass();
            if (jsClass instanceof CaseRefPaginatedListJsClass) {

                if (scriptRelevantData.isArray()) {

                    IScriptRelevantData resolvePaginatedMultipleType =
                            resolvePaginatedMultipleType(scriptRelevantData.getName(),
                                    getSupportedJsClasses(),
                                    null,
                                    getTypeMap());
                    return Collections
                            .singletonList(resolvePaginatedMultipleType);
                }
            }
        }
        return super.resolveFromScriptRelevantData(scriptRelevantData,
                isMultiple,
                genericContext);
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

        return JScriptUtils.resolveJavaScriptStringType(name,
                JsConsts.LIST,
                false,
                getSupportedJsClasses(),
                null,
                getTypeMap());
    }

    /**
     * resolve to a paginated list type
     * 
     * @param name
     * @param jsClasses
     * @param multipleClass
     * @param typeMap
     * @return IScriptRelevantData
     */
    protected IScriptRelevantData resolvePaginatedMultipleType(String name,
            List<JsClass> jsClasses, Class multipleClass,
            Map<String, String> typeMap) {

        return JScriptUtils.resolveJavaScriptStringType(name,
                JsConsts.PAGINATEDLIST,
                false,
                getSupportedJsClasses(),
                null,
                getTypeMap());
    }
}
