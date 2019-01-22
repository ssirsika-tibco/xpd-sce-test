/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.components;

import com.tibco.xpd.rsd.ui.internal.Messages;

/**
 * Media types used by RSD. Each enumeration will contain label and a model
 * value.
 *
 * @author jarciuch
 * @since 4 Feb 2015
 */
public enum MediaType {
    STANDARD_JSON(Messages.MediaType_JsonMediaType_label, "application/json"), //$NON-NLS-1$
    XML(Messages.MediaType_XmlMediaType_label, "application/xml"); //$NON-NLS-1$

    private String label;

    private String modelValue;

    private MediaType(String label, String modelValue) {
        this.label = label;
        this.modelValue = modelValue;
    }

    /**
     * @return the model value as it will be stored in the model.
     */
    public String getModelValue() {
        return modelValue;
    }

    /**
     * @return the label to be displayed.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Gets {@link MediaType} by the model's value.
     * 
     * @param modelValue
     *            the value stored in the model.
     * @return {@link MediaType} with this model's value or <code>null</code> if
     *         not found.
     */
    public static MediaType getByModelValue(String modelValue) {
        for (MediaType mt : values()) {
            if (mt.getModelValue().equals(modelValue)) {
                return mt;
            }
        }
        return null;
    }

}