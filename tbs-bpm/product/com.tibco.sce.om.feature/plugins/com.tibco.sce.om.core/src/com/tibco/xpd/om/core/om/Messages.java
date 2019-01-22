/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.om.core.om;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * 
 * @author agondal
 * @since 15 Oct 2012
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.om.core.om.messages"; //$NON-NLS-1$

    public static String AttributeType_Boolean;

    public static String AttributeType_Date;

    public static String AttributeType_DateTime;

    public static String AttributeType_Decimal;

    public static String AttributeType_Enum;

    public static String AttributeType_EnumSet;

    public static String AttributeType_Integer;

    public static String AttributeType_Text;

    public static String AttributeType_Time;

    public static String AttributeType_Set;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
