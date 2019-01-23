/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.actions.specialfolder;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.builder.BuildJob;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Unset special folder action.
 * 
 * @author njpatel
 * 
 */
public class UnsetSpecialFolderAction extends BaseSelectionListenerAction {

    private IStructuredSelection selection;

    private final ISpecialFolderModel kindInfo;

    public UnsetSpecialFolderAction(String text, String toolTipText,
            ISpecialFolderModel kindInfo) {
        super(text);
        this.kindInfo = kindInfo;

        if (kindInfo != null) {
            setId(XpdResourcesUIActivator.ID
                    + ".unsetSpecialFolder_" + kindInfo.getKind()); //$NON-NLS-1$

            if (toolTipText != null) {
                setToolTipText(toolTipText);
            }

            if (kindInfo.getIcon() != null) {
                setImageDescriptor(kindInfo.getIcon());
            }
        } else {
            throw new NullPointerException("Kind has not been set."); //$NON-NLS-1$
        }

    }

    @Override
    public boolean updateSelection(IStructuredSelection selection) {

        this.selection = selection;
        return super.updateSelection(selection);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        /*
         * Remove all ProjectFolder in the selection from the project config
         */
        if (selection != null && !selection.isEmpty()) {
            EList foldersToRemove = new BasicEList();

            // Add all SpecialFolder objects to remove to the remove list
            for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
                Object obj = iter.next();
                SpecialFolder sf = null;

                if (obj instanceof SpecialFolder) {
                    sf = (SpecialFolder) obj;
                } else if (obj instanceof IFolder) {
                    IFolder folder = (IFolder) obj;
                    ProjectConfig config = XpdResourcesPlugin.getDefault()
                            .getProjectConfig(folder.getProject());

                    if (config != null && config.getSpecialFolders() != null) {
                        sf = config.getSpecialFolders().getFolder(folder);
                    }
                }

                if (sf != null && sf.getKind().equals(kindInfo.getKind())) {
                    foldersToRemove.add(sf);
                }
            }

            // If there are special folders to remove then do so
            final Set<IProject> projects = new HashSet<IProject>();
            if (!foldersToRemove.isEmpty()) {
                // Get the project
                IProject project = ((SpecialFolder) foldersToRemove.get(0))
                        .getProject();
                ProjectConfig config = XpdResourcesPlugin.getDefault()
                        .getProjectConfig(project);

                if (config != null && config.getSpecialFolders() != null) {
                    config.getSpecialFolders().removeFolder(foldersToRemove);
                    projects.add(project);
                }
            }

            /*
             * If a special folder was unset then kick off a full build so that
             * indexers etc. get updated. There should only be one project.
             */
            if (!projects.isEmpty()) {
                for (IProject project : projects) {
                    BuildJob buildJob = new BuildJob(
                            Messages.UnsetSpecialFolderAction_unsetSpecialFolder_title,
                            project, true);
                    buildJob.setRule(ResourcesPlugin.getWorkspace()
                            .getRuleFactory().buildRule());
                    buildJob.setUser(true);
                    buildJob.schedule();
                }
            }
        }
    }
}
