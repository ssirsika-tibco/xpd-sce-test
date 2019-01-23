package com.tibco.xpd.resources;

/**
 * Studio configuration options. Use {@link #isOn()} method to check if option's
 * value is true.
 * 
 * Example:
 * 
 * <pre>
 * if (XpdConfigOption.IN_MEMORY_INDEX_DB.isOn()) {
 *     // In memory db is/should be used.
 *     // ...
 * }
 * </pre>
 *
 * @author jarciuch
 * @since 18 Mar 2016
 */
public enum XpdConfigOption {

    /** If in-memory indexer DB should be used. */
    IN_MEMORY_INDEX_DB("IN_MEMORY_INDEX_DB", false), //$NON-NLS-1$

    /** If cross reference adapter with lazy loading should be used. */
    LAZY_CROSS_REFERENCE_ADAPTER("LAZY_CROSS_REFERENCE_ADAPTER", false), //$NON-NLS-1$

    /** If default WSDL indexer should be disabled. */
    SUPPRESS_DEFAULT_WSDL_INDEXER("SUPPRESS_DEFAULT_WSDL_INDEXER", false); //$NON-NLS-1$

    private final String preferenceName;

    private final boolean defautValue;

    /**
     * Constructor.
     * 
     * @param preferenceName
     *            the local name of the preference (the full name is
     *            com.tibco.xpd.resources/preferenceName).
     * @param defautValue
     *            the default value of the preference.
     */
    XpdConfigOption(String preferenceName, boolean defautValue) {
        this.preferenceName = preferenceName;
        this.defautValue = defautValue;
    }

    /**
     * Returns the current value of the option.
     * 
     * @return the value of the option.
     */
    public boolean isOn() {
        return XpdResourcesPlugin.getDefault().getPreferenceStore()
                .getBoolean(preferenceName);
    }

    /**
     * Returns preference name.
     * 
     * @return the preference the local name of the preference (the full name is
     *         com.tibco.xpd.resources/${preferenceName}).
     */
    public String getPreferenceName() {
        return preferenceName;
    }

    /**
     * Returns default option's value.
     * 
     * @return the defautValue.
     */
    public boolean getDefautValue() {
        return defautValue;
    }
}