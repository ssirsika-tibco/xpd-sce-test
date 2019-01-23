package com.tibco.xpd.presentation.resources.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.presentation.channeltypes.ChannelTypes;
import com.tibco.xpd.presentation.resources.ui.internal.util.PresentationManager;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class PresentationResourcesUIActivator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID =
            "com.tibco.xpd.presentation.resources.ui"; //$NON-NLS-1$

    // The shared instance
    private static PresentationResourcesUIActivator plugin;

    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    /**
     * The constructor
     */
    public PresentationResourcesUIActivator() {
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

        /*
         * XPD-4141: Need to preload the workspace channels resource on plug-in
         * start-up to avoid a deadlock(with UI thread) when form is opened for
         * a user-task in a business process. This is not an issue in the
         * workbench as this resource is loaded earlier on in the lifecycle (by
         * GI Builder which does not run in the RCP).
         */
        if (XpdResourcesPlugin.isRCP()) {
            XpdResourcesPlugin
                    .getDefault()
                    .getEditingDomain()
                    .loadResource(PresentationManager.getWorkspaceChannelURI()
                            .toString());
        }
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
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static PresentationResourcesUIActivator getDefault() {
        return plugin;
    }

    /**
     * Returns logger for the plug-in.
     * 
     * @return the plug-in logger.
     */
    public Logger getLogger() {
        return logger;
    }

    public static ChannelTypes getChannelTypes() {
        Resource resource =
                XpdResourcesPlugin
                        .getDefault()
                        .getEditingDomain()
                        .getResourceSet()
                        .getResource(URI.createURI(PresentationManager.CHANNEL_TYPES_URI),
                                true);
        return (ChannelTypes) resource.getContents().get(0);
    }

    public static IStatus createStatusForException(Throwable e, String message) {
        if (message == null) {
            message = "Unexpected exception was thrown."; //$NON-NLS-1$
        }
        return new Status(IStatus.ERROR,
                PresentationResourcesUIActivator.PLUGIN_ID, message, e);
    }
}
