/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.ant.tasks;

import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.rasc.core.Messages;
import com.tibco.xpd.rasc.core.RascController;
import com.tibco.xpd.rasc.core.impl.RascControllerImpl;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.builder.BuildSynchronizerUtil;
import com.tibco.xpd.resources.logger.events.CorrelatingEventHandler;
import com.tibco.xpd.resources.logger.events.EventProcessor;
import com.tibco.xpd.resources.logger.events.EventProcessor.Event;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;

/**
 * An implementation of an Ant Task to perform the generation of RASC files for a given project, or collection of
 * projects.
 * <p>
 * The ant task uses two properties to determine the output folder to which the RASC is to be written; the ant project's
 * "basedir" and the ant task's "destdir".
 * <p>
 * If the "destdir" is not specified, the output folder should default to the "Deployment Artifacts" folder within the
 * Studio Project Workspace. If the "destdir" specifies an absolute path, that path should be used as-is. If the
 * "destdir" specifies a relative path, it should be prefixed with the "basedir".
 * <p>
 * Note that with no destdir specified then each project should get it’s RASC exported into its Deployment Artifacts
 * folder.
 *
 * 
 * @author pwatson
 * @since 17 Apr 2019
 */
@SuppressWarnings("nls")
public class GenerateRascTask extends Task {
    public static final String DEFAULT_DEST_DIR =
            Messages.RascController_default_deploy_folder;

    private String destDir;

    private boolean buildBeforeGenerating;

    private String projectName;

    private boolean failonerror;

    /**
     * Returns the directory into which the generated RASC file is to be
     * written. This may be an absolute path or a path relative ANT basedir.
     * 
     * @return the directory into which the generated RASC is to be written.
     */
    public String getDestDir() {
        return destDir;
    }

    /**
     * Sets the directory into which the generated RASC file is to be written.
     * This may be an absolute path or a path relative ANT basedir.
     * 
     * @param aValue
     *            the directory into which the generated RASC is to be written.
     */
    public void setDestDir(String aValue) {
        destDir = (aValue == null) ? null : aValue.trim();
    }

    /**
     * Called prior to creating the RASC file. Returns the File handle to the
     * directory into which the RASC is to be written.
     * <p>
     * It takes its initial value from the {@link #getDestDir() destination
     * directory}. If that value identifies a relative path, it will be prefixed
     * with the configured Ant base directory. Also ensures that the directory
     * exists.
     * 
     * @param aProject
     *            the project for which the RASC file is being generated.
     * @return the handle to the output directory.
     * @throws IOException
     *             if the directory does not exist and cannot be created.
     */
    private File getDestination(IProject aProject) throws IOException {
        File result;

        // if no destination is specified
        String dir = getDestDir();
        if ((dir == null) || (dir.isEmpty())) {
            // use the project folder and default sub-dir
            result = new File(getProjectFolder(aProject),
                    GenerateRascTask.DEFAULT_DEST_DIR);
        }

        // use the specified destdir
        else {
            result = new File(dir);

            // if this is a relative path
            if (!result.isAbsolute()) {
                // use the ANT project basedir
                File basedir = getProject().getBaseDir();

                // if ANY project has no basedir
                if (basedir == null) {
                    // use the projects workspace
                    basedir = new File(getProjectFolder(aProject),
                            GenerateRascTask.DEFAULT_DEST_DIR);
                }

                result = new File(basedir, getDestDir());
            }
        }

        // ensure the directory exists
        if ((!result.exists()) && (!result.mkdirs())) {
            throw new NotDirectoryException(
                    "Unable to create destdir: " + getDestDir());
        }

        return result;
    }

    private File getProjectFolder(IProject aProject) {
        return new File(aProject.getLocation().toOSString());
    }

    /**
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param aValue
     *            the projectName to set
     */
    public void setProjectName(String aValue) {
        projectName = (aValue != null) ? aValue.trim() : null;
    }

    /**
     * Tests if a specific project has been named for export.
     * 
     * @return <code>true</code> if a specific project has been named.
     */
    private boolean isProjectNamed() {
        return (projectName != null) && (!projectName.isEmpty());
    }

    private boolean isBPMStudioProject(IProject aProject) {
        return (aProject != null) && (aProject.isAccessible())
                && (ProjectUtil.isStudioProject(aProject))
                && (GlobalDestinationUtil.isGlobalDestinationEnabled(aProject,
                        N2Utils.N2_GLOBAL_DESTINATION_ID));
    }

