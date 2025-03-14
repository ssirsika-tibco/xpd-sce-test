/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.n2.daa.utils;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.n2.daa.Activator;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
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
            sf = SpecialFolderUtil.getCreateSpecialFolderOfKind(project,
                    COMPOSITE_MODULES_OUTPUT_KIND,
                    COMPOSITE_OUTPUTFOLDER_NAME);
        } else {
            sf = SpecialFolderUtil.getSpecialFolderOfKind(project,
                    COMPOSITE_MODULES_OUTPUT_KIND);
        }

        return sf == null ? null : sf.getFolder();
    }

    public static IMarker[] getAllDaaErrorMarkers(IResource resource, int depth)
            throws CoreException {
        if (resource != null) {
            try {
                return resource.findMarkers(
                        N2PENamingUtils.DAA_PROBLEM_MARKER_TYPE,
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
                IMarker[] markers = resource.findMarkers(
                        N2PENamingUtils.DAA_PROBLEM_MARKER_TYPE,
                        true,
                        IResource.DEPTH_INFINITE);
                if (markers != null) {
                    for (IMarker marker : markers) {
                        if (marker != null) {
                            String markerId = (String) marker.getAttribute(
                                    N2Utils.N2_PROBLEM_MARKER_ID_KEY);
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



}