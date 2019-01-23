/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.cds.customfeature;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.pde.core.IModel;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.eclipse.pde.internal.core.PDECore;
import org.eclipse.pde.internal.core.exports.FeatureExportInfo;
import org.osgi.framework.Version;

import com.tibco.amf.model.productfeature.Dependencies;
import com.tibco.amf.model.productfeature.Dependency;
import com.tibco.amf.model.productfeature.Feature;
import com.tibco.amf.model.productfeature.IncludedPlugin;
import com.tibco.amf.model.productfeature.ProductFeatureFactory;
import com.tibco.amf.model.productfeature.util.ProductFeatureResourceFactoryImpl;
import com.tibco.xpd.bom.gen.BOMGenActivator;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2ExtensionHelper;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.daa.internal.IChangeRecorder;
import com.tibco.xpd.daa.internal.util.CustomFeatureUtils;
import com.tibco.xpd.daa.internal.util.DAANamingUtils;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper.PluginProjectDetails;
import com.tibco.xpd.n2.cds.CDSActivator;
import com.tibco.xpd.n2.cds.internal.Messages;
import com.tibco.xpd.n2.cds.internal.customfeature.CustomFeatureExportJob;
import com.tibco.xpd.n2.cds.utils.CDSCustomFeatureUtils;
import com.tibco.xpd.n2.cds.utils.CDSCustomFeatureUtils.ResType;
import com.tibco.xpd.n2.cds.utils.CDSUtils;
import com.tibco.xpd.n2.daa.Activator;
import com.tibco.xpd.n2.daa.internal.packager.CustomFeaturePackagerParticipant;
import com.tibco.xpd.n2.daa.internal.packager.DAAExportUtils;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Responsible for generating custom features for bds project(s). Also
 * sub-classed to support generation of particular features.
 * 
 * @author patkinso
 * @since 18 Sep 2013
 */
public abstract class CustomFeatureManager {

    /**
     * Pattern to parse plug-in jar file name. It uses greedy quantifier to make
     * sure last '_' is caught.
     */
    public static final Pattern PLUGIN_JAR_PATTERN = Pattern
            .compile("^(.*)(_)(.*)(\\.[jJ][aA][rR])$"); //$NON-NLS-1$

    protected static Logger LOG = CDSActivator.getDefault().getLogger();

    private static final String FEATURE_FILENAME = "feature.xml"; //$NON-NLS-1$

    /**
     * Name of a temporary folder in DAA export staging area (.bpm). Destination
     * of PDE plug-ins export.
     */
    private static final String TMP_FEATURE_BUILD_FOLDER = "tmpBuildFolder"; //$NON-NLS-1$

    public static final QualifiedName SRC_FILE = new QualifiedName(
            BOMGenActivator.PLUGIN_ID, "srcFile"); //$NON-NLS-1$

    private static final QualifiedName SRC_JAVA_SERVICE_PROJECT =
            new QualifiedName(Activator.PLUGIN_ID, "srcJavaServiceProject"); //$NON-NLS-1$

    private static final QualifiedName MODEL_CHECKSUM = new QualifiedName(
            BOMGenActivator.PLUGIN_ID, "modelChecksum"); //$NON-NLS-1$

    private static final QualifiedName PROJECT_CHECKSUM = new QualifiedName(
            Activator.PLUGIN_ID, "projectChecksum"); //$NON-NLS-1$

    protected final CustomFeatureEnum customFeatureEnum;

    protected final String featureSuffix;

    /**
     * @param customFeatureEnum
     */
    protected CustomFeatureManager(CustomFeatureEnum customFeatureEnum) {
        super();
        this.customFeatureEnum = customFeatureEnum;
        featureSuffix = customFeatureEnum.getFeatureSuffix();
    }

    /**
     * @return the customFeatureEnum
     */
    protected CustomFeatureEnum getCustomFeatureEnum() {
        return customFeatureEnum;
    }

    /**
     * @param bpmStagingFolder
     * @return
     */
    private static IFolder getCustomFeaturesFolder(IFolder bpmStagingFolder) {
        String customFeaturesFolderName =
                N2PENamingUtils.CUSTOMFEATURES_FOLDER_NAME;
        return bpmStagingFolder.getFolder(customFeaturesFolderName);
    }

    /**
     * @param custFeaturesFolder
     * @return
     */
    public static IFolder getCustomFeaturesPluginsFolder(
            IFolder custFeaturesFolder) {
        return custFeaturesFolder.getFolder(N2PENamingUtils.PLUGINS_FOLDER);
    }

