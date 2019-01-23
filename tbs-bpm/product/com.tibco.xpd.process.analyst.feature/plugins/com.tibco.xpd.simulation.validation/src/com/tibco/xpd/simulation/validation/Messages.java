/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import org.eclipse.osgi.util.NLS;

/**
 * @author nwilson
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.simulation.validation.messages"; //$NON-NLS-1$
    public static String AddTaskSimulationData_AddData;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
