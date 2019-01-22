/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.validator;

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.bom.validator.IBOMValidationPreferenceManager.ValidationDestination;
import com.tibco.xpd.bom.validator.internal.validation.BOMValidationPreferenceManager;
import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class BOMValidatorActivator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.bom.validator"; //$NON-NLS-1$

    // The shared instance
    private static BOMValidatorActivator plugin;

    // Plugins logger object.
    private static Logger logger;

    // Global constant for validation destinations
    public static final String VALIDATION_DEST_ID_JAVA = "bom.java.destination"; //$NON-NLS-1$

    public static final String VALIDATION_DEST_ID_BOMGENERIC =
            "bom.generic.validation.destination"; //$NON-NLS-1$

    public static final String VALIDATION_DEST_ID_XSD = "bom.xsd.destination"; //$NON-NLS-1$

    public static final String VALIDATION_DEST_ID_WSDL = "bom.wsdl.destination"; //$NON-NLS-1$

    public static final String VALIDATION_DEST_ID_CDS = "bom.cds.destination"; //$NON-NLS-1$

    /* WSDL to BOM Validation destination */
    public static final String VALIDATION_DEST_ID_WSDL_TO_BOM =
            "wsdltobom.destination"; //$NON-NLS-1$

    /**
     * The constructor
     */
    public BOMValidatorActivator() {
        plugin = this;
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
    public static BOMValidatorActivator getDefault() {
        return plugin;
    }

    /**
     * Get XPD logger for this plugin.
     * 
     * @return
     */
    public static Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.createLogger(PLUGIN_ID);
        }
        return logger;
    }

    /**
     * Check if the given validation destination included (and enabled) in any
     * of the global destination environments selected for the project.
     * <p>
     * If not, then also check whether the force destination scope provider has
     * been set on in project preferences by user (so that bom->xsd can be
     * forced on even without appropriate destination environment).
     * 
     * @param project
     * @param destination
     * @return <code>true</code> if active.
     */
    public boolean isValidationDestinationEnabled(IProject project,
            String validationDestinationId) {

        /*
         * Check if destination is enabled in standard way via the global
         * destinations selected for project.
         */
        boolean enabled =
                GlobalDestinationUtil.isValidationDestinationEnabled(project,
                        validationDestinationId);

        if (!enabled) {
            /*
             * Otherwise see if user has forced destination on in project
             * preference page.
             */
            ValidationDestination validationDestination =
                    ValidationDestination
                            .getDestination(validationDestinationId);

            if (validationDestination != null) {
                enabled =
                        getPreferenceManager().isValidationEnabled(project,
                                validationDestination);
            }
        }

        return enabled;
    }

    /**
     * Get the BOM preference manager that deals with getting and setting
     * preference and properties values for BOM validation destinations.
     * 
     * @return
     */
    public IBOMValidationPreferenceManager getPreferenceManager() {
        return new BOMValidationPreferenceManager(getPreferenceStore());
    }
}
