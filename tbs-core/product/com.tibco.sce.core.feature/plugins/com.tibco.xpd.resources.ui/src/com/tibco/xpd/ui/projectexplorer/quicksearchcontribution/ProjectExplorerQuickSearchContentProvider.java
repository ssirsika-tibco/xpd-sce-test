/**
 * ProjectExplorerQuickSearchContentProvider.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.quicksearchcontribution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.part.ViewPart;

import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.QuickSearchPopupCategory;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * ProjectExplorerQuickSearchContentProvider
 * <p>
 * Quick search popup content provider for the project explorer view.
 * <p>
 * This allows projects, files and folders to be search BUT nothing below files.
 * Other wquick search contributors can add more searchable elements themselves
 * (preferably via the indexing service for efficiency).
 * 
 */
public class ProjectExplorerQuickSearchContentProvider extends
        AbstractQuickSearchContentProvider {

    private static final String PROJECTS_QS_CATEGORY_ID =
            "projectExplorer.projects.category"; //$NON-NLS-1$

    private static final String FOLDERS_QS_CATEGORY_ID =
            "projectExplorer.folders.category"; //$NON-NLS-1$

    private static final String FILES_QS_CATEGORY_ID =
            "projectExplorer.files.category"; //$NON-NLS-1$

    private static final String DOTSTAR_QS_CATEGORY_ID =
            "projectExplorer.dotstar.category"; //$NON-NLS-1$;

    private List<Object> availableElements = Collections.EMPTY_LIST;


    /**
     * @param partRef
     */
    public ProjectExplorerQuickSearchContentProvider(
            IWorkbenchPartReference partRef) {
        super(partRef);

        availableElements = new ArrayList<Object>();

        // Get the diagram fragment view and it's content provider.
        IWorkbenchPart part = partRef.getPart(false);
        if (part instanceof ViewPart) {
            // Cache all the currently available elements now because (a) this
            // looks providers up from extension point and (b) we can get away
            // with it because the content cannot change without search popup
            // being disabled and therefore we will get thrown away when that
            // happens.
            cacheAvailableElements();
        }
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

    @Override
    public Collection<QuickSearchPopupCategory> getCategories() {
        List<QuickSearchPopupCategory> cats =
                new ArrayList<QuickSearchPopupCategory>();

        QuickSearchPopupCategory wsCategory =
                new QuickSearchPopupCategory(
                        "projectExplorer.workspace.category", //$NON-NLS-1$
                        Messages.ProjectExplorerQuickSearchContentProvider_WorkspaceResources_label);
        cats.add(wsCategory);

        QuickSearchPopupCategory exclusions =
                new QuickSearchPopupCategory(
                        "projectExplorer.workspace.exclusions.category", //$NON-NLS-1$
                        Messages.ProjectExplorerQuickSearchContentProvider_NormallyExcludedResources_label);
        wsCategory.addChild(exclusions);

        exclusions
                .addChild(new QuickSearchPopupCategory(
                        PROJECTS_QS_CATEGORY_ID,
                        Messages.ProjectExplorerQuickSearchContentProvider_Projects_label));
        exclusions
                .addChild(new QuickSearchPopupCategory(
                        FOLDERS_QS_CATEGORY_ID,
                        Messages.ProjectExplorerQuickSearchContentProvider_Folders_label));
        exclusions
                .addChild(new QuickSearchPopupCategory(
                        DOTSTAR_QS_CATEGORY_ID,
                        Messages.ProjectExplorerQuickSearchContentProvider_HiddenElements_label));

        wsCategory
                .addChild(new QuickSearchPopupCategory(
                        FILES_QS_CATEGORY_ID,
                        Messages.ProjectExplorerQuickSearchContentProvider_Files_label));

        return cats;
    }

    @Override
    public Collection<?> getElements() {
        return getElements(Collections.EMPTY_LIST);
    }

    @Override
    public Collection<?> getElements(
            Collection<QuickSearchPopupCategory> categories) {

        boolean wantProjects = false;
        boolean wantFiles = false;
        boolean wantFolders = false;
        boolean wantDotStarResources = false;

        if (categories.isEmpty()) {
            // Unless explicity selected, don't include folders, projects or .*
            // elements.
            wantFiles = true;
        } else {
            for (QuickSearchPopupCategory cat : categories) {
                if (FOLDERS_QS_CATEGORY_ID.equals(cat.getId())) {
                    wantFolders = true;
                } else if (PROJECTS_QS_CATEGORY_ID.equals(cat.getId())) {
                    wantProjects = true;
                } else if (FILES_QS_CATEGORY_ID.equals(cat.getId())) {
                    wantFiles = true;
                } else if (DOTSTAR_QS_CATEGORY_ID.equals(cat.getId())) {
                    wantDotStarResources = true;
                }
            }
        }

        // Get all the required elements as a flat list.
        List<Object> returnElements = new ArrayList<Object>();

        for (Object element : availableElements) {
            if (!(element instanceof IResource)) {
                continue;
            }

            if (isDotStarResource((IResource) element)) {
                if (wantDotStarResources) {
                    returnElements.add(element);
                }

            } else if ((wantProjects) && (element instanceof IProject)) {
                returnElements.add(element);

            } else if ((wantFiles) && (element instanceof IFile)) {
                returnElements.add(element);
            } else if ((wantFolders) && (element instanceof IFolder)) {
                returnElements.add(element);
            }
        }

        return returnElements;
    }

    /**
     * 
     * @param res
     * @return true if the given resource or any of it's ancestors in resource
     *         tree have a name with leading "." (nominally, in eclipse this is
     *         a hidden resource.
     */
    private boolean isDotStarResource(IResource res) {
        if (res.getName().startsWith(".")) { //$NON-NLS-1$
            return true;
        }

        IContainer cont = res.getParent();
        while (cont != null) {
            if (cont.getName().startsWith(".")) { //$NON-NLS-1$
                return true;
            }
            cont = cont.getParent();
        }

        return false;
    }

    /**
     * Cache all fragments and categories in the list.
     */
    private void cacheAvailableElements() {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

        IProject[] projects = root.getProjects();

        recursiveCacheElements(projects);

        return;
    }

    /**
     * Recursively add fragment children and categories to availableElements
     * list.
     * 
     * @param fragProvider
     * @param elements
     */
    private void recursiveCacheElements(Object[] elements) {
        if (elements != null) {
            for (int i = 0; i < elements.length; i++) {
                Object element = elements[i];

                if (element instanceof IProject
                        && ((IProject) element).isOpen()) {
                    availableElements.add(element);

                    try {
                        recursiveCacheElements(((IProject) element).members());
                    } catch (CoreException e) {
                        e.printStackTrace();
                    }
                } else if (element instanceof IFolder) {
                    availableElements.add(element);

                    try {
                        recursiveCacheElements(((IFolder) element).members());
                    } catch (CoreException e) {
                        e.printStackTrace();
                    }

                } else if (element instanceof IFile) {
                    availableElements.add(element);

                }
            }
        }

        return;
    }

}
