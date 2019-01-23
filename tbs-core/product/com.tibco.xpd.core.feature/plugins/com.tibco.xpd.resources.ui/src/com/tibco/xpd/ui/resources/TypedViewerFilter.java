/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.resources;

import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Viewer filter used in selection dialogs.
 * 
 * Source of this class is based on TypedViewerFilter class from Eclipse JDT
 * plugin.
 * 
 * @deprecated (since 3.1) Use
 *             {@link com.tibco.xpd.ui.projectexplorer.viewerfilters.TypedViewerFilter}
 */
public class TypedViewerFilter extends ViewerFilter {

    private Class[] acceptedTypes;

    private Object[] rejectedElements;

    /**
     * Creates a filter that only allows elements of gives types.
     * 
     * @param acceptedTypes
     *            The types of accepted elements
     */
    public TypedViewerFilter(Class[] acceptedTypes) {
        this(acceptedTypes, null);
    }

    /**
     * Creates a filter that only allows elements of gives types, but not from a
     * list of rejected elements.
     * 
     * @param acceptedTypes
     *            Accepted elements must be of this types
     * @param rejectedElements
     *            Element equals to the rejected elements are filtered out
     */
    public TypedViewerFilter(Class[] acceptedTypes, Object[] rejectedElements) {
        Assert.isNotNull(acceptedTypes);
        this.acceptedTypes = acceptedTypes;
        this.rejectedElements = rejectedElements;
    }

    /**
     * @see ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (rejectedElements != null) {
            for (int i = 0; i < rejectedElements.length; i++) {
                if (element.equals(rejectedElements[i])) {
                    return false;
                }
            }
        }
        for (int i = 0; i < acceptedTypes.length; i++) {
            if (acceptedTypes[i].isInstance(element)) {
                return true;
            }
        }
        return false;
    }

}