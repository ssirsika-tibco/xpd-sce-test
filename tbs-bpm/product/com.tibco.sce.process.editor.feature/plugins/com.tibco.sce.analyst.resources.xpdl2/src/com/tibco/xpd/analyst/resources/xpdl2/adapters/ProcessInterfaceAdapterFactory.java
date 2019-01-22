/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.adapters;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;

/**
 * Adapter factory to adapt Xpdl2 <code>ProcessInterface</code> to
 * <code>IResource</code>.
 * 
 * @author rsomayaj
 */
public class ProcessInterfaceAdapterFactory implements IAdapterFactory {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object,
     *      java.lang.Class)
     */
    public Object getAdapter(Object adaptableObject, Class adapterType) {

        if (adaptableObject instanceof ProcessInterface) {
            if (adapterType == IResource.class) {
                // Get working copy of the Process
                WorkingCopy wc = WorkingCopyUtil
                        .getWorkingCopyFor((EObject) adaptableObject);

                if (wc != null) {
                    // Return the eclipse resource
                    return wc.getEclipseResources().get(0);
                }
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
        return new Class[] { IResource.class };
    }

}