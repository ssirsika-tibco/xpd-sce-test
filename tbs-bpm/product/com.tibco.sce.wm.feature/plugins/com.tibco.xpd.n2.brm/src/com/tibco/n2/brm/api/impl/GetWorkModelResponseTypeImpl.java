/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.GetWorkModelResponseType;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkModel;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Get Work Model Response Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkModelResponseTypeImpl#getWorkModel <em>Work Model</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GetWorkModelResponseTypeImpl extends EObjectImpl implements GetWorkModelResponseType {
    /**
     * The cached value of the '{@link #getWorkModel() <em>Work Model</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkModel()
     * @generated
     * @ordered
     */
    protected WorkModel workModel;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GetWorkModelResponseTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.GET_WORK_MODEL_RESPONSE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkModel getWorkModel() {
        return workModel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkModel(WorkModel newWorkModel, NotificationChain msgs) {
        WorkModel oldWorkModel = workModel;
        workModel = newWorkModel;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_MODEL_RESPONSE_TYPE__WORK_MODEL, oldWorkModel, newWorkModel);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkModel(WorkModel newWorkModel) {
        if (newWorkModel != workModel) {
            NotificationChain msgs = null;
            if (workModel != null)
                msgs = ((InternalEObject)workModel).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.GET_WORK_MODEL_RESPONSE_TYPE__WORK_MODEL, null, msgs);
            if (newWorkModel != null)
                msgs = ((InternalEObject)newWorkModel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.GET_WORK_MODEL_RESPONSE_TYPE__WORK_MODEL, null, msgs);
            msgs = basicSetWorkModel(newWorkModel, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_MODEL_RESPONSE_TYPE__WORK_MODEL, newWorkModel, newWorkModel));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.GET_WORK_MODEL_RESPONSE_TYPE__WORK_MODEL:
                return basicSetWorkModel(null, msgs);
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
            case N2BRMPackage.GET_WORK_MODEL_RESPONSE_TYPE__WORK_MODEL:
                return getWorkModel();
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
            case N2BRMPackage.GET_WORK_MODEL_RESPONSE_TYPE__WORK_MODEL:
                setWorkModel((WorkModel)newValue);
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
            case N2BRMPackage.GET_WORK_MODEL_RESPONSE_TYPE__WORK_MODEL:
                setWorkModel((WorkModel)null);
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
            case N2BRMPackage.GET_WORK_MODEL_RESPONSE_TYPE__WORK_MODEL:
                return workModel != null;
        }
        return super.eIsSet(featureID);
    }

} //GetWorkModelResponseTypeImpl
