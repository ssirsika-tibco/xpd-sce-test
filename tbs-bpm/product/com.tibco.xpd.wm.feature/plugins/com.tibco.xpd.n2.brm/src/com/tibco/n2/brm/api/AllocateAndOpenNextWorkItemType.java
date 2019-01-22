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
 * A representation of the model object '<em><b>Allocate And Open Next Work Item Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemType#getResource <em>Resource</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemType#getWorkListViewID <em>Work List View ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getAllocateAndOpenNextWorkItemType()
 * @model extendedMetaData="name='allocateAndOpenNextWorkItem_._type' kind='elementOnly'"
 * @generated
 */
public interface AllocateAndOpenNextWorkItemType extends EObject {
    /**
     * Returns the value of the '<em><b>Resource</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Name of the resource to whom the next work item should be allocated.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Resource</em>' attribute.
     * @see #setResource(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAllocateAndOpenNextWorkItemType_Resource()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='resource'"
     * @generated
     */
    String getResource();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemType#getResource <em>Resource</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Resource</em>' attribute.
     * @see #getResource()
     * @generated
     */
    void setResource(String value);

    /**
     * Returns the value of the '<em><b>Work List View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The ID of an optional work list view to apply before selecting the next item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work List View ID</em>' attribute.
     * @see #isSetWorkListViewID()
     * @see #unsetWorkListViewID()
     * @see #setWorkListViewID(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAllocateAndOpenNextWorkItemType_WorkListViewID()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long"
     *        extendedMetaData="kind='element' name='workListViewID'"
     * @generated
     */
    long getWorkListViewID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemType#getWorkListViewID <em>Work List View ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work List View ID</em>' attribute.
     * @see #isSetWorkListViewID()
     * @see #unsetWorkListViewID()
     * @see #getWorkListViewID()
     * @generated
     */
    void setWorkListViewID(long value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemType#getWorkListViewID <em>Work List View ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetWorkListViewID()
     * @see #getWorkListViewID()
     * @see #setWorkListViewID(long)
     * @generated
     */
    void unsetWorkListViewID();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemType#getWorkListViewID <em>Work List View ID</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Work List View ID</em>' attribute is set.
     * @see #unsetWorkListViewID()
     * @see #getWorkListViewID()
     * @see #setWorkListViewID(long)
     * @generated
     */
    boolean isSetWorkListViewID();

} // AllocateAndOpenNextWorkItemType
