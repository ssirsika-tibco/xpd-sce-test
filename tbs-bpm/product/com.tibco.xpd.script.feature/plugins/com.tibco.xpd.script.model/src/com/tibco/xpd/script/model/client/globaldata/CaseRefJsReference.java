/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.script.model.client.globaldata;

import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsDataType;
import com.tibco.xpd.script.model.client.JsExpression;
import com.tibco.xpd.script.model.client.JsReference;

/**
 * Java Script class for association attributes (association of type none or
 * shared) of a case class
 * 
 * @author bharge
 * @since 28 Oct 2013
 */
public class CaseRefJsReference implements JsReference {

    protected Property property;

    private Image image;

    private String comment;

    public CaseRefJsReference(Property property, Image jsAttribImage,
            String comment) {

        this.property = property;
        this.image = jsAttribImage;
        this.comment = comment;
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

        if (property.isMultivalued()) {

            return changeCaseInitialChar(property.getName(), false) + "Refs"; //$NON-NLS-1$
        }
        return changeCaseInitialChar(property.getName(), false) + "Ref"; //$NON-NLS-1$
    }

    private static String changeCaseInitialChar(String propertyName,
            boolean uppercase) {

        if (uppercase) {

            return Character.toString(propertyName.charAt(0)).toUpperCase()
                    + propertyName.substring(1);
        }
        return Character.toString(propertyName.charAt(0)).toLowerCase()
                + propertyName.substring(1);
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsReference#getDataType()
     * 
     * @return
     */
    @Override
    public String getDataType() {

        JsClass referencedJsClass = getReferencedJsClass();
        if (referencedJsClass instanceof CaseRefJsClass) {

            return ((CaseRefJsClass) referencedJsClass).getType();

        }
        return null;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsReference#getComment()
     * 
     * @return
     */
    @Override
    public String getComment() {

        return comment;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsReference#getIcon()
     * 
     * @return
     */
    @Override
    public Image getIcon() {

        if (this.image != null) {

            return this.image;
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsReference#getReferencedJsClass()
     * 
     * @return
     */
    @Override
    public JsClass getReferencedJsClass() {

        Class propertyUmlClass =
                (property.getType() instanceof Class) ? (Class) property
                        .getType() : null;
        if (null != propertyUmlClass) {

            CaseRefJsClass refJsClassForReference =
                    new CaseRefJsClass(propertyUmlClass);
            return refJsClassForReference;
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsReference#isMultiple()
     * 
     * @return
     */
    @Override
    public boolean isMultiple() {

        return property.isMultivalued();
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
    public JsDataType getDataTypeForJSExpression(JsExpression jsExpression,
            List<JsClass> supportedJsClasses) {

        return null;
    }

}
