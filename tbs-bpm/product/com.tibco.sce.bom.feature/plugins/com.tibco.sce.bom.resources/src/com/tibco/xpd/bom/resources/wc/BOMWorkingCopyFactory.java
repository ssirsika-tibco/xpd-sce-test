/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.resources.wc;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.WorkingCopyFactory;

/**
 * Working copy factory for BOM resources.
 * 
 * @author njpatel
 * 
 */
public class BOMWorkingCopyFactory implements WorkingCopyFactory {

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.WorkingCopyFactory#getWorkingCopy(org.eclipse.core.resources.IResource)
     */
    public WorkingCopy getWorkingCopy(IResource resource) {
        return new BOMWorkingCopy(Collections.singletonList(resource));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.WorkingCopyFactory#isFactoryFor(org.eclipse.core.resources.IResource)
     */
    public boolean isFactoryFor(IResource resource) {
        boolean isFactory = false;
        if (resource instanceof IFile) {
            String ext = ((IFile) resource).getFileExtension();

            isFactory = (ext != null && ext
                    .equalsIgnoreCase(BOMResourcesPlugin.BOM_FILE_EXTENSION));
        }
        return isFactory;
    }

}
