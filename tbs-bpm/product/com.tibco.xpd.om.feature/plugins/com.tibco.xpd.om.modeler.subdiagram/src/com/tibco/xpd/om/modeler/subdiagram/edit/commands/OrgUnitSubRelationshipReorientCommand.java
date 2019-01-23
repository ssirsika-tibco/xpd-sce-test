package com.tibco.xpd.om.modeler.subdiagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;

import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.om.modeler.subdiagram.edit.policies.OrganizationModelBaseItemSemanticEditPolicy;

/**
 * @generated
 */
public class OrgUnitSubRelationshipReorientCommand extends EditElementCommand {

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
	public OrgUnitSubRelationshipReorientCommand(
			ReorientRelationshipRequest request) {
		super(request.getLabel(), request.getRelationship(), request);
		reorientDirection = request.getDirection();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
	}

	/**
	 * @generated NOT
	 */
	public boolean canExecute() {

		// Start of generated code
		if (false == getElementToEdit() instanceof OrgUnitRelationship) {
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
	 * @generated NOT
	 */
	protected boolean canReorientSource() {
		if (!(oldEnd instanceof OrgUnit && newEnd instanceof OrgUnit)) {
			return false;
		}
		OrgUnit target = getLink().getTo();
		if (!(getLink().eContainer() instanceof Organization)) {
			return false;
		}

		/*
		 * If the target has a feature set then the new source has to be of the
		 * same type as the old source (if hierarchical)
		 */
		if (getLink().isIsHierarchical() && target != null
				&& target.getFeature() != null) {
			// if (!((OrgUnit) oldEnd).getType().equals(((OrgUnit) newEnd)
			// .getType())) {
			// return false;
			// }

			// If new target has a feature set then cannot re-orient to it
			if (((OrgUnit) newEnd).getFeature() != null) {

				OrgUnit srcNew = (OrgUnit) newEnd;

				// Get feature of new target
				OrgUnitFeature featureTrgNew = target.getFeature();

				// Get the type of the source
				OrgUnitType typeSrc = srcNew.getOrgUnitType();

				if (featureTrgNew != null
						&& (typeSrc != null && !typeSrc.getOrgUnitFeatures()
								.contains(featureTrgNew))) {
					return false;
				}
			}

		}
		Organization container = (Organization) getLink().eContainer();
		return OrganizationModelBaseItemSemanticEditPolicy.LinkConstraints
				.canExistOrgUnitRelationship_3001(container, getNewSource(),
						target);
	}

	/**
	 * @generated NOT
	 */
	protected boolean canReorientTarget() {
		if (!(oldEnd instanceof OrgUnit && newEnd instanceof OrgUnit)) {
			return false;
		}
		OrgUnit source = getLink().getFrom();
		if (!(getLink().eContainer() instanceof Organization)) {
			return false;
		}

		// Don't allow a reorientation if it ends up as a cyclic hierarchy
		OrgUnitRelationship rel = getLink();
		if (rel.isIsHierarchical()) {
			OrgUnit ouFrom = rel.getFrom();
			OrgUnit ouTo = rel.getTo();

			if (ouFrom != null && ouTo != null) {
				if (OMUtil.isOrgUnitInHierarchy(ouFrom, (OrgUnit) newEnd)) {
					return false;
				}
			}

			// If new target has a feature set then cannot re-orient to it
			if (((OrgUnit) newEnd).getFeature() != null) {

				OrgUnit trgNew = (OrgUnit) newEnd;
				OrgUnit src = (OrgUnit) ouFrom;

				// Get feature of new target
				OrgUnitFeature featureTrgNew = trgNew.getFeature();

				// Get the type of the source
				OrgUnitType typeSrc = src.getOrgUnitType();

				if (featureTrgNew != null
						&& (typeSrc != null && !typeSrc.getOrgUnitFeatures()
								.contains(featureTrgNew))) {
					return false;
				}

			}
		}

		Organization container = (Organization) getLink().eContainer();
		return OrganizationModelBaseItemSemanticEditPolicy.LinkConstraints
				.canExistOrgUnitRelationship_3001(container, source,
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
	 * @generated NOT
	 */
	protected CommandResult reorientSource() throws ExecutionException {
		// Check if the link has a default generated name - if so then rename
		boolean isDefaultName = OMUtil.hasDefaultName(getLink());

		getLink().setFrom(getNewSource());

		if (isDefaultName) {
			// Rename
			getLink().setDisplayName(
					OMUtil.createOrgUnitRelationshipName(getLink(), true));
		}
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated NOT
	 */
	protected CommandResult reorientTarget() throws ExecutionException {

		// Check if the link has a default generated name - if so then rename
		boolean isDefaultName = OMUtil.hasDefaultName(getLink());

		getLink().setTo(getNewTarget());

		if (isDefaultName) {
			// Rename
			getLink().setDisplayName(
					OMUtil.createOrgUnitRelationshipName(getLink(), true));
		}

		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected OrgUnitRelationship getLink() {
		return (OrgUnitRelationship) getElementToEdit();
	}

	/**
	 * @generated
	 */
	protected OrgUnit getOldSource() {
		return (OrgUnit) oldEnd;
	}

	/**
	 * @generated
	 */
	protected OrgUnit getNewSource() {
		return (OrgUnit) newEnd;
	}

	/**
	 * @generated
	 */
	protected OrgUnit getOldTarget() {
		return (OrgUnit) oldEnd;
	}

	/**
	 * @generated
	 */
	protected OrgUnit getNewTarget() {
		return (OrgUnit) newEnd;
	}
}
