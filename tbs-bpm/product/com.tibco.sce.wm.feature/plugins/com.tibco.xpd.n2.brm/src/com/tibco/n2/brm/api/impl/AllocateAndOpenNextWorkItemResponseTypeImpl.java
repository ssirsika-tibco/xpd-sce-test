/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemResponseType;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkItem;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Allocate And Open Next Work Item Response Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.AllocateAndOpenNextWorkItemResponseTypeImpl#getWorkItem <em>Work Item</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AllocateAndOpenNextWorkItemResponseTypeImpl extends EObjectImpl implements AllocateAndOpenNextWorkItemResponseType {
    /**
     * The cached value of the '{@link #getWorkItem() <em>Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkItem()
     * @generated
     * @ordered
     */
    protected WorkItem workItem;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AllocateAndOpenNextWorkItemResponseTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItem getWorkItem() {
        return workItem;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkItem(WorkItem newWorkItem, NotificationChain msgs) {
        WorkItem oldWorkItem = workItem;
        workItem = newWorkItem;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM, oldWorkItem, newWorkItem);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkItem(WorkItem newWorkItem) {
        if (newWorkItem != workItem) {
            NotificationChain msgs = null;
            if (workItem != null)
                msgs = ((InternalEObject)workItem).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM, null, msgs);
            if (newWorkItem != null)
                msgs = ((InternalEObject)newWorkItem).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM, null, msgs);
            msgs = basicSetWorkItem(newWorkItem, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM, newWorkItem, newWorkItem));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM:
                return basicSetWorkItem(null, msgs);
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
            case N2BRMPackage.ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM:
                return getWorkItem();
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
            case N2BRMPackage.ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM:
                setWorkItem((WorkItem)newValue);
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
            case N2BRMPackage.ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM:
                setWorkItem((WorkItem)null);
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
            case N2BRMPackage.ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM:
                return workItem != null;
        }
        return super.eIsSet(featureID);
    }

} //AllocateAndOpenNextWorkItemResponseTypeImpl
