/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.ui.internal;

/**
 * List of images registered with Image Registry.
 * 
 * @author nwilson
 * @since 12 Jan 2015
 */
public enum RestImage {
    REST_SPECIAL_FOLDER("icons/REST_SpecialFolder.png"); //$NON-NLS-1$

    private String id;

    RestImage(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
