package com.tibco.xpd.rcp;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class RCPActivator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.rcp"; //$NON-NLS-1$

    private Logger logger;

    // The shared instance
    private static RCPActivator plugin;

    private static final Map<String, IAction> globalActions =
            new HashMap<String, IAction>();

    /**
     * The constructor
     */
    public RCPActivator() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
     * BundleContext )
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
     * BundleContext )
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
    public static RCPActivator getDefault() {
        return plugin;
    }

    /**
     * Register a global action.
     * 
     * @param id
     * @param action
     */
    public static void registerGlobalAction(String id, IAction action) {
        if (id != null && action != null) {
            synchronized (globalActions) {
                globalActions.put(id, action);
            }
        }
    }

    /**
     * Get a global action with the given id.
     * 
     * @param id
     * @return
     */
    public static IAction getGlobalAction(String id) {
        IAction action = null;
        if (id != null) {
            synchronized (globalActions) {
                action = globalActions.get(id);
            }
        }
        return action;
    }

    /**
     * Returns logger for the plug-in.
     * 
     * @return plug-in's logger.
     */
    public Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.createLogger(PLUGIN_ID);
        }
        return logger;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        for (RCPImages value : RCPImages.values()) {
            if (value.getPath() != null) {
                reg.put(value.getPath(), getImageDescriptor(value.getPath()));
            }
            if (value.getDisabledPath() != null) {
                reg.put(value.getDisabledPath(),
                        getImageDescriptor(value.getDisabledPath()));
            }
        }
    }

    /**
     * Sid XPD-8302
     * 
     * @return the globalactions registered fopr the ribbon control
     */
    public static Map<String, IAction> getGlobalactions() {
        return globalActions;
    }

}
