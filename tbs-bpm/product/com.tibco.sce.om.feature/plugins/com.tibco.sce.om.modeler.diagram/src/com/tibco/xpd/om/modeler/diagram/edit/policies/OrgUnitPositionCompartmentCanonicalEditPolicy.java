package com.tibco.xpd.om.modeler.diagram.edit.policies;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.modeler.diagram.edit.parts.PositionEditPart;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramUpdater;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelNodeDescriptor;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;

/**
 * @generated
 */
public class OrgUnitPositionCompartmentCanonicalEditPolicy extends
        CanonicalEditPolicy {

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
        for (Iterator it = OrganizationModelDiagramUpdater
                .getOrgUnitPositionCompartment_5002SemanticChildren(viewObject)
                .iterator(); it.hasNext();) {
            result.add(((OrganizationModelNodeDescriptor) it.next())
                    .getModelElement());
        }
        return result;
    }

    /**
     * @generated
     */
    protected boolean isOrphaned(Collection semanticChildren, final View view) {
        int visualID = OrganizationModelVisualIDRegistry.getVisualID(view);
        switch (visualID) {
        case PositionEditPart.VISUAL_ID:
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
            myFeaturesToSynchronize.add(OMPackage.eINSTANCE
                    .getOrgUnit_Positions());
        }
        return myFeaturesToSynchronize;
    }

}
