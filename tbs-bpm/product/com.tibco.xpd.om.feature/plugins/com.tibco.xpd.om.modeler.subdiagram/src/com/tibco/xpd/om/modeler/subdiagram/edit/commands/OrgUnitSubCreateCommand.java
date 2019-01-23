package com.tibco.xpd.om.modeler.subdiagram.edit.commands;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.provider.OrganizationItemProvider;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.om.modeler.subdiagram.part.custom.IOrganizationModelDiagramConstants;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * @generated
 */
public class OrgUnitSubCreateCommand extends CreateElementCommand {

	OrgUnitFeature ouFeature;

	OrgUnit parentOrgUnit;

	Boolean isSubUnit;

	CreateElementRequest request;

	/**
	 * @generated NOT
	 */
	public OrgUnitSubCreateCommand(CreateElementRequest req) {
		super(req);

		Object parameter = req
				.getParameter(IOrganizationModelDiagramConstants.OMCreationToolFeature);

		request = req;

		if (parameter instanceof OrgUnitFeature) {
			ouFeature = (OrgUnitFeature) parameter;
		}

		Object parameter2 = req
				.getParameter(IOrganizationModelDiagramConstants.OMCreationToolParentOrgUnit);

		if (parameter2 != null && parameter2 instanceof OrgUnit) {
			parentOrgUnit = (OrgUnit) parameter2;
		}

	}

	/**
	 * @generated
	 */
	@Override
	protected EObject getElementToEdit() {
		EObject container = ((CreateElementRequest) getRequest())
				.getContainer();
		if (container instanceof View) {
			container = ((View) container).getElement();
		}
		return container;
	}

	/**
	 * @generated
	 */
	@Override
	protected EClass getEClassToEdit() {
		return OMPackage.eINSTANCE.getOrganization();
	}

	@Override
	protected EObject doDefaultElementCreation() {

		EObject eo = super.doDefaultElementCreation();

		if (eo instanceof OrgUnit) {
			OrgUnit ou = (OrgUnit) eo;

			if (ouFeature != null) {
				EList<OrgUnit> units = ou.getOrganization().getUnits();
				String defaultName = OMUtil.getDefaultName(ouFeature
						.getDisplayName(), OMUtil.getDisplayNamesArray(units));

				ou.setDisplayName(defaultName);

				ou.setFeature(ouFeature);

				if (parentOrgUnit != null) {
					// Need to create an OrgUnitRelationship with the source as
					// the parentOrg and the target as the newly created OrgUnit
					OrgUnitRelationship rel = OMFactory.eINSTANCE
							.createOrgUnitRelationship();
					rel.setTo(ou);
					rel.setFrom(parentOrgUnit);
					rel.setIsHierarchical(true);
					rel.setDisplayName(OMUtil.createOrgUnitRelationshipName(
							rel, true));
					EObject container = ou.eContainer();

					if (container instanceof Organization) {
						Organization organization = (Organization) container;
						organization.getOrgUnitRelationships().add(rel);
					}
				}
			} else {
				ou.setDisplayName(getUniqueOrgUnitName(ou));
			}
		}

		return eo;
	}

	private String getUniqueOrgUnitName(OrgUnit ou) {
		String defaultName = ""; //$NON-NLS-1$
		Organization organization = (Organization) ou.eContainer();

		IEditingDomainItemProvider provider = (IEditingDomainItemProvider) XpdResourcesPlugin
				.getDefault().getAdapterFactory().adapt(organization,
						IEditingDomainItemProvider.class);

		if (provider instanceof OrganizationItemProvider) {
			OrganizationItemProvider orgProvider = (OrganizationItemProvider) provider;

			String prefix = orgProvider
					.getString("_UI_DefaultName_OrgSubUnit_label");//$NON-NLS-1$

			Collection<String> displayNamesArray = OMUtil
					.getDisplayNamesArray(organization.getUnits());

			defaultName = OMUtil.getDefaultName(prefix, displayNamesArray);

		}

		return defaultName;
	}
}
