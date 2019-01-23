/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.ant.tasks;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.MultiRule;

import com.tibco.amf.tools.packager.internal.validatedaa.ValidateDaaOperation;
import com.tibco.xpd.ant.tasks.BPMGenerateDAATask.DAASummaryEventHandler.Result;
import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.n2.ant.tasks.AntTasksActivator;
import com.tibco.xpd.n2.daa.internal.Messages;
import com.tibco.xpd.n2.daa.internal.packager.DAAExportUtils;
import com.tibco.xpd.n2.daa.internal.wizards.FileCopier;
import com.tibco.xpd.n2.daa.internal.wizards.MultiProjectDAAGenerationWithProgress;
import com.tibco.xpd.n2.daa.propertytester.DeployableBPMAssetsTester;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.builder.BuildSynchronizerUtil;
import com.tibco.xpd.resources.logger.events.CorrelatingEventHandler;
import com.tibco.xpd.resources.logger.events.EventProcessor;
import com.tibco.xpd.resources.logger.events.EventProcessor.Event;
import com.tibco.xpd.resources.ui.util.PostImportUtil;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;
import com.tibco.xpd.resources.util.TargetPlatformInitialiser;
import com.tibco.xpd.ui.importexport.exportwizard.pages.DestinationLocationType;

/**
 * @author bharge
 * 
 */
public class BPMGenerateDAATask extends Task {

    private String daaLocation = "DaaOut"; //$NON-NLS-1$

    private boolean buildBeforeGenerating = false;

    private boolean skipPostImportTasks = true;

    private String projectName = null;

    private boolean validateDAA = false;

    private boolean failonerror = false;

    private final String newLn = System.getProperty("line.separator"); //$NON-NLS-1$

    /**
     * @return the daaLocation
     */
    public String getDaaLocation() {
        return daaLocation;
    }

    /**
     * @param daaLocation
     *            the daaLocation to set
     */
    public void setDaaLocation(String daaLocation) {
        this.daaLocation = daaLocation;
    }

    /**
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName
     *            the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public boolean isBuildBeforeGenerating() {
        return buildBeforeGenerating;
    }

    public void setBuildBeforeGenerating(boolean buildBeforeGenerating) {
        this.buildBeforeGenerating = buildBeforeGenerating;
    }

    /**
     * @return the validateDAA
     */
    public boolean isValidateDAA() {
        return validateDAA;
    }

    /**
     * @param validateDAA
     *            the validateDAA to set
     */
    public void setValidateDAA(boolean validateDAA) {
        this.validateDAA = validateDAA;
    }

