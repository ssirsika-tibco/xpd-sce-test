/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.GetWorkListViewDetailsType;
import com.tibco.n2.brm.api.N2BRMPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Get Work List View Details Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkListViewDetailsTypeImpl#getApiVersion <em>Api Version</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkListViewDetailsTypeImpl#isLockView <em>Lock View</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkListViewDetailsTypeImpl#getWorkListViewID <em>Work List View ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GetWorkListViewDetailsTypeImpl extends EObjectImpl implements GetWorkListViewDetailsType {
    /**
     * The default value of the '{@link #getApiVersion() <em>Api Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getApiVersion()
     * @generated
     * @ordered
     */
    protected static final int API_VERSION_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getApiVersion() <em>Api Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getApiVersion()
     * @generated
     * @ordered
     */
    protected int apiVersion = API_VERSION_EDEFAULT;

    /**
     * This is true if the Api Version attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean apiVersionESet;

    /**
     * The default value of the '{@link #isLockView() <em>Lock View</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isLockView()
     * @generated
     * @ordered
     */
    protected static final boolean LOCK_VIEW_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isLockView() <em>Lock View</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isLockView()
     * @generated
     * @ordered
     */
    protected boolean lockView = LOCK_VIEW_EDEFAULT;

    /**
     * This is true if the Lock View attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean lockViewESet;

    /**
     * The default value of the '{@link #getWorkListViewID() <em>Work List View ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkListViewID()
     * @generated
     * @ordered
     */
    protected static final long WORK_LIST_VIEW_ID_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getWorkListViewID() <em>Work List View ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkListViewID()
     * @generated
     * @ordered
     */
    protected long workListViewID = WORK_LIST_VIEW_ID_EDEFAULT;

    /**
     * This is true if the Work List View ID attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean workListViewIDESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GetWorkListViewDetailsTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.GET_WORK_LIST_VIEW_DETAILS_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getApiVersion() {
        return apiVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setApiVersion(int newApiVersion) {
        int oldApiVersion = apiVersion;
        apiVersion = newApiVersion;
        boolean oldApiVersionESet = apiVersionESet;
        apiVersionESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE__API_VERSION, oldApiVersion, apiVersion, !oldApiVersionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetApiVersion() {
        int oldApiVersion = apiVersion;
        boolean oldApiVersionESet = apiVersionESet;
        apiVersion = API_VERSION_EDEFAULT;
        apiVersionESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE__API_VERSION, oldApiVersion, API_VERSION_EDEFAULT, oldApiVersionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetApiVersion() {
        return apiVersionESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isLockView() {
        return lockView;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLockView(boolean newLockView) {
        boolean oldLockView = lockView;
        lockView = newLockView;
        boolean oldLockViewESet = lockViewESet;
        lockViewESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE__LOCK_VIEW, oldLockView, lockView, !oldLockViewESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetLockView() {
        boolean oldLockView = lockView;
        boolean oldLockViewESet = lockViewESet;
        lockView = LOCK_VIEW_EDEFAULT;
        lockViewESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE__LOCK_VIEW, oldLockView, LOCK_VIEW_EDEFAULT, oldLockViewESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetLockView() {
        return lockViewESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getWorkListViewID() {
        return workListViewID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkListViewID(long newWorkListViewID) {
        long oldWorkListViewID = workListViewID;
        workListViewID = newWorkListViewID;
        boolean oldWorkListViewIDESet = workListViewIDESet;
        workListViewIDESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE__WORK_LIST_VIEW_ID, oldWorkListViewID, workListViewID, !oldWorkListViewIDESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetWorkListViewID() {
        long oldWorkListViewID = workListViewID;
        boolean oldWorkListViewIDESet = workListViewIDESet;
        workListViewID = WORK_LIST_VIEW_ID_EDEFAULT;
        workListViewIDESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE__WORK_LIST_VIEW_ID, oldWorkListViewID, WORK_LIST_VIEW_ID_EDEFAULT, oldWorkListViewIDESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetWorkListViewID() {
        return workListViewIDESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE__API_VERSION:
                return getApiVersion();
            case N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE__LOCK_VIEW:
                return isLockView();
            case N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE__WORK_LIST_VIEW_ID:
                return getWorkListViewID();
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
            case N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE__API_VERSION:
                setApiVersion((Integer)newValue);
                return;
            case N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE__LOCK_VIEW:
                setLockView((Boolean)newValue);
                return;
            case N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE__WORK_LIST_VIEW_ID:
                setWorkListViewID((Long)newValue);
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
            case N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE__API_VERSION:
                unsetApiVersion();
                return;
            case N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE__LOCK_VIEW:
                unsetLockView();
                return;
            case N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE__WORK_LIST_VIEW_ID:
                unsetWorkListViewID();
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
            case N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE__API_VERSION:
                return isSetApiVersion();
            case N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE__LOCK_VIEW:
                return isSetLockView();
            case N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE__WORK_LIST_VIEW_ID:
                return isSetWorkListViewID();
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
        result.append(" (apiVersion: ");
        if (apiVersionESet) result.append(apiVersion); else result.append("<unset>");
        result.append(", lockView: ");
        if (lockViewESet) result.append(lockView); else result.append("<unset>");
        result.append(", workListViewID: ");
        if (workListViewIDESet) result.append(workListViewID); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //GetWorkListViewDetailsTypeImpl
