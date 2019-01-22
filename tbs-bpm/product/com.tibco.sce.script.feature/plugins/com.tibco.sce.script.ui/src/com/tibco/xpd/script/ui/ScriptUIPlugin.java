package com.tibco.xpd.script.ui;

import java.util.List;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class ScriptUIPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.script.ui"; //$NON-NLS-1$

    // The shared instance
    private static ScriptUIPlugin plugin;
    
    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);// new

    /**
     * The constructor
     */
    public ScriptUIPlugin() {
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
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
    public static ScriptUIPlugin getDefault() {
        return plugin;
    }

    /**
     * This retrieves the grammars associated with the given destinations from
     * the contributions to the extension point - scriptDestinationBinding
     * 
     * @param destinationId
     * @return
     */
    public List<String> getContributedGrammarsForDestination(
            String destinationId) {
        return null;
    }
    
    
    public Logger getLogger() {
        return logger;
    }
}