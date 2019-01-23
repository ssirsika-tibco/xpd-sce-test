/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.script.ui.internal.extension;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author rsomayaj
 *
 */
public class Messages {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.script.ui.extension.messages"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE =
            ResourceBundle.getBundle(BUNDLE_NAME);

    private Messages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
