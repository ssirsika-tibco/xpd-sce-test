/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.sections;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.ui.components.RsdEditingUtil;

/**
 * Section to pick request payload.
 * 
 * @author jarciuch
 * @since 6 Feb 2015
 */
public class RequestJsonPayloadSection extends JsonPayloadPickerSection {

    /**
     * Maps input to the request.
     */
    @Override
    protected EObject resollveInput(Object object) {
        if (object instanceof Method) {
            return ((Method) object).getRequest();
        }
        return super.resollveInput(object);
    }

    /**
     * Filter for this section.
     * 
     * @author jarciuch
     * @since 1 Mar 2015
     */
    public static class Filter implements org.eclipse.jface.viewers.IFilter {
        @Override
        public boolean select(Object toTest) {
            if (toTest instanceof Method) {
                Method method = (Method) toTest;
                return RsdEditingUtil.getMethodsWithRequestPayload()
                        .contains(method.getHttpMethod());
            }
            return false;
        }
    }
}
