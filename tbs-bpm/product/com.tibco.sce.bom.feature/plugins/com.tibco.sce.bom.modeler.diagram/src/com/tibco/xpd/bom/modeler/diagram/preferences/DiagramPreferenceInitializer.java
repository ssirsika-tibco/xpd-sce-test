/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;

/**
 * @generated
 */
public class DiagramPreferenceInitializer extends AbstractPreferenceInitializer {

    /**
     * @generated NOT
     */
    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = getPreferenceStore();
        DiagramPrintingPreferencePage.initDefaults(store);
        DiagramAppearancePreferencePage.initDefaults(store);
        DiagramConnectionsPreferencePage.initDefaults(store);
        DiagramRulersAndGridPreferencePage.initDefaults(store);
        DiagramGeneralPreferencePage.initDefaults(store);

        store.setDefault(IPreferenceConstants.PREF_SNAP_TO_GEOMETRY, true);

    }

    /**
     * @generated
     */
    protected IPreferenceStore getPreferenceStore() {
        return BOMDiagramEditorPlugin.getInstance().getPreferenceStore();
    }
}
