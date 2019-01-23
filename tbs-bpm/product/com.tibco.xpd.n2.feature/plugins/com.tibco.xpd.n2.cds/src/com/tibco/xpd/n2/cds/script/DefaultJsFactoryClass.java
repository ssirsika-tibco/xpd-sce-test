/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.n2.cds.script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.n2.cds.utils.CDSUtils;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsDataType;
import com.tibco.xpd.script.model.client.JsExpression;
import com.tibco.xpd.script.model.client.JsExpressionMethod;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * 
 * @author mtorres
 * 
 */
public class DefaultJsFactoryClass implements JsClass, Cloneable {

    private Image image;

    private IContentAssistIconProvider contentAssistIconProvider;

    private String name;

    private List<JsMethod> methodList;

    private List<JsAttribute> attributeList;

    private List<JsReference> referenceList;

    private String comments;

    public DefaultJsFactoryClass(String name, List<JsMethod> methodList,
            List<JsAttribute> attributeList, List<JsReference> referenceList,
            String comments) {
        this.name = name;
        this.methodList = methodList;
        this.attributeList = attributeList;
        this.referenceList = referenceList;
        this.comments = comments;
    }

    public List<JsMethod> getMethodList() {
        if (methodList == null) {
            this.methodList = new ArrayList<JsMethod>();
        }
        return this.methodList;
    }

    public void addMethod(JsMethod jsMethod) {
        getMethodList().add(jsMethod);
    }

    public List<JsMethod> getMethodList(String methodName) {
        List<JsMethod> methodList = getMethodList();
        if (methodList == null) {
            return Collections.emptyList();
        }
        List<JsMethod> toReturn = new ArrayList<JsMethod>();
        for (JsMethod jsMethod : methodList) {
            if (jsMethod.getName().equals(methodName)) {
                toReturn.add(jsMethod);
            }
        }
        return toReturn;
    }

    public String getName() {
        return this.name;
    }

    public boolean isParameterCheckingStrict() {
        return false;
    }

    public String getContentAssistString() {
        return getName();
    }

    public String getComment() {
        return comments;
    }

    public List<JsAttribute> getAttributeList() {
        if (this.attributeList == null) {
            this.attributeList = new ArrayList<JsAttribute>();
        }
        return this.attributeList;
    }

    public void addAttribute(JsAttribute jsAttribute) {
        getAttributeList().add(jsAttribute);
    }

    public List<JsReference> getReferenceList() {
        if (this.referenceList == null) {
            this.referenceList = new ArrayList<JsReference>();
        }
        return this.referenceList;
    }

    public void addReference(JsReference jsReference) {
        getReferenceList().add(jsReference);
    }

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

    public JsAttribute getAttribute(String name) {
        List<JsAttribute> attributeList = getAttributeList();
        if (attributeList != null) {
            for (Iterator<JsAttribute> iterator = attributeList.iterator(); iterator
                    .hasNext();) {
                JsAttribute jsAttribute = iterator.next();
                if (jsAttribute != null && jsAttribute.getName().equals(name)) {
                    return jsAttribute;
                }
            }
        }
        return null;
    }

    public JsMethod getMethod(String name) {
        List<JsMethod> methodList = getMethodList();
        if (methodList != null) {
            for (Iterator<JsMethod> iterator = methodList.iterator(); iterator
                    .hasNext();) {
                JsMethod jsMethod = iterator.next();
                if (jsMethod != null && jsMethod.getName().equals(name)) {
                    return jsMethod;
                }
            }
        }
        return null;
    }

    public JsReference getReference(String name) {
        List<JsReference> referenceList = new ArrayList<JsReference>();
        referenceList.addAll(getReferenceList());
        if (referenceList != null) {
            for (Iterator<JsReference> iterator = referenceList.iterator(); iterator
                    .hasNext();) {
                JsReference jsReference = iterator.next();
                if (jsReference != null && jsReference.getName().equals(name)) {
                    return jsReference;
                }
            }
        }
        return null;
    }

    public JsDataType getDataTypeForJSExpression(JsExpression jsExpression,
            List<JsClass> supportedJsClasses) {
        JsDataType dataType = new JsDataType();
        dataType.setJsExpression(jsExpression);
        if (jsExpression != null) {
            if (JScriptUtils.hasMoreJSChildren(jsExpression)) {
                JsExpression nextJsExpression =
                        jsExpression.getNextExpression();
                if (nextJsExpression != null
                        && nextJsExpression.getName() != null) {
                    // Check the expression with the methods if it is a method
                    if (nextJsExpression instanceof JsExpressionMethod) {
                        if (getMethod(nextJsExpression.getName()) != null) {
                            JsMethod method =
                                    getMethod(nextJsExpression.getName());
                            dataType =
                                    method.getDataTypeForJSExpression(nextJsExpression,
                                            supportedJsClasses);
                        } else {
                            if (getName() != null
                                    && !getName()
                                            .equals(JsConsts.UNDEFINED_DATA_TYPE)) {
                                dataType.setUndefinedCause(JsConsts.UNKNOWN_METHOD_DATA_TYPE_CAUSE);
                            }
                        }
                    } else {
                        // Check the expression with the attributes
                        if (getAttribute(nextJsExpression.getName()) != null) {
                            JsAttribute attribute =
                                    getAttribute(nextJsExpression.getName());
                            dataType =
                                    attribute
                                            .getDataTypeForJSExpression(nextJsExpression,
                                                    supportedJsClasses);
                        } else if (getReference(nextJsExpression.getName()) != null) {
                            JsReference reference =
                                    getReference(nextJsExpression.getName());
                            dataType =
                                    reference
                                            .getDataTypeForJSExpression(nextJsExpression,
                                                    supportedJsClasses);
                        } else {
                            if (getName() != null
                                    && !getName()
                                            .equals(JsConsts.UNDEFINED_DATA_TYPE)) {
                                dataType.setUndefinedCause(JsConsts.UNKNOWN_PROPERTY_DATA_TYPE_CAUSE);
                            }
                        }
                    }
                }
            } else {
                if (JsConsts.LIST.equals(this.getName())
                        || JsConsts.PAGINATEDLIST.equals(this.getName())) {
                    boolean isObjectMultiple = false;
                    if (!JScriptUtils.isJsExpressionMultiple(jsExpression)) {
                        isObjectMultiple = true;
                    }
                    IScriptRelevantData objectType =
                            JScriptUtils
                                    .resolveJavaScriptNotMultipleArrayType(this
                                            .getName(),
                                            isObjectMultiple,
                                            supportedJsClasses,
                                            CDSUtils.getDefaultCDSMultipleClass());
                    dataType.setType(objectType);
                    return dataType;
                } else {
                    /*
                     * IScriptRelevantData scriptRelevantData = new
                     * DefaultUMLScriptRelevantData( this.getName(),
                     * this.getName(), false, this);
                     */
                    IScriptRelevantData scriptRelevantData =
                            JScriptUtils.getScriptRelevantData(this,
                                    this.getName(),
                                    false);
                    dataType.setType(scriptRelevantData);
                    return dataType;
                }
            }
        }
        return dataType;
    }

    public Class getUmlClass() {
        return null;
    }

    /** {@inheritDoc}. **/
    public IScriptRelevantData getScriptRelevantData(String variableName,
            boolean isArray) {
        return null;
    }

    public void setContentAssistIconProvider(
            IContentAssistIconProvider contentAssistIconProvider) {
        this.contentAssistIconProvider = contentAssistIconProvider;
    }

}
