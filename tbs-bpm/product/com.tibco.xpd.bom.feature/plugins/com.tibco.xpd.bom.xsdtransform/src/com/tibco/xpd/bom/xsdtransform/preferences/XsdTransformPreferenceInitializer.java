/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.bom.xsdtransform.Activator;

/**
 * 
 * 
 * @author rgreen
 * @since 22 Jun 2012
 */
public class XsdTransformPreferenceInitializer extends
        AbstractPreferenceInitializer {

    /**
     * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
     * 
     */
    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();

        store.setDefault(XsdTransformPreferenceConstants.DONT_SHOW_XSD_ANNOTATION_REMOVAL_WARNING,
                true);

    }

}
