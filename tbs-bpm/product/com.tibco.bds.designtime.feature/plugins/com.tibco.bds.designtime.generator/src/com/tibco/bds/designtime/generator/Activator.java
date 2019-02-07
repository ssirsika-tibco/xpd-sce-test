package com.tibco.bds.designtime.generator;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

public class Activator extends AbstractUIPlugin {

    public static final String PLUGIN_ID = "com.tibco.bds.designtime.generator";

    // Manual override for performance logging
    private static final boolean forcePerfMetricsOn = false;

    // Forces the EMF Jet Templates to be copied to the BDS project
    private static final boolean forceEmfJetTemplateCopy = false;

    // The singleton instance of this class
    private static Activator instance;

    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    public Activator() {
    }

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        instance = this;
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        instance = null;
        super.stop(context);
    }

    public static Activator getDefault() {
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }

    /**
     * Checks if EMF Jet Templates should be copied to the project
     * 
     * @return True if copy, false otherwise
     */
    public boolean isCopyEmfJetTemplatesEnabled() {
        boolean copyEmfTemplates = false;
        if (getLogger().isDebugEnabled() || forceEmfJetTemplateCopy) {
            copyEmfTemplates = true;
        }
        return copyEmfTemplates;
    }
}
