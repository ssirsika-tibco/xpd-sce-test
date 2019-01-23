/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.wc;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.WorkingCopyFactory;

/**
 * Working copy factory to create working copies.
 *
 * @author jarciuch
 * @since 18 Feb 2015
 */
public class RsdWorkingCopyFactory implements WorkingCopyFactory {

    /** {@inheritDoc} */
    @Override
    public WorkingCopy getWorkingCopy(IResource resource) {
        return new RsdWorkingCopy(Collections.singletonList(resource));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isFactoryFor(IResource resource) {
        return resource instanceof IFile
                && RsdWorkingCopy.RSD_FILE_EXTENSION.equalsIgnoreCase(resource
                        .getFileExtension());
    }

}
