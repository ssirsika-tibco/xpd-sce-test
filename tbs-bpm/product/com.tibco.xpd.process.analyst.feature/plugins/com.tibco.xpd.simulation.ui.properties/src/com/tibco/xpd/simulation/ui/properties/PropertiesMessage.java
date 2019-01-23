/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.properties;

import java.util.ResourceBundle;

/**
 * Property sheet messages.
 */
public final class PropertiesMessage {

    /** The resource bundle ID. */
    private static final String RESOURCE_BUNDLE =
            "com.tibco.xpd.simulation.ui.properties.messages"; //$NON-NLS-1$

    /** The resource bundle. */
    private static ResourceBundle bundle =
            ResourceBundle.getBundle(RESOURCE_BUNDLE);

    /**
     * Private constructor.
     */
    private PropertiesMessage() {
    }

    /**
     * @param key The message key.
     * @return The message.
     */
    public static String getString(String key) {
        return bundle.getString(key);
    }

}
