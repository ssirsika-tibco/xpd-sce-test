/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.viewerfilters;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Filter that will include only IFile from the list of provided file
 * extensions. The filter will not filter objects that cannot be adapted into
 * IResource
 * 
 * @author wzurek
 */
public class FileExtensionInclusionFilter extends ViewerFilter {
    private Set<String> inclusions;

    /**
     * Filter for <code>IFile</code>s that have the given file extension.
     * 
     * @param inclusions
     *            set of file extensions.
     */
    public FileExtensionInclusionFilter(Set<String> inclusions) {
        this.inclusions = inclusions;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers
     * .Viewer, java.lang.Object, java.lang.Object)
     */
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (element instanceof IAdaptable) {
            IResource res = (IResource) ((IAdaptable) element)
                    .getAdapter(IResource.class);
            if (res instanceof IFile) {
                return inclusions.contains(((IFile) res).getFileExtension());
            }
        }
        return true;
    }
}
