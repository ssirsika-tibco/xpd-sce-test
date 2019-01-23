/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.internal.client.DefaultJsEnumeration;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;
import com.tibco.xpd.script.model.internal.client.IJsElementExt;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * 
 * <p>
 * <i>Created: 18 Oct 2007</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class DefaultJsReference extends DefaultMultipleJsClassResolver
        implements JsReference, IJsElementExt {

    protected Property property;

    protected String contentAssistString;

    private Image image;

    private JsClass referencedJsClass;

    private IContentAssistIconProvider contentAssistIconProvider;

    public DefaultJsReference(Property property) {
        this.property = property;
    }

    public DefaultJsReference(Property property, Class multipleClass) {
        this.property = property;
        setMultipleClass(multipleClass);
    }

    @Override
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public String getComment() {
        return JScriptUtils.getUmlElementComment(property);
    }

    @Override
    public String getDataType() {
        Type type = property.getType();
        String typeName = JsConsts.UNDEFINED_DATA_TYPE;
        if (type != null) {
            typeName = type.getName();
        }
        return typeName;
    }

    @Override
    public JsClass getReferencedJsClass() {
        if (referencedJsClass == null) {
            if (property != null) {
                Type propertyType = property.getType();
                if (propertyType instanceof Class) {
                    Class umlClass = (Class) propertyType;
                    referencedJsClass =
                            new DefaultJsClass(umlClass, multipleClass);
                    referencedJsClass.setIcon(getIcon());
                } else if (propertyType instanceof Enumeration) {
                    Enumeration umlEnumeration = (Enumeration) propertyType;
                    DefaultJsEnumeration referencedJsEnum =
                            new DefaultJsEnumeration(umlEnumeration);
                    if (contentAssistIconProvider != null) {
                        referencedJsEnum
                                .setContentAssistIconProvider(contentAssistIconProvider);
                    } else {
                        referencedJsEnum.setIcon(getIcon());
                    }
                    referencedJsClass = referencedJsEnum;
                }
            }
        }
        return referencedJsClass;
    }

    @Override
    public boolean isMultiple() {
        if (isUmlModelMultiple()) {
            return true;
        }
        return false;
    }

    private boolean isUmlModelMultiple() {
        if (property != null) {
            int multipleValue = property.getUpper();
            if (multipleValue == -1 || multipleValue > 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getName() {
        return property.getName();
    }

    @Override
    public String getContentAssistString() {
        // String contentAssistString = getName();
        // if(isMultiple()){
        //            contentAssistString+= JsConsts.ARRAY_CONTENT_ASSIST_SUFFIX; //$NON-NLS-1$
        // }
        // return contentAssistString;
        return getName();
    }

    @Override
    public Image getIcon() {
        if (this.image != null) {
            return this.image;
        } else if (contentAssistIconProvider != null) {
            return contentAssistIconProvider.getIcon(this);
        }
        return null;
    }

    public void setIcon(Image image) {
        this.image = image;
    }

    @Override
    public JsDataType getDataTypeForJSExpression(JsExpression jsExpression,
            List<JsClass> supportedJsClasses) {
        JsDataType dataType = new JsDataType();
        dataType.setJsExpression(jsExpression);
        boolean isExpressionMultiple =
                JScriptUtils.isJsExpressionMultiple(jsExpression);
        if (jsExpression != null) {
            JsClass referencedJsClass = this.getReferencedJsClass();

            /*
             * Sid XPD-3597. Do not continue if the class is a proxy reference
             * to a BM that does not exist, if it is then everything inside it
             * will be null!
             */
            if (referencedJsClass != null
                    && referencedJsClass.getUmlClass() != null
                    && !referencedJsClass.getUmlClass().eIsProxy()) {

                // Check if the uml multiplicity is multiple
                if (isMultiple()) {
                    // Check if the expression is multiple
                    if (isExpressionMultiple) {
                        if (JScriptUtils.hasMoreJSChildren(jsExpression)) {
                            return referencedJsClass
                                    .getDataTypeForJSExpression(jsExpression,
                                            supportedJsClasses);
                        } else {
                            IScriptRelevantData scriptRelevantData = null;
                            if (referencedJsClass.getName()
                                    .equals(getMultipleJsClassName())) {

                                scriptRelevantData =
                                        JScriptUtils
                                                .resolveJavaScriptNotMultipleArrayType(jsExpression
                                                        .getName(),
                                                        true,
                                                        supportedJsClasses,
                                                        getMultipleClass());
                            } else {
                                scriptRelevantData =
                                        new DefaultUMLScriptRelevantData(
                                                referencedJsClass.getName(),
                                                referencedJsClass.getName(),
                                                false, referencedJsClass);
                            }
                            dataType.setType(scriptRelevantData);
                            return dataType;
                        }
                    } else {
                        if (JScriptUtils.hasMoreJSChildren(jsExpression)) {
                            IScriptRelevantData arrayType =
                                    JScriptUtils
                                            .resolveJavaScriptStringType(jsExpression
                                                    .getName(),
                                                    getMultipleJsClassName(),
                                                    true,
                                                    supportedJsClasses,
                                                    getMultipleClass(),
                                                    null);
                            if (arrayType != null
                                    && arrayType instanceof IUMLScriptRelevantData) {
                                IUMLScriptRelevantData umlRelevantData =
                                        (IUMLScriptRelevantData) arrayType;
                                referencedJsClass =
                                        umlRelevantData.getJsClass();
                                return referencedJsClass
                                        .getDataTypeForJSExpression(jsExpression,
                                                supportedJsClasses);
                            } else {
                                dataType.setUndefinedCause(JsConsts.UNDEFINED_DATA_TYPE_CAUSE);
                                return dataType;
                            }
                        } else {
                            IScriptRelevantData scriptRelevantData =
                                    new DefaultUMLScriptRelevantData(
                                            referencedJsClass.getName(),
                                            referencedJsClass.getName(), true,
                                            referencedJsClass);
                            dataType.setType(scriptRelevantData);
                            return dataType;
                        }
                    }
                } else {
                    // Check if the expression is multiple
                    if (isExpressionMultiple) {
                        if (referencedJsClass.getName()
                                .equals(getMultipleJsClassName())) {
                            IScriptRelevantData objectType =
                                    JScriptUtils
                                            .resolveJavaScriptNotMultipleArrayType(referencedJsClass
                                                    .getName(),
                                                    false,
                                                    supportedJsClasses,
                                                    getMultipleClass());
                            if (JScriptUtils.hasMoreJSChildren(jsExpression)) {
                                if (objectType != null
                                        && objectType instanceof IUMLScriptRelevantData) {
                                    IUMLScriptRelevantData umlRelevantData =
                                            (IUMLScriptRelevantData) objectType;
                                    referencedJsClass =
                                            umlRelevantData.getJsClass();
                                    return referencedJsClass
                                            .getDataTypeForJSExpression(jsExpression,
                                                    supportedJsClasses);
                                } else {
                                    dataType.setUndefinedCause(JsConsts.UNDEFINED_DATA_TYPE_CAUSE);
                                    return dataType;
                                }
                            } else {
                                dataType.setType(objectType);
                                return dataType;
                            }
                        } else {
                            dataType.setUndefinedCause(JsConsts.ARRAY_NOT_EXPECTED_DATA_TYPE_CAUSE);
                            return dataType;
                        }
                    } else {
                        if (JScriptUtils.hasMoreJSChildren(jsExpression)) {
                            return referencedJsClass
                                    .getDataTypeForJSExpression(jsExpression,
                                            supportedJsClasses);
                        } else {
                            if (referencedJsClass.getName()
                                    .equals(getMultipleJsClassName())) {
                                IScriptRelevantData objectType =
                                        JScriptUtils
                                                .resolveJavaScriptNotMultipleArrayType(referencedJsClass
                                                        .getName(),
                                                        true,
                                                        supportedJsClasses,
                                                        getMultipleClass());
                                dataType.setType(objectType);
                                return dataType;
                            } else {
                                IUMLScriptRelevantData scriptRelevantData =
                                        new DefaultUMLScriptRelevantData(
                                                referencedJsClass.getName(),
                                                referencedJsClass.getName(),
                                                false, referencedJsClass);
                                dataType.setType(scriptRelevantData);
                                return dataType;
                            }
                        }
                    }
                }
            }

        }
        return dataType;
    }

    public void setContentAssistIconProvider(
            IContentAssistIconProvider contentAssistIconProvider) {
        this.contentAssistIconProvider = contentAssistIconProvider;
    }

    public Element getElement() {
        return property;
    }

    @Override
    public boolean isStatic() {
        if (property != null) {
            return property.isStatic();
        }
        return false;
    }

    @Override
    public boolean isReadOnly() {
        if (property != null) {
            return property.isReadOnly();
        }
        return false;
    }

    @Override
    public String getBaseDataType() {
        if (property != null && property.getType() instanceof PrimitiveType) {
            PrimitiveType primitiveType = (PrimitiveType) property.getType();
            String baseDataType = primitiveType.getName();
            while (primitiveType != null
                    && primitiveType.getGeneralizations() != null
                    && !primitiveType.getGeneralizations().isEmpty()) {
                Generalization generalization =
                        primitiveType.getGeneralizations().iterator().next();
                primitiveType = null;
                if (generalization != null) {
                    Classifier general = generalization.getGeneral();
                    if (general instanceof PrimitiveType) {
                        primitiveType = (PrimitiveType) general;
                        baseDataType = primitiveType.getName();
                    }
                }
            }
            return baseDataType;
        }
        return getDataType();
    }

}
