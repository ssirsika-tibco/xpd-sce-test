/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.simulation.common.util;

import org.eclipse.osgi.util.NLS;

/**
 * @author nwilson
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.simulation.common.util.messages"; //$NON-NLS-1$
    public static String SimulationXpdlUtils_StartEvent;
    public static String SimulationXpdlUtils_Task;
    public static String SimulationXpdlUtils_To;
    public static String SimulationXpdlUtils_Unnamed;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
