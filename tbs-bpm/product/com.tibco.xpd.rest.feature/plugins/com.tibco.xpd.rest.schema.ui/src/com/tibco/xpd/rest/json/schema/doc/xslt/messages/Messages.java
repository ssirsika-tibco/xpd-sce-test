package com.tibco.xpd.rest.json.schema.doc.xslt.messages;

import org.eclipse.osgi.util.NLS;

/**
 * Messages for JSON Documentation export.
 * 
 * 
 * @author kthombar
 * @since June 04, 2015
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.rest.json.schema.doc.xslt.messages.messages"; //$NON-NLS-1$

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }

    public static String JsonSchema_label;

    public static String QuickLinks_label;

    public static String JSONType_label;

    public static String Type_label;

    public static String Name_label;

    public static String RootJsonSchema_label;

    public static String SchemaProperties_label;

    public static String Mandatory_label;

    public static String Array_label;

    public static String DataTypeString_label;

    public static String DataTypeInteger_label;

    public static String DataTypeFloat_label;

    public static String DataTypeBoolean_label;

    public static String DataTypeDateTime_label;

    public static String Unspecified_label;

    public static String DataTypeDate_label;

    public static String DataTypeTime_label;

    public static String DataTypeDateTimeTimeZone_label;

}
