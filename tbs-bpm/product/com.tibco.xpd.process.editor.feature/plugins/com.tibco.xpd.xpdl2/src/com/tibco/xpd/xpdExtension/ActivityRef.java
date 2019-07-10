/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdl2.Activity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Activity Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.ActivityRef#getIdRef <em>Id Ref</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getActivityRef()
 * @model extendedMetaData="name='ActivityRef' kind='elementOnly'"
 * @generated
 */
public interface ActivityRef extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Id Ref</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id Ref</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Id Ref</em>' attribute.
     * @see #setIdRef(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getActivityRef_IdRef()
     * @model dataType="com.tibco.xpd.xpdl2.IdReferenceString" required="true"
     *        extendedMetaData="kind='attribute' name='IdRef' namespace='##targetNamespace'"
     * @generated
     */
    String getIdRef();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ActivityRef#getIdRef <em>Id Ref</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id Ref</em>' attribute.
     * @see #getIdRef()
     * @generated
     */
    void setIdRef(String value);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    Activity getActivity();

} // ActivityRef
