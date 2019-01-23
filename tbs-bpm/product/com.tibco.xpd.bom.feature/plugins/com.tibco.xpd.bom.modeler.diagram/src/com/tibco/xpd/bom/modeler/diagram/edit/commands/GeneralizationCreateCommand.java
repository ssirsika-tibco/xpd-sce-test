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
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.policies.UMLBaseItemSemanticEditPolicy;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;

/**
 * @generated
 */
public class GeneralizationCreateCommand extends CreateElementCommand {

    private Stereotype stereo;

    /**
     * @generated
     */
    private final EObject source;

    /**
     * @generated
     */
    private final EObject target;

    /**
     * @generated NOT
     */
    public GeneralizationCreateCommand(CreateRelationshipRequest request,
            EObject source, EObject target) {
        super(request);
        this.source = source;
        this.target = target;
        if (request.getContainmentFeature() == null) {
            setContainmentFeature(UMLPackage.eINSTANCE
                    .getClassifier_Generalization());
        }

        stereo = (Stereotype) request.getParameter(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE);

        super.setElementToEdit(source);
    }

    /**
     * @generated
     */
    public boolean canExecute() {
        if (source == null && target == null) {
            return false;
        }
        if (source != null && false == source instanceof Classifier) {
            return false;
        }
        if (target != null && false == target instanceof Classifier) {
            return false;
        }
        if (getSource() == null) {
            return true; // link creation is in progress; source is not defined yet
        }
        // target may be null here but it's possible to check constraint
        return UMLBaseItemSemanticEditPolicy.LinkConstraints
                .canCreateGeneralization_3001(getSource(), getTarget());
    }

    /**
     * @generated NOT
     */
    protected EObject doDefaultElementCreation() {

        // If the source already has a generalization then replace
        // it with the new one
        if (getSource().getGeneralizations().size() > 0) {
            getSource().getGeneralizations().remove(0);
        }

        return doDefaultElementCreationGen();
    }

    /**
     * @generated NOT
     */
    protected EObject doDefaultElementCreationGen() {
        // org.eclipse.uml2.uml.Generalization newElement = (org.eclipse.uml2.uml.Generalization) super.doDefaultElementCreation();
        Generalization newElement = UMLFactory.eINSTANCE.createGeneralization();
        getSource().getGeneralizations().add(newElement);
        newElement.setGeneral(getTarget());

        if (stereo != null) {
            UML2ModelUtil.safeApplyStereotype(newElement, stereo);
        }

        return newElement;
    }

    /**
     * @generated
     */
    protected EClass getEClassToEdit() {
        return UMLPackage.eINSTANCE.getClassifier();
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
    protected Classifier getSource() {
        return (Classifier) source;
    }

    /**
     * @generated
     */
    protected Classifier getTarget() {
        return (Classifier) target;
    }
}
