/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.util;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.webdav.http.client.IAuthenticator;

/**
 * @deprecated Moved to
 *             {@link com.tibco.xpd.deploy.server.webdav.SimpleBasicAuthenticator}
 *             . Please use it instead.
 */
@Deprecated
public class SimpleBasicAuthenticator implements IAuthenticator {
    private final static String BASIC_AUTH = "BASIC"; //$NON-NLS-1$

    Map<String, String> info;

    public SimpleBasicAuthenticator(String username, String password) {
        info = new HashMap<String, String>();
        info.put("username", username); //$NON-NLS-1$
        info.put("password", password); //$NON-NLS-1$
    }

    @SuppressWarnings("unchecked")
    public void addAuthenticationInfo(URL serverUrl, String realm,
            String scheme, Map info) {
    }

    public void addProtectionSpace(URL resourceUrl, String realm) {
    }

    @SuppressWarnings("unchecked")
    public Map getAuthenticationInfo(URL serverUrl, String realm, String scheme) {
        if (scheme != null && scheme.toUpperCase().equals(BASIC_AUTH)) {
            return info;
        }
        return null;
    }

    public String getProtectionSpace(URL resourceUrl) {
        return resourceUrl.getAuthority();
    }

    @SuppressWarnings("unchecked")
    public Map requestAuthenticationInfo(URL resourceUrl, String realm,
            String scheme) {
        if (scheme != null && scheme.toUpperCase().equals(BASIC_AUTH)) {
            return info;
        }
        return null;
    }
}