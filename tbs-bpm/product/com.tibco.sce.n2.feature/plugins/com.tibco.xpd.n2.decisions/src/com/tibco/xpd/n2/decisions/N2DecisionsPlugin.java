package com.tibco.xpd.n2.decisions;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class N2DecisionsPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.xpd.n2.decisions";

	// The shared instance
	private static N2DecisionsPlugin plugin;

    private Logger logger;
	/**
	 * The constructor
	 */
	public N2DecisionsPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
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
	public static N2DecisionsPlugin getDefault() {
		return plugin;
	}

    public synchronized Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.createLogger(PLUGIN_ID);
        }
        return logger;
    }

}
