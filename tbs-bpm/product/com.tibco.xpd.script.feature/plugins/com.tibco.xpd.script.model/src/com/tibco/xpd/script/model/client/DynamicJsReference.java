/*
 * Copyright (c) TIBCO Software Inc 2004, 2020. All rights reserved.
 */

package com.tibco.xpd.script.model.client;

import java.util.List;

import org.eclipse.swt.graphics.Image;

/**
 * For creation of dummy references to complex type data (DynamicJsAttribute only works for primitive attribute types
 *
 * Sid ACE-3099
 * 
 * @author aallway
 * @since 27 Feb 2020
 */
public class DynamicJsReference implements JsReference {

    private String name;

    private JsClass jsClass;

    /**
     * @param name
     * @param jsClass
     */
    public DynamicJsReference(String name, JsClass jsClass) {
        super();
        this.name = name;
        this.jsClass = jsClass;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsReference#getReferencedJsClass()
     *
     * @return
     */
    @Override
    public JsClass getReferencedJsClass() {
        return jsClass;
    }

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
     * @see com.tibco.xpd.script.model.client.JsReference#getName()
     *
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsReference#getDataType()
     *
     * @return
     */
    @Override
    public String getDataType() {
        return jsClass.getName();
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsReference#getComment()
     *
     * @return
     */
    @Override
    public String getComment() {
        return "";
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsReference#getIcon()
     *
     * @return
     */
    @Override
    public Image getIcon() {
        return null;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsReference#isMultiple()
     *
     * @return
     */
    @Override
    public boolean isMultiple() {
        return false;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsReference#getDataTypeForJSExpression(com.tibco.xpd.script.model.client.JsExpression,
     *      java.util.List)
     *
     * @param jsExpression
     * @param supportedJsClasses
     * @return
     */
    @Override
    public JsDataType getDataTypeForJSExpression(JsExpression jsExpression, List<JsClass> supportedJsClasses) {
        return null;
    }

}
