/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.n2.daa.internal.deploy;

import java.net.MalformedURLException;
import java.net.URL;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ui.actions.OpenInBrowserHandler;
import com.tibco.xpd.n2.daa.Activator;

/**
 * Opens Workspace in a browser.
 * 
 * @author Jan Arciuchiewicz
 */
public class OpenWorkspaceInBrowserHandler extends OpenInBrowserHandler {

    /** Workspace web application URL path. */
    private static final String WORKSPACE_WEB_PATH =
            "/workspace/workspace.html"; //$NON-NLS-1$

    /** Workspace web application port. */
    private static final int WORKSPACE_PORT = 8080;

    /** Workspace web application port. */
    private static final int WORKSPACE_LOCAL_PORT = 8181;

    /** AMX Administrator server URL configuration parameter key. */
    private static final String ADMIN_URL_PARAM_KEY = "serverUrl"; //$NON-NLS-1$

    /** Type Id of AMX Administrator server. */
    private static final String ADMIN_SERVER_TYPE_ID =
            "com.tibco.amf.tools.admincligen.adminserver"; //$NON-NLS-1$

    @Override
    protected URL getURL(Object element) {
        if (element instanceof Server
                && isServerOfType((Server) element, ADMIN_SERVER_TYPE_ID)) {
            Server server = (Server) element;
            String serverURL = getServerParamValue(server, ADMIN_URL_PARAM_KEY);
            try {
                URL adminURL = new URL(serverURL);
                String protocol = adminURL.getProtocol();
                String host = adminURL.getHost();
                // XPD-6908 Defaulted local development server to port 8181.
                int port = WORKSPACE_PORT;
                if ("localDevServer".equals(server.getId())) { //$NON-NLS-1$
                    port = WORKSPACE_LOCAL_PORT;
                }
                return new URL(protocol, host, port, WORKSPACE_WEB_PATH);
            } catch (MalformedURLException e) {
                Activator.getDefault().getLogger().error(e, e.getMessage());
            }
        }
        return null;
    }
}
