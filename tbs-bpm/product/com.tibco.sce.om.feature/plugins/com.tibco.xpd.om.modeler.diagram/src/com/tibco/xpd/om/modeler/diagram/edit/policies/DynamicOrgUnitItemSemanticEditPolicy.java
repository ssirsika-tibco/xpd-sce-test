package com.tibco.xpd.om.modeler.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;

import com.tibco.xpd.om.modeler.diagram.edit.commands.DynamicOrgReferenceCreateCommand;
import com.tibco.xpd.om.modeler.diagram.edit.commands.DynamicOrgReferenceReorientCommand;
import com.tibco.xpd.om.modeler.diagram.edit.commands.OrgUnitRelationshipCreateCommand;
import com.tibco.xpd.om.modeler.diagram.edit.commands.OrgUnitRelationshipReorientCommand;
import com.tibco.xpd.om.modeler.diagram.edit.parts.DynamicOrgReferenceEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgUnitRelationshipEditPart;
import com.tibco.xpd.om.modeler.diagram.providers.OrganizationModelElementTypes;

/**
 * @generated
 */
public class DynamicOrgUnitItemSemanticEditPolicy extends
		OrganizationModelBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		CompoundCommand cc = getDestroyEdgesCommand();
		addDestroyShortcutsCommand(cc);
		cc.add(getGEFWrapper(new DestroyElementCommand(req)));
		return cc.unwrap();
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
		if (OrganizationModelElementTypes.DynamicOrgReference_3002 == req
				.getElementType()) {
			return getGEFWrapper(new DynamicOrgReferenceCreateCommand(req, req
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
		if (OrganizationModelElementTypes.DynamicOrgReference_3002 == req
				.getElementType()) {
			return null;
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
		case DynamicOrgReferenceEditPart.VISUAL_ID:
			return getGEFWrapper(new DynamicOrgReferenceReorientCommand(req));
		}
		return super.getReorientRelationshipCommand(req);
	}

}
