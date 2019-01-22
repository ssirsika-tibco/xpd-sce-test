/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.dependency.visualization.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class DependencyVisualizationActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.xpd.dependency.visualization"; //$NON-NLS-1$

	// The shared instance
	private static DependencyVisualizationActivator plugin;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
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
	public static DependencyVisualizationActivator getDefault() {
		return plugin;
	}

	/**
	 * Logs error with provided message
	 * 
	 * @param message
	 * @param e
	 */
	public static void logError(String message, Exception e) {
		Status status = new Status(IStatus.ERROR, PLUGIN_ID, message, e);
		getDefault().getLog().log(status);
	}

	/**
	 * Logs warning with provided message
	 * @param message
	 */
	public static void logWarning(String message) {
		Status status = new Status(IStatus.WARNING, PLUGIN_ID, message);
		getDefault().getLog().log(status);
	}

	/**
	 * Logs info with provided message
	 * @param message
	 */
	public static void logInfo(String message) {
		Status status = new Status(IStatus.INFO, PLUGIN_ID, message);
		getDefault().getLog().log(status);
	}

}
