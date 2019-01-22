package com.tibco.xpd.simulation;
/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
import org.eclipse.core.runtime.Plugin;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * Plug-in activator class.
 * <p>
 * <i>Created: 16 Apr 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class SimulationProcessActivator extends Plugin {

    /** Plug-in identifier. */
    public static final String PLUGIN_ID = "com.tibco.xpd.simulation.process"; //$NON-NLS-1$

    /**
     * Returns logger for the plug-in.
     * 
     * @return Logger for this plug-in.
     */
    public static Logger getLogger() {
        return LoggerFactory.createLogger(PLUGIN_ID);
    }
}
