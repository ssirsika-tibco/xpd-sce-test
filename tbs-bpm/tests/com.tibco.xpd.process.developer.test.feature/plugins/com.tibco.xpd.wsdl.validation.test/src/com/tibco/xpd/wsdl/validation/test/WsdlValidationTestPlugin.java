package com.tibco.xpd.wsdl.validation.test;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class WsdlValidationTestPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.xpd.wsdl.validation.test"; //$NON-NLS-1$

	// The shared instance
	private static WsdlValidationTestPlugin plugin;
	
	/**
	 * The constructor
	 */
	public WsdlValidationTestPlugin() {
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
	public static WsdlValidationTestPlugin getDefault() {
		return plugin;
	}

}
