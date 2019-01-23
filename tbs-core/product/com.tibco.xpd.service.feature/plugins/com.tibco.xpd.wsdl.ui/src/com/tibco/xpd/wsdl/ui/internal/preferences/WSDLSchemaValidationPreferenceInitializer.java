/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui.internal.preferences;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;

/**
 * Preference initializer for the WSDL validation preference.
 * 
 * @author njpatel
 * 
 */
public class WSDLSchemaValidationPreferenceInitializer extends
        AbstractPreferenceInitializer {

    public WSDLSchemaValidationPreferenceInitializer() {
    }

    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = WsdlUIPlugin.getDefault().getPreferenceStore();
        try {
            store
                    .setDefault(WsdlUIPlugin.WSDL_VALIDATION_NAMESPACE_IGNORE_PREF,
                            URLEncoder.encode("http://", "UTF-8")); //$NON-NLS-1$ //$NON-NLS-2$
        } catch (UnsupportedEncodingException e) {
            // Do nothing
        }
    }
}
