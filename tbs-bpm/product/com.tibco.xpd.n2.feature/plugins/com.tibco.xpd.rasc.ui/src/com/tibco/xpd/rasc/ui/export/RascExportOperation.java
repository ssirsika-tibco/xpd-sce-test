/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.export;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.tibco.xpd.rasc.core.RascController;
import com.tibco.xpd.rasc.core.exception.RascGenerationException;
import com.tibco.xpd.rasc.ui.Messages;
import com.tibco.xpd.rasc.ui.RascUiActivator;

/**
 * Operation to export projects.
 *
 * @author nwilson
 * @since 6 Mar 2019
 */
public class RascExportOperation implements IRunnableWithProgress {

    /**
     * The RASC controller.
     */
    private RascController controller;

    /**
     * The dialog showing the progress status.
     */
    private ExportProgressMonitorDialog dialog;

    /**
     * The projects to export.
     */
    private List<IProject> projects;

    /**
     * The absolute or project relative path.
     */
    private String path;

    /**
     * True if the path is relative to the project root, false if it is
     * absolute.
     */
    private boolean isProjectRelative;

    /**
     * @param projects
     *            The projects to export.
     */
    public RascExportOperation(RascController controller,
            ExportProgressMonitorDialog dialog, List<IProject> projects,
            String path, boolean isProjectRelative) {
        this.controller = controller;
        this.dialog = dialog;
        this.projects = projects;
        this.path = path;
        this.isProjectRelative = isProjectRelative;
    }

    /**
     * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    public void run(IProgressMonitor monitor)
            throws InvocationTargetException, InterruptedException {
        monitor.beginTask(Messages.RascExportOperation_ProgressTitle,
                projects.size() * 2);
        for (IProject project : projects) {
            dialog.setStatus(project, ""); //$NON-NLS-1$
        }
        for (IProject project : projects) {
            dialog.setStatus(project,
                    Messages.RascExportOperation_ValidatingStatus);
            Thread.sleep(2000);
            dialog.setStatus(project, Messages.RascExportOperation_ValidStatus);
            monitor.worked(1);
        }
        Thread.sleep(2000);
        for (IProject project : projects) {
            dialog.setStatus(project,
                    Messages.RascExportOperation_ExportingStatus);
            try {
                if (isProjectRelative) {
                    controller.generateRasc(project,
                            getWorkspacePath(project),
                            null);
                } else {
                    controller.generateRasc(project,
                            getSystemPath(project),
                            null);
                }
                dialog.setStatus(project,
                        Messages.RascExportOperation_CompleteStatus);
            } catch (RascGenerationException e) {
                dialog.setStatus(project,
                        Messages.RascExportOperation_ErrorStatus);
                RascUiActivator.getLogger().error(e);
            } catch (CoreException e) {
                dialog.setStatus(project,
                        Messages.RascExportOperation_FolderErrorStatus);
                RascUiActivator.getLogger().error(e);
            }
            monitor.worked(1);
        }
        monitor.done();
    }

    /**
     * Gets the workspace relative RASC target IFile.
     * 
     * @param project
     *            The project.
     * @return The target RASC IFile.
     * @throws CoreException
     */
    private IFile getWorkspacePath(IProject project) throws CoreException {
        IFolder workspacePath = project.getFolder(path);
        mkdirs(workspacePath);
        return workspacePath.getFile(project.getName() + ".rasc"); //$NON-NLS-1$
    }

    public void mkdirs(IFolder folder) throws CoreException {
        if (!folder.exists()) {
            IContainer parent = folder.getParent();
            if (parent instanceof IFolder) {
                mkdirs((IFolder) parent);
            }
            folder.create(true, true, null);
        }
    }

    /**
     * Gets the system RASC target File.
     * 
     * @param project
     *            The project.
     * @return The target RASC File.
     * @throws CoreException
     */
    private File getSystemPath(IProject project) throws CoreException {
        File parent = new File(path);
        if (!parent.mkdirs()) {
            throw new CoreException(Status.CANCEL_STATUS);
        }
        return new File(parent, project.getName() + ".rasc"); //$NON-NLS-1$
    }

}
