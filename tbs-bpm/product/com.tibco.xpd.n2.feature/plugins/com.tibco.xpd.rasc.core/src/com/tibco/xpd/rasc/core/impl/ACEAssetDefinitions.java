/*
 * Copyright (c) TIBCO Software Inc 2004, 2020. All rights reserved.
 */

package com.tibco.xpd.rasc.core.impl;

/**
 * Sid ACE-4134 Class that picks up various asset definitions constants from assets contributed to ACE projects.
 *
 * NOTE: Unfortunately this repetition of definitions already defined elsewhere (in the asset types plugins them selves)
 * is unavoidable because of the requirement onm {@link RascControllerImpl} to be able to derive a single 'Project-Type'
 * from the assets that exist in the project.
 * 
 * We can't use the original definitions because the plugins that define them also depend (directly/indirectly) on
 * rasc.core plugin and therefore would create a cyclic dependency.
 *
 * @author aallway
 * @since 15 Jul 2020
 */
public class ACEAssetDefinitions {
    public final static String PROCESSES_SPECIAL_FOLDER_KIND = "processes"; //$NON-NLS-1$

    public final static String XPDL_EXTENSION = "xpdl"; //$NON-NLS-1$

    public final static String BOM_SPECIAL_FOLDER_KIND = "bom"; //$NON-NLS-1$

    public final static String BOM_FILE_EXTENSION = "bom"; //$NON-NLS-1$

    public final static String OM_SPECIAL_FOLDER_KIND = "om"; //$NON-NLS-1$

    public final static String OM_FILE_EXTENSION = "om"; //$NON-NLS-1$

    public final static String WLF_SPECIAL_FOLDER_KIND = "wlf"; //$NON-NLS-1$

    public final static String WLF_FILE_EXTENSION = "wlf"; //$NON-NLS-1$

    public final static String GSD_SPECIAL_FOLDER_KIND = "gsd"; //$NON-NLS-1$

    public final static String GSD_FILE_EXTENSION = "gsd"; //$NON-NLS-1$

    public final static String FORMS_SPECIAL_FOLDER_KIND = "forms"; //$NON-NLS-1$

    public final static String FORMS_FILE_EXTENSION = "form"; //$NON-NLS-1$

}
