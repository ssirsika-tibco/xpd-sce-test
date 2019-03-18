/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.core;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author pwatson
 * @since 18 Mar 2019
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.rasc.core.messages"; //$NON-NLS-1$

    public static String RascControllerImpl_ProgressTask;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
