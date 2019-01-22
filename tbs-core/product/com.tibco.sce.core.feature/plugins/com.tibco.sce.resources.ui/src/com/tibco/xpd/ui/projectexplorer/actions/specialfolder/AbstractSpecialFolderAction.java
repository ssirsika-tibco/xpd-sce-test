/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.actions.specialfolder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Abstract class that forms the basis of the set special folder actions.
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractSpecialFolderAction extends
        BaseSelectionListenerAction {

    private IStructuredSelection selection;

    private final Shell shell;

    private final ISpecialFolderModel sfModel;

    public AbstractSpecialFolderAction(Shell shell, String text,
            String toolTipText, ISpecialFolderModel sfModel) {
        super(text);
        this.shell = shell;
        this.sfModel = sfModel;

        if (sfModel != null) {
            setId(XpdResourcesUIActivator.ID
                    + ".setSpecialFolder_" + sfModel.getKind()); //$NON-NLS-1$

            if (toolTipText != null) {
                setToolTipText(toolTipText);
            }

            if (sfModel.getIcon() != null) {
                setImageDescriptor(sfModel.getIcon());
            }
        } else {
            throw new NullPointerException(
                    "Special folder model has not been set."); //$NON-NLS-1$
        }
    }

    @Override
    public boolean updateSelection(IStructuredSelection selection) {

        this.selection = selection;
        return super.updateSelection(selection);
    }

    @Override
    public final void run() {
        /*
         * Set all IFolder objects in the selection as project folders. Assuming
         * that all folders are from the same project.
         */
        if (selection != null && !selection.isEmpty()) {
            List<IFolder> foldersToAdd = new ArrayList<IFolder>();

            // Add all IFolders to the foldersToAdd list
            for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
                Object obj = iter.next();

                if (obj instanceof IFolder) {
                    foldersToAdd.add((IFolder) obj);
                }
            }

            // If there are folders to add as special folders then do so
            if (!foldersToAdd.isEmpty()) {
                /*
                 * If a single folder is selected and it contains a special
                 * folder at any level then this folder cannot be set as a
                 * special folder. So inform the user. (This will not be a
                 * problem with multiple selection as the set menu option will
                 * not be available.)
                 */
                if (foldersToAdd.size() == 1
                        && folderContainsSpecialFolder(foldersToAdd.get(0))) {

                    MessageDialog
                            .openInformation(
                                    shell,
                                    Messages.SetSpecialFolderAction_cannotSetDialog_title,
                                    Messages.SetSpecialFolderAction_cannotSetDialog_longdesc);

                } else {
                    run(foldersToAdd);
                }
            }
        }
    }

    /**
     * Get the special folder model associated with this action.
     * 
     * @return
     */
    protected ISpecialFolderModel getSpecialFolderModel() {
        return sfModel;
    }

    /**
     * Get shell.
     * 
     * @return
     */
    protected Shell getShell() {
        return shell;
    }

    /**
     * Configure the given
     * 
     * @param selection
     */
    protected abstract void run(List<IFolder> selection);

    /**
     * Check if the given <i>folder</i> contains a special folder at any level.
     * 
     * @param folder
     * @return <b>true</b> if the <i>folder</i> contains a special folder at any
     *         level.
     */
    private boolean folderContainsSpecialFolder(IFolder folder) {
        boolean ret = false;

        if (folder != null) {
            ProjectConfig config = XpdResourcesPlugin.getDefault()
                    .getProjectConfig(folder.getProject());

            if (config != null && config.getSpecialFolders() != null) {
                EList<SpecialFolder> sFolders = config.getSpecialFolders()
                        .getFolders();

                if (sFolders != null) {
                    // Get the folder's project relative path
                    IPath projRelativePath = folder.getProjectRelativePath();

                    /*
                     * Compare the location of each special folder with the
                     * project relative path of the folder, if they are
                     * contained in the same path suffix then the folder
                     * contains a special folder.
                     */
                    for (Iterator<SpecialFolder> iter = sFolders.iterator(); iter
                            .hasNext()
                            && !ret;) {
                        SpecialFolder sf = iter.next();

                        if (sf != null
                                && sf.getFolder() != null
                                && projRelativePath.isPrefixOf(sf.getFolder()
                                        .getProjectRelativePath()) 
                                /*
                                 * XPD-4103 - to handle the scenario of existing
                                 * default folders, in which case the prefix
                                 * match returns true
                                 */
                                && !projRelativePath.makeAbsolute().equals(sf
                                        .getFolder().getProjectRelativePath()
                                        .makeAbsolute())) {
                            ret = true;
                            break;
                        }
                    }
                }
            }
        }
        return ret;
    }

}
