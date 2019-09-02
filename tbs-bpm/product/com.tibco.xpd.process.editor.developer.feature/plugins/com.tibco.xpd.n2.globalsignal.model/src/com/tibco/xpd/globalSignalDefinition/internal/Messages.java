/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.globalSignalDefinition.internal;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author pwatson
 * @since 29 Aug 2019
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.globalSignalDefinition.internal.messages"; //$NON-NLS-1$

    public static String GsdWorkingCopy_OlderVersionMessage;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
