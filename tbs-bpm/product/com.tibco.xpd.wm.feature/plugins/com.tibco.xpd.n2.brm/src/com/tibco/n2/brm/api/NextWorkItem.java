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
 * A representation of the model object '<em><b>Next Work Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of the next work item that is of the same piled type as the work item just processed.
 * 
 * (BRM uses this definition with the Piling pattern to identify the next work item to Work Presentation.)
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.NextWorkItem#getNextItem <em>Next Item</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getNextWorkItem()
 * @model extendedMetaData="name='NextWorkItem' kind='elementOnly'"
 * @generated
 */
public interface NextWorkItem extends EObject {
    /**
     * Returns the value of the '<em><b>Next Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Unique ID of the next work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Next Item</em>' containment reference.
     * @see #setNextItem(ManagedObjectID)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getNextWorkItem_NextItem()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='nextItem'"
     * @generated
     */
    ManagedObjectID getNextItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.NextWorkItem#getNextItem <em>Next Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Next Item</em>' containment reference.
     * @see #getNextItem()
     * @generated
     */
    void setNextItem(ManagedObjectID value);

} // NextWorkItem
