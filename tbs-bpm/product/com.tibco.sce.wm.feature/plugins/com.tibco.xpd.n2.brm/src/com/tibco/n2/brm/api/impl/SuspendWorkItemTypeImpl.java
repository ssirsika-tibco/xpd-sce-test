/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.ObjectID;
import com.tibco.n2.brm.api.SuspendWorkItemType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Suspend Work Item Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.SuspendWorkItemTypeImpl#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.SuspendWorkItemTypeImpl#isForceSuspend <em>Force Suspend</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SuspendWorkItemTypeImpl extends EObjectImpl implements SuspendWorkItemType {
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
     * The default value of the '{@link #isForceSuspend() <em>Force Suspend</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isForceSuspend()
     * @generated
     * @ordered
     */
    protected static final boolean FORCE_SUSPEND_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isForceSuspend() <em>Force Suspend</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isForceSuspend()
     * @generated
     * @ordered
     */
    protected boolean forceSuspend = FORCE_SUSPEND_EDEFAULT;

    /**
     * This is true if the Force Suspend attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean forceSuspendESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SuspendWorkItemTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.SUSPEND_WORK_ITEM_TYPE;
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.SUSPEND_WORK_ITEM_TYPE__WORK_ITEM_ID, oldWorkItemID, newWorkItemID);
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
                msgs = ((InternalEObject)workItemID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SUSPEND_WORK_ITEM_TYPE__WORK_ITEM_ID, null, msgs);
            if (newWorkItemID != null)
                msgs = ((InternalEObject)newWorkItemID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SUSPEND_WORK_ITEM_TYPE__WORK_ITEM_ID, null, msgs);
            msgs = basicSetWorkItemID(newWorkItemID, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.SUSPEND_WORK_ITEM_TYPE__WORK_ITEM_ID, newWorkItemID, newWorkItemID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isForceSuspend() {
        return forceSuspend;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setForceSuspend(boolean newForceSuspend) {
        boolean oldForceSuspend = forceSuspend;
        forceSuspend = newForceSuspend;
        boolean oldForceSuspendESet = forceSuspendESet;
        forceSuspendESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.SUSPEND_WORK_ITEM_TYPE__FORCE_SUSPEND, oldForceSuspend, forceSuspend, !oldForceSuspendESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetForceSuspend() {
        boolean oldForceSuspend = forceSuspend;
        boolean oldForceSuspendESet = forceSuspendESet;
        forceSuspend = FORCE_SUSPEND_EDEFAULT;
        forceSuspendESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.SUSPEND_WORK_ITEM_TYPE__FORCE_SUSPEND, oldForceSuspend, FORCE_SUSPEND_EDEFAULT, oldForceSuspendESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetForceSuspend() {
        return forceSuspendESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.SUSPEND_WORK_ITEM_TYPE__WORK_ITEM_ID:
                return basicSetWorkItemID(null, msgs);
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
            case N2BRMPackage.SUSPEND_WORK_ITEM_TYPE__WORK_ITEM_ID:
                return getWorkItemID();
            case N2BRMPackage.SUSPEND_WORK_ITEM_TYPE__FORCE_SUSPEND:
                return isForceSuspend();
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
            case N2BRMPackage.SUSPEND_WORK_ITEM_TYPE__WORK_ITEM_ID:
                setWorkItemID((ObjectID)newValue);
                return;
            case N2BRMPackage.SUSPEND_WORK_ITEM_TYPE__FORCE_SUSPEND:
                setForceSuspend((Boolean)newValue);
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
            case N2BRMPackage.SUSPEND_WORK_ITEM_TYPE__WORK_ITEM_ID:
                setWorkItemID((ObjectID)null);
                return;
            case N2BRMPackage.SUSPEND_WORK_ITEM_TYPE__FORCE_SUSPEND:
                unsetForceSuspend();
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
            case N2BRMPackage.SUSPEND_WORK_ITEM_TYPE__WORK_ITEM_ID:
                return workItemID != null;
            case N2BRMPackage.SUSPEND_WORK_ITEM_TYPE__FORCE_SUSPEND:
                return isSetForceSuspend();
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
        result.append(" (forceSuspend: ");
        if (forceSuspendESet) result.append(forceSuspend); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //SuspendWorkItemTypeImpl
