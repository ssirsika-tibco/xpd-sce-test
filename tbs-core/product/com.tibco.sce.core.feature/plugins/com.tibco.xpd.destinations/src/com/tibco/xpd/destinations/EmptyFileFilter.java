/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.destinations;

import org.eclipse.core.resources.IFile;

import com.tibco.xpd.validation.provider.IFileFilter;

/**
 * @author NWilson
 * 
 */
public class EmptyFileFilter implements IFileFilter {

    /**
     * Always return false.
     * 
     * @see com.tibco.xpd.validation.provider.IFileFilter#accept(org.eclipse.core.resources.IFile)
     * 
     * @param file
     * @return
     */
    public boolean accept(IFile file) {
        return false;
    }

}
