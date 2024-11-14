package com.tibco.xpd.rest.ui.internal;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * Rest Services Activator used to provide access to a common REST image
 * registry.
 * 
 * @author nwilson
 * @since 30 Jan 2015
 */
public class RestServicesActivator extends AbstractUIPlugin {

    public static final String PLUGIN_ID = "com.tibco.xpd.rest.ui"; //$NON-NLS-1$

    private static RestServicesActivator plugin;

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     * 
     * @param context
     * @throws Exception
     */
    @Override
    public void start(BundleContext context) throws Exception {
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
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#initializeImageRegistry(org.eclipse.jface.resource.ImageRegistry)
     * 
     * @param reg
     */
    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        for (RestImage image : RestImage.values()) {
            reg.put(image.getId(),
                    imageDescriptorFromPlugin(PLUGIN_ID, image.getId()));
        }
    }

    /**
     * @param key
     *            The image key.
     * @return The image, or null if not found.
     */
    public Image getImage(RestImage key) {
        return getImageRegistry().get(key.getId());
    }

    /**
	 * @param key
	 *            The image key.
	 * @return The image, or null if not found.
	 */
	public ImageDescriptor getImageDescriptor(RestImage key)
	{
		return getImageRegistry().getDescriptor(key.getId());
	}

	/**
	 * @return The single plugin activator instance.
	 */
    public static RestServicesActivator getDefault() {
        return plugin;
    }
}
