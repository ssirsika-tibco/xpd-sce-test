/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.script.model.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * Abstract java script class for uml class that provides common functionality
 * for all classes. sub classes will have to provide the specific implementation
 * 
 * 
 * @author bharge
 * @since 29 Oct 2013
 */
public abstract class AbstractJsClass implements JsClass, Cloneable {

    protected Class umlClass;

    protected String name;

    protected String typeFQName;

    protected List<JsMethod> methodList;

    protected List<JsAttribute> attributeList;

    protected List<JsReference> referenceList;

    protected Image image;

    /**
     * @see com.tibco.xpd.script.model.internal.client.ContentAssistElement#getContentAssistString()
     * 
     * @return
     */
    @Override
    public String getContentAssistString() {

        return getName();
    }

    /**
     * @see com.tibco.xpd.script.model.internal.client.ContentAssistElement#getIcon()
     * 
     * @return
     */
    @Override
    public Image getIcon() {

        return this.image;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsClass#getName()
     * 
     * @return
     */
    @Override
    public String getName() {

        return this.name;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsClass#getMethodList()
     * 
     * @return
     */
    @Override
    public abstract List<JsMethod> getMethodList();

    /**
     * @see com.tibco.xpd.script.model.client.JsClass#addMethod(com.tibco.xpd.script.model.client.JsMethod)
     * 
     * @param jsMethod
     */
    @Override
    public void addMethod(JsMethod jsMethod) {
        /* do nothing, sub class can provide its own implementation */
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsClass#getMethodList(java.lang.String)
     * 
     * @param methodName
     * @return
     */
    @Override
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

    /**
     * @see com.tibco.xpd.script.model.client.JsClass#isParameterCheckingStrict()
     * 
     * @return
     */
    @Override
    public boolean isParameterCheckingStrict() {

        return false;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsClass#getComment()
     * 
     * @return
     */
    @Override
    public String getComment() {

        return JScriptUtils.getUmlElementComment(umlClass);
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsClass#getAttributeList()
     * 
     * @return
     */
    @Override
    public abstract List<JsAttribute> getAttributeList();

    /**
     * @see com.tibco.xpd.script.model.client.JsClass#addAttribute(com.tibco.xpd.script.model.client.JsAttribute)
     * 
     * @param jsAttribute
     */
    @Override
    public void addAttribute(JsAttribute jsAttribute) {
        /* do nothing, sub class can provide its own implementation */
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsClass#getReferenceList()
     * 
     * @return
     */
    @Override
    public abstract List<JsReference> getReferenceList();

    /**
     * @see com.tibco.xpd.script.model.client.JsClass#addReference(com.tibco.xpd.script.model.client.JsReference)
     * 
     * @param jsReference
     */
    @Override
    public void addReference(JsReference jsReference) {
        /* do nothing, sub class can provide its own implementation */
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsClass#setIcon(org.eclipse.swt.graphics.Image)
     * 
     * @param icon
     */
    @Override
    public void setIcon(Image icon) {

        this.image = icon;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsClass#getDataTypeForJSExpression(com.tibco.xpd.script.model.client.JsExpression,
     *      java.util.List)
     * 
     * @param jsExpression
     * @param supportedJsClasses
     * @return
     */
    @Override
    public JsDataType getDataTypeForJSExpression(JsExpression jsExpression,
            List<JsClass> supportedJsClasses) {

        // JsDataType dataType = new JsDataType();
        // dataType.setJsExpression(jsExpression);
        // if (jsExpression != null) {
        //
        // if (JScriptUtils.hasMoreJSChildren(jsExpression)) {
        //
        // JsExpression nextJsExpression =
        // jsExpression.getNextExpression();
        // if (nextJsExpression != null
        // && nextJsExpression.getName() != null) {
        //
        // /* Check the expression with the methods if it is a method */
        // if (nextJsExpression instanceof JsExpressionMethod) {
        //
        // if (getMethod(nextJsExpression.getName()) != null) {
        //
        // JsMethod method =
        // getMethod(nextJsExpression.getName());
        // dataType =
        // method.getDataTypeForJSExpression(nextJsExpression,
        // supportedJsClasses);
        // } else {
        //
        // if (getName() != null
        // && !getName()
        // .equals(JsConsts.UNDEFINED_DATA_TYPE)) {
        //
        // dataType.setUndefinedCause(JsConsts.UNKNOWN_METHOD_DATA_TYPE_CAUSE);
        // }
        // }
        // } else {
        // /* Check the expression with the attributes */
        //
        // if (getAttribute(nextJsExpression.getName()) != null) {
        //
        // JsAttribute attribute =
        // getAttribute(nextJsExpression.getName());
        // dataType =
        // attribute
        // .getDataTypeForJSExpression(nextJsExpression,
        // supportedJsClasses);
        // } else if (getReference(nextJsExpression.getName()) != null) {
        //
        // JsReference reference =
        // getReference(nextJsExpression.getName());
        // dataType =
        // reference
        // .getDataTypeForJSExpression(nextJsExpression,
        // supportedJsClasses);
        // } else {
        //
        // if (getName() != null
        // && !getName()
        // .equals(JsConsts.UNDEFINED_DATA_TYPE)) {
        //
        // dataType.setUndefinedCause(JsConsts.UNKNOWN_PROPERTY_DATA_TYPE_CAUSE);
        // }
        // }
        // }
        // }
        // } else {
        //
        // if (this.getName().equals(JsConsts.LIST)) {
        //
        // boolean isObjectMultiple = false;
        // if (!JScriptUtils.isJsExpressionMultiple(jsExpression)) {
        //
        // isObjectMultiple = true;
        // }
        // IScriptRelevantData objectType =
        // JScriptUtils
        // .resolveJavaScriptNotMultipleArrayType(this
        // .getName(),
        // isObjectMultiple,
        // supportedJsClasses,
        // JScriptUtils
        // .getDefaultMultipleClass());
        // dataType.setType(objectType);
        // return dataType;
        // } else {
        // /*
        // * IScriptRelevantData scriptRelevantData = new
        // * DefaultUMLScriptRelevantData( this.getName(),
        // * this.getName(), false, this);
        // */
        // IScriptRelevantData scriptRelevantData =
        // JScriptUtils.getScriptRelevantData(this,
        // this.getName(),
        // false);
        // dataType.setType(scriptRelevantData);
        // return dataType;
        // }
        // }
        // }
        // return dataType;
        return null;
    }

    /**
     * 
     * @param name
     * @return JsAttribute
     */
    protected JsAttribute getAttribute(String name) {

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

    /**
     * 
     * @param name
     * @return JsMethod
     */
    protected JsMethod getMethod(String name) {

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

    /**
     * 
     * @param name
     * @return JsReference
     */
    protected JsReference getReference(String name) {

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

    /**
     * @see com.tibco.xpd.script.model.client.JsClass#getUmlClass()
     * 
     * @return
     */
    @Override
    public Class getUmlClass() {

        return umlClass;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsClass#getScriptRelevantData(java.lang.String,
     *      boolean)
     * 
     * @param variableName
     * @param isArray
     * @return
     */
    @Override
    public IScriptRelevantData getScriptRelevantData(String variableName,
            boolean isArray) {

        return null;
    }

}
