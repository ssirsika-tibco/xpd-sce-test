/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.studioiprocess.internal;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * 
 * @author aallway
 * @since 13 Jun 2011
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.studioiprocess.internal.messages"; //$NON-NLS-1$

    public static String AbstractIProcessToJavaScriptConverter_CallScriptNotSupportedComment_message;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
