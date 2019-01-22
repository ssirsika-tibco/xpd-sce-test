package com.tibco.xpd.om.modeler.custom.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.ui.services.marker.AbstractMarkerNavigationProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.ui.IEditorPart;

import com.tibco.xpd.om.modeler.custom.Activator;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditor;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelSubDiagramEditor;

public class MarkerNavigationProvider extends AbstractMarkerNavigationProvider {

    @Override
    protected void doGotoMarker(IMarker marker) {
        IEditorPart editor = getEditor();
        if (editor instanceof OrganizationModelDiagramEditor
                || editor instanceof OrganizationModelSubDiagramEditor) {
            Object id;
            try {
                id = marker.getAttribute(IMarker.LOCATION);
                if (id instanceof String) {

                    if (editor instanceof OrganizationModelDiagramEditor) {
                        Diagram diagram = ((OrganizationModelDiagramEditor) editor)
                                .getDiagram();
                        EObject eo = diagram.eResource()
                                .getEObject((String) id);
                        if (eo != null) {
                            com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorUtil
                                    .openDiagram(eo);
                        }

                    }

                }
            } catch (CoreException e) {
                // ignore
                Activator.getDefault().getLogger().error(e);
            }
        }
    }
}
