/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.projectexplorer;

import java.util.Arrays;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;

import com.tibco.xpd.bizassets.resources.BusinessAssetsConstants;
import com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider;

/**
 * Project Explorer content provider for the Business Assets
 * 
 * @author njpatel
 * 
 */
public class BizAssetsContentProvider extends
        AbstractSpecialFoldersContentProvider implements
        IResourceChangeListener {

    private static final String[] SPECIAL_FOLDERS_FILTER = { BusinessAssetsConstants.BIZ_ASSETS_SPECIAL_FOLDER_KIND };

    /**
     * @return
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#getSpecialFolderKindInclusion()
     */
    @Override
    public String[] getSpecialFolderKindInclusion() {
        return SPECIAL_FOLDERS_FILTER;
    }

    /**
     * @param parentElement
     * @return
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doGetChildren(java.lang.Object)
     */
    @Override
    protected Object[] doGetChildren(Object parentElement) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param element
     * @return
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doGetParent(java.lang.Object)
     */
    @Override
    protected Object doGetParent(Object element) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param parent
     * @param theCurrentChildren
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doGetPipelinedChildren(java.lang.Object,
     *      java.util.Set)
     */
    @Override
    protected void doGetPipelinedChildren(Object parent, Set theCurrentChildren) {
        // If this is a WSDL file then get it's children
        if (parent instanceof IFile) {
            Object[] children = doGetChildren(parent);

            if (children != null && children.length > 0) {
                // Add children to the set
                theCurrentChildren.addAll(Arrays.asList(children));
            }
        }
    }

    /**
     * @param element
     * @return
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doHasChildren(java.lang.Object)
     */
    @Override
    protected boolean doHasChildren(Object element) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @param event
     * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
     */
    public void resourceChanged(IResourceChangeEvent event) {
        // TODO Auto-generated method stub

    }
}
