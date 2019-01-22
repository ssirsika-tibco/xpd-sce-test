/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.providers;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.tibco.xpd.registry.Registry;

/**
 * Adapter used to operate on object in the workbench.
 * <p>
 * <i>Created: 4 Jul 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class RegistryItemProvider implements IWorkbenchAdapter {

    /**
     * The constructor.
     */
    public RegistryItemProvider() {
    }

    /*
     * @see org.eclipse.ui.model.IWorkbenchAdapter#getChildren(java.lang.Object)
     */
    public Object[] getChildren(Object o) {
        if (o instanceof Registry) {
            return ((Registry) o).getSearches().toArray();
        }
        return new Object[0];
    }

    /*
     * @see org.eclipse.ui.model.IWorkbenchAdapter#getImageDescriptor(java.lang.Object)
     */
    public ImageDescriptor getImageDescriptor(Object object) {
        // TODO JA: needs refactoring of the ImageCache to use standard
        // AbstractUIPlugin ImageRegistry rather then home grown solution.
        return null;
    }

    /*
     * @see org.eclipse.ui.model.IWorkbenchAdapter#getLabel(java.lang.Object)
     */
    public String getLabel(Object o) {
        if (o instanceof Registry) {
            return ((Registry) o).getName();
        }
        return null;
    }

    /*
     * @see org.eclipse.ui.model.IWorkbenchAdapter#getParent(java.lang.Object)
     */
    public Object getParent(Object o) {
        return null;
    }

}
