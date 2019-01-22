/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.preferences.util;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.validation.ValidationActivator;

/**
 * Utility class to allow the getting and setting of the validation issue
 * preference value. Use:
 * <ul>
 * <li><code>{@link #getPreferenceValue(String)}</code> to get the preference
 * value of the given issue (id),</li>
 * <li><code>{@link #getDefaultPreferenceValue(String)}</code> to get the
 * default preference value of the given issue (id),
 * <li><code>{@link #setPreferenceValue(String, int)}</code> to set the
 * preference value of the given issue (id).</li>
 * <li><code>{@link #setPreferenceToDefault(String)}</code> to set the
 * preference value back to it's default.
 * </ul>
 * <p>
 * If there is need to use the preference store directly then this can be
 * accessed using <code>{@link #getPreferenceStore()}</code>. In this case
 * method <code>{@link #getPreferenceKey(String)}</code> should be used to
 * create the right key to access the preference value of the issue.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class ValidationPreferenceUtil {

    /**
     * Prefix of validation preferences added to the preference store
     */
    private static final String PREF_PREFIX = "validationIssue."; //$NON-NLS-1$

    /**
     * Get the preference stored value of the given issue. The return value
     * corresponds to the <code>{@link IMarker}</code> severity vaue.
     * 
     * @param issueId
     *            issue id of the preference to be acquired from the preference
     *            store.
     * @return int value corresponding the <code>{@link IMarker}</code> severity
     *         value.
     */
    public static int getPreferenceValue(String issueId) {
        return getPreferenceStore().getInt(getPreferenceKey(issueId));
    }

    /**
     * Get the default preference value of the given issue.
     * 
     * @param issueId
     *            id of the issue
     * @return the default value of the issue severity. This value corresponds
     *         to the <code>{@link IMarker}</code> severity value.
     */
    public static int getDefaultPreferenceValue(String issueId) {
        return getPreferenceStore().getDefaultInt(getPreferenceKey(issueId));
    }

    /**
     * Set the issue severity value in the preference store.
     * 
     * @param issueId
     *            id of the issue to set
     * @param value
     *            {@link IMarker} severity value to set for the issue
     */
    public static void setPreferenceValue(String issueId, int value) {
        getPreferenceStore().setValue(getPreferenceKey(issueId), value);
    }

    /**
     * Reset the preference value of this issue to the default value.
     * 
     * @param issueId
     *            id of issue to reset to default
     */
    public static void setPreferenceToDefault(String issueId) {
        getPreferenceStore().setToDefault(getPreferenceKey(issueId));
    }

    /**
     * Get the validation preference store.
     * 
     * @return validation preference store
     */
    public static IPreferenceStore getPreferenceStore() {
        IPreferenceStore store =
                ValidationActivator.getDefault().getPreferenceStore();

        if (store != null) {
            return store;
        } else {
            throw new NullPointerException(String
                    .format("Preference store for plugin %s is null", //$NON-NLS-1$
                            ValidationActivator.PLUGIN_ID));
        }
    }

    /**
     * Get the preference key for the given issue.
     * 
     * @param issueId
     *            id of the issue
     * @return preference key to be used in the preference store to get the
     *         stored severity value of the given issue. Use
     *         <code>{@link #getPreferenceStore()}</code> for the preference
     *         store.
     */
    public static String getPreferenceKey(String issueId) {

        if (issueId != null) {
            return PREF_PREFIX + issueId;
        } else {
            throw new NullPointerException("Issue id is null."); //$NON-NLS-1$
        }
    }
}
