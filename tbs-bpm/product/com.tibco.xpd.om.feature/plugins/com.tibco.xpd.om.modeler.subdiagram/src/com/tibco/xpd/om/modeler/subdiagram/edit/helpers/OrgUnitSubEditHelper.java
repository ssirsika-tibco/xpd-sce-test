package com.tibco.xpd.om.modeler.subdiagram.edit.helpers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;

import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.Organization;

/**
 * @generated
 */
public class OrgUnitSubEditHelper extends OrganizationModelBaseEditHelper {

	@Override
	protected ICommand getDestroyDependentsCommand(DestroyDependentsRequest req) {
		EObject elementToDestroy = req.getElementToDestroy();
		if (elementToDestroy instanceof OrgUnit) {
			// This OrgUnit may be referenced to another OrgUnit in a different
			// Organization via an Association. In this case, destroying the
			// OrgUnit will not destroy the OrgUnitRelationship because the
			// OrgUnit does not own the relationship. Therefore will will have
			// to help out and generate a destroy command for the relationship
			// ourselves.
			OrgUnit ou = (OrgUnit) elementToDestroy;
			EObject ouContainer = ou.eContainer();

			if (ouContainer instanceof Organization) {
				Organization org = (Organization) ouContainer;
				List<EObject> relsToDestroy = new ArrayList<EObject>();

				// Check Incoming.
				EList<OrgUnitRelationship> incomingRelationships = ou
						.getIncomingRelationships();

				for (OrgUnitRelationship incomingRel : incomingRelationships) {
					EObject relContainer = incomingRel.eContainer();

					// if (relContainer != org) {
					relsToDestroy.add(incomingRel);
					// }
				}

				// Check outgoing. If target OrgUnit exists in a different
				// Organization then we need to destroy the relationship from
				// here as well.
				EList<OrgUnitRelationship> outgoingRelationships = ou
						.getOutgoingRelationships();

				for (OrgUnitRelationship outgoingRel : outgoingRelationships) {
					OrgUnit to = outgoingRel.getTo();
					EObject outgoingRelContainer = to.eContainer();

					relsToDestroy.add(outgoingRel);
				}

				// If we have gathered any dependent relationships then generate
				// the command to destroy them.
				if (!relsToDestroy.isEmpty()) {
					return req.getDestroyDependentsCommand(relsToDestroy);
				}
			}

		}
		return super.getDestroyDependentsCommand(req);
	}
}
