package com.tibco.xpd.bom.gen.ui.internal;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

    private static final String BUNDLE_NAME =
            "com.tibco.xpd.bom.gen.ui.internal.messages"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    public static String BOMGeneratorWizard_buildAutomaticSwitchedOff_error_message;

    public static String BOMGeneratorWizard_errorDialog_title;

    public static String BOMGeneratorWizard_errorReportedDuringGeneration_error_message;

    public static String BOMGeneratorWizard_page_shortDescription;

    public static String BOMGeneratorWizard_selectStrategy_page_shortdesc;

    public static String BOMGeneratorWizard_title;

    public static String BOMGeneratorWizard_validationErrors_error_message;

    public static String BOMGeneratorWizard_different_projects_message;

    public static String BomGenFileChooser_browse_button;

    public static String BomGenFileChooser_file_label;

    public static String BomGenFileChooser_fileSystem_button;

    public static String BomGenFileChooser_question_dlg_title;

    public static String BomGenFileChooser_targetDirNotExist_err_desc;

    public static String BomGenFileChooser_workspace_button;

    public static String BomGenPreferencePage_connectionPassword_label;

    public static String BomGenPreferencePage_connectionURL_label;

    public static String BomGenPreferencePage_connectionUser_label;

    public static String BomGenPreferencePage_databaseConnection_group_title;

    public static String BomGenPreferencePage_folders_group_title;

    public static String BomGenPreferencePage_generationOption_group_title;

    public static String BomGenPreferencePage_generationStrategy_label;

    public static String BomGenPreferencePage_regenerateXsd_button;

    public static String BomGenPreferencePage_resourceFolder_label;

    public static String BomGenPreferencePage_sourceFolder_label;

    public static String BomGenPreferencePage_xsdGenerationOption_group_title;

    public static String BomGenPreferencePage_xsdGenOptions_group_label;

    public static String BomGenPreferencePage_xsdEnableValidation_label;

    public static String BomGenPreferencePage_wsdlGenerationOption_group_title;

    public static String BomGenPreferencePage_bomGenerationOption_group_title;

    public static String BomGenPreferencePage_wsdlEnableValidation_label;

    public static String BomGenPreferencePage_bomEnableGeneration_label;

    public static String BomGenPreferencePage_bomEnableReGenerationOnImport_label;

    public static String BomGenProjectPropertyPage_keepNamespaceFileExtension;

    public static String BomGenProjectPropertyPage_ProjectBuildMessage;

    public static String BomGenProjectPropertyPage_ProjectRebuildNote;

    public static String BosConfigureDefaults_label;

    public static String ExportBosJarAdvancedWizardPage_AdvancedGenerationOptions_label;

    public static String ExportBosJarAdvancedWizardPage_BosGenerationOptionsGroup_title;

    public static String ExportBosJarAdvancedWizardPage_BosPersistenceStrategy_label;

    public static String ExportBosJarAdvancedWizardPage_BusinessObjectServices_title;

    public static String ExportBosJarAdvancedWizardPage_ConnectionPassword_label;

    public static String ExportBosJarAdvancedWizardPage_ConnectionUrl_label;

    public static String ExportBosJarAdvancedWizardPage_ConnectionUser_label;

    public static String ExportBosJarAdvancedWizardPage_Database_title;

    public static String ExportBosJarAdvancedWizardPage_Folders_title;

    public static String ExportBosJarAdvancedWizardPage_ResourecDir_label;

    public static String ExportBosJarAdvancedWizardPage_SrcDir_label;

    public static String ExportBosJarAdvancedWizardPage_UseDefaults_label;

    public static String ExportBosJarAdvancedWizardPage_UseProjectSpecificSettings_label;

    public static String ExportGenJarWizard_bomFileNotExist_error_shortdesc;

    public static String ExportGenJarWizard_cannotFindContributor_error_shortdesc;

    public static String ExportGenJarWizard_errorDialog_title;

    public static String ExportGenJarWizard_generatingFrom_monitor_shortdesc;

    public static String ExportGenJarWizard_title;

    public static String ExportGenJarWizard_destinationArchiveAlreadyExists;

    public static String ExportGenJarWizardPage_archiveFolderNotExist_desc;

    public static String ExportGenJarWizardPage_bomFile_label;

    public static String ExportGenJarWizardPage_bomFileNotExist_err_desc;

    public static String ExportGenJarWizardPage_chooseArchiveFile_desc;

    public static String ExportGenJarWizardPage_chooseBomFile_dlg_title;

    public static String ExportGenJarWizardPage_chooseDestArchive_dlg_title;

    public static String ExportGenJarWizardPage_desc;

    public static String ExportGenJarWizardPage_destArchive_label;

    public static String ExportGenJarWizardPage_overwriteExistingFile_label;

    public static String ExportGenJarWizardPage_title;

    public static String BosExportFolderName_name;

    public static String StrategySelectionPage_generatedReportedErrors_error_message;

    public static String StrategySelectionPage_noStrategiesAvailable_label;

    public static String StrategySelectionPage_noStrategySupportsSelection_shortdesc;

    public static String StrategySelectionPage_runningDependencyAnalysis_monitor_shortdesc;

    public static String StrategySelectionPage_someStrategiesDoNoSupportSelection_shortdesc;

    public static String StrategySelectionPage_strategies_group_title;

    public static String StrategySelectionPage_validationErrorReported_error_shortdesc;

    public static String StrategySelectionPage_validationErrorReportedSeeErrorLog_error_message;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

}
