/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.destinations.preferences;

import java.util.Arrays;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.destinations.GlobalDestinationUtil;

/**
 * @author NWilson
 * 
 */
public class ComponentVersionsContentProvider implements
        IStructuredContentProvider {

    /**
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    public Object[] getElements(Object inputElement) {
        String[] elements = new String[0];
        if (inputElement instanceof String) {
            String globalComponentId = (String) inputElement;
            elements =
                    GlobalDestinationUtil
                            .getAllComponentVersions(globalComponentId);
        }
        Arrays.sort(elements, new VersionComparator());
        return elements;
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     * 
     */
    public void dispose() {
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param oldInput
     * @param newInput
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

}
