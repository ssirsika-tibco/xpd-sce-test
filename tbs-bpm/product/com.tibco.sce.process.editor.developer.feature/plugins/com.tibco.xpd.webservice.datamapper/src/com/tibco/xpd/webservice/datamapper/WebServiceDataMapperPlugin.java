package com.tibco.xpd.webservice.datamapper;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class WebServiceDataMapperPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID =
            "com.tibco.xpd.webservice.datamapper"; //$NON-NLS-1$

    // The shared instance
    private static WebServiceDataMapperPlugin plugin;

    /** logger instance. */
    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);// new

    /**
     * The constructor
     */
    public WebServiceDataMapperPlugin() {
    }

    /**
     * Log specified error.
     * 
     * @param e
     */
    public static void log(Throwable e) {
        getDefault().getLogger().error(e);
    }

    /**
     * Access to eclipse log.
     * 
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
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
    public static WebServiceDataMapperPlugin getDefault() {
        return plugin;
    }

}
