/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation;

import java.util.Collection;

import org.eclipse.core.resources.IFile;

import com.tibco.xpd.validation.provider.IFileFilter;

/**
 * The composite file filter is used internally to combine multiple file filters
 * into one.
 *
 * @author nwilson
 */
public class CompositeFileFilter implements IFileFilter {
    
    /** The filters. */
    private Collection<IFileFilter> filters;

    /**
     * @param filters The filters.
     */
    public CompositeFileFilter(Collection<IFileFilter> filters) {
        this.filters = filters;
    }
    
    /**
     * @param file The file to check.
     * @return true if any of the filters accept the file.
     * @see com.tibco.xpd.validation.provider.IFileFilter#accept(
     *      org.eclipse.core.resources.IFile)
     */
    public boolean accept(IFile file) {
        boolean ok = false;
        for (IFileFilter filter : filters) {
            ok = filter.accept(file);
            if (ok) {
                break;
            }
        }
        return ok;
    }

}
