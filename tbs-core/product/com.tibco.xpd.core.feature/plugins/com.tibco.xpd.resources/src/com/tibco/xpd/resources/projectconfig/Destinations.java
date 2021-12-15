/**
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.resources.projectconfig;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Destinations</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Project destination environments
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.Destinations#getDestination <em>Destination</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getDestinations()
 * @model
 * @generated
 */
public interface Destinations extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Destination</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.resources.projectconfig.Destination}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Destination</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Destination</em>' containment reference list.
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getDestinations_Destination()
     * @model containment="true" required="true"
     * @generated
     */
    EList<Destination> getDestination();

} // Destinations
