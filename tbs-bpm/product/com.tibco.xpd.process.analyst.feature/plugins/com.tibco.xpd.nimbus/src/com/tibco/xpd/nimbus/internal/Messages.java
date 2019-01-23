/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.nimbus.internal;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * 
 * @author aallway
 * @since 25 Oct 2011
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.nimbus.internal.messages"; //$NON-NLS-1$

    public static String ImportProcessMapSimplifiedExportValidator_FileIsValidExportFormat_message;

    public static String ImportProcessMapSimplifiedExportValidator_NonSimpleDiagrams_message;

    public static String ImportProcessMapValidator_MustHaveOneDiagram_message;

    public static String ImportNimbusPostImportSubTask_PerformingPostImprotTasks_message;

    public static String ImportNimbusProcessMapValidationPage_ValidatingCorrectType_message;

    public static String ImportNimbusProcessMapValidationPage_ValidationCancelled_message;

    public static String ImportNimbusProcessMapValidator_NotANimbusSimplifiedExport_message;

    public static String ImportNimbusProcessMapWizard_Import_description;

    public static String ImportNimbusProcessMapWizard_Import_title;

    public static String ValidateImportPage_AllFilesValid_message;

    public static String ValidateImportPage_ErorsValdiating_label;

    public static String ValidateImportPage_File_label;

    public static String ValidateImportPage_Message_label;

    public static String ValidateImportPage_Progress_label;

    public static String ValidateImportPage_ValidateProcessMaps_title;

    public static String ValidateImportPage_ValidateImportFiles_title;

    public static String ValidateImportPage_Validating_label;

    public static String ValidateImportPage_ValidationSuccess_message;

    public static String ValidateImportPage_ValidationWarnings_label;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
