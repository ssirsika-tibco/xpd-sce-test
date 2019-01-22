/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.policies;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassOperationEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramUpdater;
import com.tibco.xpd.bom.modeler.diagram.part.UMLNodeDescriptor;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;

/**
 * @generated
 */
public class AssociationClassClassifierOperationsCompartmentCanonicalEditPolicy
        extends CanonicalEditPolicy {

    /**
     * @generated
     */
    Set myFeaturesToSynchronize;

    /**
     * @generated
     */
    protected List getSemanticChildrenList() {
        View viewObject = (View) getHost().getModel();
        List result = new LinkedList();
        for (Iterator it = UMLDiagramUpdater
                .getAssociationClassOperationsCompartment_5006SemanticChildren(
                        viewObject).iterator(); it.hasNext();) {
            result.add(((UMLNodeDescriptor) it.next()).getModelElement());
        }
        return result;
    }

    /**
     * @generated
     */
    protected boolean isOrphaned(Collection semanticChildren, final View view) {
        int visualID = UMLVisualIDRegistry.getVisualID(view);
        switch (visualID) {
        case AssociationClassOperationEditPart.VISUAL_ID:
            if (!semanticChildren.contains(view.getElement())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @generated
     */
    protected String getDefaultFactoryHint() {
        return null;
    }

    /**
     * @generated
     */
    protected Set getFeaturesToSynchronize() {
        if (myFeaturesToSynchronize == null) {
            myFeaturesToSynchronize = new HashSet();
            myFeaturesToSynchronize.add(UMLPackage.eINSTANCE
                    .getClass_OwnedOperation());
        }
        return myFeaturesToSynchronize;
    }

}