    /**
     * @param project
     * @param updateFeatureVersion
     * @param timeStamp
     * @param featureFolder
     */
    private void generateCustomFeature(IProject project,
            boolean updateFeatureVersion, String timeStamp, IFile featureFile)
            throws IOException, CoreException {

        Feature customFeature = ProductFeatureFactory.eINSTANCE.createFeature();
        customFeature.setId(N2PENamingUtils.getCustomFeatureId(project,
                featureSuffix));

        String customFeatureVersion = CDSUtils.getCustomFeatureVersion(project);
        // String updatedFeatureVersion =
        // PluginManifestHelper
        // .getUpdatedBundleVersion(customFeatureVersion,
        // timeStamp);
        // NOTE: if I use updateFeatureVersion then Packager Service cannot find
        // the feature
        if (updateFeatureVersion) {
            customFeatureVersion =
                    PluginManifestHelper
                            .getUpdatedBundleVersion(customFeatureVersion,
                                    timeStamp);
        }
        customFeature.setVersion(customFeatureVersion);
        customFeature.setLabel(N2PENamingUtils.getCustomFeatureId(project,
                featureSuffix));

        // adding EMF feature dependency
        Dependencies dependencies =
                ProductFeatureFactory.eINSTANCE.createDependencies();
        EList<Dependency> imports = dependencies.getImports();
        Dependency emfDependency =
                CDSCustomFeatureUtils.getEMFFeatureDependency();
        imports.add(emfDependency);
        customFeature.setRequire(dependencies);

        /*
         * XPD-6591: process project must have a feature dependency on global
         * bom project(.model.bds project)
         */
        CDSCustomFeatureUtils
                .addCustomFeatureDependencyOnGlobalBom(dependencies, project);

        List<IncludedPlugin> generatedPlugins =
                generateIncludedPlugnList(project, timeStamp);
        /* Add feature imports */
        CustomFeatureUtils.addIncludedPlugins(customFeature, generatedPlugins);

        if (generatedPlugins.isEmpty() && dependencies.getImports().size() == 1) {
            // NO BOMs in the Project & no referenced Projects has BOMs so no
            // need to generate a Custom feature file
            return;
        }
        ResourceSet rset = new ResourceSetImpl();
        rset.getResourceFactoryRegistry().getExtensionToFactoryMap()
                .put("xml", new ProductFeatureResourceFactoryImpl()); //$NON-NLS-1$
        String customFeaturePath = featureFile.getFullPath().toPortableString();

        Resource customFeatureResource =
                rset.createResource(URI
                        .createPlatformResourceURI(customFeaturePath, false));
        customFeatureResource.getContents().add(customFeature);
        ByteArrayOutputStream os = null;
        ByteArrayInputStream byteAIS = null;
        try {
            os = new ByteArrayOutputStream();
            customFeatureResource.save(os, null);
            if (featureFile.isAccessible()) {
                // delete the existing file
                featureFile.delete(true, new NullProgressMonitor());
            }
            byteAIS = new ByteArrayInputStream(os.toByteArray());
            featureFile.create(byteAIS, true, new NullProgressMonitor());
            // to force Feature indexer
            WorkingCopy workingCopy =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(featureFile);
            IndexerServiceImpl indexerService =
                    (IndexerServiceImpl) XpdResourcesPlugin.getDefault()
                            .getIndexerService();
            indexerService.index(workingCopy);
        } finally {
            if (byteAIS != null) {
                byteAIS.close();
            }
            if (os != null) {
                os.close();
            }
        }

    }

    /**
     * @return
     * @throws CoreException
     */
    private IFolder establishFeatureFolder(IProject project,
            IFolder custFeaturesFolder) throws CoreException {

        String customFeatureId =
                N2PENamingUtils.getCustomFeatureId(project, featureSuffix);
        IPath path =
                new Path(N2PENamingUtils.FEATURES_FOLDER)
                        .append(customFeatureId);

        IFolder featuresFolder = custFeaturesFolder.getFolder(path);

        ProjectUtil.createFolder(featuresFolder,
                true,
                new NullProgressMonitor());

        return featuresFolder;
    }

    abstract List<IncludedPlugin> generateIncludedPlugnList(IProject project,
            String timeStamp);

    /**
     * @param project
     * @param pluginProjects
     * @param stageFolder
     *            Folder for staging plugin jars. This needs to have been
     *            created before passing in handle
     * @param timeStamp
     * @throws CoreException
     */
    @SuppressWarnings("restriction")
    private static void stageJarsFromCache(IProject project,
            Map<IProject, String> pluginProjects, IFolder custFeaturesFolder,
            String timeStamp) throws CoreException {

        // Copy relevant cached jars over to plug-ins folder of staging
        // area
        IFolder pluginsFolder =
                CustomFeatureManager
                        .getCustomFeaturesPluginsFolder(custFeaturesFolder);

        ProjectUtil.createFolder(pluginsFolder, /* derived */
                true, new NullProgressMonitor());

        try {
            for (IProject p : pluginProjects.keySet()) {
                String pluginId = pluginProjects.get(p);
                IResource cachedResource =
                        findResourceByPluginIdAndVersion(DAAExportUtils
                                .getCreateBOMJarsCacheFolder(project, true),
                                pluginId,
                                p,
                                IResource.FILE);
                /*
                 * JA: For now we only cover JAR case (unpacked plug-ins are not
                 * supported).
                 */
                if (cachedResource instanceof IFile) {
                    /*
                     * Optionally we could check if it is up to date, but can't
                     * do that because of Java Service features.
                     */

                    String dstFileName =
                            deriveDestPluginFileName(cachedResource.getName(),
                                    timeStamp);

                    IFile dstFile = pluginsFolder.getFile(dstFileName);
                    /*
                     * this replaces qualifier in the plug-ins in the bom jar
                     * cache (source) and then copies them to custom feature
                     * location (destination)
                     */
                    PluginManifestHelper.replaceQualifierInPluginJar(new File(
                            cachedResource.getLocation().toPortableString()),
                            new File(dstFile.getLocation().toPortableString()),
                            timeStamp,
                            false);

                } else {
                    String msg =
                            String.format(Messages.CDSCompositeContributor_JarNotFound_message,
                                    cachedResource.getFullPath()
                                            .toPortableString());
                    throw new CoreException(new Status(IStatus.ERROR,
                            CDSActivator.PLUGIN_ID, msg));
                }
            }
        } catch (IOException e) {
            String msg =
                    String.format(Messages.CDSCompositeContributor_JarGatherFailure_message);
            throw new CoreException(new Status(IStatus.ERROR,
                    CDSActivator.PLUGIN_ID, msg, e));
        } finally {
            if (custFeaturesFolder.exists()) {
                custFeaturesFolder.refreshLocal(IResource.DEPTH_INFINITE,
                        new NullProgressMonitor());
            }
        }
    }

