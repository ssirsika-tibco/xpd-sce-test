package com.tibco.xpd.validation.xpdl2;

import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

public class Activator extends AbstractUIPlugin {

    public static final String PLUGIN_ID = "com.tibco.xpd.validation.xpdl2"; //$NON-NLS-1$

    /**
     * The shared instance.
     */
    private static Activator plugin;

    /** logger instance. */
    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);// new

    public Activator() {
        plugin = this;
    }

    /**
     * Returns the shared instance.
     * 
     * @return shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }

    /**
     * Access to eclipse log.
     * 
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }
}
