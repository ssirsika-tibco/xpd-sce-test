/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.gen.ui.preferences;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;

import com.tibco.xpd.bom.gen.ui.Activator;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;

/**
 * @author rassisi
 * 
 */
public class BomGenPropertyStore extends PreferenceStore {

    private final IResource resource;

    private final IPreferenceStore workbenchStore;

    public static final String USEPROJECTSETTINGS = "useProjectSettings"; //$NON-NLS-1$

    public BomGenPropertyStore(IResource resource,
            IPreferenceStore workbenchStore) {
        this.resource = resource;
        this.workbenchStore = workbenchStore;
    }

    @Override
    public String getDefaultString(String name) {
        return workbenchStore.getDefaultString(name);
    }

    /**
     * @param name
     * @return
     */
    public String getPreferenceString(String name) {
        String result = workbenchStore.getString(name);
        if (result == null) {
            result = ""; //$NON-NLS-1$
        }
        return result;
    }

    /**
     * @param name
     * @return
     */
    public boolean getPreferenceBoolean(String name) {
        return workbenchStore.getBoolean(name);
    }

    @Override
    public String getString(String name) {
        insertValue(name);
        return super.getString(name);
    }

    private boolean inserting = false;

    /**
     * @param name
     */
    private synchronized void insertValue(String name) {
        if (inserting)
            return;
        if (super.contains(name))
            return;
        inserting = true;
        String prop = null;
        try {
            prop =
                    resource.getProject()
                            .getPersistentProperty(new QualifiedName(
                                    BOMResourcesPlugin.BOS_PREFERENCES_ID, name));
        } catch (CoreException e) {
            Activator.getDefault().getLogger().error(e);
        }
        if (prop == null && workbenchStore != null)
            prop = workbenchStore.getString(name);
        if (prop != null)
            setValue(name, prop);
        inserting = false;
    }

    /**
     * @param name
     * @return
     */
    public String getProjectProperty(String name) {
        String result;
        insertValue(name);
        try {
            result =
                    resource.getProject()
                            .getPersistentProperty(new QualifiedName(
                                    BOMResourcesPlugin.BOS_PREFERENCES_ID, name));
        } catch (CoreException e) {
            result = ""; //$NON-NLS-1$
        }
        if (result == null) {
            result = ""; //$NON-NLS-1$
        }
        return result;
    }

    /**
     * @return
     */
    public boolean isUseProjectSettings() {
        String use = getProjectProperty(USEPROJECTSETTINGS);
        return Boolean.TRUE.toString().equals(use);
    }

    /**
     * @param isUseProjectSettings
     */
    public void setUseProjectSettings(boolean isUseProjectSettings) {
        setProjectProperty(USEPROJECTSETTINGS,
                Boolean.valueOf(isUseProjectSettings).toString());
    }

    @Override
    public boolean contains(String name) {
        return workbenchStore.contains(name);
    }

    @Override
    public void setToDefault(String name) {
        setValue(name, getDefaultString(name));
    }

    @Override
    public boolean isDefault(String name) {
        String defaultValue = getDefaultString(name);
        if (defaultValue == null)
            return false;
        return defaultValue.equals(getString(name));
    }

    @Override
    public void save() throws IOException {
        writeProperties();
    }

    @Override
    public void save(OutputStream out, String header) throws IOException {
        writeProperties();
    }

    /**
     * @throws IOException
     */
    private void writeProperties() throws IOException {
        // String[] preferences = super.preferenceNames();
        // for (int i = 0; i < preferences.length; i++) {
        // String name = preferences[i];
        // try {
        // setProjectProperty(name, getString(name));
        // } catch (CoreException e) {
        // throw new IOException("Cannot write resource property " + name);
        // }
        // }
    }

    /**
     * @param name
     * @param value
     * @throws CoreException
     */
    public void setProjectProperty(String name, String value) {
        try {
            resource.getProject().setPersistentProperty(new QualifiedName(
                    BOMResourcesPlugin.BOS_PREFERENCES_ID, name),
                    value);
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
