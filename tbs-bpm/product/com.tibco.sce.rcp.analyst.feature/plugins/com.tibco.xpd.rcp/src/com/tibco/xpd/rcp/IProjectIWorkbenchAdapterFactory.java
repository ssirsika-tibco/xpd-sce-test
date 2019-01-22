/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * 
 * 
 * @author kupadhya
 * @since 7 Dec 2012
 */
public class IProjectIWorkbenchAdapterFactory implements IAdapterFactory {

    /**
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object,
     *      java.lang.Class)
     * 
     * @param adaptableObject
     * @param adapterType
     * @return
     */
    @Override
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        if (XpdResourcesPlugin.isRCP() && adaptableObject instanceof IResource
                && adapterType == IWorkbenchAdapter.class) {
            return new ProjectWorkbenchAdapter((IResource) adaptableObject);
        }
        return null;
    }

    /**
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
     * 
     * @return
     */
    @Override
    public Class[] getAdapterList() {
        return new Class[] { IWorkbenchAdapter.class };
    }

}
