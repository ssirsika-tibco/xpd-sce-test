package com.tibco.xpd.resources.ui.internal.destination;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;

/**
 * Utility class to help decode the system and user-defined global destination
 * stored in this plug-ins (<code>com.tibco.xpd.resources.ui</code>) preference
 * store.
 * <p>
 * The global destinations are stored in this plug-in so that the project
 * properties and wizard can use the data to display the available destination
 * options during project creation and also on project properties to allow user
 * to set project-wide destination. Due to dependency restrictions this class
 * has been added to aid in reading the preference store - this does not include
 * functionality to write to the store (that is available in plug-in
 * <code>com.tibco.xpd.destinations</code>).
 * </p>
 * 
 * @author njpatel
 * @since 3.2
 * 
 */
public final class GlobalDestinationUtil {
    /**
     * Preference store id for the global destination settings.
     */
    public static final String GLOBAL_DESTINATION_PREFERENCE_ID = "com.tibco.xpd.destinations.global.destinations"; //$NON-NLS-1$

    private static Pattern splitDestinationSettings = Pattern
            .compile("(?<=[^/]),"); //$NON-NLS-1$
    private static Pattern splitDestination = Pattern.compile("(?<=[^/]);"); //$NON-NLS-1$
    private static Pattern unescapePattern = Pattern
            .compile("/(?=[/,;:\\|]([^,;:\\|]|\\z))"); //$NON-NLS-1$

    /**
     * Get the individual destination settings from the given global destination
     * settings string stored in the preference store.
     * 
     * @param preferenceString
     *            global destination settings
     * @return array of individual destination settings, empty array if none.
     */
    public static String[] getDestinationSettings(String preferenceString) {
        if (preferenceString != null) {
            return splitDestinationSettings.split(preferenceString);
        }
        return new String[0];
    }

    /**
     * Get the destination information for the given global destination setting
     * string.
     * 
     * @param destinationSetting
     *            global destination settings string.
     * @return array of strings for the destination details, empty string if
     *         string is incorrect or <code>null</code>.
     */
    public static String[] getDestinationDetails(String destinationSetting) {
        if (destinationSetting != null) {
            return splitDestination.split(destinationSetting);
        }
        return new String[0];
    }

    /**
     * Unescape the given string.
     * 
     * @param s
     *            The escaped string.
     * @return The unescaped string.
     */
    public static String unescape(String s) {
        return unescapePattern.matcher(s).replaceAll(""); //$NON-NLS-1$
    }

    /**
     * Get all registered global destinations, including user-defined
     * destinations.
     * 
     * @return list of global destinations, empty list if none found.
     */
    public static List<String> getGlobalDestinations() {
        List<String> globalDestinations = new ArrayList<String>();

        // Get user-defined global destinations
        String destinationsSettings = XpdResourcesUIActivator.getDefault()
                .getPreferenceStore().getString(
                        GLOBAL_DESTINATION_PREFERENCE_ID);

        if (destinationsSettings != null && destinationsSettings.length() > 0) {
            String[] userSettings = getDestinationSettings(destinationsSettings);

            if (userSettings != null) {
                for (String setting : userSettings) {
                    if (setting.length() > 0) {
                        String[] value = getDestinationDetails(setting);
                        if (value.length > 0 && value[0].length() > 0) {
                            String name = unescape(value[0]);
                            if (!globalDestinations.contains(name)) {
                                globalDestinations.add(name);
                            }
                        }
                    }
                }
            }
        } else {
            // If no preference set then return the default destinations
            globalDestinations = getDefaultGlobalDestinations();
        }

        return globalDestinations;
    }

    /**
     * Get all the default global destination registered via the global
     * destination env. extension point.
     * 
     * @return list of default destinations, empty string if none found.
     */
    private static List<String> getDefaultGlobalDestinations() {
        List<String> defaultDestinations = new ArrayList<String>();

        IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
                .getExtensionPoint("com.tibco.xpd.destinations", //$NON-NLS-1$
                        "globalDestinationEnvironment"); //$NON-NLS-1$

        if (extensionPoint != null) {
            for (IConfigurationElement element : extensionPoint
                    .getConfigurationElements()) {
                if ("defaultGlobalDestination".equals(element.getName())) { //$NON-NLS-1$
                    String name = element.getAttribute("name"); //$NON-NLS-1$
                    if (name != null) {
                        defaultDestinations.add(name);
                    }
                }
            }
        } else {
            XpdResourcesUIActivator
                    .getDefault()
                    .getLogger()
                    .error(
                            "Cannot access the global destination environment extension point: " //$NON-NLS-1$
                                    + "com.tibco.xpd.destinations.globalDestinationEnvironment"); //$NON-NLS-1$
        }

        return defaultDestinations;
    }
}
