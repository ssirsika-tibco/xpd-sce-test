/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.helpers;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Operation;

/**
 * @generated
 */
public class EnumerationLiteralEditHelper extends UMLBaseEditHelper {
    @Override
    protected ICommand getDestroyDependentsCommand(DestroyDependentsRequest req) {

        if (req.getElementToDestroy() instanceof EnumerationLiteral) {
            EnumerationLiteral enLit = (EnumerationLiteral) req
                    .getElementToDestroy();

            EList<EObject> objsToDestroy = new BasicEList<EObject>();
            objsToDestroy.addAll(enLit.getStereotypeApplications());

            if (!objsToDestroy.isEmpty()) {
                return req.getDestroyDependentsCommand(objsToDestroy);
            }
        }

        return super.getDestroyDependentsCommand(req);
    }

}
