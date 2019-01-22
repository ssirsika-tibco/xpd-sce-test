/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.om.resources.wc;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.om.resources.OMResourcesActivator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.WorkingCopyFactory;

/**
 * Working copy factory for OM resources.
 * 
 * <p>
 * <i>Created: 21 Nov 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class OMWorkingCopyFactory implements WorkingCopyFactory {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.WorkingCopyFactory#getWorkingCopy(org.eclipse
     * .core.resources.IResource)
     */
    public WorkingCopy getWorkingCopy(IResource resource) {
        return new OMWorkingCopy(Collections.singletonList(resource));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.WorkingCopyFactory#isFactoryFor(org.eclipse.core
     * .resources.IResource)
     */
    public boolean isFactoryFor(IResource resource) {
        boolean isFactory = false;
        if (resource instanceof IFile) {
            String ext = ((IFile) resource).getFileExtension();

            if (ext != null) {
                isFactory = ext
                        .equalsIgnoreCase(OMResourcesActivator.OM_FILE_EXTENSION);

            }

        }
        return isFactory;
    }

}
