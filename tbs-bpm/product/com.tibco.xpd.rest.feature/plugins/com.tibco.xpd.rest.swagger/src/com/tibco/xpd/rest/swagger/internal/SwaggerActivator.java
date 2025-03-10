/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.rest.swagger.internal;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * Swagger Activator used to provide access to a common REST Swagger image registry.
 *
 * @author nkelkar
 * @since Aug 7, 2024
 */
public class SwaggerActivator extends AbstractUIPlugin
{

	public static final String		PLUGIN_ID	= "com.tibco.xpd.rest.swagger";	//$NON-NLS-1$

	private static SwaggerActivator	plugin;

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 * 
	 * @param context
	 * @throws Exception
	 */
	@Override
	public void start(BundleContext context) throws Exception
	{
		super.start(context);
		plugin = this;
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 * 
	 * @param context
	 * @throws Exception
	 */
	@Override
	public void stop(BundleContext context) throws Exception
	{
		super.stop(context);
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#initializeImageRegistry(org.eclipse.jface.resource.ImageRegistry)
	 * 
	 * @param reg
	 */
	@Override
	protected void initializeImageRegistry(ImageRegistry reg)
	{
		for (SwaggerImage image : SwaggerImage.values())
		{
			reg.put(image.getId(), imageDescriptorFromPlugin(PLUGIN_ID, image.getId()));
		}
	}

	/**
	 * @param key
	 *            The image key.
	 * @return The image, or null if not found.
	 */
	public Image getImage(SwaggerImage key)
	{
		return getImageRegistry().get(key.getId());
	}

	/**
	 * @param key
	 *            The image key.
	 * @return The image, or null if not found.
	 */
	public ImageDescriptor getImageDescriptor(SwaggerImage key)
	{
		return getImageRegistry().getDescriptor(key.getId());
	}

	/**
	 * @return The single plugin activator instance.
	 */
	public static SwaggerActivator getDefault()
	{
		return plugin;
	}
}
