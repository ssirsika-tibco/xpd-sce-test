package com.tibco.xpd.om.modeler.diagram.preferences;

import org.eclipse.gmf.runtime.diagram.ui.preferences.ConnectionsPreferencePage;

import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorPlugin;

/**
 * @generated
 */
public class DiagramConnectionsPreferencePage extends ConnectionsPreferencePage {

    /**
     * @generated
     */
    public DiagramConnectionsPreferencePage() {
        setPreferenceStore(OrganizationModelDiagramEditorPlugin.getInstance()
                .getPreferenceStore());
    }
}
