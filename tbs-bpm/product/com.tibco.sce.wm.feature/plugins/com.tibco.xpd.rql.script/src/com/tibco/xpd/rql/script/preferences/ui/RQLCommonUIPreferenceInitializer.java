/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.rql.script.preferences.ui;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.wst.sse.ui.internal.preferences.ui.ColorHelper;

import com.tibco.xpd.rql.script.RQLScriptPlugin;
import com.tibco.xpd.rql.script.styles.IStyleConstantsRQL;

/**
 * Sets default values for RQL Common UI preferences
 */
public class RQLCommonUIPreferenceInitializer extends
        AbstractPreferenceInitializer {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
     */
    public void initializeDefaultPreferences() {
        IPreferenceStore store =
                RQLScriptPlugin.getDefault().getPreferenceStore();

        // editor prefs
        store.setDefault(RQLCommonUIPreferenceNames.AUTO_PROPOSE, true);
        store.setDefault(RQLCommonUIPreferenceNames.AUTO_PROPOSE_CODE, ".,(");//$NON-NLS-1$

        // JavaScript Style Preferences
        String NOBACKGROUNDBOLD = " | null | false"; //$NON-NLS-1$
        String styleValue = "null" + NOBACKGROUNDBOLD; //$NON-NLS-1$
        store.setDefault(IStyleConstantsRQL.DEFAULT, styleValue);

        // keywords not "bold" at request of Usability
        styleValue = ColorHelper.getColorString(127, 0, 85) + NOBACKGROUNDBOLD;
        store.setDefault(IStyleConstantsRQL.KEYWORDENTITIES, styleValue);

        styleValue = ColorHelper.getColorString(142, 0, 255) + NOBACKGROUNDBOLD;
        store.setDefault(IStyleConstantsRQL.KEYWORDATTRIBUTES, styleValue);

        styleValue = ColorHelper.getColorString(0, 0, 255) + NOBACKGROUNDBOLD;
        store.setDefault(IStyleConstantsRQL.KEYWORDOPERATORS, styleValue);

        store.setDefault(RQLCommonUIPreferenceNames.INDENTATION_CHAR,
                RQLCommonUIPreferenceNames.SPACE);
        store.setDefault(RQLCommonUIPreferenceNames.INDENTATION_SIZE, 4);
    }

}
