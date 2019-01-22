/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.transform.de.utils;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Utility class managing special folders used by organization model deployment.
 * <p>
 * <i>Created: 03 Oct 2008</i>
 * 
 * @author glewis
 * 
 */
public final class DESpecialFoldersUtil {

    /** Default project relative organization model output folder. */
    public static final String DEFAULT_OUT_MODULE_FOLDER = ".deModulesOutput"; //$NON-NLS-1$

    /**
     * Kind of special folder used for output of deployable organization model
     * modules.
     */
    public static final String DE_MODULES_OUTPUT_KIND = "deModulesOutput"; //$NON-NLS-1$

    /**
     * Gets eclipse IFolder which is marked as the Organization Model modules
     * output special folder for the project. If the special folder is not set
     * (and project has required nature) the default one will be created and
     * returned.
     * 
     * @param project
     *            the eclipse project.
     * @param create
     *            true if the output folder should be created if don't exist.
     * @return eclipse IFolder which is marked as the Organization Model modules
     *         output special folder. If special folder doesn't exist (and
     *         project has required nature) and the create parameter is true,
     *         the default folder will be created otherwise null will be
     *         returned.
     */
    public static IFolder getModuleOutputFolder(final IProject project,
            boolean create) {
        SpecialFolder sf;
        if (create) {
            sf =
                    SpecialFolderUtil.getCreateSpecialFolderOfKind(project,
                            DE_MODULES_OUTPUT_KIND,
                            DEFAULT_OUT_MODULE_FOLDER);
        } else {
            sf =
                    SpecialFolderUtil.getSpecialFolderOfKind(project,
                            DE_MODULES_OUTPUT_KIND);
        }

        return sf == null ? null : sf.getFolder();
    }
}
