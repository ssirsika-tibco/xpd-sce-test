/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.core.validate.system.internal;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.core.validate.system.Activator;

/**
 * Preferences utility for System Validation.
 * 
 * For example the following preferences (with customized values) can be set
 * (for example in the product's plugin_customization.ini): <br/>
 * com.tibco.xpd.core.validate.system/DEFAULT_TARGET_PLATFORM_PATH=components/
 * shared/1.0.0 <br>
 * com.tibco.xpd.core.validate.system/VALIDATE_DEFAULT_TARGET_PLATFORM_PATH=true
 * 
 * @author Jan Arciuchiewicz
 */
public class SystemValidationPreferences extends AbstractPreferenceInitializer {

    /**
     * Preference key for a default target platform path. The path root is based
     * on installation home ($tibco.home) and should not be prepended with '/'.
     * It is assumed that the executable of studio is installed 3 path segments
     * from the root (for example
     * ${tibco.home}/studio/3.6/eclipse/TIBCOBusinessStudio.exe and the path
     * might need to be amended accordingly if its not the case.
     * <p>
     * EXAMPLE VALUE: "components/shared/1.0.0"
     * </p>
     */
    public static final String DEFAULT_TARGET_PLATFORM_PATH =
            "DEFAULT_TARGET_PLATFORM_PATH".toString(); //$NON-NLS-1$

    /**
     * Preference key for switching on/off validation of the default target
     * location. The value is boolean.
     */
    public static final String VALIDATE_DEFAULT_TARGET_PLATFORM_PATH =
            "VALIDATE_DEFAULT_TARGET_PLATFORM_PATH".toString(); //$NON-NLS-1$

    @Override
    public void initializeDefaultPreferences() {
        getPreferenceStore().setDefault(DEFAULT_TARGET_PLATFORM_PATH,
                "components/shared/1.0.0"); //$NON-NLS-1$
        getPreferenceStore().setDefault(VALIDATE_DEFAULT_TARGET_PLATFORM_PATH,
                true);
    }

    /**
     * Returns value of DEFAULT_TARGET_PLATFORM_PATH preference.
     */
    public static String getDefaultTargetPlatformPath() {
        return getPreferenceStore().getString(DEFAULT_TARGET_PLATFORM_PATH);
    }

    /**
     * Returns value of VALIDATE_DEFAULT_TARGET_PLATFORM_PATH preference.
     */
    public static boolean validateDefaultTargetPlatformPath() {
        return getPreferenceStore()
                .getBoolean(VALIDATE_DEFAULT_TARGET_PLATFORM_PATH);
    }

    /**
     * @return a preference store for this plugin.
     */
    public static IPreferenceStore getPreferenceStore() {
        return Activator.getDefault().getPreferenceStore();
    }
}
