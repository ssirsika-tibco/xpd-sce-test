/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

import java.util.ResourceBundle;

/**
 * @author nwilson
 */
public final class Messages {
    /**
     * Private Constructor.
     */
    private Messages() {
    }
    
    /** The resource bundle id. */
    private static final String RESOURCE_BUNDLE =
            "com.tibco.xpd.mapper.messages"; //$NON-NLS-1$

    /** The resource bundle. */
    private static ResourceBundle bundle =
            ResourceBundle.getBundle(RESOURCE_BUNDLE);

    /**
     * @return The resource bundle.
     */
    public static ResourceBundle getBundle() {
        return bundle;
    }
    
    /**
     * @param id The message key.
     * @return The message.
     */
    public static String getString(String id) {
        return bundle.getString(id);
    }
}
