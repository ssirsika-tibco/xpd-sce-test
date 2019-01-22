/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;

/**
 * Preference Initialiser for all the Process Editor preferences.
 * 
 * @author rsomayaj
 * 
 */
public class ProcessEditorPreferenceInitializer extends
        AbstractPreferenceInitializer {

    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();
        store.setDefault(ProcessEditorConstants.PREF_DONT_ASK_AGAIN_FOR_SAVE,
                false);

        /*
         * Using process analyst store as this preference is used by
         * ProcessUIUtil in that plugin.
         */
        store = Xpdl2ResourcesPlugin.getDefault().getPreferenceStore();
        store.setDefault(Xpdl2ResourcesConsts.PREF_DONT_ASK_AGAIN_FOR_PROJECT_REFERENCE,
                false);

        /*
         * Adding the checksum preference to Xpdl2ResourcesPlugin as it's more
         * to do with the XPDL2 model rather than the editor.
         */
        store.setDefault(Xpdl2ResourcesConsts.PREF_GENERATE_CHECKSUM_FOR_GENERATED_WSDL_NAMESPACE,
                false);

        // Added for XPD-3499 expand mapper array fields for CIM.
        store.setDefault(Xpdl2ResourcesConsts.PREF_EXPAND_MAPPER_FIELDS, false);
    }

}
