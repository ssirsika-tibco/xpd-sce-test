/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.resources.internal;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.resources.PreferenceStoreKeys;
import com.tibco.xpd.resources.XpdConfigOption;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Preference store initializer for all preferences contributed by this plug-in.
 * 
 * @author njpatel
 * @since 3.3
 */
public class ResourcePreferenceInitializer extends
        AbstractPreferenceInitializer {
    private final IPreferenceStore prefStore;

    public ResourcePreferenceInitializer() {
        prefStore = XpdResourcesPlugin.getDefault().getPreferenceStore();
    }

    @Override
    public void initializeDefaultPreferences() {
        // Initialize the HTTP timeout value (to 60 seconds by default)
        prefStore.setDefault(PreferenceStoreKeys.HTTP_TIMEOUT, 60000);

        prefStore.setDefault(PreferenceStoreKeys.USER_NAME,
                System.getProperty("user.name"));

        prefStore.setDefault(PreferenceStoreKeys.DOMAIN_NAME,
                Messages.UserInfoPreferencePage_domainName_defaultValue);

        prefStore.setDefault(PreferenceStoreKeys.ORG_NAME,
                Messages.UserInfoPreferencePage_orgName_defaultValue);

        prefStore.setDefault(PreferenceStoreKeys.ENDPOINT_URI,
                Messages.UserInfoPreferencePage_endpointURI_defaultValue);

        prefStore.setDefault(PreferenceStoreKeys.DESTINATION,
                Messages.UserInfoPreferencePage_destination_defaultValue);

        prefStore.setDefault(PreferenceStoreKeys.SKIP_BUILD_ON_ACTIVITY_CHANGE,
                true);
                
        for (XpdConfigOption option : XpdConfigOption.values()) {
            prefStore.setDefault(option.getPreferenceName(),
                    option.getDefautValue());
        }        
    }
}
