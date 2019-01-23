/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.deploy.internal;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author kthombar
 * @since Mar 20, 2015
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.n2.globalsignal.deploy.internal.messages"; //$NON-NLS-1$

    public static String GsdCompositeContributor_CreateGsdComponentMonitor_msg;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