    /**
     * Finds a built resource (jar file) in a folder by provided plug-in id and
     * plug-in version (taken from a pluginProject).
     * 
     * @param folder
     *            folder to search.
     * @param pluginId
     *            identifier of a plug-in
     * @param pluginProject
     *            pluginProject it is used to retrieve version of the plug-in.
     * @param resourceType
     *            type of resource to find (only {@link IResource#FILE} or
     *            {@link IResource#FOLDER} are allowed.
     * @return first built resource (jar file or folder) for a provided plug-in
     *         id or 'null' if not found.
     * @throws CoreException
     */
    private static IResource findResourceByPluginIdAndVersion(IFolder folder,
            String pluginId, IProject pluginProject, int resourceType)
            throws CoreException {

        IPluginModelBase pluginModel = PluginRegistry.findModel(pluginProject);
        if (pluginModel != null) {
            String pluginVersion = pluginModel.getPluginBase().getVersion();
            if (folder.isAccessible()) {
                for (IResource r : folder.members()) {
                    if (r instanceof IFile) {
                        Matcher matcher =
                                PLUGIN_JAR_PATTERN.matcher(r.getName());
                        if (matcher.matches()
                                && pluginId.equals(matcher.group(1))) {
                            Version rv = new Version(matcher.group(3));
                            Version pv = new Version(pluginVersion);
                            /*
                             * Because plug-in version is not replaced in the
                             * bds plug-in in this case, we can compare the
                             * whole version (including qualifier).
                             */
                            if (rv.equals(pv)) {
                                return r;
                            }
                        }
                    } else if (r instanceof IFolder) {
                        // Folders are not supported yet.
                    }
                }
            }
            return null;
        }

        return null;
    }

    /**
     * Replaces existing plugin qualifier with timestamp.
     * 
     * @param sourceFilename
     * @param timestamp
     * @return filename with supplied timestamp used as the version qualifier
     */
    private static String deriveDestPluginFileName(String sourceFilename,
            String timestamp) {

        Matcher m =
                CustomFeatureManager.PLUGIN_JAR_PATTERN.matcher(sourceFilename);
        if (m.matches() && m.groupCount() == 4) {
            try {
                Version v = Version.parseVersion(m.group(3));
                if ("qualifier".equals(v.getQualifier())) { //$NON-NLS-1$
                    v =
                            new Version(v.getMajor(), v.getMinor(),
                                    v.getMicro(), timestamp);
                }
                StringBuilder sb =
                        new StringBuilder().append(m.group(1))
                                .append(m.group(2)).append(v.toString())
                                .append(m.group(4));

                return sb.toString();
            } catch (IllegalArgumentException e) {
                LOG.error(e);
            }
        }

        String fmtErr =
                "Plugin filename %s was not updated with timestamp due to unexpected format of input string"; //$NON-NLS-1$
        LOG.error(String.format(fmtErr, sourceFilename));

        return sourceFilename;
    }

    protected abstract Map<IProject, String> getPluginProjects(
            IProject project, IProgressMonitor progressMonitor)
            throws CoreException;

