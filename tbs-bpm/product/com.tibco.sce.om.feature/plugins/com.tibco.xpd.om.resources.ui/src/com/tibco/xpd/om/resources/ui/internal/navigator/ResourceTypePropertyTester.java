/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.internal.navigator;

import org.eclipse.core.expressions.PropertyTester;

import com.tibco.xpd.om.core.om.ResourceType;

/**
 * ResourceType properties tester.
 * <p>
 * <i>Created: 16 Jan 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class ResourceTypePropertyTester extends PropertyTester {

    private static final String PROP_IS_HUMAN_RESOURCE_TYPE = "isHumanResourceType"; //$NON-NLS-1$

    /** {@inheritDoc} */
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        if (receiver instanceof ResourceType) {
            ResourceType resourceType = (ResourceType) receiver;
            if (property.equals(PROP_IS_HUMAN_RESOURCE_TYPE)) {
                if (expectedValue instanceof Boolean) {
                    boolean booleanExpectedValue = ((Boolean) expectedValue)
                            .booleanValue();
                    return resourceType.isHumanResourceType() == booleanExpectedValue;
                }
            }
            return false;
        }
        // if the tested object is not a ResourceType then ignore test.
        return true;
    }
}
