/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.validator.internal.validation;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;

/**
 * 
 * 
 * @author bharge
 * @since 26 Mar 2013
 */
public class ValidationOptionsPreferenceInitializer extends
        AbstractPreferenceInitializer {

    /**
     * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
     * 
     */
    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore preferenceStore =
                BOMValidatorActivator.getDefault().getPreferenceStore();
        preferenceStore.setDefault(BOMValidationUtil.VALIDATE_XSD, true);
    }

}
