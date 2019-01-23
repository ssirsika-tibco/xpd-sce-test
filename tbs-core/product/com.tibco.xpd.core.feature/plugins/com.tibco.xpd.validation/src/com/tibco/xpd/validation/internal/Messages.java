/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.internal;

import org.eclipse.osgi.util.NLS;

/**
 * NLS message container for the core validation plugin.
 * 
 * @author nwilson
 */
public final class Messages extends NLS {
    /**
     * Private Constructor.
     */
    private Messages() {
    }

    /** The resource bundle id. */
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.validation.internal.messages"; //$NON-NLS-1$

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    public static String AbstractValidationPreferencePage_buildAll_label;

    public static String AbstractValidationPreferencePage_buildJob_label;

    public static String AbstractValidationPreferencePage_buildProject_label;

    public static String AbstractValidationPreferencePage_error_label;

    public static String AbstractValidationPreferencePage_errorWarningUpdateDialog_message;

    public static String AbstractValidationPreferencePage_errorWarningUpdateDialog_title;

    public static String AbstractValidationPreferencePage_ignore_label;

    public static String AbstractValidationPreferencePage_info_label;

    public static String AbstractValidationPreferencePage_warning_label;

    public static String ChangeOtherProjectIdResolution_changeOtherProject_resolution_label;

    public static String ChangeProjectIdResolution_changeLifecycleId_dialog_longdesc;

    public static String ChangeProjectIdResolution_changeLifecycleId_dialog_title;

    public static String ChangeProjectIdResolution_changeProjectId_resolution_label;

    public static String ChangeProjectIdResolution_idAlreadyUsed_error_shortdesc;

    public static String ChangeProjectIdResolution_idCannotBeBlank_error_shortdesc;

    public static String ChangeProjectIdResolution_idFormatIncorrect_error_shortdesc;

    public static String ChangeProjectIdResolution_natureCheckFail_error_message;

    public static String ChangeProjectIdResolution_saveProjectFail_error_message;

    public static String GenericMarkerResolution_0;

    public static String GenericMarkerResolution_errorsDuringQuickFix_error_message;

    public static String GenericMarkerResolution_workingCopySaveFailedAfterQuickFix_error_longdesc;

    public static String MigrateProjectResolution_migrationProblems_error_shortdesc;

    public static String NewOrImportProjectListener_projectBuildJob_label;

    public static String OpenReferencedProjectResolution_cannotOpenProject_error_shortdesc;

    public static String OpenReferencedProjectResolution_errorInOpenProjectResolution_error_shortdesc;

    public static String ProjectLifecycleValidator_errorDuringValidation_message;

    public static String ProjectLifecycleValidator_validateLifeCycleJob_title;

    public static String Provider_ruleException_error_message;

    public static String RemoveProjectReferenceResolution_errorInRemovingReference_error_shortdesc;

    public static String SpecialFolderResourceValidator_duplicateResource_error_longdesc;

    public static String SpecialFolderResourceValidator_projectCyclicDependency_error_shortdesc;

    public static String ValidationActivator_AddValidationListener_shortdesc;

    public static String ValidationActivator_RemoveValidationListener_shortdesc;

    public static String ValidationBuilder_0;

    public static String ValidationBuilder_2;

    public static String ValidationBuilder_3;

    public static String ValidationBuilder_calculatingDependencies_monitor_shortdesc;

    public static String ValidationBuilder_calculatingDependencies_progress_message;

    public static String ValidationBuilder_indexing_monitor_shortdesc;

    public static String ValidationBuilder_validating_progress_message;

    public static String ValidationEngine_DestinationColonMessage;

    public static String ValidationEngine_liveValidationJob_label;

    public static String ValidationEngine_MessageInBrackets;

    public static String ValidationEngine_Validation;

    public static String ValidationEngine_validation_job_label;

    public static String ValidationManager_BadResolutionRefInIssue_description;

    public static String ValidationManager_BadReusableResolutionContribution_description;

    public static String ValidationManager_DuplicateResolution_description;

    public static String ValidationManager_invalidResolution_message;

    public static String ValidationManager_preProcessorClassNotFound_message;

    public static String ValidationManager_resolutionNotImplementingIResolution_message;

    public static String ValidationManager_ruleClassNotFound_message;

    public static String ValidationManager_ruleClassNotImplementingIValidationRule_message;

    public static String ValidationManager_scopeProviderNotFound_message;

    public static String ValidationProblemUtil_empty_label;

    public static String ValidationProblemUtil_location_label;

    public static String ValidationProblemUtil_message_label;

    public static String ValidationProblemUtil_project_label;

    public static String ValidationProblemUtil_topError_message;

    public static String Validator_0;

    public static String SetSvnDeferFileDeleteResolution_SettinSvnProp_message;

    public static String SetSvnDeferFileDeleteResolution_SvnCommitWarning_title;

    public static String SetSvnDeferFileDeleteResolution_SvnCommit_message;

}
