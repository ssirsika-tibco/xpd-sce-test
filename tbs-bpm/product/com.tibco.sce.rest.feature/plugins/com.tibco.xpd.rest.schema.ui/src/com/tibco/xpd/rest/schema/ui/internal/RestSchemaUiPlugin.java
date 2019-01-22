package com.tibco.xpd.rest.schema.ui.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class RestSchemaUiPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.rest.schema.ui"; //$NON-NLS-1$

    // The shared instance
    private static RestSchemaUiPlugin plugin;

    /**
     * @param context
     *            The bundle context.
     * @see org.eclipse.ui.plugin.AbstractUIPlugin
     *      #start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin
     *      #initializeImageRegistry(org.eclipse.jface.resource.ImageRegistry)
     * 
     * @param reg
     */
    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        for (RestSchemaImage image : RestSchemaImage.values()) {
            reg.put(image.getId(),
                    imageDescriptorFromPlugin(PLUGIN_ID, image.getId()));
        }
    }

    /**
     * @param key
     *            The image key.
     * @return The image, or null if not found.
     */
    public Image getImage(RestSchemaImage key) {
        return getImageRegistry().get(key.getId());
    }

    /**
     * @param key
     *            The image key.
     * @return The image, or null if not found.
     */
    public ImageDescriptor getImageDescriptor(RestSchemaImage key) {
        return getImageRegistry().getDescriptor(key.getId());
    }

    /**
     * @param context
     *            The bundle context.
     * @see org.eclipse.ui.plugin.AbstractUIPlugin
     *      #stop(org.osgi.framework.BundleContext)
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
    public static RestSchemaUiPlugin getDefault() {
        return plugin;
    }

    /**
     * Writes the message to the Eclipse log.
     * 
     * @param message
     *            The error message to log.
     */
    public static void logError(String message) {
        logError(message, null);
    }

    /**
     * Writes the message to the Eclipse log.
     * 
     * @param message
     *            The error message to log.
     */
    public static void logError(Exception e) {
        logError(e.getMessage(), e);
    }

    /**
     * Writes the message and stakc trace to the Eclipse log.
     * 
     * @param message
     *            The error message to log.
     * @param e
     *            The associated exception.
     */
    public static void logError(String message, Exception e) {
        if (plugin != null) {
            IStatus error = new Status(IStatus.ERROR, PLUGIN_ID, message, e);
            plugin.getLog().log(error);
        }
    }
}
