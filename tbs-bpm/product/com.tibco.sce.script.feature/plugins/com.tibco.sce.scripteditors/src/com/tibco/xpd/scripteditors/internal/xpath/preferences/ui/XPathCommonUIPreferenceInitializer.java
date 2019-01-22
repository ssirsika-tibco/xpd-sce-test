package com.tibco.xpd.scripteditors.internal.xpath.preferences.ui;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.wst.sse.ui.internal.preferences.ui.ColorHelper;

import com.tibco.xpd.scripteditors.ScriptEditorsPlugin;
import com.tibco.xpd.scripteditors.internal.xpath.styles.IStyleConstantsXPath;

/**
 * Sets default values for XPath Common UI preferences
 */
public class XPathCommonUIPreferenceInitializer extends
        AbstractPreferenceInitializer {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
     */
    public void initializeDefaultPreferences() {
        IPreferenceStore store =
                ScriptEditorsPlugin.getDefault().getPreferenceStore();

        // editor prefs
        store.setDefault(XPathCommonUIPreferenceNames.AUTO_PROPOSE, true);
        store.setDefault(XPathCommonUIPreferenceNames.AUTO_PROPOSE_CODE,
                "$,/,@");//$NON-NLS-1$

        // JavaScript Style Preferences
        String NOBACKGROUNDBOLD = " | null | false"; //$NON-NLS-1$
        String styleValue = "null" + NOBACKGROUNDBOLD; //$NON-NLS-1$
        store.setDefault(IStyleConstantsXPath.DEFAULT, styleValue);

        // keywords not "bold" at request of Usability
        styleValue = ColorHelper.getColorString(127, 0, 85) + NOBACKGROUNDBOLD;
        store.setDefault(IStyleConstantsXPath.KEYWORD, styleValue);

        styleValue = ColorHelper.getColorString(142, 0, 255) + NOBACKGROUNDBOLD;
        store.setDefault(IStyleConstantsXPath.LITERAL, styleValue);

        styleValue = ColorHelper.getColorString(63, 95, 191) + NOBACKGROUNDBOLD;
        store.setDefault(IStyleConstantsXPath.COMMENT, styleValue);

        styleValue = ColorHelper.getColorString(63, 95, 191) + " | null | true"; //$NON-NLS-1$
        store.setDefault(IStyleConstantsXPath.UNFINISHED_COMMENT, styleValue);

        store.setDefault(XPathCommonUIPreferenceNames.INDENTATION_CHAR,
                XPathCommonUIPreferenceNames.TAB);
        store.setDefault(XPathCommonUIPreferenceNames.INDENTATION_SIZE, 1);
    }

}
