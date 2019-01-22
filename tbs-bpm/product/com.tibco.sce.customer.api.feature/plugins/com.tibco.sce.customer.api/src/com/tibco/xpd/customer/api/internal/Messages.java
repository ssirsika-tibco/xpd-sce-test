/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.customer.api.internal;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * 
 * @author sajain
 * @since Apr 2, 2014
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.customer.api.internal.messages"; //$NON-NLS-1$

    public static String IProcessToBPMConversionExtensionPointManager_Monitor_msg;

    public static String IProcessToBPMConversionExtensionPointManager_conversionException_shortDesc;

    public static String IProcessToBPMConversionExtension_toString_msg;

    public static String IProcessToBPMConversionExtension_invalidContribution_shortDesc;

    public static String IProcessToBPMConversionExtension_invalidInstance_shortDesc;

    public static String IProcessToBPMConversionExtension_invalidOrderingPriority_shortDesc;

    public static String IProcessToBPMLifeCycleExtension_InvalidLifeCycleListenerContrib_msg;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
