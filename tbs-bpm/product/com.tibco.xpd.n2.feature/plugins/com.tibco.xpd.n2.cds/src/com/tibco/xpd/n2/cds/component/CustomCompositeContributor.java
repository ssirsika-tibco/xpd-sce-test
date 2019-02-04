/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.cds.component;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;

import com.tibco.amf.sca.model.composite.Composite;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2ExtensionHelper;
import com.tibco.xpd.bom.gen.internal.BOMGeneratorHelper;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.daa.CompositeContributor;
import com.tibco.xpd.daa.ICustomCompositeContributor;
import com.tibco.xpd.daa.internal.IChangeRecorder;
import com.tibco.xpd.daa.internal.preferences.DAAGenPreferences;
import com.tibco.xpd.daa.internal.util.DAANamingUtils;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.n2.cds.CDSActivator;
import com.tibco.xpd.n2.cds.customfeature.CustomFeatureEnum;
import com.tibco.xpd.n2.cds.customfeature.CustomFeatureManager;
import com.tibco.xpd.n2.cds.internal.Messages;
import com.tibco.xpd.n2.cds.utils.CDSCustomFeatureUtils;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;

/**
 * Declares 2 composite contributors corresponding to the 2 bom features now
 * required: model; DA. Both descend from common base class for custom composite
 * contributors where the bulk of the code previously in CDSCompositeContributor
 * has been migrated to.
 * 
 * @author patkinso
 * @since 18 Jan 2013
 */
