package com.tibco.xpd.rasc.core;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.rasc.core.impl.RascControllerImpl;

public class RascActivator implements BundleActivator {

	private static BundleContext context;

    private static RascActivator instance;

	static BundleContext getContext() {
		return context;
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
