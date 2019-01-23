/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import com.tibco.n2.common.organisation.api.XmlModelEntityId;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work List View Edit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of a work list view used for editing (i.e. create and delete operations).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkListViewEdit#getAuthors <em>Authors</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkListViewEdit#getUsers <em>Users</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkListViewEdit#getCustomData <em>Custom Data</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListViewEdit()
 * @model extendedMetaData="name='WorkListViewEdit' kind='elementOnly'"
 * @generated
 */
public interface WorkListViewEdit extends WorkListViewCommon {
    /**
     * Returns the value of the '<em><b>Authors</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.common.organisation.api.XmlModelEntityId}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Array of XmlModelEntityId objects for entities (GROUPS, POSITIONS, ORGUNITS and RESOURCES) who are allowed to edit the view.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Authors</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListViewEdit_Authors()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='authors'"
     * @generated
     */
    EList<XmlModelEntityId> getAuthors();

    /**
     * Returns the value of the '<em><b>Users</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.common.organisation.api.XmlModelEntityId}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Array of XmlModelEntityId objects for resources who are allowed to use the view.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Users</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListViewEdit_Users()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='users'"
     * @generated
     */
    EList<XmlModelEntityId> getUsers();

    /**
     * Returns the value of the '<em><b>Custom Data</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * A string attribute the caller can use to provide and custom data they wish to store with the work list view.  BRM will not look at or use this attribute value but will just store it with the work view definition.   If storing XML data the value should be wrapped in a CDATA section.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Custom Data</em>' attribute.
     * @see #setCustomData(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListViewEdit_CustomData()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='customData'"
     * @generated
     */
    String getCustomData();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkListViewEdit#getCustomData <em>Custom Data</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Custom Data</em>' attribute.
     * @see #getCustomData()
     * @generated
     */
    void setCustomData(String value);

} // WorkListViewEdit
