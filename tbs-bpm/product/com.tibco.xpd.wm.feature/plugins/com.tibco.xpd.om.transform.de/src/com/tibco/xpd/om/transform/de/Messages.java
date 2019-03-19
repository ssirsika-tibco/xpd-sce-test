/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.om.transform.de;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author pwatson
 * @since 19 Mar 2019
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.om.transform.de.messages"; //$NON-NLS-1$

    public static String OrgModelRascContributor_ProgressTask;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
