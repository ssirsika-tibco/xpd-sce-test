/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.pe.transform;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author pwatson
 * @since 21 Mar 2019
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.n2.pe.transform.messages"; //$NON-NLS-1$

    public static String PERascContributor_AddingRuntimeProcesses;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
