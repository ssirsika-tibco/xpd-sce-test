/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.helpers;

import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.core.util.CrossReferenceAdapter;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.commands.OperationCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.edit.commands.PropertyCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;

/**
 * @generated
 */
public class ClassEditHelper extends UMLBaseEditHelper {
    /**
     * Override and remove all incoming and outgoing associations as they are
     * dependent objects.
     */
    @SuppressWarnings("unchecked")
    @Override
    protected ICommand getDestroyDependentsCommand(DestroyDependentsRequest req) {

        if (req.getElementToDestroy() instanceof Class) {
            Class cl = (Class) req.getElementToDestroy();
            EList<EObject> objsToDestroy = new BasicEList<EObject>();

            // Destroy all associations
            objsToDestroy.addAll(cl.getAssociations());

            // Destroy all 'incoming' generalisations
            CrossReferenceAdapter crossReferencer = CrossReferenceAdapter
                    .getCrossReferenceAdapter(cl.eResource().getResourceSet());
            Set<Generalization> generalizations = crossReferencer
                    .getInverseReferencers(cl, UMLPackage.eINSTANCE
                            .getGeneralization_General(), null);

            for (Generalization gen : generalizations) {
                // It can be null if it was already removed
                if (gen.getSpecific() != null && gen.eResource() != null) {
                    objsToDestroy.add(gen);
                }
            }

            // Destroy class' stereotype applications
            objsToDestroy.addAll(cl.getStereotypeApplications());

            if (!objsToDestroy.isEmpty()) {
                return req.getDestroyDependentsCommand(objsToDestroy);
            }
        }

        return super.getDestroyDependentsCommand(req);
    }

    @Override
    protected ICommand getCreateCommand(CreateElementRequest req) {
        if (UMLElementTypes.Property_2001.equals(req.getElementType())) {
            return new PropertyCreateCommand(req);
        }
        if (UMLElementTypes.Operation_2002.equals(req.getElementType())) {
            return new OperationCreateCommand(req);
        }
        return super.getCreateCommand(req);
    }
}
