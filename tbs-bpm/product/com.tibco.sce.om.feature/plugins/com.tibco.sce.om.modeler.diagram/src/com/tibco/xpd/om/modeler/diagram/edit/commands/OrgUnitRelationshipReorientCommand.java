package com.tibco.xpd.om.modeler.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;

import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.modeler.diagram.edit.policies.OrganizationModelBaseItemSemanticEditPolicy;

/**
 * @generated
 */
public class OrgUnitRelationshipReorientCommand extends EditElementCommand {

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
    public OrgUnitRelationshipReorientCommand(
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

        // For now we are going to allow reorientation ONLY for the
        // non-hierarchical associations ACROSS Organizations. This may change
        // later though.
        EObject element = getElementToEdit();

        if (element instanceof OrgUnitRelationship) {
            OrgUnitRelationship rel = (OrgUnitRelationship) element;

            if (rel.isIsHierarchical()) {
                return false;
            } else {
                OrgUnit ouFrom = rel.getFrom();
                OrgUnit ouTo = rel.getTo();

                if (ouFrom != null && ouTo != null) {

                    if (reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE) {
                        if (ouTo.eContainer() == newEnd.eContainer()) {
                            return false;
                        }
                    }

                    if (reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET) {
                        if (ouFrom.eContainer() == newEnd.eContainer()) {
                            return false;
                        }
                    }
                }
            }
        }

        // this is the auto-generated code.
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
     * @generated
     */
    protected boolean canReorientSource() {
        if (!(oldEnd instanceof OrgUnit && newEnd instanceof OrgUnit)) {
            return false;
        }
        OrgUnit target = getLink().getTo();
        if (!(getLink().eContainer() instanceof Organization)) {
            return false;
        }
        Organization container = (Organization) getLink().eContainer();
        return OrganizationModelBaseItemSemanticEditPolicy.LinkConstraints
                .canExistOrgUnitRelationship_3001(container, getNewSource(),
                        target);
    }

    /**
     * @generated
     */
    protected boolean canReorientTarget() {
        if (!(oldEnd instanceof OrgUnit && newEnd instanceof OrgUnit)) {
            return false;
        }
        OrgUnit source = getLink().getFrom();
        if (!(getLink().eContainer() instanceof Organization)) {
            return false;
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
