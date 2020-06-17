/*
 * Copyright (c) TIBCO Software Inc 2004, 2020. All rights reserved.
 */

package com.tibco.xpd.n2.live;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerContainer;
import com.tibco.xpd.deploy.ServerType;
import com.tibco.xpd.deploy.impl.DeployFactoryImpl;
import com.tibco.xpd.deploy.manager.ServerManager;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.n2.live.internal.Activator;
import com.tibco.xpd.n2.live.internal.Messages;

/**
 * 
 * Utility class for openspace view
 * 
 * @author Ali
 * @since 4 Sep 2014
 */
public class OpenspaceViewHelper {

    /**
     * Sid ACE-2918 - In ACE there are no deployment servers, so the preferences just holds a single in-built server id.
     * 
     * For now we will keep this class as permitting multiple servers as we may introduce deployment servers back into
     * ACE or may allow the user to manually create multiple server configurations.
     */
    public static final String ACE_DUMMY_DEFAULT_SERVER_ID = "$$_ACE_DEFAULT_SERVER_ID_$$"; //$NON-NLS-1$

    private static final String ACE_DUMMY_DEFAULT_SERVER_TYPE_ID = "$$_ACE_DEFAULT_SERVER_TYPE_ID_$$"; //$NON-NLS-1$

    /** Openspace web application URL path.
     * Sid ACE-2918 - change of URL for new ACE Work Manager  */
    private static final String OPENSPACE_WEB_PATH = "/apps/work-manager/index.html"; //$NON-NLS-1$

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
     * Sid ACE-2918 Cache of dummy deployment server that has alreay been created (for now there will only ever be one
     */
    private static Map<String, Server> dummyServers = new HashMap<String, Server>();


    /**
     * @return deployment server Id stored in preference store or null
     */
    public static String getServerIdFromPreferences() {

        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        String serverId = store.getString(LIVE_DEV_SERVER_ID_PREF);
        if (!serverId.isEmpty()) {
            return serverId;
        } else {
            /* Sid ACE-2918 Always make sure the default server is set if nothing else). */
            store.setValue(LIVE_DEV_SERVER_ID_PREF, ACE_DUMMY_DEFAULT_SERVER_ID);

            return ACE_DUMMY_DEFAULT_SERVER_ID;
        }

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
     * Saves given openspace base URL in the preference store for the given server Id
     * 
     * Sid ACE-2918 Seletion based on serverId rather than Deploy Server 
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
     * If the openspace base URL is stored in the prefs for the given server then returns it otherwise creates a default
     * base URL.
     * 
     * Sid ACE-2918 Seletion based on serverId rather than Deploy Server
     * 
     * @param serverId
     * @return openspace url for the given server or null
     */
    public static String getOpenspaceBaseURL(String serverId) {

        String openspaceUrl =
                OpenspaceViewHelper
                        .getOpenspaceBaseUrlFromPrefs(serverId);

        if (openspaceUrl == null) {

            try {
                /*
                 * Sid ACE-2918 Initial URL now based on a hard coded <domain> template rather than extracted from a
                 * deply server.
                 */
                
                // XPD-6850 Default to a different port for local and remote dev
                // servers.
                int port = OPENSPACE_REMOTE_PORT;
                if ("localDevServer".equals(serverId)) { //$NON-NLS-1$
                    port = OPENSPACE_LOCAL_PORT;
                }
                openspaceUrl =
                        new URL("https", Messages.OpenspaceViewHelper_DomainNameForDefaultTemplateURL, port, OPENSPACE_WEB_PATH) //$NON-NLS-1$
                                .toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return openspaceUrl;
    }

    /**
     * Sid ACE-2918
     * 
     * @param openspaceURL
     * @return <code>true</code> if the given URL string still has the default template domain name "<domain>"
     * @throws MalformedURLException
     */
    public static boolean isTemplateOpenspaceURLDomain(String openspaceURL) throws MalformedURLException {
        URL url = new URL(openspaceURL);
        String host = url.getHost();

        if (Messages.OpenspaceViewHelper_DomainNameForDefaultTemplateURL.equals(host)) {
            return true;
        }
        return false;
    }

    /**
     * Creates openspace URL by appending parameters to the base URL. If the base URL is stored in the prefs for the
     * given server then uses that otherwise creates default base URL from the given server
     * 
     * Sid ACE-2918 Seletion based on serverId rather than Deploy Server
     * 
     * @param serverId
     * @return openspace url with params if any for the given server or null
     */
    public static String getOpenspaceURLWithParams(String serverId) {

        String openspaceUrl = getOpenspaceBaseURL(serverId);

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

            /*
             * Sid / Birju ACE-4008 : Support insertion of query pattern BEFORE # tag routing elements (i.e. where base
             * URL is /work-views in http://ace-nightly-test.emea.tibco.com/apps/work-manager/#/work-views)
             */
            int hashIdx = openspaceUrl.indexOf('#');

            StringBuilder completeUrl = new StringBuilder();
            completeUrl.append(hashIdx >= 0 ? openspaceUrl.substring(0, hashIdx) : openspaceUrl);
            completeUrl.append("?"); //$NON-NLS-1$
            completeUrl.append(params);

            if (hashIdx >= 0) {
            	completeUrl.append(openspaceUrl.substring(hashIdx));
            }

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
    
    /**
     * Sid ACE-2918 Get the deploy server with the given id.
     * 
     * Initially in ACE we will Create dummy server EMF object for the given server ID - this can be used as input for
     * the Live Dev View and property sections to avoid too much change (as these are
     * AbstractTransactionalPropertySection sub-classes which require an EMF object input - specifically a Deploy Server
     * object).
     * 
     * This dummy server is actually saved to the deploy servers file BECAUSE the base property sections, in order to
     * function correctly, actually need the input EMF object to be in a resource.
     * 
     * Later on, when we introduce ACTUAL deploy servers, then this function can changed just to retrieve the given
     * server.
     * 
     * @param serverId
     */
    public static Server getDeployServer(String serverId) {
        /* Check if already created and cached. */
        Server server = dummyServers.get(serverId);

        if (server == null) {
            /* Find the existing server if we've been here before in this workspace. */
            ServerManager serverManager = DeployUIActivator.getServerManager();
            serverManager.loadServerContainer();

            ServerContainer serverContainer = serverManager.getServerContainer();

            for (Server serv : serverContainer.getServers()) {
                if (serverId.equals(serv.getId())) {
                    server = serv;
                    break;
                }
            }

            if (server == null) {
                /* We've not been here before to create it, so do so now. */
                server = DeployFactoryImpl.eINSTANCE.createServer();
                server.eSet(DeployPackage.eINSTANCE.getUniqueIdElement_Id(), serverId);
                server.setName(Messages.OpenspaceViewPart_DefaultServer_label);

                ServerType serverType = DeployFactory.eINSTANCE.createServerType();
                serverType.setId(ACE_DUMMY_DEFAULT_SERVER_TYPE_ID);
                serverType.setName(Messages.OpenspaceViewPart_DefaultServer_label);

                serverContainer.getServerTypes().add(serverType);
                
                server.setServerType(serverType);

                /* Add and save it. */
                serverContainer.getServers().add(server);

                serverManager.saveServerContainer();
            }

            /* Cache it for next time. */
            dummyServers.put(serverId, server);
        }

        return server;
    }
}
