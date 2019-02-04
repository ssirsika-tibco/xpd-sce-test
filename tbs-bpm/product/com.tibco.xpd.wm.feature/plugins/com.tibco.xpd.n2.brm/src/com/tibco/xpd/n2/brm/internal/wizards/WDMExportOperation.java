package com.tibco.xpd.n2.brm.internal.wizards;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.n2.brm.BRMActivator;
import com.tibco.xpd.n2.brm.BRMGenerator;
import com.tibco.xpd.n2.brm.internal.Messages;
import com.tibco.xpd.n2.daa.Activator;
import com.tibco.xpd.n2.daa.internal.wizards.FileCopier;
import com.tibco.xpd.resources.builder.BuildSynchronizerUtil;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.ui.importexport.exportwizard.pages.DestinationLocationType;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog.OverwriteStatus;

/**
 * The runnable with progress operation to perform Work Model Data export.
 * 
 * @see WorkDataModelExportWizard
 * 
 * @author Jan Arciuchiewicz
 */
/* package */class WDMExportOperation implements IRunnableWithProgress {

    private static final String BOM2XSD_SF_KIND = "bom2xsd"; //$NON-NLS-1$

    private static final Logger LOG = BRMActivator.getDefault().getLogger();

    private static final String TIMESTAMP_SEPARATOR = "-"; //$NON-NLS-1$

    private boolean overwriteAll = false;

    private final DestinationLocationType destLocationType;

    private final String destOutFolderPath;

    private final List<IProject> selectedProjects;

    private IStatus operationStatus;

    private final IShellProvider shellProvider;

    /**
     * @param projects
     */
    public WDMExportOperation(List<IProject> projects,
            DestinationLocationType destLocationType, String destOutFolderPath,
            IShellProvider shellProvider) {
        this.selectedProjects = projects;
        this.destLocationType = destLocationType;
        this.destOutFolderPath = destOutFolderPath;
        this.shellProvider = shellProvider;
    }

    /**
     * {@inheritDoc}
     */
    public void run(final IProgressMonitor monitor) {
        final IProgressMonitor progress =
                monitor != null ? monitor : new NullProgressMonitor();
        try {
            progress.beginTask(Messages.WDMExportOperation_Exporting_message,
                    100);
            try {
                BuildSynchronizerUtil.waitForBuildsToFinish(progress);
            } catch (InterruptedException e) {
                setStatus(Status.CANCEL_STATUS);
                return;
            }

            progress.worked(10);
            progress.setTaskName(
                    Messages.WDMExportOperation_BuildinProjects_message);
            IStatus synchronizedBuildStatus =
                    BuildSynchronizerUtil.synchronizedBuild(selectedProjects,
                            new SubProgressMonitor(progress,
                                    IProgressMonitor.UNKNOWN),
                            ResourcesPlugin.getWorkspace().isAutoBuilding()
                                    ? false
                                    : true);
            if (synchronizedBuildStatus.getSeverity() > IStatus.WARNING) {
                setStatus(synchronizedBuildStatus);
                return;
            }
            progress.worked(20);
            progress.setTaskName(
                    Messages.WDMExportOperation_Validation_message);
            for (IProject project : selectedProjects) {
                if (ProjectUtil2.hasErrorLevelProblemMarkers(project)) {
                    String message = String.format(
                            Messages.WDMExportOperation_ProjectHasErrors_message,
                            project);
                    setStatus(new Status(IStatus.ERROR, BRMActivator.PLUGIN_ID,
                            message));
                    return;
                }
            }

            progress.worked(20);
            progress.setTaskName(Messages.WDMExportOperation_Exporting_message);
            ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {

                public void run(IProgressMonitor monitor) {

                    int size = selectedProjects.size();
                    monitor.beginTask(
                            Messages.WDMExportOperation_ExportingWDM_message,
                            100 * size);
                    operationStatus = null;
                    BRMGenerator brmGenerator = BRMGenerator.getInstance();
                    try {

                        for (IProject project : selectedProjects) {
                            IFolder tempOutLocation =
                                    project.getFolder(".tempWDMGen"); //$NON-NLS-1$
                            if (tempOutLocation.exists()) {
                                tempOutLocation.delete(true,
                                        new SubProgressMonitor(monitor, 0,
                                                SubProgressMonitor.SUPPRESS_SUBTASK_LABEL));
                            }
                            ProjectUtil.createFolder(tempOutLocation,
                                    true,
                                    new SubProgressMonitor(monitor, 0,
                                            SubProgressMonitor.SUPPRESS_SUBTASK_LABEL));
                            String timestamp =
                                    ProjectUtil2.getAutogeneratedQualifier();
                            brmGenerator.generateBRMModules(project,
                                    tempOutLocation,
                                    timestamp);
                            monitor.worked(30);
                            brmGenerator.generatePFActivityModel(project,
                                    tempOutLocation,
                                    timestamp);
                            String destPath = new Path(destOutFolderPath)
                                    .append(project.getName()
                                            + TIMESTAMP_SEPARATOR + timestamp)
                                    .toPortableString();
                            monitor.worked(20);
                            Set<IProject> refHierarchy =
                                    new HashSet<IProject>();
                            ProjectUtil.getReferencedProjectsHierarchy(project,
                                    refHierarchy,
                                    true);
                            refHierarchy.add(project);
                            monitor.worked(30);
                            for (IProject p : refHierarchy) {
                                SpecialFolder sf = SpecialFolderUtil
                                        .getSpecialFolderOfKind(p,
                                                BOM2XSD_SF_KIND);
                                if (sf != null) {
                                    IFolder sfFolder = sf.getFolder();
                                    if (sfFolder != null && sfFolder.exists()) {
                                        for (IResource resource : sfFolder
                                                .members()) {
                                            IPath destResourcePath =
                                                    tempOutLocation
                                                            .getFullPath()
                                                            .append(resource
                                                                    .getName());
                                            if (ResourcesPlugin.getWorkspace()
                                                    .getRoot().findMember(
                                                            destResourcePath) == null) {
                                                resource.copy(destResourcePath,
                                                        true,
                                                        new SubProgressMonitor(
                                                                monitor, 0,
                                                                SubProgressMonitor.SUPPRESS_SUBTASK_LABEL));
                                            } else {
                                                LOG.error(
                                                        "WDM Export: Destination path already existed: " //$NON-NLS-1$
                                                                + destResourcePath);
                                            }
                                        }
                                    }
                                }
                            }
                            for (IResource member : tempOutLocation.members()) {
                                if (member instanceof IFile) {
                                    copyFile((IFile) member,
                                            destLocationType,
                                            destPath);
                                }
                            }
                            monitor.worked(20);

                            if (tempOutLocation.exists()) {
                                tempOutLocation.delete(true,
                                        new SubProgressMonitor(monitor, 0,
                                                SubProgressMonitor.SUPPRESS_SUBTASK_LABEL));
                            }
                        }
                        setStatus(Status.OK_STATUS);
                    } catch (CoreException e) {
                        String message =
                                Messages.WDMExportOperation_ExportException_message;
                        Status status = new Status(IStatus.ERROR,
                                BRMActivator.PLUGIN_ID, message, e);
                        BRMActivator.getDefault().getLog().log(status);
                        setStatus(status);
                    } finally {
                        monitor.done();
                    }
                }

                private IStatus copyFile(IFile srcFile,
                        DestinationLocationType destLocationType,
                        String destOutFolderPath) {
                    if (srcFile != null && srcFile.exists()) {
                        FileCopier copier = new FileCopier(srcFile,
                                destLocationType, destOutFolderPath);

                        OverwriteStatus overwriteDestFile =
                                OverwriteStatus.FILE;
                        if (copier.destinationExists() && !overwriteAll) {
                            overwriteDestFile = overwriteFileMessageDialog(
                                    new Path(copier.getOutputPath()));
                            if (overwriteDestFile == OverwriteStatus.CANCEL) {
                                return Status.CANCEL_STATUS;
                            } else if (overwriteDestFile == OverwriteStatus.ALL_FILES) {
                                overwriteAll = true;
                            }
                        }
                        if (overwriteDestFile != OverwriteStatus.NO) {
                            try {
                                copier.copy();
                            } catch (CoreException e) {
                                String message = String.format(
                                        Messages.WDMExportOperation_FileCopyProblem_message,
                                        copier.getInputPath()
                                                .toPortableString(),
                                        copier.getOutputPath());
                                return new Status(IStatus.ERROR,
                                        Activator.PLUGIN_ID, message, e);
                            }
                        }
                    }
                    return Status.OK_STATUS;
                }
            }, new SubProgressMonitor(progress, 50));
        } catch (CoreException e) {
            String message =
                    Messages.WDMExportOperation_ExportException_message;
            Status status = new Status(IStatus.ERROR, BRMActivator.PLUGIN_ID,
                    message, e);
            BRMActivator.getDefault().getLog().log(status);
            setStatus(status);
        } finally {
            progress.done();
        }
    }

    /**
     * Return status of the operation. This method should be invoked after
     * {@link #run(IProgressMonitor)} to obtain the status of the performed
     * operation.
     * 
     * @return the status of the operation.
     */
    public synchronized IStatus getStatus() {
        return operationStatus;
    }

    private synchronized void setStatus(IStatus s) {
        operationStatus = s;
    }

    /**
     * Show the overwrite file message dialog
     */
    protected OverwriteStatus overwriteFileMessageDialog(
            final IPath outputPath) {
        final OverwriteStatus[] result = new OverwriteStatus[1];
        Runnable runnable = new Runnable() {
            public void run() {
                String msg = String.format(
                        Messages.WDMExportOperation_OverwriteDestination_message,
                        outputPath.lastSegment(),
                        outputPath.removeLastSegments(1).toOSString());

                OverwriteFileMessageDialog dialog =
                        new OverwriteFileMessageDialog(shellProvider.getShell(),
                                Messages.WDMExportOperation_FileExists_title,
                                msg);
                OverwriteStatus status =
                        dialog.getOverwriteStatus(dialog.open());
                synchronized (this) {
                    result[0] = status;
                }
            }
        };
        Display.getDefault().syncExec(runnable);
        synchronized (runnable) {
            return result[0];
        }
    }
}