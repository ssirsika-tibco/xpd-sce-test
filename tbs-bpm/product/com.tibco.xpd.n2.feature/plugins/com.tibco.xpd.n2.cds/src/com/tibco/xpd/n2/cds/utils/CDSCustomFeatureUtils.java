/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.cds.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.handlers.PruneHandler;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.IQueryResult;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;

import com.tibco.amf.model.productfeature.Dependencies;
import com.tibco.amf.model.productfeature.Dependency;
import com.tibco.amf.model.productfeature.Feature;
import com.tibco.amf.model.productfeature.IncludedPlugin;
import com.tibco.amf.model.productfeature.ProductFeatureFactory;
import com.tibco.amf.model.productfeature.ProductFeaturePackage;
import com.tibco.amf.model.productfeature.VersionMatch;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.gen.internal.BOMGeneratorHelper;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.wsdltransform.builder.WsdlToBomBuilder;
import com.tibco.xpd.daa.internal.util.CustomFeatureUtils;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper.PluginProjectDetails;
import com.tibco.xpd.destinations.ui.GlobalDestinationHelper;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.EAIJavaModelUtil;
import com.tibco.xpd.n2.cds.CDSActivator;
import com.tibco.xpd.n2.cds.customfeature.CustomFeatureEnum;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author kupadhya
 * 
 */
public class CDSCustomFeatureUtils {
    /** N2 Global destination ID. */
    private static final String N2_GLOBAL_DESTINATION_ID =
            "com.tibco.xpd.n2.core.n2GlobalDestination"; //$NON-NLS-1$

    public static final String BOM_CDS_GENERATOR_ID =
            "com.tibco.bds.designtime.generator"; //$NON-NLS-1$

    public static final String BOM_CDS_DA_GENERATOR_ID =
            "com.tibco.bds.designtime.generator.da"; //$NON-NLS-1$

    public static final String BOM_CDS_SI_GENERATOR_ID =
            "com.tibco.bds.designtime.generator.si"; //$NON-NLS-1$

    public enum ResType {
        BOM, XPDL
    };

    /**
     * Declares attributes for BOM plug-in projects
     * 
     * @author patkinso
     * @since 18 Jan 2013
     */
    public enum BomPluginEnum {
        MODEL(BOM_CDS_GENERATOR_ID), DB_ADAPTOR(BOM_CDS_DA_GENERATOR_ID), SCRIPTABLE_INTERFACE(
                BOM_CDS_SI_GENERATOR_ID);

        private String generatorId;

        private BomPluginEnum(String generatorId) {
            this.generatorId = generatorId;
        }

        public String getGeneratorId() {
            return generatorId;
        }

        public static Set<String> getGeneratorIds(
                Set<BomPluginEnum> bomGenerators) {

            Set<String> generatorIDs = new TreeSet<String>();

            for (BomPluginEnum value : bomGenerators) {
                generatorIDs.add(value.getGeneratorId());
            }
            return generatorIDs;
        }
    }

    /**
     * @param project
     * @return
     */
    public static Collection<String> getCustomFeatureIds(IProject project) {

        if (project != null) {

            Set<String> featureIDs = new HashSet<String>();
            Set<String> allFeatureSuffixes = new HashSet<String>();
            if (BOMGlobalDataUtils.hasCaseDataInProject(project)) {

                allFeatureSuffixes = CustomFeatureEnum.getAllFeatureSuffixes();
            } else {
                /*
                 * if it is not a case data bom project, then we need only bds
                 * feature
                 */
                String featureSuffix = CustomFeatureEnum.CDS.getFeatureSuffix();
                allFeatureSuffixes.add(featureSuffix);
            }

            for (String featureSuffix : allFeatureSuffixes) {

                String featureID =
                        N2PENamingUtils.getCustomFeatureId(project,
                                featureSuffix);
                featureIDs.add(featureID);
            }

            return featureIDs;
        }

        return Collections.<String> emptySet();
    }

