/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author nwilson
 * @since 6 Mar 2019
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.rasc.ui.messages"; //$NON-NLS-1$

    public static String AdminUrlPropertyDialog_HideDialogCheckbox;

    public static String AdminUrlPropertyDialog_InfoMessage;

    public static String AdminUrlPropertyDialog_Message;

    public static String AdminUrlPropertyDialog_Title;

    public static String AdminUrlPropertyPanel_BaseUrlLabel;

    public static String RascExportStatusPage_ExportError;

    public static String RascExportStatusPage_ExportingArtifacts_status;

    public static String RascExportStatusPage_ProjectColumn;

    public static String RascExportStatusPage_RascExportError;

    public static String RascExportStatusPage_RascValidationError0;

    public static String RascExportStatusPage_StatusColumn;

    public static String RascExportOperation_CompleteStatus;

    public static String RascExportOperation_ErrorStatus;

    public static String RascExportOperation_ExportingStatus;

    public static String RascExportOperation_FolderErrorStatus;

    public static String RascExportOperation_ProgressTitle;

    public static String RascExportOperation_ValidatingStatus;

    public static String RascExportOperation_ValidationError;

    public static String RascExportOperation_ValidStatus;

    public static String RascExportProjectSelectionPage_CyclicError;

    public static String RascExportProjectSelectionPage_NoProjectSelectedError;

    public static String RascExportProjectSelectionPage_SelectRequired;

    public static String RascExportWizard_AdminPageError;

    public static String RascExportWizard_CloseButton;

    public static String RascExportWizard_Description;

    public static String RascExportWizard_ExportButton;

    public static String RascExportWizard_LaunchButton;

    public static String RascExportWizard_Title;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
