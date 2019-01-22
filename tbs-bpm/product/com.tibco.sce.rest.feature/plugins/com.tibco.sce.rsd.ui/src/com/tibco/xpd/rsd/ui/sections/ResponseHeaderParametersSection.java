/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.sections;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.rsd.Method;

/**
 * Section for RSD's resource path parameters.
 * 
 * @author jarciuch
 * @since 2 Feb 2015
 */
public class ResponseHeaderParametersSection extends
        MethodHeaderParametersSection {

    /**
     * Maps input to the request.
     */
    @Override
    protected EObject resollveInput(Object object) {
        if (object instanceof Method) {
            return ((Method) object).getResponse();
        }
        return super.resollveInput(object);
    }
}
