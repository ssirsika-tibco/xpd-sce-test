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
 * A representation of the model object '<em><b>Get Offer Set Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetOfferSetType#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetOfferSetType#getApiVersion <em>Api Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetOfferSetType()
 * @model extendedMetaData="name='getOfferSet_._type' kind='elementOnly'"
 * @generated
 */
public interface GetOfferSetType extends EObject {
    /**
     * Returns the value of the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the work item for which an offer set is required.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Item ID</em>' containment reference.
     * @see #setWorkItemID(ManagedObjectID)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetOfferSetType_WorkItemID()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workItemID'"
     * @generated
     */
    ManagedObjectID getWorkItemID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetOfferSetType#getWorkItemID <em>Work Item ID</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Item ID</em>' containment reference.
     * @see #getWorkItemID()
     * @generated
     */
    void setWorkItemID(ManagedObjectID value);

    /**
     * Returns the value of the '<em><b>Api Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Specify the version of the return data set.   If omitted or set to 1 this API will return an array of GUIDs as it always has.  If specified as > 1 it will return an array of XmlModelEntityIds.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Api Version</em>' attribute.
     * @see #isSetApiVersion()
     * @see #unsetApiVersion()
     * @see #setApiVersion(int)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetOfferSetType_ApiVersion()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='element' name='apiVersion'"
     * @generated
     */
    int getApiVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetOfferSetType#getApiVersion <em>Api Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Api Version</em>' attribute.
     * @see #isSetApiVersion()
     * @see #unsetApiVersion()
     * @see #getApiVersion()
     * @generated
     */
    void setApiVersion(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetOfferSetType#getApiVersion <em>Api Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetApiVersion()
     * @see #getApiVersion()
     * @see #setApiVersion(int)
     * @generated
     */
    void unsetApiVersion();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetOfferSetType#getApiVersion <em>Api Version</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Api Version</em>' attribute is set.
     * @see #unsetApiVersion()
     * @see #getApiVersion()
     * @see #setApiVersion(int)
     * @generated
     */
    boolean isSetApiVersion();

} // GetOfferSetType
