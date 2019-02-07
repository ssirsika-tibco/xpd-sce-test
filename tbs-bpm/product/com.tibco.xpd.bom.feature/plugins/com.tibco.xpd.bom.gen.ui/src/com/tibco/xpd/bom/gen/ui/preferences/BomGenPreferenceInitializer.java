/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.gen.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.bom.gen.ui.Activator;

/**
 * Class used to initialize default preference values.
 */
public class BomGenPreferenceInitializer extends AbstractPreferenceInitializer {

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
     * initializeDefaultPreferences()
     */
    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();

        /*
         * XPD-5248 moved from BomGenPreferencePage which used to set the
         * default when teh pref page was viewed (meaning it didn't default
         * until you viewed the pref page.
         */
        store.setDefault(BomGenPreferenceConstants.P_ENABLE_XSD_VALIDATION,
                true);
        store.setDefault(BomGenPreferenceConstants.P_ENABLE_REGENERATE_BOM_ON_PROJECT_IMPORT,
                false);

    }

    /**
     * 
     * @return true if the preference is set to re-generate BOM on import, false
     *         otherwise
     */
    public static boolean getPrefForRegenerateBomOnImport() {
        boolean regenerateBomOnImport = false;
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        if (store != null) {
            regenerateBomOnImport =
                    store.getBoolean(BomGenPreferenceConstants.P_ENABLE_REGENERATE_BOM_ON_PROJECT_IMPORT);
        }
        return regenerateBomOnImport;
    }
}
