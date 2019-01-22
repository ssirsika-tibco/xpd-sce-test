/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.n2.daa.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.osgi.framework.Version;

import com.tibco.amf.dependency.osgi.OsgiFactory;
import com.tibco.amf.dependency.osgi.RequiredFeature;
import com.tibco.amf.dependency.osgi.VersionRange;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.daa.internal.util.DAANamingUtils;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.n2.daa.Activator;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Util class to help naming and versioning the different features
 * 
 * @author mtorres
 * 
 */
public final class N2PENamingUtils {

    private static final Logger LOG = Activator.getDefault().getLogger();

    /**
     * upper version range for the amx bpm model product feature
     */
    private static final String UPPER_RANGE = "2.0.0"; //$NON-NLS-1$

    /**
     * lower version range for the amx bpm model product feature
     */
    private static final String LOWER_RANGE = "1.0.0"; //$NON-NLS-1$

    /**
     * amx bpm model product feature dependency id
     */
    private static final String AMX_BPM_IT_MODEL_PRODUCT_FEATURE =
            "com.tibco.amx.bpm.it.model.product.feature"; //$NON-NLS-1$

    private static final String AMX_BPM_COMMON_PRODUCT_FEATURE =
            "com.tibco.amx.bpm.common.product.feature"; //$NON-NLS-1$

    private static final String DEFAULT_PROJECT_ID =
            "com.example.customfeature"; //$NON-NLS-1$

    private static final String CUSTOM_FEATURE_EXTENSION = ".customfeature"; //$NON-NLS-1$

    public static String COMPOSITE_OUTPUTFOLDER_NAME = ".bpm";

    public static final String COMPOSITE_MODULES_OUTPUT_KIND =
            "compositeModulesOutput"; //$NON-NLS-1$

    public static final String N2SOURCE_NATURE =
            "com.tibco.xpd.wm.resources.ui.wmNature"; //$NON-NLS-1$

    public static final String WEB_APP_REQUIRED_VERSION = LOWER_RANGE;

    public static final String WEB_APP_EXTENSIONPOINT_ID =
            "com.tibco.forms.resources.extension-point";

    public static final String BOM_SPECIALFOLDER_KIND = "bom"; //$NON-NLS-1$

    public static final String PROCESS_SPECIALFOLDER_KIND = "processes"; //$NON-NLS-1$

    public static final String XPDL_FILE_EXTENSION = "xpdl"; //$NON-NLS-1$

    public static String WP_FILENAME = "presentation.wp";//$NON-NLS-1$

    public static String DE_FILEEXTENSION = "de";//$NON-NLS-1$

    public static String BP_OUTPUTFOLDER_NAME = "process"; //$NON-NLS-1$

    public static String PF_OUTPUTFOLDER_NAME = "pageflow"; //$NON-NLS-1$

    public static final String DAA_ERROR_MARKER_ID = "DAA_ERROR_MARKER_ID"; //$NON-NLS-1$

    public static final String DAA_PROBLEM_MARKER_TYPE =
            "com.tibco.xpd.n2.resources.daaValidationMarker"; //$NON-NLS-1$

    public static final String BPM_COMPOSITE_CONTRIBUTION_CONTEXT =
            "com.tibco.xpd.amx.bpm"; //$NON-NLS-1$

    /**
     * Name of the folder in the DAA export staging area with staged features
     * ready for packaging by PackageParticipant2.
     */
    public static final String CUSTOMFEATURES_FOLDER_NAME = "customfeatures"; //$NON-NLS-1$

    /** Folder name of a folder containing features. */
    public static final String FEATURES_FOLDER = "features"; //$NON-NLS-1$

    /** Folder name of a folder containing plug-ins. */
    public static final String PLUGINS_FOLDER = "plugins"; //$NON-NLS-1$

    /** BOM jars cache folder. */
    public static final String BOM_JARS_CACHE_FOLDER = ".bomjars"; //$NON-NLS-1$

    /** Precompile BOM jars cache folder. */
    public static final String PRECOMPILE_BOM_JARS_CACHE_FOLDER = "bomjars"; //$NON-NLS-1$

    /** Studio BPM cache project, */
    public static final String BS_CACHE_PROJECT = ".bsCache"; //$NON-NLS-1$

    public static IFolder getModuleOutputFolder(final IProject project,
            boolean create) {
        SpecialFolder sf;
        if (create) {
            sf =
                    SpecialFolderUtil.getCreateSpecialFolderOfKind(project,
                            COMPOSITE_MODULES_OUTPUT_KIND,
                            COMPOSITE_OUTPUTFOLDER_NAME);
        } else {
            sf =
                    SpecialFolderUtil.getSpecialFolderOfKind(project,
                            COMPOSITE_MODULES_OUTPUT_KIND);
        }

        return sf == null ? null : sf.getFolder();
    }

