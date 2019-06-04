/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.core.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;

import com.tibco.bpm.dt.rasc.DeploymentFactory;
import com.tibco.bpm.dt.rasc.DeploymentWriter;
import com.tibco.bpm.dt.rasc.GovernanceState;
import com.tibco.bpm.dt.rasc.MicroService;
import com.tibco.bpm.dt.rasc.PropertyValue;
import com.tibco.bpm.dt.rasc.Version;
import com.tibco.bpm.dt.rasc.VersionRange;
import com.tibco.bpm.dt.rasc.VersionRange.Endpoint;
import com.tibco.bpm.dt.rasc.exception.RuntimeApplicationException;
import com.tibco.bpm.dt.rasc.impl.DeploymentFactoryImpl;
import com.tibco.xpd.rasc.core.Messages;
import com.tibco.xpd.rasc.core.RascActivator;
import com.tibco.xpd.rasc.core.RascContext;
import com.tibco.xpd.rasc.core.RascContributor;
import com.tibco.xpd.rasc.core.RascContributorLocator;
import com.tibco.xpd.rasc.core.RascController;
import com.tibco.xpd.rasc.core.RascWriter;
import com.tibco.xpd.rasc.core.exception.RascContributionException;
import com.tibco.xpd.rasc.core.exception.RascGenerationException;
import com.tibco.xpd.rasc.core.exception.RascInternalException;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.util.ProjectUtil2;

/**
 * Implements RascController to co-ordinate the generation of deployment RASCs
 * for given projects.
 *
 * @author pwatson
 * @since 1 Mar 2019
 */
public class RascControllerImpl implements RascController {
    private static final String PROGRESS_TASK =
            Messages.RascControllerImpl_ProgressTask;

    private static final String LOG_GENERATION_STARTED =
            "Generating RASC started."; //$NON-NLS-1$

    private static final String LOG_GENERATION_COMPLETE =
            "Generating RASC complete."; //$NON-NLS-1$

    private static final String LOG_CALLING_CONTRIBUTOR =
            "Calling RascContributor: %1$s."; //$NON-NLS-1$

    /**
     * Used to look-up the implementations of the RascContributor interface.
     */
    private final static RascContributorLocator contributorlocator =
            new RascContributorPluginsLocator();

    /**
     * A version range to use when no version range is provided.
     */
    private static final VersionRange NULL_RANGE =
            new VersionRange(new Version(0, 0, 0, null));

    /**
     * Used to generate RASC readers and writers.
     */
    private final DeploymentFactory deploymentFactory;

    /**
     * Constructs the RascController.
     */
    public RascControllerImpl() {
        deploymentFactory = new DeploymentFactoryImpl();
    }

