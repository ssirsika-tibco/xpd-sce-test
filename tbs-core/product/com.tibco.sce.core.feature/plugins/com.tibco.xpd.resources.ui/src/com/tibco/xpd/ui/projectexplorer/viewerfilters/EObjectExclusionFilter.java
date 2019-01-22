/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.viewerfilters;

import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Filter that exclude from the list all items that are in one of provided
 * {@link EClass}, it will also exclude all categories that contains that
 * objects
 * 
 * @author wzurek
 */
public class EObjectExclusionFilter extends ViewerFilter {
    private Set<EClass> exclusions;

    /**
     * Exclude objects of the given EClasses.
     * 
     * @param exclusions
     */
    public EObjectExclusionFilter(Set<EClass> exclusions) {
        this.exclusions = exclusions;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers
     * .Viewer, java.lang.Object, java.lang.Object)
     */
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        boolean match = true;

        if (element instanceof EObject) {
            match = !exclusions.contains(((EObject) element).eClass());
        }

        return match;
    }
}
