/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.internal.navigator;

import org.eclipse.core.expressions.PropertyTester;

import com.tibco.xpd.om.core.om.OrgMetaModel;

/**
 * MetaModels properties tester.
 * <p>
 * <i>Created: 16 Jan 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class MetaModelPropertyTester extends PropertyTester {

    private static final String PROP_IS_EMBEDED = "isEmbedded"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.PropertyTester#test(java.lang.Object,
     * java.lang.String, java.lang.Object[], java.lang.Object)
     */
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        if (receiver instanceof OrgMetaModel) {
            OrgMetaModel orgMetaModel = (OrgMetaModel) receiver;
            if (property.equals(PROP_IS_EMBEDED)) {
                if (expectedValue instanceof Boolean) {
                    boolean booleanExpectedValue = ((Boolean) expectedValue)
                            .booleanValue();
                    return orgMetaModel.isEmbedded() == booleanExpectedValue;
                }
            }
            return false;
        }
        // if the tested object is not a OrgMetaModel then ignore test.
        return true;
    }
}
