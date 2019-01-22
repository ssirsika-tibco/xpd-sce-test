package com.tibco.xpd.om.modeler.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;

import com.tibco.xpd.om.core.om.DynamicOrgReference;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.modeler.diagram.edit.policies.OrganizationModelBaseItemSemanticEditPolicy;

/**
 * @generated
 */
public class DynamicOrgReferenceCreateCommand extends CreateElementCommand {

	/**
	 * @generated
	 */
	private final EObject source;

	/**
	 * @generated
	 */
	private final EObject target;

	/**
	 * @generated
	 */
	private OrgModel container;

	/**
	 * @generated
	 */
	public DynamicOrgReferenceCreateCommand(CreateRelationshipRequest request,
			EObject source, EObject target) {
		super(request);
		this.source = source;
		this.target = target;
		if (request.getContainmentFeature() == null) {
			setContainmentFeature(OMPackage.eINSTANCE
					.getOrgModel_DynamicOrgReferences());
		}

		// Find container element for the new link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null; element = element
				.eContainer()) {
			if (element instanceof OrgModel) {
				container = (OrgModel) element;
				super.setElementToEdit(container);
				break;
			}
		}
	}

	/**
	 * @generated
	 */
	public boolean canExecute() {
		if (source == null && target == null) {
			return false;
		}
		if (source != null && false == source instanceof DynamicOrgUnit) {
			return false;
		}
		if (target != null && false == target instanceof Organization) {
			return false;
		}
		if (getSource() == null) {
			return true; // link creation is in progress; source is not defined yet
		}
		// target may be null here but it's possible to check constraint
		if (getContainer() == null) {
			return false;
		}
		return OrganizationModelBaseItemSemanticEditPolicy.LinkConstraints
				.canCreateDynamicOrgReference_3002(getContainer(), getSource(),
						getTarget());
	}

	/**
	 * @generated
	 */
	protected EObject doDefaultElementCreation() {
		DynamicOrgReference newElement = OMFactory.eINSTANCE
				.createDynamicOrgReference();
		getContainer().getDynamicOrgReferences().add(newElement);
		newElement.setFrom(getSource());
		newElement.setTo(getTarget());
		return newElement;
	}

	/**
	 * @generated
	 */
	protected EClass getEClassToEdit() {
		return OMPackage.eINSTANCE.getOrgModel();
	}

	/**
	 * @generated
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException(
					"Invalid arguments in create link command"); //$NON-NLS-1$
		}
		return super.doExecuteWithResult(monitor, info);
	}

	/**
	 * @generated
	 */
	protected ConfigureRequest createConfigureRequest() {
		ConfigureRequest request = super.createConfigureRequest();
		request.setParameter(CreateRelationshipRequest.SOURCE, getSource());
		request.setParameter(CreateRelationshipRequest.TARGET, getTarget());
		return request;
	}

	/**
	 * @generated
	 */
	protected void setElementToEdit(EObject element) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @generated
	 */
	protected DynamicOrgUnit getSource() {
		return (DynamicOrgUnit) source;
	}

	/**
	 * @generated
	 */
	protected Organization getTarget() {
		return (Organization) target;
	}

	/**
	 * @generated
	 */
	public OrgModel getContainer() {
		return container;
	}
}
