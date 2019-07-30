package com.tibco.xpd.analyst.resources.xpdl2.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.analyst.resources.xpdl2.internal.messages"; //$NON-NLS-1$

    private Messages() {
    }

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    public static String AbstractXpdlProjectSelectPage_ProblemCreatingModelFile_message;

    public static String BpmContentLabelProvider_BusinessProcess_label;

    public static String BpmContentLabelProvider_PageflowProcess_label;

    public static String BpmContentLabelProvider_DecisionFlow_label;

    public static String BpmContentLabelProvider_BusinessServicePageflowProcess_label;

    public static String BpmContentLabelProvider_CaseServicePageflowProcess_label;

    public static String BpmContentLabelProvider_ServiceProcess_label;

    public static String BpmContentLabelProvider_ServiceProcessInterface_label;

    public static String BpmContentLabelProvider_ReadOnly_label;

    public static String BpmContentLabelProvider_TaskLibrary_label;

    public static String BpmnCatchableErrorUtil_SetErrorToCatch_menu;

    public static String BpmnCatchableErrorUtil_SetToCatchAll_menu;

    public static String Bom2XsdSpecialFoldersUtil_SpecialFolderUnsupportedException_shortdesc;

    public static String BusinessProcessAssetConfig_DEFAULT_PROCESS_FOLDER_NAME;

    public static String ConvertFieldToParameterCommand_ConvertFieldToParameter_menu;

    public static String ConvertToCorrelationDataAction_DataFieldToCorrelationDataCommand;

    public static String ConvertToCorrelationDataAction_ParameterToCorrelationDataCommand;

    public static String ConvertToDataFieldAction_FromCorrelationDataActionCommand;

    public static String ConvertToDataFieldAction_ParameterToDataFieldCmd_label;

    public static String ConvertToParameterAction_ConvertToParamAction_longmsg;

    public static String ConvertToParameterAction_DataFieldToParamCmd_shortmsg;

    public static String ConvertToParameterAction_FromCorrelationDataCmd_shortmsg;

    public static String ConvertParticipantToDataFieldAction_CannotConvertWithNoProcesses_longdesc;

    public static String ConvertParticipantToDataFieldAction_ParticipantToDataFieldCmd_shortmsg;

    public static String ConvertParticipantToDataFieldCommand_ConvertParticToField_menu;

    public static String CopyAction_2;

    public static String CorrelationDataGroup_TITLE;

    public static String CorrelationDataToDataFieldActionProvider_ActionLabel;

    public static String DataFieldGroup_TITLE;

    public static String DataFieldToCorrelationDataActionProvider_ActionLabel;

    public static String ParameterToCorrelationDataActionProvider_ActionLabel;

    public static String DataFieldToParameterActionProvider_Action_label;

    public static String DataPicker_2;

    public static String DataPicker_MESSAGE;

    public static String DataPicker_TITLE;

    public static String DecisionFlowFilterPicker_SelectAType;

    public static String DecisionFlowFilterPicker_SelectDecisionFlow;

    public static String DecisionFlowFilterPicker_SelectDecisionFlowPackage;

    public static String DecisionFlowFilterPickerProviderHelper_GettingDecisionFlowNames_shortdesc;

    public static String DecisionFlowFilterPickerProviderHelper_GettingDecisionFlowPackageNames_shortdesc;

    public static String DeleteAction_1;

    public static String DeleteAction_2;

    public static String DeleteAction_3;

    public static String DeleteAction_CantDeleteApiParticipant_message;

    public static String DeleteAction_ConfirmReferencedFields_MESSAGE;

    public static String DeleteAction_DelRefPartic_longdesc;

    public static String DeleteAction_DelRefPartic_title;

    public static String DeleteAction_DelRefProcess_longdesc2;

    public static String DeleteAction_DelRefProcess_title;

    public static String DeleteAction_DelProcess_longdesc;

    public static String DeleteAction_DelProcess_title;

    public static String DeleteAction_DelRefTypeDecl_longdesc;

    public static String DeleteAction_DelRefTypeDecl_title;

    public static String DeleteAction_RefDataDelete_title;

    public static String FileSynchronizer_Synchronize_Workspace;

    public static String FormalParameterGroup_TITLE;

    public static String FormalParameterPicker_2;

    public static String FormalParameterPicker_MESSAGE;

    public static String FormalParameterPicker_TITLE;

    public static String FormUrlPicker_DialogMessage;

    public static String FormUrlPicker_DialogTitle;

    public static String FormUrlPicker_FormSelectedMessage;

    public static String FormUrlPicker_InvalidFormMessage;

    public static String FormUrlPicker_PageflowSelectedMessage;

    public static String FormUrlPicker_SelForm_description;

    public static String FormUrlPicker_SelForm_title;

    public static String FormUrlPicker_SelPageflowProcess_desciption;

    public static String FormUrlPicker_SelPageflowProcess_title;

    public static String InterfaceMethodSelectionPage_IfcSelDialog_shortdesc;

    public static String InterfaceMethodSelectionPage_IfcSelDialog_title;

    public static String InterfaceMethodSelectionPage_InterfaceMethodDialog_label;

    public static String InterfaceMethodSelectionPage_InterfaceMethodDoesNotExist_shortdesc;

    public static String InterfaceMethodSelectionPage_ProcIfcDoesNotExistErr_shortdesc;

    public static String InterfaceMethodSelectionPage_SpecifyEvent_message0;

    public static String InterfaceMethodSelectionPage_SpecifyProcIfc_shortdesc;

    public static String IntermediateMethodGroup_Group_title;

    public static String MoveProcessParamToIfcAction_MoveParamToIfc_shortdesc;

    public static String MoveProcessParamToIfcAction_MovingParamOp_shortdesc;

    public static String MoveProcessParticipant_MoveProcessParticipant_name;

    public static String MoveProcessParticipant_NewPackageNameInvalidError_msg;

    public static String MoveProcessProcessor_CannotMoveProcessToParentPackageValidation_msg;

    public static String MoveProcessProcessor_MoveProcessor_name;

    public static String MoveProcessToPackageChange_CannotExecuteCommandError_msg;

    public static String MoveProcessToPackageChange_MoveProcessChange_desc;

    public static String MoveProcessToPackageChange_NoCommandFoundError_msg;

    public static String MoveProcessToPackageChange_ResourceDoesNotExistsError_msg;

    public static String MoveProcessToPackageChange_ValidationMoveProcessChange_msg;

    public static String MoveProcessToPackageChange_WorkingCopyInvalidError_msg;

    public static String MoveProcessToPackageChange_WorkingCopyReadOnlyError_msg;

    public static String MoveProcessWizard_AcceptConditionToProceedValidation_msg;

    public static String MoveProcessWizard_CannotUndoProcessMove_text;

    public static String MoveProcessWizard_ChooseDestinationPackage_desc;

    public static String MoveProcessWizard_ChooseProcessPackageValidation_msg;

    public static String MoveProcessWizard_EnterNewPackageNameValidation_msg;

    public static String MoveProcessWizard_MoveProcessPage_title;

    public static String MoveProcessWizard_MoveProcessWindow_title;

    public static String MoveProcessWizard_NewPackageGroup_desc;

    public static String MoveProcessWizard_NewPackageName_label;

    public static String MoveProcessWizard_PackageAlreadyExistsValidation_msg;

    public static String MoveProcessWizard_PackageNameInvalidValidation_msg;

    public static String NewCorrelationDataWizard_NewFieldName;

    public static String NewCorrelationDataWizard_title;

    public static String NewDataFieldWizard_3;

    public static String NewDataFieldWizard_AddDataField_menu;

    public static String NewDataFieldWizard_FieldName_value;

    public static String NewDataFieldWizard_PAGEDESCRIPTION;

    public static String NewDataFieldWizard_PAGETITLE;

    public static String NewDataFieldWizard_TITLE;

    public static String NewEndErrorMethodWizard_CmdLabel_shortdesc;

    public static String NewEndErrorMethodWizard_DefaultEndError_label;

    public static String NewEndErrorMethodWizard_ErrorDefaultName_label;

    public static String NewEndErrorMethodWizard_Window_title;

    public static String NewEndErrorMethodWizard_WindowDesc_shortdesc;

    public static String NewIntermediateMethodWizard_AddIntermediateMethodCmd_shortmsg;

    public static String NewIntermediateMethodWizard_DefaultName_value;

    public static String NewIntermediateMethodWizard_IntermediateMethodName_value;

    public static String NewIntermediateMethodWizard_Page_desc;

    public static String NewIntermediateMethodWizard_Page_title;

    public static String NewIntermediateMethodWizard_Window_title;

    public static String NewParameterWizard_3;

    public static String NewParameterWizard_AddParameter_menu;

    public static String NewParameterWizard_ParameterName_value;

    public static String NewParameterWizard_PAGEMESSAGE;

    public static String NewParameterWizard_PAGETITLE;

    public static String NewParameterWizard_TITLE;

    public static String NewParticipantWizard_3;

    public static String NewParticipantWizard_AddParticipant_menu;

    public static String NewParticipantWizard_ParticipantName_value;

    public static String NewParticipantWizard_PAGEMESSAGE;

    public static String NewParticipantWizard_PAGETITLE;

    public static String NewParticipantWizard_TITLE;

    public static String NewProcessInterfaceWizard_AddProcessInterfaceCmd_label;

    public static String NewProcessInterfaceWizard_DefaultName_value;

    public static String NewProcessInterfaceWizard_DefaultStartMethod_title;

    public static String NewProcessInterfaceWizard_Dialog_title;

    public static String NewProcessInterfaceWizard_Interface_default_label;

    public static String NewProcessInterfaceWizard_Page_desc;

    public static String NewProcessInterfaceWizard_Page_title;

    public static String NewServiceProcessInterfaceWizard_Dialog_title;

    public static String NewServiceProcessInterfaceWizard_DefaultName_value;

    public static String NewServiceProcessInterfaceWizard_AddServiceProcessConfiguration_command;

    public static String NewStartMethodWizard_AddStartMethodCmd_label;

    public static String NewStartMethodWizard_DefaultName_value;

    public static String NewStartMethodWizard_Page_desc;

    public static String NewStartMethodWizard_Page_title;

    public static String NewStartMethodWizard_StartMethodName_value;

    public static String NewStartMethodWizard_Window_title;

    public static String NewTypeDeclarationWizard_0;

    public static String NewTypeDeclarationWizard_1;

    public static String NewTypeDeclarationWizard_2;

    public static String NewTypeDeclarationWizard_3;

    public static String NewTypeDeclarationWizard_5;

    public static String NewTypeDeclarationWizard_AddTypeDecl_menu;

    public static String NewTypeDeclarationWizard_TypeDeclarationName_value;

    public static String NewTypeDeclarationWizard_TITLE;

    public static String PackageMigrationResolution_message;

    public static String PackageOrProcessSelectionPage_10;

    public static String PackageOrProcessSelectionPage_12;

    public static String PackageOrProcessSelectionPage_13;

    public static String PackageOrProcessSelectionPage_2;

    public static String PackageOrProcessSelectionPage_3;

    public static String PackageOrProcessSelectionPage_4;

    public static String PackageOrProcessSelectionPage_5;

    public static String PackageOrProcessSelectionPage_6;

    public static String PackageOrProcessSelectionPage_7;

    public static String PackageOrProcessSelectionPage_9;

    public static String PackageOrProcessSelectionPage_DESC;

    public static String PackageOrProcessSelectionPage_TITLE;

    public static String PackageSelectionPage_2;

    public static String PackageSelectionPage_3;

    public static String PackageSelectionPage_4;

    public static String PackageSelectionPage_5;

    public static String PackageSelectionPage_6;

    public static String PackageSelectionPage_7;

    public static String PackageSelectionPage_DESC;

    public static String PackageSelectionPage_TITLE;

    public static String ParameterToDataFieldActionProvider_Action_label;

    public static String ParticipantToDataFieldActionProvider_Action_label;

    public static String ParticipantToDataFieldActionProvider_Title;

    public static String ParticipantGroup_TITLE;

    public static String ParticipantsPicker_2;

    public static String ParticipantsPicker_MESSAGE;

    public static String ParticipantsPicker_TITLE;

    public static String PasteAction_1;

    public static String PasteAction_Paste;

    public static String ProcessDataUtil_CannotAccessImplemented_Label;

    public static String ProcessDataUtil_false;

    public static String ProcessDataUtil_true;

    public static String ProcessOrInterfaceSelectionPage_SelectedObject_label;

    public static String ProcessInterfaceSelectionPage_SelectedObject_label;

    public static String ProcessSelectionPage_SelectedObject_label;

    public static String ProcessElementsQuickSearchContentProvider_PackagesByName_label;

    public static String ProcessElementsQuickSearchContentProvider_ProcessElementsQuickSearchContentProvider_Pageflows_label;

    public static String ProcessElementsQuickSearchContentProvider_Processes_label;

    public static String ProcessElementsQuickSearchContentProvider_ProcInterfaces_label;

    public static String ProcessElementsQuickSearchContentProvider_ProcRelatedElements_label;

    public static String ProcessElementsQuickSearchLabelProvider_Package_label;

    public static String ProcessElementsQuickSearchLabelProvider_Process_label;

    public static String ProcessElementsQuickSearchLabelProvider_ProcessElementsQuickSearchLabelProvider_Pageflow_label;

    public static String ProcessElementsQuickSearchLabelProvider_ProcessInterface_label;

    public static String ProcessFilterPickerProviderHelper_MonitorIfcAndProcessNames_shortdesc;

    public static String ProcessFilterPickerProviderHelper_MonitorProcessIfcNames_shortdesc;

    public static String ProcessFilterPickerProviderHelper_MonitorServiceProcessIfcNames_shortdesc;

    public static String ProcessFilterPickerProviderHelper_MonitorProcessNames_shortdesc;

    public static String ProcessFilterPickerProviderHelper_ProcessFilterPickerProviderHelper_MonitorPageflowNames_shortdesc;

    public static String ProcessGroup_TITLE;

    public static String ProcessInterfaceGroup_Group_title;

    public static String ProcessInterfacePropertySection_EmptyNameError_shortmsg;

    public static String ProcessInterfacePropertySection_LeadingTrailingSpacesinNameError_shortmsg;

    public static String ProcessInterfacePropertySection_NameExistsError_shortmsg;

    public static String ProcessInterfaceUtil_SetActivityInterfaceData_menu;

    public static String ProcessInterfaceUtil_SetMethodInterfaceData_menu;

    public static String ProcessInternalViewTogglerAction_analystCapability_message;

    public static String ProcessInternalViewTogglerAction_processInternal_message;

    public static String ProcessInternalViewTogglerAction_title;

    public static String ProcessPicker_3;

    public static String ProcessPicker_Process_Or_ProcessInterface;

    public static String ProcessPicker_MESSAGE;

    public static String ProcessPicker_TITLE;

    public static String ProcessSelectionPage_CantBuildBrowseList_message;

    public static String ProcessSelectionPage_2;

    public static String ProcessSelectionPage_3;

    public static String ProcessSelectionPage_CantBuildBrowseList_title;

    public static String ProcessSelectionPage_DESC;

    public static String ProcessInterfaceSelectionPage_DESC;

    public static String ProcessInterfaceMethodSelectionPage_DESC;

    public static String ProcessOrInterfaceSelectionPage_DESC;

    public static String ProcessSelectionPage_TITLE;

    public static String ProcessInterfaceSelectionPage_TITLE;

    public static String ProcessOrInterfaceSelectionPage_TITLE;

    public static String ProcessInterfaceMethodSelectionPage_TITLE;

    public static String ProcessSelectionPage_MustSelect_longdesc;

    public static String ProcessInterfaceSelectionPage_MustSelect_longdesc;

    public static String ProcessOrInterfaceSelectionPage_MustSelect_longdesc;

    public static String ProcessSelectionPage_CannotAccessObject_longdesc;

    public static String ProcessInterfaceSelectionPage_CannotAccessObject_longdesc;

    public static String ProcessOrInterfaceSelectionPage_CannotAccessObject_longdesc;

    public static String ProcessSelectionPage_BrowseDialog_title;

    public static String ProcessSelectionPage_BrowseDialog_shortdesc;

    public static String ProcessInterfaceSelectionPage_BrowseDialog_title;

    public static String ProcessInterfaceSelectionPage_BrowseDialog_shortdesc;

    public static String ProcessOrInterfaceSelectionPage_BrowseDialog_title;

    public static String ProcessOrInterfaceSelectionPage_BrowseDialog_shortdesc;

    public static String ProcessUIUtil_setProjectReferenceWithoutAsking_check_shortdesc;

    public static String ProjectSelectPage_CreatePkgButton_label;

    public static String ProjectSelectPage_File_label;

    public static String ProjectSelectPage_FileExistsError_shortmsg;

    public static String ProjectSelectPage_InvalidCharactersInProcessPackageName_shortmsg;

    public static String ProjectSelectPage_NotAProcessPackagesFolder_longdesc;

    public static String ProjectSelectPage_PackageFileEmptyError_shortmsg;

    public static String ProjectSelectPage_PackageGroup_shortdesc;

    public static String ProjectSelectPage_PackagesFolder_label;

    public static String ProjectSelectPage_Window_shortdesc;

    public static String ProjectSelectPage_Window_title;

    public static String RedoAction_0;

    public static String RedoAction_1;

    public static String RedoAction_3;

    public static String RefactorProcessParamActionProvider_MoveParamAction_menu;

    public static String RenameAction_1;

    public static String RenameAction_DuplicateIntermediateMethod_longdesc;

    public static String RenameAction_DuplicateIntermediateMethodToken_longdesc;

    public static String RenameAction_DuplicateProcessInterface_longdesc;

    public static String RenameAction_DuplicateProcessInterfaceToken_longdesc;

    public static String RenameAction_DuplicateStartMethod_longdesc;

    public static String RenameAction_DuplicateStartMethodToken_longdesc;

    public static String RenameAction_menu;

    public static String PackageFolderPicker_dialogTitle;

    public static String PackageFolderPicker_dialogMessage;

    public static String PackageFolderPicker_statusMessageSuccess;

    public static String PackageFolderPicker_statusMessageError;

    public static String RenameAction_DuplicateInPackage_longdesc;

    public static String RenameAction_DuplicateTokenInPackage_longdesc;

    public static String RenameAction_DuplicateInProcess_longdesc;

    public static String RenameAction_DuplicateTokenInProcess_longdesc;

    public static String RenameAction_DuplicateInProcessInterface_longdesc;

    public static String RenameAction_DuplicateTokenInProcessInterface_longdesc;

    public static String RenameAction_DuplicateTokenInEmbeddedSubProc_longdesc;

    public static String RenameAction_DuplicateInEmbeddedSubProc_longdesc;

    public static String RenameAction_DuplicateParticipantInPackage_longdesc;

    public static String RenameAction_DuplicateParticipantInProcess_longdesc;

    public static String RenameAction_DuplicateParticipantTokenInPackage_longdesc;

    public static String RenameAction_DuplicateParticipantTokenInProcess_longdesc;

    public static String RenameAction_DuplicateParticipantInEmbeddedSubProc_longdesc;

    public static String RenameAction_DuplicateParticipantTokenInEmbeddedSubProc_longdesc;

    public static String RenameAction_DuplicateProcess_longdesc;

    public static String RenameAction_DuplicateProcessTokenName_longdesc;

    public static String RenameAction_DuplicateTypeDeclaration_longdesc;

    public static String RenameAction_DuplicateTypeDeclarationToken_longdesc;

    public static String RenameAction_LeadSpaceNotAllowedForField;

    public static String RenameAction_LeadSpaceNotAllowedForGeneric;

    public static String RenameAction_LeadSpaceNotAllowedForPartics;

    public static String RenameAction_LeadSpaceNotAllowedForProcess;

    public static String RenameAction_LeadSpaceNotAllowedForTypes;

    public static String RenameAction_Rename_menu;

    public static String RenameAction_Rename_title;

    public static String RenameAction_RenameIntermediateMethod_menu;

    public static String RenameAction_RenamePartic_menu;

    public static String RenameAction_RenameProcess_menu;

    public static String RenameAction_RenameProcessInterface_menu;

    public static String RenameAction_RenameStartMethod_menu;

    public static String RenameAction_RenameType_menu;

    public static String RenameActionContributorParticipant_RenameParticipantForXPDL_Label;

    public static String StartMethodGroup_Group_title;

    public static String SynchronizerUtil_Dont_ask_to_save_before_synchronising;

    public static String SynchronizerUtil_File_needs_saving;

    public static String SynchronizerUtil_Files_need_saving;

    public static String SynchronizerUtil_Save_error;

    public static String SynchronizerUtil_Save_error_message;

    public static String SynchronizerUtil_Save_required_title;

    public static String TaskLibraryActivityPicker_SelectATask_longdesc;

    public static String TaskLibraryActivityPicker_TaskLibraryTaskPicker_title;

    public static String TypeDeclarationGroup_TITLE;

    public static String UndoAction_1;

    public static String UndoAction_2;

    public static String UndoAction_3;

    public static String CreationWizardProjectSelectionPage_1;

    public static String CreationWizardProjectSelectionPage_10;

    public static String CreationWizardProjectSelectionPage_11;

    public static String CreationWizardProjectSelectionPage_12;

    public static String CreationWizardProjectSelectionPage_14;

    public static String CreationWizardProjectSelectionPage_2;

    public static String CreationWizardProjectSelectionPage_3;

    public static String CreationWizardProjectSelectionPage_8;

    public static String CreationWizardProjectSelectionPage_9;

    public static String XPDCopyToClipboardCommand_label;

    public static String XPDCopyToClipboardCommand_message;

    public static String XPDCopyToClipboardCommand_shortdesc;

    public static String XPDCutToClipboardCommand_label;

    public static String XPDCutToClipboardCommand_message;

    public static String XPDCutToClipboardCommand_shortdesc;

    public static String Xpdl2ResourcesConsts_DefaultBusinessVersion_shortdesc;

    public static String Xpdl2ResourcesConsts_DefaultPackageName_shortdesc;

    public static String Xpdl2ResourcesConsts_DefaultProcessName_shortdesc;

    public static String Xpdl2WorkingCopyImpl_cannotSave_message;

    public static String Xpdl2WorkingCopyImpl_details_message;

    public static String Xpdl2WorkingCopyImpl_migrateFailed_message;

    public static String Xpdl2WorkingCopyImpl_migrationProblem_error_message;

    public static String Xpdl2WorkingCopyImpl_problemWhileLoading_message;

    public static String Xpdl2WorkingCopyImpl_SetSchemaLocationCommand;

    public static String CopyOf_tokenNoSpaces;

    public static String CopyNOf_tokenNoSpaces;

    public static String CopyAction_Menu_title;

    public static String PasteAction_Menu_title;

    public static String DeleteAction_Menu_title;

    public static String OpenAction_Menu_title;

    public static String CatchErrorCodePickerDialog_ClosesetMatch_message;

    public static String CatchErrorCodePickerDialog_SelectErroroCatch_title;

    public static String CatchErrorCodePickerDialog_SelectSpecificError_message;

    public static String CatchErrorCodePickerDialog_SelectUnspecificError_message;

    public static String CatchErrorCodePickerDialog_SelectUnspecificErrorDescription_message2;

    public static String CatchErrorCodePickerDialog_ThrownBy_label;

    public static String EndErrorGroup_ErrorEvent_title;

    public static String YouMustSelectError_message0;

    public static String DeleteAction_deleteFieldCommand_title;

    public static String DeleteAction_deleteFieldsCommand_title;

    public static String DeleteAction_deleteParamCpmmand_title;

    public static String DeleteAction_deleteParamsCommand_title;

    public static String DeleteAction_deleteParticipantCommand_title;

    public static String DeleteAction_deleteParticipantsCommand_title;

    public static String DeleteAction_deleteProcessCommand_title;

    public static String DeleteAction_deleteProcessesCommand_title;

    public static String DeleteAction_deleteTypeDeclarationCommand_title;

    public static String DeleteAction_deleteTypeDeclarationsCommand_title;

    public static String SharedResourcesSection_EmailEnum_button;

    public static String SharedResourcesSection_JdbcEnum_button;

    public static String SharedResourcesSection_RestServiceEnum_button;

    public static String DNDProcessToPackage_MoveProcessFailedError_desc;

    public static String DNDProcessToPackage_MoveProcessWizard_title;

    public static String DNDProcessToPackage_ProblemDuringProcessMove_desc;

    public static String ProcessDataUtil_decimal;

    public static String ProcessDataUtil_integer;

    public static String RenameAction_ReservedPrefix_longdesc;

    public static String RenameAction_ReservedWord_longdesc;

    public static String AbstractAddProjectReferencesForProcessMove_AddProjectReferenceParticipant_name;

    public static String AbstractAddProjectReferencesForProcessMove_CyclicProjectDependencyError_msg;

    public static String ActivityPicker_TITLE;

    public static String ActivityPicker_MESSAGE;

    public static String AddProjectReferencesParticipant_AddProjectReferencesDueToProcessOmBomDependency_msg;

    public static String AddReferencedProjectChange_AddProjectReferencesChange_desc;

    public static String Migration_LoadResource_Info_message;
}
