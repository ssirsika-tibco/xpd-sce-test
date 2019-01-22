/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.cds.customfeature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.tibco.amf.model.productfeature.IncludedPlugin;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2ExtensionHelper;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.daa.internal.util.CustomFeatureUtils;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.n2.cds.utils.CDSCustomFeatureUtils;
import com.tibco.xpd.n2.cds.utils.CDSUtils;

/**
 * Feature manager implementation to generate si plugins. SI plugins will be
 * generated for business data project(s) having boms with case classes in it
 * 
 * @author bharge
 * @since 20 Nov 2013
 */
public class SIFeatureManagerImpl extends CustomFeatureManager {

    private static SIFeatureManagerImpl instance = new SIFeatureManagerImpl();

    /**
     * @param customFeatureEnum
     */
    private SIFeatureManagerImpl() {

        super(CustomFeatureEnum.SI);
    }

    /**
     * @return the instance
     */
    public static SIFeatureManagerImpl getInstance() {

        return instance;
    }

    /**
     * @see com.tibco.xpd.n2.cds.customfeature.CustomFeatureManager#generateIncludedPlugnList(org.eclipse.core.resources.IProject,
     *      java.lang.String)
     * 
     * @param project
     * @param timeStamp
     * @return
     */
    @Override
    protected List<IncludedPlugin> generateIncludedPlugnList(IProject project,
            String timeStamp) {

        List<IncludedPlugin> generatedPlugins = new ArrayList<IncludedPlugin>();
        Map<String, String> generatedEMFProjectIds =
                new HashMap<String, String>();

        boolean isBusinessDataProject = BOMUtils.isBusinessDataProject(project);
        boolean hasCaseDataInProject =
                BOMGlobalDataUtils.hasCaseDataInProject(project);
        List<IResource> bomResources = CDSUtils.getBomResources(project);
        Set<IResource> bomResourceSet = new HashSet<IResource>(bomResources);

        /* get all generated BDS projects for the Project */
        Map<String, String> generatedBDSProjectIds =
                CustomFeatureUtils
                        .getGeneratedEMFProjectIdsWithVersion(bomResourceSet,
                                CDSCustomFeatureUtils.BOM_CDS_GENERATOR_ID);
        generatedEMFProjectIds.putAll(generatedBDSProjectIds);

        /*
         * get generated SI projects if there are any case classes in the
         * business data project
         */
        if (isBusinessDataProject && hasCaseDataInProject) {

            Set<IResource> caseBOMs = new HashSet<IResource>();

            for (IResource bomResource : bomResources) {

                if (BOMGlobalDataUtils.hasGlobalCaseClass(bomResource)) {
                    caseBOMs.add(bomResource);
                }
            }

            if (caseBOMs.size() > 0) {

                /* get all generated BDS SI projects for the Project */
                Map<String, String> generatedSIProjectIds =
                        CustomFeatureUtils
                                .getGeneratedEMFProjectIdsWithVersion(caseBOMs,
                                        CDSCustomFeatureUtils.BOM_CDS_SI_GENERATOR_ID);
                generatedEMFProjectIds.putAll(generatedSIProjectIds);
            }
        }

        Set<Entry<String, String>> entrySet = generatedEMFProjectIds.entrySet();

        for (Entry<String, String> eachEntry : entrySet) {
            String pluginId = eachEntry.getKey();
            String pluginVersion = eachEntry.getValue();
            String updatedPluginVersion =
                    PluginManifestHelper.getUpdatedBundleVersion(pluginVersion,
                            timeStamp);
            IncludedPlugin rb =
                    CustomFeatureUtils.createIncludedPlugin(pluginId,
                            updatedPluginVersion);
            generatedPlugins.add(rb);
        }

        return generatedPlugins;

    }

    /**
     * @see com.tibco.xpd.n2.cds.customfeature.CustomFeatureManager#getPluginProjects(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     * @throws CoreException
     */
    @Override
    protected Map<IProject, String> getPluginProjects(IProject project,
            IProgressMonitor progressMonitor) throws CoreException {

        Map<IProject, String> pluginProjects =
                new LinkedHashMap<IProject, String>();
        BOMGenerator2ExtensionHelper genHelper = null;

        boolean isBusinessDataProject = BOMUtils.isBusinessDataProject(project);
        boolean hasCaseDataInProject =
                BOMGlobalDataUtils.hasCaseDataInProject(project);

        Set<String> generatorIDs = new HashSet<String>();
        generatorIDs.add(CDSCustomFeatureUtils.BOM_CDS_GENERATOR_ID);
        /*
         * generate si projects only if the business data projects has case
         * classes in it. otherwise we generate only bds projects
         */
        if (isBusinessDataProject && hasCaseDataInProject) {

            generatorIDs.addAll(getCustomFeatureEnum().getGeneratorIds());
        }

        genHelper = new BOMGenerator2ExtensionHelper(generatorIDs);
        /*
         * get all the boms for the given project and get generated plugin
         * projects
         */
        for (IResource bomRes : CDSUtils.getBomResources(project)) {

            /*
             * Don't want to check this condition because we may have Generated
             * BOMs in Business Data Projects which do not have the global data
             * profile
             */
            // if (BOMGlobalDataUtils.isGlobalDataBOM(bomRes)) {

            getGenPluginProjects(pluginProjects,
                    genHelper,
                    bomRes,
                    new SubProgressMonitor(progressMonitor, 1));
            if (progressMonitor.isCanceled()) {

                /*
                 * Return the plugin projects map (that could be empty or might
                 * have incomplete result) as the caller is ignoring the return
                 * value when progress monitor is cancelled
                 */
                return pluginProjects;
            }
            // }
        }

        return pluginProjects;
    }

    /**
     * @see com.tibco.xpd.n2.cds.customfeature.CustomFeatureManager#shouldGenerateFeature(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    protected boolean shouldGenerateFeature(IProject project) {

        if (BOMUtils.isBusinessDataProject(project)) {

            return true;
        }

        return false;
    }
}
