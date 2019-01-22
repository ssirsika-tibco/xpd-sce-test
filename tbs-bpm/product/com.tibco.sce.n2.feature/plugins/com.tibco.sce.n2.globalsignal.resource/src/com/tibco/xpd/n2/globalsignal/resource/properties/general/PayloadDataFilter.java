/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.properties.general;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.globalSignalDefinition.PayloadDataField;

/**
 * Payload data filter.
 * 
 * @author sajain
 * @since Feb 24, 2015
 */
public class PayloadDataFilter extends ViewerFilter {

    /**
     * Default constructor.
     */
    public PayloadDataFilter() {
    }

    /**
     * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param parentElement
     * @param element
     * @return
     */
    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {

        if (element instanceof PayloadDataField) {

            return true;

        }
        return false;
    }

}
