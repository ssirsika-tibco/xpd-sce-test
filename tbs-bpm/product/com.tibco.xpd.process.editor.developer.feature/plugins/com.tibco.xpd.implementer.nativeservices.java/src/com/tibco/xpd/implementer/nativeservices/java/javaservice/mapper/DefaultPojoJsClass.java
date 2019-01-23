/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsDataType;
import com.tibco.xpd.script.model.client.JsExpression;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;

/**
 * Wrapper class for the Pojo class
 * 
 * @author mtorres
 * 
 */
public class DefaultPojoJsClass implements JsClass, Cloneable {

    private JavaMethodParameter containerClass;

    private Image image;

    private IContentAssistIconProvider contentAssistIconProvider;

    public DefaultPojoJsClass(JavaMethodParameter containerClass) {
        this.containerClass = containerClass;
    }

    @SuppressWarnings("unchecked")
    public List<JsMethod> getMethodList() {
        return Collections.emptyList();
    }

    List<JsMethod> dynamicMethodList = null;

    public void addMethod(JsMethod jsMethod) {
        if (dynamicMethodList == null) {
            dynamicMethodList = new ArrayList<JsMethod>();
        }
        dynamicMethodList.add(jsMethod);
    }

    public Image getIcon() {
        if (this.image != null) {
            return this.image;
        } else if (contentAssistIconProvider != null) {
            return contentAssistIconProvider.getIcon(this);
        }
        return null;
    }

    protected JsMethod createJsMethod(IMethod method) {
        DefaultPojoJsMethod jsMethod = new DefaultPojoJsMethod(method);
        jsMethod.setContentAssistIconProvider(contentAssistIconProvider);
        return jsMethod;
    }

    @Override
    public List<JsAttribute> getAttributeList() {
        List<JsAttribute> attributeList = new ArrayList<JsAttribute>();
        // Add the public pojo and dynamic attributes to the list
        if (containerClass != null) {
            JavaMethodParameter[] children =
                    containerClass.getChildren(MappingDirection.OUT);
            if (children != null) {
                for (JavaMethodParameter javaMethodParameter : children) {
                    if (javaMethodParameter != null) {
                        String typeSignature =
                                javaMethodParameter.getTypeSignature();
                        if (typeSignature != null) {
                            ParameterTypeEnum paramType =
                                    ParameterTypeEnum.getType(typeSignature);
                            if (paramType != null
                                    && !paramType
                                            .equals(ParameterTypeEnum.CLASS)) {
                                attributeList
                                        .add(createJsAttribute(javaMethodParameter));
                            }
                        }
                    }
                }
            }
        }
        if (dynamicAttributeList != null) {
            attributeList.addAll(dynamicAttributeList);
        }
        return attributeList;
    }

    protected JsAttribute createJsAttribute(JavaMethodParameter field) {
        DefaultPojoJsAttribute jsAttribute = new DefaultPojoJsAttribute(field);
        jsAttribute.setContentAssistIconProvider(contentAssistIconProvider);
        return jsAttribute;
    }

    List<JsAttribute> dynamicAttributeList = null;

    public void addAttribute(JsAttribute jsAttribute) {
        if (dynamicAttributeList == null) {
            dynamicAttributeList = new ArrayList<JsAttribute>();
        }
        dynamicAttributeList.add(jsAttribute);
    }

    @Override
    public String getName() {
        return containerClass.getElementName();
    }

    public String getContentAssistString() {
        return getName();
    }

    @Override
    public String getComment() {
        try {
            if (containerClass != null && containerClass.getIType() != null) {
                return containerClass.getIType()
                        .getAttachedJavadoc(new NullProgressMonitor());
            }
        } catch (JavaModelException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return null;
    }

    public List<JsReference> getReferenceList() {
        List<JsReference> referenceList = new ArrayList<JsReference>();
        // Add the pojo and dynamic references to the list
        JavaMethodParameter[] children =
                containerClass.getChildren(MappingDirection.OUT);
        if (children != null) {
            for (JavaMethodParameter javaMethodParameter : children) {
                if (javaMethodParameter != null) {
                    String typeSignature =
                            javaMethodParameter.getTypeSignature();
                    if (typeSignature != null) {
                        ParameterTypeEnum paramType =
                                ParameterTypeEnum.getType(typeSignature);
                        if (paramType != null
                                && paramType.equals(ParameterTypeEnum.CLASS)) {
                            referenceList
                                    .add(createJsReference(javaMethodParameter));
                        }
                    }
                }
            }
        }
        if (dynamicReferenceList != null) {
            referenceList.addAll(dynamicReferenceList);
        }
        return referenceList;
    }

    List<JsReference> dynamicReferenceList = null;

    public void addReference(JsReference jsReference) {
        if (dynamicReferenceList == null) {
            dynamicReferenceList = new ArrayList<JsReference>();
        }
        dynamicReferenceList.add(jsReference);
    }

    protected JsReference createJsReference(JavaMethodParameter field) {
        DefaultPojoJsReference jsReference = new DefaultPojoJsReference(field);
        jsReference.setContentAssistIconProvider(contentAssistIconProvider);
        return jsReference;
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

    public JsAttribute getAttribute(String name) {
        List<JsAttribute> attributeList = getAttributeList();
        if (attributeList != null) {
            for (Iterator<JsAttribute> iterator = attributeList.iterator(); iterator
                    .hasNext();) {
                JsAttribute jsAttribute = (JsAttribute) iterator.next();
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
                JsMethod jsMethod = (JsMethod) iterator.next();
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
                JsReference jsReference = (JsReference) iterator.next();
                if (jsReference != null && jsReference.getName().equals(name)) {
                    return jsReference;
                }
            }
        }
        return null;
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

    @Override
    public JsDataType getDataTypeForJSExpression(JsExpression jsExpression,
            List<JsClass> supportedJsClasses) {
        return null;
    }

    @Override
    public Class getUmlClass() {
        return null;
    }

    @Override
    public boolean isParameterCheckingStrict() {
        return false;
    }

    @Override
    public void setIcon(Image icon) {
        this.image = icon;
    }
    
    
    public JavaMethodParameter getContainerClass() {
        return containerClass;
    }
}
