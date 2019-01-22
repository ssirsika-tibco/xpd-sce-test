/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;

/**
 * 
 * @author rsomayaj
 * 
 */
public class ConceptPathFilter extends ChainingViewerFilter {

    public ConceptPathFilter() {
    }

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
        boolean result = false;
        if (element instanceof ConceptPath) {
            result = true;
        }
        return result;
    }
}