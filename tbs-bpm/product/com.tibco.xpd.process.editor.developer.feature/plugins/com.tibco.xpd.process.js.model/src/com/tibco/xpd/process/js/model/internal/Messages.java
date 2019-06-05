/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.process.js.model.internal;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * 
 * @author aallway
 * @since 3.3 (12 Feb 2010)
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.process.js.model.internal.messages"; //$NON-NLS-1$

    public static String AceScriptProcessDataWrapperObject_ProcessDataWrapperObject_description;

    public static String ProcessJavaScriptInfoProvider_AddInstsLoopScriptHeader_label;

    public static String ProcessJavaScriptInfoProvider_AutomaticAdhocTaskPreconditionScriptEnablement_label;

    public static String ProcessJavaScriptInfoProvider_ManualAdhocTaskPreconditionScriptEnablement_label;

    public static String ProcessJavaScriptInfoProvider_CancelledScriptHeader_label;

    public static String ProcessJavaScriptInfoProvider_CompletedScriptHeader_label;

    public static String ProcessJavaScriptInfoProvider_ComplexExitLoopScriptHeader_label;

    public static String ProcessJavaScriptInfoProvider_DeadloineExpiredScriptHeader_label;

    public static String ProcessJavaScriptInfoProvider_InitiatedScriptHeader_label;

    public static String ProcessJavaScriptInfoProvider_NumInstLoopScriptHeader_label;

    public static String ProcessJavaScriptInfoProvider_ParticSelectScriptHeader_label;

    public static String ProcessJavaScriptInfoProvider_RescheduleScrptHeader_label;

    public static String ProcessJavaScriptInfoProvider_RescheduleTimerScriptheader_label;

    public static String ProcessJavaScriptInfoProvider_SeqFlowScriptHeader_label;

    public static String ProcessJavaScriptInfoProvider_ServTaskScriptHeader_label;

    public static String ProcessJavaScriptInfoProvider_StdLoopBefore_label;

    public static String ProcessJavaScriptInfoProvider_StdLoopAfter_label;

    public static String ProcessJavaScriptInfoProvider_TaskScriptHeader_label;

    public static String ProcessJavaScriptInfoProvider_TimeoutScriptScriptHeader_label;

    public static String ProcessJavaScriptInfoProvider_UTCloseScriptHeader_label;

    public static String ProcessJavaScriptInfoProvider_UTOpenScriptHeader_label;

    public static String ProcessJavaScriptInfoProvider_UTSubmitScriptHeader_label;

    public static String ProcessJavaScriptInfoProvider_ScheduledScriptHeader_label;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
