package com.tibco.xpd.om.modeler.subdiagram.edit.commands;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.core.om.provider.OrgUnitItemProvider;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.om.modeler.subdiagram.part.custom.IOrganizationModelDiagramConstants;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * @generated
 */
public class PositionSubCreateCommand extends CreateElementCommand {

	PositionFeature posFeature;

	private int index = -1;

	/**
	 * @generated NOT
	 */
	public PositionSubCreateCommand(CreateElementRequest req) {
		super(req);

		Object parameter = req
				.getParameter(IOrganizationModelDiagramConstants.OMCreationToolFeature);

		if (parameter instanceof PositionFeature) {
			posFeature = (PositionFeature) parameter;
		}

		Object param2 = req
				.getParameter(IOrganizationModelDiagramConstants.OMCreateElementIndex);

		if (param2 instanceof Integer) {
			index = (Integer) param2;
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
		return OMPackage.eINSTANCE.getOrgUnit();
	}

	@Override
	protected EObject doDefaultElementCreation() {
		EObject eo = super.doDefaultElementCreation();

		if (eo instanceof Position) {
			Position pos = (Position) eo;
			String defaultName = "";

			if (posFeature != null) {
				pos.setFeature(posFeature);

				EObject container = pos.eContainer();

				if (container instanceof OrgUnit) {
					OrgUnit ou = (OrgUnit) container;
					defaultName = OMUtil.getDefaultName(posFeature
							.getDisplayName(), OMUtil.getDisplayNamesArray(ou
							.getPositions()));
				}
				pos.setDisplayName(defaultName);
			} else {
				defaultName = getUniquePositionName(pos);
				pos.setDisplayName(defaultName);
			}

			if (index != -1) {

				EObject container = pos.eContainer();

				if (container instanceof OrgUnit) {
					OrgUnit ou = (OrgUnit) container;
					EList<Position> positions = ou.getPositions();

					// Get the current index of the newly created Position
					int oldIndex = positions.indexOf(pos);
					positions.move(index, oldIndex);

				}
			}
		}

		return eo;
	}

	private String getUniquePositionName(Position position) {
		String defaultName = ""; //$NON-NLS-1$
		OrgUnit ou = (OrgUnit) position.eContainer();

		IEditingDomainItemProvider provider = (IEditingDomainItemProvider) XpdResourcesPlugin
				.getDefault().getAdapterFactory().adapt(ou,
						IEditingDomainItemProvider.class);

		if (provider instanceof OrgUnitItemProvider) {
			OrgUnitItemProvider orgProvider = (OrgUnitItemProvider) provider;

			// All to get this string!
			String prefix = orgProvider.getString("_UI_Position_type"); //$NON-NLS-1$

			defaultName = OMUtil.getDefaultName(prefix, OMUtil
					.getDisplayNamesArray(ou.getPositions()));

		}

		return defaultName;
	}

}
