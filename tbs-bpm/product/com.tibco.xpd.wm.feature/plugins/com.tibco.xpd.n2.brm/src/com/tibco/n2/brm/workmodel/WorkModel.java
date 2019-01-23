/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.workmodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Array of work models.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.workmodel.WorkModel#getWorkModel <em>Work Model</em>}</li>
 *   <li>{@link com.tibco.n2.brm.workmodel.WorkModel#getOrgModelVersion <em>Org Model Version</em>}</li>
 *   <li>{@link com.tibco.n2.brm.workmodel.WorkModel#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.workmodel.WorkmodelPackage#getWorkModel()
 * @model extendedMetaData="name='WorkModel' kind='elementOnly'"
 * @generated
 */
public interface WorkModel extends EObject {
    /**
     * Returns the value of the '<em><b>Work Model</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.WorkModel}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Model</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Model</em>' containment reference list.
     * @see com.tibco.n2.brm.workmodel.WorkmodelPackage#getWorkModel_WorkModel()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='WorkModel' namespace='##targetNamespace'"
     * @generated
     */
    EList<com.tibco.n2.brm.api.WorkModel> getWorkModel();

    /**
     * Returns the value of the '<em><b>Org Model Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Org Model Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Org Model Version</em>' attribute.
     * @see #isSetOrgModelVersion()
     * @see #unsetOrgModelVersion()
     * @see #setOrgModelVersion(int)
     * @see com.tibco.n2.brm.workmodel.WorkmodelPackage#getWorkModel_OrgModelVersion()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='orgModelVersion'"
     * @generated
     */
    int getOrgModelVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.workmodel.WorkModel#getOrgModelVersion <em>Org Model Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Org Model Version</em>' attribute.
     * @see #isSetOrgModelVersion()
     * @see #unsetOrgModelVersion()
     * @see #getOrgModelVersion()
     * @generated
     */
    void setOrgModelVersion(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.workmodel.WorkModel#getOrgModelVersion <em>Org Model Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetOrgModelVersion()
     * @see #getOrgModelVersion()
     * @see #setOrgModelVersion(int)
     * @generated
     */
    void unsetOrgModelVersion();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.workmodel.WorkModel#getOrgModelVersion <em>Org Model Version</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Org Model Version</em>' attribute is set.
     * @see #unsetOrgModelVersion()
     * @see #getOrgModelVersion()
     * @see #setOrgModelVersion(int)
     * @generated
     */
    boolean isSetOrgModelVersion();

    /**
     * Returns the value of the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Version</em>' attribute.
     * @see #setVersion(String)
     * @see com.tibco.n2.brm.workmodel.WorkmodelPackage#getWorkModel_Version()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='version'"
     * @generated
     */
    String getVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.workmodel.WorkModel#getVersion <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Version</em>' attribute.
     * @see #getVersion()
     * @generated
     */
    void setVersion(String value);

} // WorkModel
