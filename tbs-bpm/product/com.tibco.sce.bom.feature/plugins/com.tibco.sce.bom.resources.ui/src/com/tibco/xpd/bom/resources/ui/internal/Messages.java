package com.tibco.xpd.bom.resources.ui.internal;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

    private static final String BUNDLE_NAME =
            "com.tibco.xpd.bom.resources.ui.internal.messages"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    public static String AssociationItemProvider_general_label;

    public static String AssociationItemProvider_name_label;

    public static String AssociationItemProvider_name_shortdesc;

    public static String AssociationItemProvider_srcRoleMultiplicity_label;

    public static String AssociationItemProvider_srcRoleMultiplicity_shortdesc;

    public static String AssociationItemProvider_srcRoleName_label;

    public static String AssociationItemProvider_srcRoleName_shortdesc;

    public static String AssociationItemProvider_tgtRoleMultiplicity_label;

    public static String AssociationItemProvider_tgtRoleMultiplicity_shortdesc;

    public static String AssociationItemProvider_tgtRoleName_label;

    public static String AssociationItemProvider_tgtRoleName_shortdesc;

    public static String AttributesLabel;

    public static String EnumLiteralLabel;

    public static String BOMAddChildActionProvider_addChild_menu;

    public static String BOMAddChildActionProvider_convertTo_menu;

    public static String BOMAddChildActionProvider_AddChildAttribute_menu;

    public static String BOMAddChildActionProvider_AddChildClass_menu;

    public static String BOMContentProvider_editor_dialog_message;

    public static String BOMContentProvider_editor_dialog_title;

    public static String BOMCopyPasteCommandFactory_copy_menu;

    public static String BOMCopyPasteCommandFactory_outOfMemory_errDlg_title;

    public static String BOMCopyPasteCommandFactory_paste_menu;

    public static String BOMCopyPasteCommandFactory_unableToCopy_err_message;

    public static String BOMDropAdapterAssistant_dropError_message;

    public static String BOMPasteAction_paste_action;

    public static String BOMPicker_dialog_message;

    public static String BOMPicker_gettingItems_monitor_shortdesc;

    public static String BOMPicker_selectClass_title;

    public static String BOMPicker_selectPrimType_title;

    public static String BOMPicker_selectType_title;

    public static String BOMPickerPreferencePage_setProjectReferenceWithoutAsking_label;

    public static String BOMPickerProviderUtil_gettingBasePrimType_monitor_shortdesc;

    public static String BOMPickerProviderUtil_gettingClass_monitor_shortdesc;

    public static String BOMPickerProviderUtil_gettingPrimType_monitor_shortdesc;

    public static String BOMPreferencePage_longdesc;

    public static String BomEditorQuickSearchContentProvider_classes_menu;

    public static String BomEditorQuickSearchContentProvider_enumerations_menu;

    public static String BomEditorQuickSearchContentProvider_packages_menu;

    public static String BomEditorQuickSearchContentProvider_primitives_menu;

    public static String BomEditorQuickSearchLabelProvider_TypeName_Class_label;

    public static String BomEditorQuickSearchLabelProvider_TypeName_Enumeration_label;

    public static String BomEditorQuickSearchLabelProvider_TypeName_Package_label;

    public static String BomEditorQuickSearchLabelProvider_TypeName_PrimitiveType_label;

    public static String BomProjectExplorerQuickSearchContentProvider_SuperCategoryFilter_menu;

    public static String BomUIUtil_cyclicReference_longdesc;

    public static String BomUIUtil_errorInCheckingProRef_longdesc;

    public static String BomUIUtil_pickSuperclass_label;

    public static String BomUIUtil_pickType_label;

    public static String BomUIUtil_projReferenceDialog_longdesc;

    public static String BomUIUtil_projReferenceDialog_title;

    public static String BomUIUtil_setProjectReferenceWithoutAsking_label;

    public static String ClassifierPropertiesProvider_generalCategory_label;

    public static String ClassItemProvider_EmptyClassName_label;

    public static String DropCommandFactory_association_menu;

    public static String DropCommandFactory_copy_title;

    public static String DropCommandFactory_copyObjects_menu;

    public static String DropCommandFactory_drop_title;

    public static String DropCommandFactory_move_title;

    public static String DropCommandFactory_moveObjects_menu;

    public static String DropCommandFactory_property_menu;

    public static String DropCommandFactory_superclass_menu;

    public static String DropCommandFactory_unableToCopy_shortdesc;

    public static String DropCommandManager_checkProjectDependency_operation_label;

    public static String DropCommandManager_generalization_checkProjectDependency_operation_label;

    public static String DropMenuManager_checkProjectDependency_operation_label;

    public static String DropMenuManager_createSubclass_menu;

    public static String DropMenuManager_createSubclass_title;

    public static String DropMenuManager_drop_title;

    public static String DropMenuManager_failedToCreateSubclass_shortdesc;

    public static String PackageItemProvider_general_label;

    public static String PackageItemProvider_name_label;

    public static String PackageItemProvider_name_shortdesc;

    public static String PrimitiveRestrictionFacetParser_setRestrictionsValue_command_label;

    public static String PrimitiveRestrictionFacetParser_command_shortdesc;

    public static String PrimitiveRestrictionFacetParser_notset_label;

    public static String PrimitiveTypeItemProvider_restriction_label;

    public static String ProfilePasteOperation_cannotAccessProfile_message;

    public static String ProfilePasteOperation_cannotAccessProfile_title;

    public static String ProfilePasteOperation_cannotAccessProfile_withProfileName_message;

    public static String ProfilePasteOperation_multipleProfilesNotApplied_error_message;

    public static String ProfilePasteOperation_profile_errorDlg_title;

    public static String ProfilePasteOperation_profileNotApplied_error_message;

    public static String PropertyItemProvider_attributeName_label;

    public static String PropertyItemProvider_attributeName_shortdesc;

    public static String OperationItemProvider_attributeName_label;

    public static String OperationItemProvider_attributeName_shortdesc;

    public static String RenameAction_menu;

    public static String RenameAction_rename_message;

    public static String RenameAction_tooltip;

    public static String RenameBOMElementProcessor_NewNameInvalid1;

    public static String RenameBOMElementProcessor_NewNameNotEmpty1;

    public static String RenameBOMElementProcessor_NewNameNotNull1;

    public static String RenameBOMElementProcessor_RenameBOMElements2;

    public static String RenameBOMElementWizard_OldLabel;

    public static String RenameBOMElementWizard_NewLabel;

    public static String RenameBOMElementWizard_OldName;

    public static String RenameBOMElementWizard_RenameElementPageTitle;

    public static String RenameBOMElementWizard_RenameElementWindowTitle;

    public static String RestrictionUtils_comand_menu;

    public static String RestrictionUtils_false;

    public static String RestrictionUtils_true;

    public static String StereotypePropertyProvider_complexPicker_multiselect_shortdesc;

    public static String StereotypePropertyProvider_complexPicker_singleselect_shortdesc;

    public static String StereotypePropertyProvider_complexPicker_title;

    public static String TypeItemProvider_general_label;

    public static String TypeItemProvider_superclass_label;

    public static String TypeItemProvider_superclass_shortdesc;

    public static String TypeItemProvider_typeName_label;

    public static String TypeItemProvider_typeName_shortdesc;

    public static String BOMDocumentationExportWizard_exportFolder_label;

    public static String BOMDocumentationExportWizard_message;

    public static String BOMDocumentationExportWizard_title;

    public static String BOMDocXsltGen_BomModelConatiner_title;

    public static String BOMDocXsltGen_GeneratorRootFolderName;

    public static String BOMElementNameChange_InvalidContent;

    public static String BOMElementNameChange_NoCommand;

    public static String BOMElementNameChange_NoExecutableCommand;

    public static String BOMElementNameChange_ReadOnly;

    public static String BOMElementNameChange_Rename1;

    public static String BOMElementNameChange_ChangeLabel;

    public static String BOMElementNameChange_RenamingElement;

    public static String BOMElementNameChange_ResourceDoesNotExist;

    public static String BOMElementNameChange_ValidatingChange;

    public static String BOMElementRefactorParticipant_NoElementsAffected;

    public static String BpmDebugPreferencePage_CleaningProjects_shortdesc;

    public static String BpmDebugPreferencePage_EnableBpmDebug_button;

    public static String BpmDebugPreferencePage_NoteAboutBuild_shortdesc;

    public static String BpmDebugPreferencePage_NoteAboutDebugging_shortdesc;

    public static String BpmDebugPreferencePage_TibcoBpmDebugMode_label;

    public static String BuildInformationPropertiesPage_buildVersionTimestamp_label;

    public static String BuildInformationPropertiesPage_buildVersionTimestamp_longdesc;

    public static String ResourceCopier_FailedToCreateFolderErr_message;

    public static String ResourceCopier_FailedToCreateFolderException_title;

    public static String ResourceCopier_styleSheetTask_title;

    public static String ResourceCopier_unableToCreateFolderErr_message;

    public static String ResourceCopier_UnableToCreateFolderException_title;

    public static String UMLProfilePicker_monitor_label;

    public static String UMLProfilePicker_shortdesc;

    public static String UMLProfilePicker_title;

    public static String UMLStereotypePicker_shortdesc;

    public static String UMLStereotypePicker_title;

    public static String QuestionMark;

    public static String ImageLogo_alt_label;

    public static String Header_title_label;

    public static String Header_Summary_title_label;

    public static String Header_Details_title_label;

    public static String Header_Packages_title_label;

    public static String Header_Concepts_title_label;

    public static String Header_PrimitiveTypes_title_label;

    public static String Header_Enumeration_title_label;

    public static String Header_Associations_title_label;

    public static String UnknownElement_label;

    public static String Package_label;

    public static String Concept_label;

    public static String PrimitiveType_label;

    public static String Enumeration_label;

    public static String Association_label;

    public static String Komma;

    public static String Value_Inclusive;

    public static String Not_Applicable;

    public static String DOT_DOT_label;

    public static String DIGIT_ZERO;

    public static String COLON_COLON;

    public static String HTML_Header_title;

    public static String business_object_model_text;

    public static String Name_label;

    public static String Description_label;

    public static String ID_label;

    public static String SpecializationOf_label;

    public static String Type_label;

    public static String Multiplicity_label;

    public static String Association_name_label;

    public static String AttributeAssociation_name_label;

    public static String AttributeDescription_label;

    public static String EnumLiteralValue_label;

    public static String EnumLiteralValueLower_label;

    public static String EnumLiteralValueUpper_label;

    public static String Association_type_label;

    public static String Source_label;

    public static String Destination_label;

    public static String DiagramGroupTransientItemProvider_Diagrams_label;

    public static String Main_title;

    public static String Notes_label;

    public static String AssociationType_Composition_text;

    public static String AssociationType_Aggregation_text;

    public static String AssociationType_Simple_text;

    public static String OperationName_label;

    public static String OperationDescription_label;

    public static String OperationReturnType_label;

    public static String OperationParameters_label;

    public static String OperationParameterTypes_label;

    public static String OperationParameterTypeVoid_text;

    public static String Activator_ActivityChangesDialog_title;

    public static String Activator_ActivityChangesDialog_message;

    public static String Activator_buildAll_label;

    public static String Activator_buildJob_label;

    public static String Activator_buildProject_label;

    public static String ProfileApplicationItemProvider_attributeLocation_label;

    public static String ProfileApplicationItemProvider_attributeLocation_shortdesc;

    public static String RemoveXSDNotationProfile_ConvertBOM_title;

    public static String RemoveXSDNotationProfile_ConvertBOM_confirmationMessage;

    public static String RemoveXSDNotationProfile_filesAffected_infoMessage;

    public static String RemoveXSDNotationProfile_noFilesAffected_infoMessage;

    public static String RemoveXSDNotationProfile_filesAffected_saveTitle;

    public static String RemoveXSDNotationProfile_filesAffected_saveMessage;

    public static String RefactorToBusinessDataProjectAction_convertToBusinessDataDialogError_title;

    public static String RefactorToBusinessDataProjectAction_convertToBusinessDataProjectWizard_title;

    public static String RefactorToBusinessDataProjectAction_convertToBusinessDataDialog_desc2;

    public static String RefactorToBusinessDataProjectAction_convertToBusinessData_warning_message1;

    public static String RefactorToBusinessDataProjectAction_convertToBusinessData_warning_message2;

    public static String RefactorToBusinessDataProjectAction_progressMonitor_label;

    public static String RefactorToBusinessDataProjectAction_progressMonitor_label2;

    public static String RefactorPage__convertToBusinessDataDialog_title;

    public static String RefactorPage__convertToBusinessDataDialog_label;

    public static String CaseIdentifier_label;

    public static String CaseState_label;

    public static String Local_label;

    public static String Case_label;

    public static String Global_label;

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
