/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.validation.internal.ValidationManager;
import com.tibco.xpd.validation.internal.engine.ValidationEngine;
import com.tibco.xpd.validation.preferences.util.ValidationPreferenceUtil;
import com.tibco.xpd.validation.provider.IssueInfo;

/**
 * Class used to initialize default preference values for the validation issues
 * registered.
 * 
 * @author njpatel
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
     * initializeDefaultPreferences()
     */
    public void initializeDefaultPreferences() {
        IPreferenceStore store = ValidationPreferenceUtil.getPreferenceStore();

        /*
         * Get all registered issues and set the default values
         */
        ValidationEngine engine = ValidationManager.getInstance()
                .getValidationEngine();

        if (engine != null) {
            IssueInfo[] issues = engine.getAllIssues();

            if (issues != null) {
                for (IssueInfo issue : issues) {
                    store.setDefault(ValidationPreferenceUtil
                            .getPreferenceKey(issue.getId()), issue
                            .getSeverity());
                }
            }
        } else {
            throw new NullPointerException("Validation engine is null."); //$NON-NLS-1$
        }
    }
}
