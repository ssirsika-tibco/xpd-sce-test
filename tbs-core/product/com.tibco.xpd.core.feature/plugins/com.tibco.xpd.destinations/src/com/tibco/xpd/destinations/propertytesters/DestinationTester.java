/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.destinations.propertytesters;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;

import com.tibco.xpd.destinations.GlobalDestinationUtil;

/**
 * Property Testers for <code>destinations</code>. The test includes:
 * <ul>
 * <li><b>hasGlobalDestination</b>: Tests whether the given <code>Project</code>
 * has a global destination enabled.
 * </ul>
 * 
 * @author Jan Arciuchiewicz
 */
public class DestinationTester extends PropertyTester {

    public static final String PROP_HAS_GLOBAL_DESTINATION =
            "hasGlobalDestination"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.PropertyTester#test(java.lang.Object,
     * java.lang.String, java.lang.Object[], java.lang.Object)
     */
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        if (PROP_HAS_GLOBAL_DESTINATION.equals(property)) {
            if (receiver instanceof IProject && expectedValue instanceof String) {
                IProject project = (IProject) receiver;

                return GlobalDestinationUtil
                        .isGlobalDestinationEnabled(project,
                                (String) expectedValue);
            }
        }

        return false;
    }
}
