/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.ui.propertytester;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;

import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * @author kupadhya
 * 
 */
public class CapabilityTester extends PropertyTester {

    public static final String PROP_IS_SOLUTION_DESIGN_ENABLED =
            "isSolutionDesignEnabled"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.PropertyTester#test(java.lang.Object,
     * java.lang.String, java.lang.Object[], java.lang.Object)
     */
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        if (PROP_IS_SOLUTION_DESIGN_ENABLED.equals(property)) {
            if (receiver instanceof IProject
                    && expectedValue instanceof Boolean) {
                Boolean boolExpectedValue = (Boolean) expectedValue;
                if (boolExpectedValue == CapabilityUtil
                        .isDeveloperActivityEnabled()) {
                    return true;
                }
            }
        }
        return false;
    }

}
