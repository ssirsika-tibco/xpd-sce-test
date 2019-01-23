/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.bx.validation;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (27 Apr 2010)
 */
public class BxValidationPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.bx.validation"; //$NON-NLS-1$

    // The shared instance
    private static BxValidationPlugin plugin;

    /** The error log. XPD-391 */
    private Logger log;

    /**
     * The constructor
     */
    public BxValidationPlugin() {
        log = LoggerFactory.createLogger(PLUGIN_ID);
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static BxValidationPlugin getDefault() {
        return plugin;
    }

    /**
     * @return The error log.
     */
    public Logger getLogger() {
        return log;
    }

}
