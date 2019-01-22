package com.tibco.xpd.ui.projectexplorer.util;

import org.eclipse.ui.PlatformUI;

/**
 * Util class to read and set the special folder presentation value (to
 * workspace preference)
 * 
 * @author njpatel
 * 
 */
public class SpecialFolderPresentationUtil {

    /*
     * Preference key for the special folder presentation setting
     */
    private static final String PREF_SPECIALFOLDER_PRESENTATION = "com.tibco.xpd.resources.menu.specialfolderpresentation"; //$NON-NLS-1$

    /**
     * Enum for special folder presentation selection. Values are:
     * <li>PROJECTLEVEL - Display special folders at project level,</li>
     * <li>NORMAL - Display special folders under their respective parent
     * containers.</li>
     * 
     * @author njpatel
     * 
     */
    public enum PresentationType {
        PROJECTLEVEL(1), NORMAL(2);

        private int val;

        PresentationType(int val) {
            this.val = val;
        }

        public int getValue() {
            return val;
        }

        public static PresentationType getType(int val) {
            switch (val) {
            case 1:
                return PROJECTLEVEL;
            case 2:
                return NORMAL;
            }

            return null;
        }
    }

    /**
     * Get the special folder presentation value set in the workspace
     * preference. If no preference is set then it will default to project level
     * (the preference will also be updated accordingly).
     * 
     * @return <code>PresentationType</code> value of the current setting.
     */
    public static PresentationType getPreferenceValue() {
        int value = PlatformUI.getPreferenceStore().getInt(
                PREF_SPECIALFOLDER_PRESENTATION);

        if (value > 0) {
            return PresentationType.getType(value);
        }

        // If no value was set then default to project level
        setPreferenceValue(PresentationType.PROJECTLEVEL);
        return PresentationType.PROJECTLEVEL;
    }

    /**
     * Set the special folder presentation value in the workspace preference.
     * 
     * @param type
     */
    public static void setPreferenceValue(PresentationType type) {
        if (type != null) {
            PlatformUI.getPreferenceStore().setValue(
                    PREF_SPECIALFOLDER_PRESENTATION, type.getValue());
        }
    }
}
