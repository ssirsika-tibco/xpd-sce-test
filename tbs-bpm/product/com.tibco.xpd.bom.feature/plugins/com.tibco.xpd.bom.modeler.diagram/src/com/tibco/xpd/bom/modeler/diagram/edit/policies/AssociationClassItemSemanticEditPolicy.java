/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.policies;

import java.util.Iterator;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.bom.modeler.diagram.edit.commands.AssociationCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.edit.commands.AssociationEndCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.edit.commands.AssociationEndReorientCommand;
import com.tibco.xpd.bom.modeler.diagram.edit.commands.AssociationReorientCommand;
import com.tibco.xpd.bom.modeler.diagram.edit.commands.GeneralizationCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.edit.commands.GeneralizationReorientCommand;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassClassifierAttributesCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassClassifierOperationsCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassOperationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassPropertyEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationEndEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.GeneralizationEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;

/**
 * @generated
 */
public class AssociationClassItemSemanticEditPolicy extends
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
            case AssociationClassClassifierAttributesCompartmentEditPart.VISUAL_ID:
                for (Iterator cit = node.getChildren().iterator(); cit
                        .hasNext();) {
                    Node cnode = (Node) cit.next();
                    switch (UMLVisualIDRegistry.getVisualID(cnode)) {
                    case AssociationClassPropertyEditPart.VISUAL_ID:
                        cmd.add(getDestroyElementCommand(cnode));
                        break;
                    }
                }
                break;
            case AssociationClassClassifierOperationsCompartmentEditPart.VISUAL_ID:
                for (Iterator cit = node.getChildren().iterator(); cit
                        .hasNext();) {
                    Node cnode = (Node) cit.next();
                    switch (UMLVisualIDRegistry.getVisualID(cnode)) {
                    case AssociationClassOperationEditPart.VISUAL_ID:
                        cmd.add(getDestroyElementCommand(cnode));
                        break;
                    }
                }
                break;
            }
        }
    }

    /**
     * @generated
     */
    protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
        Command command = req.getTarget() == null ? getStartCreateRelationshipCommand(req)
                : getCompleteCreateRelationshipCommand(req);
        return command != null ? command : super
                .getCreateRelationshipCommand(req);
    }

    /**
     * @generated
     */
    protected Command getStartCreateRelationshipCommand(
            CreateRelationshipRequest req) {
        if (UMLElementTypes.Generalization_3001 == req.getElementType()) {
            return getGEFWrapper(new GeneralizationCreateCommand(req, req
                    .getSource(), req.getTarget()));
        }
        if (UMLElementTypes.Association_3002 == req.getElementType()) {
            return getGEFWrapper(new AssociationCreateCommand(req, req
                    .getSource(), req.getTarget()));
        }
        if (UMLElementTypes.Property_3003 == req.getElementType()) {
            return getGEFWrapper(new AssociationEndCreateCommand(req, req
                    .getSource(), req.getTarget()));
        }
        return null;
    }

    /**
     * @generated
     */
    protected Command getCompleteCreateRelationshipCommand(
            CreateRelationshipRequest req) {
        if (UMLElementTypes.Generalization_3001 == req.getElementType()) {
            return getGEFWrapper(new GeneralizationCreateCommand(req, req
                    .getSource(), req.getTarget()));
        }
        if (UMLElementTypes.Association_3002 == req.getElementType()) {
            return getGEFWrapper(new AssociationCreateCommand(req, req
                    .getSource(), req.getTarget()));
        }
        if (UMLElementTypes.Property_3003 == req.getElementType()) {
            return getGEFWrapper(new AssociationEndCreateCommand(req, req
                    .getSource(), req.getTarget()));
        }
        return null;
    }

    /**
     * Returns command to reorient EClass based link. New link target or source
     * should be the domain model element associated with this node.
     * 
     * @generated
     */
    protected Command getReorientRelationshipCommand(
            ReorientRelationshipRequest req) {
        switch (getVisualID(req)) {
        case GeneralizationEditPart.VISUAL_ID:
            return getGEFWrapper(new GeneralizationReorientCommand(req));
        case AssociationEditPart.VISUAL_ID:
            return getGEFWrapper(new AssociationReorientCommand(req));
        case AssociationEndEditPart.VISUAL_ID:
            return getGEFWrapper(new AssociationEndReorientCommand(req));
        }
        return super.getReorientRelationshipCommand(req);
    }

}
