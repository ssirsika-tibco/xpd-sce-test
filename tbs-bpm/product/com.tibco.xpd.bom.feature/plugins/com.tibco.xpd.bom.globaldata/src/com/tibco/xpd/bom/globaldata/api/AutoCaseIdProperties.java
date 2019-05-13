/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.bom.globaldata.api;

import org.eclipse.uml2.uml.Property;

/**
 * Utility class to manage AutoCaseIdentifier stereotype property values.
 *
 * @author jarciuch
 * @since 13 May 2019
 */
public class AutoCaseIdProperties {

    /* Getters and setter for AutoCaseId stereotype properties */
    /**
     * Gets the minimal number of digits in the auto generated identifier.
     * 
     * @param prop
     *            the case id class attribute.
     * @return the current value of minDigits as set in the tagged value or
     *         default 0 if not set.
     */
    public Integer getMinDigits(Property prop) {
        Object value = BOMGlobalDataUtils.getAutoCidPropetyValue(prop,
                BOMGlobalDataUtils.AutoCidProperty.MIN_DIGITS);
        if (value instanceof Integer) {
            return (Integer) value;
        }
        return Integer.valueOf(0);
    }

    /**
     * Sets minimal number of digits in the auto generated identifier.
     * 
     * @param prop
     *            the case id class attribute.
     * @param minDigits
     *            minDigitst to set. If the minDigits is null or empty string
     *            then minDigits is set to null.
     */
    public void setMinDigits(Property prop, String minDigits) {
        Integer minDigitsInteger = null;
        if (minDigits != null && !minDigits.isEmpty()) {
            minDigitsInteger = Integer.valueOf(minDigits);
        }
        BOMGlobalDataUtils.setAutoCidPropetyValue(prop,
                BOMGlobalDataUtils.AutoCidProperty.MIN_DIGITS,
                minDigitsInteger);
    }

    /**
     * Gets prefix of auto generated identifier.
     * 
     * @param prop
     *            the case id class attribute.
     * @return prefix of auto generated identifier or empty string if prefix is
     *         not defined.
     */
    public String getPrefix(Property prop) {
        Object value = BOMGlobalDataUtils.getAutoCidPropetyValue(prop,
                BOMGlobalDataUtils.AutoCidProperty.PREFIX);
        if (value instanceof String) {
            return (String) value;
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Sets the prefix of of auto generated identifier.
     * 
     * @param prop
     *            the case id class attribute.
     * @param prefix
     *            the prefix to set.
     */
    public void setPrefix(Property prop, String prefix) {
        if (prefix != null && prefix.isEmpty()) {
            prefix = null; // Unset empty string value.
        }
        BOMGlobalDataUtils.setAutoCidPropetyValue(prop,
                BOMGlobalDataUtils.AutoCidProperty.PREFIX,
                prefix);
    }

    /**
     * Gets suffix of auto generated identifier.
     * 
     * @param prop
     *            the case id class attribute.
     * @return suffix of auto generated identifier or empty string if suffix is
     *         not defined.
     */
    public String getSuffix(Property prop) {
        Object value = BOMGlobalDataUtils.getAutoCidPropetyValue(prop,
                BOMGlobalDataUtils.AutoCidProperty.SUFFIX);
        if (value instanceof String) {
            return (String) value;
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Sets the suffix of of auto generated identifier.
     * 
     * @param prop
     *            the case id class attribute.
     * @param suffix
     *            the suffix to set.
     */
    public void setSuffix(Property prop, String suffix) {
        if (suffix != null && suffix.isEmpty()) {
            suffix = null; // Unset empty string value.
        }
        BOMGlobalDataUtils.setAutoCidPropetyValue(prop,
                BOMGlobalDataUtils.AutoCidProperty.SUFFIX,
                suffix);
    }

}
