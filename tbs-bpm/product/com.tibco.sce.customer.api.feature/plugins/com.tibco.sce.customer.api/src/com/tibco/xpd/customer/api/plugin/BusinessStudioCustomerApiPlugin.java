/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.customer.api.plugin;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The plugin activator for the Business Studio Customer API plugin.
 * <p>
 * This plugin provides API's that TIBCO provides to customers that wish to
 * perform some level of customisation.
 * </p>
 * <p>
 * This plugin must be treated as a public API and therefore must be versioned
 * and remain backward compatible using the usual conventions.
 * 
 * @author aallway
 * @since 19 Sep 2013
 */
public class BusinessStudioCustomerApiPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.customer.api"; //$NON-NLS-1$

    // The shared instance
    private static BusinessStudioCustomerApiPlugin plugin;

    /** logger instance. */
    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);// new

    /**
     * The constructor
     */
    public BusinessStudioCustomerApiPlugin() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static BusinessStudioCustomerApiPlugin getDefault() {
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
