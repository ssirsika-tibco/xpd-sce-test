/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.ui;

import com.tibco.xpd.rest.schema.ui.RestConstants;
import com.tibco.xpd.rest.ui.internal.Messages;

/**
 * Utility class for providing constants.
 * 
 * @author nwilson
 * @since 12 Jan 2015
 */
public final class RestServicesUtil {

    /**
     * Version of the Rest project asset (used by migration).
     */
    public static final String REST_ASSET_VERSION = "1".toString(); //$NON-NLS-1$

    /**
     * REST special folder kind.
     */
    /* This is alias of RestConstants.REST_SPECIAL_FOLDER_KIND. */
    public static final String REST_SPECIAL_FOLDER_KIND =
            RestConstants.REST_SPECIAL_FOLDER_KIND;

    /**
     * REST Services special folder default name (externalized).
     */
    public static final String REST_SPECIAL_FOLDER_DEFAULT_NAME =
            Messages.RestServicesUtil_RestSf_name;

    /**
     * Default RSD file name (externalized).
     */
    public final static String RSD_DEFAULT_FILENAME = Messages.RestServicesUtil_DefaultRsdFile_name;

}
