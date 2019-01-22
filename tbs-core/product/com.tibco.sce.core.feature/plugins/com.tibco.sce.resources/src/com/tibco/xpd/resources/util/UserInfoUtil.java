/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.resources.util;

import static com.tibco.xpd.resources.PreferenceStoreKeys.DESTINATION;
import static com.tibco.xpd.resources.PreferenceStoreKeys.DOMAIN_NAME;
import static com.tibco.xpd.resources.PreferenceStoreKeys.ENDPOINT_URI;
import static com.tibco.xpd.resources.PreferenceStoreKeys.ORG_NAME;
import static com.tibco.xpd.resources.PreferenceStoreKeys.USER_NAME;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.preference.IPreferenceStore;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;

/**
 * UserInfoUtil - utility class to 1.load/save workspace/project specific
 * preference settings for UserInformation preference page 2. to restore default
 * settings when RestoreDefaults is pressed
 * 
 * @author bharge
 * 
 */
public final class UserInfoUtil {

    private static final String PROJECT_SPECIFIC_NODE =
            "userInfoProjectPreferenceNode"; //$NON-NLS-1$

    private final static IPreferenceStore store =
            XpdResourcesPlugin.getDefault().getPreferenceStore();

    private UserInfoUtil() {
        // Static class
    }

    /**
     * Get the project-level user profile settings. If no project-specific
     * settings are available then the workspace settings will be returned.
     * 
     * @param project
     * @return UserInfo if set, workspace-level values otherwise.
     */
    public static UserInfo getProjectPreferences(IProject project) {
        UserInfo workspaceSettings = getWorkspacePreferences();
        if (project != null) {
            IEclipsePreferences preference =
                    getEclipseProjectPreference(project);
            try {
                if (preference != null
                        && preference.nodeExists(PROJECT_SPECIFIC_NODE)) {
                    Preferences node = preference.node(PROJECT_SPECIFIC_NODE);

                    // Use the workspace level settings as the defaults
                    return new UserInfo(node.get(USER_NAME, workspaceSettings
                            .getUserName()), node.get(DOMAIN_NAME,
                            workspaceSettings.getDomainName()), node
                            .get(ORG_NAME, workspaceSettings
                                    .getOrganisationName()), node
                            .get(ENDPOINT_URI, workspaceSettings
                                    .getEndpointURI()), node.get(DESTINATION,
                            workspaceSettings.getDestination()));

                }
            } catch (BackingStoreException e) {
                logCannotAccessPreferences(project, e);
            }
        }
        return workspaceSettings;
    }

    /**
     * Get the workspace-level user profile settings.
     * 
     * @return
     */
    public static UserInfo getWorkspacePreferences() {
        return new UserInfo(store.getString(USER_NAME), store
                .getString(DOMAIN_NAME), store.getString(ORG_NAME), store
                .getString(ENDPOINT_URI), store.getString(DESTINATION));
    }

    public static void saveUserInfoToWorkspacePreferences(UserInfo info) {
        if (info != null) {
            setStoreValue(USER_NAME, info.getUserName());
            setStoreValue(DOMAIN_NAME, info.getDomainName());
            setStoreValue(ORG_NAME, info.getOrganisationName());
            setStoreValue(ENDPOINT_URI, info.getEndpointURI());
            setStoreValue(DESTINATION, info.getDestination());
        }
    }

    /**
     * Check if the project-level user profile settings.
     * 
     * @param project
     * @return
     */
    public static boolean hasProjectSpecificUserInfoSettings(IProject project) {
        if (project != null) {
            IEclipsePreferences preference =
                    getEclipseProjectPreference(project);
            try {
                return preference.nodeExists(PROJECT_SPECIFIC_NODE);
            } catch (BackingStoreException e) {
                logCannotAccessPreferences(project, e);
            }
        }
        return false;
    }

    /**
     * Set the given key value in the workspace store.
     * 
     * @param key
     * @param value
     */
    private static void setStoreValue(String key, String value) {
        store.setValue(key, value != null ? value : ""); //$NON-NLS-1$
    }

    public static void saveUserInfoToProjectPreferences(IProject project,
            UserInfo info) {
        if (project != null && info != null) {
            IEclipsePreferences preference =
                    getEclipseProjectPreference(project);
            Preferences node = preference.node(PROJECT_SPECIFIC_NODE);

            setProjectPreference(node, USER_NAME, info.getUserName());
            setProjectPreference(node, DOMAIN_NAME, info.getDomainName());
            setProjectPreference(node, ORG_NAME, info.getOrganisationName());
            setProjectPreference(node, ENDPOINT_URI, info.getEndpointURI());
            setProjectPreference(node, DESTINATION, info.getDestination());
            try {
                node.flush();
            } catch (BackingStoreException e) {
                logCannotAccessPreferences(project, e);
            }
        }
    }

    /**
     * Set the value for the key in the given project preference.
     * 
     * @param preference
     * @param key
     * @param value
     */
    private static void setProjectPreference(Preferences preference,
            String key, String value) {
        preference.put(key, value != null ? value : "");//$NON-NLS-1$
    }

    /**
     * Get the default user-profile values.
     * 
     * @return
     */
    public static UserInfo getDefaultValue() {
        return new UserInfo(store.getDefaultString(USER_NAME), store
                .getDefaultString(DOMAIN_NAME), store
                .getDefaultString(ORG_NAME), store
                .getDefaultString(ENDPOINT_URI), store
                .getDefaultString(DESTINATION));
    }

    /**
     * Get the project preferences of the given project.
     * 
     * @param project
     * @return
     */
    private static IEclipsePreferences getEclipseProjectPreference(
            IProject project) {
        ProjectScope scope = new ProjectScope(project);
        IEclipsePreferences preferences =
                scope.getNode(XpdResourcesPlugin.ID_PLUGIN);
        return preferences;
    }

    /**
     * Remove the project-specific user profile settings.
     * 
     * @param project
     */
    public static void removeProjectPreferences(IProject project) {
        if (project != null) {
            IEclipsePreferences preference =
                    getEclipseProjectPreference(project);
            try {
                if (preference.nodeExists(PROJECT_SPECIFIC_NODE)) {
                    preference.node(PROJECT_SPECIFIC_NODE).removeNode();
                    preference.flush();
                }
            } catch (BackingStoreException e) {
                logCannotAccessPreferences(project, e);
            }
        }
    }

    /**
     * Log unable to access preference for the given project.
     * 
     * @param project
     * @param e
     */
    private static void logCannotAccessPreferences(IProject project, Exception e) {
        XpdResourcesPlugin.getDefault().getLogger().error(e,
                String.format(Messages.UserInfoUtil_cannotAccessProjectPreference_error_message,
                        project.getName()));
    }
}
