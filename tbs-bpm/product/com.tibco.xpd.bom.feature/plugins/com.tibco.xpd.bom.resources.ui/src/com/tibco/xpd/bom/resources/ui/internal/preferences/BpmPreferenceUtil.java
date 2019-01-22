package com.tibco.xpd.bom.resources.ui.internal.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.bom.resources.ui.Activator;

/**
 * BPM related preferences utility. It is also used to initialize default
 * preference values.
 * 
 * @author jarciuch
 * @since 3.5.3
 */
public class BpmPreferenceUtil extends AbstractPreferenceInitializer {

    /**
     * Property key for studio in a BPM debug mode. In BPM debug mode all
     * projects BOM's generated projects are built incrementally and they will
     * also have java and pde builders installed on them.
     */
    public static final String IS_IN_BPM_DEBUG_MODE = "isInBpmDebugMode"; //$NON-NLS-1$

    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        store.setDefault(IS_IN_BPM_DEBUG_MODE, false);
    }

    public static boolean isInBpmDebugMode() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        return store.getBoolean(IS_IN_BPM_DEBUG_MODE);
    }

    public static void setInBpmDebugMode(boolean value) {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        store.setValue(IS_IN_BPM_DEBUG_MODE, value);
    }

}
