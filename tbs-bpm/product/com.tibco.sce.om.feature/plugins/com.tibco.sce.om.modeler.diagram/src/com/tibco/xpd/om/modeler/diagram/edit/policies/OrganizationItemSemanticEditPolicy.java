package com.tibco.xpd.om.modeler.diagram.edit.policies;

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

import com.tibco.xpd.om.modeler.diagram.edit.commands.DynamicOrgReferenceCreateCommand;
import com.tibco.xpd.om.modeler.diagram.edit.commands.DynamicOrgReferenceReorientCommand;
import com.tibco.xpd.om.modeler.diagram.edit.parts.DynamicOrgReferenceEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.DynamicOrgUnitEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationOrgUnitCompartmentEditPart;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;
import com.tibco.xpd.om.modeler.diagram.providers.OrganizationModelElementTypes;

/**
 * @generated
 */
public class OrganizationItemSemanticEditPolicy extends
		OrganizationModelBaseItemSemanticEditPolicy {

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
			switch (OrganizationModelVisualIDRegistry.getVisualID(node)) {
			case OrganizationOrgUnitCompartmentEditPart.VISUAL_ID:
				for (Iterator cit = node.getChildren().iterator(); cit
						.hasNext();) {
					Node cnode = (Node) cit.next();
					switch (OrganizationModelVisualIDRegistry
							.getVisualID(cnode)) {
					case OrgUnitEditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case DynamicOrgUnitEditPart.VISUAL_ID:
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
		if (OrganizationModelElementTypes.DynamicOrgReference_3002 == req
				.getElementType()) {
			return null;
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getCompleteCreateRelationshipCommand(
			CreateRelationshipRequest req) {
		if (OrganizationModelElementTypes.DynamicOrgReference_3002 == req
				.getElementType()) {
			return getGEFWrapper(new DynamicOrgReferenceCreateCommand(req, req
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
		case DynamicOrgReferenceEditPart.VISUAL_ID:
			return getGEFWrapper(new DynamicOrgReferenceReorientCommand(req));
		}
		return super.getReorientRelationshipCommand(req);
	}

}