    // TODO change this so we don't load the working copy
    public static List<String> getXpdlFilePaths(IProject project) {
        if (project != null) {
            List<IResource> xpdlResources =
                    SpecialFolderUtil
                            .getAllDeepResourcesInSpecialFolderOfKind(project,
                                    PROCESS_SPECIALFOLDER_KIND,
                                    XPDL_FILE_EXTENSION,
                                    false);
            if (xpdlResources != null && !xpdlResources.isEmpty()) {
                List<String> xpdlFilePaths = new ArrayList<String>();
                for (IResource resource : xpdlResources) {
                    if (resource != null) {
                        WorkingCopy wc =
                                XpdResourcesPlugin.getDefault()
                                        .getWorkingCopy(resource);
                        if (wc != null) {
                            EObject rootElement = wc.getRootElement();
                            if (rootElement != null) {
                                String xpdlFilePathURI =
                                        ProcessUIUtil.getURIString(rootElement,
                                                false);
                                if (xpdlFilePathURI != null) {
                                    xpdlFilePaths.add(xpdlFilePathURI);
                                }
                            }
                        }
                    }
                }
                return xpdlFilePaths;
            }
        }
        return Collections.emptyList();
    }

    public static String getCustomFeatureId(IProject project) {
        return getCustomFeatureId(project, null);
    }

    /**
     * Overloaded version takes custom feature's suffix description and ensures
     * it is used in the generation of the feature's ID. The 1 arg (IProject)
     * version of method not removed as its package (com.tibco.xpd.n2.daa) gets
     * exported
     * 
     * @param project
     * @param customFeatureSuffix
     * @return ID of custom feature
     */
    public static String getCustomFeatureId(IProject project,
            String customFeatureSuffix) {

        String customFeatureId = DEFAULT_PROJECT_ID;
        if (project != null) {
            String projectId = CompositeUtil.getProjectId(project);
            if (projectId != null) {
                /**
                 * JA: ".model.bds" is added as a fallback. This name obtaining
                 * should be refactored.
                 */
                customFeatureId =
                        (customFeatureSuffix == null) ? projectId
                                + ".model.bds" : projectId
                                + customFeatureSuffix;
            }
        }
        return customFeatureId;
    }

