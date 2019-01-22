/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.simulation.annotation;

import java.util.ResourceBundle;

/**
 * @author nwilson
 */
public final class Messages {
    
    /**
     * Private constructor.
     */
    private Messages() {
    }
    
    /** The resource bundle id. */
    private static final String RESOURCE_BUNDLE =
            "com.tibco.xpd.simulation.annotation.messages"; //$NON-NLS-1$

    /**  */
    private static ResourceBundle bundle =
            ResourceBundle.getBundle(RESOURCE_BUNDLE);

    /**
     * @return The resource bundle.
     */
    public static ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * @param key The message key.
     * @return The message.
     */
    public static String getString(String key) {
        return bundle.getString(key);
    }
}
