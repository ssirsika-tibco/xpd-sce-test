package com.tibco.xpd.n2.globalsignal.doc.xslt.messages;

import org.eclipse.osgi.util.NLS;

/**
 * Messages for GSD Documentation export.
 * 
 * 
 * @author kthombar
 * @since Apr 28, 2015
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.n2.globalsignal.doc.xslt.messages.messages"; //$NON-NLS-1$

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }

    public static String GsdFile_label;

    public static String Description_label;

    public static String QuickLinks_label;

    public static String GlobalSignals_label;

    public static String GlobalSignal_label;

    public static String PayloadData_label;

    public static String Name_label;

    public static String Correlation_label;

    public static String CorrelationImmediately_label;

    public static String TimeoutSignalAfter_label;

    public static String Seconds_label;

    public static String UseForSignalSorrelation_label;

    public static String Mandatory_label;

    public static String PayloadDataType_label;

    public static String PayloadLength_label;

    public static String DataTypeString_label;

    public static String DataTypeInteger_label;

    public static String DataTypeFloat_label;

    public static String DataTypeBoolean_label;

    public static String DataTypePerformer_label;

    public static String DataTypeDateTime_label;

    public static String Unspecified_label;

    public static String DataTypeDate_label;

    public static String DataTypeTime_label;

}
