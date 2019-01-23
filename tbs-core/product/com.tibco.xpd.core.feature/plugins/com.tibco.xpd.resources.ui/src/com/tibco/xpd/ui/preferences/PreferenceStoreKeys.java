/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.ui.preferences;

/**
 * Key holder for preferences.
 * 
 * @author mtorres
 */
public class PreferenceStoreKeys {

    private PreferenceStoreKeys() {
    }

    /**
     * Dont ask to build when changing capabilities
     */
    public static final String DONT_ASK_TO_BUILD_WHEN_CHANGING_CAPABILITIES =
            "DONT_ASK_TO_BUILD_WHEN_CHANGING_CAPABILITIES"; //$NON-NLS-1$    

    /**
     * Dont ask to build when changing destinations
     */
    public static final String DONT_ASK_TO_BUILD_WHEN_CHANGING_DESTINATIONENVIRONMENTS =
            "DONT_ASK_TO_BUILD_WHEN_CHANGING_DESTINATIONENVIRONMENTS"; //$NON-NLS-1$ 

    /**
     * Dont ask to overwrite when exporting documentation
     */
    public static final String DONT_ASK_TO_OVERWRITE_WHEN_EXPORTING_DOCUMENTATION =
            "DONT_ASK_TO_OVERWRITE_WHEN_EXPORTING_DOCUMENTATION"; //$NON-NLS-1$    

    /**
     * Overwrite when exporting documentation value
     */
    public static final String OVERWRITE_WHEN_EXPORTING_DOCUMENTATION_VALUE =
            "OVERWRITE_WHEN_EXPORTING_DOCUMENTATION_VALUE"; //$NON-NLS-1$    

    /**
     * Use custom logo image when exporting documentation value
     */
    public static final String USE_CUSTOM_LOGO_WHEN_EXPORTING_DOCUMENTATION_VALUE =
            "USE_CUSTOM_LOGO_WHEN_EXPORTING_DOCUMENTATION_VALUE"; //$NON-NLS-1$

    /**
     * Logo file path value to be used when exporting documentation
     */
    public static final String LOGO_FILE_PATH_USED_FOR_EXPORTING_DOCUMENTATION_VALUE =
            "LOGO_FILE_PATH_USED_FOR_EXPORTING_DOCUMENTATION_VALUE"; //$NON-NLS-1$

    public static final String PROJECT_SPECIFIC_DOC_NODE =
            "documentationPreferenceNode"; //$NON-NLS-1$  

    public static final String HAS_PROJECT_SPECIFIC_DOC_SETTINGS =
            "hasProjectSpecificDocSettings"; //$NON-NLS-1$

}
