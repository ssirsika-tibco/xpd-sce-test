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
 * A representation of the model object '<em><b>Privilege</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of a privilege.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.Privilege#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.Privilege#getQualifier <em>Qualifier</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getPrivilege()
 * @model extendedMetaData="name='Privilege' kind='empty'"
 * @generated
 */
public interface Privilege extends EObject {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Name of the required privilege (i.e. what BRM will look for in DE).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getPrivilege_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.Privilege#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Qualifier</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * (Optional) qualifier for this privilege.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Qualifier</em>' attribute.
     * @see #setQualifier(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getPrivilege_Qualifier()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='qualifier'"
     * @generated
     */
    String getQualifier();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.Privilege#getQualifier <em>Qualifier</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Qualifier</em>' attribute.
     * @see #getQualifier()
     * @generated
     */
    void setQualifier(String value);

} // Privilege
