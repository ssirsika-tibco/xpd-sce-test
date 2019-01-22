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
 * A representation of the model object '<em><b>Get Views For Resource Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetViewsForResourceType#getApiVersion <em>Api Version</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetViewsForResourceType#getNumberOfItems <em>Number Of Items</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetViewsForResourceType#getStartPosition <em>Start Position</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetViewsForResourceType()
 * @model extendedMetaData="name='getViewsForResource_._type' kind='empty'"
 * @generated
 */
public interface GetViewsForResourceType extends EObject {
    /**
     * Returns the value of the '<em><b>Api Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Specify the version of the return data. If omitted or set to 1 this API will return work views without specifying either the getAllocated boolean or the viewType for compatibilty with existing views.  If > 1 additional Work View data will be returned.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Api Version</em>' attribute.
     * @see #isSetApiVersion()
     * @see #unsetApiVersion()
     * @see #setApiVersion(int)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetViewsForResourceType_ApiVersion()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='apiVersion'"
     * @generated
     */
    int getApiVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetViewsForResourceType#getApiVersion <em>Api Version</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetViewsForResourceType#getApiVersion <em>Api Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetApiVersion()
     * @see #getApiVersion()
     * @see #setApiVersion(int)
     * @generated
     */
    void unsetApiVersion();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetViewsForResourceType#getApiVersion <em>Api Version</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Api Version</em>' attribute is set.
     * @see #unsetApiVersion()
     * @see #getApiVersion()
     * @see #setApiVersion(int)
     * @generated
     */
    boolean isSetApiVersion();

    /**
     * Returns the value of the '<em><b>Number Of Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Number of work items (in the work item list) to include in this page.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Number Of Items</em>' attribute.
     * @see #isSetNumberOfItems()
     * @see #unsetNumberOfItems()
     * @see #setNumberOfItems(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetViewsForResourceType_NumberOfItems()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='attribute' name='numberOfItems'"
     * @generated
     */
    long getNumberOfItems();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetViewsForResourceType#getNumberOfItems <em>Number Of Items</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Number Of Items</em>' attribute.
     * @see #isSetNumberOfItems()
     * @see #unsetNumberOfItems()
     * @see #getNumberOfItems()
     * @generated
     */
    void setNumberOfItems(long value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetViewsForResourceType#getNumberOfItems <em>Number Of Items</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetNumberOfItems()
     * @see #getNumberOfItems()
     * @see #setNumberOfItems(long)
     * @generated
     */
    void unsetNumberOfItems();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetViewsForResourceType#getNumberOfItems <em>Number Of Items</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Number Of Items</em>' attribute is set.
     * @see #unsetNumberOfItems()
     * @see #getNumberOfItems()
     * @see #setNumberOfItems(long)
     * @generated
     */
    boolean isSetNumberOfItems();

    /**
     * Returns the value of the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Position in the list of views from which to start this page. (The list is zero-based. To start at the first item, specify 0.)
     * <!-- end-model-doc -->
     * @return the value of the '<em>Start Position</em>' attribute.
     * @see #isSetStartPosition()
     * @see #unsetStartPosition()
     * @see #setStartPosition(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetViewsForResourceType_StartPosition()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='attribute' name='startPosition'"
     * @generated
     */
    long getStartPosition();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetViewsForResourceType#getStartPosition <em>Start Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Position</em>' attribute.
     * @see #isSetStartPosition()
     * @see #unsetStartPosition()
     * @see #getStartPosition()
     * @generated
     */
    void setStartPosition(long value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetViewsForResourceType#getStartPosition <em>Start Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetStartPosition()
     * @see #getStartPosition()
     * @see #setStartPosition(long)
     * @generated
     */
    void unsetStartPosition();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetViewsForResourceType#getStartPosition <em>Start Position</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Start Position</em>' attribute is set.
     * @see #unsetStartPosition()
     * @see #getStartPosition()
     * @see #setStartPosition(long)
     * @generated
     */
    boolean isSetStartPosition();

} // GetViewsForResourceType
