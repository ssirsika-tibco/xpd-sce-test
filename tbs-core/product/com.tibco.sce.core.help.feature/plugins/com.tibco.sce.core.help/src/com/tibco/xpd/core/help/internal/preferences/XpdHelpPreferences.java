package com.tibco.xpd.core.help.internal.preferences;

import java.io.File;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.core.help.CoreHelpActivator;
import com.tibco.xpd.core.help.internal.Messages;

/**
 * Class used to initialize default preference values.
 */
public class XpdHelpPreferences extends AbstractPreferenceInitializer {

    /**
     * Default offline documentation root folder name. It will be under
     * '$user.home' folder.
     */
    public static final String TIBCO_HELP_FOLDER_NAME = "TIBCOHelp"; //$NON-NLS-1$

    /**
     * Preference key for storing help content viewing preference. The value is
     * of {@link OpenMode} literal.
     */
    public static final String P_SHOW_HELP_CONTENT = "showHelpContent"; //$NON-NLS-1$

    /**
     * Preference key for storing main product page viewing preference. The
     * value is of {@link OpenMode} literal.
     */
    public static final String P_SHOW_PRODUCT_PAGE = "showProductPage"; //$NON-NLS-1$

    /**
     * Preference key for storing base offline folder location.
     */
    public static final String P_BASE_OFFLINE_FOLDER = "baseOfflineFolder"; //$NON-NLS-1$

    /**
     * Preference key for documentaion access strategy. The value is of
     * {@link AccessStrategy} literal.
     */
    public static final String P_DOC_ACCESS_MODE = "docAccessMode"; //$NON-NLS-1$

    /**
     * Documentation opening mode.
     */
    public enum OpenMode {
        IN_HELP_VIEW(Messages.XpdHelpPreferences_openInAView_label), IN_BROWSER(
                Messages.XpdHelpPreferences_openInABrowser_label);
        private String label;

        private OpenMode(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        /**
         * Gets the enumeration for a corresponding label.
         * 
         * @param l
         *            a label (needs to be equal case sensitively to a label
         *            associated with enum)
         * @return the enumeration with the provided label.
         */
        public static OpenMode getFromLabel(String l) {
            for (OpenMode om : values()) {
                if (om.getLabel().equals(l)) {
                    return om;
                }
            }
            throw new IllegalArgumentException(
                    String.format("No enum with label %1$s", l)); //$NON-NLS-1$
        }

    };

    /**
     * Documentation access strategy.
     */
    public enum AccessStrategy {
        PREFER_OFFLINE(Messages.XpdHelpPreferences_preferOfflineStrategy_label), ALWAYS_ONLINE(
                Messages.XpdHelpPreferences_AlwaysOnlineStrategy_label);
        private String label;

        private AccessStrategy(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        /**
         * Gets the enumeration for a corresponding label.
         * 
         * @param l
         *            a label (needs to be equal case sensitively to a label
         *            associated with enum)
         * @return the enumeration with the provided label.
         */
        public static AccessStrategy getFromLabel(String l) {
            for (AccessStrategy as : values()) {
                if (as.getLabel().equals(l)) {
                    return as;
                }
            }
            throw new IllegalArgumentException(
                    String.format("No enum with label %1$s", l)); //$NON-NLS-1$
        }

        /**
         * @return 2 dimensioned array with label and enum string literal.
         */
        public static String[][] getAsArray() {
            AccessStrategy[] values = values();
            String[][] t = new String[values.length][2];
            int i = 0;
            for (AccessStrategy as : values) {
                t[i][0] = as.getLabel();
                t[i][1] = as.toString();
                i++;
            }
            return t;
        }
    };

    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store =
                CoreHelpActivator.getDefault().getPreferenceStore();
        store.setDefault(P_DOC_ACCESS_MODE,
                AccessStrategy.PREFER_OFFLINE.toString());
        /*
         * Sid XPD-8237. Change default to open in external browser (we are also
         * removing the choice from preference page as internal browser doesn't
         * work for latest help pages so strictly speaking these preferences are
         * not required and may never be. Can be removed after update to Eclipse
         * 4 if we decide not to change back to option to show in internal
         * browser.
         */
        store.setDefault(P_SHOW_HELP_CONTENT, OpenMode.IN_BROWSER.toString());

