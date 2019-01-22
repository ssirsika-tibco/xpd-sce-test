package com.tibco.xpd.bom.gen.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.bom.gen.internal.messages"; //$NON-NLS-1$

    public static String BOMGenDeleteParticipant_change_name;

    public static String BOMGenDeleteParticipant_name;

    public static String BOMGenerator2_creatingProjects_monitor_shortdesc;

    public static String BOMGenerator2_errorDuringArchive_error_shortdesc;

    public static String BOMGenerator2_fileExists_dialog_message;

    public static String BOMGenerator2_fileExists_dialog_title;

    public static String BOMGenerator2_projectAlreadyExists_error_message;

    public static String BOMGenerator2_strategyHasReporterErrors_error_shortdesc;

    public static String BOMGeneratorHelper_BOMGenFailed_error_longdesc;

    public static String BOMGeneratorHelper_cyclicDependency_error_shortdesc;

    public static String BOMGeneratorHelper_CyclicDependency_message;

    public static String BOMGeneratorHelper_unexpectedError_seeEventDetails_error_longdesc;

    public static String BOMGenProjectBuilder_executeStrategy_shortdesc;

    public static String BOMGenProjectBuilder_generator_progress_shortdesc;

    public static String BOMGenProjectBuilder_generationFailed_problemMarker_shortdesc1;

    public static String BOMGenProjectBuilder_unexpecterError_shortdesc;

    public static String BOMGenProjectBuilder_validationErrorReporter_error_shortdesc;

    public static String BOMGenerator2ExtensionHelper_BDS_model_bundle_notfound_info_message;

    public static String BOMGenerator2ExtensionHelper_BOM_model_empty_info_message;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
