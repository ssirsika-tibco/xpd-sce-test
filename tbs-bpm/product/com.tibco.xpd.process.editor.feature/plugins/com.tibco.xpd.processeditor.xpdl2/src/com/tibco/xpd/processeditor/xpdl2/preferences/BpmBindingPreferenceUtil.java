/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.general.WsSoapBindingSection;

/**
 * BpmServiceBindingPreferencePage's preference initializer class to load or set
 * the preferences
 * 
 * @author bharge
 * @since 22 Mar 2012
 */
public class BpmBindingPreferenceUtil extends AbstractPreferenceInitializer {

    public static final String IS_HTTP_BINDING = "isHttpBinding"; //$NON-NLS-1$

    public static final String IS_JMS_BINDING = "isJmsBinding"; //$NON-NLS-1$

    public static final String SOAP_VERSION_DEFAULT = "soapVersionDefault"; //$NON-NLS-1$

    /**
     * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
     * 
     */
    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore preferenceStore =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();
        preferenceStore.setDefault(IS_HTTP_BINDING, true);
        preferenceStore.setDefault(IS_JMS_BINDING, false);
        preferenceStore.setDefault(SOAP_VERSION_DEFAULT,
                WsSoapBindingSection.SoapVersion.getDefault().getName());

    }

    public static boolean isHttpBinding() {
        IPreferenceStore preferenceStore =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();
        return preferenceStore.getBoolean(IS_HTTP_BINDING);
    }

    public static void setHttpBinding(boolean value) {
        IPreferenceStore preferenceStore =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();
        preferenceStore.setValue(IS_HTTP_BINDING, value);
    }

    public static boolean isJmsBinding() {
        IPreferenceStore preferenceStore =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();
        return preferenceStore.getBoolean(IS_JMS_BINDING);
    }

    public static void setJmsBinding(boolean value) {
        IPreferenceStore preferenceStore =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();
        preferenceStore.setValue(IS_JMS_BINDING, value);
    }

    public static WsSoapBindingSection.SoapVersion getSoapVersionPreference() {
        IPreferenceStore preferenceStore =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();
        String versionStr = preferenceStore.getString(SOAP_VERSION_DEFAULT);
        WsSoapBindingSection.SoapVersion version =
                WsSoapBindingSection.SoapVersion.findByName(versionStr);
        return (version != null) ? version : WsSoapBindingSection.SoapVersion
                .getDefault();
    }

    public static WsSoapBindingSection.SoapVersion getDefaultSoapVersionPreference() {
        IPreferenceStore preferenceStore =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();
        String versionStr =
                preferenceStore.getDefaultString(SOAP_VERSION_DEFAULT);
        WsSoapBindingSection.SoapVersion version =
                WsSoapBindingSection.SoapVersion.findByName(versionStr);
        return (version != null) ? version : WsSoapBindingSection.SoapVersion
                .getDefault();
    }

    public static void setSoapVersionPreference(
            WsSoapBindingSection.SoapVersion version) {
        IPreferenceStore preferenceStore =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();
        preferenceStore.setValue(SOAP_VERSION_DEFAULT, version.getName());
    }

}
