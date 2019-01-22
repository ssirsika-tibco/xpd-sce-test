/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.viewerfilters;

import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Filter that will include only objects from the list of provided EClasses.
 * 
 * @author wzurek
 */
public class EObjectInclusionFilter extends ViewerFilter {
    private Set<EClass> inclusions;

    /**
     * Filter for only object of the given EClass.
     * 
     * @param inclusions
     */
    public EObjectInclusionFilter(Set<EClass> inclusions) {
        this.inclusions = inclusions;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers
     * .Viewer, java.lang.Object, java.lang.Object)
     */
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        EClass refClass = null;
        boolean match = true;

        // Get the EClass of the model
        if (element instanceof EObject) {
            refClass = ((EObject) element).eClass();

            if (refClass != null) {
                match = validEClass(refClass);

                if (match) {
                    match = extraValidation((EObject) element);
                }
            }
        }

        return match;
    }

    /**
     * Check if the given EClass should be filtered out.
     * 
     * @param eClass
     * @return <code>true</code> if it should be included, <code>false</code> if
     *         it should be filtered out.
     */
    protected boolean validEClass(EClass eClass) {
        boolean ret = false;

        if (eClass != null) {
            ret = inclusions.contains(eClass);
        }

        return ret;
    }

    /**
     * Validate the given <code>EObject</code> to determine whether it should be
     * included by this filter.
     * 
     * @param eObj
     * @return <code>true</code> if the object should be included,
     *         <code>false</code> otherwise.
     */
    protected boolean extraValidation(EObject eObj) {
        return true;
    }
}
