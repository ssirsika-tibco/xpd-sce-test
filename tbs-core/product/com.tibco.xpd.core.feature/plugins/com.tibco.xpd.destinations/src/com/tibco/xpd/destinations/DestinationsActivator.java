package com.tibco.xpd.destinations;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.destinations.preferences.DestinationPreferences;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class DestinationsActivator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.destinations"; //$NON-NLS-1$

    // The shared instance
    private static DestinationsActivator plugin;

    private DestinationPreferences preferences;

    /**
     * The constructor
     */
    public DestinationsActivator() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
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
    public static DestinationsActivator getDefault() {
        return plugin;
    }

    @Override
    public IPreferenceStore getPreferenceStore() {
        // Delegate to the Resources UI plugin so that that plugin can access
        // the user defined global destinations to set for projects.
        return XpdResourcesUIActivator.getDefault().getPreferenceStore();
    }

    public DestinationPreferences getDestinationPreferences() {
        if (preferences == null) {
            preferences = new DestinationPreferences();
            IPreferenceStore store = getPreferenceStore();
            String s = store.getString(DestinationPreferences.PREFERENCE);
            if (s != null && s.length() != 0) {
                preferences.parse(s);
            }
        }
        return preferences;
    }
}
