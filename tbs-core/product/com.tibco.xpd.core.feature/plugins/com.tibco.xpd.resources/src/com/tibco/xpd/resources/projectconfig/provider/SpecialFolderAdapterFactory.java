/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.provider;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * Adapter factory for the <code>SpecialFolder</code>.
 * 
 * @author njpatel
 */
public class SpecialFolderAdapterFactory implements IAdapterFactory {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object,
     *      java.lang.Class)
     */
    public Object getAdapter(Object adaptableObject, Class adapterType) {

        if (adaptableObject instanceof SpecialFolder) {
            if (adapterType == IResource.class || adapterType == IFolder.class) {
                return ((SpecialFolder) adaptableObject)
                        .getAdapter(adapterType);

            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
     */
    public Class[] getAdapterList() {
        // TODO Auto-generated method stub
        return new Class[] { IResource.class, IFolder.class };
    }

}
