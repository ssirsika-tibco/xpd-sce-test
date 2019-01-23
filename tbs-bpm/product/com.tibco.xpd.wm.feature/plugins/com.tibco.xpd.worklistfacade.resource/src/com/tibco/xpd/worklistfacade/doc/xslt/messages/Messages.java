package com.tibco.xpd.worklistfacade.doc.xslt.messages;

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
            "com.tibco.xpd.worklistfacade.doc.xslt.messages.messages"; //$NON-NLS-1$

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }

    public static String WlfFile_label;

    public static String QuickLinks_label;

    public static String WorkItemAttributes_label;

    public static String WorkItemAttributesName_label;

    public static String WorkItemAttributesType_label;

    public static String WorkItemAttributesLength_label;

    public static String WorkItemAttributesDisplayName_label;

}