    public static IMarker[] getAllDaaErrorMarkers(IResource resource, int depth)
            throws CoreException {
        if (resource != null) {
            try {
                return resource
                        .findMarkers(N2PENamingUtils.DAA_PROBLEM_MARKER_TYPE,
                                true,
                                depth);
            } catch (CoreException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return new IMarker[0];
    }

    public static void cleanDaaErrorMarkers(IResource resource,
            String errorMarkerId) throws CoreException {
        if (resource != null && errorMarkerId != null) {
            try {
                IMarker[] markers =
                        resource.findMarkers(N2PENamingUtils.DAA_PROBLEM_MARKER_TYPE,
                                true,
                                IResource.DEPTH_INFINITE);
                if (markers != null) {
                    for (IMarker marker : markers) {
                        if (marker != null) {
                            String markerId =
                                    (String) marker
                                            .getAttribute(N2Utils.N2_PROBLEM_MARKER_ID_KEY);
                            if (markerId != null
                                    && markerId.equals(errorMarkerId)) {
                                try {
                                    marker.delete();
                                } catch (CoreException e) {
                                    e.printStackTrace();
                                    throw e;
                                }
                            }
                        }
                    }
                }
            } catch (CoreException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    public static void addDaaErrorMarker(IResource resource, String msg,
            String errorMarkerId) throws CoreException {
        N2PENamingUtils.addDaaErrorMarker(resource,
                msg,
                errorMarkerId,
                Collections.emptyList());
    }

    public static void addDaaErrorMarker(IResource resource, String msg,
            String errorMarkerId, List<Object> args) throws CoreException {
        try {
            N2Utils.addN2ErrorMarker(resource,
                    msg,
                    N2PENamingUtils.DAA_PROBLEM_MARKER_TYPE,
                    errorMarkerId,
                    args);
        } catch (CoreException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Name of the custom feature file (containing serialized custom feature
     * model) for a project.
     * 
     * @param project
     *            context project.
     * @return name of the custom feature file.
     */
    public static String getCustomFeatureFileName(IProject project) {
        return getCustomFeatureFileName(project, null);
    }

    /**
     * Name of the custom feature file (containing serialized custom feature
     * model) for a project.
     * 
     * @param project
     *            context project.
     * @param customFeatureSuffix
     *            suffix supplied for the custom feature
     * @return name of the custom feature file.
     */
    public static String getCustomFeatureFileName(IProject project,
            String customFeatureSuffix) {
        String customFeatureId =
                getCustomFeatureId(project, customFeatureSuffix);
        return getCustomFeatureFileName(customFeatureId);
    }

    /**
     * Name of the custom feature file (containing serialized custom feature
     * model) for a project.
     * 
     * @param customFeatureId
     * @return name of the custom feature file.
     */
    public static String getCustomFeatureFileName(String customFeatureId) {
        return customFeatureId + CUSTOM_FEATURE_EXTENSION;
    }

    /**
     * 
     * @param project
     * @param timeStamp
     * @return
     */
    public static String getDAAUpdatedFileName(IProject project,
            String timeStamp) {
        String fileName = ProjectUtil.getProjectId(project);
        String replacedProjectVersion =
                PluginManifestHelper
                        .replaceProjectVersionQualifierWithQualifierReplacer(project,
                                timeStamp);
        return fileName + "_" + replacedProjectVersion;
    }

    private static String getDAAUpdatedFileName(String projectPrefix,
            String projectVersion, String timeStamp) {
        String fileName = null;
        if (timeStamp == null || timeStamp.trim().length() < 1) {
            fileName = projectPrefix + "_" + projectVersion;
        } else {
            Version pVersion = new Version(projectVersion);
            String strVersion =
                    pVersion.getMajor() + "." + pVersion.getMinor() + "."
                            + pVersion.getMicro() + "." + timeStamp;
            fileName = projectPrefix + "_" + strVersion;
        }
        return fileName;
    }

    /**
     * Returns the name of the application with which the DAA should be deployed
     * to n2 runtime
     * 
     * @param project
     * @return
     */
    public static String getDAAApplicationName(IProject project) {
        String projectName = project.getName();
        String projectVersion = ProjectUtil.getProjectVersion(project);
        String timeStampOnProject =
                DAANamingUtils.getTimeStampOnProject(project);
        String updatedFileName =
                getDAAUpdatedFileName(projectName,
                        projectVersion,
                        timeStampOnProject);
        return updatedFileName;
    }

    /**
     * Hack to delete custom feature file when BOM changes (resulting in
     * rebuilding of EMF Project) otherwise there will be a problem marker on
     * the custom feature file resulting of DAA not getting generated
     */
    public static void deleteFileUnderBpmFolder(IProject studioProject) {

        IFolder moduleOutputFolder =
                N2PENamingUtils.getModuleOutputFolder(studioProject, false);

        if (moduleOutputFolder != null && moduleOutputFolder.isAccessible()) {

            /*
             * in case of global data there will be two custom feature files
             * (*.model.bds.customfeature and *.da.bds.customfeature). go thru
             * all the files under .bpm folder to get all custom feature files
             * and remove them
             */
            List<IResource> customFeatureFilesToDelete =
                    new ArrayList<IResource>();
            try {
                IResource[] members = moduleOutputFolder.members();

                for (IResource member : members) {

                    if (member instanceof IFile) {

                        IFile file = (IFile) member;

                        if (file.getName().endsWith(CUSTOM_FEATURE_EXTENSION)) {
                            customFeatureFilesToDelete.add(file);
                        }

                    }
                }
            } catch (CoreException e1) {
            }

            for (IResource resource : customFeatureFilesToDelete) {

                IPath filePath =
                        moduleOutputFolder.getFullPath()
                                .append(resource.getName());
                IResource fileToDelete =
                        studioProject.getWorkspace().getRoot()
                                .getFile(filePath);

                if (fileToDelete != null && fileToDelete.isAccessible()) {

                    try {

                        fileToDelete.refreshLocal(IResource.DEPTH_INFINITE,
                                new NullProgressMonitor());
                        fileToDelete.delete(Boolean.TRUE,
                                new NullProgressMonitor());
                    } catch (CoreException e) {
                    }
                }
            }

        }

    }

    public static RequiredFeature getBPMITModelPF() {
        RequiredFeature pfd = OsgiFactory.eINSTANCE.createRequiredFeature();
        pfd.setName(AMX_BPM_IT_MODEL_PRODUCT_FEATURE);
        VersionRange versionRange = OsgiFactory.eINSTANCE.createVersionRange();
        versionRange.setLower(LOWER_RANGE);
        versionRange.setLowerIncluded(true);
        versionRange.setUpper(UPPER_RANGE);
        versionRange.setUpperIncluded(false);
        pfd.setRange(versionRange);
        return pfd;
    }

    public static RequiredFeature getBPMCommonModelPF() {
        RequiredFeature pfd = OsgiFactory.eINSTANCE.createRequiredFeature();
        pfd.setName(AMX_BPM_COMMON_PRODUCT_FEATURE);
        VersionRange versionRange = OsgiFactory.eINSTANCE.createVersionRange();
        versionRange.setLower(LOWER_RANGE);
        versionRange.setLowerIncluded(true);
        versionRange.setUpper(UPPER_RANGE);
        versionRange.setUpperIncluded(false);
        pfd.setRange(versionRange);
        return pfd;
    }

    /**
     * Gets file relative to DAA generation staging area.
     * 
     * @param project
     *            the context project.
     * @param stageFolderRelativePath
     *            path relative to stage area folder.
     * @return
     */
    public static IFile getFileFromStagingArea(IProject project,
            IPath stageFolderRelativePath) {
        IFolder compositeOut = getModuleOutputFolder(project, false);
        return compositeOut.getFile(stageFolderRelativePath);
    }

}