/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd;

import org.eclipse.osgi.util.NLS;

/**
 * Messages for localization.
 *
 * @author jarciuch
 * @since 12 Feb 2015
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.rsd.messages"; //$NON-NLS-1$

    public static String DataType_Boolean;

    public static String DataType_Date;

    public static String DataType_DateTime;

    public static String DataType_Decimal;

    public static String DataType_Integer;

    public static String DataType_Text;

    public static String DataType_Time;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
