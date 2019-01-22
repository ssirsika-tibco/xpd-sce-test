/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.nimbus.xsltutil;

import org.eclipse.osgi.util.NLS;

/**
 * Messages accessed from xslt transformation.
 * 
 * @author aallway
 * @since 25 Oct 2011
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.nimbus.xsltutil.messages"; //$NON-NLS-1$

    public static String CreatedBy_label;

    public static String Vendor_label;

    public static String DefaultPool_label;

    public static String DefaultLane_label;

    public static String StartEvent_label;

    public static String EndEvent_label;

    public static String StatementSet_label;

    public static String Statement_label;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