    /**
     * Determine the plug-in projects required for the project's feature
     * 
     * @param project
     * @param progressMonitor
     * @return
     * @throws CoreException
     */
    public static Map<IProject, String> getAllPluginProjects(IProject project,
            IProgressMonitor progressMonitor) throws CoreException {

        /*
         * XPD-5462: when a process project bom class has an attribute that
         * refers to a case/global class from global data bom project, daa
         * export job fails. the failure is because the export job cannot find
         * the generated plugin projects (.si.bds and .da.bds) for the dependent
         * global data boms. so trying to resolve this issue by getting the
         * referenced global data bom projects and get all the three generated
         * si.bds and da.bds and .bds plugin projects.
         */
        Set<IProject> globalBOMRefProjects = new HashSet<IProject>();

        Set<IResource> bomDependencyList =
                CDSCustomFeatureUtils.getAllBOMResources(project);

        /*
         * Get the referenced boms and its project to check if it is a business
         * data project
         */
        for (IResource bomResource : bomDependencyList) {

            IProject bomProj = bomResource.getProject();
            if (BOMUtils.isBusinessDataProject(bomProj)) {

                if (bomProj != project) {

                    globalBOMRefProjects.add(bomProj);
                }
            }
        }

        /* get the generated plugins for this project */
        Map<IProject, String> pluginProjects =
                new LinkedHashMap<IProject, String>();

        for (CustomFeatureEnum customFeatureEnum : CustomFeatureEnum.values()) {

            CustomFeatureManager manager = customFeatureEnum.getManager();
            if (manager.shouldGenerateFeature(project)) {

                Map<IProject, String> featureSpecPluginProjects =
                        manager.getPluginProjects(project,
                                new SubProgressMonitor(progressMonitor, 1));

                if (progressMonitor.isCanceled()) {

                    /*
                     * Return empty hash map as the caller is ignoring the
                     * return value when progress monitor is cancelled
                     */
                    return new LinkedHashMap<IProject, String>();
                }
                if (null != featureSpecPluginProjects) {

                    pluginProjects.putAll(featureSpecPluginProjects);
                }
            }
        }

        /*
         * checking plugin projects is not empty to ensure that this project has
         * boms in it and plugins have been generated for them before we get the
         * generated plugins for referenced global bom projects
         */
        if (!pluginProjects.isEmpty() && !globalBOMRefProjects.isEmpty()) {

            for (IProject bomProject : globalBOMRefProjects) {

                for (CustomFeatureEnum customFeatureEnum : CustomFeatureEnum
                        .values()) {

                    CustomFeatureManager manager =
                            customFeatureEnum.getManager();
                    Map<IProject, String> featureSpecPluginProjects =
                            manager.getPluginProjects(bomProject,
                                    new SubProgressMonitor(progressMonitor, 1));
                    if (progressMonitor.isCanceled()) {

                        /*
                         * Return empty hash map as the caller is ignoring the
                         * return value when progress monitor is cancelled
                         */
                        return new LinkedHashMap<IProject, String>();
                    }
                    if (null != featureSpecPluginProjects) {

                        pluginProjects.putAll(featureSpecPluginProjects);
                    }
                }
            }
        }

        return pluginProjects;
    }

    /**
     * gets the generated plugin projects for a given bom resource
     * 
     * @param pluginProjects
     * @param genHelper
     * @param bomRes
     * @throws CoreException
     */
    protected void getGenPluginProjects(Map<IProject, String> pluginProjects,
            BOMGenerator2ExtensionHelper genHelper, IResource bomRes,
            IProgressMonitor progressMonitor) throws CoreException {

        try {

            progressMonitor.beginTask("", 2);
            for (IProject p : genHelper.getGeneratedProjects((IFile) bomRes)) {

                if (p.exists()) {

                    progressMonitor
                            .subTask(String
                                    .format(Messages.CustomFeatureManager_generated_java_models_monitor_message,
                                            p.getName()));
                    /* confirm this project represents a plugin project */
                    IPluginModelBase model = PluginRegistry.findModel(p);

                    /*
                     * While doing pre-compile projects we need to get the
                     * plugin projects with the models updated by
                     * PluginModelManager. But that job is not guaranteed to
                     * finish when we try to access those projects. So we wait
                     * until the model is updated.
                     */
                    while (model == null && !progressMonitor.isCanceled()) {
                        try {
                            /* Wait until the model is updated in the registry */
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                        }

                        model = PluginRegistry.findModel(p);
                    }
                    progressMonitor.worked(1);
                    if (progressMonitor.isCanceled()) {

                        String msg =
                                String.format(Messages.CustomFeatureManager_user_cancelled_java_model_gen_monitor_message,
                                        p.getName(),
                                        bomRes.getProject().getName());
                        CDSActivator.getDefault().getLogger().info(msg);
                        return;
                    } else if (model == null) {

                        String msg =
                                String.format(Messages.CustomFeatureManager_java_model_not_found_monitor_message,
                                        p.getName());
                        CDSActivator.getDefault().getLogger().error(msg);

                        throw new CoreException(new Status(IStatus.ERROR,
                                CDSActivator.PLUGIN_ID, msg));
                    }

                    String pluginId = model.getBundleDescription().getName();
                    pluginProjects.put(p, pluginId);

                } else {

                    String msg =
                            String.format(Messages.CDSCompositeContributor_ProjectNotFound_message,
                                    p.getName());
                    throw new CoreException(new Status(IStatus.ERROR,
                            CDSActivator.PLUGIN_ID, msg));
                }
            }
        } finally {

            progressMonitor.done();
        }
    }

    /**
     * @see com.tibco.xpd.n2.cds.component.CustomCompositeContributor#addJavaServicePluginProjects(org.eclipse.core.resources.IProject,
     *      java.util.Map)
     * 
     * @param project
     * @param pluginProjects
     */
    @SuppressWarnings("restriction")
    protected void addJavaServicePluginProjects(IProject project,
            Map<IProject, String> pluginProjects) {

        Set<IProject> referencedJavaServiceProjects =
                CDSCustomFeatureUtils
                        .getAllReferencedJavaServicePlugins(project);
        Set<PluginProjectDetails> flatList =
                new HashSet<PluginProjectDetails>();
        for (IProject eachJavaProject : referencedJavaServiceProjects) {
            PluginProjectDetails pluginProjectDetails =
                    PluginManifestHelper
                            .getPluginProjectDetails(eachJavaProject);
            PluginManifestHelper
                    .flattenProjectDetailsList(pluginProjectDetails, flatList);
        }
        IWorkspaceRoot r = ResourcesPlugin.getWorkspace().getRoot();
        for (PluginProjectDetails pluginProjDetails : flatList) {
            String pluginId = pluginProjDetails.getBundleId();
            IProject p = r.getProject(pluginProjDetails.getProjectName());
            pluginProjects.put(p, pluginId);
        }
    }

