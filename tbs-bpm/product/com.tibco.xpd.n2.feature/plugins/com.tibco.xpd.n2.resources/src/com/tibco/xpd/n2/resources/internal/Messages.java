/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.n2.resources.internal;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * 
 * @author aallway
 * @since 13 Jun 2011
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.n2.resources.internal.messages"; //$NON-NLS-1$

    public static String AbstractMigrationPointAnnotationFigure_DecreaseVisibility_button;

    public static String AbstractMigrationPointAnnotationFigure_IncreaseVisibility_button;

    public static String AbstractMigrationPointAnnotationFigure_MigrationPoint_tooltip;

    public static String AdhocPreconditionScriptBOMReferenceChange_AdhocPreconditionScript_label;

    public static String AdvancedTaskRetryProperties_ErrorDropDownValue;

    public static String AdvancedTaskRetryProperties_HaltDropDownValue;

    public static String AdvancedTaskRetryProperties_MaximumRetryAction;

    public static String AdvancedTaskRetryProperties_RetryDefaultPropertyValue;

    public static String AdvancedTaskRetryProperties_RetryInitialPeriod;

    public static String AdvancedTaskRetryProperties_RetryMax;

    public static String AdvancedTaskRetryProperties_RetryPeriodIncrement;

    public static String AdvancedTaskRetryProperties_UnspecifiedDropDownValue;

    public static String IProcessScriptToBPMJavaScriptConverter_ConvertDateTimeOffsetConstant_message;

    public static String IProcessScriptToBPMJavaScriptConverter_OrderOfDateConstantFields_message;

    public static String AdvancedCorrelationTimeoutProperties_Days;

    public static String AdvancedCorrelationTimeoutProperties_Hours;

    public static String AdvancedCorrelationTimeoutProperties_Minutes;

    public static String AdvancedCorrelationTimeoutProperties_Seconds;

    public static String AdvancedPropertiesFaultConfiguration_ErrorDropDownValue;

    public static String AdvancedPropertiesFaultConfiguration_HaltDropDownValue;

    public static String AdvancedPropertiesFaultConfiguration_SystemErrorAction;

    public static String AdvancedPropertiesFaultConfiguration_UnspecifiedDropDownValue;

    public static String MigrationPointHighlighterContribution_HighlightMigrationPointActivities_menu;

    public static String MigrationPointHighlighterContribution_MigrationPointHighlighter_tooltip;

    public static String SubProcessTaskPriorityDataRefResolver_SubProcessPriorityDataRefContext_label2;

    public static String UserTaskOverwriteModifiedDataAdvancedProperty_SetOverwriteData_menu;

    public static String UseUnqualifiedCorrelationPropertyNameAdvProp_SetUnsetProperty_menu;

    public static String AllowUnqualifiedSubProcIdAdvProp_SetUnsetUnqualifiedNamesCommand_label;

    public static String FlowAnalyzerPreferenceContributor_AnalyzerTimeoutEditor_tooltip;

    public static String FlowAnalyzerPreferenceContributor_BPMProcessesGroup_label;

    public static String FlowAnalyzerPreferenceContributor_FlowAnalyzerTimeout_error;

    public static String FlowAnalyzerPreferenceContributor_FlowAnalyzerTimeout_tooltip;

    public static String FlowAnalyzerPrefernceContributor_FLowAnalyzerPrefPage_Timeout_label;

    public static String N2LocalDevelopmentServer_Name;

    public static String RemoveGenProjectReferencesPostImportTask_RemovingGeneratedProjectReferences_message;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
