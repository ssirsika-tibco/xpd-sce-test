package com.tibco.xpd.bom.modeler.custom.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.ui.services.marker.AbstractMarkerNavigationProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Generalization;

import com.tibco.xpd.bom.modeler.custom.Activator;
import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditor;
import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditorUtil;

public class MarkerNavigationProvider extends AbstractMarkerNavigationProvider {

    @Override
    protected void doGotoMarker(IMarker marker) {
        IEditorPart editor = getEditor();
        if (editor instanceof UMLDiagramEditor) {
            Object id;
            try {
                id = marker.getAttribute(IMarker.LOCATION);
                if (id instanceof String) {
                    Diagram diagram = ((UMLDiagramEditor) editor).getDiagram();
                    EObject eo = diagram.eResource().getEObject((String) id);
                    if (eo != null) {
                        /*
                         * If this is a generalization and the general is in
                         * another model then the generalization will not be
                         * drawn in the editor, so need to select the owner
                         */
                        if (eo instanceof Generalization) {
                            Generalization gen = (Generalization) eo;
                            Element owner = gen.getOwner();
                            Classifier general = gen.getGeneral();

                            if (owner != null && general != null
                                    && owner.getModel() != general.getModel()) {
                                // Select the owner class
                                eo = owner;
                            }
                        }

                        UMLDiagramEditorUtil.openDiagram(eo);
                    }
                }
            } catch (CoreException e) {
                // ignore
                Activator.getDefault().getLogger().error(e);
            }
        }
    }
}
