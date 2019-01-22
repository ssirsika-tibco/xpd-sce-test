package com.tibco.xpd.bizassets.resources;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.bizassets.resources.messages"; //$NON-NLS-1$

    public static String BusinessAssets_StartingBusinessAssetsCreation_longdesc;

    public static String Prince2Files_MovingPrince2Files_message;

    public static String ProjectSelectPage_enterProjectName_message;

    public static String ProjectSelectPage_noProject_message;

    public static String ProjectSelectPage_prince2Exists_message;

    public static String ProjectSelectPage_project_label;

    public static String ProjectSelectPage_projectClosed_message;

    public static String ProjectSelectPage_projectNameMissing_message;

    public static String ProjectSelectPage_selectProjectDialog_longdesc;

    public static String ProjectSelectPage_selectProjectDialog_title;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
