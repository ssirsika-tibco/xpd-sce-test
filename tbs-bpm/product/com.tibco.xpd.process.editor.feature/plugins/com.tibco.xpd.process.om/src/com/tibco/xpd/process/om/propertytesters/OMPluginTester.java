/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.process.om.propertytesters;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * Property Tester to test for OM Plugin exists
 * 
 * @author glewis
 * 
 */
public class OMPluginTester extends PropertyTester {

    private static final String REFLECTION_OM_CORE_PLUGIN_ID =
            "com.tibco.xpd.om.core"; //$NON-NLS-1$

    public OMPluginTester() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.PropertyTester#test(java.lang.Object,
     * java.lang.String, java.lang.Object[], java.lang.Object)
     */
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue3) {

        if (property.equals("isOMExist")) { //$NON-NLS-1$
            Bundle omCoreBundle =
                    Platform.getBundle(REFLECTION_OM_CORE_PLUGIN_ID);
            if (omCoreBundle != null) {
                return true;
            }
        }
        return false;
    }

}
