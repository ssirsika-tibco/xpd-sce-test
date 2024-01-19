package com.tibco.xpd.bom.dbimport.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.bom.dbimport.internal.messages"; //$NON-NLS-1$

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }

    public static String BOMFileSelectionPage_FileNameLabel;

    public static String BOMFileSelectionPage_SelectValidFileErrorMessage;

    public static String BOMFileSelectionPage_title;

    public static String BOMFileSelectionPage_shortdesc;

    public static String DestinationFileSelect_ButtonSelect_label;

    public static String DestinationFileSelect_DestFile_label;

    public static String DestinationFileSelect_WindowDesc_shortdesc;

    public static String DestinationFileSelect_WindowTitle_title;

    public static String ImportDb2BomWizard_SelectProfilePage_label;

    public static String ImportDb2BomWizard_WindowTitle_title;

    public static String ImportFromDbAction_ActionLabel_label;

    public static String ProfileSelectImportWizard_ConfirmResourceOverWriteMessage;

    public static String ProfileSelectImportWizard_FileUnfoundErr_shortdesc;

    public static String ProfileSelectImportWizard_ImportDBtoBOM_Label;

    public static String ProfileSelectImportWizard_Monitor_label;

    public static String ProfileSelectImportWizard_InvalidFileNameErr_shortdesc;

    public static String ProfileSelectImportWizard_InvalidFileExtensionErr_shortdesc;

    public static String ProfileSelectImportWizard_ResourceExistsLabel;

    public static String ProfileSelectImportWizard_WizardDesc_shortdesc;

    public static String ProfileSelectImportWizard_WizardPage_title;

    public static String ProfileSelectImportWizard_WizardPageDescription_shortdesc;

    public static String ProfileSelectionPage_CreateProfileItem_label;

    public static String ProfileSelectionPage_Group_label;

    public static String ProfileSelectionPage_UnableToConnectErr_shortdesc;

    public static String ProfileSelectionPage_WindowDesc_shortdesc;

    public static String ProfileSelectionPage_WindowTitle_title;

    public static String ProfileTablesDisplayPage_DeselectAllBtn_label2;

    public static String ProfileTablesDisplayPage_SelectAllBtn_label;

    public static String ProfileTablesDisplayPage_TableInDB_label;

    public static String ProfileTablesDisplayPage_WindowDesc_shortdesc;

    public static String ProfileTablesDisplayPage_WindowTitle_title;

    public static String XtendTransformDB2BOM_RollbackTransactionErr_label;
}