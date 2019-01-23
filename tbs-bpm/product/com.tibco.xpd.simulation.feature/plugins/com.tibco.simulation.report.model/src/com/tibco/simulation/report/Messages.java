package com.tibco.simulation.report;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.simulation.report.messages"; //$NON-NLS-1$

    public static String SimRepDistributionCategory_0;

    public static String SimRepDistributionCategory_1;

    public static String SimRepDistributionCategory_2;

    public static String SimRepDistributionCategory_3;

    public static String SimRepExperimentState_0;

    public static String SimRepExperimentState_1;

    public static String SimRepExperimentState_2;

    public static String SimRepExperimentState_3;

    public static String SimRepExperimentState_4;

    public static String SimRepQueueOrder_0;

    public static String SimRepQueueOrder_1;

    public static String SimRepReferenceTimeUnit_0;

    public static String SimRepReferenceTimeUnit_1;

    public static String SimRepReferenceTimeUnit_2;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
