/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.gen;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * BOM generator activator.
 * 
 * @author njpatel
 * 
 */
public class BOMGenActivator extends AbstractUIPlugin {

    public static final String PLUGIN_ID = "com.tibco.xpd.bom.gen"; //$NON-NLS-1$

    // The shared instance
    private static BOMGenActivator plugin;

    // Plugins logger object.
    private static Logger logger;

    public BOMGenActivator() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
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
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static BOMGenActivator getDefault() {
        return plugin;
    }

    /**
     * Get XPD logger for this plugin.
     * 
     * @return
     */
    public Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.createLogger(PLUGIN_ID);
        }
        return logger;
    }
}
