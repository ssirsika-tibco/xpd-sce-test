/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.ui.projectexplorer.providers;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.INavigatorContentExtension;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * This is a common Content Provider for Special Folders. It updates all the
 * IFolders [designated as special folders] to Special Folders.
 * 
 * @author aprasad
 * @since 15 Mar 2013
 */
public class CommonSpecialFolderContentProvider extends
        AbstractSpecialFoldersContentProvider {
    /**
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#getChildren(java.lang.Object)
     * 
     * @param parentElement
     * @return
     */
    @Override
    public Object[] getChildren(Object parentElement) {
        Object[] children = null;
        /*
         * This contributor will contribute children of Special Folder, ONLY
         * when specific Special Folder content Contribution is not available.
         */if (parentElement instanceof SpecialFolder) {
            // This is a special folder
            SpecialFolder sFolder = (SpecialFolder) parentElement;
            if (getViewer() instanceof CommonViewer) {
                // get all extension contribution , contributing
                // CONTENTS/CHILDREN to
                // SpecialFolder of 'KIND'
                Set findContentExtensionsByTriggerPoint =
                        ((CommonViewer) getViewer())
                                .getNavigatorContentService()
                                .findContentExtensionsByTriggerPoint(sFolder);
                /*
                 * The returned list also contains the contributor of the
                 * SpecialFolder, i.e. the CommonSpecialFolder contributor,
                 * remove it, if exists.
                 */
                if (findContentExtensionsByTriggerPoint != null) {
                    Iterator iterator =
                            findContentExtensionsByTriggerPoint.iterator();
                    while (iterator.hasNext()) {
                        ITreeContentProvider contentProvider =
                                ((INavigatorContentExtension) iterator.next())
                                        .getContentProvider();
                        if (contentProvider != null
                                && contentProvider.equals(this)) {
                            iterator.remove();
                            break;
                        }
                    }
                    // No other Contributors to this special folder kind is
                    // found, return normal Folder contents
                    if (findContentExtensionsByTriggerPoint.isEmpty()) {
                        children = getFolderChildren(sFolder);
                    }
                }
            }
        } else {
            children = new Object[0];
        }
        return children != null ? children : new Object[0];
    }

    private Object[] getFolderChildren(SpecialFolder sFolder) {
        IFolder folder = sFolder.getFolder();
        if (folder != null) {
            try {
                return folder.members();
            } catch (CoreException e) {
                // Do nothing
            }
        }
        return new Object[0];
    }
    /**
 * 
 */
    public CommonSpecialFolderContentProvider() {
        // We want to provide content for ALL special folders, then as we're the
        // only navigatorContentDescripter for special folders we will be asked
        // to sort via our commonSorter
        // Do not suppress the SpecialFolders update and allow the designated
        // IFolders to be updated to Special Folders.
        super(false);
    }

    /**
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doGetPipelinedChildren(java.lang.Object,
     *      java.util.Set)
     * 
     * @param aParent
     * @param theCurrentChildren
     */
    @Override
    protected void doGetPipelinedChildren(Object aParent, Set theCurrentChildren) {
        /*
         * We have no explicit children of our own, we just exist to switch
         * IFolder's tO their equivalent SpecialFolder
         */
    }

    /**
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doGetChildren(java.lang.Object)
     * 
     * @param parentElement
     * @return
     */
    @Override
    protected Object[] doGetChildren(Object parentElement) {
        /*
         * We have no explicit children of our own, we just exist to switch
         * IFolder's tO their equivalent SpecialFolder
         */
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doGetParent(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected Object doGetParent(Object element) {
        /*
         * We have no explicit children of our own, we just exist to switch
         * IFolder's tO their equivalent SpecialFolder
         */
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doHasChildren(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected boolean doHasChildren(Object element) {
        /*
         * We have no explicit children of our own, we just exist to switch
         * IFolder's tO their equivalent SpecialFolder
         */
        return false;
    }

    /**
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#getSpecialFolderKindInclusion()
     * 
     * @return
     */
    @Override
    public String[] getSpecialFolderKindInclusion() {
        // Includes Special folders of All Kind
        return null;
    }

}
