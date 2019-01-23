/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.viewerfilters;

import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.ui.projectexplorer.util.SpecialFolderPresentationUtil;
import com.tibco.xpd.ui.projectexplorer.util.SpecialFolderPresentationUtil.PresentationType;

/**
 * A viewer filter that will only show <code>SpecialFolder</code>s of the given
 * kind(s). This will show all content of these folders.
 * 
 * @author njpatel
 * 
 */
public class SpecialFoldersOnlyFilter extends ViewerFilter {

    private final Set<String> foldersKindsToInclude;
    private PresentationType sfPresentation;

    /**
     * Filter only <code>SpecialFolder</code>s of the given kind(s).
     * 
     * @param folderTypes
     */
    public SpecialFoldersOnlyFilter(Set<String> folderTypes) {
        this.foldersKindsToInclude = folderTypes;
        sfPresentation = SpecialFolderPresentationUtil.getPreferenceValue();
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        boolean sel = true;

        if (!(element instanceof IWorkspaceRoot || element instanceof IProject)
                && (!(element instanceof EObject) || element instanceof SpecialFolder)) {
            if (foldersKindsToInclude != null) {
                /*
                 * First check if the given element is a ProjectFolder of the
                 * given kind
                 */
                if (element instanceof SpecialFolder) {
                    SpecialFolder sFolder = (SpecialFolder) element;
                    sel = foldersKindsToInclude.contains(sFolder.getKind());

                } else {
                    // Assume that this resource will no be included until tests
                    // carried out
                    sel = false;

                    /*
                     * If resource is contained in the included special folder
                     * (at any depth) then include
                     */
                    if (parentElement instanceof TreePath) {
                        TreePath path = (TreePath) parentElement;

                        for (int idx = 0; idx < path.getSegmentCount(); idx++) {
                            Object segment = path.getSegment(idx);

                            if (segment instanceof SpecialFolder) {
                                sel = foldersKindsToInclude
                                        .contains(((SpecialFolder) segment)
                                                .getKind());
                                break;
                            }
                        }
                    } else if (parentElement instanceof SpecialFolder) {
                        sel = foldersKindsToInclude
                                .contains(((SpecialFolder) parentElement)
                                        .getKind());
                    } else if (parentElement instanceof IFolder) {
                        /*
                         * Check if the given folder is contained in a special
                         * folder
                         */
                        SpecialFolder folder = null;
                        IResource res = (IResource) parentElement;
                        do {
                            Object parent = SpecialFolderUtil.getParent(res);
                            if (parent instanceof SpecialFolder) {
                                folder = (SpecialFolder) parent;
                            } else if (parent instanceof IFolder) {
                                res = (IResource) parent;
                            } else {
                                break;
                            }
                        } while (folder == null);

                        if (folder != null) {
                            sel = foldersKindsToInclude.contains(folder
                                    .getKind());
                        }
                    }

                    /*
                     * Resource is not contained in special folder. If this is a
                     * folder and the project explorer presentation is NORMAL
                     * then folder may contain special folder so need to show
                     * this folder
                     */
                    if (!sel && element instanceof IFolder
                            && sfPresentation == PresentationType.NORMAL) {
                        sel = containsSpecialFolder((IFolder) element);
                    }

                }

            }
        }

        return sel;
    }

    /**
     * Check if the given folder contains a special folder of the kind being
     * filtered.
     * 
     * @param folder
     * @return <code>true</code> if it contains a folder (at any level),
     *         <code>false</code> otherwise.
     */
    private boolean containsSpecialFolder(IFolder folder) {
        if (folder != null) {
            ProjectConfig config = XpdResourcesPlugin.getDefault()
                    .getProjectConfig(folder.getProject());

            if (config != null) {
                for (String kind : foldersKindsToInclude) {
                    EList<SpecialFolder> foldersOfKind = config
                            .getSpecialFolders().getFoldersOfKind(kind);

                    for (SpecialFolder sf : foldersOfKind) {
                        IFolder f = sf.getFolder();

                        if (f != null
                                && folder.getFullPath().isPrefixOf(
                                        f.getFullPath())) {
                            // Folder contains special folder
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

}
