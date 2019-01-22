/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.n2.ut.configuration.builder;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * Bundle Activator - Stores global data for this bundle such as the logger
 *
 */
public class Activator extends AbstractUIPlugin {

    public static final String PLUGIN_ID = "com.tibco.n2.ut.configuration.builder";

    // The singleton instance of this class
    private static Activator instance;

    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    public Activator() {
    }

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        instance = this;
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        instance = null;
        super.stop(context);
    }

    public static Activator getDefault() {
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }

}
