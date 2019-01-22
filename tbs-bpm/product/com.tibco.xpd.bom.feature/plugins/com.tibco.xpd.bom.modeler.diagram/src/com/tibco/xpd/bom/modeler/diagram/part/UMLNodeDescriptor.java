/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.part;

import org.eclipse.emf.ecore.EObject;

/**
 * @generated
 */
public class UMLNodeDescriptor {

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
    public UMLNodeDescriptor(EObject modelElement, int visualID) {
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
            myType = UMLVisualIDRegistry.getType(getVisualID());
        }
        return myType;
    }

}
