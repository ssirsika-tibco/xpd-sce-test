/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Get Work Model Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkModelType#getWorkModelID <em>Work Model ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkModelType()
 * @model extendedMetaData="name='getWorkModel_._type' kind='elementOnly'"
 * @generated
 */
public interface GetWorkModelType extends EObject {
    /**
     * Returns the value of the '<em><b>Work Model ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Model ID</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Model ID</em>' attribute.
     * @see #setWorkModelID(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkModelType_WorkModelID()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='workModelID'"
     * @generated
     */
    String getWorkModelID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkModelType#getWorkModelID <em>Work Model ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Model ID</em>' attribute.
     * @see #getWorkModelID()
     * @generated
     */
    void setWorkModelID(String value);

} // GetWorkModelType
