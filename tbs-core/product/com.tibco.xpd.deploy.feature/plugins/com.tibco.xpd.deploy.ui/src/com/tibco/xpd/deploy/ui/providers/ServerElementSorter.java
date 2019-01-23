/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.providers;

import org.eclipse.jface.viewers.ViewerSorter;

import com.tibco.xpd.deploy.ContainerElement;
import com.tibco.xpd.deploy.LeafElement;

/**
 * Sorts server elements.
 * <p>
 * <i>Created: 9 August 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ServerElementSorter extends ViewerSorter {

    private static final int CONTAINER_CATEGORY = 10;
    private static final int LEAF_CATEGORY = 20;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ViewerComparator#category(java.lang.Object)
     */
    @Override
    public int category(Object element) {
        if (element instanceof ContainerElement) {
            return CONTAINER_CATEGORY;
        } else if (element instanceof LeafElement) {
            return LEAF_CATEGORY;
        }
        return super.category(element);
    }
}
