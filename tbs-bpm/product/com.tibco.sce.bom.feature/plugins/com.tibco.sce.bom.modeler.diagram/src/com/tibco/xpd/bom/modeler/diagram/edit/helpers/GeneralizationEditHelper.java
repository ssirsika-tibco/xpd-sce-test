/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.helpers;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.uml2.uml.Generalization;

import com.tibco.xpd.bom.modeler.diagram.edit.commands.GeneralizationCreateCommand;

/**
 * @generated
 */
public class GeneralizationEditHelper extends UMLBaseEditHelper {

    @Override
    protected ICommand getCreateRelationshipCommand(
            CreateRelationshipRequest req) {
        return new GeneralizationCreateCommand(req, req.getSource(), req
                .getTarget());
    }

    @Override
    protected ICommand getDestroyDependentsCommand(DestroyDependentsRequest req) {

        if (req.getElementToDestroy() instanceof Generalization) {
            // Destroy all stereotype applications
            EList<EObject> applications = ((Generalization) req
                    .getElementToDestroy()).getStereotypeApplications();

            if (!applications.isEmpty()) {
                return req.getDestroyDependentsCommand(applications);
            }
        }

        return super.getDestroyDependentsCommand(req);
    }
}
