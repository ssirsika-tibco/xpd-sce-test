package com.tibco.xpd.om.modeler.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;

import com.tibco.xpd.om.core.om.DynamicOrgReference;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.modeler.diagram.edit.policies.OrganizationModelBaseItemSemanticEditPolicy;

/**
 * @generated
 */
public class DynamicOrgReferenceReorientCommand extends EditElementCommand {

	/**
	 * @generated
	 */
	private final int reorientDirection;

	/**
	 * @generated
	 */
	private final EObject oldEnd;

	/**
	 * @generated
	 */
	private final EObject newEnd;

	/**
	 * @generated
	 */
	public DynamicOrgReferenceReorientCommand(
			ReorientRelationshipRequest request) {
		super(request.getLabel(), request.getRelationship(), request);
		reorientDirection = request.getDirection();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
	}

	/**
	 * @generated
	 */
	public boolean canExecute() {
		if (false == getElementToEdit() instanceof DynamicOrgReference) {
			return false;
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE) {
			return canReorientSource();
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET) {
			return canReorientTarget();
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean canReorientSource() {
		if (!(oldEnd instanceof DynamicOrgUnit && newEnd instanceof DynamicOrgUnit)) {
			return false;
		}
		Organization target = getLink().getTo();
		if (!(getLink().eContainer() instanceof OrgModel)) {
			return false;
		}
		OrgModel container = (OrgModel) getLink().eContainer();
		return OrganizationModelBaseItemSemanticEditPolicy.LinkConstraints
				.canExistDynamicOrgReference_3002(container, getNewSource(),
						target);
	}

	/**
	 * @generated
	 */
	protected boolean canReorientTarget() {
		if (!(oldEnd instanceof Organization && newEnd instanceof Organization)) {
			return false;
		}
		DynamicOrgUnit source = getLink().getFrom();
		if (!(getLink().eContainer() instanceof OrgModel)) {
			return false;
		}
		OrgModel container = (OrgModel) getLink().eContainer();
		return OrganizationModelBaseItemSemanticEditPolicy.LinkConstraints
				.canExistDynamicOrgReference_3002(container, source,
						getNewTarget());
	}

	/**
	 * @generated
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException(
					"Invalid arguments in reorient link command"); //$NON-NLS-1$
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE) {
			return reorientSource();
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET) {
			return reorientTarget();
		}
		throw new IllegalStateException();
	}

	/**
	 * @generated
	 */
	protected CommandResult reorientSource() throws ExecutionException {
		getLink().setFrom(getNewSource());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected CommandResult reorientTarget() throws ExecutionException {
		getLink().setTo(getNewTarget());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected DynamicOrgReference getLink() {
		return (DynamicOrgReference) getElementToEdit();
	}

	/**
	 * @generated
	 */
	protected DynamicOrgUnit getOldSource() {
		return (DynamicOrgUnit) oldEnd;
	}

	/**
	 * @generated
	 */
	protected DynamicOrgUnit getNewSource() {
		return (DynamicOrgUnit) newEnd;
	}

	/**
	 * @generated
	 */
	protected Organization getOldTarget() {
		return (Organization) oldEnd;
	}

	/**
	 * @generated
	 */
	protected Organization getNewTarget() {
		return (Organization) newEnd;
	}
}
