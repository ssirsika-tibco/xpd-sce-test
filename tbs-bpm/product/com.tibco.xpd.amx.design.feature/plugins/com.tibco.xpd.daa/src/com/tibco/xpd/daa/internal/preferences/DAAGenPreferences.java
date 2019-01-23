/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.daa.internal.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.daa.DaaActivator;

/**
 * Preferences utility for DAA generation.
 * 
 * @author Jan Arciuchiewicz
 * @since 19 Sep 2012
 */
public class DAAGenPreferences extends AbstractPreferenceInitializer {

    /**
     * System property name to switch on/off cleaning .bpm folder after DAA
     * generation. Cleaning the folder is switched off by default.
     */
    public static final String CLEANDAASTAGING_SYSTEM_PROPERTY =
            "bpm.cleandaastaging"; //$NON-NLS-1$

    /**
     * System property name to switch on/off jar caching. Jar caching is
     * switched on by default. Use <code>-Dbpm.cachebomjar=false</code> starting
     * option to switch off jar caching.
     */
    public static final String CACHEBOMJARS_SYSTEM_PROPERTY =
            "bpm.cachebomjars"; //$NON-NLS-1$

    /**
     * Preference key for cleaning DAA staging folder.
     */
    public static final String CLEAN_DAA_STAGING_FOLDER =
            "cleanDAAStagingFolder"; //$NON-NLS-1$

    /**
     * System property name to switch on/off Target Platform options.
     */
    public static final String CONFIRM_TARGET_PLATFORM =
            "confirm.target.platform"; //$NON-NLS-1$

    /**
     * Preference key for caching BOM jars.
     */
    public static final String CACHE_BOM_JARS = "cacheBomJars"; //$NON-NLS-1$

    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = DaaActivator.getDefault().getPreferenceStore();
        store.setDefault(CLEAN_DAA_STAGING_FOLDER, true);
        store.setDefault(CACHE_BOM_JARS, true);
        // XPD-4935: The checkbox 'Do Not Ask Again' should by default be
        // unchecked.
        store.setDefault(CONFIRM_TARGET_PLATFORM, false);
    }

    /**
     * Returns value of CLEAN_DAA_STAGING_FOLDER preference.
     */
    public static boolean cacheBomJars() {
        IPreferenceStore store = DaaActivator.getDefault().getPreferenceStore();
        return store.getBoolean(CACHE_BOM_JARS);
    }

    /**
     * Sets value of CLEAN_DAA_STAGING_FOLDER preference.
     */
    public static void setCacheBomJars(boolean value) {
        IPreferenceStore store = DaaActivator.getDefault().getPreferenceStore();
        store.setValue(CACHE_BOM_JARS, value);
    }

    /**
     * Returns value of CLEAN_DAA_STAGING_FOLDER preference.
     */
    public static boolean cleanDAAStageFolder() {
        IPreferenceStore store = DaaActivator.getDefault().getPreferenceStore();
        return store.getBoolean(CLEAN_DAA_STAGING_FOLDER);
    }

    /**
     * Sets value of CLEAN_DAA_STAGING_FOLDER preference.
     */
    public static void setCleanDAAStageFolder(boolean value) {
        IPreferenceStore store = DaaActivator.getDefault().getPreferenceStore();
        store.setValue(CLEAN_DAA_STAGING_FOLDER, value);
    }

    /**
     * The system property (bpm.cleandaastaging) if set will be used (so the
     * default behaviour can be overridden). If property is not set then current
     * preference setting is taken.
     * 
     * @return true if staging folder should be cleared afere DAA generation.
     */
    public static boolean shouldCleanDAAStaging() {
        String cleanSystemPropertyString =
                System.getProperty(CLEANDAASTAGING_SYSTEM_PROPERTY);
        if (cleanSystemPropertyString != null) {
            return Boolean.parseBoolean(cleanSystemPropertyString);
        } else {
            return DAAGenPreferences.cleanDAAStageFolder();
        }
    }

    /**
     * Determines if bom jars should be cached during DAA generation. </p> The
     * system property (bpm.cachebomjars) if set will be used (so the default
     * behaviour can be overridden). If property is not set then current
     * preference setting is taken.
     * 
     * @return true if bom jars should be cached.
     */
    public static boolean shouldCacheBomJars() {
        String cacheBomJarsProperytString =
                System.getProperty(CACHEBOMJARS_SYSTEM_PROPERTY);
        if (cacheBomJarsProperytString != null) {
            return Boolean.parseBoolean(cacheBomJarsProperytString);
        } else {
            return DAAGenPreferences.cacheBomJars();
        }
    }

    /**
     * Returns value of CONFIRM_TARGET_PLATFORM preference.
     */
    public static boolean confirmTargetPlatform() {
        IPreferenceStore store = DaaActivator.getDefault().getPreferenceStore();
        return store.getBoolean(CONFIRM_TARGET_PLATFORM);
    }

    /**
     * Sets value of CONFIRM_TARGET_PLATFORM preference.
     */
    public static void setConfirmTargetPlatform(boolean value) {
        IPreferenceStore store = DaaActivator.getDefault().getPreferenceStore();
        store.setValue(CONFIRM_TARGET_PLATFORM, value);
    }

    public static IPreferenceStore getPreferenceStore() {
        return DaaActivator.getDefault().getPreferenceStore();
    }
}
