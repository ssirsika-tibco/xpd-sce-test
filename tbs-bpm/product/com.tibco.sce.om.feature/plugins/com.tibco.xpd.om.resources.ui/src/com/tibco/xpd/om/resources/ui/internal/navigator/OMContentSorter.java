/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.internal.navigator;

import java.text.Collator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.om.core.om.PrivilegeCategory;
import com.tibco.xpd.ui.projectexplorer.sorter.IgnoreDirtyMarkerViewerSorter;

/**
 * Viewer sorter for the OM project explorer contribution.
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class OMContentSorter extends IgnoreDirtyMarkerViewerSorter {

    // For Eclipse resources
    private static final int CAT_CONTAINER = 1;

    private static final int CAT_FILE = 2;

    private static final int CAT_OTHER = 10;
    /**
     * The constructor.
     */
    public OMContentSorter() {
    }

    /**
     * The constructor.
     * 
     * @param collator
     *            the collator to compare objects.
     */
    public OMContentSorter(Collator collator) {
        super(collator);
    }

    /*
     * (non-Javadoc) Returns the feature id so we only make sure that features
     * are grouped together.
     * 
     * @see
     * org.eclipse.jface.viewers.ViewerComparator#category(java.lang.Object)
     */
    @Override
    public int category(Object element) {
        // add sort order for eclipse resources files and folders
        int cat = CAT_OTHER;
        if (element instanceof IContainer) {
            cat = CAT_CONTAINER;
        } else if (element instanceof IFile) {
            cat = CAT_FILE;
        } else if (element instanceof PrivilegeCategory) {
            /*
             * Unfortunately the feature ID of the privilege category is higher
             * than the id of a privilege so the sort order is incorrect.
             * Subtracting arbitrary value to force the categories to appear
             * before the privileges.
             */
            return ((EObject) element).eContainingFeature().getFeatureID() - 5;
        } else if (element instanceof EObject
                && ((EObject) element).eContainingFeature() != null) {
            return ((EObject) element).eContainingFeature().getFeatureID();
        }
        return cat;
    }

}
