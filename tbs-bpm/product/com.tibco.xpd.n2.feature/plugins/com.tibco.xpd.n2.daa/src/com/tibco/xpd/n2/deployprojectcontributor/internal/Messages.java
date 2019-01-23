/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.deployprojectcontributor.internal;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * 
 * @author agondal
 * @since 4 Jun 2013
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.n2.deployprojectcontributor.internal.messages"; //$NON-NLS-1$

    public static String BPMDeployProjectContributor_DAAGenerationErrorMessage;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
