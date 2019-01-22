package com.tibco.xpd.implementer.nativeservices;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class NativeServicesActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.xpd.implementer.nativeservices"; //$NON-NLS-1$

	// The shared instance
	private static NativeServicesActivator plugin;

	/**
	 * The constructor
	 */
	public NativeServicesActivator() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/*
	 * (non-Javadoc)
	 * 
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
	public static NativeServicesActivator getDefault() {
		return plugin;
	}

	protected void initializeImageRegistry(ImageRegistry reg) {
		String[] imgs = NativeServicesConsts.IMAGES;
		for (int i = 0; i < imgs.length; i++) {
			reg.put(imgs[i], imageDescriptorFromPlugin(PLUGIN_ID, imgs[i]));
		}
	}

}
