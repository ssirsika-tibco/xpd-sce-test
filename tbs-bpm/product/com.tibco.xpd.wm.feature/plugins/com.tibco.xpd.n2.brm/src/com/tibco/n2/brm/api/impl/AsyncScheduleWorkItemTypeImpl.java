/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.AsyncScheduleWorkItemType;
import com.tibco.n2.brm.api.AsyncWorkItemDetails;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.ScheduleWorkItemType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Async Schedule Work Item Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemTypeImpl#getScheduleWorkItem <em>Schedule Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemTypeImpl#getMessageDetails <em>Message Details</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AsyncScheduleWorkItemTypeImpl extends EObjectImpl implements AsyncScheduleWorkItemType {
    /**
     * The cached value of the '{@link #getScheduleWorkItem() <em>Schedule Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScheduleWorkItem()
     * @generated
     * @ordered
     */
    protected ScheduleWorkItemType scheduleWorkItem;

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
    protected AsyncScheduleWorkItemTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.ASYNC_SCHEDULE_WORK_ITEM_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScheduleWorkItemType getScheduleWorkItem() {
        return scheduleWorkItem;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetScheduleWorkItem(ScheduleWorkItemType newScheduleWorkItem, NotificationChain msgs) {
        ScheduleWorkItemType oldScheduleWorkItem = scheduleWorkItem;
        scheduleWorkItem = newScheduleWorkItem;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE__SCHEDULE_WORK_ITEM, oldScheduleWorkItem, newScheduleWorkItem);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setScheduleWorkItem(ScheduleWorkItemType newScheduleWorkItem) {
        if (newScheduleWorkItem != scheduleWorkItem) {
            NotificationChain msgs = null;
            if (scheduleWorkItem != null)
                msgs = ((InternalEObject)scheduleWorkItem).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE__SCHEDULE_WORK_ITEM, null, msgs);
            if (newScheduleWorkItem != null)
                msgs = ((InternalEObject)newScheduleWorkItem).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE__SCHEDULE_WORK_ITEM, null, msgs);
            msgs = basicSetScheduleWorkItem(newScheduleWorkItem, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE__SCHEDULE_WORK_ITEM, newScheduleWorkItem, newScheduleWorkItem));
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE__MESSAGE_DETAILS, oldMessageDetails, newMessageDetails);
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
                msgs = ((InternalEObject)messageDetails).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE__MESSAGE_DETAILS, null, msgs);
            if (newMessageDetails != null)
                msgs = ((InternalEObject)newMessageDetails).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE__MESSAGE_DETAILS, null, msgs);
            msgs = basicSetMessageDetails(newMessageDetails, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE__MESSAGE_DETAILS, newMessageDetails, newMessageDetails));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE__SCHEDULE_WORK_ITEM:
                return basicSetScheduleWorkItem(null, msgs);
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE__MESSAGE_DETAILS:
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
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE__SCHEDULE_WORK_ITEM:
                return getScheduleWorkItem();
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE__MESSAGE_DETAILS:
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
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE__SCHEDULE_WORK_ITEM:
                setScheduleWorkItem((ScheduleWorkItemType)newValue);
                return;
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE__MESSAGE_DETAILS:
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
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE__SCHEDULE_WORK_ITEM:
                setScheduleWorkItem((ScheduleWorkItemType)null);
                return;
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE__MESSAGE_DETAILS:
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
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE__SCHEDULE_WORK_ITEM:
                return scheduleWorkItem != null;
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE__MESSAGE_DETAILS:
                return messageDetails != null;
        }
        return super.eIsSet(featureID);
    }

} //AsyncScheduleWorkItemTypeImpl
