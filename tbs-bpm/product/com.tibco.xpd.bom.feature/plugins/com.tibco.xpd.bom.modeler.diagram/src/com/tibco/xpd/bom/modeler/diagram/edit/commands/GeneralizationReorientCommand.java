/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;

import com.tibco.xpd.bom.modeler.diagram.edit.policies.UMLBaseItemSemanticEditPolicy;

/**
 * @generated
 */
public class GeneralizationReorientCommand extends EditElementCommand {

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
    public GeneralizationReorientCommand(ReorientRelationshipRequest request) {
        super(request.getLabel(), request.getRelationship(), request);
        reorientDirection = request.getDirection();
        oldEnd = request.getOldRelationshipEnd();
        newEnd = request.getNewRelationshipEnd();
    }

    /**
     * @generated
     */
    public boolean canExecute() {
        if (false == getElementToEdit() instanceof Generalization) {
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
        if (!(oldEnd instanceof Classifier && newEnd instanceof Classifier)) {
            return false;
        }

        Classifier source = (Classifier) getNewSource();

        if (source != null) {
            // Check that the source class does not already extend another class
            if (source.getGeneralizations().size() > 0) {
                return false;
            }
        }

        return canReorientSourceGen();
    }

    /**
     * @generated
     */
    protected boolean canReorientSourceGen() {
        if (!(oldEnd instanceof Classifier && newEnd instanceof Classifier)) {
            return false;
        }

        Classifier target = getLink().getGeneral();

        return UMLBaseItemSemanticEditPolicy.LinkConstraints
                .canExistGeneralization_3001(getNewSource(), target);
    }

    /**
     * @generated
     */
    protected boolean canReorientTarget() {
        if (!(oldEnd instanceof Classifier && newEnd instanceof Classifier)) {
            return false;
        }

        if (!(getLink().eContainer() instanceof Classifier)) {
            return false;
        }
        Classifier source = (Classifier) getLink().eContainer();
        return UMLBaseItemSemanticEditPolicy.LinkConstraints
                .canExistGeneralization_3001(source, getNewTarget());
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
        getOldSource().getGeneralizations().remove(getLink());
        getNewSource().getGeneralizations().add(getLink());
        return CommandResult.newOKCommandResult(getLink());
    }

    /**
     * @generated
     */
    protected CommandResult reorientTarget() throws ExecutionException {
        getLink().setGeneral(getNewTarget());
        return CommandResult.newOKCommandResult(getLink());
    }

    /**
     * @generated
     */
    protected Generalization getLink() {
        return (Generalization) getElementToEdit();
    }

    /**
     * @generated
     */
    protected Classifier getOldSource() {
        return (Classifier) oldEnd;
    }

    /**
     * @generated
     */
    protected Classifier getNewSource() {
        return (Classifier) newEnd;
    }

    /**
     * @generated
     */
    protected Classifier getOldTarget() {
        return (Classifier) oldEnd;
    }

    /**
     * @generated
     */
    protected Classifier getNewTarget() {
        return (Classifier) newEnd;
    }
}
