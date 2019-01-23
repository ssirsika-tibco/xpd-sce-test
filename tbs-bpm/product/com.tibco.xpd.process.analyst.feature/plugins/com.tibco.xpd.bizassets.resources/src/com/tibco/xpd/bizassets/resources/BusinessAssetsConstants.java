/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources;

import java.util.regex.Pattern;

public class BusinessAssetsConstants {
    // public static final String BUSINESS_ASSETS_MODEL = "";

    public static final String BIZ_ASSETS_SPECIAL_FOLDER_KIND = "bizassets"; //$NON-NLS-1$

    /** Business assets v3 asset marker. */
    public static final String BUSINESS_ASSETS_V3 =
            "com.tibco.xpd.asset.businessAssetsV3"; //$NON-NLS-1$

    public static final String IMG_FOLDER = "icons/FolderIcon.png"; //$NON-NLS-1$

    public static final String IMG_WIZARD_FOLDER =
            "icons/wizards/Business Assets nxn n p.png"; //$NON-NLS-1$

    public static final String IMG_REFRESH_ENABLED =
            "icons/full/elcl16/refresh_nav.gif"; //$NON-NLS-1$

    public static final String IMG_REFRESH_DISABLED =
            "icons/full/dlcl16/refresh_nav.gif"; //$NON-NLS-1$

    public static final String[] IMAGES =
            new String[] { IMG_FOLDER, IMG_WIZARD_FOLDER, IMG_REFRESH_ENABLED,
                    IMG_REFRESH_DISABLED };

    public static final String ADHOC_FOLDER = "Ad-Hoc Assets"; //$NON-NLS-1$

    public static final String BUSINESSASSETS_FOLDER = "Business Assets"; //$NON-NLS-1$

    public static final String PRINCE2_FOLDER = "Prince2"; //$NON-NLS-1$

    public static final String MARKER_FILE = "~MARKER.txt"; //$NON-NLS-1$

    public static final String PRINCE2_TEMPLATE_JAR_LOCATION =
            "/templates/Prince2.jar"; //$NON-NLS-1$

    public static final String PROJECT_FILE = "Project File"; //$NON-NLS-1$

    public static final String QUALITY_FILE = "Quality File"; //$NON-NLS-1$

    public static final String STAGE_FILE = "Stage File"; //$NON-NLS-1$

    public static final String CORRESPONDENCE = "Correspondence"; //$NON-NLS-1$

    public static final String IN = "In"; //$NON-NLS-1$

    public static final String OUT = "Out"; //$NON-NLS-1$

    public static final String STAGE_FILE_INIT = STAGE_FILE + "1"; //$NON-NLS-1$

    public static final String STAGE_FILE_REGEX = STAGE_FILE + "[\\d]*"; //$NON-NLS-1$

    public static final Pattern STAGE_PATTERN =
            Pattern.compile(STAGE_FILE_REGEX);

    public static final String FIRST_PART_OF_FILE_NAME =
            "[\\-\\s\\w\\(\\)\\[\\]\\}\\{]*"; //$NON-NLS-1$
}
