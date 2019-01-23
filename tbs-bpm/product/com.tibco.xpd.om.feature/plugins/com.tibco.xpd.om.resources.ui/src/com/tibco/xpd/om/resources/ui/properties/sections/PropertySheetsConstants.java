/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections;

import com.tibco.xpd.om.resources.ui.internal.Messages;

/**
 * 
 * Constants for use in building OM property sheets
 * 
 * @author rgreen
 * 
 */
public class PropertySheetsConstants {

    public static String ALLOCATION_METHOD_ANY = "ANY"; //$NON-NLS-1$

    public static String ALLOCATION_METHOD_NEXT = "NEXT"; //$NON-NLS-1$

    /**
     * Allocation method.
     */
    public static enum AllocationMethod {
        ANY(Messages.AllocableSection_allocationMethod_random_name), NEXT(
                Messages.AllocableSection_allocationMethod_roundRobin_name);

        private final String label;

        AllocationMethod(String label) {
            this.label = label;
        }

        /**
         * Get the label to be used for this allocation method.
         * 
         * @return label
         */
        public String getLabel() {
            return label;
        }
    }

}
