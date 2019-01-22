/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.n2.live.internal;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.n2.live.IPropertyProvider;

/**
 * 
 * Utility class for openspace view
 * 
 * @author Ali
 * @since 4 Sep 2014
 */
public class OpenspaceViewHelper {

    /** Openspace web application URL path. */
    private static final String OPENSPACE_WEB_PATH =
            "/openspace/openspace.html"; //$NON-NLS-1$

    /** Openspace local dev server port. */
    private static final int OPENSPACE_LOCAL_PORT = 8181;

    /** Openspace remote dev server port. */
    private static final int OPENSPACE_REMOTE_PORT = 8080;

    /**
     * The live dev extension point ID.
     */
    private static final String EXT_POINT_ID = "LiveDevelopment"; //$NON-NLS-1$

    /**
     * URL params encoding.
     */
    private static final String UTF_8_ENCODING = "UTF-8"; //$NON-NLS-1$

    private static final String LIVE_DEV_OPENSPACE_URL_PREF =
            "liveDevOpenspaceUrl_%1$s"; //$NON-NLS-1$

    private static final String LIVE_DEV_SERVER_ID_PREF = "liveDevServerId"; //$NON-NLS-1$

    /**
     * @return deployment server Id stored in preference store or null
     */
    public static String getServerIdFromPreferences() {

        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        String serverId = store.getString(LIVE_DEV_SERVER_ID_PREF);
        if (!serverId.isEmpty()) {
            return serverId;
        }
        return null;
    }

    /**
     * Saves given serveId in preference store
     */
    public static void saveServerIdInPreferences(String serverId) {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        store.setValue(LIVE_DEV_SERVER_ID_PREF, serverId);
    }

    /**
     * 
     * @return openspace base URL stored in the preference or null
     */
    public static String getOpenspaceBaseUrlFromPrefs(String serverId) {

        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        String openspaceUrl =
                store.getString(String.format(LIVE_DEV_OPENSPACE_URL_PREF,
                        serverId));
        if (!openspaceUrl.isEmpty()) {
            return openspaceUrl;
        }
        return null;
    }

    /**
     * Saves given openspace base URL in the preference store for the given
     * server Id
     */
    public static void saveOpenspaceBaseUrlInPreferences(String openspaceUrl,
            String serverId) {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        String key = String.format(LIVE_DEV_OPENSPACE_URL_PREF, serverId);
        if (openspaceUrl == null) {
            store.setToDefault(key);
        } else {
            store.setValue(key, openspaceUrl);
        }
    }

    /**
     * If the openspace base URL is stored in the prefs for the given server
     * then returns it otherwise creates a default base URL.
     * 
     * @param server
     * @return openspace url for the given server or null
     */
    public static String getOpenspaceBaseURL(Server server) {

        String openspaceUrl =
                OpenspaceViewHelper
                        .getOpenspaceBaseUrlFromPrefs(server.getId());

        if (openspaceUrl == null) {

            String serverUrlL =
                    server.getServerConfig()
                            .getConfigParameter("serverUrl").getValue(); //$NON-NLS-1$
            try {
                URL url = new URL(serverUrlL);
                String protocol = url.getProtocol();
                String host = url.getHost();

                // XPD-6850 Default to a different port for local and remote dev
                // servers.
                int port = OPENSPACE_REMOTE_PORT;
                if ("localDevServer".equals(server.getId())) { //$NON-NLS-1$
                    port = OPENSPACE_LOCAL_PORT;
                }
                openspaceUrl =
                        new URL(protocol, host, port, OPENSPACE_WEB_PATH)
                                .toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return openspaceUrl;
    }

    /**
     * Creates openspace URL by appending parameters to the base URL. If the
     * base URL is stored in the prefs for the given server then uses that
     * otherwise creates default base URL from the given server
     * 
     * @param server
     * @return openspace url with params if any for the given server or null
     */
    public static String getOpenspaceURLWithParams(Server server) {

        String openspaceUrl = getOpenspaceBaseURL(server);

        if (openspaceUrl != null && !openspaceUrl.isEmpty()) {

            // Add parameters to the URL
            Set<String> urlParams = getUrlParameters();

            String params = ""; //$NON-NLS-1$
            for (String p : urlParams) {
                params += p + "&"; //$NON-NLS-1$
            }

            if (params.isEmpty()) {
                return openspaceUrl;
            }

            // remove last & from params
            params = params.substring(0, params.length() - 1);

            StringBuilder completeUrl = new StringBuilder();
            completeUrl.append(openspaceUrl);
            completeUrl.append("?"); //$NON-NLS-1$
            completeUrl.append(params);
            return completeUrl.toString();
        }

        return openspaceUrl;
    }

    /**
     * Get Openspace URL parameters contributed through extension point
     * 
     * @return set of parameter strings as 'key=value' contributed using the
     *         live dev extension point. Both the keys and values are are
     *         encoded using UTF-8
     */
    public static Set<String> getUrlParameters() {

        Set<String> params = new HashSet<String>();

        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(Activator.PLUGIN_ID, EXT_POINT_ID);
        if (point != null) {
            IConfigurationElement[] contributions =
                    point.getConfigurationElements();

            for (IConfigurationElement contribution : contributions) {

                if ("openspaceUrlContributor".equals(contribution.getName())) { //$NON-NLS-1$

                    try {
                        Object contributor =
                                contribution
                                        .createExecutableExtension("urlPropertyProvider"); //$NON-NLS-1$ 
                        if (contributor instanceof IPropertyProvider) {

                            IPropertyProvider propertyProvider =
                                    (IPropertyProvider) contributor;

                            Map<String, String> propertiesMap =
                                    propertyProvider.getProperties();
                            if (propertiesMap != null) {
                                for (Entry<String, String> prop : propertiesMap
                                        .entrySet()) {

                                    String key =
                                            URLEncoder.encode(prop.getKey(),
                                                    UTF_8_ENCODING);
                                    String value = prop.getValue();
                                    if (value != null) {
                                        String val =
                                                URLEncoder.encode(value,
                                                        UTF_8_ENCODING);
                                        params.add(key + "=" //$NON-NLS-1$
                                                + val);
                                    }
                                }
                            }
                        }
                    } catch (CoreException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        return params;
    }
}
