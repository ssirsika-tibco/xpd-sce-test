/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.viewerfilters;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * This filter filter out any contents of an <code>IFile</code>. If the
 * parentElement presented to {@link #select(Viewer, Object, Object)} is an
 * <code>IFile</code> then it will return <code>false</code>.
 * 
 * @author njpatel
 * 
 */
public class NoFileContentFilter extends ViewerFilter {

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        IFile file = null;

        if (parentElement instanceof TreePath) {
            parentElement = ((TreePath) parentElement).getLastSegment();
        }

        if (parentElement instanceof IFile) {
            file = (IFile) parentElement;
        } else if (parentElement instanceof IAdaptable) {
            file = (IFile) ((IAdaptable) parentElement).getAdapter(IFile.class);
        } else {
            file = (IFile) Platform.getAdapterManager().getAdapter(
                    parentElement, IFile.class);
        }

        // If parent element is an IFile then return false
        return file == null;
    }

}
