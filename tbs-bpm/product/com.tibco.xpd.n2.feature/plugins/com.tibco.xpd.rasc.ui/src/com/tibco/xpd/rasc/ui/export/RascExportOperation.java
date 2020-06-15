/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.export;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.rasc.core.RascController;
import com.tibco.xpd.rasc.core.exception.RascGenerationException;
import com.tibco.xpd.rasc.ui.RascUiActivator;
import com.tibco.xpd.rasc.ui.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.builder.BuildSynchronizerUtil;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.util.GovernanceStateService;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog.OverwriteStatus;

/**
 * Operation to export projects.
 *
 * @author nwilson
 * @since 6 Mar 2019
 */
public class RascExportOperation implements IRunnableWithProgress {

    /**
     * The parent shell in case we need to show a dialog.
     */
    private Shell shell;

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
     * Set to true to automatically overwrite existing files.
     */
    private Boolean overwrite;

    /**
     * @param projects
     *            The projects to export.
     */
    public RascExportOperation(Shell shell, RascController controller,
            ExportStatusListener listener, List<IProject> projects, String path,
            boolean isProjectRelative) {
        this.shell = shell;
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
         * Checking for problem marker is going to be very small compared with actual generation, so we'll say the job
         * for each project is 10 long and make check-validation just one tick and the
         * generateRelativePath()/generateSystemPath() will be 9 ticks.
         * 
         * And we'll allow 1 tick for checking/building projects (or waiting for them to be built)
         */
        SubMonitor monitor = SubMonitor.convert(aProgressMonitor,
                Messages.RascExportOperation_ProgressTitle,
                (projects.size() * 10) + projects.size());
        monitor.subTask(Messages.RascExportOperation_ProgressTitle);

        for (IProject project : projects) {
            listener.setStatus(project, ExportStatus.WAITING, ""); //$NON-NLS-1$
        }
        boolean valid = true;

        /*
         * Sid ACE-3467 If build automatically is off, then the old call BuildSynchronizerUtil.waitForBuildsToFinish(),
         * would not actually do a build, it would just wait for any running build jobs to finish.
         * 
         * So instead we use BuildSynchronizerUtil.synchronizedBuild() which should run the build itself and wait for it
         * to finish (and if build automatically and already run then it will quickly be able to tell that and return).
         */
        IStatus synchronizedBuildStatus = BuildSynchronizerUtil
                .synchronizedBuild(projects,
                        monitor.split(projects.size()),
                false);

        if (synchronizedBuildStatus.getSeverity() > IStatus.WARNING) {
            throw new InterruptedException();
        }

        if (monitor.isCanceled()) {
            throw new InterruptedException();
        }

        // Fix the main task name after build synchronizer.
        monitor.setTaskName(Messages.RascExportOperation_ProgressTitle);

        // Check that the selected projects are valid for export
        for (IProject project : projects) {
            listener.setStatus(project, ExportStatus.RUNNING, Messages.RascExportOperation_ValidatingStatus);

            try {
                int severity = project.findMaxProblemSeverity(null, true, IResource.DEPTH_INFINITE);

                if (severity == IMarker.SEVERITY_ERROR) {
                    listener.setStatus(project,
                            ExportStatus.FAILED_VALIDATION,
                            Messages.RascExportOperation_ValidationError);
                    valid = false;

                } else {

                    /* Sid ACE-3822 do not allow generation of project(s) if project dependency has errors. */
                    Set<IProject> dependencyProjects = ProjectUtil.getReferencedProjectsHierarchy(project, null);

                    for (IProject dependencyProject : dependencyProjects) {
                        int depSeverity =
                                dependencyProject.findMaxProblemSeverity(null, true, IResource.DEPTH_INFINITE);
                        if (depSeverity == IMarker.SEVERITY_ERROR) {
                            listener.setStatus(project,
                                    ExportStatus.FAILED_VALIDATION,
                                    String.format(
                                            Messages.RascExportOperation_DependencyProjectHasProblems_StatusMsg,
                                            dependencyProject.getName()));
                            valid = false;
                            break;
                        }
                    }

                    if (valid) {
                        listener.setStatus(project, ExportStatus.RUNNING, Messages.RascExportOperation_ValidStatus);
                    }
                }

            } catch (CoreException e) {
                listener.setStatus(project,
                        ExportStatus.FAILED_VALIDATION,
                        Messages.RascExportOperation_ValidationError);
                valid = false;
            }
            monitor.worked(1);
        }

        if (valid) {
            // Generate the content for each projevct
            for (IProject project : projects) {
                if (controller.hasContributionsFor(project)) {
                    listener.setStatus(project,
                            ExportStatus.RUNNING,
                            Messages.RascExportOperation_ExportingStatus);
                    try {
                        if (isProjectRelative) {
                            generateRelativePath(project, monitor);

                        } else {
                            generateSystemPath(project, monitor);
                        }
                        listener.setStatus(project,
                                ExportStatus.COMPLETE,
                                Messages.RascExportOperation_CompleteStatus);
                    } catch (RascGenerationException e) {
                        listener.setStatus(project,
                                ExportStatus.FAILED_EXPORT,
                                Messages.RascExportOperation_ErrorStatus);
                        RascUiActivator.getLogger().error(e);
                    } catch (CoreException e) {
                        listener.setStatus(project,
                                ExportStatus.FAILED_EXPORT,
                                Messages.RascExportOperation_FolderErrorStatus);
                        RascUiActivator.getLogger().error(e);
                    } catch (OverwriteException e) {
                        listener.setStatus(project,
                                ExportStatus.FAILED_EXPORT,
                                e.getMessage());
                    }
                } else {
                    // No contribution for this project, show an info message
                    listener.setStatus(project,
                            ExportStatus.NO_CONTENT,
                            Messages.RascExportOperation_NoContentStatus);
                }
            }
        }
        monitor.subTask(""); //$NON-NLS-1$
        monitor.done();
    }

