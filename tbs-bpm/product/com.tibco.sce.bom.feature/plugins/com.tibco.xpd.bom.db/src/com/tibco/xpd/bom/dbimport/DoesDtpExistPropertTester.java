/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.dbimport;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * Property tester to ensure that the datatools features have been activated.
 * 
 * @author rsomayaj
 * 
 */
public class DoesDtpExistPropertTester extends PropertyTester {

    public DoesDtpExistPropertTester() {
    }

    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        Bundle bundle =
                Platform.getBundle("org.eclipse.datatools.connectivity.ui");
        if (bundle != null) {
            if (Bundle.ACTIVE == bundle.getState()
                    || Bundle.STARTING == bundle.getState()) {
                return true;
            }
        }
        return false;
    }
}
