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
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.daa.internal.util.CustomFeatureUtils;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.n2.cds.utils.CDSCustomFeatureUtils;

/**
 * Feature manager implementation to generate bds jars for all projects with
 * boms in it
 * 
 * @author patkinso
 * @since 18 Sep 2013
 */
class CDSFeatureManagerImpl extends CustomFeatureManager {

    private static class CDSFeatureManagerHolder {
        static final CDSFeatureManagerImpl instance =
                new CDSFeatureManagerImpl();
    }

    /**
     * 
     */
    private CDSFeatureManagerImpl() {

        super(CustomFeatureEnum.CDS);
    }

    /**
     * @param featureEnum
     */
    // public CDSFeatureManagerImpl(CustomFeatureEnum featureEnum) {
    //
    // super(featureEnum);
    // }

    /**
     * @return
     */
    public static CDSFeatureManagerImpl getInstance() {
        return CDSFeatureManagerHolder.instance;
    }

    /**
     * @param project
     * @param timeStamp
     * @param customFeatureEnum
     * @return
     */
    @Override
    protected List<IncludedPlugin> generateIncludedPlugnList(IProject project,
            String timeStamp) {

        List<IncludedPlugin> generatedPlugins = new ArrayList<IncludedPlugin>();
        Map<String, String> generatedEMFProjectIds =
                new HashMap<String, String>();
        /*
         * to package bds jars
         */
        Set<IResource> allBomResources =
                CDSCustomFeatureUtils.getAllBOMResources(project);
        Set<IResource> bomResources = new HashSet<IResource>();
        /* get non business data bom resources */
        for (IResource bomResource : allBomResources) {

            if (!BOMUtils.isBusinessDataProject(bomResource.getProject())) {

                bomResources.add(bomResource);
            }
        }

        if (bomResources.size() > 0) {

            Map<String, String> generatedProjectIds =
                    CustomFeatureUtils
                            .getGeneratedEMFProjectIdsWithVersion(bomResources,
                                    CDSCustomFeatureUtils.BOM_CDS_GENERATOR_ID);
            generatedEMFProjectIds.putAll(generatedProjectIds);

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

        /*
         * get all Plugin Projects which are being referred from Java Service
         * Task
         */
        Set<IProject> referencedJavaServiceProjects =
                CDSCustomFeatureUtils
                        .getAllReferencedJavaServicePlugins(project);
        for (IProject eachReferencedProject : referencedJavaServiceProjects) {

            CDSCustomFeatureUtils
                    .addIncludedPluginFromPluginProjectDetails(eachReferencedProject,
                            generatedPlugins,
                            timeStamp);
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

        Set<String> generatorIDs = getCustomFeatureEnum().getGeneratorIds();
        genHelper = new BOMGenerator2ExtensionHelper(generatorIDs);
        /*
         * get bds plugin projects for non business data projects (for business
         * data projects si manager will generate the required plugin projects)
         */

        Set<IResource> allBOMResources =
                CDSCustomFeatureUtils.getAllBOMResources(project);

        for (IResource bomRes : allBOMResources) {

            if (!BOMUtils.isBusinessDataProject(bomRes.getProject())) {

                getGenPluginProjects(pluginProjects,
                        genHelper,
                        bomRes,
                        new SubProgressMonitor(progressMonitor, 1));
                if (progressMonitor.isCanceled()) {

                    /*
                     * Return the plugin projects map (that could be empty or
                     * might have incomplete result) as the caller is ignoring
                     * the return value when progress monitor is cancelled
                     */
                    return pluginProjects;
                }
            }
        }

        addJavaServicePluginProjects(project, pluginProjects);

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
        return true;
    }

}
