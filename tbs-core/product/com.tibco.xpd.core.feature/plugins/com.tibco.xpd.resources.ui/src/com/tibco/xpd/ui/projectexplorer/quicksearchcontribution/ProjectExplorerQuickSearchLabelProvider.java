/**
 * ProjectExplorerQuickSearchLabelProvider.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.quicksearchcontribution;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.NavigatorContentServiceFactory;

import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * ProjectExplorerQuickSearchLabelProvider
 * <p>
 * Quick search popup label provider for the project explorer view.
 * <p>
 * This allows projects, files and folders to be search BUT nothing below files.
 * Other wquick search contributors can add more searchable elements themselves
 * (preferably via the indexing service for efficiency).
 * 
 */
public class ProjectExplorerQuickSearchLabelProvider extends
        AbstractQuickSearchLabelProvider {

    private ILabelProvider labelProvider;

    /**
     * @param partRef
     */
    public ProjectExplorerQuickSearchLabelProvider(
            IWorkbenchPartReference partRef) {
        super(partRef);

        IWorkbenchPart viewPart = getWorkbenchPartRef().getPart(false);
        if (viewPart instanceof CommonNavigator) {
            CommonNavigator projExplorer = (CommonNavigator) viewPart;

            NavigatorContentServiceFactory fact =
                    NavigatorContentServiceFactory.INSTANCE;
            INavigatorContentService navigatorService =
                    fact
                            .createContentService(ProjectExplorerQuickSearchContribution.PROJEXPLORER_VIEWID,
                                    projExplorer.getCommonViewer());

            if (navigatorService != null) {
                labelProvider = navigatorService.createCommonLabelProvider();

                navigatorService.dispose();
            }
        }

    }

    @Override
    public String getElementPath(Object element) {
        if (element instanceof IProject) {
            return ""; //$NON-NLS-1$
        } else if (element instanceof IFolder) {
            return ((IFolder) element).getFullPath().toString();
        } else if (element instanceof IFile) {
            return ((IFile) element).getFullPath().toString();
        }

        return ""; //$NON-NLS-1$
    }

    @Override
    public String getElementTypeName(Object element) {
        if (element instanceof IProject) {
            return Messages.ProjectExplorerQuickSearchLabelProvider_Project_label;
        } else if (element instanceof IFolder) {
            return Messages.ProjectExplorerQuickSearchLabelProvider_Folder_label;
        } else if (element instanceof IFile) {
            return Messages.ProjectExplorerQuickSearchLabelProvider_File_label;
        }

        return ""; //$NON-NLS-1$
    }

    @Override
    public Image getImage(Object element) {
        if (labelProvider != null) {
            return labelProvider.getImage(element);
        }
        return null;
    }

    @Override
    public String getText(Object element) {
        if (element instanceof IResource) {
            return ((IResource)element).getName();
        }
        return ""; //$NON-NLS-1$
    }

}
