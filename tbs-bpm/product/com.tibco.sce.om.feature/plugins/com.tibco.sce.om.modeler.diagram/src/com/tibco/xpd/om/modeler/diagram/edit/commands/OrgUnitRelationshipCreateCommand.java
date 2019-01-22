package com.tibco.xpd.om.modeler.diagram.edit.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;

import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.modeler.subdiagram.edit.commands.OrgUnitSubRelationshipCreateCommand;

/**
 * 
 * @generated NOT
 * 
 * @author rgreen
 * 
 */
public class OrgUnitRelationshipCreateCommand extends
        OrgUnitSubRelationshipCreateCommand {

    public OrgUnitRelationshipCreateCommand(CreateRelationshipRequest request,
            EObject source, EObject target) {
        super(request, source, target);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.om.modeler.subdiagram.edit.commands.
     * OrgUnitSubRelationshipCreateCommand#canExecute()
     */
    public boolean canExecute() {
        // For the OrgModel diagram we are disallowing the creation of
        // relationships when both OrgUnits are in the same Organization.
        OrgUnit srcOrgUnit = getSource();
        OrgUnit trgOrgUnit = getTarget();

        if (srcOrgUnit != null && trgOrgUnit != null) {
            if (srcOrgUnit.eContainer() == trgOrgUnit.eContainer()) {
                return false;
            }
        }

        return super.canExecute();
    }

}
