/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.AsyncResumeWorkItemType;
import com.tibco.n2.brm.api.AsyncWorkItemDetails;
import com.tibco.n2.brm.api.N2BRMPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Async Resume Work Item Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.AsyncResumeWorkItemTypeImpl#getMessageDetails <em>Message Details</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AsyncResumeWorkItemTypeImpl extends EObjectImpl implements AsyncResumeWorkItemType {
    /**
     * The cached value of the '{@link #getMessageDetails() <em>Message Details</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMessageDetails()
     * @generated
     * @ordered
     */
    protected AsyncWorkItemDetails messageDetails;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AsyncResumeWorkItemTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.ASYNC_RESUME_WORK_ITEM_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncWorkItemDetails getMessageDetails() {
        return messageDetails;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetMessageDetails(AsyncWorkItemDetails newMessageDetails, NotificationChain msgs) {
        AsyncWorkItemDetails oldMessageDetails = messageDetails;
        messageDetails = newMessageDetails;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_RESUME_WORK_ITEM_TYPE__MESSAGE_DETAILS, oldMessageDetails, newMessageDetails);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMessageDetails(AsyncWorkItemDetails newMessageDetails) {
        if (newMessageDetails != messageDetails) {
            NotificationChain msgs = null;
            if (messageDetails != null)
                msgs = ((InternalEObject)messageDetails).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_RESUME_WORK_ITEM_TYPE__MESSAGE_DETAILS, null, msgs);
            if (newMessageDetails != null)
                msgs = ((InternalEObject)newMessageDetails).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_RESUME_WORK_ITEM_TYPE__MESSAGE_DETAILS, null, msgs);
            msgs = basicSetMessageDetails(newMessageDetails, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_RESUME_WORK_ITEM_TYPE__MESSAGE_DETAILS, newMessageDetails, newMessageDetails));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.ASYNC_RESUME_WORK_ITEM_TYPE__MESSAGE_DETAILS:
                return basicSetMessageDetails(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.ASYNC_RESUME_WORK_ITEM_TYPE__MESSAGE_DETAILS:
                return getMessageDetails();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case N2BRMPackage.ASYNC_RESUME_WORK_ITEM_TYPE__MESSAGE_DETAILS:
                setMessageDetails((AsyncWorkItemDetails)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case N2BRMPackage.ASYNC_RESUME_WORK_ITEM_TYPE__MESSAGE_DETAILS:
                setMessageDetails((AsyncWorkItemDetails)null);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case N2BRMPackage.ASYNC_RESUME_WORK_ITEM_TYPE__MESSAGE_DETAILS:
                return messageDetails != null;
        }
        return super.eIsSet(featureID);
    }

} //AsyncResumeWorkItemTypeImpl