    /**
     * @see org.apache.tools.ant.Task#execute()
     * 
     * @throws BuildException
     */
    @Override
    public void execute() throws BuildException {

        ensureInitialisationOfTargetPlatform();

        IProgressMonitor monitor = new ConsoleProgressMonitor();

        // XPD-4935 In help->validate & On export DAA we should ensure that the
        // correct target platform is set in Studio.
        IStatus targetValidationStatus =
                DAAExportUtils.validateTargetPlatform();

        if (!targetValidationStatus.isOK()) {
            log(targetValidationStatus.getMessage());
        }

        IStatus synchronizedBuildWaitStatus =
                synchronizedBuild(null, monitor, false, false);
        if (synchronizedBuildWaitStatus.getSeverity() == IStatus.ERROR) {
            monitor.setTaskName("Waiting for build failed."); //$NON-NLS-1$
            monitor.setTaskName("Reported issues:"); //$NON-NLS-1$
            monitor.setTaskName(statusToString(synchronizedBuildWaitStatus));
            return;
        }

        final List<IProject> projects = getProjects();

        try {
            generateDAAs(projects, monitor);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Collection of <code>IProject</code>s to be considered for export
     */
    private List<IProject> getProjects() {

        final List<IProject> projects = new ArrayList<IProject>();
        if (null != getProjectName() && getProjectName().length() > 0
                && ResourcesPlugin.getWorkspace() != null
                && ResourcesPlugin.getWorkspace().getRoot() != null) {
            IProject project =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getProject(getProjectName());
            if (project.exists()) {
                projects.add(project);
            }
        } else {
            projects.addAll(getAllStudioProjectsInWorkSpace());
        }

        return projects;
    }

    private void ensureInitialisationOfTargetPlatform() {

        if (TargetPlatformInitialiser.getInstance().initalise()) {
            String fmtStr = "%sTarget Platform appears to be initialized."; //$NON-NLS-1$
            log(String.format(fmtStr, newLn), Project.MSG_INFO);
        } else {
            String fmtStr =
                    "%sFailed to locate Target Platform definition for initialization."; //$NON-NLS-1$
            log(String.format(fmtStr, newLn), Project.MSG_WARN);
        }
    }

    /**
     * Generate DAAs for the given projects.
     * 
     * @param studioProjectNames
     * @throws CoreException
     */
    protected void generateDAAs(List<IProject> projects,
            IProgressMonitor monitor) throws CoreException {

        String bar = "********************************************************"; //$NON-NLS-1$

        List<IProject> sortedProjects =
                ProjectUtil.getDependencySortedProjects(projects);

        monitor.beginTask("DAA Generation", sortedProjects.size() + 1); //$NON-NLS-1$
        try {
            if (sortedProjects == null || sortedProjects.size() == 0) {
                monitor.setTaskName(bar);
                monitor.setTaskName("No project found to generate DAA."); //$NON-NLS-1$
                monitor.setTaskName(bar);
                return;
            }

            monitor.setTaskName(bar);
            monitor.setTaskName(getStartingLabel(sortedProjects));
            monitor.setTaskName(bar);

            // Post-import tasks
            monitor.setTaskName(bar);
            monitor.setTaskName("Post-import tasks."); //$NON-NLS-1$

            /*
             * JA: Gets a closure of all dependent projects for post-import
             * task.
             */
            final Set<IProject> postImportProjects = new HashSet<IProject>();
            try {
                for (IProject p : sortedProjects) {
                    if (isBPMStudioProject(p)) {
                        postImportProjects.add(p);
                        postImportProjects.addAll(ProjectUtil2
                                .getReferencedProjectsHierarchy(p, true));
                    }
                }
            } catch (CyclicDependencyException e) {
                monitor.setTaskName("ERROR: Projects have cyclic dependency."); //$NON-NLS-1$
                monitor.setTaskName(bar);
                return;
            }

            IStatus synchronizedBuildStatus = null;

            List<IProject> sortedPostImportProjects = null;

            if (!skipPostImportTasks) {

                /*
                 * Lock workspace during import and only broadcast workspace
                 * change at the end of the import
                 */
                final IWorkspace workspace = ResourcesPlugin.getWorkspace();
                try {
                    workspace.run(new IWorkspaceRunnable() {

                        @Override
                        public void run(IProgressMonitor monitor)
                                throws CoreException {
                            PostImportUtil
                                    .getInstance()
                                    .performPostImportTasks(postImportProjects,
                                            SubProgressMonitorEx
                                                    .createMainProgressMonitor(monitor,
                                                            1));

                        }
                    },
                            MultiRule.combine(workspace.getRoot(), workspace
                                    .getRuleFactory().buildRule()),
                            IWorkspace.AVOID_UPDATE,
                            monitor);
                } catch (CoreException e) {
                    monitor.setTaskName("ERROR: Failed post-import task."); //$NON-NLS-1$
                    monitor.setTaskName(String.format("Exception: %1$s %2$s", e //$NON-NLS-1$
                            .getClass().getName(), e.getMessage()));
                    monitor.setTaskName(bar);
                    AntTasksActivator.getDefault().getLog().log(e.getStatus());
                    return;
                }
                monitor.setTaskName("Finished post-import tasks."); //$NON-NLS-1$
                monitor.setTaskName(bar);

                /*
                 * Fire a build after post-import.
                 */
                monitor.setTaskName(bar);
                monitor.setTaskName("Post-import build... "); //$NON-NLS-1$
                long ts = System.currentTimeMillis();
                if (sortedPostImportProjects == null) {
                    sortedPostImportProjects =
                            ProjectUtil
                                    .getDependencySortedProjects(postImportProjects);
                }
                synchronizedBuildStatus =
                        synchronizedBuild(sortedPostImportProjects,
                                SubProgressMonitorEx
                                        .createMainProgressMonitor(monitor, 1),
                                true,
                                true);
                monitor.setTaskName("Post-import build end:  " //$NON-NLS-1$
                        + (System.currentTimeMillis() - ts) + "ms"); //$NON-NLS-1$
            }

            if (isBuildBeforeGenerating()) {
                if (sortedPostImportProjects == null) {
                    sortedPostImportProjects =
                            ProjectUtil
                                    .getDependencySortedProjects(postImportProjects);
                }
                synchronizedBuildStatus =
                        synchronizedBuild(sortedPostImportProjects,
                                SubProgressMonitorEx
                                        .createMainProgressMonitor(monitor, 1),
                                true,
                                true);
                if (synchronizedBuildStatus != null
                        && synchronizedBuildStatus.getSeverity() > IStatus.WARNING) {
                    monitor.setTaskName("Reported build problems:"); //$NON-NLS-1$
                    monitor.setTaskName(statusToString(synchronizedBuildStatus));
                    monitor.setTaskName(bar);
                    return;
                }
            }

            /* Check errors */
            long ts = System.currentTimeMillis();
            boolean errorProblemMarkers =
                    CompositeUtil
                            .hasErrorLevelProblemMarkers(postImportProjects);
            monitor.setTaskName("Checking projects' error markers took:  " //$NON-NLS-1$
                    + (System.currentTimeMillis() - ts) + "ms " //$NON-NLS-1$
                    + (errorProblemMarkers ? "[errors detected]" //$NON-NLS-1$
                            : "[no errors]")); //$NON-NLS-1$

            /* Run extra build if errors detected. */
            if (errorProblemMarkers) {
                monitor.setTaskName("Post-import build 2 ... "); //$NON-NLS-1$
                ts = System.currentTimeMillis();
                if (sortedPostImportProjects == null) {
                    sortedPostImportProjects =
                            ProjectUtil
                                    .getDependencySortedProjects(postImportProjects);
                }
                synchronizedBuildStatus =
                        synchronizedBuild(sortedPostImportProjects,
                                SubProgressMonitorEx
                                        .createMainProgressMonitor(monitor, 1),
                                true,
                                true);
                monitor.setTaskName("Post-import build 2 end:  " //$NON-NLS-1$
                        + (System.currentTimeMillis() - ts) + "ms"); //$NON-NLS-1$
            }

            if (synchronizedBuildStatus != null
                    && synchronizedBuildStatus.getSeverity() > IStatus.WARNING) {
                monitor.setTaskName("Reported build problems:"); //$NON-NLS-1$
                monitor.setTaskName(statusToString(synchronizedBuildStatus));
                monitor.setTaskName(bar);
                return;
            }
            monitor.setTaskName(bar);

            boolean failed = false;

            EventProcessor ep =
                    new EventProcessor(new DAASummaryEventHandler());

            for (IProject project : sortedProjects) {

                if (!project.exists()) {
                    monitor.setTaskName("Can't find project: " //$NON-NLS-1$
                            + project.getName());
                    continue;
                }
                ep.begin(project.getName());
                try {
                    if (!shouldGenerateDAA(project)) {
                        monitor.setTaskName(bar);
                        monitor.setTaskName("DAA generation was skipped for project: " //$NON-NLS-1$
                                + project.getName());
                        monitor.setTaskName(bar);
                        ep.end(project.getName(), Result.SKIPPED.toString());
                        continue;
                    }

                    // Generate.
                    monitor.setTaskName("Starting DAA generation for project: " //$NON-NLS-1$
                            + project.getName());
                    IStatus result =
                            generateDAA(project,
                                    SubProgressMonitorEx
                                            .createMainProgressMonitor(monitor,
                                                    10));
                    if (result == null) {
                        monitor.setTaskName(bar);
                        monitor.setTaskName("DAA generation failed for project: " //$NON-NLS-1$
                                + project.getName());
                        monitor.setTaskName(bar);
                        ep.end(project.getName(), Result.FAILURE.toString());
                        failed = true;
                        continue;
                    } else if (result.getSeverity() > IStatus.WARNING) {
                        monitor.setTaskName(bar);
                        monitor.setTaskName("DAA generation failed for project: " //$NON-NLS-1$
                                + project.getName());
                        monitor.setTaskName("Reported issues:"); //$NON-NLS-1$
                        monitor.setTaskName(statusToString(result));
                        monitor.setTaskName(bar);
                        ep.end(project.getName(), Result.FAILURE.toString());
                        failed = true;
                        continue;
                    } else if (result.getSeverity() == IStatus.WARNING) {
                        monitor.setTaskName(bar);
                        monitor.setTaskName(String
                                .format("DAA generation for project '%1$s' finished with warnings.", //$NON-NLS-1$
                                        project.getName()));
                        monitor.setTaskName("Reported issues:"); //$NON-NLS-1$
                        monitor.setTaskName(statusToString(result));
                        monitor.setTaskName(bar);
                    } else {
                        // OK
                        monitor.setTaskName(bar);
                        monitor.setTaskName(String
                                .format("DAA generation for project '%1$s' finished successfully.", //$NON-NLS-1$
                                        project.getName()));
                        monitor.setTaskName(bar);
                    }

                    ep.end(project.getName(), Result.SUCCESS.toString());
                } catch (Throwable e) {
                    e.printStackTrace();
                    ep.end(project.getName(), Result.FAILURE.toString());
                    failed = true;
                }
            }
            ep.event(DAASummaryEventHandler.PRINT_SUMMARY);
            if (failed && failonerror) {
                throw new BuildException(
                        "DAA generation for (at least) one of the requested projects failed."); //$NON-NLS-1$
            }
        } finally {
            monitor.done();
        }
    }

    /**
     * @param projects
     * @return
     */
    private String getStartingLabel(List<IProject> projects) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Starting DAA generation for projects: [")); //$NON-NLS-1$
        boolean first = true;
        for (IProject p : projects) {
            if (first) {
                first = false;
            } else {
                sb.append(", "); //$NON-NLS-1$
            }
            sb.append(p.getName());
        }
        sb.append("]\n"); //$NON-NLS-1$
        sb.append(String.format("Number of projects for DAA generation = %d", //$NON-NLS-1$
                projects.size()));
        return sb.toString();
    }

    private static String statusToString(IStatus status) {
        StringBuilder sb = new StringBuilder();
        appendStatus(sb, status, ""); //$NON-NLS-1$
        return sb.toString();
    }

    private static void appendStatus(StringBuilder sb, IStatus status,
            String indent) {
        String oneIndent = "  "; //$NON-NLS-1$
        appendStatusLine(sb, status, indent);
        if (status instanceof MultiStatus) {
            String childIndent = indent + oneIndent;
            for (IStatus child : status.getChildren()) {
                sb.append('\n').append(childIndent);
                appendStatus(sb, child, childIndent);
            }
        }
    }

    private static void appendStatusLine(StringBuilder sb, IStatus status,
            String currentIntend) {
        int severity = status.getSeverity();
        if (severity == IStatus.OK) {
            sb.append("OK"); //$NON-NLS-1$
        } else if (severity == IStatus.ERROR) {
            sb.append("ERROR"); //$NON-NLS-1$
        } else if (severity == IStatus.WARNING) {
            sb.append("WARNING"); //$NON-NLS-1$
        } else if (severity == IStatus.INFO) {
            sb.append("INFO"); //$NON-NLS-1$
        } else if (severity == IStatus.CANCEL) {
            sb.append("CANCEL"); //$NON-NLS-1$
        } else {
            sb.append("severity="); //$NON-NLS-1$
            sb.append(severity);
        }
        sb.append(": "); //$NON-NLS-1$
        sb.append(status.getMessage());
        Throwable e = status.getException();
        if (e != null) {
            sb.append('\n').append(currentIntend);
            sb.append("Exception: ").append(e.getLocalizedMessage());
            for (StackTraceElement el : e.getStackTrace()) {
                sb.append('\n').append(currentIntend);
                sb.append(el.toString());
            }
            sb.append('\n').append(currentIntend);
        }
    }

    /**
     * Generates the DAA before deployment can take place
     * 
     * @param project
     * @return
     */
    private IStatus generateDAA(final IProject project,
            final IProgressMonitor monitor) {
        /*
         * JA: XPD-2856: DAA needs to be run in a new thread as the main
         * thread's Ant class loader cannot load OAW .ext files from bundle's
         * classpath. The used job is synchronous - current thread will on join.
         * 
         * Sid: New preserveDAA facility is not required as we override the
         * postGenerationTask and copy DAA out before calling super's cleanup
         * routines.
         */
        Job job =
                new Job(String.format("DAA generation for project: %1$s",
                        project.getName())) {
                    @Override
                    protected IStatus run(IProgressMonitor monitor) {
                        final MultiProjectDAAGenerationWithProgress daaGenOperation =
                                new MultiProjectDAAGenerationWithProgress(
                                        Arrays.asList(project), false) {
                                    /**
                                     * @see com.tibco.xpd.n2.daa.internal.wizards.MultiProjectDAAGenerationWithProgress#postGenerationTask(org.eclipse.core.resources.IProject,
                                     *      org.eclipse.core.runtime.IProgressMonitor)
                                     * 
                                     * @param project
                                     * @param monitor
                                     * @return
                                     */
                                    @Override
                                    protected IStatus postGenerationTask(
                                            IProject project,
                                            IProgressMonitor monitor) {
                                        IFile daaFile =
                                                getProjectDaaGenerator()
                                                        .getDAAFile(project);

                                        // Validate generated DAAs.
                                        if (isValidateDAA()) {
                                            validateDAA(daaFile, monitor);
                                        }
                                        copyGeneratedDAA(daaFile,
                                                project,
                                                monitor);
                                        return super
                                                .postGenerationTask(project,
                                                        monitor);
                                    }
                                };
                        daaGenOperation.setReplaceQualifierWithTS(true);
                        daaGenOperation.run(monitor);
                        IStatus opStatus = daaGenOperation.getStatus();
                        return opStatus;
                    }

                };
        job.setUser(false);
        job.schedule();
        try {
            job.join();
            return job.getResult();
        } catch (InterruptedException e) {
            throw new RuntimeException(
                    String.format("Job '%1$s' was interrupted.", job.getName()), //$NON-NLS-1$
                    e);
        }
    }

    /**
     * 
     */
    private void copyGeneratedDAA(IFile daaFile, IProject project,
            IProgressMonitor monitor) {
        // Check if an output folder is specified in the configuration
        String daaOutputLocation = getDaaLocation();
        if (daaFile != null && daaFile.exists()) {
            FileCopier copier;
            if (daaOutputLocation != null && daaOutputLocation.length() > 0) {
                copier =
                        new FileCopier(daaFile,
                                DestinationLocationType.SYSTEM_FILE,
                                daaOutputLocation);

                try {
                    monitor.setTaskName(String
                            .format("Copying the DAA '%1$s' for project '%2$s' to: '%3$s'",
                                    daaFile.getName(),
                                    project.getName(),
                                    daaOutputLocation));
                    copier.copy();
                } catch (CoreException e) {
                    monitor.setTaskName(String
                            .format("Failed to copy DAA '$1$s' for project '%2$s' to: '%3$s'",
                                    daaFile.getName(),
                                    project.getName(),
                                    daaOutputLocation));
                }

                // Also copy to Export DAA folder.
                copier =
                        new FileCopier(daaFile,
                                DestinationLocationType.PROJECT,
                                getWorkspaceExportFolder());
                try {
                    copier.copy();
                } catch (CoreException e) {
                    monitor.setTaskName(String
                            .format("Failed to copy DAA '$1$s' for project '%2$s' to: '%3$s'",
                                    daaFile.getName(),
                                    projectName,
                                    getWorkspaceExportFolder()));
                }
            }
        }
    }

    private String getWorkspaceExportFolder() {
        return "/" + Messages.ExportsFolder_title + "/" + Messages.DAAFolder_title; //$NON-NLS-1$ //$NON-NLS-2$
    }

    public static boolean isBPMStudioProject(IProject project) {
        if (project != null) {
            if (project.isAccessible()
                    && ProjectUtil.isStudioProject(project)
                    && GlobalDestinationUtil
                            .isGlobalDestinationEnabled(project,
                                    N2Utils.N2_GLOBAL_DESTINATION_ID))
                return true;
        }
        return false;
    }

    private boolean shouldGenerateDAA(IProject project) {
        DeployableBPMAssetsTester tester = new DeployableBPMAssetsTester();
        return tester.test(project,
                DeployableBPMAssetsTester.PROP_HAS_DEPLOYABLE_BPM_ASSETS,
                new Object[] {},
                null);
    }

    /**
     * 
     */
    private static void validateDAA(IFile daaFile, IProgressMonitor monitor) {
        ValidateDaaOperation validateDaaOperation =
                new ValidateDaaOperation(daaFile);
        try {
            validateDaaOperation.run(monitor);
            IStatus validationStatus =
                    validateDaaOperation.getValidationResults();
            monitor.setTaskName(String.format("DAA Validation status: %1$s",
                    statusToString(validationStatus)));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @return
     */
    public static List<IProject> getAllStudioProjectsInWorkSpace() {
        List<IProject> studioProjects = new ArrayList<IProject>();
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject[] projects = root.getProjects();
        for (IProject project : projects) {
            boolean studioProject = isBPMStudioProject(project);
            if (studioProject) {
                studioProjects.add(project);
            }
        }
        return studioProjects;
    }

    /**
     * @return the failonerror
     */
    public boolean isFailonerror() {
        return failonerror;
    }

    /**
     * @param failonerror
     *            the failonerror to set
     */
    public void setFailonerror(boolean failonerror) {
        this.failonerror = failonerror;
    }

    public static class DAASummaryEventHandler extends CorrelatingEventHandler {
        enum Result {
            SUCCESS("Success"), //
            FAILURE("Failure"), //
            SKIPPED("Skipped");
            private final String label;

            Result(String label) {
                this.label = label;
            }

            @Override
            public String toString() {
                return label;
            };
        }

        public static final String PRINT_SUMMARY = "[PRINT SUMMARY]"; //$NON-NLS-1$

        /**
         * Contains 2 elements table with correlated events: event[0] - begin
         * event, event[1] - end event.
         */
        protected List<Event[]> endEvents = new ArrayList<Event[]>();

        /**
         * Output a message line on your chosen device.
         * 
         * @param message
         */
        protected void outputLine(String message) {
            System.out.println(message);
        }

        /**
         * @see com.tibco.xpd.resources.logger.events.EventProcessor.EventHandler#handleLog(com.tibco.xpd.resources.logger.events.EventProcessor.Event)
         * 
         * @param event
         */
        @Override
        public void handleCustomEvent(Event event) {
            if (PRINT_SUMMARY.equals(event.getName())) {
                StringBuilder sb = new StringBuilder();
                sb.append(String
                        .format("-----------------%1$s:-----------------\n", "DAA GENERATION SUMARY")); //$NON-NLS-1$ 
                if (PRINT_SUMMARY.equals(event.getName())) {
                    for (Event[] events : endEvents) {
                        Event begin = events[0];
                        Event end = events[1];

                        long elapsed = (end.getTime() - begin.getTime());
                        String endResult =
                                end.getLabels().length > 0 ? end.getLabels()[0]
                                        : ""; //$NON-NLS-1$
                        sb.append(String
                                .format("|%-35s -> %s %-15s \n|  %s %s \n|\n", end.getName(), "Result:", endResult, "Time:", CorrelatingEventHandler.getElapsedTimeString(elapsed))); //$NON-NLS-1$

                    }
                }
                sb.append("--------------------------------------------------------\n"); //$NON-NLS-1$
                outputLine(sb.toString());
            }
        }

        /**
         * @see com.tibco.xpd.resources.logger.events.CorrelatingEventHandler#handleEnd(com.tibco.xpd.resources.logger.events.EventProcessor.Event,
         *      com.tibco.xpd.resources.logger.events.EventProcessor.Event)
         * 
         * @param event
         * @param correlatedBeginEvent
         */
        @Override
        public void handleEnd(Event event, Event correlatedBeginEvent) {
            endEvents.add(new Event[] { correlatedBeginEvent, event });
        }

        /**
         * @see com.tibco.xpd.resources.logger.events.CorrelatingEventHandler#getCorrelationId(com.tibco.xpd.resources.logger.events.EventProcessor.Event)
         * 
         * @param event
         * @return
         */
        @Override
        protected String getCorrelationId(Event event) {
            return event.getName();
        }
    }

    /**
     * This method builds the provided projects and waits for the the builds (or
     * refresh job) to finish.
     * 
     * @param projectList
     *            the list of projects to be synchronized, if 'null' then it
     *            only waits for jobs to finish.
     * @param monitor
     *            the job progress monitor.
     * 
     * @return IStatus the synchronization status
     **/
    private static IStatus synchronizedBuild(final List<IProject> projectList,
            IProgressMonitor monitor, final boolean doClean,
            final boolean forceFullBuild) {
        /*
         * JA: XPD-2856, XPD-3057: DAA needs to be run in a new thread as the
         * main thread's Ant class loader cannot load OAW .ext files from
         * bundle's classpath. The used job is synchronous - current thread will
         * join.
         */
        Job job = new Job(String.format("Building projects...")) {
            @Override
            protected IStatus run(IProgressMonitor progress) {
                progress.beginTask("Building projects...", 100);
                try {
                    // Perform synchronized build.
                    if (projectList != null && !projectList.isEmpty()) {

                        if (doClean) {
                            for (IProject project : projectList) {
                                progress.setTaskName(String
                                        .format("Cleaning project: %1$s",
                                                project.getName()));
                                project.build(IncrementalProjectBuilder.CLEAN_BUILD,
                                        SubProgressMonitorEx
                                                .createMainProgressMonitor(progress,
                                                        20));
                            }
                        }
                        for (IProject project : projectList) {
                            progress.setTaskName(String
                                    .format("Building project: %1$s",
                                            project.getName()));
                            IWorkspace workspace =
                                    ResourcesPlugin.getWorkspace();
                            if (!workspace.isAutoBuilding() || forceFullBuild) {
                                project.build(IncrementalProjectBuilder.FULL_BUILD,
                                        SubProgressMonitorEx
                                                .createMainProgressMonitor(progress,
                                                        40));
                            } else {
                                project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD,
                                        SubProgressMonitorEx
                                                .createMainProgressMonitor(progress,
                                                        40));
                            }
                        }
                    }
                    // Wait for scheduled builds.
                    BuildSynchronizerUtil
                            .waitForBuildsToFinish(SubProgressMonitorEx
                                    .createMainProgressMonitor(progress, 40));
                } catch (CoreException e) {
                    IStatus status =
                            new Status(
                                    IStatus.ERROR,
                                    XpdResourcesPlugin.ID_PLUGIN,
                                    1,
                                    "Core exception was thrown while building project(s).",
                                    e);
                    return status;
                } catch (OperationCanceledException e) {
                    return Status.CANCEL_STATUS;
                } catch (InterruptedException e) {
                    return Status.CANCEL_STATUS;
                } finally {
                    progress.setTaskName(""); //$NON-NLS-1$
                    progress.done();
                }

                return Status.OK_STATUS;
            }

        };
        job.setUser(false);
        job.schedule();
        try {
            long buildWaitMax =
                    Long.parseLong(System
                            .getProperty("build.wait.max.sec", "6400")); // max wait 2h default.  //$NON-NLS-1$//$NON-NLS-2$
            waitForJob(job, buildWaitMax);
            return job.getResult();
        } catch (InterruptedException e) {
            throw new RuntimeException(
                    String.format("Job '%1$s' was interrupted.", job.getName()), //$NON-NLS-1$
                    e);
        }
    }

    /**
     * Wait until job tasks is complete.
     * 
     * @throws InterruptedException
     */
    public static void waitForJob(final Job job, final long maxWaitTimeSeconds)
            throws InterruptedException {
        long waitTimeMillis = 1000L;
        Thread.sleep(waitTimeMillis);
        boolean waitForJob = true;
        for (long time = 0L; waitForJob; time++) {
            waitForJob = false;
            if (job.getState() != Job.NONE) {
                waitForJob = true;
            }
            Thread.sleep(waitTimeMillis);
            if (time >= maxWaitTimeSeconds) {
                throw new InterruptedException(
                        String.format("Job '%1$s' has passed max wait time.", //$NON-NLS-1$
                                job.getName()));
            }
        }
    }

    /**
     * @param skipPostImportTasks
     *            the skipPostImportTasks to set
     */
    public void setSkipPostImportTasks(boolean skipPostImportTasks) {
        this.skipPostImportTasks = skipPostImportTasks;
    }
}
