/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.part.custom.utils;

import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.AssociationClass;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassDanglingNodeEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassEditPart;

public class DiagramUtils {

    /**
     * 
     * An AssociationClass can have a mapping of three views to one semantic
     * element. The editparts corresponding to these views are:
     * 
     * - AssociationClassEditPart
     * 
     * - AssociationClassDanglingNodeEditPart
     * 
     * - AssociationClassConnectorEditPart
     * 
     * Use this method to retrieve the editpart that you require for an
     * AssociationClass EObject.
     * 
     * 
     * @param assocCl
     *            The AssociationClass EObject
     * @param visualID
     *            The VisualID of the editpart that needs to match the EObject.
     * @return EditPart or null
     */
    public static EditPart GetAssociationClassEditPart(
            AssociationClass assocCl, int visualID) {

        IEditorPart editorPart =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getActiveEditor();

        // DiagramEditor should be activated
        Assert.isTrue(editorPart instanceof DiagramEditor);
        DiagramEditor diagramEditor = (DiagramEditor) editorPart;

        // activated DiagramEditor must be the one that corresponds to
        // this view's diagram
        // Assert.isTrue(diagramEditor.getDiagram().equals(view.getDiagram()));

        // diagramEditor instanceof IDiagramWorkbenchPart
        IDiagramGraphicalViewer viewer =
                diagramEditor.getDiagramGraphicalViewer();
        Assert.isNotNull(viewer);

        // find the edit part
        Map epRegistry = viewer.getEditPartRegistry();

        for (Object key : epRegistry.keySet()) {
            if (key instanceof View && ((View) key).getElement() == assocCl) {
                Object value = epRegistry.get(key);

                if (value instanceof AssociationClassEditPart) {
                    AssociationClassEditPart ep1 =
                            (AssociationClassEditPart) value;

                    if (ep1.VISUAL_ID == visualID) {
                        return ep1;
                    }
                }

                if (value instanceof AssociationClassDanglingNodeEditPart) {
                    AssociationClassDanglingNodeEditPart ep2 =
                            (AssociationClassDanglingNodeEditPart) value;

                    if (ep2.VISUAL_ID == visualID) {
                        return ep2;
                    }
                }
            }
        }

        return null;
    }

    public static EditPart getEditPart(EObject eo, View view) {

        IEditorPart editorPart =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getActiveEditor();

        // DiagramEditor should be activated
        Assert.isTrue(editorPart instanceof DiagramEditor);
        DiagramEditor diagramEditor = (DiagramEditor) editorPart;

        // activated DiagramEditor must be the one that corresponds to
        // this view's diagram
        // Assert.isTrue(diagramEditor.getDiagram().equals(view.getDiagram()));

        // diagramEditor instanceof IDiagramWorkbenchPart
        IDiagramGraphicalViewer viewer =
                diagramEditor.getDiagramGraphicalViewer();
        Assert.isNotNull(viewer);

        // find the edit part
        Map epRegistry = viewer.getEditPartRegistry();

        for (Object key : epRegistry.keySet()) {
            if (key instanceof View && ((View) key) == view) {
                Object value = epRegistry.get(key);

                if (value instanceof EditPart) {
                    return (EditPart) value;
                }
            }
        }

        return null;
    }
}