    /**
     * Gets a map (pluginProject, pluginId) of plug-in projects that are in the
     * .bscache for PDE export. Looks at checksum of cached jar and that of it's
     * derived-from bom to see if it's out of date and needs rebuilding.
     * 
     * @param project
     * 
     * @param pluginProjects
     * @return
     * @throws CoreException
     */
    public static Map<IProject, String> getProjectsToBuild(IProject project,
            Map<IProject, String> pluginProjects) throws CoreException {

        if (pluginProjects.isEmpty()) {
            return Collections.<IProject, String> emptyMap();
        } else {
            IFolder jarCacheFolder =
                    DAAExportUtils.getCreateBOMJarsCacheFolder(project, true);
            Map<IProject, String> projectsToBuild =
                    new LinkedHashMap<IProject, String>();

            for (IProject p : pluginProjects.keySet()) {

                String pluginId = pluginProjects.get(p);
                IFile cachedJar =
                        (IFile) findResourceByPluginIdAndVersion(jarCacheFolder,
                                pluginId,
                                p,
                                IResource.FILE);

                boolean upToDate = false;
                if (cachedJar != null) {
                    String srcFile = cachedJar.getPersistentProperty(SRC_FILE);
                    String srcJavaServiceProject =
                            cachedJar
                                    .getPersistentProperty(SRC_JAVA_SERVICE_PROJECT);
                    if (srcFile != null) {

                        IResource file =
                                ResourcesPlugin.getWorkspace().getRoot()
                                        .findMember(srcFile);
                        if (null != file && file.isAccessible()) {

                            String modelChecksum =
                                    cachedJar
                                            .getPersistentProperty(MODEL_CHECKSUM);
                            List<IFile> affectedBoms =
                                    getBOMDeepDependencies((IFile) file);
                            String finalChecksum =
                                    DAAExportUtils
                                            .getFilesChecksum(affectedBoms);
                            upToDate =
                                    file instanceof IFile
                                            && file.isAccessible()
                                            && modelChecksum != null
                                            && modelChecksum
                                                    .equals(finalChecksum);
                        }
                    } else if (srcJavaServiceProject != null) {

                        String projChecksum =
                                cachedJar
                                        .getPersistentProperty(PROJECT_CHECKSUM);
                        IResource javaServiceProject =
                                ResourcesPlugin.getWorkspace().getRoot()
                                        .findMember(srcJavaServiceProject);
                        upToDate =
                                javaServiceProject instanceof IProject
                                        && javaServiceProject.isAccessible()
                                        && projChecksum != null
                                        && projChecksum
                                                .equals(DAAExportUtils
                                                        .getChecksum((IProject) javaServiceProject));
                    }
                }

                if (!upToDate) {
                    projectsToBuild.put(p, pluginProjects.get(p));
                }
            }

            return projectsToBuild;
        }
    }

    /**
     * 
     * Gives all sorted deep dependent bom files (including the passed bom).
     * 
     * @param bomFile
     * @return List<IFile>
     */
    private static List<IFile> getBOMDeepDependencies(IFile bomFile) {

        Collection<IResource> affectedResources =
                WorkingCopyUtil.getDeepDependencies(bomFile);
        List<IFile> bomFiles =
                new ArrayList<IFile>(affectedResources.size() + 1);
        affectedResources.add(bomFile);

        for (IResource r : affectedResources) {

            bomFiles.add((IFile) r);
        }

        Collections.sort(bomFiles, new Comparator<IFile>() {

            public int compare(IFile f1, IFile f2) {

                return f1.getName().compareTo(f2.getName());
            }
        });
        return bomFiles;
    }

    /**
     * Updates BOM jars cache.
     * 
     * @param monitor
     * @param project
     * @param tempBuidFolder
     * @param pluginProjects
     * @throws CoreException
     */
    @SuppressWarnings("restriction")
    public static void buildProjectsIntoCache(IProgressMonitor monitor,
            IProject project, IFolder bpmStagingFolder,
            Map<IProject, String> pluginProjects) throws CoreException {

        IFolder tempBuidFolder =
                bpmStagingFolder.getFolder(TMP_FEATURE_BUILD_FOLDER);

        try {
            // i) create the plug-in jars in /.bpm/tmpBuildFolder/ for the
            // relevant extension projects

            // feature info for export
            FeatureExportInfo info = new FeatureExportInfo();
            info.toDirectory = true;
            info.useJarFormat = true;
            info.destinationDirectory =
                    tempBuidFolder.getLocation().toPortableString();
            /*
             * We will always build the whole project set?? Will end up being
             * more specific
             */
            info.items = getModels(pluginProjects.keySet());
            info.qualifier = "qualifier"; //$NON-NLS-1$

            // Plug-in Export Job
            CustomFeatureExportJob exportJob =
                    new CustomFeatureExportJob(info, true);
            exportJob.setUser(true);
            exportJob.schedule();
            try {
                exportJob.join();
            } catch (InterruptedException e) {
                LOG.error(e);
                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                CDSActivator.PLUGIN_ID,
                                Messages.CDSCompositeContributor_PDEExportJobInterrupted_message));
            }

