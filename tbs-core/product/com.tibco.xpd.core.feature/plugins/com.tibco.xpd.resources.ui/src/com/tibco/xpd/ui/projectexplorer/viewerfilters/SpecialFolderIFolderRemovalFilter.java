/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.ui.projectexplorer.viewerfilters;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * This class is used to filter out IFolders from the navigator if they are
 * supposed to be special folders. Whilst the SpecialFolderContentProvider does
 * remove IFolders when it adds the Special Folders, there are some
 * circumstances where the IFolder can be added back in afterwards (usually
 * caused by JavaNavigatorContentProvider). See SCF-368 for details.
 * 
 * @author nwilson
 * @since 8 Oct 2014
 */
public class SpecialFolderIFolderRemovalFilter extends ViewerFilter {

    /**
     * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     *            The viewer to filter.
     * @param parentElement
     *            The parent element.
     * @param element
     *            The element to check.
     * @return true to include, false to exclude.
     */
    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        boolean include = true;
        if (element instanceof IFolder) {
            IFolder folder = (IFolder) element;
            if (SpecialFolderUtil.getSpecialFolderKind(folder) != null) {
                include = false;
            }
        }
        return include;
    }

}
