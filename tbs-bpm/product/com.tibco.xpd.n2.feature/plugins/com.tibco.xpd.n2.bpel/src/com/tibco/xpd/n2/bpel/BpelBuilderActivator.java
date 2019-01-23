package com.tibco.xpd.n2.bpel;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class BpelBuilderActivator extends Plugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.n2.bpel";

    // The shared instance
    private static BpelBuilderActivator plugin;

    /** The error log. XPD-391 */
    private Logger log;

    /**
     * The constructor
     */
    public BpelBuilderActivator() {
        log = LoggerFactory.createLogger(PLUGIN_ID);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
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
     * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
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
    public static BpelBuilderActivator getDefault() {
        return plugin;
    }

    /**
     * @return The error log.
     */
    public Logger getLogger() {
        return log;
    }

}
