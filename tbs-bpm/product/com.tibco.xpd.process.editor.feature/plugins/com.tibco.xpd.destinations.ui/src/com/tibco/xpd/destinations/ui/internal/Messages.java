/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.destinations.ui.internal;

import org.eclipse.osgi.util.NLS;

/**
 * @author aallway
 *
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.destinations.ui.internal.messages"; //$NON-NLS-1$

    public static String DestinationEnvironmentsPropertySection_DestEnvs_title;

    public static String DestinationEnvironmentsPropertySection_Envs_title;

    public static String DestinationUtil_SetDestEnv_menu;

    public static String DestinationUtil_SetPackageDest_menu;
    
    public static String NonExistentDestination;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
