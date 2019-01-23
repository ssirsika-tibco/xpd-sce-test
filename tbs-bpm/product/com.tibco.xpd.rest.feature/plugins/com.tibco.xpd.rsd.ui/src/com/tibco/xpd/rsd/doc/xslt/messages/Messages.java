package com.tibco.xpd.rsd.doc.xslt.messages;

import org.eclipse.osgi.util.NLS;

/**
 * Messages for REST Documentation export.
 * 
 * 
 * @author kthombar
 * @since June 04, 2015
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.rsd.doc.xslt.messages.messages"; //$NON-NLS-1$

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }

    public static String RestServiceDescriptor_label;

    public static String QuickLinks_label;

    public static String Resources_label;

    public static String ContextPath_label;

    public static String MediaType_label;

    public static String StandardJsonMediaType_label;

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

    public static String ServiceName_label;

    public static String PathTemplate_label;

    public static String PathParams_label;

    public static String Methods_label;

    public static String HttpMethod_label;

    public static String HttpMethodGET_label;

    public static String Method_label;

    public static String Request_label;

    public static String Response_label;

    public static String Fault_label;

    public static String Payload_label;

    public static String Value_label;

    public static String Content_label;

    public static String DefaultContentTypeValue;

    public static String JsonType_label;

    public static String StatusCode_label;

    public static String HeaderParameters_label;

    public static String QueryParameters_label;

    public static String DefaultValue_label;

}
