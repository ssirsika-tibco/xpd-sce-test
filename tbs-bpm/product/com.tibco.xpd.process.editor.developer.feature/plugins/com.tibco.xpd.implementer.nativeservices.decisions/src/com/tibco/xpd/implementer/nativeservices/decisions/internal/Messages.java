package com.tibco.xpd.implementer.nativeservices.decisions.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.implementer.nativeservices.decisions.internal.messages"; //$NON-NLS-1$

    public static String DecFlowServiceTaskScriptProvider_DescribeDecFlowInputMappingScript;

    public static String DecFlowServiceTaskScriptProvider_DescribeDecFlowOutputMappingScript;

    public static String DecisionFlowToBpmProcessDropContribution_ConvertToInvokeDecSvc_menu;

    public static String DecisionFlowToBpmProcessDropContribution_CreateTask;

    public static String DecisionServiceSection_DECFLOW_LOCATION_LABEL;

    public static String DecisionServiceSection_DECFLOW_NAME_LABEL;

    public static String DecisionServiceSection_DecFlowInvocation_hyperlink_action;

    public static String DecisionServiceSection_DecFlowInvocation_hyperlink_desc;

    public static String DecisionServiceSection_ELIPSES;

    public static String DecisionServiceSection_MustSelectADecFlow_tooltip;

    public static String DecisionServiceSection_THE_SAME_PACKAGE;

    public static String DecisionServiceSection_UNDEFINED;

    public static String DecisionServiceSection_UnresolvedReferences_message;
    public static String DecisionServiceTaskHyperlinkHandler_DecisionFlowNotSet_tooltip2;

    public static String DecisionServiceTaskHyperlinkHandler_OpenDecisionFlowHyperlink_tooltip;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
