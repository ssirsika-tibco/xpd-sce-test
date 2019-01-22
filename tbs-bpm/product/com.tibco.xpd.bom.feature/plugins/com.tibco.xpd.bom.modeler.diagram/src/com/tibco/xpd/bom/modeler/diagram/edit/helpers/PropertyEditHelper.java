/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.helpers;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;

/**
 * @generated NOT
 */
public class PropertyEditHelper extends UMLBaseEditHelper {
    @Override
    protected ICommand getDestroyDependentsCommand(DestroyDependentsRequest req) {

        if (req.getElementToDestroy() instanceof Property) {
            Property prop = (Property) req.getElementToDestroy();
            Association assoc = prop.getAssociation();

            EList<EObject> objsToDestroy = new BasicEList<EObject>();
            objsToDestroy.addAll(prop.getStereotypeApplications());

            if (assoc != null) {
                // Destroy the association and the property's stereotype
                // applications
                objsToDestroy.add(assoc);
            }
            if (!objsToDestroy.isEmpty()) {
                return req.getDestroyDependentsCommand(objsToDestroy);
            }
        }

        return super.getDestroyDependentsCommand(req);
    }
}
