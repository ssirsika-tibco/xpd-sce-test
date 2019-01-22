package com.tibco.xpd.quickfixtooltip.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.quickfixtooltip.internal.messages"; //$NON-NLS-1$

    public static String QuickFixToolTipControl_GotoProblemLocation_tooltip;

    public static String QuickFixToolTipControl_Next_tooltip;

    public static String QuickFixToolTipControl_NumErrors_tooltip;

    public static String QuickFixToolTipControl_NumInfos_tooltip;

    public static String QuickFixToolTipControl_NumQuickFixesFormatMsg_label;

    public static String QuickFixToolTipControl_NumWarnings_tooltip;

    public static String QuickFixToolTipControl_OneQuickFixAvailable_label;

    public static String QuickFixToolTipControl_Previous_tooltip;

    public static String QuickFixToolTipControl_ProblemMarkerNotAvailable_message;

    public static String QuickFixToolTipControl_SelectNext_tooltip;

    public static String QuickFixToolTipControl_SelectPrev_tooltip;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
