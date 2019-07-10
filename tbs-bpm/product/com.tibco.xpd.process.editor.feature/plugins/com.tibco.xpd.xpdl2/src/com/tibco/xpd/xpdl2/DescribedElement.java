/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Described Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.DescribedElement#getDescription <em>Description</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDescribedElement()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface DescribedElement extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Description</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Description</em>' containment reference.
     * @see #setDescription(Description)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDescribedElement_Description()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Description' namespace='##targetNamespace'"
     * @generated
     */
    Description getDescription();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.DescribedElement#getDescription <em>Description</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' containment reference.
     * @see #getDescription()
     * @generated
     */
    void setDescription(Description value);

} // DescribedElement