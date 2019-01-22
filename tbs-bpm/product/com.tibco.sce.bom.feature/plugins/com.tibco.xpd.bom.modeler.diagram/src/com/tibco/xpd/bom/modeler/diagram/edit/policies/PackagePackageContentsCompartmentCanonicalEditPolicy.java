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

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramUpdater;
import com.tibco.xpd.bom.modeler.diagram.part.UMLNodeDescriptor;

/**
 * IMPORTANT. This class is no longer auto-generated since the
 * nested editparts have been removed. However, it is need for
 * the CANONICAL_ROLE editpolicy installed 
 * PackagePackageContentsCompartmentEditPart.
 */


public class PackagePackageContentsCompartmentCanonicalEditPolicy extends
        CanvasPackageCanonicalEditPolicy {

    /**
     * @generated
     */
    Set myFeaturesToSynchronize;

    /**
     * @generated NOT
     */
    protected List getSemanticChildrenList() {
        View viewObject = (View) getHost().getModel();
        List result = new LinkedList();
        for (Iterator it = UMLDiagramUpdater
                .getPackagePackageContentsCompartment_5001SemanticChildren(
                        viewObject).iterator(); it.hasNext();) {
            result.add(((UMLNodeDescriptor) it.next()).getModelElement());
        }
        return result;
    }

    /**
     * @generated
     */
    protected boolean isOrphaned(Collection semanticChildren, final View view) {
        // int visualID = UMLVisualIDRegistry.getVisualID(view);
        // switch (visualID) {
        // case NestedPackageEditPart.VISUAL_ID:
        // case NestedClassEditPart.VISUAL_ID:
        // return !semanticChildren.contains(view.getElement())
        // || visualID != UMLVisualIDRegistry.getNodeVisualID(
        // (View) getHost().getModel(), view.getElement());
        // }
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
                    .getPackage_PackagedElement());
        }
        return myFeaturesToSynchronize;
    }

}
