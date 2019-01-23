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
 * A representation of the model object '<em><b>Get Work Type Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkTypeType#getWorkTypeID <em>Work Type ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkTypeType()
 * @model extendedMetaData="name='getWorkType_._type' kind='elementOnly'"
 * @generated
 */
public interface GetWorkTypeType extends EObject {
    /**
     * Returns the value of the '<em><b>Work Type ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Type ID</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Type ID</em>' attribute.
     * @see #setWorkTypeID(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkTypeType_WorkTypeID()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='workTypeID'"
     * @generated
     */
    String getWorkTypeID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkTypeType#getWorkTypeID <em>Work Type ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Type ID</em>' attribute.
     * @see #getWorkTypeID()
     * @generated
     */
    void setWorkTypeID(String value);

} // GetWorkTypeType
