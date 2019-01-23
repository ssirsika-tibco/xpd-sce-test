/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.propertytesters;

import org.eclipse.core.expressions.PropertyTester;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;

/**
 * 
 * @author aallway
 * @since
 */
public class FeatureAvailabilityTester extends PropertyTester {

    /**
     * Is the given object an .xpdl2 process package file content.
     */
    private static final String IS_WM_FEATURE_AVAILABLE =
            "isWMFeatureAvailable"; //$NON-NLS-1$

    public FeatureAvailabilityTester() {
    }

    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        if (IS_WM_FEATURE_AVAILABLE.equals(property)) {
            return Xpdl2ResourcesPlugin.isWmFeatureAvailable();
        }

        return false;
    }

}
