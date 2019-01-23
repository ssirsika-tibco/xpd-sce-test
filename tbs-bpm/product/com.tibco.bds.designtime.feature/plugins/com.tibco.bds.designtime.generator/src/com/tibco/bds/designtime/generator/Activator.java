package com.tibco.bds.designtime.generator;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.bds.designtime.generator.internal.PerfMetrics;
import com.tibco.bds.designtime.generator.internal.PerfMetricsEnabled;
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
     * Gets the Performance Metrics Class suitable for the enabled logging
     * 
     * @return PerfMetrics class
     */
    public PerfMetrics getPerfMetrics() {
        if (forcePerfMetricsOn) {
            // If forces print to system out
            return new PerfMetricsEnabled(true);
        }
        if (getLogger().isDebugEnabled()) {
            // Don't print to system out
            return new PerfMetricsEnabled(false);
        }
        // Default is turned off
        return new PerfMetrics();
    }

    /**
     * Checks if EMF Jet Templates should be copied to the project
     * 
     * @return True if copy, false otherwise
     */
    public boolean isCopyEmfJetTemplatesEnabled() {
        boolean copyEmfTemplates = false;
        if ( getLogger().isDebugEnabled() || forceEmfJetTemplateCopy) {
            copyEmfTemplates = true;
        }
        return copyEmfTemplates;
    }
}
