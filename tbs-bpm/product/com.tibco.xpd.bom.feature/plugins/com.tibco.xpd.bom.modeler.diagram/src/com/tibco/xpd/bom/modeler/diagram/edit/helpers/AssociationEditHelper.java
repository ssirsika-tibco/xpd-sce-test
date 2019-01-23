/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.helpers;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.modeler.diagram.edit.commands.AssociationCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.edit.commands.AssociationReorientCommand;

/**
 * @generated
 */
public class AssociationEditHelper extends UMLBaseEditHelper {

    /**
     * Additional logic for removing all the association ends from the end
     * classes as they are dependent elements.
     */
    @Override
    protected ICommand getDestroyDependentsCommand(DestroyDependentsRequest req) {
        Association assoc = (Association) req.getElementToDestroy();
        EList<Property> ends = assoc.getMemberEnds();
        EList<EObject> objsToDestroy = new BasicEList<EObject>();

        // Destroy the property ends
        for (Property prop : ends) {
            if (prop.getOwner() != assoc) {
                objsToDestroy.add(prop);
            }
        }

        // Destroy the association's stereotype applications
        objsToDestroy.addAll(assoc.getStereotypeApplications());

        if (!objsToDestroy.isEmpty()) {
            return req.getDestroyDependentsCommand(objsToDestroy);
        }

        return super.getDestroyDependentsCommand(req);
    }

    @Override
    protected ICommand getCreateRelationshipCommand(
            CreateRelationshipRequest req) {
        return new AssociationCreateCommand(req, req.getSource(), req
                .getTarget());
    }

    @Override
    protected ICommand getReorientRelationshipCommand(
            ReorientRelationshipRequest req) {
        return new AssociationReorientCommand(req);
    }

}
