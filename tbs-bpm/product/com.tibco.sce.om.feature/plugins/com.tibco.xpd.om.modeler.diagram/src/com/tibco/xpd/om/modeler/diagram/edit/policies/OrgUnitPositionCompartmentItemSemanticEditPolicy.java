package com.tibco.xpd.om.modeler.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.modeler.diagram.edit.commands.PositionCreateCommand;
import com.tibco.xpd.om.modeler.diagram.providers.OrganizationModelElementTypes;

/**
 * @generated
 */
public class OrgUnitPositionCompartmentItemSemanticEditPolicy extends
        OrganizationModelBaseItemSemanticEditPolicy {

    /**
     * @generated
     */
    protected Command getCreateCommand(CreateElementRequest req) {
        if (OrganizationModelElementTypes.Position_2002 == req.getElementType()) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(OMPackage.eINSTANCE
                        .getOrgUnit_Positions());
            }
            return getGEFWrapper(new PositionCreateCommand(req));
        }
        return super.getCreateCommand(req);
    }

}
