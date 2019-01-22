/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.AsyncStartGroupType;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.StartGroupType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Async Start Group Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.AsyncStartGroupTypeImpl#getActivityID <em>Activity ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.AsyncStartGroupTypeImpl#getGroupID <em>Group ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.AsyncStartGroupTypeImpl#getStartGroup <em>Start Group</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AsyncStartGroupTypeImpl extends EObjectImpl implements AsyncStartGroupType {
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
     * The cached value of the '{@link #getStartGroup() <em>Start Group</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartGroup()
     * @generated
     * @ordered
     */
    protected StartGroupType startGroup;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AsyncStartGroupTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.ASYNC_START_GROUP_TYPE;
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
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_START_GROUP_TYPE__ACTIVITY_ID, oldActivityID, activityID));
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
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_START_GROUP_TYPE__GROUP_ID, oldGroupID, groupID, !oldGroupIDESet));
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
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.ASYNC_START_GROUP_TYPE__GROUP_ID, oldGroupID, GROUP_ID_EDEFAULT, oldGroupIDESet));
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
    public StartGroupType getStartGroup() {
        return startGroup;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetStartGroup(StartGroupType newStartGroup, NotificationChain msgs) {
        StartGroupType oldStartGroup = startGroup;
        startGroup = newStartGroup;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_START_GROUP_TYPE__START_GROUP, oldStartGroup, newStartGroup);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStartGroup(StartGroupType newStartGroup) {
        if (newStartGroup != startGroup) {
            NotificationChain msgs = null;
            if (startGroup != null)
                msgs = ((InternalEObject)startGroup).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_START_GROUP_TYPE__START_GROUP, null, msgs);
            if (newStartGroup != null)
                msgs = ((InternalEObject)newStartGroup).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_START_GROUP_TYPE__START_GROUP, null, msgs);
            msgs = basicSetStartGroup(newStartGroup, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_START_GROUP_TYPE__START_GROUP, newStartGroup, newStartGroup));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.ASYNC_START_GROUP_TYPE__START_GROUP:
                return basicSetStartGroup(null, msgs);
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
            case N2BRMPackage.ASYNC_START_GROUP_TYPE__ACTIVITY_ID:
                return getActivityID();
            case N2BRMPackage.ASYNC_START_GROUP_TYPE__GROUP_ID:
                return getGroupID();
            case N2BRMPackage.ASYNC_START_GROUP_TYPE__START_GROUP:
                return getStartGroup();
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
            case N2BRMPackage.ASYNC_START_GROUP_TYPE__ACTIVITY_ID:
                setActivityID((String)newValue);
                return;
            case N2BRMPackage.ASYNC_START_GROUP_TYPE__GROUP_ID:
                setGroupID((Long)newValue);
                return;
            case N2BRMPackage.ASYNC_START_GROUP_TYPE__START_GROUP:
                setStartGroup((StartGroupType)newValue);
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
            case N2BRMPackage.ASYNC_START_GROUP_TYPE__ACTIVITY_ID:
                setActivityID(ACTIVITY_ID_EDEFAULT);
                return;
            case N2BRMPackage.ASYNC_START_GROUP_TYPE__GROUP_ID:
                unsetGroupID();
                return;
            case N2BRMPackage.ASYNC_START_GROUP_TYPE__START_GROUP:
                setStartGroup((StartGroupType)null);
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
            case N2BRMPackage.ASYNC_START_GROUP_TYPE__ACTIVITY_ID:
                return ACTIVITY_ID_EDEFAULT == null ? activityID != null : !ACTIVITY_ID_EDEFAULT.equals(activityID);
            case N2BRMPackage.ASYNC_START_GROUP_TYPE__GROUP_ID:
                return isSetGroupID();
            case N2BRMPackage.ASYNC_START_GROUP_TYPE__START_GROUP:
                return startGroup != null;
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
        result.append(", groupID: ");
        if (groupIDESet) result.append(groupID); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //AsyncStartGroupTypeImpl
