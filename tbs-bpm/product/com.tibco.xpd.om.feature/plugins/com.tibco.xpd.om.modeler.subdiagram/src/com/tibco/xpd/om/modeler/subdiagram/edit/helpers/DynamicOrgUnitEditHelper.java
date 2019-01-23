package com.tibco.xpd.om.modeler.subdiagram.edit.helpers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;

import com.tibco.xpd.om.core.om.DynamicOrgReference;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;

/**
 * @generated
 */
public class DynamicOrgUnitEditHelper extends OrganizationModelBaseEditHelper {

    /**
     * @see com.tibco.xpd.om.modeler.subdiagram.edit.helpers.OrganizationModelBaseEditHelper#getDestroyReferenceCommand(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest)
     * 
     * @param req
     * @return
     */
    @Override
    protected ICommand getDestroyReferenceCommand(DestroyReferenceRequest req) {
        // TODO Auto-generated method stub
        return super.getDestroyReferenceCommand(req);
    }

    /**
     * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelper#getDestroyDependentsCommand(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest)
     * 
     * @param req
     * @return
     */
    @Override
    protected ICommand getDestroyDependentsCommand(DestroyDependentsRequest req) {

        EObject eo = req.getElementToDestroy();
        if (eo instanceof DynamicOrgUnit) {
            DynamicOrgReference orgReference =
                    ((DynamicOrgUnit) eo).getDynamicOrganization();

            if (orgReference != null) {
                return req.getDestroyDependentCommand(orgReference);
            }
        }

        return super.getDestroyDependentsCommand(req);
    }

    /**
     * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelper#getDestroyElementWithDependentsCommand(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest)
     * 
     * @param req
     * @return
     */
    @Override
    protected ICommand getDestroyElementWithDependentsCommand(
            DestroyElementRequest req) {
        // TODO Auto-generated method stub
        return super.getDestroyElementWithDependentsCommand(req);
    }

    /**
     * @see com.tibco.xpd.om.modeler.subdiagram.edit.helpers.OrganizationModelBaseEditHelper#getDestroyElementCommand(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest)
     * 
     * @param req
     * @return
     */
    @Override
    protected ICommand getDestroyElementCommand(DestroyElementRequest req) {
        // TODO Auto-generated method stub
        return super.getDestroyElementCommand(req);
    }
}
