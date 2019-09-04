/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.internal;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author nwilson
 * @since 6 Mar 2019
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.rasc.ui.internal.messages"; //$NON-NLS-1$

    public static String AdminUrlPreferencePage_ShowAdminUrlDialog;

    public static String AdminUrlPropertyDialog_HideDialogCheckbox;

    public static String AdminUrlPropertyDialog_InfoMessage;

    public static String AdminUrlPropertyDialog_Message;

    public static String AdminUrlPropertyPanel_BaseUrlLabel;

    public static String AdminUrlPropertyPanel_DefaultUrlWarning;

    public static String LifecycleActionProvider_CreateDraftMenuLabel;

    public static String LifecycleActionProvider_DeploymentMenuLabel;

    public static String LifecycleActionProvider_GenerateDraftMenuLabel;

    public static String LifecycleActionProvider_GenerateProductionMenuLabel;

    public static String LifecycleActionProvider_LockForProductionMenuLabel;

    public static String LockedForProductionLabelDecorator_DecoratorText;

    public static String LockedForProductionLabelDecorator_ReadOnlyDecoratorText;

    public static String LockForProductionAction_invalidProjectMessage;

    public static String LockForProductionAction_invalidProjectTtitle;

    public static String OverwriteException_FileMessage;

    public static String ProgressBarMonitor_ExportComplete;

    public static String RascExportStatusPage_ExportError;

    public static String RascExportStatusPage_ExportingArtifacts_status;

    public static String RascExportStatusPage_ProblemsFinish_status;

    public static String RascExportStatusPage_ProjectColumn;

    public static String RascExportStatusPage_RascExportError;

    public static String RascExportStatusPage_RascValidationError;

    public static String RascExportStatusPage_StatusColumn;

    public static String RascExportOperation_CompleteStatus;

    public static String RascExportOperation_ErrorStatus;

    public static String RascExportOperation_ExportingStatus;

    public static String RascExportOperation_FolderErrorStatus;

    public static String RascExportOperation_NoContentStatus;

    public static String RascExportOperation_OverwriteDialogTitle;

    public static String RascExportOperation_ProgressTitle;

    public static String RascExportOperation_ValidatingStatus;

    public static String RascExportOperation_ValidationError;

    public static String RascExportOperation_ValidStatus;

    public static String RascExportProjectSelectionPage_CyclicError;

    public static String RascExportProjectSelectionPage_NoProjectSelectedError;

    public static String RascExportProjectSelectionPage_SelectRequired;

    public static String RascExportWizard_AdminPageError;

    public static String RascExportWizard_CloseButton;

    public static String RascGenerateDraftRascWizard_Description;

    public static String RascGenerateProductionRascWizard_Description;

    public static String RascExportWizard_ExportButton;

    public static String RascExportWizard_LaunchButton;

    public static String RascGenerateDraftRascWizard_Title;

    public static String RascGenerateProductionRascWizard_Title;

    public static String ReadOnlyResourceChangeListener_ChangeDetectedMessage;

    public static String ReadOnlyResourceChangeListener_ChangeDetectedTitle;

    public static String ReadOnlyResourceChangeListener_CreatingDraftJob;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
