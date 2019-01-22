/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * 
 * @author rsawant
 * @since 18-Dec-2012
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.validation.xpdl2.resolutions.messages"; //$NON-NLS-1$

    public static String RemoveInterfaceParameter;

    public static String DisAssociateActivityInterfaceDataResolution_RemoveData;

    public static String DisAssociateActivityInterfaceSingleDataFieldResolution_RemoveDataFieldFromActivityInterface;

    public static String RemoveUnusedParticipantResolution;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
