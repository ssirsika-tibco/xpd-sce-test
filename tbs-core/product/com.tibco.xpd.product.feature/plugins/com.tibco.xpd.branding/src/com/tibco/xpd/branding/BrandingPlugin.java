/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.branding;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class BrandingPlugin extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "com.tibco.xpd.branding";
	// The shared instance.
	private static BrandingPlugin plugin;

	/**
	 * The constructor.
	 */
	public BrandingPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static BrandingPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path.
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path); //$NON-NLS-1$
	}

	/**
	 * Logs error with a message and/or exception.
	 * 
	 * @param message message to log. If message is 'null' then it is equivalent to {@link #logError(Exception)}. 
	 * @param e error to log. Can be 'null'.
	 */
	public static void logError(String message, Exception e) {
		if (e != null && message != null) {			
			getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, message, e)); 
		} else if (message != null) {
			getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, message)); 
		} else {
			logError(e);
		}
	}
	
	/**
	 * Logs error caused by exception.
	 * 
	 * @param e error to log. Can be 'null'.
	 */
	public static void logError(Exception e) {
		if (e instanceof CoreException) {			
			getDefault().getLog().log(((CoreException) e).getStatus());
		} else if (e != null) {
			getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, "Exception was thrown.", e)); //$NON-NLS-1$
		} else {
			StackTraceElement stackElement = new Exception().getStackTrace()[1];
			getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, "Error reported from: " + stackElement)); //$NON-NLS-1$
		}
	}
	
	/**
	 * Logs warning with a message.
	 * 
	 * @param message message to log. 
	 */
	public static void logWarning(String message) {
		getDefault().getLog().log(new Status(IStatus.WARNING, PLUGIN_ID, message)); 
	}
	
	/**
	 * Logs info with a message.
	 * 
	 * @param message message to log. 
	 */
	public static void logInfo(String message) {
		getDefault().getLog().log(new Status(IStatus.INFO, PLUGIN_ID, message)); 
	}
}