    public static IResource getProjectCustomFeatureFile(IProject project) {
        IFolder cdsModulesFolder = CDSUtils.getCDSOutDestFolder(project);
        if (cdsModulesFolder != null && cdsModulesFolder.isAccessible()) {
            return cdsModulesFolder.getFile(N2PENamingUtils
                    .getCustomFeatureFileName(project));
        }
        return null;
    }

    public static Feature getProjectCustomFeatureModel(String projectName) {
        IResource projectCustomFeatureFile =
                getProjectCustomFeatureFile(projectName);
        return getCustomFeature(projectCustomFeatureFile);
    }

    /**
     * Given custom feature file, give custom feature
     * 
     * @param customFeature
     * @return
     */
    private static Feature getCustomFeature(IResource customFeature) {
        if (customFeature == null || !customFeature.isAccessible()) {
            return null;
        }
        try {
            ResourceSetImpl resourceSet = new ResourceSetImpl();
            URI uri = URI.createFileURI(customFeature.getFullPath().toString());
            Resource resource = resourceSet.createResource(uri);
            resource.load(Collections.EMPTY_MAP);
            Feature feature =
                    (Feature) EcoreUtil.getObjectByType(resource.getContents(),
                            ProductFeaturePackage.Literals.FEATURE);
            return feature;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static IResource getProjectCustomFeatureFile(String projectName) {
        if (projectName == null) {
            return null;
        }
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(projectName);
        if (project == null || !project.isAccessible()) {
            return null;
        }
        IResource customFeature = getProjectCustomFeatureFile(project);
        return customFeature;
    }

    /**
     * @param project
     * @return
     */
    public static IResource getStagingCustomFeatureFile(IProject project) {
        return getStagingCustomFeatureFile(project, null);
    }

    /**
     * 
     * @param project
     * @return
     */
    public static IResource getStagingCustomFeatureFile(IProject project,
            CustomFeatureEnum customFeatureEnum) {
        IFolder daaFolder =
                N2PENamingUtils.getModuleOutputFolder(project, false);
        if (daaFolder == null || !daaFolder.isAccessible()) {
            return null;
        }
        IPath customFeaturePath =
                daaFolder
                        .getFullPath()
                        .append(N2PENamingUtils.getCustomFeatureFileName(project));
        IResource customFeature =
                project.getWorkspace().getRoot().getFile(customFeaturePath);
        /*
         * If custom feature file is not present try to find custom feature that
         * uses cached jars.
         */
        if (!customFeature.isAccessible()) {
            /*
             * For example:
             * /MyProject/customfeatures/features/com.my.project/feature.xml
             */
            String custFeatureSuffix =
                    (customFeatureEnum != null) ? customFeatureEnum
                            .getFeatureSuffix() : null;
            IPath featurePath =
                    daaFolder
                            .getFullPath()
                            .append(N2PENamingUtils.CUSTOMFEATURES_FOLDER_NAME)
                            .append(N2PENamingUtils.FEATURES_FOLDER)
                            .append(N2PENamingUtils.getCustomFeatureId(project,
                                    custFeatureSuffix)).append("feature.xml"); //$NON-NLS-1$
            customFeature =
                    project.getWorkspace().getRoot().getFile(featurePath);
        }
        return customFeature;
    }

    /**
     * 
     * @param projectName
     * @param qualifierReplacer
     * @return
     */
    public static IResource getStagingCustomFeatureFile(String projectName) {
        return getStagingCustomFeatureFile(projectName, null);
    }

    public static IResource getStagingCustomFeatureFile(String projectName,
            CustomFeatureEnum customFeatureEnum) {

        if (projectName == null) {
            return null;
        }
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(projectName);
        if (project == null || !project.isAccessible()) {
            return null;
        }
        return getStagingCustomFeatureFile(project, customFeatureEnum);
    }

    public static Feature getStagingCustomFeatureModel(String projectName) {
        IResource stagingCustomFeatureFile =
                getStagingCustomFeatureFile(projectName);
        Feature customFeature = getCustomFeature(stagingCustomFeatureFile);
        return customFeature;
    }

    public static void addIncludedPluginFromPluginProjectDetails(
            IProject eachReferencedProject,
            List<IncludedPlugin> generatedPlugins, String timeStamp) {

        PluginManifestHelper.PluginProjectDetails pluginProjectDetails =
                PluginManifestHelper
                        .getPluginProjectDetails(eachReferencedProject);
        Set<PluginProjectDetails> flatList =
                new HashSet<PluginProjectDetails>();
        PluginManifestHelper.flattenProjectDetailsList(pluginProjectDetails,
                flatList);

        for (PluginProjectDetails eachPluginProjectDetails : flatList) {

            String updatedPluginVersion =
                    PluginManifestHelper
                            .getUpdatedBundleVersion(eachPluginProjectDetails
                                    .getBundleVersion(), timeStamp);
            IncludedPlugin eachRB =
                    CustomFeatureUtils
                            .createIncludedPlugin(eachPluginProjectDetails
                                    .getBundleId(), updatedPluginVersion);
            generatedPlugins.add(eachRB);
        }
    }

    private static Set<IResource> getGeneratedBOMForReferencedWSDL(
            IProject project) {
        Set<IResource> projectBOMResources = new HashSet<IResource>();
        List<IResource> xpdlResources =
                SpecialFolderUtil
                        .getAllDeepResourcesInSpecialFolderOfKind(project,
                                N2PENamingUtils.PROCESS_SPECIALFOLDER_KIND,
                                N2PENamingUtils.XPDL_FILE_EXTENSION,
                                false);
        for (IResource xpdlFile : xpdlResources) {
            if (!N2Utils.hasN2ProcessesOrInterfaces(xpdlFile)) {
                continue;
            }
            String portableXpdlPathString =
                    xpdlFile.getFullPath().toPortableString();
            Set<IResource> referenceBOMSet =
                    getAllGeneratedBOMForReferencedWSDLFromXpdl(project,
                            portableXpdlPathString);
            projectBOMResources.addAll(referenceBOMSet);

        }
        return projectBOMResources;
    }

    /**
     * Returns a map (id and version) of generated emf projects for all the boms
     * referenced by a given project.
     * 
     * @param project
     * @param projectType
     * @return
     */
    public static Map<String, String> getGeneratedProjectsFromBOM(
            IProject project, String projectType) {

        Set<IResource> projectBOMResources = getAllBOMResources(project);

        Map<String, String> generatedProjectIds =
                CustomFeatureUtils
                        .getGeneratedEMFProjectIdsWithVersion(projectBOMResources,
                                projectType);
        return generatedProjectIds;
    }

    /**
     * Gets all user defined and generated boms for the given project (and its
     * referenced projects)
     * 
     * @param project
     * @return Set<IResource> of bom resources referenced by the given project
     */
    public static Set<IResource> getAllBOMResources(IProject project) {

        Set<IResource> projectBOMResources = new HashSet<IResource>();
        Set<IProject> referencedProjectsHierarchy = new HashSet<IProject>();
        referencedProjectsHierarchy.add(project);
        /* get all referenced projects */
        Set<IProject> allProjectsHierarchy =
                ProjectUtil.getReferencedProjectsHierarchy(project,
                        referencedProjectsHierarchy);
        ResType[] resTypeArr = new ResType[1];
        resTypeArr[0] = ResType.BOM;

        for (IProject iProject : allProjectsHierarchy) {
            Set<IResource> allBOMDependencyList =
                    CDSCustomFeatureUtils.getAllBOMDependencyList(iProject,
                            resTypeArr);
            projectBOMResources.addAll(allBOMDependencyList);
        }
        /* not sure whether we need to check for BOM generated from WSDLs. */
        Set<IResource> generatedBOMForReferencedWSDL =
                getGeneratedBOMForReferencedWSDL(project);
        projectBOMResources.addAll(generatedBOMForReferencedWSDL);

        return projectBOMResources;
    }

    /**
     * Returns all BOM resources which are being directly/indirectly referred
     * from given xpdl file but in the same project
     * 
     * @param project
     * @param xpdlFilePath
     * @return
     */
    public static Set<IResource> getAllReferencedBOMFromXpdlWithInGivenProject(
            IProject project, String xpdlFilePath) {
        String projectName = project.getName();
        Set<IResource> projectBomRes = new HashSet<IResource>();
        Set<IResource> allReferencedBOMFromXpdl =
                getAllReferencedBOMFromXpdl(project, xpdlFilePath);
        if (allReferencedBOMFromXpdl != null) {
            for (IResource bomResource : allReferencedBOMFromXpdl) {
                String bomResProjectName = bomResource.getProject().getName();
                if (projectName.equals(bomResProjectName)) {
                    projectBomRes.add(bomResource);
                }

            }
        }
        return projectBomRes;
    }

    /**
     * Given a XPDL file, it returns all BOM resources referenced directly and
     * indirectly
     * 
     * @param project
     * @param xpdlFilePath
     * @return
     */
    public static Set<IResource> getAllReferencedBOMFromXpdl(IProject project,
            String xpdlFilePath) {
        Set<IResource> toReturn = new HashSet<IResource>();
        Set<IResource> referencedBoms =
                ProcessUIUtil.queryReferencingBomResources(project,
                        xpdlFilePath,
                        false);
        toReturn.addAll(referencedBoms);
        if (referencedBoms != null) {
            for (IResource bomResource : referencedBoms) {
                Set<IResource> bomSet = new HashSet<IResource>();
                populateAllReferencedBOM(bomResource, bomSet);
                toReturn.addAll(bomSet);
            }
        }
        return toReturn;
    }

    /**
     * Given a XPDL file, it returns all BOM generated for all the reference
     * WSDL
     * 
     * @param project
     * @param xpdlFilePath
     * @return
     */
    public static Set<IResource> getAllGeneratedBOMForReferencedWSDLFromXpdl(
            IProject project, String xpdlFilePath) {
        Set<IResource> toReturn = new HashSet<IResource>();
        IFile xpdlFile =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(xpdlFilePath));
        Collection<IResource> queryReferencedWSDLResources =
                ProcessUIUtil.queryReferencedWSDLResources(xpdlFile, false);
        for (IResource eachWSDL : queryReferencedWSDLResources) {
            Set<IFile> bomFiles =
                    WsdlToBomBuilder
                            .getBOMFiles((IFile) eachWSDL, false, false);
            for (IFile correspondingBOM : bomFiles) {
                if (correspondingBOM != null && correspondingBOM.isAccessible()) {
                    toReturn.add(correspondingBOM);
                    Set<IResource> bomSet = new HashSet<IResource>();
                    populateAllReferencedBOM(correspondingBOM, bomSet);
                    toReturn.addAll(bomSet);
                }
            }
        }
        return toReturn;
    }

    private static void populateAllReferencedBOM(IResource bomRes,
            Set<IResource> bomSet) {
        WorkingCopy bomWC = WorkingCopyUtil.getWorkingCopy(bomRes);
        List<IResource> dependencyList = bomWC.getDependency();
        if (dependencyList != null) {
            for (IResource eachResource : dependencyList) {
                WorkingCopy eachResWC =
                        WorkingCopyUtil.getWorkingCopy(eachResource);
                if (eachResWC instanceof BOMWorkingCopy) {
                    bomSet.add(eachResource);
                    populateAllReferencedBOM(eachResource, bomSet);
                }
            }
        }
    }

    public static Set<IResource> getAllReferencedBOM(IProject studioProject,
            ResType[] resTypeArr) {
        Set<IProject> referencedProjectsHierarchy = new HashSet<IProject>();
        referencedProjectsHierarchy.add(studioProject);
        // get all referenced projects
        Set<IProject> allProjectsHierarchy =
                ProjectUtil.getReferencedProjectsHierarchy(studioProject,
                        referencedProjectsHierarchy);
        Set<IResource> allBomReferred = new HashSet<IResource>();
        for (IProject iProject : allProjectsHierarchy) {
            allBomReferred
                    .addAll(getAllBOMDependencyList(iProject, resTypeArr));
        }
        return allBomReferred;
    }

    /**
     * Returns a list of all BOM files which come from passed & external project
     * 
     * @param project
     * @return
     */
    public static Set<IResource> getAllBOMDependencyList(IProject project,
            ResType[] resTypeArr) {
        Set<IResource> allBomReferred = new HashSet<IResource>();
        for (int index = 0; index < resTypeArr.length; index++) {
            ResType resType = resTypeArr[index];
            if (ResType.BOM == resType) {
                // finding all external BOM projects due to BOM file reference
                List<IResource> bomResources =
                        SpecialFolderUtil
                                .getAllDeepResourcesInSpecialFolderOfKind(project,
                                        N2PENamingUtils.BOM_SPECIALFOLDER_KIND,
                                        N2PENamingUtils.BOM_SPECIALFOLDER_KIND,
                                        false);
                for (final IResource resource : bomResources) {
                    if (resource != null && resource.exists()) {
                        if (!(resource instanceof IFile)) {
                            continue;
                        }
                        IFile bomFile = (IFile) resource;
                        allBomReferred.add(bomFile);
                        populateAllReferencedBOM(bomFile, allBomReferred);
                    }
                }

            } else if (ResType.XPDL == resType) {
                // finding all external BOM projects due to xpdl file reference
                List<IResource> xpdlResources =
                        SpecialFolderUtil
                                .getAllDeepResourcesInSpecialFolderOfKind(project,
                                        N2PENamingUtils.PROCESS_SPECIALFOLDER_KIND,
                                        N2PENamingUtils.XPDL_FILE_EXTENSION,
                                        false);
                if (xpdlResources != null) {
                    for (final IResource resource : xpdlResources) {
                        if (resource != null && resource.exists()) {
                            if (!(resource instanceof IFile)) {
                                continue;
                            }
                            IFile xpdlFile = (IFile) resource;
                            if (!N2Utils.hasN2ProcessesOrInterfaces(xpdlFile)) {
                                continue;
                            }
                            Set<IResource> allReferencedBOMFromXpdl =
                                    getAllReferencedBOMFromXpdl(project,
                                            xpdlFile.getFullPath()
                                                    .toPortableString());
                            allBomReferred.addAll(allReferencedBOMFromXpdl);

                        }
                    }
                }
            }
        }
        return allBomReferred;
    }

    /**
     * Finds all BOM resources which come from dependent projects and adds
     * feature dependency in custom feature dependency
     * 
     * @param dependencies
     * @param project
     */
    public static void addCustomFeatureDependencies(Dependencies dependencies,
            IProject project) {
        // Add dependencies for Studio Project Dependency
        ResType[] resTypeArr = new ResType[1];
        resTypeArr[0] = ResType.XPDL;
        Set<IProject> externalProjectsWithBOM =
                getExternalProjectsWithBOM(project, resTypeArr);
        for (IProject referencedProject : externalProjectsWithBOM) {
            String dependantCustomFeatureId =
                    N2PENamingUtils.getCustomFeatureId(referencedProject);
            String dependantCustomFeatureVersion =
                    CDSUtils.getCustomFeatureVersion(referencedProject);
            if (dependantCustomFeatureId != null
                    && dependantCustomFeatureVersion != null) {
                Dependency dependency =
                        ProductFeatureFactory.eINSTANCE.createDependency();
                dependency.setFeature(dependantCustomFeatureId);
                dependency.setMatch(VersionMatch.COMPATIBLE);
                // dependantCustomFeatureVersion =
                //                        dependantCustomFeatureVersion.replaceAll(".qualifier$", //$NON-NLS-1$
                //                                ""); //$NON-NLS-1$
                dependency.setVersion(dependantCustomFeatureVersion);
                dependencies.getImports().add(dependency);
            }
        }

    }

    /**
     * Finds all BOM resources which come from dependent projects, checks if the
     * dependent project is a business data project and adds feature dependency
     * in custom feature dependency
     * 
     * @param dependencies
     * @param project
     */
    public static void addCustomFeatureDependencyOnGlobalBom(
            Dependencies dependencies, IProject project) {

        /*
         * XPD-7085: Prior to this jira we were taking bom resource and/or xpdl
         * resource dependency into account to add feature dependency. We now do
         * it by taking project reference into account.
         * 
         * This is because we now add requires capability between process
         * project and BDP even when there is no direct dependency between
         * processes / boms in the process project. In order for
         * deploy/undeploy/upgrade etc use cases, when we include the requires
         * capability we also need to include requires bundles in the
         * requirements file. This implies that we should add feature
         * dependencies on custom features for the generated plugins also (to
         * reflect in machine.xmi).
         */

        Set<IResource> allBOMResources = getAllBOMResources(project);
        for (IResource bomResource : allBOMResources) {

            IProject referencedProject = bomResource.getProject();

            /*
             * Don't want to add dependency on itself. Don't want to add
             * dependency if it is not a business data project.
             */
            if (referencedProject != project
                    && BOMUtils.isBusinessDataProject(referencedProject)) {

                String dependantCustomFeatureId =
                        N2PENamingUtils.getCustomFeatureId(referencedProject);
                String dependantCustomFeatureVersion =
                        CDSUtils.getCustomFeatureVersion(referencedProject);
                if (dependantCustomFeatureId != null
                        && dependantCustomFeatureVersion != null) {

                    Dependency dependency =
                            ProductFeatureFactory.eINSTANCE.createDependency();
                    dependency.setFeature(dependantCustomFeatureId);

                    dependency.setMatch(VersionMatch.PERFECT);

                    dependency.setVersion(dependantCustomFeatureVersion);
                    dependencies.getImports().add(dependency);
                }
            }
        }
    }

    /**
     * Returns a list of all BOM files which come from external project
     * 
     * @param project
     * @return
     */
    public static Set<IProject> getExternalProjectsWithBOM(IProject project,
            ResType[] resTypeArr) {
        Set<IProject> externalProjects = new HashSet<IProject>();
        String projectName = project.getName();
        Set<IResource> allBOMDependencyList =
                getAllBOMDependencyList(project, resTypeArr);
        for (IResource bomResource : allBOMDependencyList) {
            IProject bomResProject = bomResource.getProject();
            String bomResProjectName = bomResProject.getName();
            if (!projectName.equals(bomResProjectName)) {
                externalProjects.add(bomResProject);
            }
        }
        return externalProjects;
    }

    /**
     * We could have got Plugin version from Project but would like to keep that
     * logic at just one place
     * 
     * @param project
     * @return
     */
    public static String getGeneratedEMFProjectVersion(IProject project) {
        return ProjectUtil.getProjectVersion(project);
    }

    private static final String ECLIPSE_EMF_FEATURE = "org.eclipse.emf"; //$NON-NLS-1$

    private static final String EMF_FEATURE_VERSION = "2.4.0"; //$NON-NLS-1$

    public static Dependency getEMFFeatureDependency() {
        Dependency emfDependency =
                ProductFeatureFactory.eINSTANCE.createDependency();
        emfDependency.setFeature(ECLIPSE_EMF_FEATURE);
        emfDependency.setMatch(VersionMatch.COMPATIBLE);
        emfDependency.setVersion(EMF_FEATURE_VERSION);
        return emfDependency;
    }

    /**
     * Returns a list of all BOM files which come from external project
     * 
     * @param project
     * @return
     */
    public static Set<IResource> getBOMReferencesFromExternalProject(
            IProject project, ResType[] resTypeArr) {
        Set<IResource> externalBOMList = new HashSet<IResource>();
        String projectName = project.getName();
        Set<IResource> allBOMDependencyList =
                getAllBOMDependencyList(project, resTypeArr);
        for (IResource bomResource : allBOMDependencyList) {
            IProject bomResProject = bomResource.getProject();
            String bomResProjectName = bomResProject.getName();
            if (!projectName.equals(bomResProjectName)) {
                externalBOMList.add(bomResource);
            }
        }
        return externalBOMList;
    }

    /**
     * Returns a collecion of Java Projects referred from all the XPDL files in
     * the passed project
     * 
     * @param project
     * @return
     */
    public static Set<IProject> getAllReferencedJavaServicePlugins(
            IProject project) {
        List<IResource> xpdlResources =
                SpecialFolderUtil
                        .getAllDeepResourcesInSpecialFolderOfKind(project,
                                N2PENamingUtils.PROCESS_SPECIALFOLDER_KIND,
                                N2PENamingUtils.XPDL_FILE_EXTENSION,
                                false);
        Set<IProject> javaServiceProjects = new HashSet<IProject>();
        if (xpdlResources != null) {
            for (final IResource eachResource : xpdlResources) {
                Set<IProject> referencedJavaServiceProjects =
                        getReferencedJavaServiceProjects(eachResource);
                javaServiceProjects.addAll(referencedJavaServiceProjects);
            }
        }
        return javaServiceProjects;
    }

    /**
     * Returns a collection of Java Projects referred from Java Service task in
     * a given xpdl file
     * 
     * @param xpdlFile
     * @return
     */
    public static Set<IProject> getReferencedJavaServiceProjects(
            IResource xpdlResource) {
        Set<IProject> referredProjects = new HashSet<IProject>();
        if (xpdlResource == null || !xpdlResource.isAccessible()) {
            return referredProjects;
        }
        if (!(xpdlResource instanceof IFile)) {
            return referredProjects;
        }
        IFile xpdlFile = (IFile) xpdlResource;
        if (!N2Utils.hasN2ProcessesOrInterfaces(xpdlFile)) {
            return referredProjects;
        }
        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(xpdlFile);
        if (workingCopy != null) {
            Package xpdlPackage = (Package) workingCopy.getRootElement();
            Collection<Activity> javaServiceTasks =
                    getJavaServiceTasks(xpdlPackage);
            if (javaServiceTasks == null || javaServiceTasks.isEmpty()) {
                return referredProjects;
            }
            for (Activity activity : javaServiceTasks) {
                IProject projectReferredFromJavaServiceTask =
                        getProjectReferredFromJavaServiceTask(activity);
                if (projectReferredFromJavaServiceTask != null) {
                    referredProjects.add(projectReferredFromJavaServiceTask);
                }

            }
        }
        return referredProjects;
    }

    private static IProject getProjectReferredFromJavaServiceTask(
            Activity javaServiceActivity) {
        if (!(javaServiceActivity.getImplementation() instanceof Task)) {
            return null;
        }
        TaskService taskService =
                ((Task) javaServiceActivity.getImplementation())
                        .getTaskService();
        if (taskService == null) {
            return null;
        }
        String type =
                (String) Xpdl2ModelUtil.getOtherAttribute(taskService,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType());

        if (!"Java".equals(type)) { //$NON-NLS-1$
            return null;
        }
        EAIJava eaiJava = EAIJavaModelUtil.getEAIJavaObj(taskService);
        if (eaiJava == null) {
            return null;
        }
        String projectName = eaiJava.getProject();
        if (projectName == null || projectName.trim().length() < 1) {
            return null;
        }
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(projectName);
        if (project == null || !project.isAccessible()) {
            return null;
        }
        return project;
    }

    @SuppressWarnings("unchecked")
    public static Collection<Activity> getJavaServiceTasks(Package xpdlPackage) {

        // Prune all processes without global N2 destination environment
        PruneHandler pruneHandler = new PruneHandler() {
            /** {@inheritDoc} */
            @Override
            public boolean shouldPrune(EObject object) {
                if (object instanceof com.tibco.xpd.xpdl2.Process) {
                    com.tibco.xpd.xpdl2.Process process =
                            (com.tibco.xpd.xpdl2.Process) object;
                    if (!GlobalDestinationHelper
                            .isGlobalDestinationEnabled(process,
                                    N2_GLOBAL_DESTINATION_ID)) {
                        return true;
                    }
                }
                return false;
            }
        };
        IQueryResult result =
                new SELECT(new FROM(xpdlPackage), new WHERE(
                        new IsJavaServiceTask(pruneHandler))).execute();
        Exception e = result.getException();
        if (e != null) {
            throw new RuntimeException(e);
        }
        return (Collection<Activity>) result.getEObjects();
    }

    /**
     * Condition for selection of manual task activities.
     */
    public static class IsJavaServiceTask extends EObjectCondition {
        public IsJavaServiceTask() {
            super();
        }

        public IsJavaServiceTask(PruneHandler pruneHandler) {
            super(pruneHandler);
        }

        @Override
        public boolean isSatisfied(EObject object) {
            if (object instanceof Activity) {
                Activity activity = (Activity) object;
                if (activity.getImplementation() instanceof Task) {
                    TaskService taskService =
                            ((Task) activity.getImplementation())
                                    .getTaskService();
                    if (taskService != null) {
                        String type =
                                (String) Xpdl2ModelUtil
                                        .getOtherAttribute(taskService,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ImplementationType());

                        if ("Java".equals(type)) { //$NON-NLS-1$
                            return true;
                        }
                    }
                }
            }
            return false;
        }

    }

    /**
     * 
     * @param procOrGlobalBOMProject
     * @param monitor
     * @return
     */
    public static IStatus runOnDemandBdsGenerators(
            IProject procOrGlobalBOMProject, IProgressMonitor monitor) {

        try {
            // TODO Override runOnDemandGeneration() method to specify
            // extension which feature the resultant plug-in is for so
            // knows which generators should be returned.
            //
            // Probably need method within BOMGenerator2ExtensionHelper to
            // get filtered list of generators [getGenerators(String
            // feature);]

            if (BOMUtils.isBusinessDataProject(procOrGlobalBOMProject)) {
                /*
                 * if it is a business data project with case classes in it then
                 * we need bds, si.bds and da.bds generators
                 */
                if (BOMGlobalDataUtils
                        .hasCaseDataInProject(procOrGlobalBOMProject)) {

                    BOMGeneratorHelper.getInstance()
                            .runOnDemandGeneration(procOrGlobalBOMProject,
                                    monitor,
                                    CustomFeatureEnum.getAllGeneratorIDs());
                } else {
                    /*
                     * if it is business data project with just global classes
                     * in it then we need only bds generators
                     */
                    Set<String> generatorId =
                            Collections
                                    .<String> singleton(CDSCustomFeatureUtils.BOM_CDS_GENERATOR_ID);
                    BOMGeneratorHelper.getInstance()
                            .runOnDemandGeneration(procOrGlobalBOMProject,
                                    monitor,
                                    generatorId);
                }
            } else {

                /*
                 * we need only bds generator for process project
                 */
                Set<String> generatorId =
                        Collections
                                .<String> singleton(CDSCustomFeatureUtils.BOM_CDS_GENERATOR_ID);
                BOMGeneratorHelper.getInstance()
                        .runOnDemandGeneration(procOrGlobalBOMProject,
                                monitor,
                                generatorId);
                /*
                 * for a process project get all the referenced global data bom
                 * projects. generate all the three bds projects for the
                 * referenced global data bom
                 */
                Set<IProject> refGDBomProjects = new HashSet<IProject>();
                Set<IProject> allRefProjects = new HashSet<IProject>();
                try {
                    allRefProjects =
                            ProjectUtil2
                                    .getReferencedProjectsHierarchy(procOrGlobalBOMProject,
                                            true);
                } catch (CyclicDependencyException e) {
                }
                /* get all global data projects */
                for (IProject iProject : allRefProjects) {

                    if (BOMUtils.isBusinessDataProject(iProject)) {

                        refGDBomProjects.add(iProject);
                    }
                }

                /*
                 * generate bds projects for referenced business data projects
                 */
                for (IProject iProject : refGDBomProjects) {
                    /*
                     * generate all the three .bds, si.bds and da.bds for case
                     * data
                     */
                    if (BOMGlobalDataUtils.hasCaseDataInProject(iProject)) {

                        BOMGeneratorHelper.getInstance()
                                .runOnDemandGeneration(iProject,
                                        monitor,
                                        CustomFeatureEnum.getAllGeneratorIDs());
                    } else {
                        /*
                         * generate only .bds for business data project with
                         * only global boms
                         */
                        BOMGeneratorHelper.getInstance()
                                .runOnDemandGeneration(iProject,
                                        monitor,
                                        generatorId);
                    }
                }

            }

        } catch (CoreException e) {

            CDSActivator.getDefault().getLog().log(e.getStatus());
            return e.getStatus();
        }
        return Status.OK_STATUS;
    }
}
