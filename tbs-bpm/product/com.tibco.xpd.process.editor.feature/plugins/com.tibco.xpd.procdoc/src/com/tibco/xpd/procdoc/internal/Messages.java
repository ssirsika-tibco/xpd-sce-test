/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.procdoc.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.procdoc.internal.messages"; //$NON-NLS-1$

    private Messages() {
    }

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    public static String ExportWizard_CantExport_title;

    public static String ExportWizard_DuplicateIds_longdesc;

    public static String ExportWizard_ExportConvertingProgressLeader_label;

    public static String ExportWizard_exportFolder_message;

    public static String ExportWizard_message;

    public static String ExportWizard_title;

    public static String ProcDoc_FailedToCreateFolder;

    public static String ProcDoc_UnableToCreateFolder;

    public static String ImageCreator_GeneratingProcessImagesMonitorMsg;

    public static String ProcessModelFolderLabel;

    public static String ImageCreator_SavingProcessImagesMonitorMsg;

    public static String ProcDocOption_ShowTokenName_label;

    public static String ProcDocOptionsPage_DataFIelds_label;

    public static String ProcDocOptionsPage_DiagramImages_label;

    public static String ProcDocOptionsPage_ExtAttrs_label;

    public static String ProcDocOptionsPage_FormalParams_label;

    public static String ProcDocOptionsPage_Implementations_label;

    public static String ProcDocOptionsPage_ActivityInterfaces_label;

    public static String ProcDocOptionsPage_OptionalOut_label;

    public static String ProcDocOptionsPage_Participants_label;

    public static String ProcDocOptionsPage_RestoreDefault_label;

    public static String ProcDocOptionsPage_SaveDefault_label;

    public static String ProcDocOptionsPage_SeqFlow_label;

    public static String ProcDocOptionsPage_StoredPref_label;

    public static String ProcDocOptionsPage_TypeDeclarations_label;

    public static String ProcDocOptionsPage_WizardPage_description;

    public static String ProcDocOptionsPage_WizardPage_title;

    public static String ProcDocPackageInfo_CaseReference_label;

    public static String ProcDocPackageInfo_DuplicateIdPackages_longdesc;

    public static String ProcDocPackageInfo_PageflowEngineDeploymentTarget_label;

    public static String ProcDocPackageInfo_ProcessEngineDeploymentTarget_label;

    public static String ResourceCopier_CreatingStylesheetMonitorMsg;

    public static String XPDLDocXsltGen_NoOutputFileFound;

    public static String XPDLDocXsltGen_ProcessInterfaceModelContainer_title;

    public static String XPDLDocXsltGen_ProcessModelContainer_title;
}