        store.setDefault(P_SHOW_PRODUCT_PAGE, OpenMode.IN_BROWSER.toString());
        store.setDefault(P_BASE_OFFLINE_FOLDER,
                new File(new File(System.getProperty("user.home")), //$NON-NLS-1$
                        TIBCO_HELP_FOLDER_NAME).toString());

    }

    /**
     * @return mode for opening product help content.
     */
    public static OpenMode getHelpContentOpenPreference() {
        IPreferenceStore store =
                CoreHelpActivator.getDefault().getPreferenceStore();
        try {
            return OpenMode.valueOf(store.getString(P_SHOW_HELP_CONTENT));
        } catch (IllegalArgumentException e) {
            CoreHelpActivator.getDefault().logError(e);
        }
        /*
         * Sid XPD-8237. Change default to open in external browser (we are also
         * removing the choice from preference page as internal browser doesn't
         * work for latest help pages so strictly speaking these preferences are
         * not required and may never be. Can be removed after update to Eclipse
         * 4 if we decide not to change back to option to show in internal
         * browser.
         */
        return OpenMode.IN_BROWSER; // default
    }

    /**
     * @return default mode for opening product help content.
     */
    public static OpenMode getDefaultHelpContentOpenPreference() {
        IPreferenceStore store =
                CoreHelpActivator.getDefault().getPreferenceStore();
        try {
            return OpenMode
                    .valueOf(store.getDefaultString(P_SHOW_HELP_CONTENT));
        } catch (IllegalArgumentException e) {
            CoreHelpActivator.getDefault().logError(e);
        }
        /*
         * Sid XPD-8237. Change default to open in external browser (we are also
         * removing the choice from preference page as internal browser doesn't
         * work for latest help pages so strictly speaking these preferences are
         * not required and may never be. Can be removed after update to Eclipse
         * 4 if we decide not to change back to option to show in internal
         * browser.
         */
        return OpenMode.IN_BROWSER; // default
    }

    /**
     * @return mode for opening product help page.
     */
    public static OpenMode getProductPageOpenPreference() {
        IPreferenceStore store =
                CoreHelpActivator.getDefault().getPreferenceStore();
        try {
            return OpenMode.valueOf(store.getString(P_SHOW_PRODUCT_PAGE));
        } catch (IllegalArgumentException e) {
            CoreHelpActivator.getDefault().logError(e);
        }
        return OpenMode.IN_BROWSER; // default
    }

    /**
     * @return default mode for opening product help page.
     */
    public static OpenMode getDefaultProductPageOpenPreference() {
        IPreferenceStore store =
                CoreHelpActivator.getDefault().getPreferenceStore();
        try {
            return OpenMode
                    .valueOf(store.getDefaultString(P_SHOW_PRODUCT_PAGE));
        } catch (IllegalArgumentException e) {
            CoreHelpActivator.getDefault().logError(e);
        }
        return OpenMode.IN_BROWSER; // default
    }

    /**
     * @return base offline folder preference.
     */
    public static File getBaseOfflineFolder() {
        IPreferenceStore store =
                CoreHelpActivator.getDefault().getPreferenceStore();
        String folder = store.getString(P_BASE_OFFLINE_FOLDER);
        if (folder != null && !folder.trim().isEmpty()) {
            return new File(folder);
        }
        return new File(store.getDefaultString(P_BASE_OFFLINE_FOLDER));
    }

    /**
     * @return access strategy preference.
     */
    public static AccessStrategy getAccessStrategy() {
        IPreferenceStore store =
                CoreHelpActivator.getDefault().getPreferenceStore();
        String asString = store.getString(P_DOC_ACCESS_MODE);
        if (asString == null || asString.trim().isEmpty()) {
            asString = store.getDefaultString(P_DOC_ACCESS_MODE);
        }
        try {
            return AccessStrategy.valueOf(asString);
        } catch (IllegalArgumentException e) {
            CoreHelpActivator.getDefault().logError(e);
        }
        return AccessStrategy.PREFER_OFFLINE; // default
    }
}
