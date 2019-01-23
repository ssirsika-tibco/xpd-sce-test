/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.GetWorkTypeResponseType;
import com.tibco.n2.brm.api.N2BRMPackage;

import com.tibco.n2.common.datamodel.WorkType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Get Work Type Response Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkTypeResponseTypeImpl#getWorkType <em>Work Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GetWorkTypeResponseTypeImpl extends EObjectImpl implements GetWorkTypeResponseType {
    /**
     * The cached value of the '{@link #getWorkType() <em>Work Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkType()
     * @generated
     * @ordered
     */
    protected WorkType workType;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GetWorkTypeResponseTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.GET_WORK_TYPE_RESPONSE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkType getWorkType() {
        return workType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkType(WorkType newWorkType, NotificationChain msgs) {
        WorkType oldWorkType = workType;
        workType = newWorkType;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_TYPE_RESPONSE_TYPE__WORK_TYPE, oldWorkType, newWorkType);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkType(WorkType newWorkType) {
        if (newWorkType != workType) {
            NotificationChain msgs = null;
            if (workType != null)
                msgs = ((InternalEObject)workType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.GET_WORK_TYPE_RESPONSE_TYPE__WORK_TYPE, null, msgs);
            if (newWorkType != null)
                msgs = ((InternalEObject)newWorkType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.GET_WORK_TYPE_RESPONSE_TYPE__WORK_TYPE, null, msgs);
            msgs = basicSetWorkType(newWorkType, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_TYPE_RESPONSE_TYPE__WORK_TYPE, newWorkType, newWorkType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.GET_WORK_TYPE_RESPONSE_TYPE__WORK_TYPE:
                return basicSetWorkType(null, msgs);
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
            case N2BRMPackage.GET_WORK_TYPE_RESPONSE_TYPE__WORK_TYPE:
                return getWorkType();
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
            case N2BRMPackage.GET_WORK_TYPE_RESPONSE_TYPE__WORK_TYPE:
                setWorkType((WorkType)newValue);
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
            case N2BRMPackage.GET_WORK_TYPE_RESPONSE_TYPE__WORK_TYPE:
                setWorkType((WorkType)null);
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
            case N2BRMPackage.GET_WORK_TYPE_RESPONSE_TYPE__WORK_TYPE:
                return workType != null;
        }
        return super.eIsSet(featureID);
    }

} //GetWorkTypeResponseTypeImpl
