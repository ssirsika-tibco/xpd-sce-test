package com.tibco.xpd.daa.internal.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.daa.CompositeContributor;
import com.tibco.xpd.daa.DaaActivator;
import com.tibco.xpd.daa.internal.BaseProjectDAAGenerator;
import com.tibco.xpd.daa.internal.Messages;
import com.tibco.xpd.daa.internal.preferences.DAAGenPreferences;
import com.tibco.xpd.daa.internal.util.BpmProjectChecksumUtils;
import com.tibco.xpd.daa.internal.util.CompositeContributorsExtensionManager;
import com.tibco.xpd.daa.internal.util.StudioVersionUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.builder.BuildSynchronizerUtil;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;
import com.tibco.xpd.ui.importexport.exportwizard.pages.DestinationLocationType;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog.OverwriteStatus;
import com.tibco.xpd.validation.utils.ValidationProblemUtil;

/**
 * Represents the DAA generation operation for multiple project. The operation
 * will report progress.
 * 
 * @author mtorres
 */
public abstract class AbstractMultiProjectDAAGenerationWithProgress implements
        IRunnableWithProgress {

    public static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    private static boolean replaceQualifierWithTS = false;

    /**
     * The operation is wrapped in {@link IWorkspaceRunnable} to prevent
     * creating multiple subsequent resource deltas (and kicking of the build).
     * 
     * @author mtorres
     */
    protected class GenDAA implements IWorkspaceRunnable {
        @Override
        public void run(IProgressMonitor originalMonitor) {
            operationStatus = null;
            try {
                /*
                 * Can't guarantee what monitor was passed to us, so to take
                 * control properly we'll shell the orginal one in one that we
                 * have control over.
                 */
                originalMonitor.beginTask("", getSelectedProjects().size()); //$NON-NLS-1$

                List<IStatus> problems = new ArrayList<IStatus>();
                final BaseProjectDAAGenerator daaGen = getProjectDaaGenerator();
                for (IProject project : getSelectedProjects()) {
                    IProgressMonitor monitor =
                            SubProgressMonitorEx
                                    .createMainProgressMonitor(originalMonitor,
                                            1);
                    try {
                        monitor.beginTask(String
                                .format(Messages.AbstractProjectDAAGenerator_GeneratingDaaForProject,
                                        project.getName()),
                                2);

                        if (monitor.isCanceled()) {
                            throw new OperationCanceledException();
                        }

                        IStatus s =
                                daaGen.generateDAA(project,
                                        SubProgressMonitorEx
                                                .createMainProgressMonitor(monitor,
                                                        1),
                                        isReplaceQualifierWithTS());

                        if (s.getSeverity() > IStatus.OK) {
                            IStatus status =
                                    getDAAProjectGenerationStatus(project, s);
                            problems.add(status);
                            if (status.getSeverity() == IStatus.CANCEL) {
                                monitor.setCanceled(true);
                            }
                        }

                        /*
                         * If user already cacenlled duri8ng the generation then
                         * dont' make them answer the overwrite file question!
                         */
                        if (monitor.isCanceled()) {
                            throw new OperationCanceledException();
                        }

                        if (s.getSeverity() < IStatus.ERROR) {
                            // XPD-2523 Add Studio version to DAA.
                            IFile daaFile = daaGen.getDAAFile(project);
                            if (daaFile != null) {
                                StudioVersionUtil.includeVersionFile(daaFile);
                            }

                            IStatus status =
                                    postGenerationTask(project,
                                            SubProgressMonitorEx
                                                    .createNestedSubProgressMonitor(monitor,
                                                            1));
                            int severity = status.getSeverity();
                            if (status != null && severity > IStatus.OK
                                    && severity != IStatus.CANCEL) {
                                problems.add(status);
                            }
                            if (severity == IStatus.CANCEL) {
                                monitor.setCanceled(true);
                            }
                        }

                        if (monitor.isCanceled()) {
                            throw new OperationCanceledException();
                        }
                    } catch (OperationCanceledException e) {
                        // Operation canceled just return.
                        setStatus(Status.CANCEL_STATUS);
                        return;

                    } catch (Exception e) {
                        String message =
                                String.format(Messages.AbstractMultiProjectDAAGenerationWithProgress_ErrorGeneratingDaaForProject,
                                        project);
                        Status status =
                                new Status(IStatus.ERROR,
                                        DaaActivator.PLUGIN_ID, message, e);
                        DaaActivator.getDefault().getLog().log(status);
                        problems.add(status);

                    } finally {
                        monitor.done();
                    }

                }

                if (problems.isEmpty()) {
                    setStatus(Status.OK_STATUS);
                } else {
                    MultiStatus daaBuildStatus =
                            new MultiStatus(
                                    DaaActivator.PLUGIN_ID,
                                    0,
                                    Messages.AbstractMultiProjectDAAGenerationWithProgress_ProblemDuringDaaGeneration,
                                    null);
                    for (IStatus problem : problems) {
                        daaBuildStatus.add(problem);
                    }
                    setStatus(daaBuildStatus);
                }

            } finally {
                originalMonitor.done();
            }
        }
    }

    protected abstract BaseProjectDAAGenerator getProjectDaaGenerator();

    protected abstract boolean hasErrorLevelProblemMarkers(IProject project);

    protected abstract String getCompositeContributorContextId();

    private IStatus operationStatus = Status.OK_STATUS;

    protected List<IProject> selectedProjects;

    protected DestinationLocationType destLocationType;

    protected String destOutFolderPath;

    /**
     * New flag can be set on construction to control whether DAA itself should
     * be deleted when cleaning the staging folder at end of generation.
     */
    private final boolean preserveDaaAfterGeneration;

    /**
     * New constructor can be set on construction to control whether DAA itself
     * should be deleted when cleaning the staging folder at end of generation.
     * <p>
     * This will save the creator overriding the postGenerationTask() completely
     * just for this purpose (which is what happens commonly on idividual deploy
     * wizard /command line and so on).
     * 
     * @param preservDaaAfterGeneration
     */
    public AbstractMultiProjectDAAGenerationWithProgress(
            boolean preservDaaAfterGeneration) {
        super();
        this.preserveDaaAfterGeneration = preservDaaAfterGeneration;
    }

    /**
     * Performs additional operation after DAA generation finished successfully.
     * This method might be invoked from the background thread.
     * 
     * @param project
     *            the project.
     * @param monitor
     *            the progress monitor to report the progress.
     * @return the status of the operation
     */
    protected IStatus postGenerationTask(IProject project,
            final IProgressMonitor monitor) {

        final IProject proj = project;
        final IFile daaFile = getProjectDaaGenerator().getDAAFile(project);

        WorkspaceJob CalculateChecksumJob =
                new WorkspaceJob(
                        Messages.AbstractMultiProjectDAAGenerationWithProgress_CalculatingChecksum_message) {
                    @Override
                    public IStatus runInWorkspace(IProgressMonitor monitor)
                            throws CoreException {
                        BpmProjectChecksumUtils.calculateAndSetChecksum(proj,
                                daaFile);
                        return Status.OK_STATUS;
                    }
                };
        CalculateChecksumJob.setRule(ResourcesPlugin.getWorkspace().getRoot());
        CalculateChecksumJob.schedule();

        if (DAAGenPreferences.shouldCleanDAAStaging()) {
            /*
             * JA: The cleaning is now being asynchronously scheduled with a
             * workspace rule (to avoid deadlock with a refreshing job). It will
             * only execute when workspace (or all resource) locks are released
             * by other threads.
             */
            WorkspaceJob cleanStagingJob =
                    new WorkspaceJob(
                            Messages.AbstractMultiProjectDAAGenerationWithProgress_CleaningStagingFolderJob_name) {
                        @Override
                        public IStatus runInWorkspace(IProgressMonitor monitor)
                                throws CoreException {
                            /**
                             * Sid XPD-7573. This JIRA noted that sometimes
                             * whilst cleaning DAA staging folder on
                             * drag-drop-deploy, we would get a file delete
                             * error.
                             * 
                             * This could be VERY difficult to reproduce and
                             * never in debug. The cause is VERY timing
                             * dependent and basically comes down to the fact
                             * that whilst trying to perform the
                             * java.IO.File.delete() (from a
                             * eclipse.File.delete()) the os delete fails
                             * because something else hjas the file open.
                             * 
                             * The 'something else' was some filter expression
                             * running via CommonViewer which was doing a
                             * refresh. The fitler expression was a 'content
                             * type' Tester expression which opens the file in
                             * Java IO and happens to happen simultanesouly with
                             * our separate thread doing the delete.
                             * 
                             * So the fix is to try to prevent this happening by
                             * adding a little rery (with logging) loop.
                             */
                            int retryCount = 0;

                            while (true) {
                                try {
                                    cleanUpStagingArea(preserveDaaAfterGeneration,
                                            monitor);
                                    break;
                                } catch (CoreException e) {
                                    if (retryCount < 10) {
                                        retryCount++;
                                        DaaActivator
                                                .getDefault()
                                                .getLogger()
                                                .info("AbstractMultiProjectDAAGenerationWithProgress.postGenerationTask() retry: " //$NON-NLS-1$
                                                        + retryCount
                                                        + ": Failed attempting to clean up staging folder (retry in 1 seconds): " //$NON-NLS-1$
                                                        + e.getMessage());

                                        monitor.subTask(Messages.AbstractMultiProjectDAAGenerationWithProgress_RetryingCleanStagingFolder_message);
                                        try {
                                            Thread.sleep(1000);
                                            continue;
                                        } catch (InterruptedException e1) {
                                            break;
                                        }
                                    }

                                    return e.getStatus();
                                }
                            }

                            return Status.OK_STATUS;
                        }
                    };
            cleanStagingJob.setRule(ResourcesPlugin.getWorkspace().getRoot());
            cleanStagingJob.schedule();
        }
        return Status.OK_STATUS;
    }

    /**
     * Perform the multi-project daa generation. {@inheritDoc}
     */
    @Override
    public void run(final IProgressMonitor monitor) {
        try {
            /*
             * Sid XPD-2758. Moved all work into sub-function so we can start
             * top of monitor stack with new SubProgressMonitorEx
             */
            monitor.beginTask("", 6); //$NON-NLS-1$

            /*
             * Clean up after any previous exports on these projects.
             */
            cleanUpStagingArea(false,
                    SubProgressMonitorEx.createMainProgressMonitor(monitor, 1));

            if (monitor.isCanceled()) {
                setStatus(Status.CANCEL_STATUS);
                return;
            }

            /*
             * Pre-prepare the projects (allow the composite contributors chance
             * to do any last-minute stuff).
             * 
             * Execute CompositeContributor#prepareProject(...) in the workspace
             * runnable before it gets into composite generation and DAA
             * creation in a separate operation, so all ResourceListeners will
             * be notified and build run if needed.
             */
            ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
                @Override
                public void run(IProgressMonitor monitor) throws CoreException {
                    prepareProjects(monitor);
                }
            }, SubProgressMonitorEx.createMainProgressMonitor(monitor, 1));

            if (monitor.isCanceled()) {
                setStatus(Status.CANCEL_STATUS);
                return;
            }

            if (getStatus().getSeverity() > IStatus.WARNING) {
                return;
            }

            /*
             * Make sure any builds are complete.
             */
            IStatus synchronizedBuildStatus =
                    BuildSynchronizerUtil
                            .synchronizedBuild(getSelectedProjects(),
                                    SubProgressMonitorEx
                                            .createMainProgressMonitor(monitor,
                                                    1),
                                    false);

            if (synchronizedBuildStatus.getSeverity() > IStatus.WARNING) {
                setStatus(synchronizedBuildStatus);
                return;
            }

            if (monitor.isCanceled()) {
                setStatus(Status.CANCEL_STATUS);
                return;
            }
            if (getStatus().getSeverity() > IStatus.WARNING) {
                return;
            }

            /*
             * Make sure there are no problem markers on projects.
             */
            boolean validateOk =
                    validateProjects(SubProgressMonitorEx
                            .createMainProgressMonitor(monitor, 1));
            if (monitor.isCanceled()) {
                setStatus(Status.CANCEL_STATUS);
                return;
            }
            if (!validateOk) {
                return;
            }

            /*
             * One more wait for build to finish!
             */
            try {
                BuildSynchronizerUtil
                        .waitForBuildsToFinish(SubProgressMonitorEx
                                .createMainProgressMonitor(monitor, 1));

            } catch (InterruptedException e) {
                monitor.setCanceled(true);
            }

            if (monitor.isCanceled()) {
                setStatus(Status.CANCEL_STATUS);
                return;
            }

            /*
             * And finally... Generate the DAA's
             */
            ResourcesPlugin.getWorkspace().run(new GenDAA(),
                    SubProgressMonitorEx.createMainProgressMonitor(monitor, 1));

        } catch (CoreException e) {
            String message =
                    Messages.AbstractMultiProjectDAAGenerationWithProgress_ExceptionsWhileGeneratingDaas;
            Status status =
                    new Status(IStatus.ERROR, DaaActivator.PLUGIN_ID, message,
                            e);
            DaaActivator.getDefault().getLog().log(status);
            setStatus(status);

        } finally {
            monitor.done();
        }
    }

    /**
     * @param createNestedSubProgressMonitor
     * @return <code>true</code> if selected rojects have no error markers.
     */
    private boolean validateProjects(IProgressMonitor monitor) {
        try {
            monitor.beginTask(Messages.AbstractMultiProjectDAAGenerationWithProgress_ValidatingProjects,
                    getSelectedProjects().size());

            for (IProject project : getSelectedProjects()) {
                if (monitor.isCanceled()) {
                    return false;
                }

                if (hasErrorLevelProblemMarkers(project)) {
                    setStatus(ValidationProblemUtil
                            .getErrorMarkersStatus(project));
                    return false;
                }

                monitor.worked(1);
            }

            return true;

        } finally {
            monitor.done();
        }
    }

    /**
     * Clean up the DAA staging area.
     * 
     * @param monitor
     * @throws CoreException
     */
    protected void cleanUpStagingArea(boolean preserveDAA,
            IProgressMonitor monitor) throws CoreException {
        try {
            monitor.beginTask(Messages.AbstractMultiProjectDAAGenerationWithProgress_CleaningStagingAreas_message,
                    getSelectedProjects().size() + 1);

            for (IProject project : getSelectedProjects()) {
                if (monitor.isCanceled()) {
                    break;
                }

                BaseProjectDAAGenerator daaGenerator = getProjectDaaGenerator();

                // TODO JA: Workaround to avoid CDS build.
                daaGenerator.getModuleOutputFolder(project, true);

                // Clean staging folder. It has to be done before this operation
                // goes into "WorkspaceModify" mode so all notifications
                // (working copy depends on when cleaning in memory cache) can
                // be send and processed.
                daaGenerator.clean(project, preserveDAA, SubProgressMonitorEx
                        .createSubTaskProgressMonitor(monitor, 1));
            }
        } finally {
            monitor.done();
        }
    }

    /**
     * Pre-prepare the projects (allow the composite contributors chance to do
     * any last-minute stuff).
     * 
     * @param monitor
     */
    private void prepareProjects(IProgressMonitor monitor) {
        try {
            monitor.beginTask("", getSelectedProjects().size()); //$NON-NLS-1$

            MultiStatus multiStatus =
                    new MultiStatus(
                            DaaActivator.PLUGIN_ID,
                            0,
                            Messages.AbstractMultiProjectDAAGenerationWithProgress_ProblemsWithArtifactsPreparation_message,
                            null);

            // Validate project before composite creation.
            for (IProject project : getSelectedProjects()) {
                prepareProject(project,
                        multiStatus,
                        SubProgressMonitorEx
                                .createNestedSubProgressMonitor(monitor, 1));
                if (multiStatus.getSeverity() > IStatus.WARNING) {
                    break;
                }
                if (monitor.isCanceled()) {
                    monitor.setCanceled(true);
                    break;
                }
            }

            setStatus(multiStatus);

        } finally {
            monitor.done();
        }
    }

    /**
     * Pre-prepare the individual project (allow the composite contributors
     * chance to do any last-minute stuff).
     * 
     * @param project
     * @param multiStatus
     * @param monitor
     */
    private void prepareProject(IProject project, MultiStatus multiStatus,
            IProgressMonitor monitor) {
        try {
            List<CompositeContributor> compositeContributors =
                    CompositeContributorsExtensionManager.getInstance()
                            .getCompositeContributors();

            monitor.beginTask(String
                    .format(Messages.AbstractMultiProjectDAAGenerationWithProgress_PreparingProject_message,
                            project.getName()),
                    compositeContributors.size());

            for (CompositeContributor contributor : compositeContributors) {
                IStatus s =

                        contributor
                                .prepareProject(project,
                                        SubProgressMonitorEx
                                                .createNestedSubProgressMonitor(monitor,
                                                        1));

                if (s.getSeverity() > IStatus.OK) {
                    multiStatus.add(s);
                }
                if (s.getSeverity() > IStatus.WARNING) {
                    /*
                     * If there is problem with a contributor (for a specific
                     * project) skip others contributors for this project.
                     */
                    break;
                }
            }

        } finally {
            monitor.done();
        }
    }

    /**
     * Create new status based on provided with a new project specific message.
     */
    protected IStatus getDAAProjectGenerationStatus(IProject p, IStatus s) {
        String message = s.getMessage();
        message =
                String.format(Messages.AbstractMultiProjectDAAGenerationWithProgress_ProblemWithDaaGenerationForProject,
                        p);
        if (s.getMessage() != null && s.getMessage().trim().length() > 0) {
            message += " : " + s.getMessage(); //$NON-NLS-1$
        }

        // If a multi-status then maintain it
        if (s instanceof MultiStatus) {
            s =
                    new MultiStatus(s.getPlugin(), s.getCode(),
                            s.getChildren(), message, s.getException());
        } else {
            s =
                    new Status(s.getSeverity(), s.getPlugin(), message,
                            s.getException());
        }

        return s;
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
     * @return the replaceQualifierWithTS
     */
    public boolean isReplaceQualifierWithTS() {
        return replaceQualifierWithTS;
    }

    /**
     * @param replaceQualifierWithTS
     *            the replaceQualifierWithTS to set
     */
    public void setReplaceQualifierWithTS(boolean replaceQualifierWithTS) {
        AbstractMultiProjectDAAGenerationWithProgress.replaceQualifierWithTS =
                replaceQualifierWithTS;
    }

    /**
     * Show the overwrite file message dialog
     */
    protected OverwriteStatus overwriteFileMessageDialog(final IPath outputPath) {
        final OverwriteStatus[] result = new OverwriteStatus[1];
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String msg =
                        String.format(Messages.AbstractMultiProjectDAAGenerationWithProgress_FileAlreadyExists_message,
                                outputPath.lastSegment(),
                                outputPath.removeLastSegments(1).toOSString());

                OverwriteFileMessageDialog dialog =
                        new OverwriteFileMessageDialog(
                                Display.getDefault().getActiveShell(),
                                Messages.AbstractMultiProjectDAAGenerationWithProgress_FileAlreadyExists_title,
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

    public void setSelectedProjects(List<IProject> selectedProjects) {
        this.selectedProjects = selectedProjects;
    }

    public void setDestLocationType(DestinationLocationType destLocationType) {
        this.destLocationType = destLocationType;
    }

    public void setDestOutFolderPath(String destOutFolderPath) {
        this.destOutFolderPath = destOutFolderPath;
    }

    public DestinationLocationType getDestLocationType() {
        return destLocationType;
    }

    public String getDestOutFolderPath() {
        return destOutFolderPath;
    }

    public List<IProject> getSelectedProjects() {
        return selectedProjects;
    }

}