/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.viewerfilters;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * The filter filters other objects of the same eclass as provided filter
 * object, if filter object is null, the filter is transparent
 * 
 * @author wzurek
 */
public class EObjectFilter extends ViewerFilter {

    private EObject theEO = null;

    /*
     * @see
     * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers
     * .Viewer, java.lang.Object, java.lang.Object)
     */
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (theEO != null) {
            EObject eo = null;
            if (element instanceof EObject) {
                eo = (EObject) element;
            }
            if (eo != null && theEO.eClass() == eo.eClass()) {
                return eo == theEO;
            }
        }
        return true;
    }

    /**
     * Select only the EObject given.
     * 
     * @param filter
     */
    public void setObjectFilter(EObject filter) {
        this.theEO = filter;
    }
}
