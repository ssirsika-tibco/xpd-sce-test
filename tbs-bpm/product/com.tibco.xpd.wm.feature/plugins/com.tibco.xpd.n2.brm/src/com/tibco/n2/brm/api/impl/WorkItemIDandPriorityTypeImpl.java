/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.ManagedObjectID;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkItemIDandPriorityType;
import com.tibco.n2.brm.api.WorkItemPriorityType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Item IDand Priority Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemIDandPriorityTypeImpl#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemIDandPriorityTypeImpl#getWorkItemPriority <em>Work Item Priority</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkItemIDandPriorityTypeImpl extends EObjectImpl implements WorkItemIDandPriorityType {
    /**
     * The cached value of the '{@link #getWorkItemID() <em>Work Item ID</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkItemID()
     * @generated
     * @ordered
     */
    protected ManagedObjectID workItemID;

    /**
     * The cached value of the '{@link #getWorkItemPriority() <em>Work Item Priority</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkItemPriority()
     * @generated
     * @ordered
     */
    protected WorkItemPriorityType workItemPriority;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkItemIDandPriorityTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.WORK_ITEM_IDAND_PRIORITY_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ManagedObjectID getWorkItemID() {
        return workItemID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkItemID(ManagedObjectID newWorkItemID, NotificationChain msgs) {
        ManagedObjectID oldWorkItemID = workItemID;
        workItemID = newWorkItemID;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_ID, oldWorkItemID, newWorkItemID);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkItemID(ManagedObjectID newWorkItemID) {
        if (newWorkItemID != workItemID) {
            NotificationChain msgs = null;
            if (workItemID != null)
                msgs = ((InternalEObject)workItemID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_ID, null, msgs);
            if (newWorkItemID != null)
                msgs = ((InternalEObject)newWorkItemID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_ID, null, msgs);
            msgs = basicSetWorkItemID(newWorkItemID, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_ID, newWorkItemID, newWorkItemID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemPriorityType getWorkItemPriority() {
        return workItemPriority;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkItemPriority(WorkItemPriorityType newWorkItemPriority, NotificationChain msgs) {
        WorkItemPriorityType oldWorkItemPriority = workItemPriority;
        workItemPriority = newWorkItemPriority;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_PRIORITY, oldWorkItemPriority, newWorkItemPriority);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkItemPriority(WorkItemPriorityType newWorkItemPriority) {
        if (newWorkItemPriority != workItemPriority) {
            NotificationChain msgs = null;
            if (workItemPriority != null)
                msgs = ((InternalEObject)workItemPriority).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_PRIORITY, null, msgs);
            if (newWorkItemPriority != null)
                msgs = ((InternalEObject)newWorkItemPriority).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_PRIORITY, null, msgs);
            msgs = basicSetWorkItemPriority(newWorkItemPriority, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_PRIORITY, newWorkItemPriority, newWorkItemPriority));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_ID:
                return basicSetWorkItemID(null, msgs);
            case N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_PRIORITY:
                return basicSetWorkItemPriority(null, msgs);
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
            case N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_ID:
                return getWorkItemID();
            case N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_PRIORITY:
                return getWorkItemPriority();
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
            case N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_ID:
                setWorkItemID((ManagedObjectID)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_PRIORITY:
                setWorkItemPriority((WorkItemPriorityType)newValue);
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
            case N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_ID:
                setWorkItemID((ManagedObjectID)null);
                return;
            case N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_PRIORITY:
                setWorkItemPriority((WorkItemPriorityType)null);
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
            case N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_ID:
                return workItemID != null;
            case N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_PRIORITY:
                return workItemPriority != null;
        }
        return super.eIsSet(featureID);
    }

} //WorkItemIDandPriorityTypeImpl
