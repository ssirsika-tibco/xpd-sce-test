package com.tibco.xpd.om.modeler.diagram.edit.policies;

import java.util.Iterator;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.modeler.diagram.edit.commands.OrgUnitCreateCommand;
import com.tibco.xpd.om.modeler.diagram.edit.commands.OrgUnitRelationshipCreateCommand;
import com.tibco.xpd.om.modeler.diagram.edit.commands.OrgUnitRelationshipReorientCommand;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitPositionCompartmentEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitRelationshipEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.PositionEditPart;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;
import com.tibco.xpd.om.modeler.diagram.providers.OrganizationModelElementTypes;

/**
 * @generated
 */
public class OrgUnitItemSemanticEditPolicy extends
        OrganizationModelBaseItemSemanticEditPolicy {

    @Override
    protected Command getCreateCommand(CreateElementRequest req) {
        // TODO Auto-generated method stub
        if (OrganizationModelElementTypes.OrgUnit_2001 == req.getElementType()) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(OMPackage.eINSTANCE
                        .getOrganization_Units());
            }
            return getGEFWrapper(new OrgUnitCreateCommand(req));
        }

        return super.getCreateCommand(req);
    }

    /**
     * @generated
     */
    protected Command getDestroyElementCommand(DestroyElementRequest req) {
        CompoundCommand cc = getDestroyEdgesCommand();
        addDestroyChildNodesCommand(cc);
        addDestroyShortcutsCommand(cc);
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
            switch (OrganizationModelVisualIDRegistry.getVisualID(node)) {
            case OrgUnitPositionCompartmentEditPart.VISUAL_ID:
                for (Iterator cit = node.getChildren().iterator(); cit
                        .hasNext();) {
                    Node cnode = (Node) cit.next();
                    switch (OrganizationModelVisualIDRegistry
                            .getVisualID(cnode)) {
                    case PositionEditPart.VISUAL_ID:
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
        if (OrganizationModelElementTypes.OrgUnitRelationship_3001 == req
                .getElementType()) {
            return getGEFWrapper(new OrgUnitRelationshipCreateCommand(req, req
                    .getSource(), req.getTarget()));
        }
        return null;
    }

    /**
     * @generated
     */
    protected Command getCompleteCreateRelationshipCommand(
            CreateRelationshipRequest req) {
        if (OrganizationModelElementTypes.OrgUnitRelationship_3001 == req
                .getElementType()) {
            return getGEFWrapper(new OrgUnitRelationshipCreateCommand(req, req
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
        case OrgUnitRelationshipEditPart.VISUAL_ID:
            return getGEFWrapper(new OrgUnitRelationshipReorientCommand(req));
        }
        return super.getReorientRelationshipCommand(req);
    }

}