    /**
     * Returns a list of <code>IProject</code>s to be considered for export. The
     * list will be ordered according to their dependencies.
     * 
     * @return dependency sorted list of <code>IProject</code>s.
     * @throws CoreException
     */
    private List<IProject> getProjects() throws CoreException {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        if (workspace == null) {
            return Collections.emptyList();
        }

        IWorkspaceRoot root = workspace.getRoot();
        if (root == null) {
            return Collections.emptyList();
        }

        List<IProject> result = new ArrayList<>();

        if (isProjectNamed()) {
            IProject rootProject = root.getProject(getProjectName());
            if (rootProject.exists()) {
                result.add(rootProject);
            }
        } else {
            // no specific project named so use all
            for (IProject p : root.getProjects()) {
                if (isBPMStudioProject(p)) {
                    result.add(p);
                }
            }
        }

        return (result.isEmpty()) ? result
                : ProjectUtil.getDependencySortedProjects(result);
    }

    public boolean isBuildBeforeGenerating() {
        return buildBeforeGenerating;
    }

    public void setBuildBeforeGenerating(boolean aValue) {
        buildBeforeGenerating = aValue;
    }

    /**
     * 
     * @return the failonerror
     */
    public boolean isFailOnError() {
        return failonerror;
    }

    /**
     * Determines whether the Ant Task should fail if the RASC generation fails
     * for any given project.
     * 
     * @param aValue
     *            the failonerror to set.
     */
    public void setFailOnError(boolean aValue) {
        failonerror = aValue;
    }

    /**
     * @see org.apache.tools.ant.Task#execute()
     * 
     * @throws BuildException
     */
    @Override
    public void execute() throws BuildException {
        try {
            IProgressMonitor monitor = new ConsoleProgressMonitor();

            // wait for any existing builds to finish
            IStatus buildStatus =
                    synchronizedBuild(null, monitor, false, false);

            if (buildStatus.getSeverity() == IStatus.ERROR) {
                monitor.setTaskName("Waiting for build failed.");
                monitor.setTaskName("Reported issues:");
                monitor.setTaskName(statusToString(buildStatus));
                return;
            }

            List<IProject> projects = getProjects();
            if (projects.isEmpty()) {
                monitor.setTaskName(
                        "Deployment Artifact Generation: No projects found.");

                // if a specific project was named
                if (isProjectNamed()) {
                    // raise an error
                    throw new BuildException(String.format(
                            "Unable to locate named project '%1$s'",
                            getProjectName()));
                }
                return;
            }

            generateRASCs(projects, monitor);
        } catch (BuildException e) {
            throw e;
        } catch (Exception e) {
            throw new BuildException(e.getMessage(), e);
        }
    }

    /**
     * Generate RASCs for the given projects.
     * 
     * @param studioProjectNames
     * @throws CoreException
     */
    private void generateRASCs(List<IProject> aProjects,
            IProgressMonitor aMonitor) throws Exception {
        aMonitor.beginTask("Deployment Artifact Generation",
                aProjects.size() + 1);
        try {
            aMonitor.setTaskName(getStartingLabel(aProjects));

            if (isBuildBeforeGenerating()) {
                IStatus status = build(aProjects, aMonitor);
                if (status != Status.OK_STATUS) {
                    aMonitor.setTaskName(
                            "Unable to generate Deployment Artifact(s). There were errors building the workspace project(s): "
                                    + status.getMessage());
                    return;
                }
            }

            // always check for errors - even if we have just done a build
            if (errorsExist(aProjects)) {
                aMonitor.setTaskName(
                        "Unable to generate Deployment Artifact(s). One or more projects have problems that must be resolved first.");
                return;
            }

            EventProcessor ep = new EventProcessor(new SummaryEventHandler());
            RascController controller = new RascControllerImpl();

            boolean failed = false;
            for (IProject nextProject : aProjects) {
                String thisName = nextProject.getName();

                if (!nextProject.exists()) {
                    aMonitor.setTaskName("Can't find project: " + thisName);
                    continue;
                }
                ep.begin(thisName);
                try {
                    if (!controller.hasContributionsFor(nextProject)) {
                        aMonitor.setTaskName(String.format(
                                "Deployment Artifact generation was skipped for project '%1$s' (does not contain deployable content).",
                                thisName));
                        ep.end(thisName, Result.SKIPPED.toString());
                        continue;
                    }

                    // Generate.
                    aMonitor.setTaskName(
                            "Starting Deployment Artifact generation for project: "
                                    + thisName);
                    IStatus result = generateRASC(controller,
                            nextProject,
                            SubProgressMonitorEx
                                    .createMainProgressMonitor(aMonitor, 10));
                    if ((result == null)
                            || (result.getSeverity() > IStatus.WARNING)) {
                        aMonitor.setTaskName(
                                "Deployment Artifact generation failed for project: "
                                        + thisName);
                        ep.end(thisName, Result.FAILURE.toString());
                        failed = true;
                        continue;
                    } else if (result.getSeverity() == IStatus.WARNING) {
                        aMonitor.setTaskName(String.format(
                                "Deployment Artifact generation for project '%1$s' finished with warnings.",
                                thisName));
                        aMonitor.setTaskName("Reported issues:");
                        aMonitor.setTaskName(statusToString(result));
                    } else { // OK
                        aMonitor.setTaskName(String.format(
                                "Deployment Artifact generation for project '%1$s' finished successfully.",
                                thisName));
                    }

                    ep.end(thisName, result.getMessage());
                } catch (Exception e) {
                    ep.end(thisName, Result.FAILURE.toString());
                    failed = true;
                }
            }
            ep.event(SummaryEventHandler.PRINT_SUMMARY);
            if (failed && isFailOnError()) {
                throw new BuildException(
                        "Deployment Artifact generation failed for (at least) one of the requested projects.");
            }
        } finally {
            aMonitor.done();
        }
    }

