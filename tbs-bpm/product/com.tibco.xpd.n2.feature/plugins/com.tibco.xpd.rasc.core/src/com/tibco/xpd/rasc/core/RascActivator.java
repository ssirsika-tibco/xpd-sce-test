package com.tibco.xpd.rasc.core;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.rasc.core.impl.RascControllerImpl;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

public class RascActivator implements BundleActivator {

	private static BundleContext context;

    private static RascActivator instance;

    public static final String PLUGIN_ID = "com.tibco.xpd.rasc.core"; //$NON-NLS-1$

    private Logger logger;

    static BundleContext getContext() {
		return context;
	}

    public synchronized Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.createLogger(PLUGIN_ID);
        }
        return logger;
    }

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
    public void start(BundleContext bundleContext) throws Exception {
        RascActivator.instance = this;
		RascActivator.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
    public void stop(BundleContext bundleContext) throws Exception {
		RascActivator.context = null;
	}

    /**
     * A static accessor to retrieve the RascActivator instance, from which the
     * RascController implementation can be retrieved.
     * 
     * @return the default RascActivator instance.
     * @see #getRascController()
     */
    public static RascActivator getDefault() {
        return RascActivator.instance;
    }

    /**
     * Returns a new RascController that will co-ordinate the generation of
     * deployment RASCs for given projects.
     * 
     * @return a new RascController instance.
     */
    public RascController getRascController() {
        return new RascControllerImpl();
    }
}
