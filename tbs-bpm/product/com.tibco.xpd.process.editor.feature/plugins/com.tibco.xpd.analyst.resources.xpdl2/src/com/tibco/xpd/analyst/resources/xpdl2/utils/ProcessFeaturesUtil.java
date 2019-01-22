/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.utils;

import com.tibco.xpd.resources.util.FeaturesUtil;

/**
 * Utility features class
 * 
 * @author mtorres
 * 
 * @since 3.1
 **/
public class ProcessFeaturesUtil {

    /**
     * Use this method to know if a process developer feature is installed
     * 
     * @return true if the feature is installed and false if the feature is not
     *         installed
     **/
    public static boolean isProcessDeveloperFeatureInstalled() {
        return FeaturesUtil
                .isPluginInstalled("com.tibco.xpd.implementer.resources.xpdl2");//$NON-NLS-1$
    }

}
