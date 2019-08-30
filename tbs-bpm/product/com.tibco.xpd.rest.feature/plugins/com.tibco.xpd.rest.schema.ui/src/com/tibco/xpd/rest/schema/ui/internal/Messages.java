/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.internal;

import org.eclipse.osgi.util.NLS;

/**
 * Messages for internationalisation.
 * 
 * @author nwilson
 * @since 13 Jan 2015
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.rest.schema.ui.internal.messages"; //$NON-NLS-1$

    public static String CopyPasteActionHandler_CopyNOf_name;

    public static String CopyPasteActionHandler_CopyOf_name;

    public static String CopyPasteActionHandler_CutAction;

    public static String CopyPasteActionHandler_PasteAction;

    public static String ImportJsonSchemaWizard_pageDescription;

    public static String ImportJsonSchemaWizard_pageTitle;

    public static String ImportJsonSchemaWizard_windowTitle;

    public static String JsonDocXsltGeneration_JsonSchemaFolder_title;

    public static String JsonDocXsltGeneration_JsonSchemaModelContainer_title;

    public static String JsonSampleImport_importExecutionError;

    public static String JsonSampleImport_jsonParsingError;

    public static String JsonSchemaClassSection_DuplicateTypeNotAllowedError_msg;

    public static String JsonSchemaClassSection_EmptyTypeNotAllowedError_msg;

    public static String JsonSchemaEditor_addClassCommand;

    public static String JsonSchemaEditor_addClassTooltip;

    public static String JsonSchemaEditor_addPropertyCommand;

    public static String JsonSchemaEditor_addPropertyTooltip;

    public static String JsonSchemaEditor_classesHeader;

    public static String JsonSchemaEditor_newClassBaseName;

    public static String JsonSchemaEditor_newPropertyBaseName;

    public static String JsonSchemaEditor_ReadOnly;

    public static String JsonSchemaEditor_removeClassCommand;

    public static String JsonSchemaEditor_removeClassTooltip;

    public static String JsonSchemaEditor_removePropertyCommand;

    public static String JsonSchemaEditor_removePropertyTooltip;

    public static String JsonSchemaEditor_schemaHeader;

    public static String JsonSchemaEditor_title;

    public static String JsonSchemaEditorContributor_missingActionHandler;

    public static String JsonSchemaEditorDetails_arrayLabel;

    public static String JsonSchemaEditorDetails_classNameCommand;

    public static String JsonSchemaEditorDetails_classNameLabel;

    public static String JsonSchemaEditorDetails_classNameTooltip;

    public static String JsonSchemaEditorDetails_mandatoryLabel;

    public static String JsonSchemaEditorDetails_propertyNameCommand;

    public static String JsonSchemaEditorDetails_propertyNameLabel;

    public static String JsonSchemaEditorDetails_propertyNameTooltip;

    public static String JsonSchemaEditorDetails_propertyTypeLabel;

    public static String JsonSchemaEditorDetails_setArrayCommand;

    public static String JsonSchemaEditorDetails_setMandatoryCommand;

    public static String JsonSchemaEditorDetails_unsetArrayCommand;

    public static String JsonSchemaEditorDetails_unsetMandatoryCommand;

    public static String JsonSchemaEditorPart_contentInvalid;

    public static String JsonSchemaEditorPart_fileUnsupported;

    public static String JsonSchemaEditorPart_invalidInputType;

    public static String JsonSchemaFilePage_defaultJsonSchemaFileName;

    public static String JsonSchemaFilePage_Description;

    public static String JsonSchemaFilePage_Title;

    public static String JsonSchemaImportPage_browseJsonSampleButton;

    public static String JsonSchemaImportPage_browseJsonSchemaButton;

    public static String JsonSchemaImportPage_importJsonSampleButton;

    public static String JsonSchemaImportPage_importJsonSampleTooltip;

    public static String JsonSchemaImportPage_importJsonSchemaButton;

    public static String JsonSchemaImportPage_importJsonSchemaTooltip;

    public static String JsonSchemaImportPage_noSampleError;

    public static String JsonSchemaImportPage_noSampleFileError;

    public static String JsonSchemaImportPage_noSchemaFileError;

    public static String JsonSchemaImportPage_pageTitle;

    public static String JsonSchemaImportPage_pasteJsonSampleButton;

    public static String JsonSchemaImportPage_pasteJsonSampleTooltip;

    public static String JsonSchemaPropertySection_DuplicatePropertyNameError_msg;

    public static String JsonSchemaPropertySection_EmptyPropertyNameNotAllowedError_msg;

    public static String JsonSchemaPropertySection_PropertyNameInvalidError_msg;

    public static String JsonSchemaPropertySection_PropertyTypeNotSet_Error_msg;

    public static String JsonSchemaPropertySection_InvalidReference_Error_msg;

    public static String JsonSchemaPropertySection_MissingProjectReference_Error_msg;

    public static String JsonSchemaTypePicker_clearJsonType;

    public static String JsonSchemaTypePicker_PropertyType;

    public static String JsonSchemaTypePicker_setJsonType;

    public static String NewJsonSchemaWizard_filePageDescription;

    public static String NewJsonSchemaWizard_filePageTitle;

    public static String ResourceCopier_CreatingStylesheetProgressMonitor_msg;

    public static String ResourceCopier_FailedToCreateFolderError_msg;

    public static String ResourceCopier_UnableToCreateFolderError_msg;

    public static String UmlJsonSchemaTitleLabelProvider_Class;

    public static String UmlJsonSchemaTitleLabelProvider_Property;

    public static String UmlTypePickerContentProvider_JsonPayloadLabel;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
