package com.tibco.bx.composite.xpdl.core;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class N2PEComponentCoreActivator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.bx.composite.xpdl.core"; //$NON-NLS-1$

	// The shared instance
	private static N2PEComponentCoreActivator plugin;
	
	/**
	 * The constructor
	 */
	public N2PEComponentCoreActivator() {
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
	public static N2PEComponentCoreActivator getDefault() {
		return plugin;
	}

	public static IStatus createInfoStatus(String message) {
		return new Status(IStatus.INFO, PLUGIN_ID, IStatus.OK, message, null);
	}

	public static IStatus createWarningStatus(String message,
			Throwable exception) {
		return new Status(IStatus.WARNING, PLUGIN_ID, IStatus.OK, message,
				exception);
	}

	public static IStatus createErrorStatus(String message, Throwable exception) {
		return new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, message,
				exception);
	}

	public static void logError(String message, Throwable exception) {
		getDefault().getLog().log(createErrorStatus(message, exception));
	}

	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}
}
