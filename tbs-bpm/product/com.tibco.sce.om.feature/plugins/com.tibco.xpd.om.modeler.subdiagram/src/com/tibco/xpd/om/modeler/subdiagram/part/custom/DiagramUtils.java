/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.modeler.subdiagram.part.custom;

import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class DiagramUtils {

    /**
     * 
     * Returns the corresponding EditPart for the passed in View.
     * 
     * @param View
     *            view
     * @return EditPart editPart
     */
    public static EditPart getEditPart(View view) {

        IEditorPart editorPart = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getActivePage().getActiveEditor();

        // DiagramEditor should be activated
        Assert.isTrue(editorPart instanceof DiagramEditor);
        DiagramEditor diagramEditor = (DiagramEditor) editorPart;

        // activated DiagramEditor must be the one that corresponds to
        // this view's diagram
        // Assert.isTrue(diagramEditor.getDiagram().equals(view.getDiagram()));

        // diagramEditor instanceof IDiagramWorkbenchPart
        IDiagramGraphicalViewer viewer = diagramEditor
                .getDiagramGraphicalViewer();
        Assert.isNotNull(viewer);

        // find the edit part
        Map epRegistry = viewer.getEditPartRegistry();

        EditPart ep = (EditPart) epRegistry.get(view);

        return ep;

    }

    /**
     * 
     * Returns true if the hierarchy of OrgUnit nodes below the passed in view
     * is collapsed.
     * 
     * To determine if the hierarchy is collapsed each Edge originating from the
     * passed in Node is examined to see if its visibility is set to true or
     * false. If an edge is found that is set to invisble then we are going to
     * assume that the hierarchy is collapsed. Bear in mind that it is possible
     * that an edge may belong to the node that isn't set to invisible for
     * example if a
     * 
     * 
     * @param Node
     *            orgUnitNode
     * @return boolean true if hierarchy of nodes is collapsed
     */
    public static boolean isOrgUnitHierarchyCollapsed(Node orgUnitNode) {

        boolean isCollapsed = false;

        // Examine all the source edges to see if they are set to visible.
        // If visible the hierarchy is expanded
        EList sourceEdges = orgUnitNode.getSourceEdges();

        for (Object object : sourceEdges) {
            Edge edge = (Edge) object;
            if (!edge.isVisible()) {
                isCollapsed = true;
                break;
            }
        }

        return isCollapsed;

    }

}
