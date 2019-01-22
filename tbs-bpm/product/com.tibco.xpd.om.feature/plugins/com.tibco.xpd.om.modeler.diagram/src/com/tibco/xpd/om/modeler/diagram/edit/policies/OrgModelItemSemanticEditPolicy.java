package com.tibco.xpd.om.modeler.diagram.edit.policies;

import java.util.List;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.commands.DuplicateEObjectsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.modeler.diagram.edit.commands.OrganizationCreateCommand;
import com.tibco.xpd.om.modeler.diagram.providers.OrganizationModelElementTypes;

/**
 * @generated
 */
public class OrgModelItemSemanticEditPolicy extends
        OrganizationModelBaseItemSemanticEditPolicy {

    /**
     * @generated
     */
    protected Command getCreateCommand(CreateElementRequest req) {
        if (OrganizationModelElementTypes.Organization_1001 == req
                .getElementType()) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(OMPackage.eINSTANCE
                        .getOrgModel_Organizations());
            }
            return getGEFWrapper(new OrganizationCreateCommand(req));
        }
        return super.getCreateCommand(req);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.om.modeler.diagram.edit.policies.
     * OrganizationModelBaseItemSemanticEditPolicy
     * #getDuplicateCommand(org.eclipse
     * .gmf.runtime.emf.type.core.requests.DuplicateElementsRequest)
     */
    protected Command getDuplicateCommand(DuplicateElementsRequest req) {
        List<?> toDuplicate = req.getElementsToBeDuplicated();

        // Only allow duplication of Organizations
        if (toDuplicate != null && !toDuplicate.isEmpty()) {
            for (Object elem : toDuplicate) {
                if (!(elem instanceof Organization)) {
                    return null;
                }
            }
        }

        return getDuplicateCommandGen(req);
    }

    /**
     * @generated
     */
    protected Command getDuplicateCommandGen(DuplicateElementsRequest req) {
        TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
                .getEditingDomain();
        return getGEFWrapper(new DuplicateAnythingCommand(editingDomain, req));
    }

    /**
     * @generated
     */
    private static class DuplicateAnythingCommand extends
            DuplicateEObjectsCommand {

        /**
         * @generated
         */
        public DuplicateAnythingCommand(
                TransactionalEditingDomain editingDomain,
                DuplicateElementsRequest req) {
            super(editingDomain, req.getLabel(), req
                    .getElementsToBeDuplicated(), req
                    .getAllDuplicatedElementsMap());
        }

    }

}
