/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.n2.live.internal;

import org.eclipse.osgi.util.NLS;

/**
 * Live dev message externalisation.
 * 
 * @author nwilson
 * @since 2 Sep 2014
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.n2.live.internal.messages"; //$NON-NLS-1$

    public static String Activator_PerspectiveDialogMessage;

    public static String Activator_PerspectiveDialogTitle;

    public static String Activator_PerspectiveDialogToggle;

    public static String OpenspaceViewConnectionPropertySection_DevServerLabel;

    public static String OpenspaceViewConnectionPropertySection_OpenspaceUrlLabel;

    public static String OpenspaceViewConnectionPropertySection_OpenspaceViewRefreshNote;

    public static String OpenspaceViewHelper_DomainNameForDefaultTemplateURL;

    public static String OpenspaceViewPart_ConfigureWorkManagerURL_message;

    public static String OpenspaceViewPart_CopyUrlToolTip;

    public static String OpenspaceViewPart_BrowserLoadingMessage;

    public static String OpenspaceViewPart_DefaultServer_label;

    public static String OpenspaceViewPart_InvalidUrl;

    public static String OpenspaceViewPart_InvalidWorkManagerURL_message;

    public static String OpenspaceViewPart_LaunchErrorMessage;

    public static String OpenspaceViewPart_LaunchErrorTitle;

    public static String OpenspaceViewPart_LaunchLiveDevWorkManager_btn;

    public static String OpenspaceViewPart_RefreshButtonToolTip;

    public static String OpenspaceViewPart_RelaunchButtonToolTip;

    public static String OpenspaceViewPart_ServerComboLabel;

    public static String OpenspaceViewPart_ServerNotFoundLabel;

    public static String OpenspaceViewPart_SetRULDomainTooltip_message;

    public static String SetOpenspaceUrlOperation_Label;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
