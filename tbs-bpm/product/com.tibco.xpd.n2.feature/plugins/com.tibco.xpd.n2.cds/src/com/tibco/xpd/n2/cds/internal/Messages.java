package com.tibco.xpd.n2.cds.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.n2.cds.internal.messages"; //$NON-NLS-1$

    public static String BOMCacheDeleteParticipant_DeleteParticipant_name;

    public static String BOMCacheDeleteParticipant_DeleteParticipantChange_name;

    public static String CDSCompositeContributor_AddingRuntimeBundles_message;

    public static String CDSCompositeContributor_BDSIOException_message;

    public static String CDSCompositeContributor_BDSProjectsMissing_error_message_2;

    public static String CDSCompositeContributor_CheckingSourceBoms_message;

    public static String CDSCompositeContributor_JarExportFailed_message;

    public static String CDSCompositeContributor_JarGatherFailure_message;

    public static String CDSCompositeContributor_JarNotFound_message;

    public static String CDSCompositeContributor_PDEExportJobInterrupted_message;

    public static String CDSCompositeContributor_PluginModelNotFound_message;

    public static String CDSCompositeContributor_PreparingCustomFeature_message;

    public static String CDSCompositeContributor_ProjectNotFound_message;

    public static String CustomFeatureExportJob_ErrorsDuringExport_message;

    public static String CustomFeatureExportJob_ExportPlugins_message;

    public static String CustomFeatureExportJob_ProblemDuringExport_message;

    public static String CustomFeatureExportJob_CyclicDependency_message;

    public static String CustomFeatureManager_generated_java_models_monitor_message;

    public static String CustomFeatureManager_user_cancelled_java_model_gen_monitor_message;

    public static String CustomFeatureManager_java_model_not_found_monitor_message;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
