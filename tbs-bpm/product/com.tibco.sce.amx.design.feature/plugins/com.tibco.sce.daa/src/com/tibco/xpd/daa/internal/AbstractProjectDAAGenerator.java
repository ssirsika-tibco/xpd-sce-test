/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.daa.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.osgi.framework.Version;

import com.tibco.amf.model.daaspec.DistributedApplicationArchiveDescriptor;
import com.tibco.amf.sca.componenttype.ComponentTypeActivator;
import com.tibco.amf.sca.componenttype.CompositeModelBuilder;
import com.tibco.amf.sca.model.composite.Composite;
import com.tibco.amf.sca.model.composite.CompositePackage;
import com.tibco.amf.tools.packager.IPackagerService;
import com.tibco.amf.tools.packager.PackagerPlugin;
import com.tibco.xpd.daa.CompositeContributor;
import com.tibco.xpd.daa.DaaActivator;
import com.tibco.xpd.daa.internal.util.CompositeContributorsExtensionManager;
import com.tibco.xpd.daa.internal.util.DAANamingUtils;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.daa.internal.util.UnprotectedWriteOperation;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;

/**
 * Generates SCA composite and DAA archive for a specified eclipse project.
 * 
 * @author mtorres
 */
public abstract class AbstractProjectDAAGenerator extends
        BaseProjectDAAGenerator {

    public static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    /**
     * Private constructor. Use {@link #getInstance()} to obtain shared
     * instance.
     */
    public AbstractProjectDAAGenerator() {
    }

    /**
     * Generates the SCA composite and DAA archive for a specified project.
     * 
     * @param project
     *            the XPD project.
     * @param monitor
     *            the monitor to report the progress.
     * @param replaceWithTS
     *            flag to decide whether qualifier must be replaced with time
     *            stamp
     */
    @Override
    public IStatus generateDAA(IProject project,
            final IProgressMonitor monitor, final boolean replaceWithTS) {
        IChangeRecorder changeRecorder = getChangeRecorder();
        try {
            monitor.beginTask(String
                    .format(Messages.AbstractProjectDAAGenerator_GeneratingDaaForProject,
                            project.getName()),
                    3);

            LOG.debug("***************START*******************************"); //$NON-NLS-1$
            long beforeWholeTimeMillis = System.currentTimeMillis();

            // JA: Cleaning was moved outside of workspace modify operation
            // (into MultiProjectDAAGenerationWithProgress) to process resource
            // deletion notifications working copies depends on.
            // clean(project, new NullProgressMonitor());

            IFolder compositeOut = getModuleOutputFolder(project, false);

            Composite[] compositeArray = new Composite[1];

            long beforeCompositeTimeMillis = System.currentTimeMillis();

            String timeStamp = setTimestampOnProject(project);

            IStatus status =
                    buildComposite(project,
                            compositeOut,
                            compositeArray,
                            SubProgressMonitorEx
                                    .createNestedSubProgressMonitor(monitor, 1),
                            timeStamp,
                            replaceWithTS,
                            changeRecorder);

            if (status.getSeverity() > IStatus.WARNING) {
                clean(project, new NullProgressMonitor());
                MultiStatus compositeBuildStatus =
                        new MultiStatus(
                                DaaActivator.PLUGIN_ID,
                                0,
                                Messages.AbstractProjectDAAGenerator_ProblemWithCompositeGeneration,
                                null);
                compositeBuildStatus.add(status);
                return compositeBuildStatus;
            }

            doPostBuildComposite(compositeOut,
                    compositeArray,
                    timeStamp,
                    SubProgressMonitorEx
                            .createNestedSubProgressMonitor(monitor, 1));

            saveComposite(compositeArray[0]);

            compositeOut.refreshLocal(IResource.DEPTH_INFINITE, null);

            long afterCompositeTimeMillis = System.currentTimeMillis();
            long timeTakenToGenerateComposite =
                    afterCompositeTimeMillis - beforeCompositeTimeMillis;
            LOG.debug("*********The time taken to generate a Composite " //$NON-NLS-1$
                    + timeTakenToGenerateComposite + " milliseconds"); //$NON-NLS-1$

            if (compositeArray[0] != null) {
                // build DAA
                IFile compositeResource = getComposite(compositeOut, project);

                XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(compositeResource).reLoad();

                long beforeDAATimeMillis = System.currentTimeMillis();

                IStatus daaBuildStatus =
                        buildDAA(project,
                                compositeArray[0],
                                compositeResource,
                                compositeOut,
                                SubProgressMonitorEx
                                        .createNestedSubProgressMonitor(monitor,
                                                1),
                                timeStamp);

                postBuildDaaTask(compositeResource);

                long afterDAATimeMillis = System.currentTimeMillis();
                long timeTakenForDAAGeneration =
                        afterDAATimeMillis - beforeDAATimeMillis;
                long timeTakenForWholeProcess =
                        afterDAATimeMillis - beforeWholeTimeMillis;
                LOG.debug("*********The time taken to generate DAA " //$NON-NLS-1$
                        + timeTakenForDAAGeneration + " milliseconds"); //$NON-NLS-1$
                LOG.debug("*********The time taken for the whole process " //$NON-NLS-1$
                        + timeTakenForWholeProcess + " milliseconds"); //$NON-NLS-1$
                LOG.debug("***************END*******************************"); //$NON-NLS-1$

                if (status.getSeverity() > IStatus.OK) {
                    List<IStatus> children = new ArrayList<IStatus>();
                    children.add(status);
                    if (daaBuildStatus.getSeverity() > IStatus.OK) {
                        children.add(daaBuildStatus);
                    }
                    return new MultiStatus(
                            DaaActivator.PLUGIN_ID,
                            0,
                            children.toArray(new IStatus[children.size()]),
                            Messages.AbstractProjectDAAGenerator_ProblemsReported,
                            null);
                }
                return daaBuildStatus;
            } else {
                return new Status(
                        IStatus.ERROR,
                        DaaActivator.PLUGIN_ID,
                        String.format(Messages.AbstractProjectDAAGenerator_CompositeCouldNotBeGeneratedForProject,
                                project));
            }
        } catch (CoreException e) {
            LOG.error(e);
            return new Status(
                    IStatus.ERROR,
                    DaaActivator.PLUGIN_ID,
                    String.format(Messages.AbstractProjectDAAGenerator_ErrorReportedForGenerationOfDaa,
                            project), e);
        } finally {
            // reset all project versions
            monitor.done();
            revertProjectChanges(project);
        }
    }

    /**
     * @param compositeOut
     * @param compositeArray
     * @param timeStamp
     * @param monitor
     */
    private void doPostBuildComposite(IFolder compositeOut,
            Composite[] compositeArray, String timeStamp,
            IProgressMonitor monitor) {
        try {
            monitor.beginTask("Performing Composite Tasks", 1);
            postBuildCompositeTask(compositeArray[0], compositeOut, timeStamp);
        } finally {
            monitor.done();
        }
    }

    protected String setTimestampOnProject(IProject project) {
        // reading Project version
        String strProjectVersion = ProjectUtil.getProjectVersion(project);
        Version projectVersion = new Version(strProjectVersion);
        String qualifier = projectVersion.getQualifier();
        String timeStamp = qualifier;
        if (qualifier == null || qualifier.trim().length() < 1) {
            timeStamp = ""; //$NON-NLS-1$
        } else if (PluginManifestHelper.PROPERTY_QUALIFIER.equals(qualifier)) {
            timeStamp = DAANamingUtils.getAutogeneratedQualifier();
        }
        DAANamingUtils.setTimeStampOnProject(project, timeStamp);
        return timeStamp;
    }

    @Override
    protected abstract String getCompositeContributorContext();

    protected abstract boolean canClean(IProject project);

    @Override
    public abstract IFolder getModuleOutputFolder(IProject project,
            boolean create);

    protected abstract IFile getComposite(IFolder compositeOut, IProject project);

    protected abstract String getCompositeName(IProject project);

    protected abstract String getVersionNumber(IProject project);

    protected abstract void postBuildDaaTask(IFile compositeResource);

    protected abstract void postBuildCompositeTask(Composite composite,
            IFolder outFolder, String timeStamp);

    protected abstract boolean containsDaaErrors(IProject project);

    protected abstract Set<String> getSharedResources(Composite composite);

    protected abstract String[] getFeatureIds(IProject project);

    protected abstract void revertProjectChanges(IProject project);

    protected abstract IChangeRecorder getChangeRecorder();

    protected abstract String getDaaName(IProject project);

    protected IStatus buildComposite(final IProject project,
            final IFolder outFolder, Composite[] createdCompositeArray,
            IProgressMonitor monitor, final String timeStamp,
            final boolean replaceWithTS, IChangeRecorder changeRecorder) {

        try {

            MultiStatus status =
                    new MultiStatus(
                            DaaActivator.PLUGIN_ID,
                            0,
                            Messages.AbstractProjectDAAGenerator_ProblemsWhileCompositeCreation,
                            null);
            final List<CompositeContributor> compositeContributors =
                    CompositeContributorsExtensionManager
                            .getInstance()
                            .getCompositeContributors(getCompositeContributorContext());

            monitor.beginTask(String
                    .format(Messages.AbstractProjectDAAGenerator_BuildingCompositeForProject,
                            project.getName()),
                    (compositeContributors.size() * 2));

            // Validate project before composite creation.
            for (CompositeContributor contributor : compositeContributors) {
                IStatus s =
                        contributor.validate(project, SubProgressMonitorEx
                                .createNestedSubProgressMonitor(monitor, 1));

                if (s.getSeverity() == IStatus.CANCEL) {
                    return s;
                }
                if (s.getSeverity() > IStatus.OK) {
                    status.add(s);
                }
            }
            if (status.getSeverity() > IStatus.WARNING) {
                return new MultiStatus(DaaActivator.PLUGIN_ID, 0,
                        status.getChildren(),
                        Messages.AbstractProjectDAAGenerator_ComponentsProblem,
                        null);
            }

            final ComponentTypeActivator cts =
                    ComponentTypeActivator.getDefault();
            final CompositeModelBuilder mb = cts.getModelBuilder();

            final URI compositeLocation =
                    URI.createPlatformResourceURI(outFolder.getFullPath()
                            .append(getCompositeName(project))
                            .toPortableString(), true);
            final Composite composite = mb.createComposite(compositeLocation);

            // Contribute (and configure) components.
            for (CompositeContributor contributor : compositeContributors) {
                IStatus s =
                        contributor
                                .contributeToComposite(project,
                                        outFolder,
                                        composite,
                                        compositeLocation,
                                        timeStamp,
                                        replaceWithTS,
                                        changeRecorder,
                                        SubProgressMonitorEx
                                                .createNestedSubProgressMonitor(monitor,
                                                        1));
                if (s.getSeverity() == IStatus.CANCEL) {
                    return s;
                }
                if (s.getSeverity() > IStatus.OK) {
                    status.add(s);
                }
            }

            // check if the composite is not empty
            if (composite.getComponents().isEmpty()) {
                return new Status(
                        IStatus.ERROR,
                        DaaActivator.PLUGIN_ID,
                        Messages.AbstractProjectDAAGenerator_GeneratedCompositeEmpty);
            }
            if (status.getSeverity() > IStatus.WARNING) {
                return new MultiStatus(DaaActivator.PLUGIN_ID, 0,
                        status.getChildren(),
                        Messages.AbstractProjectDAAGenerator_ComponentsProblem,
                        null);
            }

            TransactionalEditingDomain editingDomain =
                    TransactionUtil.getEditingDomain(composite);

            Command cmd =
                    SetCommand.create(editingDomain,
                            composite,
                            CompositePackage.eINSTANCE.getComposite_Version(),
                            getVersionNumber(project));

            if (cmd != null && cmd.canExecute()) {
                editingDomain.getCommandStack().execute(cmd);
            }
            createdCompositeArray[0] = composite;
            return status;

        } finally {
            monitor.done();
        }
    }

    protected Composite saveComposite(final Composite composite) {

        TransactionalEditingDomain editingDomain =
                TransactionUtil.getEditingDomain(composite);

        // Saving composite.
        UnprotectedWriteOperation setContentsOp =
                new UnprotectedWriteOperation(editingDomain) {
                    @Override
                    protected Object doExecute() {
                        try {
                            composite.eResource().save(null);
                            LOG.debug(String.format //
                                    ("Composite " + "'%s' saved.", // //$NON-NLS-1$ //$NON-NLS-2$
                                            composite.eResource().getURI()
                                                    .toString()));
                        } catch (IOException ioex) {
                            LOG.error(ioex);
                        }
                        return composite;
                    }
                };
        setContentsOp.execute();

        if (composite != null) {
            IFile file =
                    WorkspaceSynchronizer.getUnderlyingFile(composite
                            .eResource());
            if (file != null) {
                try {
                    file.refreshLocal(IResource.DEPTH_ZERO, null);
                    file.setDerived(true);
                    LOG.debug(String.format("Local file" //$NON-NLS-1$
                            + " '%s' refreshed.", file.getFullPath())); //$NON-NLS-1$
                } catch (CoreException e) {
                    LOG.error(e);
                }
            }
        }
        return composite;
    }

    @SuppressWarnings("unchecked")
    private IStatus buildDAA(IProject project, Composite composite,
            IFile compositeFile, IFolder outFolder, IProgressMonitor monitor,
            String timeStamp) {

        try {
            monitor.beginTask("", 3); //$NON-NLS-1$

            if (!containsDaaErrors(project)) {
                IPackagerService packagerService =
                        PackagerPlugin.getDefault().getPackagerService();

                IFile[] modelFiles = new IFile[1];
                modelFiles[0] = compositeFile;
                String[] featureIds = getFeatureIds(project);
                DistributedApplicationArchiveDescriptor generateDaaDescriptor;
                try {
                    generateDaaDescriptor =
                            packagerService.generateDaaDescriptor(modelFiles,
                                    false,
                                    featureIds,
                                    new NullProgressMonitor());
                    generateDaaDescriptor.setQualifier(timeStamp);
                    String pathToAppend = getDaaName(project);
                    pathToAppend =
                            pathToAppend
                                    + "." + DAANamingUtils.DAA_FILE_EXTENSION; //$NON-NLS-1$
                    IPath daaFilePath =
                            outFolder.getFullPath().append(pathToAppend);
                    generateDaaDescriptor
                            .setDaaSaveLocationInWorkspace(daaFilePath
                                    .toPortableString());

                    Set<String> sharedResources = getSharedResources(composite);
                    if (sharedResources != null) {
                        generateDaaDescriptor.getPackagedApplications().get(0)
                                .getSharedResources().addAll(sharedResources);
                    }

                    generateDaaDescriptor.getPackagedApplications().get(0)
                            .getSharedResources()
                            .add("_compiled_svar.substvar"); //$NON-NLS-1$

                    Map optionsMap = new HashMap();
                    optionsMap
                            .put(IPackagerService.PLUGIN_BUILD_SCHEDULING_RULE,
                                    new MutexRule());
                    // TODO JA: Replace with constant when the it will be in
                    // Target
                    // Platform.
                    // optionsMap.put(IPackagerService.WAIT_FOR_AUTOBUILD_JOBS,
                    // Boolean.FALSE);
                    optionsMap.put("packager.service.wait.for.autobuild.jobs", //$NON-NLS-1$
                            Boolean.FALSE);

                    monitor.worked(1);

                    IStatus status =
                            packagerService
                                    .generateDaa(generateDaaDescriptor,
                                            optionsMap,
                                            SubProgressMonitorEx
                                                    .createSubTaskProgressMonitor(monitor,
                                                            1));

                    if (!status.isOK()) {
                        if (status.getException() != null) {
                            LOG.debug("DAA Status exception is " //$NON-NLS-1$
                                    + status.getException().toString());
                            XpdResourcesPlugin
                                    .getDefault()
                                    .getLogger()
                                    .error(status.getException(),
                                            "DAA Status exception is " //$NON-NLS-1$
                                                    + status.getException()
                                                            .toString()
                                                    + " in plugin " + status.getPlugin()); //$NON-NLS-1$
                        }
                        String message = status.getMessage();
                        LOG.debug("DAA Status message is " + message); //$NON-NLS-1$
                        XpdResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error("DAA Packager Status message " + message); //$NON-NLS-1$
                    }
                    IFile daaFile =
                            project.getWorkspace().getRoot()
                                    .getFile(daaFilePath);
                    if (daaFile != null && daaFile.exists()) {
                        daaFile.setDerived(true);
                    }
                    // cleanDaabinFolder(project, new NullProgressMonitor());

                    monitor.worked(1);
                    return status;

                } catch (CoreException e) {
                    XpdResourcesPlugin.getDefault().getLogger().error(e);
                }

            }
            return new Status(
                    IStatus.ERROR,
                    DaaActivator.PLUGIN_ID,
                    String.format(Messages.AbstractProjectDAAGenerator_ProjectWithProblems,
                            project));

        } finally {
            monitor.done();
        }
    }

    protected void cleanAllProjects(IProgressMonitor monitor)
            throws CoreException {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        int work = root.getProjects().length * 10;
        monitor.beginTask(Messages.AbstractProjectDAAGenerator_CleaningProjects,
                work);
        try {
            for (IProject project : root.getProjects()) {
                if (monitor.isCanceled())
                    throw new OperationCanceledException();
                if (canClean(project)) {
                    clean(project, new NullProgressMonitor());
                    monitor.worked(1);
                }
            }
        } finally {
            monitor.done();
        }

    }

    private static class MutexRule implements ISchedulingRule {
        @Override
        public boolean isConflicting(ISchedulingRule rule) {
            return rule == this;
        }

        @Override
        public boolean contains(ISchedulingRule rule) {
            return rule == this;
        }
    }

    public static void recordProjectVersionChange(String projectName,
            String originalVersion) {
        List<ProjectInfo> projectInfoList = getProjectInfo();
        ProjectInfo info = null;
        for (ProjectInfo eachProjInfo : projectInfoList) {
            if (eachProjInfo.getProjectName().equals(projectName)) {
                info = eachProjInfo;
                break;
            }
        }
        if (info == null) {
            info = new ProjectInfo();
            projectInfoList.add(info);
        }
        info.setProjectName(projectName);
        info.setOriginalProjectVersion(originalVersion);

    }

    /**
     * Not used for the time being as we assume all require bundle qualifier
     * gets replaced with bundle qualifier
     * 
     * @param projectName
     * @param requiredProjectId
     */
    public static void recordRequireBundleVersionChange(String projectName,
            String requiredProjectId) {
        List<ProjectInfo> projectInfoList = getProjectInfo();
        ProjectInfo info = null;
        for (ProjectInfo eachProjInfo : projectInfoList) {
            if (eachProjInfo.getProjectName().equals(projectName)) {
                info = eachProjInfo;
                break;
            }
        }
        if (info == null) {
            info = new ProjectInfo();
            info.setProjectName(projectName);
            projectInfoList.add(info);
        }
        info.getRequireBundles().add(requiredProjectId);
    }

    private static List<ProjectInfo> getProjectInfo() {
        return threadLocalProjectInfo.get();
    }

    private static ThreadLocalProjectInfo threadLocalProjectInfo =
            new ThreadLocalProjectInfo();

    private static class ThreadLocalProjectInfo extends
            ThreadLocal<List<ProjectInfo>> {
        @Override
        protected List<ProjectInfo> initialValue() {
            return new ArrayList<ProjectInfo>();
        }
    }

}
