/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.GetWorkModelsResponseType;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkModelList;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Get Work Models Response Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkModelsResponseTypeImpl#getWorkModelList <em>Work Model List</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GetWorkModelsResponseTypeImpl extends EObjectImpl implements GetWorkModelsResponseType {
    /**
     * The cached value of the '{@link #getWorkModelList() <em>Work Model List</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkModelList()
     * @generated
     * @ordered
     */
    protected WorkModelList workModelList;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GetWorkModelsResponseTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.GET_WORK_MODELS_RESPONSE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkModelList getWorkModelList() {
        return workModelList;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkModelList(WorkModelList newWorkModelList, NotificationChain msgs) {
        WorkModelList oldWorkModelList = workModelList;
        workModelList = newWorkModelList;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_MODELS_RESPONSE_TYPE__WORK_MODEL_LIST, oldWorkModelList, newWorkModelList);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkModelList(WorkModelList newWorkModelList) {
        if (newWorkModelList != workModelList) {
            NotificationChain msgs = null;
            if (workModelList != null)
                msgs = ((InternalEObject)workModelList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.GET_WORK_MODELS_RESPONSE_TYPE__WORK_MODEL_LIST, null, msgs);
            if (newWorkModelList != null)
                msgs = ((InternalEObject)newWorkModelList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.GET_WORK_MODELS_RESPONSE_TYPE__WORK_MODEL_LIST, null, msgs);
            msgs = basicSetWorkModelList(newWorkModelList, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_MODELS_RESPONSE_TYPE__WORK_MODEL_LIST, newWorkModelList, newWorkModelList));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.GET_WORK_MODELS_RESPONSE_TYPE__WORK_MODEL_LIST:
                return basicSetWorkModelList(null, msgs);
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
            case N2BRMPackage.GET_WORK_MODELS_RESPONSE_TYPE__WORK_MODEL_LIST:
                return getWorkModelList();
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
            case N2BRMPackage.GET_WORK_MODELS_RESPONSE_TYPE__WORK_MODEL_LIST:
                setWorkModelList((WorkModelList)newValue);
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
            case N2BRMPackage.GET_WORK_MODELS_RESPONSE_TYPE__WORK_MODEL_LIST:
                setWorkModelList((WorkModelList)null);
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
            case N2BRMPackage.GET_WORK_MODELS_RESPONSE_TYPE__WORK_MODEL_LIST:
                return workModelList != null;
        }
        return super.eIsSet(featureID);
    }

} //GetWorkModelsResponseTypeImpl
