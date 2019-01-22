/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.helpers;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.uml2.uml.AssociationClass;

/**
 * @generated
 */
public class AssociationClassEditHelper extends UMLBaseEditHelper {

    @Override
    protected ICommand getDestroyDependentsCommand(DestroyDependentsRequest req) {
        /*
         * Destroy any stereotypes applied to this Assocation Class
         */
        if (req.getElementToDestroy() instanceof AssociationClass) {
            EList<EObject> appls = ((AssociationClass) req
                    .getElementToDestroy()).getStereotypeApplications();
            if (!appls.isEmpty()) {
                return req.getDestroyDependentsCommand(appls);
            }

        }

        return super.getDestroyDependentsCommand(req);
    }
}
