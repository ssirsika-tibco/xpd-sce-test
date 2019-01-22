/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.policies.UMLBaseItemSemanticEditPolicy;

/**
 * @generated
 */
public class AssociationEndCreateCommand extends CreateElementCommand {

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
    public AssociationEndCreateCommand(CreateRelationshipRequest request,
            EObject source, EObject target) {
        super(request);
        this.source = source;
        this.target = target;
        if (request.getContainmentFeature() == null) {
            setContainmentFeature(UMLPackage.eINSTANCE
                    .getAssociation_OwnedEnd());
        }

        super.setElementToEdit(source);
    }

    /**
     * @generated
     */
    public boolean canExecute() {
        if (source == null && target == null) {
            return false;
        }
        if (source != null && false == source instanceof Association) {
            return false;
        }
        if (target != null && false == target instanceof Type) {
            return false;
        }
        if (getSource() == null) {
            return true; // link creation is in progress; source is not defined yet
        }
        // target may be null here but it's possible to check constraint
        return UMLBaseItemSemanticEditPolicy.LinkConstraints
                .canCreateProperty_3003(getSource(), getTarget());
    }

    /**
     * @generated
     */
    protected EObject doDefaultElementCreation() {
        Property newElement = UMLFactory.eINSTANCE.createProperty();
        getSource().getOwnedEnds().add(newElement);
        newElement.setType(getTarget());
        return newElement;
    }

    /**
     * @generated
     */
    protected EClass getEClassToEdit() {
        return UMLPackage.eINSTANCE.getAssociation();
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
    protected Association getSource() {
        return (Association) source;
    }

    /**
     * @generated
     */
    protected Type getTarget() {
        return (Type) target;
    }
}
