/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.xpdExtension.ScriptInformation;

/**
 * @author nwilson
 */
public class ScriptInformationFilter extends ChainingViewerFilter {

    /**
     * @param viewer
     * @param parentElement
     * @param element
     * @return
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.filter.ChainingViewerFilter#doSelect(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    protected boolean doSelect(Viewer viewer, Object parentElement,
            Object element) {
        boolean result = false;
        if (element instanceof ScriptInformation) {
            result = true;
        }
        return result;
    }

}
