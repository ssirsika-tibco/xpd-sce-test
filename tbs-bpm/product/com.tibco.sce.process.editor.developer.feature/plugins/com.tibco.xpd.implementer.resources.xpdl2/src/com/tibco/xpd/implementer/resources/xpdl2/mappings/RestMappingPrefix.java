/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings;

import com.tibco.xpd.rsd.ParameterStyle;

/**
 * Enumeration for REST service payload / parameter name prefixes.
 * 
 * @author jarciuch
 * @since 21 Apr 2015
 */
public enum RestMappingPrefix {
    PATH_PARAM("REST_PATH_"), //$NON-NLS-1$

    QUERY_PARAM("REST_QUERY_"), //$NON-NLS-1$

    HEADER_PARAM("REST_HEADER_"), //$NON-NLS-1$

    PAYLOAD("REST_PAYLOAD"); //$NON-NLS-1$

    private String prefix;

    private RestMappingPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Check if the string starts with prefix.
     * 
     * @param s
     *            the string to check.
     * @return 'true' if the string starts with prefix.
     */
    public boolean startsWithPrefix(String s) {
        return s != null && s.startsWith(prefix);
    }

    /**
     * Removes prefix from the string.
     * 
     * @param s
     *            the string to remove prefix from.
     * @return string without prefix if it is prefixed or original string if s
     *         doesn't start with prefix.
     */
    public String removePrefix(String s) {
        return startsWithPrefix(s) ? s.substring(prefix.length()) : s;
    }

    /**
     * Prepends prefix to the string.
     * 
     * @param s
     *            the string to prepend prefix to.
     * @return a string with prepended prefix.
     */
    public String addPrefix(String s) {
        return prefix + s;
    }

    /**
     * Returns parameter prefix enum for the specified parameter style.
     * 
     * @param paramStyle
     *            the parameter style.
     * @return parameter prefix for the specified parameter style or
     *         <code>null</code> if unsupported parameter style.
     */
    public static RestMappingPrefix getForParamStyle(ParameterStyle paramStyle) {
        if (paramStyle == null) {
            return null;
        }
        switch (paramStyle) {
        case PATH:
            return PATH_PARAM;
        case QUERY:
            return QUERY_PARAM;
        case HEADER:
            return HEADER_PARAM;
        default:
            assert false : String.format("Unsupported parameter style: '%1$s'", //$NON-NLS-1$
                    paramStyle);
            return null;
        }
    }
}