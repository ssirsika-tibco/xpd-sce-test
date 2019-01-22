/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.helpers;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.bom.modeler.diagram.edit.commands.ClassCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.edit.commands.EnumerationCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.edit.commands.PackageCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.edit.commands.PrimitiveTypeCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;

/**
 * @generated
 */
public class PackageEditHelper extends UMLBaseEditHelper {

    /**
     * Create creation command for all possible children. Note that using it
     * here require change to canonical edit policy.
     */
    protected ICommand getCreateCommand(CreateElementRequest req) {
        IElementType type = req.getElementType();
        if (UMLElementTypes.Package_79.equals(type)
                || UMLElementTypes.Package_1001.equals(type)) {
            return new PackageCreateCommand(req);
        }
        if (UMLElementTypes.Class_1002.equals(type)) {
            return new ClassCreateCommand(req);
        }
        if (UMLElementTypes.PrimitiveType_1003.equals(type)) {
            return new PrimitiveTypeCreateCommand(req);
        }
        if (UMLElementTypes.Enumeration_1004.equals(type)) {
            return new EnumerationCreateCommand(req);
        }

        return super.getCreateCommand(req);
    }

    @Override
    protected ICommand getDestroyDependentsCommand(DestroyDependentsRequest req) {
        if (req.getElementToDestroy() instanceof Package) {
            // Destroy all stereotype applications
            EList<EObject> applications = ((Package) req.getElementToDestroy())
                    .getStereotypeApplications();

            if (!applications.isEmpty()) {
                return req.getDestroyDependentsCommand(applications);
            }
        }
        return super.getDestroyDependentsCommand(req);
    }
}
