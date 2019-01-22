package com.tibco.xpd.core.ant.task;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class CoreAntTasksActivator extends Plugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.core.ant.task"; //$NON-NLS-1$

    /** logger instance. */
    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    // The shared instance
    private static CoreAntTasksActivator plugin;

    /**
     * The constructor
     */
    public CoreAntTasksActivator() {
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
    public static CoreAntTasksActivator getDefault() {
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
