/*
 * Copyright (c) TIBCO Software Inc 2004, 20069. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav.internal;

import java.util.Set;

import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.deploy.webdav.WebDAVPlugin;
import com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider;

/**
 * Content provider required for correct display of WebDAV deployable special
 * folders.
 * <p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class WebDAVSpecialFolderContentProvider extends
        AbstractSpecialFoldersContentProvider implements ITreeContentProvider {

    private static final String[] specialFolders =
            new String[] { WebDAVPlugin.WEBDAV_DEPLOYABLE_KIND };

    @Override
    protected Object[] doGetChildren(Object parentElement) {
        return null;
    }

    @Override
    protected Object doGetParent(Object element) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doGetPipelinedChildren(Object aParent, Set theCurrentChildren) {
    }

    @Override
    protected boolean doHasChildren(Object element) {
        return false;
    }

    @Override
    public String[] getSpecialFolderKindInclusion() {
        return specialFolders;
    }

}
