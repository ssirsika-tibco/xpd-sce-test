/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 *
 * @author aallway
 * @since 3.3 (25 Jun 2010)
 */
public class Messages {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.implementer.resources.xpdl2.properties.messages"; //$NON-NLS-1$

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
