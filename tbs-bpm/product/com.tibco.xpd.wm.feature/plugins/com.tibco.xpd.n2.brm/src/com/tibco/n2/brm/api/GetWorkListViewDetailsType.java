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
 * A representation of the model object '<em><b>Get Work List View Details Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkListViewDetailsType#getApiVersion <em>Api Version</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkListViewDetailsType#isLockView <em>Lock View</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkListViewDetailsType#getWorkListViewID <em>Work List View ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListViewDetailsType()
 * @model extendedMetaData="name='getWorkListViewDetails_._type' kind='empty'"
 * @generated
 */
public interface GetWorkListViewDetailsType extends EObject {
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
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListViewDetailsType_ApiVersion()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='apiVersion'"
     * @generated
     */
    int getApiVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkListViewDetailsType#getApiVersion <em>Api Version</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetWorkListViewDetailsType#getApiVersion <em>Api Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetApiVersion()
     * @see #getApiVersion()
     * @see #setApiVersion(int)
     * @generated
     */
    void unsetApiVersion();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetWorkListViewDetailsType#getApiVersion <em>Api Version</em>}' attribute is set.
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
     * Returns the value of the '<em><b>Lock View</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Should this work list view be locked for editing?  A lock record will be placed against this view for the calling resource ID.   This lock is only removed when the view is updated or an explicit unlock is called.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Lock View</em>' attribute.
     * @see #isSetLockView()
     * @see #unsetLockView()
     * @see #setLockView(boolean)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListViewDetailsType_LockView()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='lockView'"
     * @generated
     */
    boolean isLockView();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkListViewDetailsType#isLockView <em>Lock View</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Lock View</em>' attribute.
     * @see #isSetLockView()
     * @see #unsetLockView()
     * @see #isLockView()
     * @generated
     */
    void setLockView(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetWorkListViewDetailsType#isLockView <em>Lock View</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetLockView()
     * @see #isLockView()
     * @see #setLockView(boolean)
     * @generated
     */
    void unsetLockView();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetWorkListViewDetailsType#isLockView <em>Lock View</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Lock View</em>' attribute is set.
     * @see #unsetLockView()
     * @see #isLockView()
     * @see #setLockView(boolean)
     * @generated
     */
    boolean isSetLockView();

    /**
     * Returns the value of the '<em><b>Work List View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The unique ID of the work list view to delete.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work List View ID</em>' attribute.
     * @see #isSetWorkListViewID()
     * @see #unsetWorkListViewID()
     * @see #setWorkListViewID(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListViewDetailsType_WorkListViewID()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='attribute' name='workListViewID'"
     * @generated
     */
    long getWorkListViewID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkListViewDetailsType#getWorkListViewID <em>Work List View ID</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetWorkListViewDetailsType#getWorkListViewID <em>Work List View ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetWorkListViewID()
     * @see #getWorkListViewID()
     * @see #setWorkListViewID(long)
     * @generated
     */
    void unsetWorkListViewID();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetWorkListViewDetailsType#getWorkListViewID <em>Work List View ID</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Work List View ID</em>' attribute is set.
     * @see #unsetWorkListViewID()
     * @see #getWorkListViewID()
     * @see #setWorkListViewID(long)
     * @generated
     */
    boolean isSetWorkListViewID();

} // GetWorkListViewDetailsType
