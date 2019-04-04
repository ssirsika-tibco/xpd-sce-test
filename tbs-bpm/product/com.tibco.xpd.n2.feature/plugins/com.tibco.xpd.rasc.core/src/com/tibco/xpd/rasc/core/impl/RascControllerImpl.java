/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.core.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;

import com.tibco.bpm.dt.rasc.DeploymentFactory;
import com.tibco.bpm.dt.rasc.DeploymentWriter;
import com.tibco.bpm.dt.rasc.Version;
import com.tibco.bpm.dt.rasc.VersionRange;
import com.tibco.bpm.dt.rasc.VersionRange.Endpoint;
import com.tibco.bpm.dt.rasc.exception.RuntimeApplicationException;
import com.tibco.bpm.dt.rasc.impl.DeploymentFactoryImpl;
import com.tibco.xpd.rasc.core.Messages;
import com.tibco.xpd.rasc.core.RascActivator;
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
    private static final String PROGRESS_TASK = Messages.RascControllerImpl_ProgressTask;

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
        File rasc = generate(aProject, aProgressMonitor);
        if (rasc != null) {
            try {
                copy(rasc, aFile, aProgressMonitor);
            } catch (IOException e) {
                throw new RascInternalException(e.getMessage(), e);
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
        File rasc = generate(aProject, aProgressMonitor);
        if (rasc != null) {
            try {
                copy(rasc, aFile, aProgressMonitor);
            } catch (IOException e) {
                throw new RascInternalException(e.getMessage(), e);
            }
        }
    }

    private File generate(IProject aProject, IProgressMonitor aProgressMonitor)
            throws RascGenerationException {
        try {
            /*
             * Only interested in contributors that have something to do for
             * this project.
             */
            List<RascContributor> allContributors =
                    contributorlocator.getContributors();
            List<RascContributor> contributors = new ArrayList<>();
            
            for (RascContributor contributor : allContributors) {
                if (contributor.hasContributionsFor(aProject)) {
                    contributors.add(contributor);
                }
            }
            
            int workSize = contributors.size();
            
            SubMonitor monitor = SubMonitor.convert(aProgressMonitor,
                    RascControllerImpl.PROGRESS_TASK,
                    workSize);
            try {
                // has the job been cancelled by the user
                if (monitor.isCanceled()) {
                    return null;
                }

                Logger logger = RascActivator.getDefault().getLogger();
                logger.debug(RascControllerImpl.LOG_GENERATION_STARTED);

                File result = File.createTempFile("sce", "rasc"); //$NON-NLS-1$ //$NON-NLS-2$
                OutputStream output = new FileOutputStream(result);
                try {
                    // create a new deployment
                    final DeploymentWriter deployment = deploymentFactory
                            .forDeployment(NullIdGenerator.INSTANCE, output);

                    // create a facade over the DeploymentWriter
                    RascWriter writer = (aName, aArtifactName, aInternalName,
                            aMicroServices) -> deployment.addContent(aName,
                                    null,
                                    aArtifactName,
                                    aInternalName,
                                    aMicroServices);

                    // has the job been cancelled by the user
                    if (monitor.isCanceled()) {
                        return null;
                    }

                    // call each contributor in the given order
                    for (RascContributor contributor : contributors) {
                        try {
                            logger.debug(String.format(
                                    RascControllerImpl.LOG_CALLING_CONTRIBUTOR,
                                    contributor.getClass().getName()));

                            contributor.process(aProject,
                                    monitor.split(1),
                                    writer);

                        } catch (OperationCanceledException e) {
                            // job has been cancelled by the user
                            return null;
                        } catch (Exception e) {
                            throw new RascContributionException(contributor, e);
                        }
                    }

                    // write manifest and close stream
                    setManifest(deployment, aProject);
                    deployment.close();

                    logger.debug(RascControllerImpl.LOG_GENERATION_COMPLETE);
                } catch (RuntimeApplicationException e) {
                    throw new RascInternalException(e.getMessage(), e);
                } finally {
                    output.close();
                }

                return result;
            } finally {
                monitor.done();
            }
        } catch (IOException e) {
            throw new RascInternalException(e.getMessage(), e);
        }
    }

    /**
     * Record the project properties within the given deployment manifest.
     * 
     * @param aManifest
     *            the manifest to be updated.
     * @param aProject
     *            the project whose properties are to be copied.
     * @throws RascGenerationException
     *             if the project cannot be introspected for some reason.
     */
    private void setManifest(DeploymentWriter aManifest, IProject aProject)
            throws RascGenerationException {
        try {
            AppSummary appSummary = new AppSummary(aProject);

            aManifest.setApplicationName(appSummary.getName());
            aManifest.setApplicationInternalName(appSummary.getInternalName());

            // set the qualifier of the application version to current timestamp
            Version version = appSummary.getVersion();
            version = new Version(version.getMajor(), version.getMinor(),
                    version.getMicro(),
                    ProjectUtil2.getAutogeneratedQualifier());
            aManifest.setAppVersion(version);

            for (IProject dependency : aProject.getReferencedProjects()) {
                AppSummary summary = new AppSummary(dependency);

                // calculate a version range based on the application's version
                version = summary.getVersion();
                VersionRange range = (version == null)
                        ? RascControllerImpl.NULL_RANGE
                        : new VersionRange(Endpoint.INCLUSIVE, version,
                                new Version(version.getMajor() + 1, 0, 0, null),
                                Endpoint.EXCLUSIVE);

                aManifest.addDependency(summary.getInternalName(), range);
            }
        } catch (CoreException e) {
            throw new RascInternalException(e.getMessage(), e);
        }
    }

    /**
     * Copies the given File to the given IFile.
     * 
     * @param aSource
     *            the source file to be copied.
     * @param aDest
     *            the destination to which the source is to be copied.
     * @param aProgressMonitor
     *            a progress monitor, or null if progress reporting is not
     *            desired.
     * @throws IOException
     *             if an error occurs during the copy.
     */
    private void copy(File aSource, IFile aDest,
            IProgressMonitor aProgressMonitor) throws IOException {
        InputStream input =
                new BufferedInputStream(new FileInputStream(aSource));
        try {
            if (!aDest.exists()) {
                aDest.create(input, true, aProgressMonitor);
            } else {
                aDest.setContents(input, true, false, aProgressMonitor);
            }
        } catch (CoreException e) {
            throw new IOException(e);
        } finally {
            input.close();
        }
    }

    /**
     * Copies the given source File to the given destination File.
     * 
     * @param aSource
     *            the source file to be copied.
     * @param aDest
     *            the destination to which the source is to be copied.
     * @param aProgressMonitor
     *            a progress monitor, or null if progress reporting is not
     *            desired.
     * @throws IOException
     *             if an error occurs during the copy.
     */
    private void copy(File aSource, File aDest,
            IProgressMonitor aProgressMonitor) throws IOException {
        InputStream input =
                new BufferedInputStream(new FileInputStream(aSource));
        try {
            OutputStream output =
                    new BufferedOutputStream(new FileOutputStream(aDest));
            try {
                byte[] buffer = new byte[8192];
                int len;
                while ((len = input.read(buffer, 0, buffer.length)) != -1) {
                    output.write(buffer, 0, len);
                }
            } finally {
                output.close();
            }
        } finally {
            input.close();
        }
    }

    /**
     * A simple data class that retrieves the summary application information
     * from a given IProject reference.
     */
    private static class AppSummary {
        private IProject project;

        private ProjectDetails details;

        public AppSummary(IProject aProject) {
            ProjectConfig projectConfig =
                    XpdResourcesPlugin.getDefault().getProjectConfig(aProject);

            project = aProject;
            details = projectConfig.getProjectDetails();
        }

        /**
         * Returns the name of the application. This is it's user friendly name.
         */
        public String getName() {
            return project.getName();
        }

        /**
         * Returns the application's lifecycle version.
         * 
         * @return
         */
        public Version getVersion() {
            String version = details.getVersion();
            return (version == null) ? null : new Version(version);
        }

        /**
         * Returns the internal, namespaced name of the application. This is
         * used to uniquely identify the application. Together With the version
         * number, this will identify a version of the same application.
         */
        public String getInternalName() {
            return details.getId();
        }
    }
}
