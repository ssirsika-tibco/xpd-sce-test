/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.destinations.ui;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.xpdl2.Process;

/**
 * Tests destination envireonments of the XPDL process.
 * <p>
 * <i>Created: 20 Feb 2007</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ProcessDestinationProperyTester extends PropertyTester {

    /** Property name for testing process destination environment. */
    public static final String HAS_DESTINATION_ENVIRONMENT = "hasDestinationEnvironment"; //$NON-NLS-1$

    /**
     * Test if process has destination environment enabled.
     * 
     * @param receiver
     *            XPDL process or object which can be adapted to XPDL process.
     * @param property
     *            name of the property the only acceptable value is
     *            <code>hasDestinationEnvironment</code>.
     * @param args
     *            additional arguments (not used).
     * @param expectedValue
     *            destination environment id which will be tested.
     * @return true if process has enabled destination enviroment specified by
     *         expectedValue.
     * @see org.eclipse.core.expressions.PropertyTester#test(java.lang.Object,
     *      java.lang.String, java.lang.Object[], java.lang.Object)
     */
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        if (property.equals(HAS_DESTINATION_ENVIRONMENT)) {
            Process process = null;
            if (receiver instanceof Process) {
                process = (Process) receiver;
            } else if (receiver instanceof IAdaptable) {
                process = (Process) ((IAdaptable) receiver);
            } else {
                process = (Process) Platform.getAdapterManager().getAdapter(
                        receiver, Process.class);
            }
            if (process != null && expectedValue instanceof String) {
                String destination = (String) expectedValue;
                return DestinationUtil.isValidationDestinationEnabled(process,
                        destination);
            }
        }
        return false;
    }

}
