/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.n2.resources;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

import com.tibco.xpd.n2.resources.ui.FlowAnalyzerPreferenceContributor;

/**
 * Preference store initalizer for n2.resources plugin.
 * 
 * @author aallway
 * @since 4 Jul 2014
 */
public class N2PreferenceInitializer extends AbstractPreferenceInitializer {

    public N2PreferenceInitializer() {
    }

    /**
     * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
     * 
     */
    @Override
    public void initializeDefaultPreferences() {
        /* FLow analyzer timeout. */
        BundleActivator
                .getDefault()
                .getPreferenceStore()
                .setDefault(FlowAnalyzerPreferenceContributor.FLOW_ANALYZER_TIMEOUT_PREF,
                        FlowAnalyzerPreferenceContributor.FLOW_ANALYZER_TIMEOUT_DEFAULT);
    }

}