            monitor.worked(80);

            // check if the export was successfully and report problem if
            // not.
            IStatus exportResult = exportJob.getResult();
            String logsFileName = "logs.zip"; //$NON-NLS-1$
            if (exportJob.hasAntErrors()) {
                IPath exportOperationLogsLocation =
                        PDECore.getDefault().getStateLocation()
                                .append(logsFileName);
                /*
                 * Copy the logs.zip file from temp bpm folder to
                 * exportOperationLogsLocation in .metadata (this is required
                 * because the bpm staging folder is cleaned by the DAA
                 * generation operation at the end including the logs.zip file)
                 */
                IFile logsFileSrc = tempBuidFolder.getFile(logsFileName);
                if (logsFileSrc != null) {
                    try {
                        File inputFile =
                                new File(logsFileSrc.getLocation().toString());
                        File outputFile =
                                new File(exportOperationLogsLocation.toString());
                        outputFile.createNewFile();
                        if (inputFile != null && inputFile.exists()) {

                            FileInputStream inStream =
                                    new FileInputStream(inputFile);
                            FileOutputStream outStream =
                                    new FileOutputStream(outputFile);

                            if (inStream != null && outStream != null) {
                                byte[] buffer = new byte[1024];
                                int length;
                                while ((length = inStream.read(buffer)) > 0) {
                                    outStream.write(buffer, 0, length);
                                }
                                inStream.close();
                                outStream.close();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Status status =
                        new Status(
                                Status.ERROR,
                                CDSActivator.PLUGIN_ID,
                                String.format(Messages.CustomFeatureExportJob_ErrorsDuringExport_message,
                                        exportOperationLogsLocation));
                throw new CoreException(status);
            } else if (exportResult != null
                    && exportResult.getSeverity() > IStatus.WARNING) {
                throw new CoreException(exportResult);
            }

            tempBuidFolder.refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());

            // ii) update the cache with these newly generated jars from
            // /.bpm/tmpBuildFolder
            updateCache(project, pluginProjects, tempBuidFolder);
            monitor.worked(10);
        } finally {
            if (!Platform.inDebugMode()) {
                if (tempBuidFolder.isAccessible()) {
                    tempBuidFolder.delete(true, new NullProgressMonitor());
                }
            }
        }
    }

    /**
     * Returns plug-in models for provided plug-in projects.
     * 
     * @throws CoreException
     */
    private static IModel[] getModels(Collection<IProject> pluginProjects)
            throws CoreException {
        List<IModel> pluginModels = new ArrayList<IModel>();
        for (IProject p : pluginProjects) {
            IPluginModelBase model = PluginRegistry.findModel(p);
            if (model != null) {
                pluginModels.add(model);
            } else {
                String msg =
                        String.format(Messages.CDSCompositeContributor_PluginModelNotFound_message,
                                p.getName());
                throw new CoreException(new Status(IStatus.ERROR,
                        CDSActivator.PLUGIN_ID, msg));
            }
        }
        return pluginModels.toArray(new IModel[pluginModels.size()]);
    }

    /**
     * Updates BOM jars cache.
     * 
     * @param project
     * 
     * @param projectsToBuild
     *            the project the cache should be updated.
     * @param tempBuiFolder
     *            temporary PDE export folder.
     */
    private static void updateCache(IProject project,
            Map<IProject, String> projectsToBuild, IFolder tempBuiFolder)
            throws CoreException {

        IFolder pluginsFolder =
                tempBuiFolder.getFolder(N2PENamingUtils.PLUGINS_FOLDER);
        for (IProject p : projectsToBuild.keySet()) {
            if (p.isAccessible()) {
                String pluginId = projectsToBuild.get(p);
                IResource jarFile =
                        first(findResourcesByPluginId(pluginsFolder,
                                pluginId,
                                IResource.FILE));

                if (jarFile != null) {
                    IFolder dstFolder =
                            DAAExportUtils.getCreateBOMJarsCacheFolder(project,
                                    true);
                    /* Remove previous jar(s). */
                    for (IResource oldJar : findResourcesByPluginId(dstFolder,
                            pluginId,
                            IResource.FILE)) {
                        if (oldJar instanceof IFile && oldJar.exists()) {
                            oldJar.delete(true, new NullProgressMonitor());
                        }
                    }

                    /*
                     * Prevent problem with names different only by case. (see:
                     * XPD-4435)
                     */
                    String jarName = jarFile.getName();
                    for (IResource r : dstFolder.members()) {
                        if (jarName.equalsIgnoreCase(r.getName())) {
                            r.delete(true, new NullProgressMonitor());
                        }
                    }
                    IFile dstFile = dstFolder.getFile(jarName);

                    jarFile.copy(dstFile.getFullPath(),
                            true,
                            new NullProgressMonitor());
                    /*
                     * Set BOM source file timeStamp for cached jar. The java
                     * service projects for now will always be rebuilt
                     * (potential room for improvement in future).
                     */
                    String srcFile = p.getPersistentProperty(SRC_FILE);
                    if (srcFile != null) {
                        IResource file =
                                ResourcesPlugin.getWorkspace().getRoot()
                                        .findMember(srcFile);
                        if (file != null && file.isAccessible()) {
                            /*
                             * JA: file.getModificationStamp() cannot be used.
                             */
                            dstFile.setPersistentProperty(SRC_FILE, srcFile);

                            List<IFile> affectedBoms =
                                    getBOMDeepDependencies((IFile) file);
                            String finalChecksum =
                                    DAAExportUtils
                                            .getFilesChecksum(affectedBoms);

                            dstFile.setPersistentProperty(MODEL_CHECKSUM,
                                    finalChecksum);
                        }
                    } else { // Java service project.
                        dstFile.setPersistentProperty(SRC_JAVA_SERVICE_PROJECT,
                                p.getFullPath().toPortableString());
                        /*
                         * JA: MANIFEST.MF is modified every time project is
                         * being built, so modification stamp cannot be used
                         * (even if bin folder is excluded).
                         */
                        dstFile.setPersistentProperty(PROJECT_CHECKSUM,
                                DAAExportUtils.getChecksum(p));
                    }

                } else {
                    String msg =
                            String.format(Messages.CDSCompositeContributor_JarExportFailed_message,
                                    pluginId);
                    throw new CoreException(new Status(IStatus.ERROR,
                            CDSActivator.PLUGIN_ID, msg));
                }
            }
        }
    }

    /**
     * Finds matching built resources (jar files) in a folder by provided
     * plug-in id.
     * 
     * @param folder
     *            folder to search.
     * @param pluginId
     *            identifier of a plug-in
     * @param resourceType
     *            type of resource to find (only {@link IResource#FILE} or
     *            {@link IResource#FOLDER} are allowed.
     * @return collection of built resources (jar file or folder) for a provided
     *         plug-in empty collection is returned if there are no matches.
     * @throws CoreException
     */
    private static Collection<IResource> findResourcesByPluginId(
            IFolder folder, String pluginId, int resourceType)
            throws CoreException {
        ArrayList<IResource> matching = new ArrayList<IResource>(1);
        if (folder.isAccessible()) {
            for (IResource r : folder.members()) {
                if (r instanceof IFile) {
                    Matcher matcher =
                            CustomFeatureManager.PLUGIN_JAR_PATTERN.matcher(r
                                    .getName());
                    if (matcher.matches() && pluginId.equals(matcher.group(1))) {
                        matching.add(r);
                    }
                } else if (r instanceof IFolder) {
                    // Folders are not supported yet.
                }
            }
        }
        return matching;
    }

    /**
     * Returns first element from the collection's iterator or 'null' if
     * collection is empty.
     * 
     * @param collection
     *            the collection.
     * @return first element from the collection's iterator or 'null' if
     *         collection is empty.
     */
    private static <T> T first(Collection<T> collection) {
        if (!collection.isEmpty()) {
            return collection.iterator().next();
        }
        return null;
    }

    /**
     * Stages custom feature with accompanying plug-ins in the custom feature
     * staging folder.
     * 
     * @param project
     * 
     * @param pluginProjects
     * @param stagingFolder
     * @param timeStamp
     * @param createMainProgressMonitor
     * @throws CoreException
     * @throws IOException
     */
    public static IFolder stageCustomFeatureJars(IProject project,
            Map<IProject, String> pluginProjects, IFolder bpmStagingFolder,
            String timeStamp, IProgressMonitor monitor) throws CoreException,
            IOException {

        monitor.beginTask(Messages.CDSCompositeContributor_PreparingCustomFeature_message,
                100);

        IFolder stagingCustFeatureFolder = null;

        if (!pluginProjects.isEmpty()) {

            /*
             * determine whether any of the required plug-in projects in the
             * cache are out-of-date.
             */
            // if (!getProjectsToBuild(pluginProjects).isEmpty()) {
            // buildProjectsIntoCache(monitor,
            // bpmStagingFolder,
            // pluginProjects);
            // }

            // create custom feature artifacts
            // (P/{bom-proj}/.bpm/customfeatures/features)
            // i.e. in .bpm staging area create feature resources
            stagingCustFeatureFolder =
                    getCustomFeaturesFolder(bpmStagingFolder);

            // plug-ins (P/{bom-proj}/.bpm/customfeatures/plugins)
            stageJarsFromCache(project,
                    pluginProjects,
                    stagingCustFeatureFolder,
                    timeStamp);
        }

        monitor.done();

        return stagingCustFeatureFolder;
    }

    /**
     * 
     * Generate custom features for Cached jars - custom feature is pre-built
     * and staged in the staging folder. Then
     * {@link CustomFeaturePackagerParticipant} will be used to package this
     * pre-built feature.
     * 
     * @param project
     * @param timeStamp
     * @param custFeaturesFolder
     * @throws CoreException
     * @throws IOException
     */
    public static void generateCustomFeaturesWhenJarsCached(IProject project,
            String timeStamp, IFolder custFeaturesFolder) throws CoreException,
            IOException {

        for (CustomFeatureEnum cfEnum : CustomFeatureEnum.values()) {

            CustomFeatureManager manager = cfEnum.getManager();
            if (manager.shouldGenerateFeature(project)
                    && custFeaturesFolder != null) {

                /* determine feature file */
                IFolder featureFolder =
                        manager.establishFeatureFolder(project,
                                custFeaturesFolder);
                IFile featureFile = featureFolder.getFile(FEATURE_FILENAME);
                /* generate feature file contents */
                manager.generateCustomFeature(project,
                        true,
                        timeStamp,
                        featureFile);
            }
        }
    }

    /**
     * @param project
     * @return <code>true</code> if feature should be generated for this project
     */
    protected abstract boolean shouldGenerateFeature(IProject project);

    /**
     * @param project
     * @param projectType
     * @param timeStamp
     * @param changeRecorder
     */
    protected void replaceBundlesQualifierWithTS(IProject project,
            String projectType, String timeStamp, IChangeRecorder changeRecorder) {

        Map<String, String> generatedProjectsFromBOM =
                CDSCustomFeatureUtils.getGeneratedProjectsFromBOM(project,
                        projectType);

        if (generatedProjectsFromBOM.size() > 0) {
            Set<String> generatedPluginIds = generatedProjectsFromBOM.keySet();

            for (String generatedPluginId : generatedPluginIds) {
                IProject emfProject =
                        DAANamingUtils
                                .getProjectWithName(DAANamingUtils
                                        .getGeneratedEMFPlugProjectName(generatedPluginId));
                if (emfProject == null || !emfProject.isAccessible()) {
                    continue;
                }
                PluginManifestHelper
                        .replaceBundleManifestQualifierWithTS(emfProject,
                                timeStamp,
                                Boolean.TRUE,
                                changeRecorder);
            }
        }
    }

    /**
     * 
     * Traditional way (when bom jar caching is turned off) - custom feature is
     * created and passed to DAA packager which then uses PDE export to create
     * jars and package custom feature.
     * 
     * @param project
     * @param timeStamp
     * @param stagingFolder
     * @throws CoreException
     * @throws IOException
     */
    public static void generateCustomFeaturesWhenJarsCacheOff(IProject project,
            String timeStamp, IFolder stagingFolder) throws IOException,
            CoreException {

        /*
         * XPD-5462: when a process project bom class has an attribute that
         * refers to a case/global class from global data bom project, daa
         * export job fails. the failure is because the export job cannot find
         * the generated plugin projects (.si.bds and .da.bds) for the dependent
         * global data boms. so trying to resolve this issue by getting the
         * referenced global data bom projects and get all the three generated
         * si.bds and da.bds and .bds plugin projects.
         */
        Set<IProject> globalBOMRefProjects = new HashSet<IProject>();

        /* get the referenced boms if there are any boms in this project */
        ResType[] resTypeArr = new ResType[2];
        resTypeArr[0] = ResType.BOM;
        resTypeArr[1] = ResType.XPDL;

        Set<IResource> bomDependencyList =
                CDSCustomFeatureUtils.getAllBOMDependencyList(project,
                        resTypeArr);

        /* if the referenced boms are global data boms, then get its project */
        for (IResource bomResource : bomDependencyList) {

            if (BOMGlobalDataUtils.isGlobalDataBOM(bomResource)) {

                IProject bomProj = bomResource.getProject();
                if (bomProj != project) {

                    globalBOMRefProjects.add(bomProj);
                }
            }
        }

        /* generate the custom feature for this project */

        for (CustomFeatureEnum customFeatureEnum : CustomFeatureEnum.values()) {

            CustomFeatureManager manager = customFeatureEnum.getManager();
            if (manager.shouldGenerateFeature(project)) {

                /* determine feature file */
                String featureSuffix = customFeatureEnum.getFeatureSuffix();
                String featureFileName =
                        N2PENamingUtils.getCustomFeatureFileName(project,
                                featureSuffix);
                IFile featureFile = stagingFolder.getFile(featureFileName);

                /* generate feature file contents */
                manager.generateCustomFeature(project,
                        false,
                        timeStamp,
                        featureFile);
            }
        }

        /*
         * generate the custom feature for referenced global bom projects
         */

        for (IProject bomProject : globalBOMRefProjects) {

            for (CustomFeatureEnum customFeatureEnum : CustomFeatureEnum
                    .values()) {

                CustomFeatureManager manager = customFeatureEnum.getManager();
                if (manager.shouldGenerateFeature(bomProject)) {
                    /* determine feature file */
                    String featureSuffix = customFeatureEnum.getFeatureSuffix();
                    String featureFileName =
                            N2PENamingUtils
                                    .getCustomFeatureFileName(bomProject,
                                            featureSuffix);
                    IFile featureFile = stagingFolder.getFile(featureFileName);

                    /* generate feature file contents */
                    manager.generateCustomFeature(bomProject,
                            false,
                            timeStamp,
                            featureFile);
                }

            }
        }
    }

}
