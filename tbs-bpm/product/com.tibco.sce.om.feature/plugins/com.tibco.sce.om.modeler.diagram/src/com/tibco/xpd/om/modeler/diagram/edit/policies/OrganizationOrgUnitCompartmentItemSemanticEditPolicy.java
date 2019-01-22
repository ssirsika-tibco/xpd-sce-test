package com.tibco.xpd.om.modeler.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.modeler.diagram.edit.commands.DynamicOrgUnitCreateCommand;
import com.tibco.xpd.om.modeler.diagram.edit.commands.OrgUnitCreateCommand;
import com.tibco.xpd.om.modeler.diagram.providers.OrganizationModelElementTypes;

/**
 * @generated
 */
public class OrganizationOrgUnitCompartmentItemSemanticEditPolicy extends
		OrganizationModelBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (OrganizationModelElementTypes.OrgUnit_2001 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(OMPackage.eINSTANCE
						.getOrganization_Units());
			}
			return getGEFWrapper(new OrgUnitCreateCommand(req));
		}
		if (OrganizationModelElementTypes.DynamicOrgUnit_2003 == req
				.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(OMPackage.eINSTANCE
						.getOrganization_Units());
			}
			return getGEFWrapper(new DynamicOrgUnitCreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

}
