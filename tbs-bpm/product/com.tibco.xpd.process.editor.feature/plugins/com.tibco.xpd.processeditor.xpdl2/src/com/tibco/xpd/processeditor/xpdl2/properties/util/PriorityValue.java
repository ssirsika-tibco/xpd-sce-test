/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.util;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;

/**
 * Process / task Priority values.
 * 
 * @author aallway
 * @since 27 Oct 2011
 */
public class PriorityValue {

    /**
     * @return The set of priority values that are valid for sub-process tasks
     */
    public static PriorityValue[] getSubProcessTaskPriorityValues() {
        return subProcessTaskPriorityValues;
    }

    /**
     * @return The set of priority values that are valid for sub-process tasks
     */
    public static PriorityValue[] getProcessPriorityValues() {
        return processPriorityValues;
    }

    /** xpdl:Activity/xpdl:Priority element values that user can select */
    private static PriorityValue[] subProcessTaskPriorityValues =
            new PriorityValue[] {
                    /*
                     * null string "" = element present BUT empty =
                     * "Inherit from parent"
                     */
                    new PriorityValue(
                            "", Messages.PriorityValue_SubProcTaskInheritFromParent_label), //$NON-NLS-1$
                    new PriorityValue(
                            "100", Messages.PriorityValue_SubProcTaskLow100_label), //$NON-NLS-1$
                    new PriorityValue(
                            "200", Messages.PriorityValue_SubProcTaskNormal200_label), //$NON-NLS-1$
                    new PriorityValue(
                            "300", Messages.PriorityValue_SubProcTaskHigh300_label), //$NON-NLS-1$
                    new PriorityValue(
                            "400", Messages.PriorityValue_SubProcTaskHigher400_label), //$NON-NLS-1$
            };

    /*
     * Valid values for whole process priority setting
     */
    private static PriorityValue[] processPriorityValues =
            new PriorityValue[] {
                    /*
                     * null string "" = element present BUT empty =
                     * "Inherit from parent"
                     */
                    new PriorityValue("", Messages.PriorityValue_Default2), //$NON-NLS-1$
                    new PriorityValue(
                            "100", Messages.PriorityValue_ProcessLow100_label), //$NON-NLS-1$
                    new PriorityValue(
                            "200", Messages.PriorityValue_ProcessNormal200_label), //$NON-NLS-1$
                    new PriorityValue(
                            "300", Messages.PriorityValue_ProcessHigh300_label), //$NON-NLS-1$
                    new PriorityValue(
                            "400", Messages.PriorityValue_ProcessHigher400_label), //$NON-NLS-1$
            };

    private String value;

    private String label;

    private PriorityValue(String value, String label) {
        super();
        this.value = value;
        this.label = label;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PriorityValue) {
            return value.equals(((PriorityValue) obj).value);
        }
        return false;
    }
}
