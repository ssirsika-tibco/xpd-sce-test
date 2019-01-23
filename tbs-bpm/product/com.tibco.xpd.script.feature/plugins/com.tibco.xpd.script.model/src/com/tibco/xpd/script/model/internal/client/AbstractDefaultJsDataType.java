/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.model.internal.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.script.model.client.DefaultJsAttribute;
import com.tibco.xpd.script.model.client.DefaultJsMethod;
import com.tibco.xpd.script.model.client.DefaultJsReference;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsDataType;
import com.tibco.xpd.script.model.client.JsExpression;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * This class provides a wrapper for the Data Type
 * in UML model
 * 
 * @author mtorres
 * 
 */
public abstract class AbstractDefaultJsDataType implements IJsDataType{
    
    protected DataType umlDataType;
    
    private Image image;
    
    private IContentAssistIconProvider contentAssistIconProvider;

    public AbstractDefaultJsDataType(DataType umlDataType) {
        this.umlDataType = umlDataType;
    }

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public List<JsMethod> getMethodList() {        
        List<JsMethod> methodList = new ArrayList<JsMethod>();
        //Add the uml and dynamic methods to the list
        List<JsMethod> umlMethodList = getUmlMethodList();
        if(umlMethodList != null){
            methodList.addAll(umlMethodList);
        }
        if(dynamicMethodList != null){
            methodList.addAll(dynamicMethodList);
        }
        return methodList;
    }
    
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    private List<JsMethod> getUmlMethodList() {
        EList allOperations = umlDataType.getAllOperations();
        if (allOperations == null) {
            return Collections.EMPTY_LIST;
        }
        List<JsMethod> methodList = new ArrayList<JsMethod>();
        for (Object element : allOperations) {
            if (element instanceof Operation) {
                Operation operation = (Operation) element;
                JsMethod jsMethod = createJsMethod(operation);
                methodList.add(jsMethod);
            }
        }
        return methodList;
    }  
    
    List<JsMethod> dynamicMethodList = null;
    
    public void addMethod(JsMethod jsMethod) {
        if(dynamicMethodList == null){
            dynamicMethodList = new ArrayList<JsMethod>(); 
        }
        dynamicMethodList.add(jsMethod);
    }
    
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public List<JsMethod> getMethodList(String methodName) {
        List<JsMethod> methodList = getMethodList();
        if (methodList == null) {
            return Collections.EMPTY_LIST;
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
        return JScriptUtils.getUmlName(umlDataType);
    }

    // TODO
    public boolean isParameterCheckingStrict() {
        return false;
    }

    public String getContentAssistString() {
        return getName();
    }

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public String getComment() {
        return JScriptUtils.getUmlElementComment(umlDataType);
    }

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public List<JsAttribute> getAttributeList() {
        List<JsAttribute> attributeList = new ArrayList<JsAttribute>();
        //Add the uml and dynamic attributes to the list
        List<JsAttribute> umlAttributeList = getUmlAttributeList();
        if(umlAttributeList != null){
            attributeList.addAll(umlAttributeList);
        }
        if(dynamicAttributeList != null){
            attributeList.addAll(dynamicAttributeList);
        }
        return attributeList;
    }
    
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    protected List<JsAttribute> getUmlAttributeList() {
        List<Property> allAttributes = umlDataType.getAllAttributes();
        List<JsAttribute> attributeList = new ArrayList<JsAttribute>();
        if (allAttributes != null) {
            for (Property property : allAttributes) {
                if (property != null
                        && !(property.getType() instanceof Class || property
                                .getType() instanceof Enumeration)) {
                    JsAttribute defaultJsAttribute =
                            createJsAttribute(property);
                    attributeList.add(defaultJsAttribute);
                }
            }
        }        
        
        return attributeList;
    }
    
    List<JsAttribute> dynamicAttributeList = null;
    
    public void addAttribute(JsAttribute jsAttribute) {
        if(dynamicAttributeList == null){
            dynamicAttributeList = new ArrayList<JsAttribute>(); 
        }
        dynamicAttributeList.add(jsAttribute);
    }
    
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public List<JsReference> getReferenceList() {
        List<JsReference> referenceList = new ArrayList<JsReference>();
        //Add the uml and dynamic references to the list
        List<JsReference> umlReferenceList = getUmlReferenceList();
        if(umlReferenceList != null){
            referenceList.addAll(umlReferenceList);
        }
        if(dynamicReferenceList != null){
            referenceList.addAll(dynamicReferenceList);
        }
        return referenceList;
    }   
    
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    private List<JsReference> getUmlReferenceList() {
        List<Property> allAttributes = umlDataType.getAllAttributes();
        if (allAttributes == null) {
            return Collections.EMPTY_LIST;
        }
        List<JsReference> referenceList = new ArrayList<JsReference>();        
        for (Property property : allAttributes) {
            if (property != null) {
                if (property.getType() instanceof Class || property.getType() instanceof Enumeration) {
                    JsReference defaultJsReference =
                            createJsReference(property);
                    referenceList.add(defaultJsReference);
                }
            }
        }
        return referenceList;
    }
    
    List<JsReference> dynamicReferenceList = null;
   
    public void addReference(JsReference jsReference) {
        if(dynamicReferenceList == null){
            dynamicReferenceList = new ArrayList<JsReference>(); 
        }
        dynamicReferenceList.add(jsReference);
    }

    public Image getIcon() {
        if(contentAssistIconProvider != null){
            return contentAssistIconProvider.getIcon(this);
        }else if(this.image != null){
            return this.image;
        }  
        return null;
    }

    protected JsMethod createJsMethod(Operation operation) {
        DefaultJsMethod jsMethod =
                new DefaultJsMethod(operation);
        if (getContentAssistIconProvider() != null) {
            jsMethod
                    .setContentAssistIconProvider(getContentAssistIconProvider());
        } else {
            jsMethod.setIcon(getIcon());
        }
        return jsMethod;
    }

    protected JsAttribute createJsAttribute(Property property) {
        DefaultJsAttribute jsAttribute = new DefaultJsAttribute(property);
        if (getContentAssistIconProvider() != null) {
            jsAttribute
                    .setContentAssistIconProvider(getContentAssistIconProvider());
        } else {
            jsAttribute.setIcon(getIcon());
        }
        return jsAttribute;
    }
    

    protected JsReference createJsReference(Property property) {
        DefaultJsReference jsReference = new DefaultJsReference(property);
        if (getContentAssistIconProvider() != null) {
            jsReference
                    .setContentAssistIconProvider(getContentAssistIconProvider());
        } else {
            jsReference.setIcon(getIcon());
        }
        return jsReference;
    }

    /**
     * Use setContentAssistIconProvider(
     *       IContentAssistIconProvider contentAssistIconProvider) instead
     *    
     **/
    @Deprecated
    public void setIcon(Image image) {
        this.image = image;
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

    public JsDataType getDataTypeForJSExpression(JsExpression jsExpression,
            List<JsClass> supportedJsClasses) {
        JsDataType dataType = new JsDataType();
        dataType.setJsExpression(jsExpression);
        IScriptRelevantData scriptRelevantData =
                JScriptUtils.getScriptRelevantData(this, this.getName(), false);
        dataType.setType(scriptRelevantData);
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

    public IContentAssistIconProvider getContentAssistIconProvider() {
        return contentAssistIconProvider;
    }

    public void setContentAssistIconProvider(
            IContentAssistIconProvider contentAssistIconProvider) {
        this.contentAssistIconProvider = contentAssistIconProvider;
    }
    
    @Override
    public DataType getDataType() {
        return umlDataType;
    }
    
}
