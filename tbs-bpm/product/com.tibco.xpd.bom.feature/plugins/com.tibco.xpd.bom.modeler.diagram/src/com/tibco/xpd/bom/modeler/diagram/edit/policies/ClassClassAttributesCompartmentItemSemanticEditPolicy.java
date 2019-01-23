/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.modeler.diagram.edit.commands.PropertyCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;

/**
 * @generated
 */
public class ClassClassAttributesCompartmentItemSemanticEditPolicy extends
        UMLBaseItemSemanticEditPolicy {

    /**
     * Mark the request with the property that the edit helper will not create
     * the another create command.
     */

    protected Command getCreateCommand(CreateElementRequest req) {
        req.setParameter(IEditCommandRequest.REPLACE_DEFAULT_COMMAND,
                Boolean.TRUE);
        return getCreateCommandGen(req);
    }

    /**
     * @generated
     */
    protected Command getCreateCommandGen(CreateElementRequest req) {
        if (UMLElementTypes.Property_2001 == req.getElementType()) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(UMLPackage.eINSTANCE
                        .getStructuredClassifier_OwnedAttribute());
            }

            // Get any stereo type of the thing that is being added
            Stereotype stereo =
                    (Stereotype) req
                            .getParameter(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE);

            // Check the stereotype to see if it is an auto case identifier
            // of case state as these will need to be added to the tool
            // selection
            Stereotype autoCIDStereo =
                    GlobalDataProfileManager.getInstance()
                            .getStereotype(StereotypeKind.AUTO_CASE_IDENTIFIER);
            Stereotype caseStateStereo =
                    GlobalDataProfileManager.getInstance()
                            .getStereotype(StereotypeKind.CASE_STATE);
            if ((autoCIDStereo == stereo) || (caseStateStereo == stereo)) {
                // Dealing with a case identifier, so make sure that it can only
                // be added to a case class
                EObject container = req.getContainer();
                if ((container != null) && (container instanceof Class)) {
                    if (!GlobalDataProfileManager.getInstance()
                            .isCase((Class) container)) {
                        // Not a case class, so do not run our add as you can
                        // only add a case identifier to a case class
                        return super.getCreateCommand(req);
                    }
                }
            }

            return getGEFWrapper(new PropertyCreateCommand(req));
        }
        return super.getCreateCommand(req);
    }

}
