/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */
package com.tibco.xpd.processscriptlibrary.resource;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class ProcessScriptLibraryResourcePluginActivtor extends AbstractUIPlugin {

	// The plug-in ID
	public static final String									PLUGIN_ID						= "com.tibco.xpd.processscriptlibrary.resource";	//$NON-NLS-1$

	/**
	 * Image used in all Wizards and wizard pages of Process Script Library
	 */
	public static final String									PSL_WIZARD_IMAGE		= "icons/obj16/processScriptLibraryWizard.png";		//$NON-NLS-1$

	// The shared instance
	private static ProcessScriptLibraryResourcePluginActivtor plugin;
	
	/**
	 * Logger instance.
	 */
	private final Logger										logger					= LoggerFactory
			.createLogger(PLUGIN_ID);

	private static final String[]								IMAGES				= new String[]{
			ProcessScriptLibraryConstants.IMG_SCRIPT_FILE,																				//
			ProcessScriptLibraryConstants.IMG_SCRIPT_FUNCTION,																			//
			ProcessScriptLibraryConstants.IMG_SCRIPT_FUNCTION_FOLDER,																	//
			ProcessScriptLibraryConstants.IMG_SCRIPT_FUNCTION_NEW,																		//
			ProcessScriptLibraryConstants.IMG_SCRIPT_FILE_WIZARD};

	/**
	 * Process Script Library Format Version value.
	 * 
	 */
	public static final String									FORMAT_VERSION					= "1";												//$NON-NLS-1$

	/**
	 * The constructor
	 */
	public ProcessScriptLibraryResourcePluginActivtor() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

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
	public static ProcessScriptLibraryResourcePluginActivtor getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in relative path.
	 * 
	 * @generated
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getBundledImageDescriptor(String path)
	{
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * Logs the Error Message.
	 * 
	 * @generated
	 */
	public void logError(String error)
	{
		logError(error, null);
	}

	/**
	 * Logs the Error Message.
	 * 
	 * @generated
	 */
	public void logError(Throwable throwable)
	{
		logError(null, throwable);
	}

	/**
	 * Logs the Error Message.
	 * 
	 * @generated
	 */
	public void logError(String error, Throwable throwable)
	{
		if (error == null && throwable != null)
		{
			error = throwable.getMessage();
		}
		getLog().log(new Status(IStatus.ERROR, ProcessScriptLibraryResourcePluginActivtor.PLUGIN_ID, IStatus.OK, error,
				throwable));
		debug(error, throwable);
	}

	/**
	 * Logs the Info Message.
	 * 
	 * @generated
	 */
	public void logInfo(String message)
	{
		logInfo(message, null);
	}

	/**
	 * Logs the Info Message.
	 * 
	 * @generated
	 */
	public void logInfo(String message, Throwable throwable)
	{
		if (message == null && throwable != null)
		{
			message = throwable.getMessage();
		}
		getLog().log(new Status(IStatus.INFO, ProcessScriptLibraryResourcePluginActivtor.PLUGIN_ID, IStatus.OK, message,
				throwable));
		debug(message, throwable);
	}

	/**
	 * Logs the Debug Message.
	 * 
	 * @generated
	 */
	private void debug(String message, Throwable throwable)
	{
		if (!isDebugging())
		{
			return;
		}
		if (message != null)
		{
			System.err.println(message);
		}
		if (throwable != null)
		{
			throwable.printStackTrace();
		}
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#initializeImageRegistry(org.eclipse.jface.resource.ImageRegistry)
	 *
	 * @param reg
	 */
	@Override
	protected void initializeImageRegistry(ImageRegistry reg)
	{
		String[] imgs = ProcessScriptLibraryResourcePluginActivtor.IMAGES;
		for (int i = 0; i < imgs.length; i++)
		{
			reg.put(imgs[i], getImageDescriptor(imgs[i]));
		}
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in relative path.
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path)
	{
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

}