    /**
     * Generates the RASC before deployment can take place
     * 
     * @param aProject
     * @return
     */
    private IStatus generateRASC(RascController aController, IProject aProject,
            IProgressMonitor aMonitor) throws InterruptedException {
        /*
         * RASC needs to be run in a new thread as the main thread's Ant class
         * loader cannot load OAW .ext files from bundle's classpath. The used
         * job is synchronous - current thread will on join.
         */
        Job job = new Job("Deployment Artifact generation for project: "
                + aProject.getName()) {
            @Override
            protected IStatus run(IProgressMonitor aProgress) {
                File rascFile;
                try {
                    rascFile = getOutputFile(aProject);
                    aProgress.setTaskName("Generating Deployment Artifact: "
                            + rascFile.getAbsolutePath());
                    aController.generateRasc(aProject, rascFile, aProgress);
                } catch (Exception e) {
                    return new Status(IStatus.ERROR,
                            XpdResourcesPlugin.ID_PLUGIN, 1,
                            "Exception was thrown while generating Deployment Artifact for "
                                    + aProject.getName(),
                            e);
                }
                return new Status(IStatus.OK, XpdResourcesPlugin.ID_PLUGIN,
                        "Generated Deployment Artifact: "
                                + rascFile.getAbsolutePath());
            }
        };
        job.setUser(false);
        job.schedule();

        job.join();
        return job.getResult();
    }

    /**
     * Returns a handle to the file into which the RASC generated for the given
     * project should be written.
     * 
     * @param aProject
     *            the project for which the RASC file is to be written.
     * @return the file into which the RASC is to be written.
     * @throws IOException
     *             if the output directory does not exist and cannot be created.
     */
    private File getOutputFile(IProject aProject) throws IOException {
        return new File(getDestination(aProject), aProject.getName() + ".rasc");
    }

