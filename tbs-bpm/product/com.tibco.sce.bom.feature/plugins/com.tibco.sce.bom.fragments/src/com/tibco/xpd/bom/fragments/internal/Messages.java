package com.tibco.xpd.bom.fragments.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.bom.fragments.internal.messages"; //$NON-NLS-1$
    public static String BOMFragmentsContributor_createFragment_monitor_shortdesc;
    public static String BOMFragmentsContributor_creatingSampleCategory_monitor_shortdesc;
    public static String BOMFragmentsContributor_creatingSampleFragments_monitor_shortdesc;
    public static String BOMFragmentsContributor_deleteExisingCategories_monitor_shortdesc;
    public static String BOMFragmentsContributor_fragmentsInitFailure_error_title;
    public static String BOMFragmentsContributor_updateFragments_monitor_shortdesc;
    public static String BOMFragmentTransferDropTargetListener_dropFragment_command_label;
    public static String BOMFragmentTransferDropTargetListener_failedToCreateFragment_errorDialog_message;
    public static String BOMFragmentTransferDropTargetListener_failedToCreateFragment_errorDialog_title;
    public static String BOMFragmentTransferDropTargetListener_failedToLoadFragment_error_log_shortdesc;
    public static String BOMFragmentTransferDropTargetListener_fragmentPasteFail_error_log_shortdesc;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
