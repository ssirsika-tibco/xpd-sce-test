/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.util;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * Utility features class
 * 
 * @author mtorres
 * 
 * @since 3.1
 **/
public class FeaturesUtil {

    /**
     * Use this method to know if a plugin is installed
     * 
     * @param pluginId
     *            The id of the plugin
     * @return true if the plugin is installed and false if the plugin is not
     *         installed
     **/
    public static boolean isPluginInstalled(String pluginId) {
        Bundle bundle = Platform.getBundle(pluginId);
        if (bundle != null) {
            return true;
        }
        return false;
    }

}
