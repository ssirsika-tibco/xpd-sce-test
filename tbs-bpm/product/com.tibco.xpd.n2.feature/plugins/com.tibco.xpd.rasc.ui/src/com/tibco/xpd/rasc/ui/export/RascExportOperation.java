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
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.tibco.xpd.rasc.core.RascController;
import com.tibco.xpd.rasc.core.exception.RascGenerationException;
import com.tibco.xpd.rasc.ui.Messages;
import com.tibco.xpd.rasc.ui.RascUiActivator;
import com.tibco.xpd.resources.builder.BuildSynchronizerUtil;

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
    private ExportStatusListener listener;

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
            ExportStatusListener listener, List<IProject> projects, String path,
            boolean isProjectRelative) {
        this.controller = controller;
        this.listener = listener;
        this.projects = projects;
        this.path = path;
        this.isProjectRelative = isProjectRelative;
    }

    /**
     * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    public void run(IProgressMonitor aProgressMonitor)
            throws InvocationTargetException, InterruptedException {

        /*
         * Checking for problem marker is going to be very small compared with
         * actual generation, so we'll say the job is 10 long and make
         * check-validation just one tick and the generate will be 10 ticks.
         */
        SubMonitor monitor = SubMonitor.convert(aProgressMonitor,
                Messages.RascExportOperation_ProgressTitle,
                projects.size() * 10);

        for (IProject project : projects) {
            listener.setStatus(project, ExportStatus.WAITING, ""); //$NON-NLS-1$
        }
        boolean valid = true;

        BuildSynchronizerUtil.waitForBuildsToFinish(monitor);

        for (IProject project : projects) {
            listener.setStatus(project,
                    ExportStatus.RUNNING,
                    Messages.RascExportOperation_ValidatingStatus);
            try {
                int severity = project.findMaxProblemSeverity(null,
                        true,
                        IResource.DEPTH_INFINITE);
                if (severity == IMarker.SEVERITY_ERROR) {
                    listener.setStatus(project,
                            ExportStatus.FAILED,
                            Messages.RascExportOperation_ErrorStatus);
                    valid = false;
                } else {
                    listener.setStatus(project,
                            ExportStatus.RUNNING,
                            Messages.RascExportOperation_ValidStatus);

                }
            } catch (CoreException e) {
                listener.setStatus(project,
                        ExportStatus.FAILED,
                        Messages.RascExportOperation_ErrorStatus);
                valid = false;
            }
            monitor.worked(1);
        }

        if (valid) {
            for (IProject project : projects) {
                listener.setStatus(project,
                        ExportStatus.RUNNING,
                        Messages.RascExportOperation_ExportingStatus);
                try {
                    if (isProjectRelative) {
                        controller.generateRasc(project,
                                getWorkspacePath(project),
                                monitor.split(9));

                    } else {
                        controller.generateRasc(project,
                                getSystemPath(project),
                                null);
                    }
                    listener.setStatus(project,
                            ExportStatus.COMPLETE,
                            Messages.RascExportOperation_CompleteStatus);
                } catch (RascGenerationException e) {
                    listener.setStatus(project,
                            ExportStatus.FAILED,
                            Messages.RascExportOperation_ErrorStatus);
                    RascUiActivator.getLogger().error(e);
                } catch (CoreException e) {
                    listener.setStatus(project,
                            ExportStatus.FAILED,
                            Messages.RascExportOperation_FolderErrorStatus);
                    RascUiActivator.getLogger().error(e);
                }

            }
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

    /**
     * Creating any missing folders.
     * 
     * @param folder
     *            The final folder in the path.
     * @throws CoreException
     *             If there was a problem creating folders.
     */
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
        if (!parent.exists() && !parent.mkdirs()) {
            throw new CoreException(Status.CANCEL_STATUS);
        }
        return new File(parent, project.getName() + ".rasc"); //$NON-NLS-1$
    }

}
