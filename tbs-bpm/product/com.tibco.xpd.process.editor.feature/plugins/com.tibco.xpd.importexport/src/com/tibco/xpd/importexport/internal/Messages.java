package com.tibco.xpd.importexport.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.importexport.internal.messages"; //$NON-NLS-1$

    private Messages() {
    }

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    public static String CustomImportWizard_invalidImportDest_message;

    public static String CustomImportWizard_picker_message;

    public static String CustomImportWizard_picker_title;

    public static String DestPage_DestinationGrpTitle;

    public static String DestPage_FolderNotValid;

    public static String DestPage_FolderRadioBtn;

    public static String DestPage_FolderSelectionMsg;

    public static String DestPage_InstallMessage;

    public static String DestPage_NoPathSelected;

    public static String DestPage_SelectFolder;

    public static String ExportFolder_title;

    public static String MAAFolder_title;

    public static String ExportXPDLWizard_CannotExportDialogTitle;

    public static String ExportXPDLWizard_NoProcessesWithDestinationType;

    public static String ExportXPDLWizard_ProblemWithDestinationType;

    public static String ImportXPDLWizard_destinationError_message;

    public static String ImportXPDLWizard_MigratingToLatestVersion_message;

    public static String ImportXPDLWizard_migrationFailed_message;

    public static String NewPluginGeneratorWizard_createPluginTask_title;

    public static String NewPluginGeneratorWizard_destinationPage_title;

    public static String NewPluginGeneratorWizard_folderNotFound_message;

    public static String NewPluginGeneratorWizard_locationNotFoundDialog_title;

    public static String NewPluginGeneratorWizard_pluginPage_title;

    public static String NewPluginGeneratorWizard_restartFailedDialog_message;

    public static String NewPluginGeneratorWizard_restartFailedDialog_title;

    public static String NewPluginGeneratorWizard_window_title;

    public static String NewPluginGeneratorWizard_wizardTypeExport_title;

    public static String NewPluginGeneratorWizard_wizardTypeImport_title;

    public static String PluginGenerator_pluginExists_message;

    public static String PluginGenerator_pluginExistsDialogTitle;

    public static String PluginGenerator_unableToInsertIntoPlugin_message;

    public static String PluginInfoPage_type_label;

    public static String PluginInfoPage_wizardTypeGroup_label;

    public static String PluginPage_DefaultMsg;

    public static String PluginPage_DefaultPluginName;

    public static String PluginPage_DefaultPluginNameChkBox;

    public static String PluginPage_DefaultPluginVersion;

    public static String PluginPage_IDIsEmpty;

    public static String PluginPage_IdLabel;

    public static String PluginPage_DefaultImportId_notrans;

    public static String PluginPage_DefaultExportId_notrans;

    public static String PluginPage_InvalidID;

    public static String PluginPage_InvalidVersion;

    public static String PluginPage_NameLabel;

    public static String PluginPage_NameNotSet;

    public static String PluginPage_Plugin;

    public static String PluginPage_PluginGrpTitle;

    public static String PluginPage_ProviderLabel;

    public static String PluginPage_DefaultProvider;

    public static String PluginPage_VersionLabel;

    public static String PluginPage_VersionNotSet;

    public static String TreeViewerFilterPage_exportWizard_message;

    public static String TreeViewerFilterPage_exportWizard_title;

    public static String TreeViewerFilterPage_fileFilter_label;

    public static String TreeViewerFilterPage_importWizard_message;

    public static String TreeViewerFilterPage_importWizard_title;

    public static String TreeViewerFilterPage_selectSpecialFolderError_message;

    public static String TreeViewerFilterPage_specialFolderFilter_label;

    public static String WizardInfoPage_category_label;

    public static String WizardInfoPage_categoryGroup_label;

    public static String WizardInfoPage_default_message;

    public static String WizardInfoPage_defaultExportFolder_label;

    public static String WizardInfoPage_description_label;

    public static String WizardInfoPage_dtdFilter_message;

    public static String WizardInfoPage_exportWizard_title;

    public static String WizardInfoPage_default_title;

    public static String WizardInfoPage_folder_label;

    public static String WizardInfoPage_iconDialog_title;

    public static String WizardInfoPage_iconErr_title;

    public static String WizardInfoPage_iconFileNotFoundErr_shortdesc;

    public static String WizardInfoPage_iconGroup_label;

    public static String WizardInfoPage_iconPreview_tooltip;

    public static String WizardInfoPage_imageLoadFailedErr_shortdesc;

    public static String WizardInfoPage_importWizard_title;

    public static String WizardInfoPage_invalidFileExtErr_shortdesc;

    public static String WizardInfoPage_noProjectExportFolderErr_shortdesc;

    public static String WizardInfoPage_outputFileExt_label;

    public static String WizardInfoPage_outputFileExtNotSetErr_shortdesc;

    public static String WizardInfoPage_projectExportFolder_label;

    public static String WizardInfoPage_schema_label;

    public static String WizardInfoPage_schemaDialog_title;

    public static String WizardInfoPage_schemaFileNotFound_shortdesc;

    public static String WizardInfoPage_schemaFilter_message;

    public static String WizardInfoPage_title_label;

    public static String WizardInfoPage_wizardInfoGroup_label;

    public static String WizardInfoPage_wizardTitleNotSetErr_shortdesc;

    public static String WizardInfoPage_xslDialog_title;

    public static String WizardInfoPage_xslFileDialogFilterName_message;

    public static String WizardInfoPage_xslt_label;

    public static String WizardInfoPage_xsltFileNotFoundErr_shortdesc;

    public static String WizardInfoPage_xsltNotSet_shortdesc;

    public static String WizardInfoPage_default_desc;

    public static String WizardInfoPage_default_file_ext_notrans;

    public static String MaaImportWizardPage_Selection_title;

    public static String MaaImportWizardPage_DuplicateFileError_message;

    public static String MaaFileImportWizard_title;

    public static String MaaImportWizardPage_Selection_longdesc;

    public static String MaaImportWizardPage_ProjectsListTitle;

    public static String MaaImportWizardPage_selectAll;

    public static String MaaImportWizardPage_deselectAll;

    public static String MaaImportWizardPage_refresh;

    public static String MaaImportWizardPage_RootSelectTitle;

    public static String MaaImportWizardPage_ArchiveSelectTitle;

    public static String MaaImportWizardPage_browse;

    public static String MaaImportWizardPage_ImportProjectsDescription;

    public static String MaaImportWizardPage_SearchingMessage;

    public static String MaaImportWizardPage_ProcessingMessage;

    public static String MaaImportWizardPage_projectsInWorkspace;

    public static String MaaImportWizardPage_maaBadFormat;

    public static String MaaImportWizardPage_maaCouldNotRead;

    public static String MaaImportWizardPage_internalErrorTitle;

    public static String MaaImportWizardPage_CheckingMessage;

    public static String MaaImportWizardPage_SelectDialogTitle;

    public static String MaaImportWizardPage_SelectArchiveDialogTitle;

    public static String MaaImportWizardPage_errorMessage;

    public static String MaaImportWizardPage_existsQuestion;

    public static String MaaImportWizardPage_overwriteNameAndPathQuestion;

    public static String InputOutputSelectionWizardPage_NoSelectionError_message;

    public static String MAAFromProjectExportWizard_addingProjectToExport_monitor_label;

    public static String MAAFromProjectExportWizard_errorCreatingMAA_dialog_longdesc;

    public static String MAAFromProjectExportWizard_errorCreatingMAA_dialog_title;

    public static String MAAFromProjectExportWizard_exporting_monitor_label;

    public static String MAAFromProjectExportWizard_invalidPath_error_longdesc;

    public static String MAAFromProjectExportWizard_title;

    public static String MAAFromImportExportWizard_DestinationExistsDialog_message;

    public static String MAAFromImportExportWizard_DestinationExistsDialog_title;

    public static String MAAInputOutputSelectionWizardPage_newMAA_fileDialog_title;
}
