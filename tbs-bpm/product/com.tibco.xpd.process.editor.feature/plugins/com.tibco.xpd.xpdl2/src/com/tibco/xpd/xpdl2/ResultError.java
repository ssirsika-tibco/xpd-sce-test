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
 * A representation of the model object '<em><b>Result Error</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.ResultError#getErrorCode <em>Error Code</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getResultError()
 * @model extendedMetaData="name='ResultError_._type' kind='elementOnly'"
 * @generated
 */
public interface ResultError extends OtherAttributesContainer,
        OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Error Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Error Code</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Error Code</em>' attribute.
     * @see #setErrorCode(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getResultError_ErrorCode()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='ErrorCode'"
     * @generated
     */
    String getErrorCode();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ResultError#getErrorCode <em>Error Code</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Error Code</em>' attribute.
     * @see #getErrorCode()
     * @generated
     */
    void setErrorCode(String value);

} // ResultError
