/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions.saveas;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.internal.wizards.datatransfer.FileSystemExportOperation;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.ProjectResource;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;

/**
 * Save as action that will create a new .maa.
 * 
 * @author njpatel
 * 
 */
public class SaveAsExtFolderAction extends SaveAsAction {

    private static final String PREF_PREV_PATH = "saveAsExtFolder-prev-path"; //$NON-NLS-1$

    /**
     * @param shell
     * @param title
     */
    public SaveAsExtFolderAction(Shell shell) {
        super(shell, Messages.SaveAsExtFolderAction_label);
        setToolTipText(Messages.SaveAsExtFolderAction_tooltip);
        setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER));
    }

    /**
     * @see com.tibco.xpd.rcp.internal.actions.saveas.SaveAsAction#export(com.tibco.xpd.rcp.internal.resources.IRCPContainer,
     *      java.io.File, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param resource
     * @param target
     * @param monitor
     * @throws CoreException
     */
    @Override
    protected void export(IRCPContainer resource, File target,
            IProgressMonitor monitor) throws CoreException {
        List<IProject> projects = new ArrayList<IProject>();
        IPath exportFolder = new Path(target.getAbsolutePath());
        Collection<ProjectResource> projectResources =
                resource.getProjectResources();

        monitor.beginTask(Messages.SaveAsExtFolderAction_saving_monitor_shortdesc,
                projectResources.size());
        try {
            for (ProjectResource prRes : projectResources) {
                monitor.subTask(prRes.getName());
                IProject project = prRes.getProject();

                /*
                 * Check if the target folder already contains a folder with the
                 * same name as the project, if it does then abort the save.
                 */
                IPath projectPath = exportFolder.append(project.getName());
                File folder = projectPath.toFile();
                if (folder.exists()) {
                    throw new CoreException(
                            new Status(
                                    IStatus.ERROR,
                                    RCPActivator.PLUGIN_ID,
                                    Messages.SaveAsExtFolderAction_projectsAlreadyExist_error_longdesc));
                }
                projects.add(project);
            }

            if (!projects.isEmpty()) {
                // Create the target folder if it doesn't already exist
                if (!target.exists()) {
                    try {
                        if (!target.mkdir()) {
                            throw new CoreException(
                                    new Status(
                                            IStatus.ERROR,
                                            RCPActivator.PLUGIN_ID,
                                            String.format("Failed to create folder '%s'.",
                                                    target.getAbsolutePath())));
                        }
                    } catch (SecurityException e) {
                        throw new CoreException(new Status(IStatus.ERROR,
                                RCPActivator.PLUGIN_ID,
                                String.format("Failed to create folder '%s'",
                                        target.getAbsolutePath()), e));
                    }
                }
                for (IProject project : projects) {
                    if (!exportProject(project,
                            target,
                            new SubProgressMonitorEx(monitor, 1))) {
                        // Operation cancelled
                        break;
                    }
                }
            }
        } finally {
            monitor.done();
        }
    }

    /**
     * Export the given project to the target folder.
     * 
     * @param project
     * @param target
     * @return <code>true</code> if the project was exported, <code>false</code>
     *         if the operation was cancelled.
     */
    @SuppressWarnings("restriction")
    private boolean exportProject(IProject project, File target,
            IProgressMonitor monitor) {

        FileSystemExportOperation op =
                new FileSystemExportOperation(project,
                        target.getAbsolutePath(), new IOverwriteQuery() {

                            @Override
                            public String queryOverwrite(String pathString) {
                                return ALL;
                            }
                        });

        try {
            op.run(monitor);
            return true;
        } catch (InterruptedException e) {
            // Do nothing
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.actions.saveas.SaveAsAction#getTargetPath()
     * 
     * @return
     */
    @Override
    protected String getTargetPath() {
        DirectoryDialog dlg = new DirectoryDialog(getShell());
        dlg.setText(Messages.SaveAsExtFolderAction_saveAsExtFolder_dialog_title);
        dlg.setMessage(Messages.SaveAsExtFolderAction_saveAsExtFolder_dialog_shortdesc);

        /*
         * Get the previously used path from the preferences
         */
        IPreferenceStore store = RCPActivator.getDefault().getPreferenceStore();

        if (store != null) {
            String path = store.getString(PREF_PREV_PATH);

            if (path != null && !path.isEmpty()) {
                dlg.setFilterPath(path);
            }
        }

        String selectedPath = dlg.open();

        // Persist the path
        if (selectedPath != null && store != null) {
            store.setValue(PREF_PREV_PATH, selectedPath);
        }

        return selectedPath;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.actions.saveas.SaveAsAction#preExportCheck(java.io.File)
     * 
     * @param target
     * @return
     * @throws CoreException
     */
    @Override
    protected boolean preExportCheck(File target) {
        return true;
    }
}
