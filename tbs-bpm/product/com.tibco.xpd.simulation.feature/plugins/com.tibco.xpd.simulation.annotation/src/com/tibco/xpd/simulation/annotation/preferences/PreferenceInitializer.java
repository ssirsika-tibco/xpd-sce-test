/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.annotation.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.simulation.annotation.SimulationAnnotationPlugin;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

    /**
     * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
     *      initializeDefaultPreferences()
     */
    public void initializeDefaultPreferences() {
        IPreferenceStore store =
                SimulationAnnotationPlugin.getDefault().getPreferenceStore();
        store.setDefault(PreferenceConstants.P_OBSERVED, true);
        store.setDefault(PreferenceConstants.P_CURRENT, true);
        store.setDefault(PreferenceConstants.P_DELAY, true);
        store.setDefault(PreferenceConstants.P_UTILISATION, true);
    }

}
