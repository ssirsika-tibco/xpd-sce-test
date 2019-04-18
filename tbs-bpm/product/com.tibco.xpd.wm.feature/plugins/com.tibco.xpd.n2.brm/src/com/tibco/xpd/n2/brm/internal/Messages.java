/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.brm.internal;

import org.eclipse.osgi.util.NLS;

/**
 * @author Jan Arciuchiewicz
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.n2.brm.internal.messages"; //$NON-NLS-1$


    public static String BRMGenerator_invalidWlf_message;

    public static String BRMGenerator_invalidWlfContent_message;

    public static String BRMGenerator_onlyOneWlfPerProject_message;

    public static String BRMGenerator_resourceSaveProblem_message;

    public static String BrmModelsRascContributor_UserTaskModelExport_status;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
