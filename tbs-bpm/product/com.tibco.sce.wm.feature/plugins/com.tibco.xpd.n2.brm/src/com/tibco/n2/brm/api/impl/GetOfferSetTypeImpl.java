/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.GetOfferSetType;
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
 * An implementation of the model object '<em><b>Get Offer Set Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetOfferSetTypeImpl#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetOfferSetTypeImpl#getApiVersion <em>Api Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GetOfferSetTypeImpl extends EObjectImpl implements GetOfferSetType {
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GetOfferSetTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.GET_OFFER_SET_TYPE;
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_OFFER_SET_TYPE__WORK_ITEM_ID, oldWorkItemID, newWorkItemID);
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
                msgs = ((InternalEObject)workItemID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.GET_OFFER_SET_TYPE__WORK_ITEM_ID, null, msgs);
            if (newWorkItemID != null)
                msgs = ((InternalEObject)newWorkItemID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.GET_OFFER_SET_TYPE__WORK_ITEM_ID, null, msgs);
            msgs = basicSetWorkItemID(newWorkItemID, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_OFFER_SET_TYPE__WORK_ITEM_ID, newWorkItemID, newWorkItemID));
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
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_OFFER_SET_TYPE__API_VERSION, oldApiVersion, apiVersion, !oldApiVersionESet));
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
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.GET_OFFER_SET_TYPE__API_VERSION, oldApiVersion, API_VERSION_EDEFAULT, oldApiVersionESet));
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
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.GET_OFFER_SET_TYPE__WORK_ITEM_ID:
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
            case N2BRMPackage.GET_OFFER_SET_TYPE__WORK_ITEM_ID:
                return getWorkItemID();
            case N2BRMPackage.GET_OFFER_SET_TYPE__API_VERSION:
                return getApiVersion();
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
            case N2BRMPackage.GET_OFFER_SET_TYPE__WORK_ITEM_ID:
                setWorkItemID((ManagedObjectID)newValue);
                return;
            case N2BRMPackage.GET_OFFER_SET_TYPE__API_VERSION:
                setApiVersion((Integer)newValue);
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
            case N2BRMPackage.GET_OFFER_SET_TYPE__WORK_ITEM_ID:
                setWorkItemID((ManagedObjectID)null);
                return;
            case N2BRMPackage.GET_OFFER_SET_TYPE__API_VERSION:
                unsetApiVersion();
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
            case N2BRMPackage.GET_OFFER_SET_TYPE__WORK_ITEM_ID:
                return workItemID != null;
            case N2BRMPackage.GET_OFFER_SET_TYPE__API_VERSION:
                return isSetApiVersion();
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
        result.append(')');
        return result.toString();
    }

} //GetOfferSetTypeImpl
