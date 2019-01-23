/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelType;
import com.tibco.n2.brm.api.AsyncWorkItemDetails;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.ScheduleWorkItemWithModelType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Async Schedule Work Item With Model Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemWithModelTypeImpl#getScheduleWorkItemWithModel <em>Schedule Work Item With Model</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemWithModelTypeImpl#getMessageDetails <em>Message Details</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AsyncScheduleWorkItemWithModelTypeImpl extends EObjectImpl implements AsyncScheduleWorkItemWithModelType {
    /**
     * The cached value of the '{@link #getScheduleWorkItemWithModel() <em>Schedule Work Item With Model</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScheduleWorkItemWithModel()
     * @generated
     * @ordered
     */
    protected ScheduleWorkItemWithModelType scheduleWorkItemWithModel;

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
    protected AsyncScheduleWorkItemWithModelTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScheduleWorkItemWithModelType getScheduleWorkItemWithModel() {
        return scheduleWorkItemWithModel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetScheduleWorkItemWithModel(ScheduleWorkItemWithModelType newScheduleWorkItemWithModel, NotificationChain msgs) {
        ScheduleWorkItemWithModelType oldScheduleWorkItemWithModel = scheduleWorkItemWithModel;
        scheduleWorkItemWithModel = newScheduleWorkItemWithModel;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__SCHEDULE_WORK_ITEM_WITH_MODEL, oldScheduleWorkItemWithModel, newScheduleWorkItemWithModel);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setScheduleWorkItemWithModel(ScheduleWorkItemWithModelType newScheduleWorkItemWithModel) {
        if (newScheduleWorkItemWithModel != scheduleWorkItemWithModel) {
            NotificationChain msgs = null;
            if (scheduleWorkItemWithModel != null)
                msgs = ((InternalEObject)scheduleWorkItemWithModel).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__SCHEDULE_WORK_ITEM_WITH_MODEL, null, msgs);
            if (newScheduleWorkItemWithModel != null)
                msgs = ((InternalEObject)newScheduleWorkItemWithModel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__SCHEDULE_WORK_ITEM_WITH_MODEL, null, msgs);
            msgs = basicSetScheduleWorkItemWithModel(newScheduleWorkItemWithModel, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__SCHEDULE_WORK_ITEM_WITH_MODEL, newScheduleWorkItemWithModel, newScheduleWorkItemWithModel));
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__MESSAGE_DETAILS, oldMessageDetails, newMessageDetails);
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
                msgs = ((InternalEObject)messageDetails).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__MESSAGE_DETAILS, null, msgs);
            if (newMessageDetails != null)
                msgs = ((InternalEObject)newMessageDetails).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__MESSAGE_DETAILS, null, msgs);
            msgs = basicSetMessageDetails(newMessageDetails, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__MESSAGE_DETAILS, newMessageDetails, newMessageDetails));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__SCHEDULE_WORK_ITEM_WITH_MODEL:
                return basicSetScheduleWorkItemWithModel(null, msgs);
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__MESSAGE_DETAILS:
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
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__SCHEDULE_WORK_ITEM_WITH_MODEL:
                return getScheduleWorkItemWithModel();
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__MESSAGE_DETAILS:
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
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__SCHEDULE_WORK_ITEM_WITH_MODEL:
                setScheduleWorkItemWithModel((ScheduleWorkItemWithModelType)newValue);
                return;
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__MESSAGE_DETAILS:
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
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__SCHEDULE_WORK_ITEM_WITH_MODEL:
                setScheduleWorkItemWithModel((ScheduleWorkItemWithModelType)null);
                return;
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__MESSAGE_DETAILS:
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
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__SCHEDULE_WORK_ITEM_WITH_MODEL:
                return scheduleWorkItemWithModel != null;
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__MESSAGE_DETAILS:
                return messageDetails != null;
        }
        return super.eIsSet(featureID);
    }

} //AsyncScheduleWorkItemWithModelTypeImpl
