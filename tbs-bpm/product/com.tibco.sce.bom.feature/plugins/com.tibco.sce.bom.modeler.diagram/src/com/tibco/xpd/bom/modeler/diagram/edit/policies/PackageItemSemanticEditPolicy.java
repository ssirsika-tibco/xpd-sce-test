/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.policies;

import java.util.Iterator;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackagePackageContentsCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;

/**
 * @generated
 */
public class PackageItemSemanticEditPolicy extends
        UMLBaseItemSemanticEditPolicy {

    /**
     * @generated
     */
    protected Command getDestroyElementCommand(DestroyElementRequest req) {
        CompoundCommand cc = getDestroyEdgesCommand();
        addDestroyChildNodesCommand(cc);
        addDestroyShortcutsCommand(cc);
        View view = (View) getHost().getModel();
        if (view.getEAnnotation("Shortcut") != null) { //$NON-NLS-1$
            req.setElementToDestroy(view);
        }
        cc.add(getGEFWrapper(new DestroyElementCommand(req)));
        return cc.unwrap();
    }

    /**
     * @generated
     */
    protected void addDestroyChildNodesCommand(CompoundCommand cmd) {
        View view = (View) getHost().getModel();
        EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
        if (annotation != null) {
            return;
        }
        for (Iterator it = view.getChildren().iterator(); it.hasNext();) {
            Node node = (Node) it.next();
            switch (UMLVisualIDRegistry.getVisualID(node)) {
            case PackagePackageContentsCompartmentEditPart.VISUAL_ID:
                for (Iterator cit = node.getChildren().iterator(); cit
                        .hasNext();) {
                    Node cnode = (Node) cit.next();
                    switch (UMLVisualIDRegistry.getVisualID(cnode)) {
                    }
                }
                break;
            }
        }
    }

}
