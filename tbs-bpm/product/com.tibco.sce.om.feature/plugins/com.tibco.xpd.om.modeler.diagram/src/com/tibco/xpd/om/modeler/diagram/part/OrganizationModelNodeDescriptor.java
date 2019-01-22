package com.tibco.xpd.om.modeler.diagram.part;

import org.eclipse.emf.ecore.EObject;

/**
 * @generated
 */
public class OrganizationModelNodeDescriptor {

    /**
     * @generated
     */
    private EObject myModelElement;

    /**
     * @generated
     */
    private int myVisualID;

    /**
     * @generated
     */
    private String myType;

    /**
     * @generated
     */
    public OrganizationModelNodeDescriptor(EObject modelElement, int visualID) {
        myModelElement = modelElement;
        myVisualID = visualID;
    }

    /**
     * @generated
     */
    public EObject getModelElement() {
        return myModelElement;
    }

    /**
     * @generated
     */
    public int getVisualID() {
        return myVisualID;
    }

    /**
     * @generated
     */
    public String getType() {
        if (myType == null) {
            myType = OrganizationModelVisualIDRegistry.getType(getVisualID());
        }
        return myType;
    }

}