public class CustomCompositeContributor extends CompositeContributor
        implements ICustomCompositeContributor {

    /**
     * @see com.tibco.xpd.daa.ICustomCompositeContributor#getCustomFeatureIds(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    public Collection<String> getCustomFeatureIds(IProject project) {

        return CDSCustomFeatureUtils.getCustomFeatureIds(project);
    }

    protected static Logger LOG = CDSActivator.getDefault().getLogger();

    /**
     * Pattern to parse plug-in jar file name. It uses greedy quantifier to make
     * sure last '_' is caught.
     */

    /*
     * Sid ACE-122 no need to add script descritptors to DAA any more because
     * there are no BOM-specific JS classes any more.
     * 
     * Removed generateScriptDescriptors() function
     */

    /**
     * Replaces qualifier for all plug-ins projects participating in the BPM
     * project's custom feature.
     * 
     * @param project
     *            the BPM project.
     * @param timeStamp
     *            timeStamp to replace.
     * @param changeRecorder
     *            records version changes.
     */
    private void replacePluginsQualifierWithTS(IProject project,
            CustomFeatureEnum customFeatureEnum, String timeStamp,
            IChangeRecorder changeRecorder) {

        switch (customFeatureEnum) {
        case CDS: {
            /*
             * replacing all plug-in version qualifier with TS for user
             * generated & wsdl generated BOM(s)
             */
            replaceCDSBundlesQualifierWithTS(project,
                    timeStamp,
                    changeRecorder);

            /*
             * XPD-7262 - Forms feature is returning to use of BOM JS custom
             * feature plugin strategy (business data project handling). Removed
             * our (defunct) call to replace version qualifiers in JS plugins.
             */

            replaceJavaServicePluginQualifierWithTS(project,
                    timeStamp,
                    changeRecorder);
            break;
        }
        case SI: {
            /* replacing all SI plug-in version qualifier with TS */
            replaceSIBundlesQualifierWithTS(project, timeStamp, changeRecorder);
            break;
        }
        case DA: {
            /* replacing all DA plug-in version qualifier with TS */
            replaceDABundlesQualifierWithTS(project, timeStamp, changeRecorder);
            break;
        }
        }

    }

    /**
     * @see com.tibco.xpd.daa.CompositeContributor#prepareProject(org.eclipse.core.resources.IProject,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param procOrGlobalBOMProject
     * @param monitor
     * @return
     */
    @Override
    public IStatus prepareProject(IProject procOrGlobalBOMProject,
            IProgressMonitor monitor) {

        IStatus status = CDSCustomFeatureUtils
                .runOnDemandBdsGenerators(procOrGlobalBOMProject, monitor);
        if (Status.OK != status.getSeverity()) {

            return status;
        }
        return super.prepareProject(procOrGlobalBOMProject, monitor);
    }

    /**
     * @see com.tibco.xpd.daa.CompositeContributor#validate(org.eclipse.core.resources.IProject,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param project
     * @param monitor
     * @return
     */
    @Override
    public IStatus validate(IProject project, IProgressMonitor monitor) {

        try {
            if (project != null) {

                /*
                 * get all BOM resources for specified project
                 */
                Set<IFile> bomResources = getAllBomFiles(project);
                monitor.beginTask(
                        Messages.CDSCompositeContributor_CheckingSourceBoms_message,
                        bomResources.size() + 1);

                /*
                 * By default we have all generator ids - which is used when a
                 * business data project has case classes in its bom(s)
                 */
                Set<String> allGeneratorIDs =
                        CustomFeatureEnum.getAllGeneratorIDs();
                if (BOMUtils.isBusinessDataProject(project)) {
                    /*
                     * It is a global data bom project but does not have any
                     * case classes in it, then we need only bds generator id
                     */
                    if (!BOMGlobalDataUtils.hasCaseDataInProject(project)) {

                        allGeneratorIDs = Collections.<String> singleton(
                                CDSCustomFeatureUtils.BOM_CDS_GENERATOR_ID);
                    }
                } else {
                    /*
                     * It is not a global data bom project, then we need only
                     * bds generator id
                     */
                    allGeneratorIDs = Collections.<String> singleton(
                            CDSCustomFeatureUtils.BOM_CDS_GENERATOR_ID);
                }

                BOMGenerator2ExtensionHelper genHelper =
                        new BOMGenerator2ExtensionHelper(allGeneratorIDs);
                /*
                 * For each BOM resource check that we have the generated
                 * projects for it - one for each contribution
                 */
                if (genHelper.getExtensions().length > 0) {

                    for (IFile res : bomResources) {

                        if (monitor.isCanceled()) {
                            return Status.CANCEL_STATUS;
                        }

                        for (IProject proj : genHelper
                                .getGeneratedProjects(res)) {

                            if (!proj.exists()) {

                                LOG.info("Expected project " + proj.getName() //$NON-NLS-1$
                                        + " not found in the workspace"); //$NON-NLS-1$
                                return new Status(IStatus.ERROR,
                                        CDSActivator.PLUGIN_ID,
                                        String.format(
                                                Messages.CDSCompositeContributor_BDSProjectsMissing_error_message_2,
                                                proj.getName()));
                            }
                        }
                        monitor.worked(1);
                    }

                }

            }

            return super.validate(project, new SubProgressMonitor(monitor, 1));

        } catch (CoreException e) {
            return e.getStatus();
        } finally {
            monitor.done();
        }

    }

    /**
     * @param project
     * @return
     * @throws CoreException
     */
    private Set<IFile> getAllBomFiles(IProject project) throws CoreException {
        List<IFile> allBOMFilesForProject =
                BOMGeneratorHelper.getAllBOMFilesForProject(project);
        Set<IFile> bomResources =
                new LinkedHashSet<IFile>(allBOMFilesForProject);
        return bomResources;
    }

    /**
     * @param project
     * @param projectType
     * @param timeStamp
     * @param changeRecorder
     */
    private static void replaceBundlesQualifierWithTS(IProject project,
            String projectType, String timeStamp,
            IChangeRecorder changeRecorder) {

        Map<String, String> generatedProjectsFromBOM = CDSCustomFeatureUtils
                .getGeneratedProjectsFromBOM(project, projectType);

        if (generatedProjectsFromBOM.size() > 0) {

            Set<String> generatedPluginIds = generatedProjectsFromBOM.keySet();

            for (String generatedPluginId : generatedPluginIds) {

                IProject emfProject = DAANamingUtils.getProjectWithName(
                        DAANamingUtils.getGeneratedEMFPlugProjectName(
                                generatedPluginId));

                if (emfProject == null || !emfProject.isAccessible()) {
                    continue;
                }

                /*
                 * for a generated bds plugin project get the source project to
                 * check if it is business data project. for business data
                 * project we need the build version time stamp
                 */
                IProject srcBOMProject = getSourceBOMProject(emfProject);

                if (null != srcBOMProject
                        && BOMUtils.isBusinessDataProject(srcBOMProject)) {

                    String buildVersionTimeStamp =
                            BOMUtils.getBuildVersionTimeStamp(srcBOMProject);
                    PluginManifestHelper.replaceBundleManifestQualifierWithTS(
                            emfProject,
                            buildVersionTimeStamp,
                            Boolean.TRUE,
                            changeRecorder);
                } else {

                    PluginManifestHelper.replaceBundleManifestQualifierWithTS(
                            emfProject,
                            timeStamp,
                            Boolean.TRUE,
                            changeRecorder);
                }
            }
        }
    }

    /**
     * Get the source XPD project (if there is one) for the given emf project
     * that gets generated for each bom package
     * 
     * @param emfProject
     * @return The project with the given project id or <code>null</code> if not
     *         found.
     */
    private static IProject getSourceBOMProject(IProject emfProject) {

        try {
            if (emfProject.getName().endsWith(".bds")) { //$NON-NLS-1$

                String persistentProperty = emfProject
                        .getPersistentProperty(CustomFeatureManager.SRC_FILE);
                IFile srcFile = ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(persistentProperty));
                IProject srcBOMProject = srcFile.getProject();
                return srcBOMProject;
            }

        } catch (CoreException e) {

            LOG.error(e);
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.daa.CompositeContributor#contributeToComposite(org.eclipse.core.resources.IProject,
     *      org.eclipse.core.resources.IContainer,
     *      com.tibco.amf.sca.model.composite.Composite,
     *      org.eclipse.emf.common.util.URI, java.lang.String, boolean,
     *      com.tibco.xpd.daa.internal.IChangeRecorder,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param project
     * @param stagingArea
     * @param composite
     * @param compositeLocation
     * @param timeStamp
     * @param replaceWithTS
     * @param changeRecorder
     * @param monitor
     * @return
     */
    @Override
    public IStatus contributeToComposite(IProject project,
            IContainer stagingArea, Composite composite, URI compositeLocation,
            String timeStamp, boolean replaceWithTS,
            IChangeRecorder changeRecorder, IProgressMonitor monitor) {

        long beforeTimeMillis = System.currentTimeMillis();

        monitor.beginTask(
                Messages.CDSCompositeContributor_AddingRuntimeBundles_message,
                100);

        if (stagingArea instanceof IFolder) {
            IFolder stagingFolder = (IFolder) stagingArea;

            /*
             * Sid ACE-122 no need to add script descritptors to DAA any more
             * because there are no BOM-specific JS classes any more.
             * 
             * Removed generateScriptDescriptors() function
             */

            try {
                if (DAAGenPreferences.shouldCacheBomJars()) {

                    /*
                     * When a project is pre compiled bom jars are cached under
                     * .precompiled folder and they will be picked up from there
                     * for staging
                     */
                    boolean isPrecompiled =
                            ProjectUtil.isPrecompiledProject(project);

                    Map<IProject, String> pluginProjects =
                            CustomFeatureManager.getAllPluginProjects(project,
                                    new SubProgressMonitor(monitor, 1));

                    if (monitor.isCanceled()) {

                        return Status.CANCEL_STATUS;
                    }
                    /*
                     * if the project is not pre compiled then build the jars
                     * into cache and use them from the cache for staging
                     */
                    if (!isPrecompiled) {

                        /*
                         * Cached jars - custom feature is pre-built (and will
                         * use cached jars if possible) and staged in the
                         * staging folder. Then {@link
                         * CustomFeaturePackagerParticipant} will be used to
                         * package this pre-built feature.
                         */

                        if (!pluginProjects.isEmpty()) {

                            /*
                             * determine whether any of the required plug-in
                             * projects in the cache are out-of-date.
                             */
                            if (!CustomFeatureManager
                                    .getProjectsToBuild(project, pluginProjects)
                                    .isEmpty()) {

                                CustomFeatureManager.buildProjectsIntoCache(
                                        monitor,
                                        project,
                                        stagingFolder,
                                        pluginProjects);
                            }

                        }
                    }
                    IFolder stagingCustFeatureFolder =
                            CustomFeatureManager.stageCustomFeatureJars(project,
                                    pluginProjects,
                                    stagingFolder,
                                    timeStamp,
                                    SubProgressMonitorEx
                                            .createMainProgressMonitor(monitor,
                                                    90));

                    // features
                    // (P/{bom-proj}/.bpm/customfeatures/features)
                    CustomFeatureManager.generateCustomFeaturesWhenJarsCached(
                            project,
                            timeStamp,
                            stagingCustFeatureFolder);

                    if (monitor.isCanceled()) {
                        return Status.CANCEL_STATUS;
                    }
                    monitor.worked(10);

                    replacePluginsQualifierWithTS(project,
                            timeStamp,
                            changeRecorder);
                } else {

                    /*
                     * Traditional way - custom feature is created and passed to
                     * DAA packager which then uses PDE export to create jars
                     * and package custom feature.
                     */
                    replacePluginsQualifierWithTS(project,
                            timeStamp,
                            changeRecorder);

                    if (monitor.isCanceled()) {
                        return Status.CANCEL_STATUS;
                    }
                    monitor.worked(50);

                    CustomFeatureManager.generateCustomFeaturesWhenJarsCacheOff(
                            project,
                            timeStamp,
                            stagingFolder);

                    monitor.worked(50);
                }
            } catch (IOException e) {
                LOG.error(e);
                return new Status(IStatus.ERROR, CDSActivator.PLUGIN_ID,
                        Messages.CDSCompositeContributor_BDSIOException_message);
            } catch (CoreException e) {
                LOG.getPluginLogger().log(e.getStatus());
                return e.getStatus();
            }

            if (LOG.isDebugEnabled()) {
                long afterTimeMillis = System.currentTimeMillis();
                long timeTaken = afterTimeMillis - beforeTimeMillis;
                String fmtStr =
                        "The time taken to create a BDS custom feature %d milliseconds"; //$NON-NLS-1$
                LOG.debug(String.format(fmtStr, timeTaken));
            }
        }

        return Status.OK_STATUS;
    }

    /**
     * @param project
     * @param timeStamp
     * @param changeRecorder
     */
    private void replacePluginsQualifierWithTS(IProject project,
            String timeStamp, IChangeRecorder changeRecorder) {

        for (CustomFeatureEnum cfEnum : CustomFeatureEnum.values()) {

            replacePluginsQualifierWithTS(project,
                    cfEnum,
                    timeStamp,
                    changeRecorder);
        }
    }

    /**
     * 
     * @param project
     * @param timeStamp
     * @param changeRecorder
     */
    private void replaceCDSBundlesQualifierWithTS(IProject project,
            String timeStamp, IChangeRecorder changeRecorder) {

        replaceBundlesQualifierWithTS(project,
                CDSCustomFeatureUtils.BOM_CDS_GENERATOR_ID,
                timeStamp,
                changeRecorder);
    }

    /**
     * @param project
     * @param timeStamp
     * @param changeRecorder
     */
    private void replaceSIBundlesQualifierWithTS(IProject project,
            String timeStamp, IChangeRecorder changeRecorder) {

        replaceBundlesQualifierWithTS(project,
                CDSCustomFeatureUtils.BOM_CDS_SI_GENERATOR_ID,
                timeStamp,
                changeRecorder);
    }

    /**
     * 
     * @param project
     * @param timeStamp
     * @param changeRecorder
     */
    private void replaceJavaServicePluginQualifierWithTS(IProject project,
            String timeStamp, IChangeRecorder changeRecorder) {

        Set<IProject> javaServicePlugins = CDSCustomFeatureUtils
                .getAllReferencedJavaServicePlugins(project);
        for (IProject eachReferencedProject : javaServicePlugins) {
            PluginManifestHelper.replaceBundleManifestQualifierWithTS(
                    eachReferencedProject,
                    timeStamp,
                    Boolean.TRUE,
                    changeRecorder);
        }
    }

    /**
     * 
     * @param project
     * @param timeStamp
     * @param changeRecorder
     */
    private void replaceDABundlesQualifierWithTS(IProject project,
            String timeStamp, IChangeRecorder changeRecorder) {

        replaceBundlesQualifierWithTS(project,
                CDSCustomFeatureUtils.BOM_CDS_DA_GENERATOR_ID,
                timeStamp,
                changeRecorder);
    }

}