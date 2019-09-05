package com.tibco.xpd.rasc.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.rasc.ui.governance.ReadOnlyResourceChangeListener;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class RascUiActivator extends AbstractUIPlugin {

    public static final String ID = "com.tibco.xpd.rasc.ui"; //$NON-NLS-1$

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.rasc.ui"; //$NON-NLS-1$

    /**
     * Main image for the export wizard.
     */
    public static final String RASC_EXPORT_WIZARD_IMAGE =
            "RASC_EXPORT_WIZARD_IMAGE"; //$NON-NLS-1$

    /**
     * Preference Store key for the admin base URL.
     */
    public static final String ADMIN_BASE_URL = "adminBaseUrl"; //$NON-NLS-1$

    /**
     * Preference Store key for boolean flag indicating whether to hide the
     * admin URL dialog on launch.
     */
    public static final String HIDE_ADMIN_BASE_URL = "hideAdminBaseUrl"; //$NON-NLS-1$

    /**
     * The maximum number of entries to record in the Admin URL history.
     */
    private static final int URL_HISTORY_SIZE = 10;

    // The shared instance
    private static RascUiActivator plugin;

    private Logger logger =
            LoggerFactory.createLogger(RascUiActivator.PLUGIN_ID);

    private ReadOnlyResourceChangeListener readOnlyListener;

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
        if (!XpdResourcesPlugin.isInHeadlessMode()) {
            readOnlyListener = new ReadOnlyResourceChangeListener();
            ResourcesPlugin.getWorkspace().addResourceChangeListener(readOnlyListener);
        }
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#initializeImageRegistry(org.eclipse.jface.resource.ImageRegistry)
     *
     * @param reg
     */
    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        reg.put(RASC_EXPORT_WIZARD_IMAGE,
                imageDescriptorFromPlugin(PLUGIN_ID,
                        "icons/wizban/rasc_wiz.png")); //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        if (readOnlyListener != null) {
            ResourcesPlugin.getWorkspace().removeResourceChangeListener(readOnlyListener);
        }
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     *
     * @return the shared instance
     */
    public static RascUiActivator getDefault() {
        return plugin;
    }

    /**
     * @return The logger instance.
     */
    public static Logger getLogger() {
        return getDefault().logger;
    }

    /**
     * @return The admin base URL or an empty string if it hasn't been set.
     */
    public String getAdminBaseUrl() {
        return getPreferenceStore().getString(ADMIN_BASE_URL);
    }

    /**
     * Sets the admin base URL; and adds the entry to the history.
     * 
     * @param url
     *            The admin base URL.
     */
    public void setAdminBaseUrl(String url) {
        getPreferenceStore().setValue(ADMIN_BASE_URL, url);
        addAdminBaseHistory(url);
    }

    /**
     * Returns the Admin URL history as an array of String. The entries will be
     * ordered with the most recent entry first.
     * 
     * @return the history of Admin URL entries.
     */
    public String[] getAdminBaseHistory() {
        List<String> result = _getAdminBaseHistory();
        return result.toArray(new String[result.size()]);
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path.
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(ID, path);
    }

    /**
     * Returns the history of collection admin url entries as a List. This
     * allows for easier manipulation when adding new entries.
     */
    private List<String> _getAdminBaseHistory() {
        IPreferenceStore preferences = getPreferenceStore();

        List<String> result = new ArrayList<>();
        for (int i = 1; i <= URL_HISTORY_SIZE; i++) {
            String value = preferences.getString(ADMIN_BASE_URL + i);
            if ((value != null) && (value.length() > 0)) {
                result.add(value);
            }
        }

        return result;
    }

    /**
     * Adds the given Admin URL to the history. The given entry will be placed
     * at the head of the list. If the resulting list exceeds URL_HISTORY_SIZE,
     * the list will be truncated.
     * 
     * @param url
     *            the URL to be added to the Admin URL history.
     */
    private void addAdminBaseHistory(String url) {
        List<String> history = _getAdminBaseHistory();

        // remove any previous entry of the same name
        history.remove(url);

        // add new entry at the top
        if (history.isEmpty())
            history.add(url);
        else
            history.add(0, url);

        IPreferenceStore preferences = getPreferenceStore();
        int count = Math.min(URL_HISTORY_SIZE, history.size());
        for (int i = 1; i <= count; i++) {
            preferences.setValue(ADMIN_BASE_URL + i, history.get(i - 1));
        }
    }

    /**
     * @return true if the admin URL dialog should be hidden.
     */
    public boolean getHideAdminUrlDialog() {
        return getPreferenceStore().getBoolean(HIDE_ADMIN_BASE_URL);
    }

    /**
     * @param hide
     *            true if the admin URL dialog should be hidden.
     */
    public void setHideAdminUrlDialog(boolean hide) {
        getPreferenceStore().setValue(HIDE_ADMIN_BASE_URL, hide);
    }
}
