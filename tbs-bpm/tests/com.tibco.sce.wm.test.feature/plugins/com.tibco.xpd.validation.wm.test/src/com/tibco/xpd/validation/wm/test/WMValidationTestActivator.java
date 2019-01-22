package com.tibco.xpd.validation.wm.test;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class WMValidationTestActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.xpd.validation.wm.test"; //$NON-NLS-1$

	// The shared instance
	private static WMValidationTestActivator plugin;
	
	 // Logger for publishing info messages
    private static Logger logger = null;

	/**
	 * The constructor
	 */
	public WMValidationTestActivator() {
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
	public static WMValidationTestActivator getDefault() {
		return plugin;
	}
	
    /**
     * Write info messages to eclipse logger.
     * 
     * @param message
     */
    public static void logInfo(String message) {
        // Initialize the logger if not done already; only do it once
        if (logger == null) {
            logger = LoggerFactory.createLogger(PLUGIN_ID);
        }
        // For convenience, also print to console
        System.out.println(message);
        logger.info(message);
    }

}
