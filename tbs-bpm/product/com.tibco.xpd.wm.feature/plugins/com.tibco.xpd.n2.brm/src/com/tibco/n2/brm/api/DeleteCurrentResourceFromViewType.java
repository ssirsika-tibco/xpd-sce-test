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
 * A representation of the model object '<em><b>Delete Current Resource From View Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.DeleteCurrentResourceFromViewType#getWorkListViewID <em>Work List View ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getDeleteCurrentResourceFromViewType()
 * @model extendedMetaData="name='deleteCurrentResourceFromView_._type' kind='empty'"
 * @generated
 */
public interface DeleteCurrentResourceFromViewType extends EObject {
    /**
     * Returns the value of the '<em><b>Work List View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Position in the work view list from which to start this page. (The list is zero-based. To start at the first item, specify 0.)
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work List View ID</em>' attribute.
     * @see #isSetWorkListViewID()
     * @see #unsetWorkListViewID()
     * @see #setWorkListViewID(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDeleteCurrentResourceFromViewType_WorkListViewID()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='attribute' name='workListViewID'"
     * @generated
     */
    long getWorkListViewID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DeleteCurrentResourceFromViewType#getWorkListViewID <em>Work List View ID</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.DeleteCurrentResourceFromViewType#getWorkListViewID <em>Work List View ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetWorkListViewID()
     * @see #getWorkListViewID()
     * @see #setWorkListViewID(long)
     * @generated
     */
    void unsetWorkListViewID();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.DeleteCurrentResourceFromViewType#getWorkListViewID <em>Work List View ID</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Work List View ID</em>' attribute is set.
     * @see #unsetWorkListViewID()
     * @see #getWorkListViewID()
     * @see #setWorkListViewID(long)
     * @generated
     */
    boolean isSetWorkListViewID();

} // DeleteCurrentResourceFromViewType
