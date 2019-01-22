/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.AsyncCancelWorkItemResponseType;
import com.tibco.n2.brm.api.AsyncWorkItemDetails;
import com.tibco.n2.brm.api.N2BRMPackage;

import com.tibco.n2.common.api.exception.ErrorLine;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Async Cancel Work Item Response Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.AsyncCancelWorkItemResponseTypeImpl#getMessageDetails <em>Message Details</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.AsyncCancelWorkItemResponseTypeImpl#getErrorMessage <em>Error Message</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AsyncCancelWorkItemResponseTypeImpl extends EObjectImpl implements AsyncCancelWorkItemResponseType {
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
     * The cached value of the '{@link #getErrorMessage() <em>Error Message</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getErrorMessage()
     * @generated
     * @ordered
     */
    protected ErrorLine errorMessage;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AsyncCancelWorkItemResponseTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE;
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS, oldMessageDetails, newMessageDetails);
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
                msgs = ((InternalEObject)messageDetails).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS, null, msgs);
            if (newMessageDetails != null)
                msgs = ((InternalEObject)newMessageDetails).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS, null, msgs);
            msgs = basicSetMessageDetails(newMessageDetails, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS, newMessageDetails, newMessageDetails));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ErrorLine getErrorMessage() {
        return errorMessage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetErrorMessage(ErrorLine newErrorMessage, NotificationChain msgs) {
        ErrorLine oldErrorMessage = errorMessage;
        errorMessage = newErrorMessage;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE, oldErrorMessage, newErrorMessage);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setErrorMessage(ErrorLine newErrorMessage) {
        if (newErrorMessage != errorMessage) {
            NotificationChain msgs = null;
            if (errorMessage != null)
                msgs = ((InternalEObject)errorMessage).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE, null, msgs);
            if (newErrorMessage != null)
                msgs = ((InternalEObject)newErrorMessage).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE, null, msgs);
            msgs = basicSetErrorMessage(newErrorMessage, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE, newErrorMessage, newErrorMessage));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS:
                return basicSetMessageDetails(null, msgs);
            case N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE:
                return basicSetErrorMessage(null, msgs);
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
            case N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS:
                return getMessageDetails();
            case N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE:
                return getErrorMessage();
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
            case N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS:
                setMessageDetails((AsyncWorkItemDetails)newValue);
                return;
            case N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE:
                setErrorMessage((ErrorLine)newValue);
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
            case N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS:
                setMessageDetails((AsyncWorkItemDetails)null);
                return;
            case N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE:
                setErrorMessage((ErrorLine)null);
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
            case N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS:
                return messageDetails != null;
            case N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE:
                return errorMessage != null;
        }
        return super.eIsSet(featureID);
    }

} //AsyncCancelWorkItemResponseTypeImpl
