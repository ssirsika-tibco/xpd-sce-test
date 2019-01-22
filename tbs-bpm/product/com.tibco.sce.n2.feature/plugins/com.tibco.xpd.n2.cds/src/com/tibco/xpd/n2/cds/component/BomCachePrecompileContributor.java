/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.n2.cds.component;

import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.tibco.xpd.n2.cds.CDSActivator;
import com.tibco.xpd.n2.cds.customfeature.CustomFeatureManager;
import com.tibco.xpd.n2.cds.internal.Messages;
import com.tibco.xpd.n2.cds.utils.CDSCustomFeatureUtils;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.precompile.AbstractPreCompileContributor;
import com.tibco.xpd.resources.precompile.PreCompileContributorManager;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Implementation class that contributes cached bom jars to the pre compiled
 * folder when a project is flagged as a pre compiled project.
 * 
 * @author bharge
 * @since 27 Oct 2014
 */
public class BomCachePrecompileContributor extends
        AbstractPreCompileContributor {

    Logger logger = CDSActivator.getDefault().getLogger();

    /**
     * @see com.tibco.xpd.n2.resources.ui.PreCompileContributor#prepareForPrecompile(org.eclipse.core.resources.IProject,
     *      org.eclipse.core.resources.IFolder,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param project
     * @param preCompileFolder
     * @param progressMonitor
     * @return
     */
    @Override
    public IStatus precompile(IProject project, IFolder preCompileFolder,
            IProgressMonitor progressMonitor) {

        try {

            /* Pass new sub-progress monitors down to called methods */
            progressMonitor.beginTask("", 2); //$NON-NLS-1$
            progressMonitor
                    .subTask(Messages.BomCachePrecompileContributor_generating_bds_bundles_monitor_message);

            IFolder precompiledBomCacheJarFolder =
                    preCompileFolder
                            .getFolder(N2PENamingUtils.PRECOMPILE_BOM_JARS_CACHE_FOLDER);

            if (!precompiledBomCacheJarFolder.exists()) {

                ProjectUtil.createFolder(precompiledBomCacheJarFolder,
                        false,
                        new NullProgressMonitor());
            }

            IStatus status =
                    CDSCustomFeatureUtils.runOnDemandBdsGenerators(project,
                            new SubProgressMonitor(progressMonitor, 1));
            if (Status.OK != status.getSeverity()) {

                status =
                        new Status(
                                Status.ERROR,
                                CDSActivator.PLUGIN_ID,
                                Messages.BomCachePrecompileContributor_generating_bds_bundles_monitor_message);
                return status;
            }

            if (progressMonitor.isCanceled()) {

                status =
                        new Status(
                                Status.ERROR,
                                CDSActivator.PLUGIN_ID,
                                Messages.BomCachePrecompileContributor_generating_bds_bundles_monitor_message);
                return status;
            }

            /* Set subtask "Caching runtime BOM bundles" */
            progressMonitor
                    .subTask(Messages.BomCachePrecompileContributor_caching_bds_bundles_monitor_message);

            Map<IProject, String> pluginProjects =
                    CustomFeatureManager.getAllPluginProjects(project,
                            new SubProgressMonitor(progressMonitor, 1));

            if (!pluginProjects.isEmpty()) {

                /*
                 * determine whether any of the required plug-in projects in the
                 * cache are out-of-date.
                 */
                Map<IProject, String> projectsToBuild =
                        CustomFeatureManager.getProjectsToBuild(project,
                                pluginProjects);

                if (!projectsToBuild.isEmpty()) {
                    progressMonitor
                            .subTask(Messages.BomCachePrecompileContributor_compiling_bds_bundles_monitor_message);

                    CustomFeatureManager
                            .buildProjectsIntoCache(new SubProgressMonitor(
                                    progressMonitor, 1),
                                    project,
                                    preCompileFolder
                                            .getFolder(N2PENamingUtils.PRECOMPILE_BOM_JARS_CACHE_FOLDER),
                                    pluginProjects);
                }
            }

            if (progressMonitor.isCanceled()) {

                status =
                        new Status(
                                Status.ERROR,
                                CDSActivator.PLUGIN_ID,
                                Messages.BomCachePrecompileContributor_caching_bds_bundles_monitor_message);
                return status;
            }

        } catch (CoreException e) {

            Status status =
                    new Status(Status.ERROR, CDSActivator.PLUGIN_ID,
                            e.getMessage());
            return status;
        } finally {

            progressMonitor.done();
        }

        return Status.OK_STATUS;
    }

    /**
     * @see com.tibco.xpd.resources.precompile.AbstractEnablePreCompileContributor#getSourceResource(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    public IResource getSourceResource(IProject project) {

        IFolder bomJarsFolder =
                project.getFolder(N2PENamingUtils.BOM_JARS_CACHE_FOLDER);
        return bomJarsFolder;
    }

    /**
     * @see com.tibco.xpd.resources.precompile.AbstractEnablePreCompileContributor#getPrecompileTarget(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    public IPath getPrecompileTarget(IProject project) {

        IFolder precompileFolder =
                project.getFolder(PreCompileContributorManager.getInstance().PRECOMPILE_OUTPUTFOLDER_NAME);

        IFolder bomJarFolder =
                precompileFolder
                        .getFolder(N2PENamingUtils.PRECOMPILE_BOM_JARS_CACHE_FOLDER);

        return bomJarFolder.getProjectRelativePath();

    }

}
