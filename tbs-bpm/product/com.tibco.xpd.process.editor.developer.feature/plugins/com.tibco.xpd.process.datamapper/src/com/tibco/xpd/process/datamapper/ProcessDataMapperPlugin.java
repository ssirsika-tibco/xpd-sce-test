/*Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.*/
package com.tibco.xpd.process.datamapper;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author Ali
 * @since 1 Feb 2015
 */
public class ProcessDataMapperPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.process.datamapper"; //$NON-NLS-1$

    // The shared instance
    private static ProcessDataMapperPlugin plugin;

    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    /**
     * The constructor
     */
    public ProcessDataMapperPlugin() {
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
    public static ProcessDataMapperPlugin getDefault() {
        return plugin;
    }

    /**
     * Returns logger for the plug-in.
     * 
     * @return plug-in's logger.
     */
    public Logger getLogger() {
        return logger;
    }

}
