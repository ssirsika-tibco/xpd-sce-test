package com.tibco.xpd.ui.projectexplorer.viewerfilters;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;

/**
 * This filter has been written originally for the ConceptsFolderPicker
 * as a replacement to the SpecialFoldersOnlyFilter which hid non-top-level 
 * special folders. Note that it doesn't listen for changes in tree structure;
 * so if the tree structure changes after instantiation then those changes will
 * not be reflected in the results.
 * @author pwells
 * 
 * @deprecated Use {@link SpecialFoldersOnlyFilter}.
 *
 */
public class SpecialAndParentsOfSpecialFoldersFilter extends ViewerFilter {
    private HashMap<IProject, SpecialFolders> map = new HashMap<IProject, SpecialFolders>();

    /**
     * Using an instance of the old filter to handle top-level elements
     */
    private SpecialFoldersOnlyFilter specialFoldersOnlyFilter;

    public SpecialAndParentsOfSpecialFoldersFilter(Set<String> folderTypes) {
        specialFoldersOnlyFilter = new SpecialFoldersOnlyFilter(folderTypes);

        // for performance, build a list of all special folders
        for (IProject thisProject : ResourcesPlugin.getWorkspace().getRoot()
                .getProjects()) {
            final ProjectConfig config = XpdResourcesPlugin.getDefault()
                    .getProjectConfig(thisProject);
            if (config == null)
                continue;
            map.put(thisProject, config.getSpecialFolders());
        }
    }

    @Override
    @SuppressWarnings( { "unused", "unchecked" })
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (specialFoldersOnlyFilter.select(viewer, parentElement, element)) {
            return true;
        }

        if (!(element instanceof IFolder)) {
            return false;
        }

        final IFolder candidateFolder = (IFolder) element;

        final SpecialFolders projectSpecialFolders = map.get(candidateFolder
                .getProject());

        if (projectSpecialFolders == null) {
            return false;
        }

        Iterator iterator = projectSpecialFolders.getFolders().iterator();
        int inc = 0;
        while (iterator.hasNext()) {
            if (isParent(((SpecialFolder) iterator.next()).getFolder(),
                    candidateFolder)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isParent(IFolder childFolder, IFolder parentFolder) {
        IContainer thisLevel = childFolder;
        while (thisLevel != null) {
            if (thisLevel.equals(parentFolder)) {
                return true;
            }
            thisLevel = thisLevel.getParent();
        }
        return false;
    }
}
