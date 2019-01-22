/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.resources.INonXPDProjectWCFactory;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.WorkingCopyFactory;

/**
 * Factory to create the WSDL working copy.
 * <p>
 * Since 3.3 this factory is also able to create working copies for WSDLs in
 * non-XPD nature projects.
 * </p>
 * 
 * @author nwilson
 */
public class WsdlWorkingCopyFactory implements WorkingCopyFactory,
        INonXPDProjectWCFactory {

    /**
     * @param resource
     *            The resource.
     * @return The working copy.
     * @see com.tibco.xpd.resources.WorkingCopyFactory#getWorkingCopy(org.eclipse.core.resources.IResource)
     */
    @Override
    public WorkingCopy getWorkingCopy(IResource resource) {
        return new WsdlWorkingCopy(resource);
    }

    /**
     * @param resource
     *            The resource.
     * @return true if this factory is valid for the resource.
     * @see com.tibco.xpd.resources.WorkingCopyFactory#isFactoryFor(org.eclipse.core.resources.IResource)
     */
    @Override
    public boolean isFactoryFor(IResource resource) {

        boolean isFactory = false;

        /*
         * SCF-376: Saket: Need to make sure that we create WSDL working copy
         * only when the resource is an IFile.
         */
        if (resource instanceof IFile) {

            String ext = ((IFile) resource).getFileExtension();

            isFactory = "wsdl".equalsIgnoreCase(ext); //$NON-NLS-1$
        }

        return isFactory;
    }
}
