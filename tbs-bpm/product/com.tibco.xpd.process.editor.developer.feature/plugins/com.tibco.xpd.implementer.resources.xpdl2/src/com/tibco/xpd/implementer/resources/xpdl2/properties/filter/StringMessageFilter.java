/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

import org.eclipse.jface.viewers.Viewer;

/**
 * @author rsomayaj
 * 
 */
public class StringMessageFilter extends ChainingViewerFilter {
    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.filter.ChainingViewerFilter#doSelect(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param parentElement
     * @param element
     * @return
     */
    @Override
    protected boolean doSelect(Viewer viewer, Object parentElement,
            Object element) {
        if (element instanceof String) {
            return true;
        }
        return false;
    }
}
