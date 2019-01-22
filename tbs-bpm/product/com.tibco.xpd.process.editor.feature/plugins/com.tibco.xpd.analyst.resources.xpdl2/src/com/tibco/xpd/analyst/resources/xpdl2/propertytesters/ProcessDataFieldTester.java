/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.propertytesters;

import org.eclipse.core.expressions.PropertyTester;

import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Process;

/**
 * @author rsomayaj
 * 
 * 
 */
public class ProcessDataFieldTester extends PropertyTester {

    public ProcessDataFieldTester() {
    }

    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue3) {

        if (receiver instanceof DataField) {
            DataField dataField = (DataField) receiver;
            if (dataField.eContainer() != null
                    && dataField.eContainer() instanceof Process
                    && !dataField.isCorrelation()) {
                return true;
            }
        }
        return false;
    }

}