    /**
     * @param project
     *            The project to generate a RASC for.
     * @param monitor
     *            The progress monitor.
     * @throws RascGenerationException
     *             If the RASC could not be generated.
     * @throws CoreException
     *             If there was any other error.
     * @throws OverwriteException
     */
    private void generateSystemPath(IProject project, SubMonitor monitor)
            throws RascGenerationException, CoreException, OverwriteException {
        File systemPath = getSystemPath(project);
        if (systemPath.exists()) {
            showOverwriteDialog(systemPath.toString());
        }
        controller.generateRasc(project, systemPath, monitor.split(9));
    }

    /**
     * @param project
     *            The project to generate a RASC for.
     * @param monitor
     *            The progress monitor.
     * @throws RascGenerationException
     *             If the RASC could not be generated.
     * @throws CoreException
     *             If there was any other error.
     * @throws OverwriteException
     */
    private void generateRelativePath(IProject project, SubMonitor monitor)
            throws RascGenerationException, CoreException, OverwriteException {
        IFile workspacePath = getWorkspacePath(project);
        if (workspacePath.exists()) {
            showOverwriteDialog(project.getName() + File.separator
                    + workspacePath.getProjectRelativePath().toOSString());
        }
        controller.generateRasc(project, workspacePath, monitor.split(9));
    }

    /**
     * @param file
     *            The name of the file to overwrite.
     * @throws OverwriteException
     */
    private void showOverwriteDialog(String file) throws OverwriteException {
        OverwriteStatus status;
        if (Boolean.FALSE.equals(overwrite)) {
            status = OverwriteStatus.CANCEL;
        } else if (Boolean.TRUE.equals(overwrite)) {
            status = OverwriteStatus.ALL_FILES;
        } else {
            OverwriteFileMessageDialog overwriteDialog =
                    new OverwriteFileMessageDialog(shell,
                            Messages.RascExportOperation_OverwriteDialogTitle,
                            file);
            final AtomicInteger result = new AtomicInteger(-1);
            shell.getDisplay()
                    .syncExec(() -> result.set(overwriteDialog.open()));
            status = overwriteDialog.getOverwriteStatus(result.get());
        }
        if (status == OverwriteStatus.ALL_FILES) {
            overwrite = Boolean.TRUE;
        } else if (status == OverwriteStatus.NO) {
            throw new OverwriteException(file);
        } else if (status == OverwriteStatus.CANCEL) {
            overwrite = Boolean.FALSE;
            throw new OverwriteException(file);
        }
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

        /* Sid ACE-3299: refactored RASC file name building for re-use in non-workspace folder output. */
        StringBuilder fileName = getRascFileName(project);

        return workspacePath.getFile(fileName.toString());
    }

    /**
     * Sid ACE-3299
     * 
     * @param project
     * @return The correct name for the RASC fpr the given project
     * 
     * @throws CoreException
     */
    public StringBuilder getRascFileName(IProject project) throws CoreException {
        GovernanceStateService gss = new GovernanceStateService();

        StringBuilder fileName = new StringBuilder();
        fileName.append(project.getName());
        fileName.append("-"); //$NON-NLS-1$
        if (gss.isLockedForProduction(project)) {
            fileName.append("prod"); //$NON-NLS-1$
        } else {
            fileName.append("dev"); //$NON-NLS-1$
        }
        ProjectConfig projectConfig = XpdResourcesPlugin.getDefault().getProjectConfig(project);

        if (projectConfig != null) {
            ProjectDetails projectDetails = projectConfig.getProjectDetails();
            if (projectDetails != null) {
                String version = projectDetails.getVersion();
                if (version != null) {
                    String[] parts = version.split("\\."); //$NON-NLS-1$
                    if (parts.length > 1) {
                        fileName.append("-"); //$NON-NLS-1$
                        fileName.append(parts[0]);
                        fileName.append("."); //$NON-NLS-1$
                        fileName.append(parts[1]);
                    }
                }
            }
        }
        fileName.append(".rasc"); //$NON-NLS-1$
        return fileName;
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

            /*
             * Sid ACE-3886 Don't set the output folder to derived otherwise it won't get included in project exports.
             */
            // folder.setDerived(true, null);
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

        StringBuilder fileName = getRascFileName(project);

        /* Sid ACE-3299: Use governance state and version suffixes for non workspace folder RASCs too. */
        return new File(parent, fileName.toString()); // $NON-NLS-1$
    }

}
