package com.tibco.xpd.om.modeler.subdiagram.edit.policies;

import java.util.List;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.commands.DuplicateEObjectsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.modeler.subdiagram.edit.commands.DynamicOrgUnitCreateCommand;
import com.tibco.xpd.om.modeler.subdiagram.edit.commands.OrgUnitSubCreateCommand;
import com.tibco.xpd.om.modeler.subdiagram.providers.OrganizationModelElementTypes;

/**
 * @generated
 */
public class OrganizationSubItemSemanticEditPolicy extends
		OrganizationModelBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (OrganizationModelElementTypes.OrgUnit_1001 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(OMPackage.eINSTANCE
						.getOrganization_Units());
			}
			return getGEFWrapper(new OrgUnitSubCreateCommand(req));
		}
		if (OrganizationModelElementTypes.DynamicOrgUnit_1002 == req
				.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(OMPackage.eINSTANCE
						.getOrganization_Units());
			}
			return getGEFWrapper(new DynamicOrgUnitCreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.tibco.xpd.om.modeler.subdiagram.edit.policies.
	 * OrganizationModelBaseItemSemanticEditPolicy
	 * #getDuplicateCommand(org.eclipse
	 * .gmf.runtime.emf.type.core.requests.DuplicateElementsRequest)
	 */
	protected Command getDuplicateCommand(DuplicateElementsRequest req) {
		// Cannot duplicate an Organization or OrgUnitRelationship
		List<?> toDuplicate = req.getElementsToBeDuplicated();
		if (toDuplicate != null && !toDuplicate.isEmpty()) {
			for (Object object : toDuplicate) {
				if (object instanceof Organization
						|| object instanceof OrgUnitRelationship) {
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
