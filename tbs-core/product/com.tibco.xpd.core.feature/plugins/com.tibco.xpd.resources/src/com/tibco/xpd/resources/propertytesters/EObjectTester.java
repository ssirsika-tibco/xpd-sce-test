/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources.propertytesters;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EObject;

/**
 * Property Tester to see if the given EObject belongs to the given Package.
 * 
 * @author njpatel
 * 
 */
public class EObjectTester extends PropertyTester {

    public static final String PROP_ISFROMPACKAGE = "isFromPackage"; //$NON-NLS-1$

    /**
     * EObject property tester
     */
    public EObjectTester() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.PropertyTester#test(java.lang.Object,
     *      java.lang.String, java.lang.Object[], java.lang.Object)
     */
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {

        // Test whether the EObject is from the given package
        if (property.equals(PROP_ISFROMPACKAGE)) {

            // Receiver should be EObject and there should be an expected value
            if (receiver != null && receiver instanceof EObject
                    && expectedValue != null) {

                EObject eo = (EObject) receiver;

                return (eo.eClass().getEPackage().getName()
                        .equals(expectedValue));
            }
        }

        return false;

    }

}
