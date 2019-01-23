package com.tibco.xpd.core.help;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 */
public class CoreHelpActivator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.core.help"; //$NON-NLS-1$

    // The shared instance
    private static CoreHelpActivator plugin;

    /**
     * The constructor
     */
    public CoreHelpActivator() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
        XpdHelpService.getInstance();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance.
     * 
     * @return the shared instance.
     */
    public static CoreHelpActivator getDefault() {
        return plugin;
    }

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        super.initializeImageRegistry(reg);

        for (String img : HelpImages.IMAGES) {
            reg.put(img,
                    ImageDescriptor.createFromURL(getBundle().getEntry(img)));
        }
    }

    /**
     * Logs error originating from this plug-in.
     * 
     * @param message
     *            the message to log.
     * @param exception
     *            the exception to log (can be 'null')
     */
    public void logError(String message, Throwable exception) {
        getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, message, exception));
    }

    /**
     * Logs error originating from this plug-in.
     * 
     * @param exception
     *            the exception to log (can be 'null')
     */
    public void logError(Throwable exception) {
        getLog().log(new Status(IStatus.ERROR, PLUGIN_ID,
                "Exception was thrown.", exception)); //$NON-NLS-1$
    }

    /**
     * Logs info originating from this plug-in.
     * 
     * @param message
     *            the message to log.
     */
    public void logInfo(String message) {
        getLog().log(new Status(IStatus.INFO, PLUGIN_ID, message));
    }
}
