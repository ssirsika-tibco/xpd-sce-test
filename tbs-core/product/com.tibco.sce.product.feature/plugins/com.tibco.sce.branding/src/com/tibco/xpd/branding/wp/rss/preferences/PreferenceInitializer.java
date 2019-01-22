/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.branding.wp.rss.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.branding.BrandingPlugin;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

    

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
    public void initializeDefaultPreferences() {
        IPreferenceStore store = BrandingPlugin.getDefault()
                .getPreferenceStore();
        store
                .setDefault(PreferenceConstants.CUSTOM_URL,
                        PreferenceConstants.DEFAULT_STUDIO_FORUM_URL); 
    }

}
