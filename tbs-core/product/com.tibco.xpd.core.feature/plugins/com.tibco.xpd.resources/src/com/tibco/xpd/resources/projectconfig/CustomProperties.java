/**
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Custom Properties</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Customer name value pair properties
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.CustomProperties#getCustomProperty <em>Custom Property</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getCustomProperties()
 * @model
 * @generated
 */
public interface CustomProperties extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Custom Property</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.resources.projectconfig.CustomProperty}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Custom Property</em>' containment reference list.
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getCustomProperties_CustomProperty()
     * @model containment="true" required="true"
     * @generated
     */
    EList<CustomProperty> getCustomProperty();

} // CustomProperties
