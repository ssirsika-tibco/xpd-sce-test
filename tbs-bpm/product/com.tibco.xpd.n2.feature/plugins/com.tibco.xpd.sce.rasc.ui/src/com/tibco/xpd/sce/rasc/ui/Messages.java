/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.rasc.ui;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author nwilson
 * @since 6 Mar 2019
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.sce.rasc.ui.messages"; //$NON-NLS-1$

    public static String ExportProgressMonitorDialog_ExportError;

    public static String ExportProgressMonitorDialog_LaunchAdminButton;

    public static String ExportProgressMonitorDialog_LaunchError;

    public static String ExportProgressMonitorDialog_OKButton;

    public static String ExportProgressMonitorDialog_ProjectColumn;

    public static String ExportProgressMonitorDialog_StatusColumn;

    public static String ExportProgressMonitorDialog_Title;

    public static String RascExportOperation_CompleteStatus;

    public static String RascExportOperation_ExportingStatus;

    public static String RascExportOperation_ProgressTitle;

    public static String RascExportOperation_ValidatingStatus;

    public static String RascExportOperation_ValidStatus;

    public static String RascExportProjectSelectionPage_NoProjectSelectedError;
    public static String RascExportWizard_Description;

    public static String RascExportWizard_Title;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
