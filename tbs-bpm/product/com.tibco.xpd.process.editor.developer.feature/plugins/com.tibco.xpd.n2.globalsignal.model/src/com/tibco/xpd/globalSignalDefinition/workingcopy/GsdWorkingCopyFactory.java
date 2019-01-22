/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.globalSignalDefinition.workingcopy;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.globalSignalDefinition.util.GsdConstants;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.WorkingCopyFactory;

/**
 * Working copy factory for Global Signal Definition resources.
 * 
 * @author sajain
 * @since Jan 28, 2015
 */
public class GsdWorkingCopyFactory implements
        WorkingCopyFactory {

    /**
     * @see com.tibco.xpd.resources.WorkingCopyFactory#getWorkingCopy(org.eclipse.core.resources.IResource)
     * 
     * @param resource
     * @return
     */
    @Override
    public WorkingCopy getWorkingCopy(IResource resource) {
        return new GsdWorkingCopy(
                Collections.singletonList(resource));
    }

    /**
     * @see com.tibco.xpd.resources.WorkingCopyFactory#isFactoryFor(org.eclipse.core.resources.IResource)
     * 
     * @param resource
     * @return
     */
    @Override
    public boolean isFactoryFor(IResource resource) {
        boolean isFactory = false;
        if (resource instanceof IFile) {
            String ext = ((IFile) resource).getFileExtension();

            isFactory =
                    (ext != null && ext
                            .equalsIgnoreCase(GsdConstants.GSD_FILE_EXTENSION));
        }
        return isFactory;
    }

}
