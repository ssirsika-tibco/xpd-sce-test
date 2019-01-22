/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.CompleteWorkItemType;
import com.tibco.n2.brm.api.ManagedObjectID;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkItemBody;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Complete Work Item Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.CompleteWorkItemTypeImpl#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.CompleteWorkItemTypeImpl#getWorkItemPayload <em>Work Item Payload</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.CompleteWorkItemTypeImpl#isGetNextPiledItem <em>Get Next Piled Item</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CompleteWorkItemTypeImpl extends EObjectImpl implements CompleteWorkItemType {
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
     * The cached value of the '{@link #getWorkItemPayload() <em>Work Item Payload</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkItemPayload()
     * @generated
     * @ordered
     */
    protected WorkItemBody workItemPayload;

    /**
     * The default value of the '{@link #isGetNextPiledItem() <em>Get Next Piled Item</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isGetNextPiledItem()
     * @generated
     * @ordered
     */
    protected static final boolean GET_NEXT_PILED_ITEM_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isGetNextPiledItem() <em>Get Next Piled Item</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isGetNextPiledItem()
     * @generated
     * @ordered
     */
    protected boolean getNextPiledItem = GET_NEXT_PILED_ITEM_EDEFAULT;

    /**
     * This is true if the Get Next Piled Item attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean getNextPiledItemESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CompleteWorkItemTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.COMPLETE_WORK_ITEM_TYPE;
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_ID, oldWorkItemID, newWorkItemID);
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
                msgs = ((InternalEObject)workItemID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_ID, null, msgs);
            if (newWorkItemID != null)
                msgs = ((InternalEObject)newWorkItemID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_ID, null, msgs);
            msgs = basicSetWorkItemID(newWorkItemID, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_ID, newWorkItemID, newWorkItemID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemBody getWorkItemPayload() {
        return workItemPayload;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkItemPayload(WorkItemBody newWorkItemPayload, NotificationChain msgs) {
        WorkItemBody oldWorkItemPayload = workItemPayload;
        workItemPayload = newWorkItemPayload;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_PAYLOAD, oldWorkItemPayload, newWorkItemPayload);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkItemPayload(WorkItemBody newWorkItemPayload) {
        if (newWorkItemPayload != workItemPayload) {
            NotificationChain msgs = null;
            if (workItemPayload != null)
                msgs = ((InternalEObject)workItemPayload).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_PAYLOAD, null, msgs);
            if (newWorkItemPayload != null)
                msgs = ((InternalEObject)newWorkItemPayload).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_PAYLOAD, null, msgs);
            msgs = basicSetWorkItemPayload(newWorkItemPayload, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_PAYLOAD, newWorkItemPayload, newWorkItemPayload));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isGetNextPiledItem() {
        return getNextPiledItem;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetNextPiledItem(boolean newGetNextPiledItem) {
        boolean oldGetNextPiledItem = getNextPiledItem;
        getNextPiledItem = newGetNextPiledItem;
        boolean oldGetNextPiledItemESet = getNextPiledItemESet;
        getNextPiledItemESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__GET_NEXT_PILED_ITEM, oldGetNextPiledItem, getNextPiledItem, !oldGetNextPiledItemESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetGetNextPiledItem() {
        boolean oldGetNextPiledItem = getNextPiledItem;
        boolean oldGetNextPiledItemESet = getNextPiledItemESet;
        getNextPiledItem = GET_NEXT_PILED_ITEM_EDEFAULT;
        getNextPiledItemESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__GET_NEXT_PILED_ITEM, oldGetNextPiledItem, GET_NEXT_PILED_ITEM_EDEFAULT, oldGetNextPiledItemESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetGetNextPiledItem() {
        return getNextPiledItemESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_ID:
                return basicSetWorkItemID(null, msgs);
            case N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_PAYLOAD:
                return basicSetWorkItemPayload(null, msgs);
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
            case N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_ID:
                return getWorkItemID();
            case N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_PAYLOAD:
                return getWorkItemPayload();
            case N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__GET_NEXT_PILED_ITEM:
                return isGetNextPiledItem();
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
            case N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_ID:
                setWorkItemID((ManagedObjectID)newValue);
                return;
            case N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_PAYLOAD:
                setWorkItemPayload((WorkItemBody)newValue);
                return;
            case N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__GET_NEXT_PILED_ITEM:
                setGetNextPiledItem((Boolean)newValue);
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
            case N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_ID:
                setWorkItemID((ManagedObjectID)null);
                return;
            case N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_PAYLOAD:
                setWorkItemPayload((WorkItemBody)null);
                return;
            case N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__GET_NEXT_PILED_ITEM:
                unsetGetNextPiledItem();
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
            case N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_ID:
                return workItemID != null;
            case N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_PAYLOAD:
                return workItemPayload != null;
            case N2BRMPackage.COMPLETE_WORK_ITEM_TYPE__GET_NEXT_PILED_ITEM:
                return isSetGetNextPiledItem();
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
        result.append(" (getNextPiledItem: ");
        if (getNextPiledItemESet) result.append(getNextPiledItem); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //CompleteWorkItemTypeImpl