    /**
     * @see com.tibco.xpd.rasc.core.RascController#hasContributionsFor(org.eclipse.core.resources.IProject)
     */
    @Override
    public boolean hasContributionsFor(IProject aProject) {
        List<RascContributor> contributors =
                contributorlocator.getContributors();
        if (contributors != null) {
            for (RascContributor contributor : contributors) {
                if (contributor.hasContributionsFor(aProject)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.rasc.core.RascController#generateRasc(org.eclipse.core.resources.IProject,
     *      org.eclipse.core.resources.IFile,
     *      org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    public void generateRasc(IProject aProject, IFile aFile,
            IProgressMonitor aProgressMonitor) throws RascGenerationException {
        try {
            String location = aFile.getLocation().toOSString();
            generateRasc(aProject, new File(location), aProgressMonitor);
        } finally {
            try {
                // refresh the generated file
                aFile.refreshLocal(IResource.DEPTH_ZERO, aProgressMonitor);
            } catch (CoreException ignore) {
            }
        }
    }

    /**
     * @see com.tibco.xpd.rasc.core.RascController#generateRasc(org.eclipse.core.resources.IProject,
     *      java.io.File, org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    public void generateRasc(IProject aProject, File aFile,
            IProgressMonitor aProgressMonitor) throws RascGenerationException {
        try {
            // make sure the parent folder exists
            File parent = aFile.getParentFile();
            if ((parent != null) && (!parent.exists())) {
                parent.mkdirs();
            }

            try (OutputStream output = new FileOutputStream(aFile)) {
                // generate RASC and write it to the file
                generateRasc(aProject, output, aProgressMonitor);
            }
        } catch (IOException e) {
            throw new RascInternalException(e.getMessage(), e);
        }
    }

    /**
     * @see com.tibco.xpd.rasc.core.RascController#generateRasc(org.eclipse.core.resources.IProject,
     *      java.io.OutputStream, org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    public void generateRasc(IProject aProject, OutputStream aOutput,
            IProgressMonitor aProgressMonitor) throws RascGenerationException {
        try {
            /*
             * Only interested in contributors that have something to do for
             * this project.
             */
            List<RascContributor> allContributors =
                    contributorlocator.getContributors();
            List<RascContributor> contributors = new ArrayList<>();

            // filter those contributors with any work to do
            for (RascContributor contributor : allContributors) {
                if (contributor.hasContributionsFor(aProject)) {
                    contributors.add(contributor);
                }
            }

            // if there are no contributions to make
            if (contributors.isEmpty()) {
                return;
            }

            int workSize = contributors.size();

            SubMonitor monitor = SubMonitor.convert(aProgressMonitor,
                    RascControllerImpl.PROGRESS_TASK,
                    workSize);
            monitor.subTask(RascControllerImpl.PROGRESS_TASK);

            try {
                // has the job been cancelled by the user
                if (monitor.isCanceled()) {
                    return;
                }

                Logger logger = RascActivator.getDefault().getLogger();
                logger.debug(RascControllerImpl.LOG_GENERATION_STARTED);

                try {
                    // create a new deployment
                    final DeploymentWriter deployment = deploymentFactory
                            .forDeployment(NullIdGenerator.INSTANCE, aOutput);

                    // create a facade over the DeploymentWriter
                    RascWriter writer = new RascWriter() {
                        @Override
                        public OutputStream addContent(String aName,
                                String aArtifactName, String aInternalName,
                                MicroService[] aMicroServices)
                                throws RuntimeApplicationException,
                                IOException {
                            return deployment.addContent(aName,
                                    null,
                                    aArtifactName,
                                    aInternalName,
                                    aMicroServices);
                        }

                        @Override
                        public void setManifestAttribute(String aAttrName,
                                PropertyValue[] aValues) {
                            deployment.setProperties(aAttrName, aValues);
                        }
                    };

                    // has the job been cancelled by the user
                    if (monitor.isCanceled()) {
                        return;
                    }

                    AppSummary appSummary = new AppSummary(aProject);

                    // call each contributor in the given order
                    for (RascContributor contributor : contributors) {
                        try {
                            logger.debug(String.format(
                                    RascControllerImpl.LOG_CALLING_CONTRIBUTOR,
                                    contributor.getClass().getName()));

                            contributor.process(aProject,
                                    appSummary,
                                    monitor.split(1),
                                    writer);
                        } catch (OperationCanceledException e) {
                            // job has been cancelled by the user
                            return;
                        } catch (Exception e) {
                            throw new RascContributionException(contributor, e);
                        }
                    }

                    // write manifest and close stream
                    setManifest(deployment, appSummary);
                    deployment.close();

                    logger.debug(RascControllerImpl.LOG_GENERATION_COMPLETE);
                } catch (RuntimeApplicationException e) {
                    throw new RascInternalException(e.getMessage(), e);
                }
            } finally {
                monitor.subTask(""); //$NON-NLS-1$
                monitor.done();
            }
        } catch (IOException e) {
            throw new RascInternalException(e.getMessage(), e);
        }
    }

    /**
     * Record the application properties within the given deployment manifest.
     * 
     * @param aManifest
     *            the manifest to be updated.
     * @param aAppSummary
     *            the summary of the application whose properties are to be
     *            copied.
     * @throws RascGenerationException
     *             if the project cannot be introspected for some reason.
     */
    private void setManifest(DeploymentWriter aManifest, AppSummary aAppSummary)
            throws RascGenerationException {
        try {
            aManifest.setApplicationName(aAppSummary.getName());
            aManifest.setApplicationInternalName(aAppSummary.getInternalName());
            aManifest.setAppVersion(aAppSummary.getVersion());
            aManifest.setGovernanceState(GovernanceState.DRAFT);

            for (IProject dependency : aAppSummary.getReferencedProjects()) {
                AppSummary summary = new AppSummary(dependency);

                aManifest.addDependency(summary.getInternalName(),
                        summary.getDependencyRange());
            }
        } catch (CoreException e) {
            throw new RascInternalException(e.getMessage(), e);
        }
    }

    /**
     * A simple data class that retrieves the summary application information
     * from a given IProject reference.
     */
    private static class AppSummary implements RascContext {
        private final IProject project;

        private final ProjectDetails details;

        private final Version version;

        private final VersionRange dependencyRange;

        private IProject[] referencedProjects = null;

        public AppSummary(IProject aProject) {
            ProjectConfig projectConfig =
                    XpdResourcesPlugin.getDefault().getProjectConfig(aProject);

            project = aProject;
            details = projectConfig.getProjectDetails();

            // get the actual application version
            Version appVersion = (details.getVersion() == null) ? null
                    : new Version(details.getVersion());

            if (appVersion != null) {
                // set micro-version to 0 and the qualifier to current timestamp
                version = new Version(appVersion.getMajor(),
                        appVersion.getMinor(), 0,
                        ProjectUtil2.getAutogeneratedQualifier());
            } else {
                version = null;
            }

            // calculate a version range based on the application's version
            dependencyRange = (appVersion == null)
                    ? RascControllerImpl.NULL_RANGE
                    : new VersionRange(Endpoint.INCLUSIVE,
                            new Version(appVersion.getMajor(),
                                    appVersion.getMinor(),
                                    appVersion.getMicro(), null),
                            new Version(appVersion.getMajor() + 1, 0, 0, null),
                            Endpoint.EXCLUSIVE);
        }

        /**
         * Returns the name of the application. This is it's user friendly name.
         */
        public String getName() {
            return project.getName();
        }

        /**
         * Returns the internal, namespaced name of the application. This is
         * used to uniquely identify the application. Together With the version
         * number, this will identify a version of the same application.
         */
        public String getInternalName() {
            return details.getId();
        }

        /**
         * @see com.tibco.xpd.rasc.core.RascContext#getVersion()
         */
        @Override
        public Version getVersion() {
            return version;
        }

        /**
         * Returns the range that should be applied to dependencies on this
         * application. This is used when iterating the applications on which
         * the deployed application depends.
         * 
         * @return the application dependency range
         */
        public VersionRange getDependencyRange() {
            return dependencyRange;
        }

        /**
         * Returns the projects referenced by the deployed application. This
         * includes both the static and dynamic references. The returned
         * projects need not exist in the workspace. The result will not contain
         * duplicates. Returns an empty array if there are no referenced
         * projects.
         * 
         * @return this projects referenced by the deployed application.
         * @throws CoreException
         */
        public IProject[] getReferencedProjects() throws CoreException {
            if (referencedProjects == null) {
                referencedProjects = project.getReferencedProjects();
            }
            return referencedProjects;
        }
    }
}