    /**
     * @param aProjects
     * @return
     */
    private String getStartingLabel(List<IProject> aProjects) {
        StringBuilder sb = new StringBuilder();
        sb.append("Starting Deployment Artifact generation for projects: [");
        boolean first = true;
        for (IProject p : aProjects) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(p.getName());
        }
        sb.append("]\n");
        sb.append(String.format(
                "Number of projects for Deployment Artifact generation = %d",
                Integer.valueOf(aProjects.size())));
        return sb.toString();
    }

    private static String statusToString(IStatus status) {
        StringBuilder sb = new StringBuilder();
        appendStatus(sb, status, "");
        return sb.toString();
    }

    private static void appendStatus(StringBuilder aBuilder, IStatus aStatus,
            String aIndent) {
        final String oneIndent = "  ";
        appendStatusLine(aBuilder, aStatus, aIndent);
        if (aStatus instanceof MultiStatus) {
            String childIndent = aIndent + oneIndent;
            for (IStatus child : aStatus.getChildren()) {
                aBuilder.append('\n').append(childIndent);
                appendStatus(aBuilder, child, childIndent);
            }
        }
    }

    private static void appendStatusLine(StringBuilder aBuilder,
            IStatus aStatus, String aIndent) {
        int severity = aStatus.getSeverity();
        if (severity == IStatus.OK) {
            aBuilder.append("OK");
        } else if (severity == IStatus.ERROR) {
            aBuilder.append("ERROR");
        } else if (severity == IStatus.WARNING) {
            aBuilder.append("WARNING");
        } else if (severity == IStatus.INFO) {
            aBuilder.append("INFO");
        } else if (severity == IStatus.CANCEL) {
            aBuilder.append("CANCEL");
        } else {
            aBuilder.append("severity=").append(severity);
        }
        aBuilder.append(": ").append(aStatus.getMessage());
        Throwable e = aStatus.getException();
        if (e != null) {
            aBuilder.append('\n').append(aIndent);
            aBuilder.append("Exception: ").append(e.getLocalizedMessage());
            for (StackTraceElement el : e.getStackTrace()) {
                aBuilder.append('\n').append(aIndent).append(el.toString());
            }
            aBuilder.append('\n').append(aIndent);
        }
    }

    /**
     * For a given collection of Studio Projects tells whether there are error
     * level problem markers on them or their referenced projects.
     * 
     * @param aProjects
     *            collection of projects.
     * @return true if one of the studioProjects or one of their referenced
     *         project has error marker.
     * @throws CoreException
     */
    private boolean errorsExist(Collection<IProject> aProjects)
            throws CoreException {
        // collate all projects and referenced projects
        Set<IProject> allProjects = new HashSet<>();
        for (IProject studioProject : aProjects) {
            Set<IProject> referencedProjects = ProjectUtil
                    .getReferencedProjectsHierarchy(studioProject, null);

            allProjects.addAll(referencedProjects);
            allProjects.add(studioProject);
        }

        // search for any error marker
        for (IProject eachProject : allProjects) {
            if (!eachProject.isAccessible()) {
                continue;
            }
            IMarker[] markers = eachProject
                    .findMarkers(null, true, IResource.DEPTH_INFINITE);
            for (IMarker marker : markers) {
                if (marker.getAttribute(IMarker.SEVERITY,
                        IMarker.SEVERITY_INFO) == IMarker.SEVERITY_ERROR) {
                    // no need to check further
                    return true;
                }
            }
        }

        return false;
    }

    private enum Result {
        SUCCESS("Success"), //
        FAILURE("Failure"), //
        SKIPPED("Skipped");
        private final String label;

        Result(String aValue) {
            label = aValue;
        }

        @Override
        public String toString() {
            return label;
        };
    }

    private static class SummaryEventHandler extends CorrelatingEventHandler {
        private static final String PRINT_SUMMARY = "[PRINT SUMMARY]";

        /**
         * Contains 2 elements table with correlated events: event[0] - begin
         * event, event[1] - end event.
         */
        protected List<Event[]> endEvents = new ArrayList<>();

        /**
         * @see com.tibco.xpd.resources.logger.events.EventProcessor.EventHandler#handleLog(com.tibco.xpd.resources.logger.events.EventProcessor.Event)
         */
        @Override
        public void handleCustomEvent(Event event) {
            if (PRINT_SUMMARY.equals(event.getName())) {
                StringBuilder sb = new StringBuilder();
                sb.append(
                        "---------------- DEPLOYMENT ARTIFACT GENERATION SUMMARY ---------------\n");

                for (Event[] events : endEvents) {
                    Event begin = events[0];
                    Event end = events[1];

                    Long elapsed =
                            Long.valueOf(end.getTime() - begin.getTime());
                    String endResult =
                            end.getLabels().length > 0 ? end.getLabels()[0]
                                    : "";
                    sb.append(String.format(
                            "|%-35s -> %s\n|  Duration (ms): %d\n|\n",
                            end.getName(),
                            endResult,
                            elapsed));
                }
                sb.append(
                        "-----------------------------------------------------------------------\n");
                System.out.println(sb.toString());
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

    private IStatus build(List<IProject> aProjects, IProgressMonitor aMonitor)
            throws InterruptedException, CoreException {
        IStatus status = synchronizedBuild(aProjects,
                SubProgressMonitorEx.createMainProgressMonitor(aMonitor, 1),
                true,
                true);
        if (status != null && status.getSeverity() > IStatus.WARNING) {
            aMonitor.setTaskName("Reported build problems:");
            aMonitor.setTaskName(statusToString(status));
            return status;
        }

        // Check errors
        long ts = System.currentTimeMillis();
        boolean errorProblemMarkers = errorsExist(aProjects);
        aMonitor.setTaskName("Checking projects' error markers took:  "
                + (System.currentTimeMillis() - ts) + "ms "
                + (errorProblemMarkers ? "[errors detected]" : "[no errors]"));

        // Run extra build if errors detected.
        if (errorProblemMarkers) {
            aMonitor.setTaskName("Post-import build 2 ... ");
            status = synchronizedBuild(aProjects,
                    SubProgressMonitorEx.createMainProgressMonitor(aMonitor, 1),
                    true,
                    true);
            aMonitor.setTaskName(
                    String.format("Post-import build 2 end: %1$dms",
                            Long.valueOf(System.currentTimeMillis() - ts)));
        }

        if (status != null && status.getSeverity() > IStatus.WARNING) {
            aMonitor.setTaskName("Reported build problems:");
            aMonitor.setTaskName(statusToString(status));
            return status;
        }

        return Status.OK_STATUS;
    }

    /**
     * This method builds the provided projects and waits for the the builds (or
     * refresh job) to finish.
     * 
     * @param aProjects
     *            the list of projects to be synchronized, if 'null' then it
     *            only waits for jobs to finish.
     * @param aMonitor
     *            the job progress monitor.
     * 
     * @return IStatus the synchronization status
     **/
    private IStatus synchronizedBuild(List<IProject> aProjects,
            IProgressMonitor aMonitor, boolean doClean, boolean forceFullBuild)
            throws InterruptedException {
        /*
         * RASC needs to be run in a new thread as the main thread's Ant class
         * loader cannot load OAW .ext files from bundle's classpath. The used
         * job is synchronous - current thread will join.
         */
        Job job = new Job("Building projects...") {
            @Override
            protected IStatus run(IProgressMonitor aProgress) {
                aProgress.beginTask("Building projects...", 100);
                try {
                    // Perform synchronized build.
                    if (aProjects != null && !aProjects.isEmpty()) {
                        if (doClean) {
                            for (IProject p : aProjects) {
                                aProgress.setTaskName(
                                        String.format("Cleaning project: %1$s",
                                                p.getName()));
                                p.build(IncrementalProjectBuilder.CLEAN_BUILD,
                                        SubProgressMonitorEx
                                                .createMainProgressMonitor(
                                                        aProgress,
                                                        20));
                            }
                        }
                        for (IProject p : aProjects) {
                            aProgress.setTaskName(
                                    String.format("Building project: %1$s",
                                            p.getName()));
                            IWorkspace workspace =
                                    ResourcesPlugin.getWorkspace();
                            if (!workspace.isAutoBuilding() || forceFullBuild) {
                                p.build(IncrementalProjectBuilder.FULL_BUILD,
                                        SubProgressMonitorEx
                                                .createMainProgressMonitor(
                                                        aProgress,
                                                        40));
                            } else {
                                p.build(IncrementalProjectBuilder.INCREMENTAL_BUILD,
                                        SubProgressMonitorEx
                                                .createMainProgressMonitor(
                                                        aProgress,
                                                        40));
                            }
                        }
                    }

                    // Wait for scheduled builds.
                    BuildSynchronizerUtil
                            .waitForBuildsToFinish(SubProgressMonitorEx
                                    .createMainProgressMonitor(aProgress, 40));
                } catch (CoreException e) {
                    IStatus status = new Status(IStatus.ERROR,
                            XpdResourcesPlugin.ID_PLUGIN, 1,
                            "Core exception was thrown while building project(s).",
                            e);
                    return status;
                } catch (OperationCanceledException e) {
                    return Status.CANCEL_STATUS;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return Status.CANCEL_STATUS;
                } finally {
                    aProgress.setTaskName("");
                    aProgress.done();
                }

                return Status.OK_STATUS;
            }
        };
        job.setUser(false);
        job.schedule();

        // max wait 2h default.
        long buildWait = Long
                .parseLong(System.getProperty("build.wait.max.sec", "6400"));
        for (long time = 0L; (job.getState() != Job.NONE); time++) {
            Thread.sleep(500L);
            if (time >= buildWait) {
                job.cancel();
                throw new BuildException(
                        String.format("Job '%1$s' has passed max wait time.",
                                job.getName()));
            }
        }

        return job.getResult();
    }
}
