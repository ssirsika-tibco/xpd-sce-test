package com.tibco.xpd.om.modeler.diagram.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorPlugin;

/**
 * @generated
 */
public class DiagramPreferenceInitializer extends AbstractPreferenceInitializer {

    /**
     * @generated NOT
     */
    public void initializeDefaultPreferences() {
        IPreferenceStore store = getPreferenceStore();
        DiagramPrintingPreferencePage.initDefaults(store);
        DiagramGeneralPreferencePage.initDefaults(store);
        DiagramAppearancePreferencePage.initDefaults(store);
        DiagramConnectionsPreferencePage.initDefaults(store);
        DiagramRulersAndGridPreferencePage.initDefaults(store);

        // Set the initial grid defaults
        store.setDefault(IPreferenceConstants.PREF_SHOW_GRID, false);
        store.setDefault(IPreferenceConstants.PREF_SNAP_TO_GRID, true);
        store.setDefault(IPreferenceConstants.PREF_SNAP_TO_GEOMETRY, true);
        store.setDefault(IPreferenceConstants.PREF_GRID_SPACING, 1.0);

    }

    /**
     * @generated
     */
    protected IPreferenceStore getPreferenceStore() {
        return OrganizationModelDiagramEditorPlugin.getInstance()
                .getPreferenceStore();
    }
}
