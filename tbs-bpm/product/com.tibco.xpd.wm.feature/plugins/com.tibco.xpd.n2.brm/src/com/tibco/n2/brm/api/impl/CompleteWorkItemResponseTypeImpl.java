/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.CompleteWorkItemResponseType;
import com.tibco.n2.brm.api.ManagedObjectID;
import com.tibco.n2.brm.api.N2BRMPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Complete Work Item Response Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.CompleteWorkItemResponseTypeImpl#getGroupID <em>Group ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.CompleteWorkItemResponseTypeImpl#getNextWorkItem <em>Next Work Item</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CompleteWorkItemResponseTypeImpl extends EObjectImpl implements CompleteWorkItemResponseType {
    /**
     * The default value of the '{@link #getGroupID() <em>Group ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGroupID()
     * @generated
     * @ordered
     */
    protected static final long GROUP_ID_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getGroupID() <em>Group ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGroupID()
     * @generated
     * @ordered
     */
    protected long groupID = GROUP_ID_EDEFAULT;

    /**
     * This is true if the Group ID attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean groupIDESet;

    /**
     * The cached value of the '{@link #getNextWorkItem() <em>Next Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNextWorkItem()
     * @generated
     * @ordered
     */
    protected ManagedObjectID nextWorkItem;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CompleteWorkItemResponseTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.COMPLETE_WORK_ITEM_RESPONSE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getGroupID() {
        return groupID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGroupID(long newGroupID) {
        long oldGroupID = groupID;
        groupID = newGroupID;
        boolean oldGroupIDESet = groupIDESet;
        groupIDESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.COMPLETE_WORK_ITEM_RESPONSE_TYPE__GROUP_ID, oldGroupID, groupID, !oldGroupIDESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetGroupID() {
        long oldGroupID = groupID;
        boolean oldGroupIDESet = groupIDESet;
        groupID = GROUP_ID_EDEFAULT;
        groupIDESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.COMPLETE_WORK_ITEM_RESPONSE_TYPE__GROUP_ID, oldGroupID, GROUP_ID_EDEFAULT, oldGroupIDESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetGroupID() {
        return groupIDESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ManagedObjectID getNextWorkItem() {
        return nextWorkItem;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetNextWorkItem(ManagedObjectID newNextWorkItem, NotificationChain msgs) {
        ManagedObjectID oldNextWorkItem = nextWorkItem;
        nextWorkItem = newNextWorkItem;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.COMPLETE_WORK_ITEM_RESPONSE_TYPE__NEXT_WORK_ITEM, oldNextWorkItem, newNextWorkItem);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNextWorkItem(ManagedObjectID newNextWorkItem) {
        if (newNextWorkItem != nextWorkItem) {
            NotificationChain msgs = null;
            if (nextWorkItem != null)
                msgs = ((InternalEObject)nextWorkItem).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.COMPLETE_WORK_ITEM_RESPONSE_TYPE__NEXT_WORK_ITEM, null, msgs);
            if (newNextWorkItem != null)
                msgs = ((InternalEObject)newNextWorkItem).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.COMPLETE_WORK_ITEM_RESPONSE_TYPE__NEXT_WORK_ITEM, null, msgs);
            msgs = basicSetNextWorkItem(newNextWorkItem, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.COMPLETE_WORK_ITEM_RESPONSE_TYPE__NEXT_WORK_ITEM, newNextWorkItem, newNextWorkItem));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.COMPLETE_WORK_ITEM_RESPONSE_TYPE__NEXT_WORK_ITEM:
                return basicSetNextWorkItem(null, msgs);
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
            case N2BRMPackage.COMPLETE_WORK_ITEM_RESPONSE_TYPE__GROUP_ID:
                return getGroupID();
            case N2BRMPackage.COMPLETE_WORK_ITEM_RESPONSE_TYPE__NEXT_WORK_ITEM:
                return getNextWorkItem();
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
            case N2BRMPackage.COMPLETE_WORK_ITEM_RESPONSE_TYPE__GROUP_ID:
                setGroupID((Long)newValue);
                return;
            case N2BRMPackage.COMPLETE_WORK_ITEM_RESPONSE_TYPE__NEXT_WORK_ITEM:
                setNextWorkItem((ManagedObjectID)newValue);
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
            case N2BRMPackage.COMPLETE_WORK_ITEM_RESPONSE_TYPE__GROUP_ID:
                unsetGroupID();
                return;
            case N2BRMPackage.COMPLETE_WORK_ITEM_RESPONSE_TYPE__NEXT_WORK_ITEM:
                setNextWorkItem((ManagedObjectID)null);
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
            case N2BRMPackage.COMPLETE_WORK_ITEM_RESPONSE_TYPE__GROUP_ID:
                return isSetGroupID();
            case N2BRMPackage.COMPLETE_WORK_ITEM_RESPONSE_TYPE__NEXT_WORK_ITEM:
                return nextWorkItem != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (groupID: ");
        if (groupIDESet) result.append(groupID); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //CompleteWorkItemResponseTypeImpl
