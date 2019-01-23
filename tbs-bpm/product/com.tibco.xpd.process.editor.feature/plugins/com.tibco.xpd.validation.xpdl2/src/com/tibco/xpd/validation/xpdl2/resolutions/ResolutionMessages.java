/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.ResourceBundle;

/**
 * @author nwilson
 */
public final class ResolutionMessages {
    /**
     * Private Constructor.
     */
    private ResolutionMessages() {
    }
    
    /** The resource bundle id. */
    private static final String RESOURCE_BUNDLE =
            "com.tibco.xpd.validation.xpdl2.resolutions.messages"; //$NON-NLS-1$

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
     * @param id The id for the text string.
     * @return The string associated with the key.
     */
    public static String getText(String id) {
        return bundle.getString(id);
    }
}
