package com.tibco.xpd.om.modeler.subdiagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.modeler.subdiagram.edit.commands.OrgUnitSubRelationshipCreateCommand;
import com.tibco.xpd.om.modeler.subdiagram.edit.commands.OrgUnitSubRelationshipReorientCommand;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.OrgUnitSubRelationshipEditPart;
import com.tibco.xpd.om.modeler.subdiagram.providers.OrganizationModelElementTypes;

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
			return getGEFWrapper(new OrgUnitSubRelationshipCreateCommand(req,
					req.getSource(), req.getTarget()));
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
			return getGEFWrapper(new OrgUnitSubRelationshipCreateCommand(req,
					req.getSource(), req.getTarget()));
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
		case OrgUnitSubRelationshipEditPart.VISUAL_ID:
			return getGEFWrapper(new OrgUnitSubRelationshipReorientCommand(req));
		}
		return super.getReorientRelationshipCommand(req);
	}

}
