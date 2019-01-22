/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.cds.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.tibco.amf.dependency.osgi.OsgiFactory;
import com.tibco.amf.dependency.osgi.RequiredBundle;
import com.tibco.amf.dependency.osgi.RequiredFeature;
import com.tibco.amf.dependency.osgi.VersionRange;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.xpd.daa.internal.util.CustomFeatureUtils;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper.PluginProjectDetails;
import com.tibco.xpd.n2.cds.customfeature.CustomFeatureEnum;
import com.tibco.xpd.n2.cds.utils.CDSCustomFeatureUtils.ResType;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;

/**
 * @author kupadhya
 * 
 */
public class CDSDependencyUtils {

    /**
     * For a given xpdl file, find out all the referred BOM & add them as
     * required bundles
     * 
     * @param project
     * @param xpdlFilePath
     * @param requiredBundles
     * @param qualifierReplacer
     */
    public static void addAllRequiredBundlesForXpdlFile(IProject project,
            String xpdlFilePath, List<RequiredBundle> requiredBundles,
            String qualifierReplacer) {

        // Set<IResource> allReferencedBOMForGivenProject =
        // CDSCustomFeatureUtils.getAllReferencedBOMFromXpdl(project,
        // xpdlFilePath);
        /*
         * XPD-2258: we need to add all the BOM present in the project & the
         * referenced projects as we do not know what BOM might be used for xsd
         * Any data type
         */
        ResType[] resTypeArr = new ResType[1];
        resTypeArr[0] = ResType.BOM;
        Set<IResource> allReferencedBOMForGivenProject =
                CDSCustomFeatureUtils.getAllReferencedBOM(project, resTypeArr);

        updateRBListForPassedBOMReferences(project,
                allReferencedBOMForGivenProject,
                requiredBundles,
                qualifierReplacer,
                CDSCustomFeatureUtils.BOM_CDS_GENERATOR_ID);
    }

    /**
     * For a given xpdl file, find out all the referred WSDL & then
     * corresponding generated BOM & add them as required bundles
     * 
     * @param project
     * @param xpdlFilePath
     * @param requiredBundles
     * @param qualifierReplacer
     */
    public static void addAllRequiredBundlesForReferencedWSDLFile(
            IProject project, String xpdlFilePath,
            List<RequiredBundle> requiredBundles, String qualifierReplacer) {
        Set<IResource> referencedBOMSet =
                CDSCustomFeatureUtils
                        .getAllGeneratedBOMForReferencedWSDLFromXpdl(project,
                                xpdlFilePath);
        updateRBListForPassedBOMReferences(project,
                referencedBOMSet,
                requiredBundles,
                qualifierReplacer,
                CDSCustomFeatureUtils.BOM_CDS_GENERATOR_ID);
    }

