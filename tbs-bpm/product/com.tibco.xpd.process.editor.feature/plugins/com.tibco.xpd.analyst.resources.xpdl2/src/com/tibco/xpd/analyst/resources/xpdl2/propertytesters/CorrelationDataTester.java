/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.propertytesters;

import org.eclipse.core.expressions.PropertyTester;

import com.tibco.xpd.xpdl2.DataField;

/**
 * @author rsomayaj
 * 
 * 
 */
public class CorrelationDataTester extends PropertyTester {

    public CorrelationDataTester() {
    }

    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {

        if (receiver instanceof DataField) {
            DataField dataField = (DataField) receiver;
            if (dataField.isCorrelation()) {
                return true;
            }
        }
        return false;
    }

}
