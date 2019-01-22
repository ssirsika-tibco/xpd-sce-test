/**
 * 
 */
package com.tibco.xpd.bom.wsdltransform.internal;

import org.eclipse.osgi.util.NLS;

/**
 * @author glewis
 * 
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.bom.wsdltransform.internal.messages"; //$NON-NLS-1$

    public static String BOM2WSDLTransformer_errorInTransformation_error_message;

    public static String BOM2WSDLTransformer_exporting_progress_shortdesc;

    public static String BOM2WSDLTransformer_problemGeneringWSDL_error_message;

    public static String BOM2WSDLTransformer_unableToLoadSchema_error_message;

    public static String BOM2WSDLTransformer_validationFailed_error_message;

    public static String CheckValidSchemaAndConstructsValidationRule_wsdlContainsUnsupportedConstructs_error_message;

    public static String default_bomfolder_name;

    public static String ExportToWSDLWizard_WindowTitle;

    public static String ExportBOMToWSDLWizard_generatorErrors_shortdesc;

    public static String ExportBOMToWSDLWizard_shortdesc;

    public static String XtendTransformer_cannot_validate_external_wsdl_message;

    public static String XSDValidation_removeMarkers_shortdesc;

    public static String ImportWSDLWizard_importWSDLWizard_title;

    public static String ImportWSDLWizard_destinationNotBOMFolder_warning_shortdesc;

    public static String ImportWSDLWizard_destinationProject_error_shortdesc;

    public static String ImportWSDLWizard_selectFolderDialog_title;

    public static String ImportWSDLWizard_selectFolderDialog_shortdesc;

    public static String ImportWSDLWizard_importWSDLError_message;

    public static String ExportClassToWSDLWizard_exportErrorDialog_shortdesc;

    public static String ExportClassToWSDLWizard_exportErrorDialog_title;

    public static String ExportClassToWSDLWizard_exportingClass_progress_shortdesc;

    public static String ExportClassToWSDLWizard_fileExists_errorDialog_shortdesc;

    public static String ExportClassToWSDLWizard_fileExists_errorDialog_title;

    public static String ExportClassToWSDLWizard_generatorErrors_shortdesc;

    public static String ExportClassToWSDLWizard_preserveSchemas_button;

    public static String ExportClassToWSDLWizard_shortdesc;

    public static String ExportClassToWSDLAction_title;

    public static String ExportClassToWSDLAction_shortdesc;

    public static String ImportWSDLWizard_progress_label;

    public static String MissingBomAssetResolutionGenerator_AddBOMfolderResolution_label;

    public static String RemoveGenBOMPostImportProjectTask_BomWillBeRegeneratedOnImport;

    public static String RemoveGenBOMPostImportProjectTask_invalidBomVersionFound;

    public static String RemoveGenBOMPostImportProjectTask_checkingGenBOM_monitor_shortdesc;

    public static String WsdlBDSSupportResolution_closeEditor_dialog_title;

    public static String WsdlBDSSupportResolution_closingEditor_progress_shortdesc;

    public static String WsdlBDSSupportResolution_editorNeedsClosing_message;

    public static String WsdlBDSSupportResolution_ErrorApplyingChange_dialog_title;

    public static String WsdlBDSSupportResolution_fileNeedsSaveAndEditorClosed_message;

    public static String WsdlBDSSupportResolution_problemClosingEditor_error_message;

    public static String WsdlBDSSupportResolution_problemClosingEditor_error_title;

    public static String WsdlBDSSupportResolution_problemDisablingBDSSupport_error_message;

    public static String WsdlBDSSupportResolution_problemEnablingBDSSupport_error_message;

    public static String WsdlBDSSupportResolution_saveAndCloseEditor_dialog_title;

    public static String WsdlBDSSupportResolution_savingAndClosingEditor_progress_shortdesc;

    public static String WSDL2BOMTransformer_expectedWSDLResource_error_message;

    public static String WSDL2BOMTransformer_cyclicDependency_error_message_1;

    public static String WSDL2BOMTransformer_duplicate_resolved_namespace_shortdesc;

    public static String WSDLExportSelectionValidator_destination_error_shortdesc;

    public static String WSDLExportSelectionValidator_errorCheckingBuilders_error_message;

    public static String WSDLExportSelectionValidator_problemsValidating_error_message;

    public static String WSDLExportSelectionValidator_validation_error_shortdesc;

    public static String WSDLExportSelectionValidator_validationBuilderNotEnabled_error_message;

    public static String WSDLTransformUtil_wsdlHasCyclicImport_error_longdesc;

    public static String TransformHelper_wsdl_contains_multiple_output_parts_message;

    public static String TransformHelper_wsdl_services_will_be_ignored_message;

    public static String TransformHelper_wsdl_binding_faults_will_be_ignored_message;

    public static String TransformHelper_wsdl_contains_wsdl_import_message;

    public static String TransformHelper_wsdl_does_not_support_schemas_without_targetnamespaces_message;

    public static String WsdlRenameCaseInsensitiveTargetNamespaceResolution_lowercase_targetnamespaces_shortdesc;

    public static String WsdlToBomBuilder_missingBomAsset_error_message;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