    /**
     * Adds all the Java Plugin Projects which are referred from an xpdl file
     * 
     * @param project
     * @param xpdlFilePath
     * @param requiredBundles
     * @param qualifierReplacer
     */
    public static void addReferredJavaServiceTaskBundles(IProject project,
            String xpdlFilePath, List<RequiredBundle> requiredBundles,
            String qualifierReplacer) {
        // finding out any Plugin Projects referred from a Java Service task
        // from xpdl file
        IFile xpdlFile =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(xpdlFilePath));
        if (xpdlFile == null || !xpdlFile.isAccessible()) {
            return;
        }
        Set<IProject> referencedJavaServiceProjects =
                CDSCustomFeatureUtils
                        .getReferencedJavaServiceProjects(xpdlFile);
        Set<PluginProjectDetails> flatList =
                new HashSet<PluginProjectDetails>();
        for (IProject eachJavaProject : referencedJavaServiceProjects) {
            PluginProjectDetails pluginProjectDetails =
                    PluginManifestHelper
                            .getPluginProjectDetails(eachJavaProject);
            PluginManifestHelper
                    .flattenProjectDetailsList(pluginProjectDetails, flatList);
        }
        for (PluginProjectDetails eachPluginProjectDetails : flatList) {
            String bundleId = eachPluginProjectDetails.getBundleId();
            String bundleVersion = eachPluginProjectDetails.getBundleVersion();
            RequiredBundle javaRB =
                    createRequireBundleFromIncludedPlugin(bundleId,
                            bundleVersion,
                            qualifierReplacer);
            addRequiredBundleToList(requiredBundles, javaRB);
        }
    }

    /**
     * Returns a list of RequiredBundle by resolving all the BOM files referred
     * [directly or indirectly] from all the xpdl files in the Project.
     * 
     * @param projectName
     * @param qualifierReplacer
     * 
     */
    public static void updateRBListWithExternalprojectReferences(
            String projectName,
            List<RequiredBundle> externalBomRequiredBundles,
            String qualifierReplacer, String bomGeneratorContributionId) {
        if (projectName == null || projectName.length() < 1) {
            return;
        }
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(projectName);
        if (project == null || !project.isAccessible()) {
            return;
        }
        List<String> xpdlFilePaths = N2PENamingUtils.getXpdlFilePaths(project);
        if (xpdlFilePaths == null || xpdlFilePaths.isEmpty()) {
            return;
        }
        ResType[] resTypeArr = new ResType[1];
        resTypeArr[0] = ResType.XPDL;
        for (String xpdlFilePath : xpdlFilePaths) {
            if (xpdlFilePath == null || xpdlFilePath.trim().length() < 1) {
                continue;
            }
            Set<IResource> referencesFromExternalProject =
                    CDSCustomFeatureUtils
                            .getBOMReferencesFromExternalProject(project,
                                    resTypeArr);
            updateRBListForPassedBOMReferences(project,
                    referencesFromExternalProject,
                    externalBomRequiredBundles,
                    qualifierReplacer,
                    bomGeneratorContributionId);
        }

    }

    /**
     * Update externalBomRequiredBundles with the corresponding project id for
     * the project
     * 
     * @param referencesFromExternalProject
     * @param externalBomRequiredBundles
     * @param qualifierReplacer
     */
    private static void updateRBListForPassedBOMReferences(IProject project,
            Set<IResource> referencesFromExternalProject,
            List<RequiredBundle> externalBomRequiredBundles,
            String qualifierReplacer, String bomGeneratorContributorId) {
        if (referencesFromExternalProject == null
                || referencesFromExternalProject.isEmpty()) {
            return;
        }
        Map<String, String> generatedEMFProjectIdsWithVersion =
                CustomFeatureUtils
                        .getGeneratedEMFProjectIdsWithVersion(referencesFromExternalProject,
                                bomGeneratorContributorId);
        Set<Entry<String, String>> entrySet =
                generatedEMFProjectIdsWithVersion.entrySet();
        for (Entry<String, String> eachEntry : entrySet) {
            String pluginId = eachEntry.getKey();
            String pluginVersion = eachEntry.getValue();
            if (pluginId == null || pluginId.trim().length() < 1
                    || pluginVersion == null
                    || pluginVersion.trim().length() < 1) {
                continue;
            }
            RequiredBundle requiredBundle =
                    OsgiFactory.eINSTANCE.createRequiredBundle();
            requiredBundle.setName(pluginId);
            VersionRange versionRange =
                    OsgiFactory.eINSTANCE.createVersionRange();
            String versionWithTimeStamp =
                    PluginManifestHelper.getUpdatedBundleVersion(pluginVersion,
                            qualifierReplacer);
            versionRange.setLower(versionWithTimeStamp);
            versionRange.setLowerIncluded(true);
            // upper version
            versionRange.setUpper(versionWithTimeStamp);
            versionRange.setUpperIncluded(Boolean.TRUE);
            requiredBundle.setRange(versionRange);
            addRequiredBundleToList(externalBomRequiredBundles, requiredBundle);
        }
    }

    /**
     * Only adds the passed RequiredBundle if it is not there in the list
     * 
     * @param externalBomRequiredBundles
     * @param requiredBundle
     */
    private static void addRequiredBundleToList(
            List<RequiredBundle> externalBomRequiredBundles,
            RequiredBundle requiredBundle) {
        if (externalBomRequiredBundles == null) {
            return;
        }
        boolean alreadyPresent = false;
        for (RequiredBundle existingRB : externalBomRequiredBundles) {
            if (existingRB == null) {
                continue;
            }
            if (doRequiredBundleMatch(existingRB, requiredBundle)) {
                alreadyPresent = true;
                break;
            }
        }
        if (!alreadyPresent) {
            externalBomRequiredBundles.add(requiredBundle);
        }
    }

    public static boolean doRequiredBundleMatch(RequiredBundle lhsRB,
            RequiredBundle rhsRB) {
        boolean doMatch = false;
        if (lhsRB == null || rhsRB == null) {
            return doMatch;
        }
        if (lhsRB.getName().equals(rhsRB.getName())) {
            doMatch = doVersionMatch(lhsRB.getRange(), rhsRB.getRange());
        }
        return doMatch;
    }

    /**
     * Compares the newly created required capability with existing required
     * capability.
     * 
     * @param bomProject
     * @param existingReqCapability
     * @param createReqCapability
     * @return <code>true</code> if newly created required capability matches
     *         with existing required capability <code>false</code> otherwise
     */
    public static boolean doRequiredCapabilitiesMatch(IProject bomProject,
            RequiredCapability existingReqCapability,
            RequiredCapability createReqCapability) {

        boolean doMatch = false;
        if (null == existingReqCapability || null == createReqCapability) {

            return doMatch;
        }
        if (existingReqCapability.getId().equals(createReqCapability.getId())
                && existingReqCapability.getType()
                        .equals(createReqCapability.getType())) {

            doMatch =
                    doVersionMatch(existingReqCapability.getVersionRange(),
                            createReqCapability.getVersionRange());
        }
        return doMatch;
    }

    /**
     * Compares the newly created required feature with the existing feature.
     * First compares the names, if the names match then compares the version
     * range.
     * 
     * @param existingRequiredFeature
     * @param createRequiredFeature
     * @return <code>true</code> if newly created required feature matches with
     *         existing required feature <code>false</code> otherwise
     */
    public static boolean doRequiredFeatureMatch(
            RequiredFeature existingRequiredFeature,
            RequiredFeature createRequiredFeature) {

        boolean doMatch = false;

        if (existingRequiredFeature == null || createRequiredFeature == null) {
            return doMatch;
        }

        if (existingRequiredFeature.getName()
                .equals(createRequiredFeature.getName())) {

            doMatch =
                    doVersionMatch(existingRequiredFeature.getRange(),
                            createRequiredFeature.getRange());
        }

        return doMatch;
    }

    /**
     * Returns true when both VersionRange match on all attrbutes
     * 
     * @param lhsVersion
     * @param rhsVersion
     * @return
     */
    private static boolean doVersionMatch(VersionRange lhsVersion,
            VersionRange rhsVersion) {
        boolean doMatch = false;
        if (lhsVersion == null || rhsVersion == null) {
            return doMatch;
        }
        if (lhsVersion.getLower().equals(rhsVersion.getLower())
                && lhsVersion.getUpper().equals(rhsVersion.getUpper())
                && lhsVersion.isLowerIncluded() == rhsVersion.isLowerIncluded()
                && lhsVersion.isUpperIncluded() == rhsVersion.isUpperIncluded()) {
            doMatch = true;
        }
        return doMatch;
    }

    public static RequiredBundle createRequireBundleFromIncludedPlugin(
            String plugInId, String generatedPlugInVersoin,
            String qualifierReplacer) {
        RequiredBundle requiredBundle =
                OsgiFactory.eINSTANCE.createRequiredBundle();
        requiredBundle.setName(plugInId);
        VersionRange versionRange = OsgiFactory.eINSTANCE.createVersionRange();
        String includedPluginVersion = generatedPlugInVersoin;
        String lowerPluginVersion =
                PluginManifestHelper
                        .getUpdatedBundleVersion(includedPluginVersion,
                                qualifierReplacer);
        // lower range
        versionRange.setLower(lowerPluginVersion);
        versionRange.setLowerIncluded(Boolean.TRUE);
        // upper range
        versionRange.setUpper(lowerPluginVersion);
        versionRange.setUpperIncluded(Boolean.TRUE);
        requiredBundle.setRange(versionRange);
        return requiredBundle;
    }

    public static void addRequiredFeaturesForXpdlFile(String xpdlFilePath,
            Requirements requirements, IProject project,
            String qualifierReplacer) {
        // we need to add all the Projects as features which contains BOM
        // referred from the xpdl file
        RequiredFeature createRequiredFeature =
                createRequiredFeature(project, qualifierReplacer);
        boolean alreadyPresent = false;
        EList<RequiredFeature> featureDependencies =
                requirements.getFeatureDependencies();

        for (RequiredFeature requiredFeature : featureDependencies) {

            if (requiredFeature == null) {
                continue;
            }
            if (CDSDependencyUtils.doRequiredFeatureMatch(requiredFeature,
                    createRequiredFeature)) {
                alreadyPresent = true;
                break;
            }
        }

        if (!alreadyPresent && null != createRequiredFeature) {

            requirements.getFeatureDependencies().add(createRequiredFeature);
        }
        // XPD-553 Set<IProject> externalProjectsWithBOM =
        // CustomFeatureUtils
        // .getExternalBOMProjectsReferredFromXpdl(xpdlFilePath,
        // project);
        // addExternalBomProductFeatureDependencies(externalProjectsWithBOM,
        // requirements.getFeatureDependencies(),
        // qualifierReplacer);
    }

    /**
     * Adds CustomFeature file entry (if present) to the requirements object
     * 
     * @param requirements
     * @param project
     * @param qualifierReplacer
     */
    public static void addRequiredFeatures(Requirements requirements,
            IProject project, String qualifierReplacer) {

        RequiredFeature createRequiredFeature =
                createRequiredFeature(project, qualifierReplacer);
        boolean alreadyPresent = false;
        EList<RequiredFeature> featureDependencies =
                requirements.getFeatureDependencies();

        for (RequiredFeature requiredFeature : featureDependencies) {

            if (requiredFeature == null) {
                continue;
            }
            if (CDSDependencyUtils.doRequiredFeatureMatch(requiredFeature,
                    createRequiredFeature)) {
                alreadyPresent = true;
                break;
            }
        }

        if (!alreadyPresent && null != createRequiredFeature) {

            featureDependencies.add(createRequiredFeature);
        }
    }

    /**
     * Adds CustomFeature file entry (if present) to the requirements object
     * 
     * @param requirements
     * @param project
     * @param qualifierReplacer
     */
    public static void addRequiredFeatures(Requirements requirements,
            IProject project, Set<CustomFeatureEnum> custFeatures,
            String qualifierReplacer) {

        if (custFeatures == null) {
            addRequiredFeatures(requirements, project, qualifierReplacer);
        } else {
            for (CustomFeatureEnum custFeature : custFeatures) {
                RequiredFeature createRequiredFeature =
                        createRequiredFeature(project,
                                qualifierReplacer,
                                custFeature);
                if (createRequiredFeature != null) {
                    requirements.getFeatureDependencies()
                            .add(createRequiredFeature);
                }
            }

        }

    }

    /**
     * Adds CustomFeature file entry (if present) for referenced projects to the
     * requirements object
     * 
     * @param requirements
     * @param project
     * @param qualifierReplacer
     */
    public static void addCustomFeatureForReferencedProjects(
            Requirements requirements, IProject project,
            String qualifierReplacer) {
        // Add product feature dependencies of referenced projects
        ResType[] resTypeArr = new ResType[1];
        resTypeArr[0] = ResType.XPDL;
        Set<IProject> externalProjectsWithBOM =
                CDSCustomFeatureUtils.getExternalProjectsWithBOM(project,
                        resTypeArr);
        addExternalBomProductFeatureDependencies(externalProjectsWithBOM,
                requirements.getFeatureDependencies(),
                qualifierReplacer);
    }

    /**
     * Returns instance of RequiredFeature if a Custom feature file exists on
     * Staging area. If not custom feature file present then returns null.
     * 
     * @param project
     * @param qualifierReplacer
     * @return
     */
    private static RequiredFeature createRequiredFeature(IProject project,
            String qualifierReplacer) {
        return createRequiredFeature(project, qualifierReplacer, null);
    }

    /**
     * Returns instance of RequiredFeature if a Custom feature file exists on
     * Staging area. If not custom feature file present then returns null.
     * 
     * @param project
     * @param qualifierReplacer
     * @return
     */
    private static RequiredFeature createRequiredFeature(IProject project,
            String qualifierReplacer, CustomFeatureEnum custFeatureEnum) {

        IResource customFeature =
                CDSCustomFeatureUtils.getStagingCustomFeatureFile(project
                        .getName(), custFeatureEnum);

        if (customFeature != null && customFeature.isAccessible()) {
            RequiredFeature pfd = OsgiFactory.eINSTANCE.createRequiredFeature();
            String featureSuffix =
                    (custFeatureEnum != null) ? custFeatureEnum
                            .getFeatureSuffix() : null;
            pfd.setName(N2PENamingUtils.getCustomFeatureId(project,
                    featureSuffix));
            pfd.setRange(getVersionRangeForCustomFeature(project,
                    qualifierReplacer));
            return pfd;
        }
        return null;
    }

    private static void addExternalBomProductFeatureDependencies(
            Set<IProject> externalProjectsWithBOM,
            List<RequiredFeature> featureDependencies, String qualifierReplacer) {
        if (externalProjectsWithBOM != null
                && !externalProjectsWithBOM.isEmpty()) {
            for (IProject referencedProject : externalProjectsWithBOM) {
                if (referencedProject != null) {
                    RequiredFeature createRequiredFeature =
                            createRequiredFeature(referencedProject,
                                    qualifierReplacer);
                    if (createRequiredFeature != null) {
                        featureDependencies.add(createRequiredFeature);
                    }
                }
            }
        }
    }

    /**
     * 
     * @param project
     * @param qualifierReplacer
     * @param qualifierReplacer
     * @return
     */
    private static VersionRange getVersionRangeForCustomFeature(
            IProject project, String qualifierReplacer) {

        VersionRange versionRange = OsgiFactory.eINSTANCE.createVersionRange();
        String versionNumber = CDSUtils.getCustomFeatureVersion(project);

        String lowerRangeValue =
                PluginManifestHelper.getUpdatedBundleVersion(versionNumber,
                        qualifierReplacer);
        versionRange.setLower(lowerRangeValue);
        versionRange.setLowerIncluded(true);
        // String upperRangeValue =
        // PluginManifestHelper.getFeatureUpperRangeValue(versionNumber,
        // qualifierReplacer);
        versionRange.setUpper(lowerRangeValue);
        versionRange.setUpperIncluded(true);

        return versionRange;
    }

}
