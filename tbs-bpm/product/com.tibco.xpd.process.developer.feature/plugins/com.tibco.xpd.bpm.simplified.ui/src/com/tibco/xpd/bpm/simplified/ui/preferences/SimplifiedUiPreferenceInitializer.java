package com.tibco.xpd.bpm.simplified.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.bpm.simplified.ui.BpmSimplifiedUiActivator;

/**
 * Class used to initialize default preference values.
 *
 * @author jarciuch
 * @since 15 Jun 2015
 */
public class SimplifiedUiPreferenceInitializer extends
        AbstractPreferenceInitializer {

    public static final String CHANGE_CAPABILITIES_WHEN_SWITCHING_PERSPECTIVE =
            "CHANGE_CAPABILITIES_WHEN_SWITCHING_PERSPECTIVE"; //$NON-NLS-1$

    public static final String LAST_ACTIVE_PERSPECTIVE =
            "LAST_ACTIVE_PERSPECTIVE"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
     * initializeDefaultPreferences()
     */
    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store =
                BpmSimplifiedUiActivator.getDefault().getPreferenceStore();

        store.setDefault(SimplifiedUiPreferenceInitializer.CHANGE_CAPABILITIES_WHEN_SWITCHING_PERSPECTIVE,
                true);

        store.setDefault(SimplifiedUiPreferenceInitializer.LAST_ACTIVE_PERSPECTIVE,
                ""); //$NON-NLS-1$
    }

}
