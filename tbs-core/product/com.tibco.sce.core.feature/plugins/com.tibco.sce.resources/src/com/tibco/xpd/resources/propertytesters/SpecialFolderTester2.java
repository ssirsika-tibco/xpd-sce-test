/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.propertytesters;

import org.eclipse.core.expressions.PropertyTester;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * Property Testers for <code>SpecialFolder</code>. The test includes:
 * <ul>
 * <li> <b>isOfKind</b>: Tests whether the given <code>SpecialFolder</code>
 * is of a given kind
 * </ul>
 * 
 * @author njpatel
 */
public class SpecialFolderTester2 extends PropertyTester {

    public static final String PROP_ISOFKIND = "isOfKind"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.PropertyTester#test(java.lang.Object,
     *      java.lang.String, java.lang.Object[], java.lang.Object)
     */
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {

        if (receiver instanceof SpecialFolder) {
            SpecialFolder sFolder = (SpecialFolder) receiver;

            return sFolder.getKind().equals((String) expectedValue);
        }

        return false;
    }

}
