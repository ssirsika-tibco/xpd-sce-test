/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.ManagedObjectID;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.NextWorkItem;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Next Work Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.NextWorkItemImpl#getNextItem <em>Next Item</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NextWorkItemImpl extends EObjectImpl implements NextWorkItem {
    /**
     * The cached value of the '{@link #getNextItem() <em>Next Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNextItem()
     * @generated
     * @ordered
     */
    protected ManagedObjectID nextItem;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected NextWorkItemImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.NEXT_WORK_ITEM;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ManagedObjectID getNextItem() {
        return nextItem;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetNextItem(ManagedObjectID newNextItem, NotificationChain msgs) {
        ManagedObjectID oldNextItem = nextItem;
        nextItem = newNextItem;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.NEXT_WORK_ITEM__NEXT_ITEM, oldNextItem, newNextItem);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNextItem(ManagedObjectID newNextItem) {
        if (newNextItem != nextItem) {
            NotificationChain msgs = null;
            if (nextItem != null)
                msgs = ((InternalEObject)nextItem).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.NEXT_WORK_ITEM__NEXT_ITEM, null, msgs);
            if (newNextItem != null)
                msgs = ((InternalEObject)newNextItem).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.NEXT_WORK_ITEM__NEXT_ITEM, null, msgs);
            msgs = basicSetNextItem(newNextItem, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.NEXT_WORK_ITEM__NEXT_ITEM, newNextItem, newNextItem));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.NEXT_WORK_ITEM__NEXT_ITEM:
                return basicSetNextItem(null, msgs);
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
            case N2BRMPackage.NEXT_WORK_ITEM__NEXT_ITEM:
                return getNextItem();
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
            case N2BRMPackage.NEXT_WORK_ITEM__NEXT_ITEM:
                setNextItem((ManagedObjectID)newValue);
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
            case N2BRMPackage.NEXT_WORK_ITEM__NEXT_ITEM:
                setNextItem((ManagedObjectID)null);
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
            case N2BRMPackage.NEXT_WORK_ITEM__NEXT_ITEM:
                return nextItem != null;
        }
        return super.eIsSet(featureID);
    }

} //NextWorkItemImpl
