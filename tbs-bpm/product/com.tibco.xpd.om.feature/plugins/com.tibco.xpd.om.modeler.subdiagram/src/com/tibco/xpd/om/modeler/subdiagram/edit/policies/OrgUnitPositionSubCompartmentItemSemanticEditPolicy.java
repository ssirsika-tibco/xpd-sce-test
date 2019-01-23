package com.tibco.xpd.om.modeler.subdiagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.modeler.subdiagram.edit.commands.PositionSubCreateCommand;
import com.tibco.xpd.om.modeler.subdiagram.providers.OrganizationModelElementTypes;

/**
 * @generated
 */
public class OrgUnitPositionSubCompartmentItemSemanticEditPolicy extends
		OrganizationModelBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (OrganizationModelElementTypes.Position_2001 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(OMPackage.eINSTANCE
						.getOrgUnit_Positions());
			}
			return getGEFWrapper(new PositionSubCreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

}
