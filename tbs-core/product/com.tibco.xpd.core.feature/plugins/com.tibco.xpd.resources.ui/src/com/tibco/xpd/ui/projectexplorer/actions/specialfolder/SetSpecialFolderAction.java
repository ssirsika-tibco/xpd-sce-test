/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.actions.specialfolder;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.builder.BuildJob;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel.MultiplicityType;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Set Special Folder action. This will mark the selected folder as a special
 * folder.
 * 
 * @author njpatel
 */
public class SetSpecialFolderAction extends AbstractSpecialFolderAction {

    /**
     * Constructor.
     * 
     * @param shell
     * @param text
     * @param toolTipText
     * @param sfModel
     */
    public SetSpecialFolderAction(Shell shell, String text, String toolTipText,
            ISpecialFolderModel sfModel) {
        super(shell, text, toolTipText, sfModel);
    }

    @Override
    protected void run(List<IFolder> selection) {
        boolean buildRequired = false;
        // Add the special folder(s)
        final IProject project = selection.get(0).getProject();
        ProjectConfig config = XpdResourcesPlugin.getDefault()
                .getProjectConfig(project);

        ISpecialFolderModel sfModel = getSpecialFolderModel();

        if (sfModel != null && config != null
                && config.getSpecialFolders() != null) {
            try {
                config.getSpecialFolders().addFolder(
                        new BasicEList<IFolder>(selection), sfModel.getKind());
                buildRequired = true;
            } catch (IllegalArgumentException e) {
                // If this is a singleton special folder kind then
                // inform user
                if (sfModel.getMultiplicity() == MultiplicityType.SINGLE) {
                    if (selection.size() > 1) {
                        openOneSpecialFolderWarning();
                    } else {
                        buildRequired = doSwitch(selection.get(0));
                    }
                } else {
                    throw e;
                }
            }

            /*
             * If a special folder was created then kick off a full build so
             * that indexers etc. get updated.
             */
            if (buildRequired) {
                BuildJob buildJob = new BuildJob(
                        Messages.SetSpecialFolderAction_setSpecialFolder_title,
                        project, true);
                buildJob.setRule(ResourcesPlugin.getWorkspace()
                        .getRuleFactory().buildRule());
                buildJob.setUser(true);
                buildJob.schedule();
            }
        }
    }

    /**
     * Ask the user if they wish to switch the SpecialFolder. If they do then
     * make the selected folder the special folder of the selected kind.
     * 
     * @param foldersToAdd
     * @return <code>true</code> if the folder was switched.
     */
    private boolean doSwitch(IFolder selectedFolder) {
        boolean folderSwitched = false;
        ProjectConfig config = XpdResourcesPlugin.getDefault()
                .getProjectConfig(selectedFolder.getProject());
        ISpecialFolderModel sfModel = getSpecialFolderModel();

        if (sfModel != null && config != null
                && config.getSpecialFolders() != null) {
            EList<SpecialFolder> currFolder = config.getSpecialFolders()
                    .getFoldersOfKind(sfModel.getKind());

            if (currFolder != null && currFolder.size() > 0) {
                SpecialFolder sf = currFolder.get(0);

                if (sf != null) {
                    String msg = MessageFormat
                            .format(
                                    Messages.SetSpecialFolderAction_singleSpecialFolder_longdesc,
                                    new Object[] {
                                            sfModel.getName(),
                                            sf.getLocation(),
                                            selectedFolder
                                                    .getProjectRelativePath()
                                                    .toString() });

                    if (MessageDialog
                            .openConfirm(
                                    getShell(),
                                    Messages.SetSpecialFolderAction_specialfolder_title,
                                    msg)) {
                        config.getSpecialFolders().changeFolder(sf,
                                selectedFolder);
                        folderSwitched = true;
                    }
                }
            } else {
                // Failed to get currently selected special folder so instead
                // show warning message
                openOneSpecialFolderWarning();
            }
        } else {
            openOneSpecialFolderWarning();
        }
        return folderSwitched;
    }

    /**
     * Open warning message to inform user that only one kind of this special
     * folder can exist in the project.
     */
    private void openOneSpecialFolderWarning() {
        // Inform user that there can only be one
        // special folder of the kind
        MessageDialog
                .openWarning(
                        getShell(),
                        Messages.SetSpecialFolderAction_specialfolder_title,
                        MessageFormat
                                .format(
                                        Messages.SetSpecialFolderAction_onlyOneSpecialFolder_longdesc,
                                        new Object[] { getSpecialFolderModel()
                                                .getName() }));
    }
}
