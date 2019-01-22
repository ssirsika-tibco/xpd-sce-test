/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.GetWorkTypesResponseType;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkTypeList;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Get Work Types Response Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkTypesResponseTypeImpl#getWorkTypelList <em>Work Typel List</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GetWorkTypesResponseTypeImpl extends EObjectImpl implements GetWorkTypesResponseType {
    /**
     * The cached value of the '{@link #getWorkTypelList() <em>Work Typel List</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkTypelList()
     * @generated
     * @ordered
     */
    protected WorkTypeList workTypelList;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GetWorkTypesResponseTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.GET_WORK_TYPES_RESPONSE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkTypeList getWorkTypelList() {
        return workTypelList;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkTypelList(WorkTypeList newWorkTypelList, NotificationChain msgs) {
        WorkTypeList oldWorkTypelList = workTypelList;
        workTypelList = newWorkTypelList;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_TYPES_RESPONSE_TYPE__WORK_TYPEL_LIST, oldWorkTypelList, newWorkTypelList);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkTypelList(WorkTypeList newWorkTypelList) {
        if (newWorkTypelList != workTypelList) {
            NotificationChain msgs = null;
            if (workTypelList != null)
                msgs = ((InternalEObject)workTypelList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.GET_WORK_TYPES_RESPONSE_TYPE__WORK_TYPEL_LIST, null, msgs);
            if (newWorkTypelList != null)
                msgs = ((InternalEObject)newWorkTypelList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.GET_WORK_TYPES_RESPONSE_TYPE__WORK_TYPEL_LIST, null, msgs);
            msgs = basicSetWorkTypelList(newWorkTypelList, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_TYPES_RESPONSE_TYPE__WORK_TYPEL_LIST, newWorkTypelList, newWorkTypelList));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.GET_WORK_TYPES_RESPONSE_TYPE__WORK_TYPEL_LIST:
                return basicSetWorkTypelList(null, msgs);
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
            case N2BRMPackage.GET_WORK_TYPES_RESPONSE_TYPE__WORK_TYPEL_LIST:
                return getWorkTypelList();
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
            case N2BRMPackage.GET_WORK_TYPES_RESPONSE_TYPE__WORK_TYPEL_LIST:
                setWorkTypelList((WorkTypeList)newValue);
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
            case N2BRMPackage.GET_WORK_TYPES_RESPONSE_TYPE__WORK_TYPEL_LIST:
                setWorkTypelList((WorkTypeList)null);
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
            case N2BRMPackage.GET_WORK_TYPES_RESPONSE_TYPE__WORK_TYPEL_LIST:
                return workTypelList != null;
        }
        return super.eIsSet(featureID);
    }

} //GetWorkTypesResponseTypeImpl
