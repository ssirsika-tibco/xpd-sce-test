/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.AllocationHistory;
import com.tibco.n2.brm.api.AsyncWorkItemDetails;
import com.tibco.n2.brm.api.ItemBody;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.OnNotificationType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>On Notification Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.OnNotificationTypeImpl#getMessageDetails <em>Message Details</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.OnNotificationTypeImpl#getItemBody <em>Item Body</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.OnNotificationTypeImpl#getAllocationHistory <em>Allocation History</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OnNotificationTypeImpl extends EObjectImpl implements OnNotificationType {
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
     * The cached value of the '{@link #getItemBody() <em>Item Body</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getItemBody()
     * @generated
     * @ordered
     */
    protected ItemBody itemBody;

    /**
     * The cached value of the '{@link #getAllocationHistory() <em>Allocation History</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAllocationHistory()
     * @generated
     * @ordered
     */
    protected EList<AllocationHistory> allocationHistory;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected OnNotificationTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.ON_NOTIFICATION_TYPE;
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.ON_NOTIFICATION_TYPE__MESSAGE_DETAILS, oldMessageDetails, newMessageDetails);
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
                msgs = ((InternalEObject)messageDetails).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ON_NOTIFICATION_TYPE__MESSAGE_DETAILS, null, msgs);
            if (newMessageDetails != null)
                msgs = ((InternalEObject)newMessageDetails).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ON_NOTIFICATION_TYPE__MESSAGE_DETAILS, null, msgs);
            msgs = basicSetMessageDetails(newMessageDetails, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ON_NOTIFICATION_TYPE__MESSAGE_DETAILS, newMessageDetails, newMessageDetails));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ItemBody getItemBody() {
        return itemBody;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetItemBody(ItemBody newItemBody, NotificationChain msgs) {
        ItemBody oldItemBody = itemBody;
        itemBody = newItemBody;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.ON_NOTIFICATION_TYPE__ITEM_BODY, oldItemBody, newItemBody);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setItemBody(ItemBody newItemBody) {
        if (newItemBody != itemBody) {
            NotificationChain msgs = null;
            if (itemBody != null)
                msgs = ((InternalEObject)itemBody).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ON_NOTIFICATION_TYPE__ITEM_BODY, null, msgs);
            if (newItemBody != null)
                msgs = ((InternalEObject)newItemBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ON_NOTIFICATION_TYPE__ITEM_BODY, null, msgs);
            msgs = basicSetItemBody(newItemBody, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ON_NOTIFICATION_TYPE__ITEM_BODY, newItemBody, newItemBody));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<AllocationHistory> getAllocationHistory() {
        if (allocationHistory == null) {
            allocationHistory = new EObjectContainmentEList<AllocationHistory>(AllocationHistory.class, this, N2BRMPackage.ON_NOTIFICATION_TYPE__ALLOCATION_HISTORY);
        }
        return allocationHistory;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.ON_NOTIFICATION_TYPE__MESSAGE_DETAILS:
                return basicSetMessageDetails(null, msgs);
            case N2BRMPackage.ON_NOTIFICATION_TYPE__ITEM_BODY:
                return basicSetItemBody(null, msgs);
            case N2BRMPackage.ON_NOTIFICATION_TYPE__ALLOCATION_HISTORY:
                return ((InternalEList<?>)getAllocationHistory()).basicRemove(otherEnd, msgs);
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
            case N2BRMPackage.ON_NOTIFICATION_TYPE__MESSAGE_DETAILS:
                return getMessageDetails();
            case N2BRMPackage.ON_NOTIFICATION_TYPE__ITEM_BODY:
                return getItemBody();
            case N2BRMPackage.ON_NOTIFICATION_TYPE__ALLOCATION_HISTORY:
                return getAllocationHistory();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case N2BRMPackage.ON_NOTIFICATION_TYPE__MESSAGE_DETAILS:
                setMessageDetails((AsyncWorkItemDetails)newValue);
                return;
            case N2BRMPackage.ON_NOTIFICATION_TYPE__ITEM_BODY:
                setItemBody((ItemBody)newValue);
                return;
            case N2BRMPackage.ON_NOTIFICATION_TYPE__ALLOCATION_HISTORY:
                getAllocationHistory().clear();
                getAllocationHistory().addAll((Collection<? extends AllocationHistory>)newValue);
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
            case N2BRMPackage.ON_NOTIFICATION_TYPE__MESSAGE_DETAILS:
                setMessageDetails((AsyncWorkItemDetails)null);
                return;
            case N2BRMPackage.ON_NOTIFICATION_TYPE__ITEM_BODY:
                setItemBody((ItemBody)null);
                return;
            case N2BRMPackage.ON_NOTIFICATION_TYPE__ALLOCATION_HISTORY:
                getAllocationHistory().clear();
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
            case N2BRMPackage.ON_NOTIFICATION_TYPE__MESSAGE_DETAILS:
                return messageDetails != null;
            case N2BRMPackage.ON_NOTIFICATION_TYPE__ITEM_BODY:
                return itemBody != null;
            case N2BRMPackage.ON_NOTIFICATION_TYPE__ALLOCATION_HISTORY:
                return allocationHistory != null && !allocationHistory.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //OnNotificationTypeImpl
