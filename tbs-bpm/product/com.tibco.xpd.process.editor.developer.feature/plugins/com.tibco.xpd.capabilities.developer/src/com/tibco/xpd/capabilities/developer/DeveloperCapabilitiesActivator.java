/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.capabilities.developer;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.capabilities.developer.ui.activities.DeveloperActivitySupport;

/**
 * The activator class controls the plug-in life cycle
 */
public class DeveloperCapabilitiesActivator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID =
            "com.tibco.xpd.capabilities.developer"; //$NON-NLS-1$

    // The shared instance
    private static DeveloperCapabilitiesActivator plugin;

    private DeveloperActivitySupport deploymentActivitySupport;

    /**
     * The constructor
     */
    public DeveloperCapabilitiesActivator() {
        deploymentActivitySupport = new DeveloperActivitySupport();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
     * BundleContext)
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
        // boolean devPrefEnabled = DeveloperCapabilitiesActivator.getDefault()
        // .getPluginPreferences().getBoolean(
        // DeveloperCapabilitiesConstants.DEVELOPER_ACTIVITY_ID);
        // DeveloperActivityUtil.setDeveloperActivity(devPrefEnabled);
        IActivityManager activityManager = PlatformUI.getWorkbench()
                .getActivitySupport().getActivityManager();
        activityManager.addActivityManagerListener(deploymentActivitySupport);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
     * BundleContext)
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        if (PlatformUI.getWorkbench() != null
                && PlatformUI.getWorkbench().getActivitySupport() != null
                && PlatformUI.getWorkbench().getActivitySupport()
                        .getActivityManager() != null) {
            IActivityManager activityManager = PlatformUI.getWorkbench()
                    .getActivitySupport().getActivityManager();
            activityManager
                    .removeActivityManagerListener(deploymentActivitySupport);
        }
        // IPersistentPreferenceStore prefStore = ((IPersistentPreferenceStore)
        // getPreferenceStore());
        // prefStore.setValue(
        // DeveloperCapabilitiesConstants.DEVELOPER_ACTIVITY_ID,
        // DeveloperActivityUtil.isDeveloperActivityEnabled());
        // prefStore.save();
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static DeveloperCapabilitiesActivator getDefault() {
        return plugin;
    }

}
