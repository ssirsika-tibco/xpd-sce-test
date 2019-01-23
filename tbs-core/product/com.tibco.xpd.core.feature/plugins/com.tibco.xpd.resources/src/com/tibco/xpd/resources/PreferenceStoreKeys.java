/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.resources;

/**
 * Keys for the preference store.
 * 
 * @author njpatel
 * 
 */
public final class PreferenceStoreKeys {
    /**
     * HTTP Timeout key in the preference store - stored in milliseconds
     */
    public static final String HTTP_TIMEOUT = "HTTP_TIMEOUT"; //$NON-NLS-1$

    // The following are the keys for the user profiles
    public static final String USER_NAME = "userInfo.userName";

    public static final String DOMAIN_NAME = "userInfo.domainName";

    public static final String ORG_NAME = "userInfo.organisationName";

    public static final String ENDPOINT_URI = "userInfo.endpointURI";

    public static final String DESTINATION = "userInfo.destination";

    /**
     * Boolean flag to skip workspace full build on activities's enablement
     * change .
     */
    public static final String SKIP_BUILD_ON_ACTIVITY_CHANGE =
            "SKIP_BUILD_ON_ACTIVITY_CHANGE"; //$NON-NLS-1$
}
