/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.xsd;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.resources.INonXPDProjectWCFactory;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.WorkingCopyFactory;

/**
 * Working copy factory that will create XSD working copies.
 * <p>
 * Since 3.3 this factory is also able to create working copies for XSDs in
 * non-XPD nature projects.
 * </p>
 * 
 * @since 3.2
 * @author Dhiraj
 */
public class XSDWorkingCopyFactory implements WorkingCopyFactory,
        INonXPDProjectWCFactory {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.WorkingCopyFactory#getWorkingCopy(org.eclipse
     * .core.resources.IResource)
     */
    @Override
    public WorkingCopy getWorkingCopy(IResource resource) {
        return new XSDWorkingCopy(Collections.singletonList(resource));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.WorkingCopyFactory#isFactoryFor(org.eclipse.core
     * .resources.IResource)
     */
    @Override
    public boolean isFactoryFor(IResource resource) {

        boolean isFactory = false;

        /*
         * SCF-376: Saket: Need to make sure that we create XSD working copy
         * only when the resource is an IFile.
         */
        if (resource instanceof IFile) {

            String ext = ((IFile) resource).getFileExtension();

            isFactory = "xsd".equalsIgnoreCase(ext); //$NON-NLS-1$
        }

        return isFactory;
    }
}
