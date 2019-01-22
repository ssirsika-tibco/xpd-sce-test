/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.cds.script;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.script.model.client.DefaultJsAttribute;
import com.tibco.xpd.script.model.client.DefaultJsClass;
import com.tibco.xpd.script.model.client.JsAttribute;

/**
 * JsClass specifically for Rest payload types.
 * 
 * @author nwilson
 * @since 21 Aug 2015
 */
public class RestJsClass extends DefaultJsClass {
    /**
     * 
     */
    private final ILabelProvider labelProvider;

    /**
     * @param umlClass
     * @param labelProvider
     */
    public RestJsClass(Class umlClass, ILabelProvider labelProvider) {
        super(umlClass);
        this.labelProvider = labelProvider;
    }

    @Override
    protected JsAttribute createJsAttribute(Property property) {
        DefaultJsAttribute jsAttribute =
                new DefaultJsAttribute(property, multipleClass);

        /*
         * Sid XPD-8251 - Noticed NPE when we are called from validation (label
         * provider not set in that circumstance.
         */
        if (labelProvider != null) {
            jsAttribute.setIcon(labelProvider.getImage(property));
        }
        return jsAttribute;
    }
}