/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.resources;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Filters applys only to IContainer objects. It filters out everything wchich
 * is up the parent.
 * 
 * @author jarciuch
 */
public class SubtreeViewerFilter extends ViewerFilter {

    private final String rootContainerPath;

    public SubtreeViewerFilter(IContainer rootContainer) {
        Assert.isNotNull(rootContainer);
        rootContainerPath = rootContainer.getFullPath().toString();
    }

    public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (element instanceof IResource) {
            String resourcePath = ((IResource) element).getFullPath()
                    .toString();
            if (resourcePath.startsWith(rootContainerPath)) {
                return true;
            }
        }
        return false;
    }
}