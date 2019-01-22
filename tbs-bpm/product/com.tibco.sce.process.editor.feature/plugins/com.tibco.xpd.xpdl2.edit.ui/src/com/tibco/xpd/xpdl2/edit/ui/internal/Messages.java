/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.xpdl2.edit.ui.internal;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.osgi.util.NLS;

/**
 * @author wzurek
 * 
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.xpdl2.edit.ui.internal.messages"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    public static String AbstractDescriptionSection_OpenURL_tooltip;

    public static String BaseFieldOrParamPropertySection_AllowedValues_label;

    public static String BaseFieldOrParamPropertySection_DuplicateLabelInPackage_longdesc;

    public static String BaseFieldOrParamPropertySection_DuplicateLabelInprocess_longdesc;

    public static String BaseFieldOrParamPropertySection_DuplicateLabelInprocessInterface_longdesc;

    public static String BaseFieldOrParamPropertySection_DuplicateLabelInEmbeddedSubProc;

    public static String BaseFieldOrParamPropertySection_DuplicateNameInPackage_longdesc;

    public static String BaseFieldOrParamPropertySection_DuplicateNameInprocess_longdesc;

    public static String BaseFieldOrParamPropertySection_DuplicateNameInprocessInterface_longdesc;

    public static String BaseFieldOrParamPropertySection_DuplicateNameInEmbeddedSubProc;

    public static String BaseFieldOrParamPropertySection_InitialValues_label;

    public static String BaseFieldOrParamPropertySection_InvalidDecimal_tooltip;

    public static String BaseFieldOrParamPropertySection_InvalidInteger_Tooltip;

    public static String BaseFieldOrParamPropertySection_SetParameterType_menu;

    public static String BaseFieldOrParamPropertySection_SetFieldType_menu;

    public static String BaseFieldOrParamPropertySection_Tasks_label;

    public static String BaseFieldOrParamPropertySection_Goto_label;

    public static String BaseFieldOrParamPropertySection_References_label;

    public static String BaseTypeSection_basicArray_label;

    public static String BaseTypeSection_basicLength_label;

    public static String BaseTypeSection_basicScale_label;

    public static String BaseTypeSection_basicType_label;

    public static String BaseTypeSection_basicTypeCmb_label;

    public static String BaseTypeSection_declaredTypeCmb_label;

    public static String BaseTypeSection_declaredTypeID_label;

    public static String BaseTypeSection_externalRefCmb_label;

    public static String BaseTypeSection_bomTypeCmb_label;

    public static String BaseTypeSection_caseTypeRefCmb_label;

    public static String BaseTypeSection_bomTypeNotSet_longdesc;

    public static String BaseTypeSection_caseTypeReferenceNotSet_longdesc;

    public static String BaseTypeSection_FieldTypeSectionHeader_label;

    public static String BaseTypeSection_FloatLength_tooltip;

    public static String BaseTypeSection_IntegerLength_tooltip;

    public static String BaseTypeSection_noReferenceSet_shortdesc;

    public static String BaseTypeSection_NotApplicable_text;

    public static String BaseTypeSection_bomType_label;

    public static String BaseTypeSection_selectBOMType_browse_tooltip;

    public static String BaseTypeSection_selectCaseTypeReference_browse_tooltip;

    public static String BaseTypeSection_setBasicType_label;

    public static String BaseTypeSection_SetFieldDecimals_menu;

    public static String BaseTypeSection_SetFieldLen_menu;

    public static String BaseTypeSection_setDeclaredType_label;

    public static String BaseTypeSection_setBOMTypeDomain_label;

    public static String BaseTypeSection_setCaseRefType_label;

    public static String BaseTypeSection_caseTypeReference_label;

    public static String BaseTypeSection_StringLength_tooltip;

    public static String BaseTypeSection_UnknownDataType_longdesc;

    public static String BaseTypeSection_UnresolvedReference;

    public static String BaseTypeSection_UnsupportedDataType_longdesc;

    public static String BaseTypeSection_UnsupportedDataType_shortDesc;

    public static String DataFieldPropertySection_SetArrayType_menu;

    public static String DataFieldPropertySection_SetInitValue_menu;

    public static String DataFieldPropertySection_InitialValue_label;

    public static String DataFieldPropertySection_InitialValues_label;

    public static String DescriptionSection_description_label;

    public static String DescriptionSection_DocURL_label2;

    public static String DescriptionSection_SetDesc_menu;

    public static String DescriptionSection_SetDocURL_menu;

    public static String PackageInformationSection_author_label;

    public static String PackageInformationSection_version_label;

    public static String PackageInformationSection_costunit_label;

    public static String PackageInformationSection_language_label;

    public static String PackageInformationSection_created_label;

    public static String PackageInformationSection_desc_label;

    public static String PackageInformationSection_docLocation_label;

    public static String PackageInformationSection_SetAuthor_menu;

    public static String PackageInformationSection_SetCreated_menu;

    public static String PackageInformationSection_SetDescription_menu;

    public static String PackageInformationSection_SetDocumentation_menu;

    public static String PackageInformationSection_SetPubStatus_menu;

    public static String PackageInformationSection_SetVersion_menu;

    public static String PackageInformationSection_SetCostUnit_menu;

    public static String PackageInformationSection_SetLanguage_menu;

    public static String PackageInformationSection_status_label;

    public static String ParameterPropertySection_DefaultInitialValue_value;

    public static String ParameterPropertySection_DeleteFormalParameterCommand_msg;

    public static String ParameterPropertySection_in_label;

    public static String ParameterPropertySection_InitialValues_label;

    public static String ParameterPropertySection_inout_label;

    public static String ParameterPropertySection_InterfaceParam_label;

    public static String ParameterPropertySection_mode_label;

    public static String ParameterPropertySection_name_label;

    public static String ParameterPropertySection_Mandatory_label;

    public static String ParameterPropertySection_ReadOnly_label;

    public static String ParameterPropertySection_out_label;

    public static String ParameterPropertySection_RemoveInitialValueFromParameterCommand_msg;

    public static String ParameterPropertySection_SetParamMode_menu;

    public static String ParticipantPropertySection_DuplicateInPackage_longdesc;

    public static String ParticipantPropertySection_DuplicateInProcess_longdesc;

    public static String ParticipantPropertySection_DuplicateInEmbeddedSubProcess_longdesc;

    public static String ParticipantPropertySection_LeadSpaceNotAllowed_longdesc;

    public static String ParticipantPropertySection_NoEmptyName_longdesc;

    // XPD-3865: DO NOT REMOVE, USED IN DECISIONS FEATURE
    public static String ParticipantPropertySection_ReferenceSectionHeader_label;

    public static String ParticipantPropertySection_SetType_menu;

    // XPD-3865: DO NOT REMOVE, USED IN DECISIONS FEATURE
    public static String ParticipantPropertySection_SharedResourceSection_label;

    public static String ProcessDescriptionSection_DocumentationUrl_label;

    public static String ProcessDescriptionSection_SetProcessDescription_menu;

    public static String ProcessInternalViewUtil_OrgModel_Label;

    public static String TypeDeclarationPropertySection_DataFields_label;

    public static String TypeDeclarationPropertySection_DuplicateType_longdesc;

    public static String TypeDeclarationPropertySection_FormalParameters_label;

    public static String TypeDeclarationPropertySection_Processes_label;

    public static String TypeDeclarationPropertySection_ProcessInterfaces_label;

    public static String TypeDeclarationPropertySection_SetTypeDeclType_menu;

    public static String TypeDeclarationPropertySection_TypeDeclarations_label;

    public static String XpdlMigrate_incorrectPAckageVersion_label;

    public static String XpdlMigrate_migrationError_shortdesc;

    public static String XpdlMigrate_migrationFatalError_shortdesc;

    public static String XpdlMigrate_migrationWarning_shortdesc;

    public static String TextValidator_Invalid_Boolean_message;

    public static String TextValidator_Invalid_Date_message;

    public static String TextValidator_Invalid_ExtDate_message;

    public static String PickerUtil_projReferenceDialog_title;

    public static String PickerUtil_cyclicReference_longdesc1;

    public static String PickerUtil_projReferenceDialog_longdesc;

    public static String PickerUtil_errorInCheckingProRef_longdesc;

    public static String PickerUtil_setProjectReferenceWithoutAsking_label;

    public static String PickerUtil_pickComplexType_label;

    public static String PickerUtil_pickGlobalType_label;

    public static String ClearButton_label;

    public static String ParticipantType_Human_Label;

    public static String ParticipantType_Organizational_Unit_Label;

    public static String ParticipantType_Role_Label;

    public static String ParticipantType_System_Label;

    public static String InitialValueTable_MoveUp_Label;

    public static String InitialValueTable_MoveUp_Tooltip;

    public static String InitialValueTable_MoveDown_Label;

    public static String InitialValueTable_MoveDown_Tooltip;

    public static String InitialValueTable_Add_Label;

    public static String InitialValueTable_Add_Tooltip;

    public static String InitialValueTable_Delete_Label;

    public static String InitialValueTable_Delete_Tooltip;

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
