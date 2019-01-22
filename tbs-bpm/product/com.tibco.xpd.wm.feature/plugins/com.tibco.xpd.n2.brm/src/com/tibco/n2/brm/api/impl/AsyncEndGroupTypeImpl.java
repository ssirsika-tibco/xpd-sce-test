/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.AsyncEndGroupType;
import com.tibco.n2.brm.api.EndGroupType;
import com.tibco.n2.brm.api.N2BRMPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Async End Group Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.AsyncEndGroupTypeImpl#getActivityID <em>Activity ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.AsyncEndGroupTypeImpl#getEndGroup <em>End Group</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AsyncEndGroupTypeImpl extends EObjectImpl implements AsyncEndGroupType {
    /**
     * The default value of the '{@link #getActivityID() <em>Activity ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivityID()
     * @generated
     * @ordered
     */
    protected static final String ACTIVITY_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getActivityID() <em>Activity ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivityID()
     * @generated
     * @ordered
     */
    protected String activityID = ACTIVITY_ID_EDEFAULT;

    /**
     * The cached value of the '{@link #getEndGroup() <em>End Group</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEndGroup()
     * @generated
     * @ordered
     */
    protected EndGroupType endGroup;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AsyncEndGroupTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.ASYNC_END_GROUP_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getActivityID() {
        return activityID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setActivityID(String newActivityID) {
        String oldActivityID = activityID;
        activityID = newActivityID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_END_GROUP_TYPE__ACTIVITY_ID, oldActivityID, activityID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EndGroupType getEndGroup() {
        return endGroup;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetEndGroup(EndGroupType newEndGroup, NotificationChain msgs) {
        EndGroupType oldEndGroup = endGroup;
        endGroup = newEndGroup;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_END_GROUP_TYPE__END_GROUP, oldEndGroup, newEndGroup);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEndGroup(EndGroupType newEndGroup) {
        if (newEndGroup != endGroup) {
            NotificationChain msgs = null;
            if (endGroup != null)
                msgs = ((InternalEObject)endGroup).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_END_GROUP_TYPE__END_GROUP, null, msgs);
            if (newEndGroup != null)
                msgs = ((InternalEObject)newEndGroup).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_END_GROUP_TYPE__END_GROUP, null, msgs);
            msgs = basicSetEndGroup(newEndGroup, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_END_GROUP_TYPE__END_GROUP, newEndGroup, newEndGroup));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.ASYNC_END_GROUP_TYPE__END_GROUP:
                return basicSetEndGroup(null, msgs);
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
            case N2BRMPackage.ASYNC_END_GROUP_TYPE__ACTIVITY_ID:
                return getActivityID();
            case N2BRMPackage.ASYNC_END_GROUP_TYPE__END_GROUP:
                return getEndGroup();
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
            case N2BRMPackage.ASYNC_END_GROUP_TYPE__ACTIVITY_ID:
                setActivityID((String)newValue);
                return;
            case N2BRMPackage.ASYNC_END_GROUP_TYPE__END_GROUP:
                setEndGroup((EndGroupType)newValue);
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
            case N2BRMPackage.ASYNC_END_GROUP_TYPE__ACTIVITY_ID:
                setActivityID(ACTIVITY_ID_EDEFAULT);
                return;
            case N2BRMPackage.ASYNC_END_GROUP_TYPE__END_GROUP:
                setEndGroup((EndGroupType)null);
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
            case N2BRMPackage.ASYNC_END_GROUP_TYPE__ACTIVITY_ID:
                return ACTIVITY_ID_EDEFAULT == null ? activityID != null : !ACTIVITY_ID_EDEFAULT.equals(activityID);
            case N2BRMPackage.ASYNC_END_GROUP_TYPE__END_GROUP:
                return endGroup != null;
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
        result.append(" (activityID: ");
        result.append(activityID);
        result.append(')');
        return result.toString();
    }

} //AsyncEndGroupTypeImpl
