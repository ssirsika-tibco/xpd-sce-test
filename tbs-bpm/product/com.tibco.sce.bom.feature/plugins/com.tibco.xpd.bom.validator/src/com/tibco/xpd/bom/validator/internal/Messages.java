/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.bom.validator.internal;

import org.eclipse.osgi.util.NLS;

/**
 * @author wzurek
 * 
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.bom.validator.internal.messages"; //$NON-NLS-1$

    public static String AbstractRenameResolution_rename_title;

    public static String AssociationTypeChangeResolution_setUnidirectionalComposition_command_label;

    public static String BOMMigrationUtil_cannotDetermineVersion_error_shortdesc;

    public static String BOMMigrationUtil_migrationCommand_shortdesc;

    public static String BOMMigrationUtil_problemMigrating_error_shortdesc;

    public static String BOMMigrationUtil_problemSaving_error_shortdesc;

    public static String BOMMigrationUtil_unableToMigrateVersions_error_shortdesc;

    public static String BrokenReferenceResolution_brokenReferenceDialog_title;

    public static String BrokenReferenceResolution_doNotShowDialog_label;

    public static String BrokenReferenceResolution_generalization_shortdesc;

    public static String BrokenReferenceResolution_manuallyResolve_message;

    public static String BrokenReferenceResolution_manuallyResolveWithName_message;

    public static String BrokenReferenceResolution_propertyType_shortdesc;

    public static String BrokenReferenceResolution_reference_shortdesc;

    public static String BrokenReferencesRule_elementType_label;

    public static String BrokenReferencesRule_generalization_label;

    public static String ConceptValidationPreferencePage_page_message;

    public static String InvalidBOMFileResolutionGenerator_longdesc;

    public static String InvalidBOMFileResolutionGenerator_migrationError_dialog_shortdesc;

    public static String InvalidBOMFileResolutionGenerator_migrationError_dialog_title;

    public static String InvalidBOMFileResolutionGenerator_shortdesc;

    public static String PrimitiveTypeRestrictions_DecimalPlaces_label;

    public static String PrimitiveTypeRestrictions_LowerLimit_label;

    public static String PrimitiveTypeRestrictions_MaxTextLength_label;

    public static String PrimitiveTypeRestrictions_NumberLen_label;

    public static String PrimitiveTypeRestrictions_UpperLimit_label;

    public static String PropertyMultiplicityRule_LowerLimitExceedsUpper_label;

    public static String PropertyMultiplicityRule_UpperLimitEqualsZero_label;

    public static String RenameConceptResolution_concept_name_wrong_format_message;

    public static String RenameConceptResolution_name_required_message;

    public static String RenameConceptResolution_rename_concept_action;

    public static String RenamePackageResolution_name_required_message;

    public static String RenamePackageResolution_package_name_wrong_format_message;

    public static String RenamePackageResolution_rename_package_message;

    public static String ValidationOptionsPreferencePage_ApplyingChangeWillTriggerFullBuild_longdesc;

    public static String ValidationOptionsPreferencePage_ApplyingChangeWillTriggerProjBuild_longdesc;

    public static String ValidationOptionsPreferencePage_ConfigureProjectSettings_label;

    public static String ValidationOptionsPreferencePage_ConfigureWorkspaceSettings_label;

    public static String ValidationOptionsPreferencePage_EnableProjSetting_label;

    public static String ValidationOptionsPreferencePage_EnableWsdlExportGen_button;

    public static String ValidationOptionsPreferencePage_EnableXsdExport_button;

    public static String ValidationOptionsPreferencePage_ProjectSpecificConfiguration_label;

    public static String ValidationOptionsPreferencePage_ProjectToConfig_label;

    public static String ValidationOptionsPreferencePage_SameCapabilityMayBeEnabledByDestinations_longdesc;

    public static String ValidationOptionsPreferencePage_ShowOnlyProjectsWithSettings_button;

    public static String ValidationOptionsPreferencePage_ValidationBuild_label;

    public static String ValidationOptionsPreferencePage_ValidationOptions_label;

    public static String ValidationOptionsPreferencePage_ValidateXSDOption_label;

    public static String ValidationOptionsPreferencePage_ValidateXSDOption_button;

    // Global Data (Moved from separate plug-in)
    public static String AggregationEntity_partclass_issue_arg_message;

    public static String AssociationTypesRule_sourceclass_issue_arg_message;

    public static String AssociationTypesRule_targetclass_issue_arg_message;

    public static String AggregationEntity_wholeclass_issue_arg_message;

    public static String ClassStereotypes_case_type_issue_arg_message;

    public static String ClassStereotypes_global_type_issue_arg_message;

    public static String ClassStereotypes_local_type_issue_arg_message;

    public static String CompositionEndsRule_association_relationship_label;

    public static String GeneralizationEndsRule_generalization_relationship_label;

    public static String GeneralizationTypesRule_subclass_issue_arg_message;

    public static String GeneralizationTypesRule_superclass_issue_arg_message;

    public static String RemoveAttributeCompositionResolution_command_label;

    public static String RemoveRelationshipResolution_command_label;

    
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
