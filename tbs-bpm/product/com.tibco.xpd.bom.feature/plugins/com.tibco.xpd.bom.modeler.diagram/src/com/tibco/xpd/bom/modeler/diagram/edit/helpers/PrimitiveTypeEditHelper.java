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
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class PrimitiveTypeEditHelper extends UMLBaseEditHelper {
    /**
     * Override and remove all incoming and outgoing associations as they are
     * dependent objects.
     */
    @SuppressWarnings("unchecked")
    @Override
    protected ICommand getDestroyDependentsCommand(DestroyDependentsRequest req) {
        if (req.getElementToDestroy() instanceof PrimitiveType) {
            PrimitiveType pType = (PrimitiveType) req.getElementToDestroy();
            EList<EObject> objsToDestroy = new BasicEList<EObject>();

            // Destroy all 'incoming' generalizations
            CrossReferenceAdapter crossReferencer = CrossReferenceAdapter
                    .getCrossReferenceAdapter(pType.eResource()
                            .getResourceSet());
            Set<Generalization> generalizations = crossReferencer
                    .getInverseReferencers(pType, UMLPackage.eINSTANCE
                            .getGeneralization_General(), null);

            for (Generalization gen : generalizations) {
                // It can be null if it is already removed
                if (gen.getSpecific() != null && gen.eResource() != null) {
                    objsToDestroy.add(gen);
                }
            }

            // Destroy all stereotype applications
            objsToDestroy.addAll(pType.getStereotypeApplications());

            if (!objsToDestroy.isEmpty()) {
                return req.getDestroyDependentsCommand(objsToDestroy);
            }
        }

        return super.getDestroyDependentsCommand(req);
    }
}
