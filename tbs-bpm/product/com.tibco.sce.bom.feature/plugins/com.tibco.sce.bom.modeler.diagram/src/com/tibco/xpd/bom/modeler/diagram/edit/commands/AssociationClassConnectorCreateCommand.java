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
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.CollaborationUse;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.policies.UMLBaseItemSemanticEditPolicy;

/**
 * @generated
 */
public class AssociationClassConnectorCreateCommand extends
        CreateElementCommand {

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
    private Package container;

    /**
     * @generated
     */
    public AssociationClassConnectorCreateCommand(
            CreateRelationshipRequest request, EObject source, EObject target) {
        super(request);
        this.source = source;
        this.target = target;
        if (request.getContainmentFeature() == null) {
            setContainmentFeature(UMLPackage.eINSTANCE
                    .getPackage_PackagedElement());
        }

        // Find container element for the new link.
        // Climb up by containment hierarchy starting from the source
        // and return the first element that is instance of the container class.
        for (EObject element = source; element != null; element = element
                .eContainer()) {
            if (element instanceof Package) {
                container = (Package) element;
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
        if (source != null && false == source instanceof CollaborationUse) {
            return false;
        }
        if (target != null && false == target instanceof CollaborationUse) {
            return false;
        }
        if (getSource() == null) {
            return true; // link creation is in progress; source is not defined yet
        }
        // target may be null here but it's possible to check constraint
        if (getContainer() == null) {
            return false;
        }
        return UMLBaseItemSemanticEditPolicy.LinkConstraints
                .canCreateAssociationClass_3004(getContainer(), getSource(),
                        getTarget());
    }

    /**
     * @generated
     */
    protected EObject doDefaultElementCreation() {
        AssociationClass newElement = UMLFactory.eINSTANCE
                .createAssociationClass();
        getContainer().getPackagedElements().add(newElement);
        newElement.getCollaborationUses().add(getSource());
        newElement.getCollaborationUses().add(getTarget());
        return newElement;
    }

    /**
     * @generated
     */
    protected EClass getEClassToEdit() {
        return UMLPackage.eINSTANCE.getPackage();
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
    protected CollaborationUse getSource() {
        return (CollaborationUse) source;
    }

    /**
     * @generated
     */
    protected CollaborationUse getTarget() {
        return (CollaborationUse) target;
    }

    /**
     * @generated
     */
    public Package getContainer() {
        return container;
    }
}
