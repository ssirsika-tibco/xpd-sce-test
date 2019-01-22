/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.core.help.internal;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author jarciuch
 * @since 7 Aug 2014
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.core.help.internal.messages"; //$NON-NLS-1$

    public static String DefineHelpCommands_contentHelpPostfix_label;

    public static String DefineHelpCommands_downloadOfflineHelp_label;

    public static String DefineHelpCommands_helpCommandCategory_desc;

    public static String DefineHelpCommands_helpCommandCategory_name;

    public static String DefineHelpCommands_productHelpPagePostfix_label;

    public static String DocFileUtils_DocDownloadFailed_message;

    public static String DocFileUtils_DocExtractionProblem_message;

    public static String DocFileUtils_DocServerNotFound_message;

    public static String DocFileUtils_downloadingDocs_label;

    public static String DocFileUtils_downloadingDocs_message;

    public static String DocFileUtils_extractingFiles_message;

    public static String DocFileUtils_ServerNotFound_message;

    public static String XpdHelpContentControl_available_message;

    public static String XpdHelpContentControl_Details_label;

    public static String XpdHelpContentControl_docDownload_label;

    public static String XpdHelpContentControl_download_button;

    public static String XpdHelpContentControl_downloadUrl_label;

    public static String XpdHelpContentControl_helpContentUrl_label;

    public static String XpdHelpContentControl_nameColumn_header;

    public static String XpdHelpContentControl_notAvailable_message;

    public static String XpdHelpContentControl_offline_header;

    public static String XpdHelpContentControl_offlineFolder_label;

    public static String XpdHelpContentControl_productHelpPage_label;

    public static String XpdHelpContentControl_productName_label;

    public static String XpdHelpContentControl_Refresh_button;

    public static String XpdHelpContentControl_version_label;

    public static String XpdHelpContentControl_versionColumn_header;

    public static String XpdHelpContentControl_warning_message;

    public static String XpdHelpContentControl_warning_title;

    public static String XpdHelpContentPreferencePage_baseOfflineDocFolder_label;

    public static String XpdHelpContentPreferencePage_contentAccessStrategy_label;

    public static String XpdHelpContentPreferencePage_existingFileError_message;

    public static String XpdHelpContentPreferencePage_folderDoesntExist_message;

    public static String XpdHelpContentPreferencePage_invalidLocation_message;

    public static String XpdHelpContentPreferencePage_location_label;

    public static String XpdHelpContentPreferencePage_locationCantBeOpened_message;

    public static String XpdHelpContentPreferencePage_openingLocation_message;

    public static String XpdHelpPreferencePage_browserLink_message;

    public static String XpdHelpPreferencePage_helpDisplayInfo_message;

    public static String XpdHelpPreferencePage_openModes_label;

    public static String XpdHelpPreferencePage_openProductHelpContent_label;

    public static String XpdHelpPreferencePage_openProductHelpPage_label;

    public static String XpdHelpPreferences_AlwaysOnlineStrategy_label;

    public static String XpdHelpPreferences_openInABrowser_label;

    public static String XpdHelpPreferences_openInAView_label;

    public static String XpdHelpPreferences_preferOfflineStrategy_label;

    public static String XpdHelpService_couldNotRemoveFolder_message;

    public static String XpdHelpService_downloadingOfflineHelp_message;

    public static String XpdHelpService_removingOfflineFolder_message;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
