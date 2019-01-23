/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.AsyncWorkItemDetails;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.ObjectID;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Async Work Item Details</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.AsyncWorkItemDetailsImpl#getWorkItemId <em>Work Item Id</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.AsyncWorkItemDetailsImpl#getActivityID <em>Activity ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.AsyncWorkItemDetailsImpl#getUaSequenceId <em>Ua Sequence Id</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.AsyncWorkItemDetailsImpl#getBrmSequenceId <em>Brm Sequence Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AsyncWorkItemDetailsImpl extends EObjectImpl implements AsyncWorkItemDetails {
    /**
     * The cached value of the '{@link #getWorkItemId() <em>Work Item Id</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkItemId()
     * @generated
     * @ordered
     */
    protected ObjectID workItemId;

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
     * The default value of the '{@link #getUaSequenceId() <em>Ua Sequence Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUaSequenceId()
     * @generated
     * @ordered
     */
    protected static final int UA_SEQUENCE_ID_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getUaSequenceId() <em>Ua Sequence Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUaSequenceId()
     * @generated
     * @ordered
     */
    protected int uaSequenceId = UA_SEQUENCE_ID_EDEFAULT;

    /**
     * This is true if the Ua Sequence Id attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean uaSequenceIdESet;

    /**
     * The default value of the '{@link #getBrmSequenceId() <em>Brm Sequence Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBrmSequenceId()
     * @generated
     * @ordered
     */
    protected static final int BRM_SEQUENCE_ID_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getBrmSequenceId() <em>Brm Sequence Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBrmSequenceId()
     * @generated
     * @ordered
     */
    protected int brmSequenceId = BRM_SEQUENCE_ID_EDEFAULT;

    /**
     * This is true if the Brm Sequence Id attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean brmSequenceIdESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AsyncWorkItemDetailsImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.ASYNC_WORK_ITEM_DETAILS;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ObjectID getWorkItemId() {
        return workItemId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkItemId(ObjectID newWorkItemId, NotificationChain msgs) {
        ObjectID oldWorkItemId = workItemId;
        workItemId = newWorkItemId;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__WORK_ITEM_ID, oldWorkItemId, newWorkItemId);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkItemId(ObjectID newWorkItemId) {
        if (newWorkItemId != workItemId) {
            NotificationChain msgs = null;
            if (workItemId != null)
                msgs = ((InternalEObject)workItemId).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__WORK_ITEM_ID, null, msgs);
            if (newWorkItemId != null)
                msgs = ((InternalEObject)newWorkItemId).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__WORK_ITEM_ID, null, msgs);
            msgs = basicSetWorkItemId(newWorkItemId, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__WORK_ITEM_ID, newWorkItemId, newWorkItemId));
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
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__ACTIVITY_ID, oldActivityID, activityID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getUaSequenceId() {
        return uaSequenceId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUaSequenceId(int newUaSequenceId) {
        int oldUaSequenceId = uaSequenceId;
        uaSequenceId = newUaSequenceId;
        boolean oldUaSequenceIdESet = uaSequenceIdESet;
        uaSequenceIdESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__UA_SEQUENCE_ID, oldUaSequenceId, uaSequenceId, !oldUaSequenceIdESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetUaSequenceId() {
        int oldUaSequenceId = uaSequenceId;
        boolean oldUaSequenceIdESet = uaSequenceIdESet;
        uaSequenceId = UA_SEQUENCE_ID_EDEFAULT;
        uaSequenceIdESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__UA_SEQUENCE_ID, oldUaSequenceId, UA_SEQUENCE_ID_EDEFAULT, oldUaSequenceIdESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetUaSequenceId() {
        return uaSequenceIdESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getBrmSequenceId() {
        return brmSequenceId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setBrmSequenceId(int newBrmSequenceId) {
        int oldBrmSequenceId = brmSequenceId;
        brmSequenceId = newBrmSequenceId;
        boolean oldBrmSequenceIdESet = brmSequenceIdESet;
        brmSequenceIdESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__BRM_SEQUENCE_ID, oldBrmSequenceId, brmSequenceId, !oldBrmSequenceIdESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetBrmSequenceId() {
        int oldBrmSequenceId = brmSequenceId;
        boolean oldBrmSequenceIdESet = brmSequenceIdESet;
        brmSequenceId = BRM_SEQUENCE_ID_EDEFAULT;
        brmSequenceIdESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__BRM_SEQUENCE_ID, oldBrmSequenceId, BRM_SEQUENCE_ID_EDEFAULT, oldBrmSequenceIdESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetBrmSequenceId() {
        return brmSequenceIdESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__WORK_ITEM_ID:
                return basicSetWorkItemId(null, msgs);
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
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__WORK_ITEM_ID:
                return getWorkItemId();
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__ACTIVITY_ID:
                return getActivityID();
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__UA_SEQUENCE_ID:
                return getUaSequenceId();
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__BRM_SEQUENCE_ID:
                return getBrmSequenceId();
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
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__WORK_ITEM_ID:
                setWorkItemId((ObjectID)newValue);
                return;
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__ACTIVITY_ID:
                setActivityID((String)newValue);
                return;
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__UA_SEQUENCE_ID:
                setUaSequenceId((Integer)newValue);
                return;
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__BRM_SEQUENCE_ID:
                setBrmSequenceId((Integer)newValue);
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
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__WORK_ITEM_ID:
                setWorkItemId((ObjectID)null);
                return;
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__ACTIVITY_ID:
                setActivityID(ACTIVITY_ID_EDEFAULT);
                return;
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__UA_SEQUENCE_ID:
                unsetUaSequenceId();
                return;
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__BRM_SEQUENCE_ID:
                unsetBrmSequenceId();
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
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__WORK_ITEM_ID:
                return workItemId != null;
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__ACTIVITY_ID:
                return ACTIVITY_ID_EDEFAULT == null ? activityID != null : !ACTIVITY_ID_EDEFAULT.equals(activityID);
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__UA_SEQUENCE_ID:
                return isSetUaSequenceId();
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS__BRM_SEQUENCE_ID:
                return isSetBrmSequenceId();
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
        result.append(", uaSequenceId: ");
        if (uaSequenceIdESet) result.append(uaSequenceId); else result.append("<unset>");
        result.append(", brmSequenceId: ");
        if (brmSequenceIdESet) result.append(brmSequenceId); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //AsyncWorkItemDetailsImpl
