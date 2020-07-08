/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.core.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
import com.tibco.xpd.rasc.core.RascAppSummary;
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
import com.tibco.xpd.resources.util.GovernanceStateService;
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
     * The ID of the model asset type for Rest Serice projects.
     */
    private static final String REST_ASSET_TYPE = "com.tibco.xpd.rest.asset"; //$NON-NLS-1$

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
     * Service for checking the project governance state.
     */
    private GovernanceStateService gss;

    /**
     * Constructs the RascController.
     */
    public RascControllerImpl() {
        deploymentFactory = new DeploymentFactoryImpl();
        gss = new GovernanceStateService();
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

                /*
                 * Sid ACE-3886 Don't set the output file to derived otherwise it won't get included in project exports.
                 */
                // aFile.setDerived(true, aProgressMonitor);
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
                            /*
                             * Sid ACE-4134 when contributor writes properties then preserve existing properties.
                             */
                            try {
                                PropertyValue[] currentValues = deployment.getProperties(aAttrName);

                                if (currentValues != null && currentValues.length > 0) {
                                    PropertyValue[] mergedValues =
                                            new PropertyValue[currentValues.length + aValues.length];

                                    System.arraycopy(currentValues, 0, mergedValues, 0, currentValues.length);
                                    System.arraycopy(aValues,
                                            0,
                                            mergedValues,
                                            currentValues.length,
                                            mergedValues.length);

                                    aValues = mergedValues;
                                }

                                deployment.setProperties(aAttrName, aValues);

                            } catch (RuntimeApplicationException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    };

                    // has the job been cancelled by the user
                    if (monitor.isCanceled()) {
                        return;
                    }

                    RascContext context = new RascContextImpl(aProject);

                    // call each contributor in the given order
                    for (RascContributor contributor : contributors) {
                        try {
                            logger.debug(String.format(
                                    RascControllerImpl.LOG_CALLING_CONTRIBUTOR,
                                    contributor.getClass().getName()));

                            contributor.process(aProject,
                                    context,
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
                    setManifest(aProject, deployment, context.getAppSummary());
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
     * @param aProject
     *            the project for the deployment.
     * @param aManifest
     *            the manifest to be updated.
     * @param aAppSummary
     *            the summary of the application whose properties are to be
     *            copied.
     * @throws RascGenerationException
     *             if the project cannot be introspected for some reason.
     */
    private void setManifest(IProject aProject, DeploymentWriter aManifest,
            RascAppSummary aAppSummary) throws RascGenerationException {
        try {
            aManifest.setApplicationName(aAppSummary.getName());
            aManifest.setApplicationInternalName(aAppSummary.getInternalName());
            aManifest.setAppVersion(aAppSummary.getVersion());
            if (gss.isLockedForProduction(aProject)) {
                aManifest.setGovernanceState(GovernanceState.PUBLISHED);
            } else {
                aManifest.setGovernanceState(GovernanceState.DRAFT);
            }

            for (RascAppSummary dependency : aAppSummary
                    .getReferencedProjects()) {

                // ignore REST service projects
                if (dependency
                        .hasAssetType(RascControllerImpl.REST_ASSET_TYPE)) {
                    continue;
                }

                aManifest.addDependency(dependency.getInternalName(),
                        dependency.getDependencyRange());
            }
        } catch (CoreException e) {
            throw new RascInternalException(e.getMessage(), e);
        }
    }

    /**
     * An implementation of the RascContext that is based on a given IProject
     * reference.
     */
    private static class RascContextImpl implements RascContext {
        private final RascAppSummary summary;

        public RascContextImpl(IProject aProject) {
            summary = new AppSummary(aProject);
        }

        /**
         * @see com.tibco.xpd.rasc.core.RascContext#getAppSummary()
         */
        @Override
        public RascAppSummary getAppSummary() {
            return summary;
        }

        /**
         * @see com.tibco.xpd.rasc.core.RascContext#getVersion()
         */
        @Override
        public Version getVersion() {
            return summary.getVersion();
        }
    }

    /**
     * Implements RascAppSummary to provide the summary application information
     * from a given IProject reference.
     */
    private static class AppSummary implements RascAppSummary {
        private final IProject project;

        private final ProjectConfig projectConfig;

        private final ProjectDetails details;

        private final Version version;

        private final VersionRange dependencyRange;

        private Collection<RascAppSummary> referencedProjects = null;

        public AppSummary(IProject aProject) {
            projectConfig =
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

                // calculate a version range based on the application's version
                dependencyRange = new VersionRange(Endpoint.INCLUSIVE,
                        new Version(appVersion.getMajor(),
                                appVersion.getMinor(), appVersion.getMicro(),
                                null),
                        new Version(appVersion.getMajor() + 1, 0, 0, null),
                        Endpoint.EXCLUSIVE);
            } else {
                version = null;
                dependencyRange = RascControllerImpl.NULL_RANGE;
            }
        }

        /**
         * @see com.tibco.xpd.rasc.core.RascContext#getName()
         */
        @Override
        public String getName() {
            return project.getName();
        }

        /**
         * @see com.tibco.xpd.rasc.core.RascContext#getInternalName()
         */
        @Override
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
         * @see com.tibco.xpd.rasc.core.RascDependency#getDependencyRange()
         */
        @Override
        public VersionRange getDependencyRange() {
            return dependencyRange;
        }

        /**
         * @see com.tibco.xpd.rasc.core.RascContext#hasAssetType(java.lang.String)
         */
        @Override
        public boolean hasAssetType(String aAssetTypeId) {
            if (projectConfig != null) {
                return projectConfig.hasAssetType(aAssetTypeId);
            }

            return false;
        }

        /**
         * @see com.tibco.xpd.rasc.core.RascContext#getReferencedProjects()
         */
        @Override
        public Collection<RascAppSummary> getReferencedProjects()
                throws CoreException {
            if (referencedProjects == null) {
                referencedProjects = new ArrayList<>();
                for (IProject reference : project.getReferencedProjects()) {
                    referencedProjects.add(new AppSummary(reference));
                }
            }
            return referencedProjects;
        }

        /**
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return getInternalName().hashCode();
        }

        /**
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (!(obj instanceof RascAppSummary)) {
                return false;
            }

            RascAppSummary other = (RascAppSummary) obj;
            return Objects.equals(getInternalName(), other.getInternalName());
        }
    }
}
