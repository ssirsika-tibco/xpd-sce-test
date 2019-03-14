/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.cdm;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * CDM utilities.
 *
 * @author jarciuch
 * @since 12 Mar 2019
 */
public class CdmUtil {

    /** Default project relative CDM output folder. */
    public static final String DEFAULT_OUT_MODULE_FOLDER = ".cdmOut"; //$NON-NLS-1$

    /**
     * Kind of special folder used for output of deployable CDM model modules.
     */
    public static final String CDM_MODULES_OUTPUT_KIND = "cdmModulesOutput"; //$NON-NLS-1$

    /**
     * Gets eclipse IFolder which is marked as the CDM modules output special
     * folder for the project. If the special folder is not set (and project has
     * required nature) the default one will be created and returned.
     * 
     * @param project
     *            the eclipse project.
     * @param create
     *            true if the output folder should be created if don't exist.
     * @return eclipse IFolder which is marked as the CDM modules output special
     *         folder. If special folder doesn't exist (and project has required
     *         nature) and the create parameter is true, the default folder will
     *         be created otherwise null will be returned.
     */
    public static IFolder getModuleOutputFolder(final IProject project,
            boolean create) {
        SpecialFolder sf;
        if (create) {
            sf =
                    SpecialFolderUtil.getCreateSpecialFolderOfKind(project,
                            CDM_MODULES_OUTPUT_KIND,
                            DEFAULT_OUT_MODULE_FOLDER);
        } else {
            sf =
                    SpecialFolderUtil.getSpecialFolderOfKind(project,
                            CDM_MODULES_OUTPUT_KIND);
        }

        return sf == null ? null : sf.getFolder();
    }
    
}
