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
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.modeler.diagram.edit.policies.UMLBaseItemSemanticEditPolicy;

/**
 * @generated
 */
public class CompositionReorientCommand extends EditElementCommand {

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
    public CompositionReorientCommand(ReorientRelationshipRequest request) {
        super(request.getLabel(), request.getRelationship(), request);
        reorientDirection = request.getDirection();
        oldEnd = request.getOldRelationshipEnd();
        newEnd = request.getNewRelationshipEnd();
    }

    /**
     * @generated
     */
    public boolean canExecute() {
        if (!(getElementToEdit() instanceof Association)) {
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
        if (!(oldEnd instanceof Type && newEnd instanceof Type)) {
            return false;
        }
        if (getLink().getEndTypes().size() <= 1) {
            return false;
        }
        Type target = (Type) getLink().getEndTypes().get(1);
        if (!(getLink().eContainer() instanceof Package)) {
            return false;
        }
        Package container = (Package) getLink().eContainer();
        return UMLBaseItemSemanticEditPolicy.LinkConstraints
                .canExistAssociation_3002(container, getNewSource(), target);
    }

    /**
     * @generated NOT
     */
    protected boolean canReorientTarget() {
        if (!(oldEnd instanceof Type && newEnd instanceof Type)) {
            return false;
        }
        if (getLink().getEndTypes().size() <= 0) {
            return false;
        }
        Type source = (Type) getLink().getEndTypes().get(0);
        if (!(getLink().eContainer() instanceof Package)) {
            return false;
        }
        Package container = (Package) getLink().eContainer();
        return UMLBaseItemSemanticEditPolicy.LinkConstraints
                .canExistAssociation_3002(container, source, getNewTarget());
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
        Association assoc = getLink();
        Property src = assoc.getMemberEnds().get(1);
        Property trg = assoc.getMemberEnds().get(0);

        if (src.getOwner() != assoc) {
            org.eclipse.uml2.uml.Class cl = (Class) getNewTarget();
            cl.getOwnedAttributes().add(src);
        }
        trg.setType(getNewTarget());
        return CommandResult.newOKCommandResult(assoc);
    }

    /**
     * @generated NOT
     */
    protected CommandResult reorientTarget() throws ExecutionException {
        Association assoc = getLink();
        Property src = assoc.getMemberEnds().get(0);
        Property trg = assoc.getMemberEnds().get(1);

        if (src.getOwner() != assoc) {
            org.eclipse.uml2.uml.Class cl = (Class) getNewTarget();
            cl.getOwnedAttributes().add(src);
        }
        trg.setType(getNewTarget());
        return CommandResult.newOKCommandResult(assoc);
    }

    /**
     * @generated
     */
    protected Association getLink() {
        return (Association) getElementToEdit();
    }

    /**
     * @generated
     */
    protected Type getOldSource() {
        return (Type) oldEnd;
    }

    /**
     * @generated
     */
    protected Type getNewSource() {
        return (Type) newEnd;
    }

    /**
     * @generated
     */
    protected Type getOldTarget() {
        return (Type) oldEnd;
    }

    /**
     * @generated
     */
    protected Type getNewTarget() {
        return (Type) newEnd;
    }
}
