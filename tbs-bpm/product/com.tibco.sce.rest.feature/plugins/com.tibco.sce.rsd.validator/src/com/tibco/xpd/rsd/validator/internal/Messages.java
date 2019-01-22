/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.validator.internal;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author aallway
 * @since 17 Aug 2015
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.rsd.validator.internal.messages"; //$NON-NLS-1$

    public static String DecodeRestPathResolution_DecodeContextPath_menu;

    public static String DecodeRestPathResolution_DecodeResourcePath_Decode;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
