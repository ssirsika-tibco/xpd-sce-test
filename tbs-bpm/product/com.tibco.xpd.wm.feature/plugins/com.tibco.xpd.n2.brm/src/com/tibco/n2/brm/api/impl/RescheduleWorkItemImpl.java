/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.ItemBody;
import com.tibco.n2.brm.api.ItemSchedule;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.ObjectID;
import com.tibco.n2.brm.api.RescheduleWorkItem;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reschedule Work Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.RescheduleWorkItemImpl#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.RescheduleWorkItemImpl#getItemSchedule <em>Item Schedule</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.RescheduleWorkItemImpl#getItemBody <em>Item Body</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RescheduleWorkItemImpl extends EObjectImpl implements RescheduleWorkItem {
    /**
     * The cached value of the '{@link #getWorkItemID() <em>Work Item ID</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkItemID()
     * @generated
     * @ordered
     */
    protected ObjectID workItemID;

    /**
     * The cached value of the '{@link #getItemSchedule() <em>Item Schedule</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getItemSchedule()
     * @generated
     * @ordered
     */
    protected ItemSchedule itemSchedule;

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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RescheduleWorkItemImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.RESCHEDULE_WORK_ITEM;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ObjectID getWorkItemID() {
        return workItemID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkItemID(ObjectID newWorkItemID, NotificationChain msgs) {
        ObjectID oldWorkItemID = workItemID;
        workItemID = newWorkItemID;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.RESCHEDULE_WORK_ITEM__WORK_ITEM_ID, oldWorkItemID, newWorkItemID);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkItemID(ObjectID newWorkItemID) {
        if (newWorkItemID != workItemID) {
            NotificationChain msgs = null;
            if (workItemID != null)
                msgs = ((InternalEObject)workItemID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.RESCHEDULE_WORK_ITEM__WORK_ITEM_ID, null, msgs);
            if (newWorkItemID != null)
                msgs = ((InternalEObject)newWorkItemID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.RESCHEDULE_WORK_ITEM__WORK_ITEM_ID, null, msgs);
            msgs = basicSetWorkItemID(newWorkItemID, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.RESCHEDULE_WORK_ITEM__WORK_ITEM_ID, newWorkItemID, newWorkItemID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ItemSchedule getItemSchedule() {
        return itemSchedule;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetItemSchedule(ItemSchedule newItemSchedule, NotificationChain msgs) {
        ItemSchedule oldItemSchedule = itemSchedule;
        itemSchedule = newItemSchedule;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.RESCHEDULE_WORK_ITEM__ITEM_SCHEDULE, oldItemSchedule, newItemSchedule);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setItemSchedule(ItemSchedule newItemSchedule) {
        if (newItemSchedule != itemSchedule) {
            NotificationChain msgs = null;
            if (itemSchedule != null)
                msgs = ((InternalEObject)itemSchedule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.RESCHEDULE_WORK_ITEM__ITEM_SCHEDULE, null, msgs);
            if (newItemSchedule != null)
                msgs = ((InternalEObject)newItemSchedule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.RESCHEDULE_WORK_ITEM__ITEM_SCHEDULE, null, msgs);
            msgs = basicSetItemSchedule(newItemSchedule, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.RESCHEDULE_WORK_ITEM__ITEM_SCHEDULE, newItemSchedule, newItemSchedule));
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.RESCHEDULE_WORK_ITEM__ITEM_BODY, oldItemBody, newItemBody);
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
                msgs = ((InternalEObject)itemBody).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.RESCHEDULE_WORK_ITEM__ITEM_BODY, null, msgs);
            if (newItemBody != null)
                msgs = ((InternalEObject)newItemBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.RESCHEDULE_WORK_ITEM__ITEM_BODY, null, msgs);
            msgs = basicSetItemBody(newItemBody, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.RESCHEDULE_WORK_ITEM__ITEM_BODY, newItemBody, newItemBody));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.RESCHEDULE_WORK_ITEM__WORK_ITEM_ID:
                return basicSetWorkItemID(null, msgs);
            case N2BRMPackage.RESCHEDULE_WORK_ITEM__ITEM_SCHEDULE:
                return basicSetItemSchedule(null, msgs);
            case N2BRMPackage.RESCHEDULE_WORK_ITEM__ITEM_BODY:
                return basicSetItemBody(null, msgs);
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
            case N2BRMPackage.RESCHEDULE_WORK_ITEM__WORK_ITEM_ID:
                return getWorkItemID();
            case N2BRMPackage.RESCHEDULE_WORK_ITEM__ITEM_SCHEDULE:
                return getItemSchedule();
            case N2BRMPackage.RESCHEDULE_WORK_ITEM__ITEM_BODY:
                return getItemBody();
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
            case N2BRMPackage.RESCHEDULE_WORK_ITEM__WORK_ITEM_ID:
                setWorkItemID((ObjectID)newValue);
                return;
            case N2BRMPackage.RESCHEDULE_WORK_ITEM__ITEM_SCHEDULE:
                setItemSchedule((ItemSchedule)newValue);
                return;
            case N2BRMPackage.RESCHEDULE_WORK_ITEM__ITEM_BODY:
                setItemBody((ItemBody)newValue);
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
            case N2BRMPackage.RESCHEDULE_WORK_ITEM__WORK_ITEM_ID:
                setWorkItemID((ObjectID)null);
                return;
            case N2BRMPackage.RESCHEDULE_WORK_ITEM__ITEM_SCHEDULE:
                setItemSchedule((ItemSchedule)null);
                return;
            case N2BRMPackage.RESCHEDULE_WORK_ITEM__ITEM_BODY:
                setItemBody((ItemBody)null);
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
            case N2BRMPackage.RESCHEDULE_WORK_ITEM__WORK_ITEM_ID:
                return workItemID != null;
            case N2BRMPackage.RESCHEDULE_WORK_ITEM__ITEM_SCHEDULE:
                return itemSchedule != null;
            case N2BRMPackage.RESCHEDULE_WORK_ITEM__ITEM_BODY:
                return itemBody != null;
        }
        return super.eIsSet(featureID);
    }

} //RescheduleWorkItemImpl
