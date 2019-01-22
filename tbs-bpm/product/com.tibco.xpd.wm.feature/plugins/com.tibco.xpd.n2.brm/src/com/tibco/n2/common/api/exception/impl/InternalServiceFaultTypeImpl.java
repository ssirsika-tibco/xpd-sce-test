/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.api.exception.impl;

import com.tibco.n2.common.api.exception.ErrorLine;
import com.tibco.n2.common.api.exception.ExceptionPackage;
import com.tibco.n2.common.api.exception.InternalServiceFaultType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Internal Service Fault Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.api.exception.impl.InternalServiceFaultTypeImpl#getError <em>Error</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InternalServiceFaultTypeImpl extends EObjectImpl implements InternalServiceFaultType {
    /**
     * The cached value of the '{@link #getError() <em>Error</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getError()
     * @generated
     * @ordered
     */
    protected ErrorLine error;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected InternalServiceFaultTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ExceptionPackage.Literals.INTERNAL_SERVICE_FAULT_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ErrorLine getError() {
        return error;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetError(ErrorLine newError, NotificationChain msgs) {
        ErrorLine oldError = error;
        error = newError;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ExceptionPackage.INTERNAL_SERVICE_FAULT_TYPE__ERROR, oldError, newError);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setError(ErrorLine newError) {
        if (newError != error) {
            NotificationChain msgs = null;
            if (error != null)
                msgs = ((InternalEObject)error).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ExceptionPackage.INTERNAL_SERVICE_FAULT_TYPE__ERROR, null, msgs);
            if (newError != null)
                msgs = ((InternalEObject)newError).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ExceptionPackage.INTERNAL_SERVICE_FAULT_TYPE__ERROR, null, msgs);
            msgs = basicSetError(newError, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ExceptionPackage.INTERNAL_SERVICE_FAULT_TYPE__ERROR, newError, newError));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ExceptionPackage.INTERNAL_SERVICE_FAULT_TYPE__ERROR:
                return basicSetError(null, msgs);
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
            case ExceptionPackage.INTERNAL_SERVICE_FAULT_TYPE__ERROR:
                return getError();
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
            case ExceptionPackage.INTERNAL_SERVICE_FAULT_TYPE__ERROR:
                setError((ErrorLine)newValue);
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
            case ExceptionPackage.INTERNAL_SERVICE_FAULT_TYPE__ERROR:
                setError((ErrorLine)null);
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
            case ExceptionPackage.INTERNAL_SERVICE_FAULT_TYPE__ERROR:
                return error != null;
        }
        return super.eIsSet(featureID);
    }

} //InternalServiceFaultTypeImpl
