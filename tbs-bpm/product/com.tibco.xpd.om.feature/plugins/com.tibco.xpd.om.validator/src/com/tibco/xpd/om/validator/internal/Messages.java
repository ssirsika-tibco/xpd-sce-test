package com.tibco.xpd.om.validator.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.om.validator.internal.messages"; //$NON-NLS-1$

    public static String SetUniqueGroupNameResolution_blankLabel_error_message;

    public static String SetUniqueGroupNameResolution_blankName_error_message;

    public static String SetUniqueGroupNameResolution_group_prefix_label;

    public static String SetUniqueGroupNameResolution_labelAlreadyExists_error_message;

    public static String SetUniqueGroupNameResolution_nameAlreadyExists_error_message;

    public static String SetUniqueGroupNameResolution_nameCanOnlyContainAlphaNumeric_error_message;

    public static String SetUniqueGroupNameResolution_NoIssueId_error_message;

    public static String SetUniqueGroupNameResolution_renameGroupLabel_message;

    public static String SetUniqueGroupNameResolution_renameGroupLabel_title;

    public static String SetUniqueGroupNameResolution_renameGroupName_message;

    public static String SetUniqueGroupNameResolution_renameGroupName_title;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
